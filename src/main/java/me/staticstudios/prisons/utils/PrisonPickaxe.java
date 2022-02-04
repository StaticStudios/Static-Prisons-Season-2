package me.staticstudios.prisons.utils;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.enchants.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class PrisonPickaxe {
    public static void addLevel(ItemStack pickaxe, long levelsToAdd) {
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return;
        ItemMeta meta = pickaxe.getItemMeta();
        long currentAmount = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.LONG)) {
            currentAmount = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.LONG);
        }
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.LONG, currentAmount + levelsToAdd);
        boolean updated = false;
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (ChatColor.stripColor(line).startsWith("Level:")) {
                    lore.set(i, ChatColor.GREEN + "Level: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + levelsToAdd));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lore.add(ChatColor.GREEN + "Level: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + levelsToAdd));
            }
        } else {
            lore.add(ChatColor.GREEN + "Level: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + levelsToAdd));
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }

    public static void addXP(ItemStack pickaxe, long xpToAdd) {
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return;
        ItemMeta meta = pickaxe.getItemMeta();
        long currentAmount = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "xp"), PersistentDataType.LONG)) {
            currentAmount = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "xp"), PersistentDataType.LONG);
        }
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "xp"), PersistentDataType.LONG, currentAmount + xpToAdd);
        boolean updated = false;
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (ChatColor.stripColor(line).startsWith("Experience:")) {
                    lore.set(i, ChatColor.GREEN + "Experience: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + xpToAdd));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lore.add(ChatColor.GREEN + "Experience: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + xpToAdd));
            }
        } else {
            lore.add(ChatColor.GREEN + "Experience: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + xpToAdd));
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }

    public static void addBlocksMined(ItemStack pickaxe, long amountToAdd) {
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return;
        ItemMeta meta = pickaxe.getItemMeta();
        long currentAmount = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "blocksBroken"), PersistentDataType.LONG)) {
            currentAmount = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "blocksBroken"), PersistentDataType.LONG);
        }
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "blocksBroken"), PersistentDataType.LONG, currentAmount + amountToAdd);
        boolean updated = false;
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (ChatColor.stripColor(line).startsWith("Blocks Broken:")) {
                    lore.set(i, ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + amountToAdd));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lore.add(ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + amountToAdd));
            }
        } else {
            lore.add(ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + Utils.addCommasToLong(currentAmount + amountToAdd));
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }
}
