package net.staticstudios.prisons.chat.tags;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.chat.tags.commands.AddAllPlayerChatTagsCommand;
import net.staticstudios.prisons.chat.tags.commands.AddPlayerChatTagCommand;
import net.staticstudios.prisons.chat.tags.commands.ChatTagsCommand;
import net.staticstudios.prisons.chat.tags.commands.RemovePlayerChatTagCommand;
import net.staticstudios.prisons.commands.CommandManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ChatTags {

    private static final Map<String, Component> chatTags = new LinkedHashMap<>();

    public static void init() {
        CommandManager.registerCommand("addallchattags", new AddAllPlayerChatTagsCommand());
        CommandManager.registerCommand("addchattag", new AddPlayerChatTagCommand());
        CommandManager.registerCommand("removechattag", new RemovePlayerChatTagCommand());
        CommandManager.registerCommand("chattags", new ChatTagsCommand());

        loadConfig();
    }

    private static void loadConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder() + "/chatTags.yml"));

        if (!config.contains("chatTags")) {
            config.createSection("chatTags");
        }


        ConfigurationSection root = config.getConfigurationSection("chatTags");

        for (String tag : Objects.requireNonNull(root).getKeys(false)) {
            chatTags.put(tag, StaticPrisons.miniMessage().deserialize(root.getString(tag)));
        }
    }


    public static Component getFromID(String id) {
        return chatTags.getOrDefault(id, Component.empty());
    }

    public static Set<String> getAllKeys() {
        return chatTags.keySet();
    }
}
