package net.staticstudios.prisons.gui;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUI {
    //TODO: make a error page to send players to when the page isn't found
    public static GUIPage getGUIPage(String id) {
        if (!GUIPage.guiPages.containsKey(id)) return null;
        return GUIPage.guiPages.get(id);
    }

    public static ItemStack createMenuItemOfPlayerSkull(String fromPage, OfflinePlayer playerToGetSkullOf, String name, String... lore) {
        List<String> itemLore = new ArrayList<String>();
        Collections.addAll(itemLore, lore);
        ItemStack item = PrisonUtils.Players.getSkull(playerToGetSkullOf);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "normal");
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "fromPage"), PersistentDataType.STRING, fromPage);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createPlaceholderItemOfPlayerSkull(OfflinePlayer playerToGetSkullOf, String name, String... lore) {
        List<String> itemLore = new ArrayList<String>();
        Collections.addAll(itemLore, lore);
        ItemStack item = PrisonUtils.Players.getSkull(playerToGetSkullOf);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "placeholder");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createMenuItem(String fromPage, Material icon, String name, String... lore) {
        List<String> itemLore = new ArrayList<String>();
        Collections.addAll(itemLore, lore);
        ItemStack item = new ItemStack(icon);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "normal");
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "fromPage"), PersistentDataType.STRING, fromPage);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createEnchantedMenuItem(String fromPage, Material icon, String name, String... lore) {
        List<String> itemLore = new ArrayList<String>();
        Collections.addAll(itemLore, lore);
        ItemStack item = new ItemStack(icon);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "normal");
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "fromPage"), PersistentDataType.STRING, fromPage);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(itemLore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addEnchant(Enchantment.LURE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createBackButton(String fromPage, String menuToGoTo) {
        List<String> itemLore = new ArrayList<String>();
        itemLore.add(ChatColor.RED + "This will take you back to the previous GUI menu");
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "backButton");
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "fromPage"), PersistentDataType.STRING, fromPage);
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "menuToGoTo"), PersistentDataType.STRING, menuToGoTo);
        itemMeta.setDisplayName(ChatColor.RED + "Go Back");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createPlaceholderItem(Material icon, String name, String... lore) {
        List<String> itemLore = new ArrayList<String>();
        if (lore != null) {
            Collections.addAll(itemLore, lore);
        }
        ItemStack item = new ItemStack(icon);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "placeholder");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createLightGrayPlaceholderItem() {
        ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "placeholder");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ChatColor.RED + "");
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createDarkGrayPlaceholderItem() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "placeholder");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ChatColor.RED + "");
        item.setItemMeta(itemMeta);
        return item;
    }
    public static ItemStack createRedPlaceholderItem() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "placeholder");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ChatColor.RED + "");
        item.setItemMeta(itemMeta);
        return item;
    }
}
