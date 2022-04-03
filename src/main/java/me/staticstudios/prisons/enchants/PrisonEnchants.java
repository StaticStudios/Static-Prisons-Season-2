package me.staticstudios.prisons.enchants;

import org.bukkit.ChatColor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PrisonEnchants {
    public static PrisonEnchant FORTUNE;
    public static PrisonEnchant ORE_SPLITTER;
    public static PrisonEnchant TOKENATOR;
    public static PrisonEnchant KEY_FINDER;
    public static PrisonEnchant METAL_DETECTOR;
    public static PrisonEnchant EXPLOSION;
    public static PrisonEnchant JACK_HAMMER;
    public static PrisonEnchant DOUBLE_WAMMY;
    public static PrisonEnchant MULTI_DIRECTIONAL;
    public static PrisonEnchant MERCHANT;
    public static PrisonEnchant CONSISTENCY;
    public static PrisonEnchant HASTE;
    public static PrisonEnchant SPEED;
    public static PrisonEnchant NIGHT_VISION;

    public static final LinkedHashMap<String, String> enchantIDsToNames = new LinkedHashMap<>();
    public static void initialize() {
        enchantIDsToNames.put(FORTUNE.ENCHANT_ID, ChatColor.AQUA + FORTUNE.DISPLAY_NAME);
        enchantIDsToNames.put(ORE_SPLITTER.ENCHANT_ID, ChatColor.AQUA + ORE_SPLITTER.DISPLAY_NAME);
        enchantIDsToNames.put(TOKENATOR.ENCHANT_ID, ChatColor.AQUA + TOKENATOR.DISPLAY_NAME);
        enchantIDsToNames.put(KEY_FINDER.ENCHANT_ID, ChatColor.AQUA + KEY_FINDER.DISPLAY_NAME);
        enchantIDsToNames.put(METAL_DETECTOR.ENCHANT_ID, ChatColor.AQUA + METAL_DETECTOR.DISPLAY_NAME);
        enchantIDsToNames.put(EXPLOSION.ENCHANT_ID, ChatColor.AQUA + EXPLOSION.DISPLAY_NAME);
        enchantIDsToNames.put(JACK_HAMMER.ENCHANT_ID, ChatColor.AQUA + JACK_HAMMER.DISPLAY_NAME);
        enchantIDsToNames.put(DOUBLE_WAMMY.ENCHANT_ID, ChatColor.AQUA + DOUBLE_WAMMY.DISPLAY_NAME);
        enchantIDsToNames.put(MULTI_DIRECTIONAL.ENCHANT_ID, ChatColor.AQUA + MULTI_DIRECTIONAL.DISPLAY_NAME);
        enchantIDsToNames.put(CONSISTENCY.ENCHANT_ID, ChatColor.AQUA + CONSISTENCY.DISPLAY_NAME);
        enchantIDsToNames.put(HASTE.ENCHANT_ID, ChatColor.AQUA + HASTE.DISPLAY_NAME);
        enchantIDsToNames.put(SPEED.ENCHANT_ID, ChatColor.AQUA + SPEED.DISPLAY_NAME);
        enchantIDsToNames.put(NIGHT_VISION.ENCHANT_ID, ChatColor.AQUA + NIGHT_VISION.DISPLAY_NAME);
    }
    public static Map<UUID, String> playerUUIDToPickaxeID = new HashMap<>();

}
