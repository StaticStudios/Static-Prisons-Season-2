package net.staticstudios.prisons.mines.old;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.logging.Level;

public abstract class BaseMine {
    public static final int percentOfMineToBeDestroyedBeforeRefill = 50;
    public static final int distanceBetweenMines = 500; //X axis
    public static int currentOffsetAmount = 0;

    private int timeBetweenRefills = MineManager.defaultMineRefillTime;
    public int timeUntilNextRefill;
    public long lastRefilledAt = 0; //In seconds
    //public int autoRefillTimeOffset = 0;
    private boolean refillsOnTimer = false;
    private boolean refillWhenMineIsEmpty = true;

    private final int amountOfBlocksRequiredToBeBrokenToRefill;
    private final int maxBlocksInMine;
    private int blocksInMine;

    public final Location minLocation;
    public final Location maxLocation;
    private Location whereToTpPlayerOnRefill;

    public final String mineID;
    public final int mineOffset; //X axis

    public final Region region;
    private final RandomPattern randomPattern = new RandomPattern();


    private BlockType type;
    private boolean singleType = false;

    private int tokenMultiplier = 0;

    public int getTokenMultiplier() { return tokenMultiplier; }
    public void setTokenMultiplier(int value) { tokenMultiplier = value; }


    public BaseMine(String mineID, Location loc1, Location loc2) {
        this.mineID = mineID;

        this.mineOffset = currentOffsetAmount;
        currentOffsetAmount += distanceBetweenMines;

        loc1.setX(loc1.getX() + mineOffset);
        loc2.setX(loc2.getX() + mineOffset);
        this.minLocation = PrisonUtils.calcMinPoint(loc1, loc2);
        this.maxLocation = PrisonUtils.calcMaxPoint(loc1, loc2);

        region = new CuboidRegion(BukkitAdapter.adapt(minLocation.getWorld()), BlockVector3.at(minLocation.getX(), minLocation.getY(), minLocation.getZ()), BlockVector3.at(maxLocation.getX(), maxLocation.getY(), maxLocation.getZ()));
        whereToTpPlayerOnRefill = new Location(Bukkit.getWorld(mineID), 0, 100, 0);

        maxBlocksInMine = (Math.abs((int) minLocation.getX() - mineOffset) + Math.abs((int) maxLocation.getX() - mineOffset) + 1) * (Math.abs((int) minLocation.getZ()) + Math.abs((int) maxLocation.getZ()) + 1) * (Math.abs((int) minLocation.getY()) + Math.abs((int) maxLocation.getY()));
        blocksInMine = 0;
        amountOfBlocksRequiredToBeBrokenToRefill = maxBlocksInMine * percentOfMineToBeDestroyedBeforeRefill / 100;

        registerWorldGuard();
        MineManager.registerMine(this, false);
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public int getAmountOfBlocksRequiredToBeBrokenToRefill() {
        return amountOfBlocksRequiredToBeBrokenToRefill;
    }

    public int getMaxBlocksInMine() {
        return maxBlocksInMine;
    }

    public int getBlocksInMine() {
        return blocksInMine;
    }

    public void setWhereToTpPlayerOnRefill(Location whereToTpPlayerOnRefill) {
        this.whereToTpPlayerOnRefill = whereToTpPlayerOnRefill;
    }

    public Location getWhereToTpPlayerOnRefill() {
        return whereToTpPlayerOnRefill;
    }

    public long getLastRefilledAt() {
        return lastRefilledAt;
    }

    public void setLastRefilledAt(long lastRefilledAt) {
        this.lastRefilledAt = lastRefilledAt;
    }

    public boolean getIfRefillsOnTimer() {
        return refillsOnTimer;
    }

    public void setIfRefillsOnTimer(boolean value) {
        if (value) {
            refillsOnTimer = true;
            timeUntilNextRefill = 0;
            MineManager.minesThatShouldRefillOnTimer.put(mineID, this);
        } else {
            refillsOnTimer = false;
            MineManager.minesThatShouldRefillOnTimer.remove(mineID);
        }
    }

    public int getTimeBetweenRefills() {
        return timeBetweenRefills;
    }

    public void setTimeBetweenRefills(int value) {
        timeBetweenRefills = value;
    }

    public boolean isRefillWhenMineIsEmpty() {
        return refillWhenMineIsEmpty;
    }

    public void setRefillWhenMineIsEmpty(boolean refillWhenMineIsEmpty) {
        this.refillWhenMineIsEmpty = refillWhenMineIsEmpty;
    }
    public Region getRegion() {
        return region;
    }
    public boolean brokeBlocksInMine(long blocksBroken) {
        blocksInMine -= blocksBroken;
        if (refillWhenMineIsEmpty && blocksInMine <= amountOfBlocksRequiredToBeBrokenToRefill) {
            refill();
            return true;
        }
        return false;
    }


    public void setBlockPattern(MineBlock[] mineBlocks) {
        if (mineBlocks.length == 1) {
            singleType = true;
            type = BukkitAdapter.asBlockType(mineBlocks[0].type);
        } else return;//createRandomPattern(mineBlocks);
    }




    public void registerWorldGuard() {
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(mineID + "-MINE", BlockVector3.at(minLocation.getX(), minLocation.getY(), minLocation.getZ()), BlockVector3.at(maxLocation.getX(), maxLocation.getY(), maxLocation.getZ()));
        region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
        region.setPriority(1);
        WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("mines"))).addRegion(region);
    }

    public void refill() {
        Bukkit.getLogger().log(Level.INFO, "Mine Refilled: " + mineID);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
            if (singleType) {
                editSession.setBlocks(region, type);
            } else editSession.setBlocks(region, randomPattern);
            editSession.close();
        });
        setLastRefilledAt(Instant.now().getEpochSecond());
        for (Player p : Bukkit.getWorld("mines").getPlayers()) {
            if (isPlayerInMine(p)) {
                try {
                    p.teleport(whereToTpPlayerOnRefill);
                } catch (IllegalStateException ignore) {
                }
            }
            if (p.getLocation().getX() >= mineOffset - distanceBetweenMines / 2 && p.getLocation().getX() <= mineOffset + distanceBetweenMines / 2)
                p.sendMessage(ChatColor.LIGHT_PURPLE + "This mine has been refilled!");
        }
        if (refillsOnTimer) timeUntilNextRefill = timeBetweenRefills;
        blocksInMine = maxBlocksInMine;
    }

    /*
    void createRandomPattern(MineBlock[] blocks) {
        for (MineBlock mineBlock : blocks) {
            this.randomPattern.add(BukkitAdapter.asBlockType(mineBlock.type).getDefaultState().toBaseBlock(), mineBlock.chance);
        }
    }

     */

    boolean isPlayerInMine(Player p) {
        return (minLocation.getX() <= p.getLocation().getX() && p.getLocation().getX() <= maxLocation.getX() &&
                minLocation.getY() <= p.getLocation().getY() && p.getLocation().getY() <= maxLocation.getY() &&
                minLocation.getZ() <= p.getLocation().getZ() && p.getLocation().getZ() <= maxLocation.getZ()
        );
    }
}