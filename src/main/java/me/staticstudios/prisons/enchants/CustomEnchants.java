package me.staticstudios.prisons.enchants;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CustomEnchants {

    public static final LinkedHashMap<String, String> enchantIDsToNames = new LinkedHashMap<String, String>();

    static {
        enchantIDsToNames.put("fortune", ChatColor.AQUA + "Fortune");
        enchantIDsToNames.put("tokenator", ChatColor.AQUA + "Tokenator");
        enchantIDsToNames.put("jackHammer", ChatColor.AQUA + "Jack Hammer");
        enchantIDsToNames.put("doubleWammy", ChatColor.AQUA + "Double Wammy");
        enchantIDsToNames.put("multiDirectional", ChatColor.AQUA + "Multi-Directional");
        enchantIDsToNames.put("xpFinder", ChatColor.AQUA + "XP Finder");
        enchantIDsToNames.put("merchant", ChatColor.AQUA + "Merchant");
        enchantIDsToNames.put("keyFinder", ChatColor.AQUA + "Key Finder");
        enchantIDsToNames.put("haste", ChatColor.AQUA + "Haste");
        enchantIDsToNames.put("speed", ChatColor.AQUA + "Speed");
        enchantIDsToNames.put("nightVision", ChatColor.AQUA + "Night Vision");
    }

    public static long getEnchantLevel(ItemStack item, String enchant) {
        if (!Utils.checkIsPrisonPickaxe(item)) return 0;
        if (!item.hasItemMeta()) return 0;
        if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), enchant), PersistentDataType.LONG)) {
            return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), enchant), PersistentDataType.LONG);
        }
        return 0;
    }

    public static void setEnchantLevel(ItemStack item, String enchant, long level) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), enchant), PersistentDataType.LONG, level);
        item.setItemMeta(meta);
        updateLore(item);
    }

    public static void addEnchantLevel(ItemStack item, String enchant, long levelsToAdd) {
        setEnchantLevel(item, enchant, getEnchantLevel(item, enchant) + levelsToAdd);
    }

    public static void removeEnchantLevels(ItemStack item, String enchant, long levelsToRemove) {
        setEnchantLevel(item, enchant, getEnchantLevel(item, enchant) - levelsToRemove);
    }
    public static void removeEnchant(ItemStack item, String enchant) {
        setEnchantLevel(item, enchant, 0);
        updateLore(item);
    }

    public static void updateLore(ItemStack item) {
        List<String> itemLore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            itemLore = item.getItemMeta().getLore();
        }
        if (meta.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "customEnchantLore"), PersistentDataType.STRING)) {
            String[] enchantLore = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "customEnchantLore"), PersistentDataType.STRING).split("\\$\\+\\$\\+\\$");
            for (String lore : enchantLore) {
                itemLore.remove(lore);
            }
        }
        List<String> loreAdded = new ArrayList<>();
        for (String enchant : enchantIDsToNames.keySet()) {
            if (getEnchantLevel(item, enchant) > 0) {
                itemLore.add(enchantIDsToNames.get(enchant) + ": " + Utils.addCommasToLong(getEnchantLevel(item, enchant)));
                loreAdded.add(enchantIDsToNames.get(enchant) + ": " + Utils.addCommasToLong(getEnchantLevel(item, enchant)));
            }
        }

        StringBuilder customEnchantLore = new StringBuilder();
        for (int i = 0; i < loreAdded.size(); i++) {
            customEnchantLore.append(loreAdded.get(i));
            if (i + 1 != customEnchantLore.length()) customEnchantLore.append("$+$+$");
        }
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "customEnchantLore"), PersistentDataType.STRING, customEnchantLore.toString());
        meta.setLore(itemLore);
        item.setItemMeta(meta);
    }
}
