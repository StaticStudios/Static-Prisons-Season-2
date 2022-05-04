package me.staticstudios.prisons.enchants.handler;

import me.staticstudios.prisons.enchants.*;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class PrisonEnchants {
    public static BaseEnchant FORTUNE;
    public static BaseEnchant ORE_SPLITTER;
    public static BaseEnchant TOKENATOR;
    public static BaseEnchant KEY_FINDER;
    public static BaseEnchant METAL_DETECTOR;
    public static BaseEnchant EXPLOSION;
    public static BaseEnchant JACK_HAMMER;
    public static BaseEnchant DOUBLE_WAMMY;
    public static BaseEnchant MULTI_DIRECTIONAL;
    public static BaseEnchant MERCHANT;
    public static BaseEnchant CONSISTENCY;
    public static BaseEnchant HASTE;
    public static BaseEnchant SPEED;
    public static BaseEnchant NIGHT_VISION;


    public static Map<String, BaseEnchant> enchantIDToEnchant = new HashMap<>();

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

    public static void createEnchants() {
        FORTUNE = new FortuneEnchant();
        ORE_SPLITTER = new DoubleFortuneEnchant();
        TOKENATOR = new TokenatorEnchant();
        KEY_FINDER = new KeyFinderEnchant();
        METAL_DETECTOR = new SpecialFinderEnchant();
        EXPLOSION = new ExplosionEnchant();
    }


    public static Map<UUID, String> playerUUIDToPickaxeID = new HashMap<>();

}
