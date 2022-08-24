package net.staticstudios.prisons.ui.tablist;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TeamPrefix {

    private static final Map<String, String> teamPrefix = new LinkedHashMap<>();

    public static void init() {
        loadConfig();
    }

    private static final MiniMessage deserializer = MiniMessage.builder()
            .preProcessor(s -> s.replace("<phase>", "0"))
            .tags(TagResolver.resolver(TagResolver.standard()))
            .build();

    private static void loadConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder() + "/teamPrefix.yml"));

        if (!config.contains("teamPrefix")) {
            config.createSection("teamPrefix");
        }

        ConfigurationSection root = config.getConfigurationSection("teamPrefix");

        for (String tag : Objects.requireNonNull(root).getKeys(false)) {
            teamPrefix.put(tag, root.getString(tag));
        }
    }

    public static Component getFromIdDeserialized(String id) {
        return deserializer.deserialize(getFromId(id));
    }

    public static String getFromId(String id) {
        return teamPrefix.getOrDefault(id, "");
    }

    public static Map<String, String> getTeamPrefix() {
        return new LinkedHashMap<>(teamPrefix);
    }

    public static Map<String, Component> getTeamPrefixAsComponents() {
        return teamPrefix.entrySet().stream()
                .collect(LinkedHashMap::new,
                        (stringComponentLinkedHashMap, stringComponentMap) -> stringComponentLinkedHashMap.put(stringComponentMap.getKey(), deserializer.deserialize(stringComponentMap.getValue())),
                        Map::putAll);
    }
}
