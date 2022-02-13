package me.staticstudios.prisons.utils;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.enchants.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PrisonPickaxe {
    public static final int BASE_XP_PER_BLOCK_BROKEN = 2;
    public static final int BASE_XP_PER_PICKAXE_LEVEL = 10000;
    public static final double XP_REQUIREMENT_INCREASE_PERCENTAGE = 0.05;
    public static long getXpRequiredForPickaxeLevel(int level) {
        return BigDecimal.valueOf(BASE_XP_PER_PICKAXE_LEVEL).multiply(BigDecimal.valueOf(XP_REQUIREMENT_INCREASE_PERCENTAGE).add(BigDecimal.ONE).pow(level)).longValue();
    }

    public static int getLevel(ItemStack pickaxe) {
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return 0;
        ItemMeta meta = pickaxe.getItemMeta();
        long level = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.LONG)) {
            level = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.LONG);
        }
        return (int) level;
    }
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
    /**
     * @return true if the pickaxe has leveled up
     */
    public static boolean addXP(ItemStack pickaxe, long xpToAdd) {
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return false;
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
                    lore.set(i, ChatColor.GREEN + "Experience: " + ChatColor.WHITE + Utils.prettyNum(currentAmount + xpToAdd) + " / " + Utils.prettyNum(getXpRequiredForPickaxeLevel(getLevel(pickaxe) + 1)));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lore.add(ChatColor.GREEN + "Experience: " + ChatColor.WHITE + Utils.prettyNum(currentAmount + xpToAdd) + " / " + Utils.prettyNum(getXpRequiredForPickaxeLevel(getLevel(pickaxe) + 1)));
            }
        } else {
            lore.add(ChatColor.GREEN + "Experience: " + ChatColor.WHITE + Utils.prettyNum(currentAmount + xpToAdd) + " / " + Utils.prettyNum(getXpRequiredForPickaxeLevel(getLevel(pickaxe) + 1)));
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
        if (getXpRequiredForPickaxeLevel(getLevel(pickaxe) + 1) <= currentAmount) {
            addLevel(pickaxe, 1);
            return true;
        }
        return false;
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
