package net.staticstudios.prisons.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ChatTags {

    private static final Map<String, Component> chatTags = new LinkedHashMap<>();

    public static void init() {
        loadConfig();
    }

    private static void loadConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder() + "/chatTags.yml"));

        if (!config.contains("chatTags")) {
            config.createSection("chatTags");
        }

        MiniMessage miniMessage = MiniMessage.miniMessage();

        ConfigurationSection root = config.getConfigurationSection("chatTags");

        for (String tag : Objects.requireNonNull(root).getKeys(false)) {
            chatTags.put(tag, miniMessage.deserialize(root.getString(tag)));
        }
    }


    public static Component getFromID(String id) {
        return chatTags.getOrDefault(id, Component.empty());
    }

    public static Set<String> getAllKeys() {
        return chatTags.keySet();
    }
}
