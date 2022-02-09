package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenus {
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void item10Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("warps").open(player);
            }
            @Override
            public void item11Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("enchantsSelectPickaxe").open(player);
            }

            @Override
            public void item34Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("upgradeBackpack").open(player);
            }


            @Override
            public void item45Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("rankUp").open(player);
            }
            @Override
            public void item53Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("prestige").open(player);
            }

        };
        guiPage.identifier = "main";

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());


        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.COMPASS, ChatColor.GREEN + "" + ChatColor.BOLD + "Warps", ChatColor.GRAY + "" + ChatColor.ITALIC + "Go places!"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.DIAMOND_PICKAXE, ChatColor.AQUA + "" + ChatColor.BOLD + "Enchants", ChatColor.GRAY + "" + ChatColor.ITALIC + "Upgrade your pickaxe!"));
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.ENDER_CHEST, ChatColor.YELLOW + "" + ChatColor.BOLD + "Backpack", ChatColor.GRAY + "" + ChatColor.ITALIC + "Upgrade your backpack!"));
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());


        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.EMERALD, ChatColor.GREEN + "" + ChatColor.BOLD + "Rank Up!", ChatColor.GRAY + "" + ChatColor.ITALIC + "Unlock new mines!"));
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.REDSTONE_TORCH, ChatColor.RED + "Settings", ChatColor.GRAY + "Manage your settings"));
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.NETHER_STAR, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige", ChatColor.GRAY + "" + ChatColor.ITALIC + "Restart and do it all over again!", ChatColor.GRAY + "" + ChatColor.ITALIC + "With a slight bonus of course..."));


        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&9Static &bPrisons");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
}
