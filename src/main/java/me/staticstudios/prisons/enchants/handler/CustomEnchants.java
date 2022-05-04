package me.staticstudios.prisons.enchants.handler;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class CustomEnchants {

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
        PrisonPickaxe.updateCachedStats(item);
    }

    /**
     *This method should only be called if an enchant is being changed by code, not by a player. This will not update a pickaxe's cached stats
     */

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
        for (String enchant : PrisonEnchants.enchantIDsToNames.keySet()) {
            if (getEnchantLevel(item, enchant) > 0) {
                itemLore.add(PrisonEnchants.enchantIDsToNames.get(enchant) + ": " + Utils.addCommasToNumber(getEnchantLevel(item, enchant)));
                loreAdded.add(PrisonEnchants.enchantIDsToNames.get(enchant) + ": " + Utils.addCommasToNumber(getEnchantLevel(item, enchant)));
            }
        }

        StringBuilder customEnchantLore = new StringBuilder();
        for (int i = 0; i < loreAdded.size(); i++) { //TODO: change the way that this is stored to use an array that uses an ID to point to the old display name in the event that a display name is changed; to avoid duplicates
            customEnchantLore.append(loreAdded.get(i));
            if (i + 1 != customEnchantLore.length()) customEnchantLore.append("$+$+$");
        }
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "customEnchantLore"), PersistentDataType.STRING, customEnchantLore.toString());
        meta.setLore(itemLore);
        item.setItemMeta(meta);
    }
}
