package me.staticstudios.prisons.misc.chat;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class ChatTags {
    public static final Map<String, String> chatTags = new HashMap<>();
    public static final String WEEWOO = ChatColor.translateAlternateColorCodes('&', "&8[&x&0&0&0&0&f&fW&x&3&3&0&0&c&ce&x&6&6&0&0&9&9e&x&9&9&0&0&6&6W&x&c&c&0&0&3&3o&x&f&f&0&0&0&0o&8] ");
    public static final String MR_YUH = ChatColor.translateAlternateColorCodes('&', "&8[&x&f&b&2&4&2&4M&x&c&b&2&d&6&cr&x&9&c&3&7&b&5.&x&6&c&4&0&f&dY&x&5&a&7&d&f&9u&x&4&7&b&b&f&6h&x&3&5&f&8&f&2h&8] ");
    public static final String LGBTQ = ChatColor.translateAlternateColorCodes('&', "&8[&cL&6G&eB&aT&9Q&5+&8] ");
    public static final String NO_SLEEP = ChatColor.translateAlternateColorCodes('&', "&8[&bWhat's Sleep?&8] ");
    public static final String _24_7 = ChatColor.translateAlternateColorCodes('&', "&8[&x&f&b&3&d&f&92&x&b&a&7&3&f&a4&x&7&a&a&a&f&c/&x&3&9&e&0&f&d7&8] ");
    public static final String TOXIC = ChatColor.translateAlternateColorCodes('&', "&8[&2&lToxic&8] ");
    public static final String YOUTUBE = ChatColor.translateAlternateColorCodes('&', "&8[&c&lYou&f&lTube&8] ");
    public static final String TWITCH = ChatColor.DARK_GRAY + "[" + ChatColor.of("#9146FF")  + ChatColor.BOLD + "Twitch" + ChatColor.DARK_GRAY + "] ";
    public static final String STREAMER = ChatColor.translateAlternateColorCodes('&', "&8[&dStreamer&8] ");
    public static final String BUILDER = ChatColor.translateAlternateColorCodes('&', "&8[&b&lBuilder&8] ");
    public static final String DEV = ChatColor.translateAlternateColorCodes('&', "&8[&b&lDev&8] ");
    static {
        chatTags.put("weewoo", WEEWOO);
        chatTags.put("mr.yuh", MR_YUH);
        chatTags.put("no_sleep", NO_SLEEP);
        chatTags.put("lgbtq", LGBTQ);
        chatTags.put("24/7", _24_7);
        chatTags.put("toxic", TOXIC);
        chatTags.put("youtube", YOUTUBE);
        chatTags.put("twitch", TWITCH);
        chatTags.put("streamer", STREAMER);
        chatTags.put("builder", BUILDER);
        chatTags.put("dev", DEV);
    }

    public static String getChatTagFromID(String id) {
        if (chatTags.containsKey(id)) return chatTags.get(id);
        return "";
    }
}
