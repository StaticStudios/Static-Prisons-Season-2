package me.staticstudios.prisons.gui;

import me.staticstudios.prisons.gui.menus.*;
import me.staticstudios.prisons.misc.chat.ChatTags;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;

public class GUIPage {
    public static TreeMap<String, GUIPage> guiPages = new TreeMap<String, GUIPage>();
    public String args = "";
    public Object[] argsObjArr = null;
    public static void initializeGUIPages() {
        List<Class> classes = new ArrayList<>();
        classes.add(MainMenus.class);
        classes.add(EnchantsMenus.class);
        classes.add(RankUpMenus.class);
        classes.add(WarpsMenus.class);
        classes.add(ChatTagsMenus.class);
        for (Class c : classes) {
            //Method[] methods = Main.class.getDeclaredMethods();
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                try {
                    method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String identifier = "";
    public String onCloseGoToMenu = "main";
    public int size = 0;
    //public boolean forceSize = false;
    public String guiTitle = "New GUI Menu";
    public int permissionLevelNeeded = 0;
    public List<ItemStack> menuItems = new ArrayList<>();
    public void register() {
        if (identifier.equals("")) {
            Bukkit.getLogger().log(Level.INFO, ChatColor.RED + "Tried to register the GUI menu: \"" + guiTitle + "\" without a identifier!");
        }  else {
            GUIPage.guiPages.put(identifier, this);
        }
    }
    public void open(Player player) {
        onOpen(player);
        List<ItemStack> currentMenuItems = new ArrayList<>(menuItems);
        size = currentMenuItems.size();
        if (onCloseGoToMenu != null) size += 1;
        if (size % 9 != 0) size += 9;
        size /= 9;
        size *= 9;
        if (size > 54) size = 54;
        int amountOfPlaceholders;
        if (onCloseGoToMenu != null) {
            amountOfPlaceholders = size - currentMenuItems.size() - 1;
        } else {
            amountOfPlaceholders = size - currentMenuItems.size();
        }
        for (int i = 0; i < amountOfPlaceholders; i++) {
            currentMenuItems.add(GUI.createLightGrayPlaceholderItem());
        }
        if (onCloseGoToMenu != null) currentMenuItems.add(GUI.createBackButton(identifier, onCloseGoToMenu));
        Inventory gui = Bukkit.createInventory(player, size, guiTitle);
        gui.setContents(currentMenuItems.toArray(new ItemStack[0]));
        player.openInventory(gui);
    }
    public void onOpen(Player player) {}
    public void onClose(Player player) {}
    public void itemClicked(InventoryClickEvent e) {}
    public void item0Clicked(InventoryClickEvent e) {}
    public void item1Clicked(InventoryClickEvent e) {}
    public void item2Clicked(InventoryClickEvent e) {}
    public void item3Clicked(InventoryClickEvent e) {}
    public void item4Clicked(InventoryClickEvent e) {}
    public void item5Clicked(InventoryClickEvent e) {}
    public void item6Clicked(InventoryClickEvent e) {}
    public void item7Clicked(InventoryClickEvent e) {}
    public void item8Clicked(InventoryClickEvent e) {}
    public void item9Clicked(InventoryClickEvent e) {}
    public void item10Clicked(InventoryClickEvent e) {}
    public void item11Clicked(InventoryClickEvent e) {}
    public void item12Clicked(InventoryClickEvent e) {}
    public void item13Clicked(InventoryClickEvent e) {}
    public void item14Clicked(InventoryClickEvent e) {}
    public void item15Clicked(InventoryClickEvent e) {}
    public void item16Clicked(InventoryClickEvent e) {}
    public void item17Clicked(InventoryClickEvent e) {}
    public void item18Clicked(InventoryClickEvent e) {}
    public void item19Clicked(InventoryClickEvent e) {}
    public void item20Clicked(InventoryClickEvent e) {}
    public void item21Clicked(InventoryClickEvent e) {}
    public void item22Clicked(InventoryClickEvent e) {}
    public void item23Clicked(InventoryClickEvent e) {}
    public void item24Clicked(InventoryClickEvent e) {}
    public void item25Clicked(InventoryClickEvent e) {}
    public void item26Clicked(InventoryClickEvent e) {}
    public void item27Clicked(InventoryClickEvent e) {}
    public void item28Clicked(InventoryClickEvent e) {}
    public void item29Clicked(InventoryClickEvent e) {}
    public void item30Clicked(InventoryClickEvent e) {}
    public void item31Clicked(InventoryClickEvent e) {}
    public void item32Clicked(InventoryClickEvent e) {}
    public void item33Clicked(InventoryClickEvent e) {}
    public void item34Clicked(InventoryClickEvent e) {}
    public void item35Clicked(InventoryClickEvent e) {}
    public void item36Clicked(InventoryClickEvent e) {}
    public void item37Clicked(InventoryClickEvent e) {}
    public void item38Clicked(InventoryClickEvent e) {}
    public void item39Clicked(InventoryClickEvent e) {}
    public void item40Clicked(InventoryClickEvent e) {}
    public void item41Clicked(InventoryClickEvent e) {}
    public void item42Clicked(InventoryClickEvent e) {}
    public void item43Clicked(InventoryClickEvent e) {}
    public void item44Clicked(InventoryClickEvent e) {}
    public void item45Clicked(InventoryClickEvent e) {}
    public void item46Clicked(InventoryClickEvent e) {}
    public void item47Clicked(InventoryClickEvent e) {}
    public void item48Clicked(InventoryClickEvent e) {}
    public void item49Clicked(InventoryClickEvent e) {}
    public void item50Clicked(InventoryClickEvent e) {}
    public void item51Clicked(InventoryClickEvent e) {}
    public void item52Clicked(InventoryClickEvent e) {}
    public void item53Clicked(InventoryClickEvent e) {}
}
