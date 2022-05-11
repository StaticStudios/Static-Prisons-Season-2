package net.staticstudios.gui;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * This class is persistent as any GUI that is created with this (instead of GUICreator) will be kept in memory
 * Use this class for any static menus or any menus being viewed by multiple users
 */
public abstract class StaticGUI extends GUIUtils implements InventoryHolder {

    private static JavaPlugin parent;
    public static void enable(JavaPlugin parent) {
        StaticGUI.parent = parent;
        GUIUtils.init();
        parent.getServer().getPluginManager().registerEvents(new GUIListener(), parent);
    }
    public static JavaPlugin getParent() { return parent; }

    private Inventory inventory;
    private Inventory openOnClose = null;
    private GUIRunnable onCloseRun = null;

    private final UUID guiUUID = UUID.randomUUID();
    public String menuID = "";
    public String getMenuID() { return menuID; }
    public void setMenuID(String menuID) { this.menuID = menuID; }
    public Inventory getMenuToOpenOnClose() { return openOnClose; }
    public void setInventoryToOpenOnClose(Inventory inv) { openOnClose = inv; }
    public GUIRunnable getOnCloseRun() { return onCloseRun; }
    public void setOnCloseRun(GUIRunnable r) { onCloseRun = r; }
    public boolean destroyOnClose = false;
    public void setDestroyOnClose(boolean destroy) {
        destroyOnClose = destroy;
    }
    public static Map<UUID, StaticGUI> allMenus = new HashMap<>();
    public Map<Integer, GUIRunnable> callbacks = new HashMap<>();

    public void destroy() {
        allMenus.remove(guiUUID);
    }

    public StaticGUI(int size, String title) {
        inventory = Bukkit.createInventory(this, Math.max(1, (size / 9)) * 9, title);
        allMenus.put(guiUUID, this);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public ItemStack createNormalItem(Material icon, String name, List<String> lore) {
        if (lore != null) lore = new ArrayList<>(lore); //Some lists are immutable
        ItemStack itemStack = new ItemStack(icon);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r" + name));
        if (lore != null) {
            for (int i = 0; i < lore.size(); i++)
                lore.set(i, ChatColor.translateAlternateColorCodes('&', "&7" + lore.get(i)));
            meta.setLore(lore);
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public ItemStack createNormalItem(ItemStack item, GUIRunnable callback) {
        applyRunnableToItem(item, callback);
        return item;
    }
    public ItemStack createNormalItem(Material icon, String name, List<String> lore, GUIRunnable callback) {
        if (lore != null) lore = new ArrayList<>(lore); //Some lists are immutable
        ItemStack itemStack = createNormalItem(icon, name, lore);
        applyRunnableToItem(itemStack, callback);
        return itemStack;
    }

    public ItemStack createNormalItemOfPlayerSkull(OfflinePlayer player, String name, List<String> lore, GUIRunnable callback) {
        if (lore != null) lore = new ArrayList<>(lore); //Some lists are immutable
        ItemStack itemStack = createNormalItemOfPlayerSkull(player, name, lore);
        applyRunnableToItem(itemStack, callback);
        return itemStack;
    }

    public ItemStack createNormalItemOfPlayerSkull(OfflinePlayer player, String name, List<String> lore) {
        if (lore != null) lore = new ArrayList<>(lore); //Some lists are immutable
        ItemStack itemStack = GUIUtils.createCustomSkull(player);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r" + name));
        for (int i = 0; i < lore.size(); i++) lore.set(i, ChatColor.translateAlternateColorCodes('&', "&7" + lore.get(i)));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private void applyRunnableToItem(ItemStack itemStack, GUIRunnable runnable) {
        new GUIItem(itemStack).setRunnable(runnable, guiUUID);
        callbacks.put(runnable.hashCode(), runnable);
    }

}
