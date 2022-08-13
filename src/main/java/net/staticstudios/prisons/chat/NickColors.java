package net.staticstudios.prisons.chat;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class NickColors {

    private static final Map<String, TextColor> nickColors = new HashMap<>();

    public static void init() {
        loadConfig();
    }

    private static void loadConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder() + "/nickColors.yml"));

        if (!config.contains("nickColors")) {
            config.createSection("nickColors");
        }

        ConfigurationSection root = config.getConfigurationSection("nickColors");

        assert root != null;

        for (String key : root.getKeys(false)) {
            nickColors.put(key, TextColor.fromHexString(Objects.requireNonNull(root.getString(key))));
        }
    }

    public static TextColor getColor(String id) {
        return nickColors.getOrDefault(id, NamedTextColor.WHITE);
    }

    public static Set<String> getKeySet() {
        return nickColors.keySet();
    }

}
