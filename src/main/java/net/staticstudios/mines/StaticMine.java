package net.staticstudios.mines;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.staticstudios.mines.minesapi.events.MineRefilledEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class StaticMine {

    /**
     * create mines from a command
     * refill timer as well
     */

    public static RegionManager WORLDGAURD_MINE_REGION_MANAGER;
    private static boolean hasRemovedOldWGRegions = false;

    private static Map<String, StaticMine> ALL_MINES = new HashMap<>();
    private static List<String> SORTED_MINE_IDS = new ArrayList<>();
    public static StaticMine getMine(String id) { return ALL_MINES.get(id); }
    public static Collection<StaticMine> getAllMines() { return ALL_MINES.values(); }
    public static Set<String> getAllMineIDS() { return ALL_MINES.keySet(); }
    public static void unloadAllMines() { ALL_MINES = new HashMap<>(); }

    private String id;
    private BlockVector3 minPoint;
    private BlockVector3 maxPoint;
    private org.bukkit.World world;
    private World weWorld;
    private Region region;
    private ProtectedCuboidRegion wgRegion;
    private boolean shouldRefillSync = false;
    private boolean shouldSaveToFile = false;
    private MineBlock[] mineBlocks = new MineBlock[] {new MineBlock(BlockTypes.STONE, 100) };

    private long blocksInMine;
    private long blocksInFullMine;
    private double refillAtPercentLeft = 50d; //TODO: can configure in config

    public String getID() { return id; }
    public org.bukkit.World getWorld() { return world; }
    public World getWEWorld() { return weWorld; }
    public Location getMinPoint() { return new Location(world, minPoint.getX(), minPoint.getY(), minPoint.getZ(), 0, 0); }
    public Location getMaxPoint() { return new Location(world, maxPoint.getX(), maxPoint.getY(), maxPoint.getZ(), 0, 0); }
    public BlockVector3 getMinVector() { return minPoint; }
    public BlockVector3 getMaxVector() { return maxPoint; }
    public boolean getShouldRefillSync() { return shouldRefillSync; }
    public void setShouldRefillSync(boolean shouldRefillSync) { this.shouldRefillSync = shouldRefillSync; }
    public MineBlock[] getMineBlocks() { return mineBlocks; }
    public void setMineBlocks(MineBlock[] mineBlocks) { this.mineBlocks = mineBlocks; }
    public RandomPattern getBlockPattern() { return MineBlock.buildRandomPattern(mineBlocks); }
    public boolean isShouldSaveToFile() { return shouldSaveToFile; }
    public void setShouldSaveToFile(boolean shouldSaveToFile) { this.shouldSaveToFile = shouldSaveToFile; }
    public Region getRegion() { return region; }
    public ProtectedRegion getWorldGuardRegion() { return wgRegion; }
    public void removeBlocksBrokenInMine(long blocksBroken) {
        blocksInMine -= blocksBroken;
        if ((double) blocksInMine / blocksInFullMine < refillAtPercentLeft / 100) refill();
    }


    public StaticMine(String id, Location point1, Location point2) {
        if (!Objects.equals(point1.getWorld(), point2.getWorld())) {
            Bukkit.getLogger().warning("Tried to create a world with min and max points in different worlds! Skipping it...");
            return;
        }
        this.id = id;
        Location minPoint = StaticMine.getMinPoint(point1, point2);
        Location maxPoint = StaticMine.getMaxPoint(point1, point2);
        this.minPoint = BlockVector3.at(minPoint.getBlockX(), minPoint.getBlockY(), minPoint.getBlockZ());
        this.maxPoint = BlockVector3.at(maxPoint.getBlockX(), maxPoint.getBlockY(), maxPoint.getBlockZ());
        blocksInFullMine = (long) (maxPoint.getBlockX() - minPoint.getBlockX()) * (maxPoint.getBlockY() - minPoint.getBlockY()) * (maxPoint.getBlockZ() - minPoint.getBlockZ());
        blocksInMine = 0;
        world = minPoint.getWorld();
        weWorld = BukkitAdapter.adapt(world);
        region = new CuboidRegion(weWorld, this.minPoint, this.maxPoint);
        wgRegion = new ProtectedCuboidRegion(id + "--static-mine", this.minPoint, this.maxPoint);
        wgRegion.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
        wgRegion.setPriority(1);
        WORLDGAURD_MINE_REGION_MANAGER.addRegion(wgRegion);

        ALL_MINES.put(this.id, this);
        SORTED_MINE_IDS.add(this.id);
    }

    public void refill() {
        if (!shouldRefillSync) {
            Bukkit.getScheduler().runTaskAsynchronously(StaticMines.getParent(), () -> refillMine(true));
        } else refillMine(false);
    }

    void refillMine(boolean async) {
        EditSession editSession = WorldEdit.getInstance().newEditSession(weWorld);
        editSession.setBlocks(region, getBlockPattern());
        editSession.close();
        blocksInMine = (long) (maxPoint.getBlockX() - minPoint.getBlockX()) * (maxPoint.getBlockY() - minPoint.getBlockY()) * (maxPoint.getBlockZ() - minPoint.getBlockZ());
        Bukkit.getLogger().log(Level.INFO, "Refilled mine: " + id);
        if (async) {
            Bukkit.getScheduler().runTask(StaticMines.getParent(), () -> Bukkit.getPluginManager().callEvent(new MineRefilledEvent(id, getMinPoint(), getMaxPoint())));
        } else Bukkit.getPluginManager().callEvent(new MineRefilledEvent(id, getMinPoint(), getMaxPoint()));
    }


    //Loading/Saving
    public static void loadMines() {
        unloadAllMines();
        FileConfiguration config = StaticMines.getInstance().getConfig();
        if (!config.contains("wait_for_world")) config.set("wait_for_world", "null");
        if (!config.contains("mines_load_delay")) config.set("mines_load_delay", 20);
        StaticMines.getInstance().saveConfig();
        tryToLoadMines(config.getString("wait_for_world"), config.getInt("mines_load_delay"));
    }

    private static void tryToLoadMines(String worldToWaitFor, int waitAfterWorldLoads) {
        if (Bukkit.getWorld(worldToWaitFor) == null) {
            Bukkit.getScheduler().runTaskLater(StaticMines.getParent(), () -> tryToLoadMines(worldToWaitFor, waitAfterWorldLoads), 1);
            return;
        }
        if (WORLDGAURD_MINE_REGION_MANAGER == null) WORLDGAURD_MINE_REGION_MANAGER = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld(worldToWaitFor)));
        if (!hasRemovedOldWGRegions) {
            for (String rgID : WORLDGAURD_MINE_REGION_MANAGER.getRegions().keySet()) {
                if (rgID.endsWith("--static-mine")) WORLDGAURD_MINE_REGION_MANAGER.removeRegion(rgID);
            }
            hasRemovedOldWGRegions = true;
        }
        Bukkit.getScheduler().runTaskLater(StaticMines.getParent(), () -> {
            File minesConfigFile = new File(StaticMines.getInstance().getDataFolder(), "mines.yml");
                try {
                    minesConfigFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            FileConfiguration minesConfig = YamlConfiguration.loadConfiguration(minesConfigFile);
            for (String key : minesConfig.getKeys(false)) loadMine(key, minesConfig.getConfigurationSection(key));
        }, Math.max(0, waitAfterWorldLoads));
        StaticMines.getParent().getLogger().log(Level.INFO, "Finished loading all mines (mines.yml)");
    }

    static void loadMine(String mineID, ConfigurationSection mineConfig) {
        if (mineConfig.getString("location.point1.world") == null || mineConfig.getString("location.point2.world") == null) {
            StaticMines.getParent().getLogger().warning("Tried to load a mine with an invalid location! Skipping it... Mine: " + mineID);
            return;
        }
        if (Bukkit.getWorld(mineConfig.getString("location.point1.world")) == null || Bukkit.getWorld(mineConfig.getString("location.point2.world")) == null) {
            StaticMines.getParent().getLogger().log(Level.INFO, "Tried to load a mine with an invalid location! Skipping it... Mine: " + mineID);
            return;
        }

        StaticMine mine = new StaticMine(mineID, new Location(Bukkit.getWorld(mineConfig.getString("location.point1.world")),
                mineConfig.getDouble("location.point1.x"),
                mineConfig.getDouble("location.point1.y"),
                mineConfig.getDouble("location.point1.z"), 0, 0),
                new Location(Bukkit.getWorld(mineConfig.getString("location.point2.world")),
                mineConfig.getDouble("location.point2.x"),
                mineConfig.getDouble("location.point2.y"),
                mineConfig.getDouble("location.point2.z"), 0, 0));
        mine.setShouldRefillSync(mineConfig.getBoolean("shouldRefillSync"));
        List<MineBlock> mineBlocks = new ArrayList<>();
        for (String key : mineConfig.getConfigurationSection("blocks").getKeys(false)) {
            String _key = key.toLowerCase();
            if (BlockTypes.get("minecraft:" + _key) == null) {
                StaticMines.getParent().getLogger().warning("Tried to load a mine with an invalid block! Skipping it... Mine: " + mineID + ", Invalid block: " + key);
                continue;
            }
            mineBlocks.add(new MineBlock(BlockTypes.get("minecraft:" + _key), mineConfig.getDouble("blocks." + key)));
        }
        mine.setMineBlocks(mineBlocks.toArray(new MineBlock[0]));
        mine.refill();
        mine.setShouldSaveToFile(true);
    }


    public static void saveMines() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticMines.getParent(), StaticMine::saveMinesSync);
    }

    public static void saveMinesSync() {
        File minesConfigFile = new File(StaticMines.getInstance().getDataFolder(), "mines.yml");
        FileConfiguration minesConfig = new YamlConfiguration();
        for (String id : SORTED_MINE_IDS) {
            if (!ALL_MINES.get(id).shouldSaveToFile) continue;
            ConfigurationSection section = minesConfig.createSection(id);
            StaticMine mine = ALL_MINES.get(id);
            section.set("shouldRefillSync", mine.getShouldRefillSync());
            ConfigurationSection blocks = section.createSection("blocks");
            for (MineBlock mineBlock : mine.getMineBlocks()) blocks.set(mineBlock.getBlockType().getId().split("minecraft:")[1], mineBlock.getChance());
            ConfigurationSection point1 = section.createSection("location.point1");
            point1.set("world", mine.getWorld().getName());
            point1.set("x", mine.getMinPoint().getBlockX());
            point1.set("y", mine.getMinPoint().getBlockY());
            point1.set("z", mine.getMinPoint().getBlockZ());
            ConfigurationSection point2 = section.createSection("location.point2");
            point2.set("world", mine.getWorld().getName());
            point2.set("x", mine.getMaxPoint().getBlockX());
            point2.set("y", mine.getMaxPoint().getBlockY());
            point2.set("z", mine.getMaxPoint().getBlockZ());
        }


        try {
            minesConfig.save(minesConfigFile);
        } catch (IOException e) {
            StaticMines.getParent().getLogger().warning("Could not save mine data");
            e.printStackTrace();
        }
    }

    //Util methods
    static Location getMinPoint(Location loc1, Location loc2) {
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());
        return new Location(loc1.getWorld(), minX, minY, minZ);
    }

    static Location getMaxPoint(Location loc1, Location loc2) {
        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());
        return new Location(loc1.getWorld(), maxX, maxY, maxZ);
    }

    public static record MineBlock(BlockType blockType, double chance) {

        public BlockType getBlockType() {
            return blockType;
        }

        public double getChance() {
            return chance;
        }


        public static RandomPattern buildRandomPattern(MineBlock[] mineBlocks) {
            RandomPattern randomPattern = new RandomPattern();
            for (MineBlock mineBlock : mineBlocks) {
                randomPattern.add(mineBlock.getBlockType(), mineBlock.getChance());
            }
            return randomPattern;
        }
    }

}

