package net.staticstudios.prisons.UI.tablist;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TeamPrefix {

    private static YamlConfiguration config;

    private static final Map<String, Component> teamPrefix = new LinkedHashMap<>();

    public static void init() {
        loadConfig();
    }

    private static void loadConfig() {
        config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder() + "/teamPrefix.yml"));

        if (!config.contains("teamPrefix")) {
            config.createSection("teamPrefix");
        }

        MiniMessage miniMessage = MiniMessage.miniMessage();

        ConfigurationSection root = config.getConfigurationSection("teamPrefix");

        for (String tag : Objects.requireNonNull(root).getKeys(false)) {
            teamPrefix.put(tag, miniMessage.deserialize(root.getString(tag)));
        }
    }

    public static Component getFromID(String id) {
        return teamPrefix.getOrDefault(id, Component.empty());
    }
}
