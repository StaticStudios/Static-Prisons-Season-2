package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class SettingsMenus {
    //settings menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                String enabled = ChatColor.DARK_GRAY + "(" + ChatColor.GREEN + "Enabled" + ChatColor.DARK_GRAY + ")";
                String disabled = ChatColor.DARK_GRAY + "(" + ChatColor.RED + "Disabled" + ChatColor.DARK_GRAY + ")";
                String clickToChange = ChatColor.GRAY + "Click to change!";
                if (playerData.getIsAutoSellEnabled()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.DIAMOND, ChatColor.AQUA + "Auto-Sell " + enabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Automatically sell your backpack when it's full.", "", clickToChange));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.DIAMOND, ChatColor.AQUA + "Auto-Sell " + disabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Automatically sell your backpack when it's full.", "", clickToChange));
                if (playerData.getIsChatFilterEnabled()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.REDSTONE, ChatColor.AQUA + "Chat Filter " + enabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Censor explicit player messages in chat.", "", clickToChange));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.REDSTONE, ChatColor.AQUA + "Chat Filter " + disabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Censor explicit player messages in chat.", "", clickToChange));
                if (!playerData.getAreTipsDisabled()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.EMERALD, ChatColor.AQUA + "Tips " + enabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Receive helpful tips in chat every few minutes.", "", clickToChange));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.EMERALD, ChatColor.AQUA + "Tips " + disabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Receive helpful tips in chat every few minutes.", "", clickToChange));
            }

            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getCanEnableAutoSell()) {
                    player.sendMessage(ChatColor.RED + "You do not have auto-sell!");
                    return;
                }
                playerData.setIsAutoSellEnabled(!playerData.getIsAutoSellEnabled());
                open(player);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                playerData.setIsChatFilterEnabled(!playerData.getIsChatFilterEnabled());
                open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                playerData.setAreTipsDisabled(!playerData.getAreTipsDisabled());
                open(player);
            }
        };
        guiPage.identifier = "settings";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dSettings");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
