package net.staticstudios.prisons.gui.menus;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.GUIPage;
import net.md_5.bungee.api.ChatColor;
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
                if (!playerData.getAreTipsDisabled()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.EMERALD, ChatColor.AQUA + "Tips " + enabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Receive helpful tips in chat every few minutes.", "", clickToChange));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.EMERALD, ChatColor.AQUA + "Tips " + disabled, ChatColor.GRAY + "" + ChatColor.ITALIC + "Receive helpful tips in chat every few minutes.", "", clickToChange));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER, ChatColor.YELLOW + "" + ChatColor.BOLD + "Chat Settings ", ChatColor.GRAY + "" + ChatColor.ITALIC + "Change your chat color, font settings, and more!"));

                //UI theme
                Material currentTheme = playerData.getUITheme();
                ChatColor color = ChatColor.AQUA;
                String themeName = "Blue";
                switch (currentTheme) {
                    case RED_DYE -> {
                        color = ChatColor.RED;
                        themeName = "Red";
                    }
                    case ORANGE_DYE -> {
                        color = ChatColor.GOLD;
                        themeName = "Orange";
                    }
                    case LIME_DYE -> {
                        color = ChatColor.GREEN;
                        themeName = "Green";
                    }
                    case PURPLE_DYE -> {
                        color = ChatColor.DARK_PURPLE;
                        themeName = "Purple";
                    }
                }
                menuItems.add(GUI.createEnchantedMenuItem(identifier, currentTheme, color + "" + ChatColor.BOLD + "UI Theme (Current Theme: " + themeName + ")", ChatColor.GRAY + "" + ChatColor.ITALIC + "Change your UI theme!"));

            }

            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getCanExplicitlyEnableAutoSell() && !playerData.getPlayerRanks().contains("warrior") && !playerData.getIsNitroBoosting()) {
                    player.sendMessage(ChatColor.RED + "You do not have auto-sell!");
                    playerData.setIsAutoSellEnabled(false);
                    return;
                }
                playerData.setIsAutoSellEnabled(!playerData.getIsAutoSellEnabled());
                open(player);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                playerData.setAreTipsDisabled(!playerData.getAreTipsDisabled());
                open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("chatSettings").open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("themeSettings").open(player);
            }
        };
        guiPage.identifier = "settings";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dSettings");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
    //chat settings menu
    public static void chat() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                String current = ChatColor.WHITE + "";
                if (playerData.getIsChatColorEnabled()) current += playerData.getChatColor();
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                current += "This is what your chat currently looks like.";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.YELLOW_DYE,  current, ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to change your chat's color!"));
                if (playerData.getIsChatBold()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.BLACK_DYE,  current, ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to make your chat un-bold!"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.BLACK_DYE, current, ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to make your chat bold!"));
                if (playerData.getIsChatItalic()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.SPECTRAL_ARROW,  current, ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to make your chat un-italic!"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.SPECTRAL_ARROW, current, ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to make your chat italic!"));
                if (playerData.getIsChatUnderlined()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.IRON_TRAPDOOR,  current, ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to make your chat un-underlined!"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.IRON_TRAPDOOR, current, ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to make your chat underlined!"));
            }

            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("chatSettingsColor").open(player);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("mythic")) {
                    player.sendMessage(ChatColor.RED + "You need Mythic rank or higher to use this!");
                    return;
                }
                playerData.setIsChatBold(!playerData.getIsChatBold());
                open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("mythic")) {
                    player.sendMessage(ChatColor.RED + "You need Mythic rank or higher to use this!");
                    return;
                }
                playerData.setIsChatItalic(!playerData.getIsChatItalic());
                open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("mythic")) {
                    player.sendMessage(ChatColor.RED + "You need Mythic rank or higher to use this!");
                    return;
                }
                playerData.setIsChatUnderlined(!playerData.getIsChatUnderlined());
                open(player);
            }
        };
        guiPage.identifier = "chatSettings";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dChat Settings");
        guiPage.onCloseGoToMenu = "settings";
        guiPage.register();
    }
    //chat color settings menu
    public static void chatColor() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                String current = ChatColor.WHITE + "";
                if (playerData.getIsChatColorEnabled()) current += playerData.getChatColor();
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                current += "This is what your chat currently looks like.";

                String change;
                change = ChatColor.WHITE + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.BLACK + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.DARK_BLUE + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.DARK_GREEN + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.DARK_AQUA + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.DARK_RED + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.DARK_PURPLE + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.GOLD + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.GRAY + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.DARK_GRAY + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.BLUE + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.GREEN + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.AQUA + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.RED + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.LIGHT_PURPLE + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.YELLOW + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.of("#8945ff") + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.of("#ff00ff") + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
                change = ChatColor.of("#ff8c00") + "";
                if (playerData.getIsChatBold()) current += ChatColor.BOLD;
                if (playerData.getIsChatItalic()) current += ChatColor.ITALIC;
                if (playerData.getIsChatUnderlined()) current += ChatColor.UNDERLINE;
                change += "Click to make your chat color this!";
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PAPER,  current, change));
            }

            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(false);
                open(player);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.BLACK);
                open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.DARK_BLUE);
                open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.DARK_GREEN);
                open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.DARK_AQUA);
                open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.DARK_RED);
                open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.DARK_PURPLE);
                open(player);
            }
            @Override
            public void item7Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.GOLD);
                open(player);
            }
            @Override
            public void item8Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.GRAY);
                open(player);
            }
            @Override
            public void item9Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.DARK_GRAY);
                open(player);
            }
            @Override
            public void item10Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.BLUE);
                open(player);
            }
            @Override
            public void item11Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.GREEN);
                open(player);
            }
            @Override
            public void item12Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.AQUA);
                open(player);
            }
            @Override
            public void item13Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.RED);
                open(player);
            }
            @Override
            public void item14Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.LIGHT_PURPLE);
                open(player);
            }
            @Override
            public void item15Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.RED);
                open(player);
            }
            @Override
            public void item16Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.of("#8945ff"));
                open(player);
            }
            @Override
            public void item17Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.of("#ff00ff"));
                open(player);
            }
            @Override
            public void item18Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    player.sendMessage(ChatColor.RED + "You need Warrior rank or higher to use this!");
                    return;
                }
                playerData.setIsChatColorEnabled(true);
                playerData.setChatColor(ChatColor.of("#ff8c00"));
                open(player);
            }

        };
        guiPage.identifier = "chatSettingsColor";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dChange Your Chat Color");
        guiPage.onCloseGoToMenu = "chatSettings";
        guiPage.register();
    }
    //chat settings menu
    public static void theme() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                //UI theme
                Material currentTheme = playerData.getUITheme();
                ChatColor color = ChatColor.AQUA;
                String themeName = "Blue";
                switch (currentTheme) {
                    case RED_DYE -> {
                        color = ChatColor.RED;
                        themeName = "Red";
                    }
                    case ORANGE_DYE -> {
                        color = ChatColor.GOLD;
                        themeName = "Orange";
                    }
                    case LIME_DYE -> {
                        color = ChatColor.GREEN;
                        themeName = "Green";
                    }
                    case PURPLE_DYE -> {
                        color = ChatColor.DARK_PURPLE;
                        themeName = "Purple";
                    }
                }
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.LIGHT_BLUE_DYE, ChatColor.AQUA + "Change theme to: BLUE", ChatColor.GRAY + "" + ChatColor.ITALIC + "Current theme: " + themeName));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.PURPLE_DYE, ChatColor.DARK_PURPLE + "Change theme to: PURPLE", ChatColor.GRAY + "" + ChatColor.ITALIC + "Current theme: " + themeName));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.LIME_DYE, ChatColor.GREEN + "Change theme to: GREEN", ChatColor.GRAY + "" + ChatColor.ITALIC + "Current theme: " + themeName));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.RED_DYE, ChatColor.RED + "Change theme to: RED", ChatColor.GRAY + "" + ChatColor.ITALIC + "Current theme: " + themeName));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.ORANGE_DYE, ChatColor.GOLD + "Change theme to: ORANGE", ChatColor.GRAY + "" + ChatColor.ITALIC + "Current theme: " + themeName));
            }

            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                new PlayerData(player).setUITheme(Material.LIGHT_BLUE_DYE);
                player.sendMessage(ChatColor.AQUA + "UI theme changed!");
                GUI.getGUIPage("settings").open(player);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                new PlayerData(player).setUITheme(Material.PURPLE_DYE);
                player.sendMessage(ChatColor.AQUA + "UI theme changed!");
                GUI.getGUIPage("settings").open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                new PlayerData(player).setUITheme(Material.LIME_DYE);
                player.sendMessage(ChatColor.AQUA + "UI theme changed!");
                GUI.getGUIPage("settings").open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                new PlayerData(player).setUITheme(Material.RED_DYE);
                player.sendMessage(ChatColor.AQUA + "UI theme changed!");
                GUI.getGUIPage("settings").open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                new PlayerData(player).setUITheme(Material.ORANGE_DYE);
                player.sendMessage(ChatColor.AQUA + "UI theme changed!");
                GUI.getGUIPage("settings").open(player);
            }
        };
        guiPage.identifier = "themeSettings";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dChat Settings");
        guiPage.onCloseGoToMenu = "settings";
        guiPage.register();
    }
}
