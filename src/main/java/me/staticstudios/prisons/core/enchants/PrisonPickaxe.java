package me.staticstudios.prisons.core.enchants;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrisonPickaxe {
    public static Map<Player, Map<String, Integer>> cachedPickaxeStats = new HashMap<>(); //Make sure that this keeps the current player's item in their main hand

    public static Map<String, Integer> getCachedEnchants(Player player) {
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            cachedPickaxeStats.remove(player);
            return null;
        }
        if (!cachedPickaxeStats.containsKey(player)) updateCachedStats(player);
        return cachedPickaxeStats.get(player);
    }

    public static void updateCachedStats(Player player) {
        Map<String, Integer> map = new HashMap<>();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!Utils.checkIsPrisonPickaxe(item)) {
            cachedPickaxeStats.remove(player);
            return;
        }
        for (String ench : CustomEnchants.enchantIDsToNames.keySet()) {
            map.put(ench, (int) CustomEnchants.getEnchantLevel(item, ench));
        }
        cachedPickaxeStats.put(player, map);
    }



    public static final long BASE_XP_PER_BLOCK_BROKEN = 2;
    public static final long BASE_XP_PER_PICKAXE_LEVEL = 10000;
    public static final int XP_INCREASES_EVERY_X_LEVELS = 25;
    public static long getXpRequiredForPickaxeLevel(int level) {
        long cost = BASE_XP_PER_BLOCK_BROKEN;
        for (int i = 0; i < level; i++) cost += (i / XP_INCREASES_EVERY_X_LEVELS + 1) * BASE_XP_PER_PICKAXE_LEVEL;
        return cost;
    }

    public static int getLevel(ItemStack pickaxe) {
        if (true) return 0;
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return 0;
        ItemMeta meta = pickaxe.getItemMeta();
        long level = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.LONG)) {
            level = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.LONG);
        }
        return (int) level;
    }
    public static void addLevel(ItemStack pickaxe, long levelsToAdd) {
        if (true) return;
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
                    lore.set(i, ChatColor.GREEN + "Level: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + levelsToAdd));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lore.add(ChatColor.GREEN + "Level: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + levelsToAdd));
            }
        } else {
            lore.add(ChatColor.GREEN + "Level: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + levelsToAdd));
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }
    /**
     * @return true if the pickaxe has leveled up
     */
    public static boolean addXP(ItemStack pickaxe, long xpToAdd) {
        if (true) return false;
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

    public static void addBlocksBroken(ItemStack pickaxe, long amountToAdd) {
        if (true) return;
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
                    lore.set(i, ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + amountToAdd));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lore.add(ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + amountToAdd));
            }
        } else {
            lore.add(ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + amountToAdd));
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }
    public static void addBlocksMined(ItemStack pickaxe, long amountToAdd) {
        if (true) return;
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return;
        ItemMeta meta = pickaxe.getItemMeta();
        long currentAmount = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "blocksMined"), PersistentDataType.LONG)) {
            currentAmount = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "blocksMined"), PersistentDataType.LONG);
        }
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "blocksMined"), PersistentDataType.LONG, currentAmount + amountToAdd);
        boolean updated = false;
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (ChatColor.stripColor(line).startsWith("Blocks Mined:")) {
                    lore.set(i, ChatColor.GREEN + "Blocks Mined: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + amountToAdd));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lore.add(ChatColor.GREEN + "Blocks Mined: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + amountToAdd));
            }
        } else {
            lore.add(ChatColor.GREEN + "Blocks Mined: " + ChatColor.WHITE + Utils.addCommasToNumber(currentAmount + amountToAdd));
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }
}
