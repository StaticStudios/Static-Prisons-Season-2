package net.staticstudios.mines;

import com.sk89q.worldedit.math.BlockVector3;
import net.staticstudios.mines.minesapi.events.BlockBrokenInMineEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class StaticMines implements Listener {

    private static JavaPlugin parent;
    private static StaticMines instance;
    public static boolean isEnabled() { return instance == null; }
    private static Runnable runAfterInitialLoad = () -> {};

    public static void enable(JavaPlugin parent) {
        enable(parent, () -> {});
    }
    public static void enable(JavaPlugin parent, Runnable runAfterInitialLoad) {
        StaticMines.parent = parent;
        instance = new StaticMines();
        instance.dataFolder = new File(parent.getDataFolder() + "/Static-Mines");
        if (!instance.dataFolder.exists()) instance.dataFolder.mkdirs();
        instance.configFile = new File(instance.getDataFolder() + "/config.yml");
        try {
            instance.configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            disable();
            return;
        }
        instance.reloadConfig();
        if (!instance.config.contains("command_label")) instance.config.set("command_label", DEFAULT_COMMAND_LABEL);
        instance.saveConfig();
        instance.onEnable();
        Bukkit.getLogger().info("Successfully enabled Static-Mines");
    }
    public static void disable() {
        StaticMines.parent = null;
        StaticMines.instance = null;
        Bukkit.getLogger().info("Successfully disabled Static-Mines");
    }

    public static JavaPlugin getParent() { return parent; }
    public static StaticMines getInstance() { return StaticMines.instance; }

    private static final String DEFAULT_COMMAND_LABEL = "static-mines";
    private String commandLabel;
    private File configFile = null;
    private FileConfiguration config = null;
    private File dataFolder = null;

    public void onEnable() {
        StaticMine.loadMines();
        parent.getServer().getPluginManager().registerEvents(this, StaticMines.parent);
        commandLabel = config.getString("command_label");
        if (parent.getCommand(commandLabel) == null) {
            getParent().getLogger().warning("There is no command registered for StaticMines... consider registering one in your plugin's main class with the command label: '" + commandLabel + "' (specified in the StaticMines config.yml). Adding this command will allow for more features to be used in-game however it is not required.");
        } else {
            parent.getCommand(commandLabel).setExecutor(new StaticMinesCommand());
            parent.getCommand(commandLabel).setTabCompleter(new StaticMinesCommand());
        }
        Bukkit.getScheduler().runTaskTimer(parent, StaticMine::refillAllTimedMines, 0, 1);
    }

    public void onDisable() {
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            parent.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) reloadConfig();
        return config;
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }


    public static void setRunAfterInitialLoad(Runnable runAfterInitialLoad) {
        StaticMines.runAfterInitialLoad = runAfterInitialLoad;
    }
    public static Runnable getRunAfterInitialLoad() {
        return runAfterInitialLoad;
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent e) {
        Location blockLoc = e.getBlock().getLocation();
        for (StaticMine mine : StaticMine.getAllMines()) {
            BlockVector3 min = mine.getMinVector();
            BlockVector3 max = mine.getMaxVector();
            if (blockLoc.getX() < min.getX() || blockLoc.getY() < min.getY() || blockLoc.getZ() < min.getZ()) continue;
            if (blockLoc.getX() > max.getX() || blockLoc.getY() > max.getY() || blockLoc.getZ() > max.getZ()) continue;
            Bukkit.getPluginManager().callEvent(new BlockBrokenInMineEvent(e, mine.getID()));
            return;
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onBlockBroken(BlockBrokenInMineEvent e) {
        e.getMine().removeBlocksBrokenInMine(1);
    }
}
