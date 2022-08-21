package net.staticstudios.mines.builder;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.utils.WeightedElements;

import java.util.UUID;
import java.util.function.Consumer;

public class StaticMineBuilder {

    /**
     * @return A new MineBuilder instance.
     */
    public static StaticMineBuilder getBuilder() {
        return new StaticMineBuilder();
    }

    private String id = UUID.randomUUID().toString();
    private BlockVector3 corner1 = BlockVector3.at(0, 0, 0);
    private BlockVector3 corner2 = BlockVector3.at(0, 0, 0);
    private org.bukkit.World bukkitWorld;
    private WeightedElements<BlockType> blocks = new WeightedElements<BlockType>().add(BlockTypes.STONE, 1);
    private Consumer<StaticMine> onRefill;


    //Settings
    private boolean async = false;
    private boolean saveToFile = false;
    private double refillAtPercentFull = 50d;
    private int secondsBetweenRefill = 600;
    private boolean refillOnTimer = false;


    public StaticMineBuilder() {}

    /**
     * Set the mine's ID.
     * @param id The mine's ID.
     * @return This builder instance.
     */
    public StaticMineBuilder id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Set the corners of the mine.
     * @param corner1 The mine's corner 1.
     * @param corner2 The mine's corner 2.
     * @return This builder instance.
     */
    public StaticMineBuilder corners(BlockVector3 corner1, BlockVector3 corner2) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        return this;
    }

    /**
     * Set the mine's world.
     * @param world The mine's world.
     * @return This builder instance.
     */
    public StaticMineBuilder world(World world) {
        this.bukkitWorld = BukkitAdapter.adapt(world);
        return this;
    }

    /**
     * Set the mine's world.
     * @param world The mine's world.
     * @return This builder instance.
     */
    public StaticMineBuilder world(org.bukkit.World world) {
        this.bukkitWorld = world;
        return this;
    }

    /**
     * Set the mine's blocks.
     * @param blocks The mine's blocks.
     * @return This builder instance.
     */
    public StaticMineBuilder blocks(WeightedElements<BlockType> blocks) {
        this.blocks = blocks;
        return this;
    }

    /**
     * Set whether the mine should refill synchronously.
     * @param refillSync The mine's refill sync setting.
     * @return This builder instance.
     */
    public StaticMineBuilder refillSync(boolean refillSync) {
        this.async = refillSync;
        return this;
    }

    /**
     * Set whether the mine should save to file.
     * @param saveToFile The mine's save to file setting.
     * @return This builder instance.
     */
    public StaticMineBuilder saveToFile(boolean saveToFile) {
        this.saveToFile = saveToFile;
        return this;
    }

    /**
     * Set the amount of the mine left before it should refill automatically.
     * @param refillAtPercentFull The mine's refill at percent full setting (0 - 100).
     * @return This builder instance.
     */
    public StaticMineBuilder refillAtPercentFull(double refillAtPercentFull) {
        this.refillAtPercentFull = refillAtPercentFull;
        return this;
    }

    /**
     * Set the amount of time between automatic refills.
     * @param secondsBetweenRefill The mine's seconds between refills setting.
     * @return This builder instance.
     */
    public StaticMineBuilder secondsBetweenRefill(int secondsBetweenRefill) {
        this.secondsBetweenRefill = secondsBetweenRefill;
        return this;
    }

    /**
     * Set whether the mine should refill on timer.
     * @param refillOnTimer The mine's refill on timer setting.
     * @return This builder instance.
     */
    public StaticMineBuilder refillOnTimer(boolean refillOnTimer) {
        this.refillOnTimer = refillOnTimer;
        return this;
    }

    /**
     * Set whether the mine should refill asynchronously.
     * @param async The mine's async setting.
     * @return This builder instance.
     */
    public StaticMineBuilder async(boolean async) {
        this.async = async;
        return this;
    }

    /**
     * Set the mine's on refill callback.
     * @param consumer The mine's on refill callback.
     * @return This builder instance.
     */
    public StaticMineBuilder onRefill(Consumer<StaticMine> consumer) {
        onRefill = consumer;
        return this;
    }

    /**
     * Build the mine.
     * @return The built mine.
     */
    public StaticMine build() {
        StaticMine mine = new StaticMine(id, bukkitWorld, corner1, corner2, blocks);
        mine.runOnRefill(onRefill);
        mine.settings().async(async);
        mine.settings().saveToFile(saveToFile);
        mine.settings().refillAtPercentFull(refillAtPercentFull);
        mine.settings().secondsBetweenRefill(secondsBetweenRefill);
        mine.settings().refillOnTimer(refillOnTimer);
        return mine;
    }
}
