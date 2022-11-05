package net.staticstudios.prisons.utils;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

public class StaticFileSystemManager {

    private static final Logger LOGGER = Logger.getLogger("StaticFileSystemManager");

    private static final StaticPrisons INSTANCE = StaticPrisons.getInstance();


    /**
     * @return The default config.
     */
    public FileConfiguration getDefaultConfig() {
        return INSTANCE.getConfig();
    }

    /**
     * Returns a potential file from the plugin's data folder.
     * Can also be in subfolders.
     *
     * @param pathFromDataFolder The path from the data folder. eg: "config.yml" or "subfolder/config.json"
     * @return The file if found or empty if not.
     */
    public static Optional<File> getFile(String pathFromDataFolder) {

        File file = new File(INSTANCE.getDataFolder(), pathFromDataFolder);

        return Optional.ofNullable(file.exists() ? file : null);
    }

    /**
     * Returns a File from the plugin's data folder. Creates it if it doesn't exist.
     *
     * @param pathFromDataFolder The path from the data folder. eg: "config.yml" or "subfolder/config.yml"
     * @return The file.
     */
    public static File getFileOrCreate(String pathFromDataFolder) {
        File file = new File(INSTANCE.getDataFolder(), pathFromDataFolder);

        try {
            if (file.createNewFile()) {
                LOGGER.info("Created file " + file.getName());
            }
        } catch (Exception e) {
            LOGGER.severe("Unable to create file: " + pathFromDataFolder + ": " + e.getMessage());
        }

        return file;
    }

    /**
     * Returns a YamlConfiguration from the plugin's data folder. Creates it if it doesn't exist.
     *
     * @param pathFromDataFolder The path from the data folder. eg: "config.yml" or "subfolder/config.yml"
     * @return The YamlConfiguration
     */
    public static YamlConfiguration getYamlConfiguration(String pathFromDataFolder) {
        return YamlConfiguration.loadConfiguration(getFileOrCreate(pathFromDataFolder));
    }

    /**
     * Returns a YamlConfiguration from the plugin's data folder. Creates it if it doesn't exist.
     *
     * @param pathFromDataFolder The path from the data folder. eg: "config.yml" or "subfolder/config.yml"
     * @param sectionPath        the path to the section to check if exists. eg: "section.subsection"
     * @return The YamlConfiguration.
     */
    public static YamlConfiguration getYamlConfiguration(String pathFromDataFolder, String sectionPath) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(getFileOrCreate(pathFromDataFolder));

        if (!configuration.contains(sectionPath)) {
            configuration.createSection(sectionPath);
        }

        return configuration;
    }
}
