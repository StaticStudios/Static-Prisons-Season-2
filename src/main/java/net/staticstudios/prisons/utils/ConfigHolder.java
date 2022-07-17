package net.staticstudios.prisons.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class ConfigHolder { //todo

    private final FileConfiguration config;
    public FileConfiguration getConfig() { return config; }

    public ConfigHolder(File file) {
        config = YamlConfiguration.loadConfiguration(file);
    }

}
