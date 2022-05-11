package net.staticstudios.prisons.gui.menus;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.GUIPage;
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
            public void item12Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("auctionHouse").args = "0";
                GUI.getGUIPage("auctionHouse").open(player);
            }
            @Override
            public void item13Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                if (!new PlayerData(player).getHasPrivateMine()) {
                    player.sendMessage(ChatColor.RED + "You do not have a private mine! Private mines can be purchased on our store (store.static-studios.net) or they can be won as prizes from crates and/or events!");
                    return;
                }
                GUI.getGUIPage("privateMines").open(player);
            }
            @Override
            public void item14Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("gamble").open(player);
            }

            @Override
            public void item16Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("chatTags").open(player);
            }

            @Override
            public void item28Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("stats").args = player.getName();
                GUI.getGUIPage("stats").open(player);
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
            public void item49Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("settings").open(player);
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
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.COMPASS, ChatColor.GREEN + "" + ChatColor.BOLD + "Warps", ChatColor.GRAY + "" + ChatColor.ITALIC + "Go somewhere!"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.DIAMOND_PICKAXE, ChatColor.AQUA + "" + ChatColor.BOLD + "Enchants", ChatColor.GRAY + "" + ChatColor.ITALIC + "Upgrade your pickaxe!"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.CLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Auction House", ChatColor.GRAY + "" + ChatColor.ITALIC + "Buy items sold by other players!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Type: \"/auc hand <price>\" to sell an item!"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.DEEPSLATE_DIAMOND_ORE, ChatColor.AQUA + "" + ChatColor.BOLD + "Private Mines", ChatColor.GRAY + "" + ChatColor.ITALIC + "Mine in your own private space!"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.SUNFLOWER, ChatColor.YELLOW + "" + ChatColor.BOLD + "Casino", ChatColor.GRAY + "" + ChatColor.ITALIC + "Test your luck for a chance to win prizes!"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.AMETHYST_SHARD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Multipliers", ChatColor.GRAY + "" + ChatColor.ITALIC + "View and activate your available multipliers!"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.NAME_TAG, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Chat Tags", ChatColor.GRAY + "" + ChatColor.ITALIC + "Customize how you look!"));
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
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.PLAYER_HEAD, ChatColor.GREEN + "" + ChatColor.BOLD + "Your Stats", ChatColor.GRAY + "" + ChatColor.ITALIC + "View your stats!"));
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
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.GUNPOWDER, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Settings", ChatColor.GRAY + "" + ChatColor.ITALIC + "Change the way you play"));
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.NETHER_STAR, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige", ChatColor.GRAY + "" + ChatColor.ITALIC + "Restart and do it all over again!", ChatColor.GRAY + "" + ChatColor.ITALIC + "With a slight bonus of course..."));


        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&9Static &bPrisons");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
}
