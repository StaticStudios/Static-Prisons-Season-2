package net.staticstudios.prisons.customitems;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.minebombs.MineBombTier1;
import net.staticstudios.prisons.customitems.minebombs.MineBombTier2;
import net.staticstudios.prisons.customitems.minebombs.MineBombTier3;
import net.staticstudios.prisons.customitems.minebombs.MineBombTier4;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItems implements Listener { //todo: add an internal mapping that maps an ID to an item/runnable to get the item so commands can be used with tab completion | aka finish the custom items module
    public static void init() {
        new MineBombTier1();
        new MineBombTier2();
        new MineBombTier3();
        new MineBombTier4();

        //TODO: keys, pickaxes, etc.


        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new CustomItems(), StaticPrisons.getInstance()); //todo: loop through all custom items and run a listener for each one
    }

    public static final Map<String, CustomItem> ITEMS = new HashMap<>();

    public static ItemStack getItem(String id, Player player) {
        CustomItem item = ITEMS.get(id);
        if (item == null) {
            return null;
        }
        return item.getItem(player);
    }


//    @EventHandler
//    void onDrop(PlayerDropItemEvent e) {
//        MineBombItem.itemDropped(e);
//    }
    @EventHandler //do this to "fight" worldguard
    void onClick(PlayerInteractEvent e) {
//        if (e.getHand() == null) return;
//        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
//        MineBombItem.blockPlaced(e);

        for (CustomItem item : ITEMS.values()) {
            if (item.onInteract(e)) {
                e.setCancelled(true);
                return;
            }
        }
    }
    private static ItemStack createCrateKey(String type, String name) {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "crateKey"), PersistentDataType.STRING, type);
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
        ItemStack item = createCrateKey("vote", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "VOTE CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getKitCrateKey(int amount) {
        ItemStack item = createCrateKey("kit", ChatColor.RED + "" + ChatColor.BOLD + "KIT CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getPickaxeCrateKey(int amount) {
        ItemStack item = createCrateKey("pickaxe", ChatColor.GREEN + "" + ChatColor.BOLD + "PICKAXE CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getCommonCrateKey(int amount) {
        ItemStack item = createCrateKey("common", ChatColor.AQUA + "" + ChatColor.BOLD + "COMMON CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getRareCrateKey(int amount) {
        ItemStack item = createCrateKey("rare", ChatColor.of("#03fca1") + "" + ChatColor.BOLD + "RARE CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getEpicCrateKey(int amount) {
        ItemStack item = createCrateKey("epic", ChatColor.of("#9B37FF") + "" + ChatColor.BOLD + "EPIC CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getLegendaryCrateKey(int amount) {
        ItemStack item = createCrateKey("legendary", ChatColor.GOLD + "" + ChatColor.BOLD + "LEGENDARY CRATE KEY");
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getStaticCrateKey(int amount) {
        ItemStack item = createCrateKey("static", ChatColor.translateAlternateColorCodes('&', "&x&f&a&0&4&f&f&lS&x&e&7&0&3&f&f&lT&x&d&4&0&3&f&f&lA&x&c&2&0&2&f&f&lT&x&a&f&0&2&f&f&lI&x&9&c&0&1&f&f&lC &x&8&9&0&0&f&f&lC&x&7&6&0&e&f&f&lR&x&6&2&2&9&f&f&lA&x&4&f&4&5&f&f&lT&x&3&b&6&0&f&f&lE &x&2&7&7&c&f&f&lK&x&1&4&9&7&f&f&lE&x&0&0&b&3&f&f&lY"));
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getStaticpCrateKey(int amount) {
        ItemStack item = createCrateKey("staticp", ChatColor.translateAlternateColorCodes('&', "&x&f&f&0&0&0&0&lS&x&f&f&3&6&0&0&lT&x&f&f&6&d&0&0&lA&x&f&f&a&4&0&0&lT&x&f&f&d&a&0&0&lI&x&d&b&f&f&0&0&lC&x&6&d&f&f&0&0&l+ &x&0&0&f&f&0&0&lC&x&0&0&9&2&6&d&lR&x&0&0&2&4&d&b&lA&x&1&5&0&0&d&b&lT&x&3&6&0&0&a&6&lE &x&5&5&0&0&8&e&lK&x&7&5&0&0&b&0&lE&x&9&4&0&0&d&3&lY"));
        item.setAmount(amount);
        return item;
    }

//    public static ItemStack getMineBombTier1() {
//        ItemStack item = new ItemStack(Material.TNT);
//        ItemMeta meta = item.getItemMeta();
//        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 1);
//        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Small Mine Bomb");
//        List<String> lore = new ArrayList<>();
//        lore.add("");
//        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
//        meta.setLore(lore);
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        meta.addEnchant(Enchantment.LURE, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        item.setItemMeta(meta);
//        return item;
//    }
//    public static ItemStack getMineBombTier2() {
//        ItemStack item = new ItemStack(Material.TNT);
//        ItemMeta meta = item.getItemMeta();
//        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 2);
//        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Medium Mine Bomb");
//        List<String> lore = new ArrayList<>();
//        lore.add("");
//        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
//        meta.setLore(lore);
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        meta.addEnchant(Enchantment.LURE, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        item.setItemMeta(meta);
//        return item;
//    }
//    public static ItemStack getMineBombTier3() {
//        ItemStack item = new ItemStack(Material.TNT);
//        ItemMeta meta = item.getItemMeta();
//        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 3);
//        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Large Mine Bomb");
//        List<String> lore = new ArrayList<>();
//        lore.add("");
//        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
//        meta.setLore(lore);
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        meta.addEnchant(Enchantment.LURE, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        item.setItemMeta(meta);
//        return item;
//    }
//    public static ItemStack getMineBombTier4() {
//        ItemStack item = new ItemStack(Material.TNT);
//        ItemMeta meta = item.getItemMeta();
//        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER, 4);
//        meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "HUGE Mine Bomb");
//        List<String> lore = new ArrayList<>();
//        lore.add("");
//        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Drop me in a mine and I'll explode!");
//        meta.setLore(lore);
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        meta.addEnchant(Enchantment.LURE, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        item.setItemMeta(meta);
//        return item;
//    }
}