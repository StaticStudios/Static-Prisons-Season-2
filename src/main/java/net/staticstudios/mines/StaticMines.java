package net.staticstudios.mines;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.staticstudios.mines.api.events.MineCreatedEvent;
import net.staticstudios.mines.utils.WeightedElement;
import net.staticstudios.mines.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public final class StaticMines implements Listener {

    private static final StaticMinesListener eventListener = new StaticMinesListener();

    private static JavaPlugin parent;
    public static JavaPlugin getParent() {
        return parent;
    }

    static final Map<String, StaticMine> MINES = new HashMap<>();
    static final Map<World, Set<StaticMine>> WORLD_MINES = new HashMap<>();
    static final LinkedList<StaticMine> ORDERED_MINES = new LinkedList<>();


    private static String commandLabel;
    private static boolean usingCommand = false;

    private static BukkitTask refillTimerTask;

    /**
     * Register a mine.
     * @param mine The mine to register.
     */
    static void registerMine(StaticMine mine) {
        MINES.put(mine.getId(), mine);
        ORDERED_MINES.add(mine);
        Set<StaticMine> mines = WORLD_MINES.getOrDefault(mine.getBukkitWorld(), new HashSet<>());
        mines.add(mine);
        WORLD_MINES.put(mine.getBukkitWorld(), mines);

        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(mine.getWorld());
        assert regionManager != null;
        regionManager.addRegion(mine.getProtectedMineRegion());

        new MineCreatedEvent(mine).callEvent();
    }

    /**
     * Unregister a mine.
     * @param mine The mine to unregister.
     */
    static void unregisterMine(StaticMine mine) {
        MINES.remove(mine.getId());
        ORDERED_MINES.remove(mine);
        Set<StaticMine> mines = WORLD_MINES.getOrDefault(mine.getBukkitWorld(), new HashSet<>());
        mines.remove(mine);
        WORLD_MINES.put(mine.getBukkitWorld(), mines);
    }



    /**
     * Initialize the StaticMines API, this method should be called before creating any mines.
     *
     * @param parent Your plugin instance.
     * @return A completable future that will be completed when init tasks have been completed. Some of these tasks are async, so this could take some time.
     */
    public static CompletableFuture<Void> enable(JavaPlugin parent) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        StaticMines.parent = parent;

        if (!StaticMinesConfig.init()) {
            log("Encountered an error while loading StaticMines!");
            disable();
            return CompletableFuture.completedFuture(null);
        }
        StaticMinesThreadManager.init();

        onEnable();
        StaticMines.log("Successfully enabled Static-Mines");
        return future;
    }

    /**
     * Method responsible for enabling StaticMines after it has passed all checks.
     */
    private static void onEnable() {
        loadMines();
        parent.getServer().getPluginManager().registerEvents(eventListener, StaticMines.parent);
        commandLabel = StaticMinesConfig.getConfig().getString("command_label");
        usingCommand = parent.getCommand(commandLabel) != null;
        if (!usingCommand) {
            StaticMines.log("There is no command registered for StaticMines... consider registering one in your plugin's main class with the command label: '" + commandLabel + "' (specified in the StaticMines config.yml). Adding this command will allow for more features to be used in-game however it is not required.");
        } else {
            parent.getCommand(commandLabel).setExecutor(new StaticMinesCommand());
            parent.getCommand(commandLabel).setTabCompleter(new StaticMinesCommand());
        }

        refillTimerTask = Bukkit.getScheduler().runTaskTimer(parent, () -> {
            for (StaticMine mine : MINES.values()) {
                if (!mine.settings().refillOnTimer()) continue;
                if (mine.getCurrentBlockCount() == mine.getBlockCountWhenFull()) {
                    mine.setLastRefilledAt(System.currentTimeMillis());
                    continue;
                }
                if (mine.getLastRefilledAt() + mine.settings().secondsBetweenRefill() * 1000L <= System.currentTimeMillis()) {
                    mine.refill();
                }
            }
        }, 0, 5);
    }

    /**
     * Disable the StaticMines API, this method should be called when your plugin is disabled.
     * This will stop listening/handling events, unregister all commands and run a few clean-up tasks.
     */
    public static void disable() {
        log("Successfully disabled Static-Mines");
        StaticMinesThreadManager.shutdown();
        saveMines();
        cleanUp();
        if (usingCommand && parent.getCommand(commandLabel) != null) {
            parent.getCommand(commandLabel).setExecutor(null);
            parent.getCommand(commandLabel).setTabCompleter(null);
        }
        parent = null;

        MINES.clear();
        WORLD_MINES.clear();
        ORDERED_MINES.clear();

        HandlerList.unregisterAll(eventListener);
    }

    /**
     * Log a message with the StaticMines prefix.
     * @param str The message to log.
     */
    public static void log(String str) {
        if (parent != null) {
            parent.getLogger().info("[Static-Mines] " + str);
        } else {
            Bukkit.getLogger().info("[Static-Mines] " + str);
        }
    }

    /**
     * Load all mines synchronously from the mines.yml.
     */
    public static void loadMines() {
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(Objects.requireNonNull(getMinesFile()));
        for (String mineID : fileData.getKeys(false)) {
            ConfigurationSection mineData = fileData.getConfigurationSection(mineID);
            if (mineData == null) {
                log("Mine '" + mineID + "' is missing data, skipping it...");
                continue;
            }
            String worldName = mineData.getString("world");
            if (worldName == null) {
                log("Mine '" + mineID + "' is missing a world, skipping it...");
                continue;
            }
            BlockVector3 corner1 = BlockVector3.at(mineData.getInt("point1.x"), mineData.getInt("point1.y"), mineData.getInt("point1.z"));
            BlockVector3 corner2 = BlockVector3.at(mineData.getInt("point2.x"), mineData.getInt("point2.y"), mineData.getInt("point2.z"));

            ConfigurationSection blockData = mineData.getConfigurationSection("blocks");
            if (blockData == null) {
                log("Mine '" + mineID + "' is missing block data, skipping it...");
                continue;
            }
            WeightedElements<BlockType> blocks = new WeightedElements<>();
            for (String blockType : blockData.getKeys(false)) {
                double weight = blockData.getDouble(blockType);
                BlockType type = BlockTypes.get("minecraft:" + blockType);
                blocks.add(WeightedElements.of(type, weight));
            }

            log("Loading mine '" + mineID + "'...");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                log("Mine '" + mineID + "' is in a world that does not exist, loading world '" + worldName + "'...");
                new WorldCreator(worldName).createWorld();
            }
            StaticMine mine = new StaticMine(
                    mineID, Bukkit.getWorld(worldName),
                    corner1, corner2,
                    blocks
            );

            ConfigurationSection settingsData = mineData.getConfigurationSection("settings");
            if (settingsData == null) {
                settingsData = new YamlConfiguration();
            }

            mine.settings().saveToFile(true);
            mine.settings().async(settingsData.getBoolean("async", true));
            mine.settings().refillOnTimer(settingsData.getBoolean("refill_on_timer", true));
            mine.settings().refillAtPercentFull(settingsData.getInt("refill_at_percent_full", 50));
            mine.settings().secondsBetweenRefill(settingsData.getInt("seconds_between_refills", 600));
            mine.refill();
        }
    }

    /**
     * Save all mine data to a file, a mine will only be saved if its "saveToFile" setting is true.
     */
    public static void saveMines() {
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(Objects.requireNonNull(getMinesFile()));
        for (StaticMine mine : MINES.values()) {
            ConfigurationSection mineData = fileData.getConfigurationSection(mine.getId());
            if (mineData != null && !mine.settings().saveToFile()) { //The mine is currently saved to file, but the config says it shouldn't be saved to file anymore.
                fileData.set(mine.getId(), null);
                continue;
            } else if (mineData == null && mine.settings().saveToFile()) { //The mine is not currently saved to file, but the config says it should be saved to file.
                mineData = fileData.createSection(mine.getId());
            }
            assert mineData != null;
            putIfAbsent(mineData, "world", mine.getWorld().getName());
            putIfAbsent(mineData, "point1.x", mine.getMinPoint().getBlockX());
            putIfAbsent(mineData, "point1.y", mine.getMinPoint().getBlockY());
            putIfAbsent(mineData, "point1.z", mine.getMinPoint().getBlockZ());
            putIfAbsent(mineData, "point2.x", mine.getMaxPoint().getBlockX());
            putIfAbsent(mineData, "point2.y", mine.getMaxPoint().getBlockY());
            putIfAbsent(mineData, "point2.z", mine.getMaxPoint().getBlockZ());

            ConfigurationSection blockData = mineData.createSection("blocks");
            for (WeightedElement<BlockType> block : mine.getBlocks().getElements()) {
                putIfAbsent(blockData, block.getElement().getId().replace("minecraft:", ""), block.getWeight());
            }

            ConfigurationSection settingData = mineData.getConfigurationSection("settings");
            if (settingData == null) {
                settingData = mineData.createSection("settings");
            }
            putIfAbsent(settingData, "async", mine.settings().async());
            putIfAbsent(settingData, "refill_on_timer", mine.settings().refillOnTimer());
            putIfAbsent(settingData, "refill_at_percent_full", mine.settings().refillAtPercentFull());
            putIfAbsent(settingData, "seconds_between_refills", mine.settings().secondsBetweenRefill());
        }

        try {
            fileData.save(getMinesFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void putIfAbsent(ConfigurationSection section, String path, Object value) {
        if (!section.contains(path)) {
            section.set(path, value);
        }
    }

    static File getMinesFile() {
        File minesFile = new File(parent.getDataFolder(), "/Static-Mines/mines.yml");
        if (!minesFile.exists()) {
            StaticMines.log("No mines.yml found, creating one...");
            minesFile.getParentFile().mkdirs();
            try {
                minesFile.createNewFile();
            } catch (Exception e) {
                StaticMines.log("Failed to create mines.yml!");
                StaticMines.log(e.getMessage());
                return null;
            }
        }
        return minesFile;
    }





    /**
     * Cleans up anything left behind by StaticMines. This will be called when StaticMines is disabled.
     */
    static void cleanUp() {
        //Stop the refill timer task
        if (refillTimerTask != null) {
            refillTimerTask.cancel();
            refillTimerTask = null;
        }

        //Get rid of all world guard regions
        RegionContainer regions = WorldGuard.getInstance().getPlatform().getRegionContainer();
        if (regions != null) {
            for (World word : WORLD_MINES.keySet()) {
                com.sk89q.worldedit.world.World wgWorld = BukkitAdapter.adapt(word);
                if (wgWorld == null) continue;
                RegionManager regionManager = regions.get(wgWorld);
                if (regionManager == null) continue;
                for (String rgID : regionManager.getRegions().keySet()) {
                    if (rgID.endsWith("--static-mine")) {
                        regionManager.removeRegion(rgID);
                    }
                }
            }
        }
    }

    /**
     * Get a mine from its location
     * @param yAxis Whether the Y-Axis should be factored in, otherwise it will return the first mine containing the location's X and Z coordinates.
     * @return A StaticMine instance that contains that location. Null if no mine contains that location.
     */
    @Nullable
    public static StaticMine fromLocation(Location location, boolean yAxis) {
        if (location == null) return null;
        Set<StaticMine> mines = WORLD_MINES.get(location.getWorld());
        if (mines == null) return null;
        for (StaticMine mine : mines) {
            if (yAxis && mine.getRegion().contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                return mine;
            } else if (mine.getRegion().contains(location.getBlockX(), location.getBlockZ())) {
                return mine;
            }
        }
        return null;
    }
}
