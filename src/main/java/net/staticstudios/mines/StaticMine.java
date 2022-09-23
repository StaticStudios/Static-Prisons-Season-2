package net.staticstudios.mines;

import com.google.common.base.Preconditions;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.mines.utils.WeightedElements;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Each instance of this class represents a mine.
 */
public class StaticMine {

    /**
     * @param id The mine's ID.
     * @return The mine with the given ID, or null if no mine with the given ID exists.
     */
    public static StaticMine getMine(String id) {
        return StaticMines.MINES.get(id);
    }

    private final String id;
    private final org.bukkit.World bukkitWorld;
    private final World world;
    private final BlockVector3 minPoint;
    private final BlockVector3 maxPoint;
    private final Region mineRegion;
    private final ProtectedRegion protectedMineRegion;
    private final RandomPattern blockPattern;
    private final WeightedElements<BlockType> blocks;

    private final StaticMineSettings settings = new StaticMineSettings();

    private long lastRefilledAt = 0;
    private long currentBlockCount = 0;

    private Consumer<StaticMine> onRefill = null;

    /**
     * @return The mine's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The mine's Bukkit world.
     */
    public org.bukkit.World getBukkitWorld() {
        return bukkitWorld;
    }

    /**
     * @return The mine's WorldEdit world.
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return The mine's minimum point.
     */
    public BlockVector3 getMinPoint() {
        return minPoint;
    }

    /**
     * @return The mine's maximum point.
     */
    public BlockVector3 getMaxPoint() {
        return maxPoint;
    }

    /**
     * @return The mine's region.
     */
    public Region getRegion() {
        return mineRegion;
    }

    /**
     * @return The mine's WorldGuard region.
     */
    public ProtectedRegion getProtectedMineRegion() {
        return protectedMineRegion;
    }

    /**
     * @return The mine's block pattern.
     */
    public RandomPattern getBlockPattern() {
        return blockPattern;
    }

    /**
     * @return The mine's blocks.
     */
    public WeightedElements<BlockType> getBlocks() {
        return blocks;
    }

    /**
     * @return The mine's settings.
     */
    public StaticMineSettings settings() {
        return settings;
    }

    /**
     * @return The time (in milliseconds) that the mine was last refilled. If the mine has never been refilled, this will be 0.
     */
    public long getLastRefilledAt() {
        return lastRefilledAt;
    }

    void setLastRefilledAt(long lastRefilledAt) {
        this.lastRefilledAt = lastRefilledAt;
    }

    /**
     * @return The amount of blocks in the mine when it is full.
     */
    public long getBlockCountWhenFull() {
        return mineRegion.getVolume();
    }

    /**
     * @return The current amount of blocks in the mine.
     */
    public long getCurrentBlockCount() {
        return currentBlockCount;
    }



    public StaticMine(String id, org.bukkit.World bukkitWorld,
                      BlockVector3 corner1, BlockVector3 corner2,
                      WeightedElements<BlockType> blocks) {

        Preconditions.checkNotNull(id, "id");
        Preconditions.checkNotNull(bukkitWorld, "bukkitWorld");
        Preconditions.checkNotNull(corner1, "corner1");
        Preconditions.checkNotNull(corner2, "corner2");
        Preconditions.checkNotNull(blocks, "mine blocks");

        if (StaticMines.MINES.containsKey(id)) {
            throw new IllegalArgumentException("Tried to create a mine with the ID: '" + id + "' when a mine with that ID already exists!");
        }

        this.id = id;
        this.bukkitWorld = bukkitWorld;
        this.world = BukkitAdapter.adapt(bukkitWorld);
        this.minPoint = StaticMineUtils.getMinPoint(corner1, corner2);
        this.maxPoint = StaticMineUtils.getMaxPoint(corner1, corner2);
        this.mineRegion = new CuboidRegion(world, minPoint, maxPoint);
        this.protectedMineRegion = new ProtectedCuboidRegion(id + StaticMinesConfig.REGION_SUFFIX, minPoint, maxPoint);
        this.blocks = blocks;
        this.blockPattern = StaticMineUtils.toRandomPattern(blocks);

        this.protectedMineRegion.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
        this.protectedMineRegion.setPriority(1);

        StaticMines.registerMine(this);
    }

    /**
     * Refills the mine. This may or may not be asynchronous.
     * @return A future that will be completed when the refill operation has been completed.
     */
    public CompletableFuture<StaticMine> refill() {
        CompletableFuture<StaticMine> future = new CompletableFuture<>();
        Runnable runnable = () -> {
            long startTime = System.nanoTime();
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
                editSession.setBlocks(mineRegion, blockPattern);
            }

            currentBlockCount = mineRegion.getVolume();
            lastRefilledAt = System.currentTimeMillis();

            StaticMinesThreadManager.runOnMainThread(() -> {
                if (onRefill != null) {
                    onRefill.accept(this);
                }
                future.complete(this);
            });
            long endTime = System.nanoTime();
            StaticMines.log("Refilled '" + id + "' in " + new DecimalFormat("#.##").format((endTime - startTime) / 1_000_000d) + "ms (" + (settings.async() ? "async" : "sync") + ")");
        };

        if (settings.async()) {
            lastRefilledAt = System.currentTimeMillis(); //Set this here so that the timer loop doesn't try and reset it twice at the same time.
            currentBlockCount = mineRegion.getVolume(); //Set this now so the mine doesn't attempt to refill multiple times at once in certain situations.
            StaticMinesThreadManager.submit(runnable);
        } else {
            runnable.run();
        }

        return future;
    }

    /**
     * Set a callback to be run each time the mine is refilled.
     * @param consumer The callback to be run. If it is null, then no callback will be run.
     */
    public void runOnRefill(@Nullable Consumer<StaticMine> consumer) {
        this.onRefill = consumer;
    }

    /**
     * Unregister this mine, this will disable all listeners
     */
    public void delete() {
        StaticMines.unregisterMine(this);
    }

    /**
     * @return A list of all the players within the mine region.
     */
    public List<Player> getPlayersInMine() {
        List<Player> players = new LinkedList<>();
        for (Player player : bukkitWorld.getPlayers()) {
            if (player.getLocation().getX() < minPoint.getX() || player.getLocation().getY() < minPoint.getY() || player.getLocation().getZ() < minPoint.getZ())
                continue;
            if (player.getLocation().getX() > maxPoint.getX() || player.getLocation().getY() > maxPoint.getY() || player.getLocation().getZ() > maxPoint.getZ())
                continue;

            players.add(player);
        }
        return players;
    }

    /**
     * Call this method to update the mine's current block count, specifying the amount of blocks that were broken.
     * @param blocksToRemove The amount of blocks to remove from the mine's block count.
     * @return true if the mine was refilled, false if it was not.
     */
    public boolean removeBlocks(long blocksToRemove) {
        currentBlockCount -= blocksToRemove;
        if (currentBlockCount < 0 || currentBlockCount / (double) getBlockCountWhenFull() * 100 < settings.refillAtPercentFull()) {
            refill();
            return true;
        }
        return false;
    }
}

