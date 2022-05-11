package net.staticstudios.prisons.customItems;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.enchants.handler.CustomEnchants;
import net.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CustomItems {
    public static final String PICKAXE_TIER_1_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 1");
    public static final String PICKAXE_TIER_2_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 2");
    public static final String PICKAXE_TIER_3_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 3");
    public static final String PICKAXE_TIER_4_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 4");
    public static final String PICKAXE_TIER_5_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 5");
    public static final String PICKAXE_TIER_6_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 6");
    public static final String PICKAXE_TIER_7_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 7");
    public static final String PICKAXE_TIER_8_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 8");
    public static final String PICKAXE_TIER_9_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 9");
    public static final String PICKAXE_TIER_10_NAME = ChatColor.translateAlternateColorCodes('&', "&bPickaxe Tier 10");
    private static ItemStack crateCrateKey(String type, String name) {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "customItemGroup"), PersistentDataType.STRING, "crateKey");
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "customItemType"), PersistentDataType.STRING, type);
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.LURE, 100, true);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Open a crate with this key for");
        lore.add(ChatColor.GRAY + "a random reward! " + ChatColor.AQUA + "/warp crates");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack getVoteCrateKey(int amount) {
        ItemStack item = crateCrateKey("vote", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "VOTE CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getKitCrateKey(int amount) {
        ItemStack item = crateCrateKey("kit", ChatColor.RED + "" + ChatColor.BOLD + "KIT CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getPickaxeCrateKey(int amount) {
        ItemStack item = crateCrateKey("pickaxe", ChatColor.GREEN + "" + ChatColor.BOLD + "PICKAXE CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getCommonCrateKey(int amount) {
        ItemStack item = crateCrateKey("common", ChatColor.AQUA + "" + ChatColor.BOLD + "COMMON CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getRareCrateKey(int amount) {
        ItemStack item = crateCrateKey("rare", ChatColor.of("#03fca1") + "" + ChatColor.BOLD + "RARE CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getEpicCrateKey(int amount) {
        ItemStack item = crateCrateKey("epic", ChatColor.of("#9B37FF") + "" + ChatColor.BOLD + "EPIC CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getLegendaryCrateKey(int amount) {
        ItemStack item = crateCrateKey("legendary", ChatColor.GOLD + "" + ChatColor.BOLD + "LEGENDARY CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getStaticCrateKey(int amount) {
        ItemStack item = crateCrateKey("static", ChatColor.translateAlternateColorCodes('&', "&x&f&a&0&4&f&f&lS&x&e&7&0&3&f&f&lT&x&d&4&0&3&f&f&lA&x&c&2&0&2&f&f&lT&x&a&f&0&2&f&f&lI&x&9&c&0&1&f&f&lC &x&8&9&0&0&f&f&lC&x&7&6&0&e&f&f&lR&x&6&2&2&9&f&f&lA&x&4&f&4&5&f&f&lT&x&3&b&6&0&f&f&lE &x&2&7&7&c&f&f&lK&x&1&4&9&7&f&f&lE&x&0&0&b&3&f&f&lY"));
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getStaticpCrateKey(int amount) {
        ItemStack item = crateCrateKey("staticp", ChatColor.translateAlternateColorCodes('&', "&x&f&f&0&0&0&0&lS&x&f&f&3&6&0&0&lT&x&f&f&6&d&0&0&lA&x&f&f&a&4&0&0&lT&x&f&f&d&a&0&0&lI&x&d&b&f&f&0&0&lC&x&6&d&f&f&0&0&l+ &x&0&0&f&f&0&0&lC&x&0&0&9&2&6&d&lR&x&0&0&2&4&d&b&lA&x&1&5&0&0&d&b&lT&x&3&6&0&0&a&6&lE &x&5&5&0&0&8&e&lK&x&7&5&0&0&b&0&lE&x&9&4&0&0&d&3&lY"));
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getPickaxeTier1() { return null; }
    public static ItemStack getPickaxeTier2() { return null; }
    public static ItemStack getPickaxeTier3() { return null; }
    public static ItemStack getPickaxeTier4() { return null; }
    public static ItemStack getPickaxeTier5() { return null; }
    public static ItemStack getPickaxeTier6() { return null; }
    public static ItemStack getPickaxeTier7() { return null; }
    public static ItemStack getPickaxeTier8() { return null; }
    public static ItemStack getPickaxeTier9() { return null; }
    public static ItemStack getPickaxeTier10() { return null; }
/*
    //Pickaxes
    public static ItemStack getPickaxeTier1() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_1_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 25);
        return item;
    }
    public static ItemStack getPickaxeTier2() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_2_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 50);
        CustomEnchants.setEnchantLevel(item, "tokenator", 20);
        return item;
    }
    public static ItemStack getPickaxeTier3() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_3_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 100);
        CustomEnchants.setEnchantLevel(item, "tokenator", 50);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 15);
        return item;
    }
    public static ItemStack getPickaxeTier4() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_4_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 250);
        CustomEnchants.setEnchantLevel(item, "tokenator", 75);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 75);
        return item;
    }
    public static ItemStack getPickaxeTier5() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_5_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 350);
        CustomEnchants.setEnchantLevel(item, "tokenator", 100);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 150);
        CustomEnchants.setEnchantLevel(item, "multiDirectional", 75);
        return item;
    }
    public static ItemStack getPickaxeTier6() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_6_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 500);
        CustomEnchants.setEnchantLevel(item, "tokenator", 150);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 250);
        CustomEnchants.setEnchantLevel(item, "multiDirectional", 150);
        return item;
    }
    public static ItemStack getPickaxeTier7() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_7_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 750);
        CustomEnchants.setEnchantLevel(item, "tokenator", 250);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 250);
        CustomEnchants.setEnchantLevel(item, "multiDirectional", 250);
        CustomEnchants.setEnchantLevel(item, "keyFinder", 100);
        return item;
    }
    public static ItemStack getPickaxeTier8() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_8_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 1000);
        CustomEnchants.setEnchantLevel(item, "oreSplitter", 10);
        CustomEnchants.setEnchantLevel(item, "tokenator", 400);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 400);
        CustomEnchants.setEnchantLevel(item, "multiDirectional", 400);
        CustomEnchants.setEnchantLevel(item, "keyFinder", 250);
        return item;
    }
    public static ItemStack getPickaxeTier9() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_9_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 1500);
        CustomEnchants.setEnchantLevel(item, "oreSplitter", 25);
        CustomEnchants.setEnchantLevel(item, "tokenator", 500);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 1000);
        CustomEnchants.setEnchantLevel(item, "doubleWammy", 10);
        CustomEnchants.setEnchantLevel(item, "multiDirectional", 1000);
        CustomEnchants.setEnchantLevel(item, "keyFinder", 500);
        return item;
    }
    public static ItemStack getPickaxeTier10() {
        ItemStack item = Utils.createNewPickaxe();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PICKAXE_TIER_10_NAME);
        item.setItemMeta(meta);
        CustomEnchants.setEnchantLevel(item, "fortune", 3000);
        CustomEnchants.setEnchantLevel(item, "oreSplitter", 75);
        CustomEnchants.setEnchantLevel(item, "tokenator", 1000);
        CustomEnchants.setEnchantLevel(item, "jackHammer", 2500);
        CustomEnchants.setEnchantLevel(item, "doubleWammy", 50);
        CustomEnchants.setEnchantLevel(item, "multiDirectional", 2500);
        CustomEnchants.setEnchantLevel(item, "keyFinder", 1000);
        CustomEnchants.setEnchantLevel(item, "consistency", 1);
        return item;
    }

 */

    public static ItemStack getMineBombTier1() {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Small Mine Bomb");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack getMineBombTier2() {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 2);
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Medium Mine Bomb");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack getMineBombTier3() {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 3);
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Large Mine Bomb");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack getMineBombTier4() {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 4);
        meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "HUGE Mine Bomb");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
