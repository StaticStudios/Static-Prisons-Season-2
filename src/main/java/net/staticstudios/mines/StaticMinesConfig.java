package net.staticstudios.mines;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StaticMinesConfig {


    private static final String DEFAULT_COMMAND_LABEL = "static-mines";
    public static final String REGION_SUFFIX = "--static-mine";

    private static File dataFolder = null;
    private static File configFile = null;
    private static FileConfiguration config = null;

    /**
     * @return true if the config was loaded successfully, false otherwise.
     */
    public static boolean init() {
        try {
            dataFolder = new File(StaticMines.getParent().getDataFolder() + "/Static-Mines");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            configFile = new File(dataFolder + "/config.yml");
            configFile.createNewFile();

            //Defaults
            loadConfig();
            addDefaults();
            saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @return The data folder for StaticMines. (yourPluginDataFolder/Static-Mines)
     */
    public static File getDataFolder() {
        return dataFolder;
    }

    /**
     * Save the config to disk.
     */
    public static void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            StaticMines.log("Could not save config to " + configFile);
            ex.printStackTrace();
        }
    }

    /**
     * @return The config file for StaticMines.
     */
    public static FileConfiguration getConfig() {
        if (config == null) loadConfig();
        return config;
    }

    /**
     * Grab the config file from disk and load it into memory.
     */
    public static void loadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }


    static void addDefaults() {
        config.addDefault("command_label", DEFAULT_COMMAND_LABEL);
        config.addDefault("thread_pool.core_size", 3);
        config.addDefault("thread_pool.max_size", 10);
        config.addDefault("thread_pool.keep_alive_time", 30_000); //30 seconds
        config.options().copyDefaults();
    }

}
