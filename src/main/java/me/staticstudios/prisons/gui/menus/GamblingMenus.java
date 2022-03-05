package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.gambling.CoinFlip;
import me.staticstudios.prisons.gambling.GambleHandler;
import me.staticstudios.prisons.gambling.TokenFlip;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class GamblingMenus {
    //Main Gambling Menu
    public static void gamble() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                guiPages.get("cf").open((Player) e.getWhoClicked());
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                guiPages.get("tf").open((Player) e.getWhoClicked());
            }
        };
        guiPage.identifier = "gamble";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dGambling");
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.PAPER, ChatColor.GREEN + "Coin Flips", new String[]{ChatColor.GRAY + "Two players will have a 50/50 chance to win or lose, winner takes all.", ChatColor.GRAY + "To create a coin flip type \"/cf <amount> <heads|tails>\""}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.SUNFLOWER, ChatColor.GOLD + "Token Flips", new String[]{ChatColor.GRAY + "Two players will have a 50/50 chance to win or lose, winner takes all.", ChatColor.GRAY + "To create a token flip type \"/tf <amount> <heads|tails>\""}));
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }

    //Coin Flip menu
    public static void cf() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                for (int i = 0; i < 53; i++) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
                menuItems.set(0, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(8, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(9, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(17, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(18, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(26, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(27, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(35, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(36, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(44, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(45, GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                int index = 0;
                for (String key : GambleHandler.coinFlips.keySet()) {
                    CoinFlip cf = GambleHandler.coinFlips.get(key);
                    Material icon;
                    String side;
                    if (cf.isHeads) {
                        icon = cf.headsIcon;
                        side = "Heads";
                    } else {
                        icon = cf.tailsIcon;
                        side = "Tails";
                    }
                    if ((index + 1) % 7 == 0) {
                        index += 3;
                    }
                    if (player.equals(cf.owner)) {
                        menuItems.set(index + 1, GUI.createMenuItem(identifier, icon, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + cf.owner.getName(), ChatColor.DARK_GRAY + "Bet: " + ChatColor.GREEN + "$" + Utils.addCommasToNumber(cf.amount), ChatColor.DARK_GRAY + "Side: " + ChatColor.AQUA + side, "", ChatColor.RED + "Click to cancel"));
                        ItemMeta meta = menuItems.get(index + 1).getItemMeta();
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, cf.uuid);
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "isYours"), PersistentDataType.INTEGER, 1);
                        menuItems.get(index + 1).setItemMeta(meta);
                    } else {
                        menuItems.set(index + 1, GUI.createMenuItem(identifier, icon, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + cf.owner.getName(), ChatColor.DARK_GRAY + "Bet: " + ChatColor.GREEN + "$" + Utils.addCommasToNumber(cf.amount), ChatColor.DARK_GRAY + "Side: " + ChatColor.AQUA + side, "", ChatColor.GREEN + "Click to accept bet"));
                        ItemMeta meta = menuItems.get(index + 1).getItemMeta();
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, cf.uuid);
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "isYours"), PersistentDataType.INTEGER, 0);
                        menuItems.get(index + 1).setItemMeta(meta);
                    }
                    index++;
                }
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "isYours"), PersistentDataType.INTEGER) == 1) {
                    CoinFlip.removeAFlip(uuid);
                    open(player);
                    return;
                }
                if (!CoinFlip.checkIfExists(uuid)) {
                    player.sendMessage(ChatColor.RED + "This CoinFlip no longer exists!");
                    open(player);
                    return;
                }
                CoinFlip cf = GambleHandler.coinFlips.get(uuid);
                if (cf.amount.compareTo(new PlayerData(player.getUniqueId()).getMoney()) > 0) {
                    player.sendMessage(ChatColor.RED + "You do not have enough money to make this bet!");
                    return;
                }
                cf.runBet(player);
            }
        };
        guiPage.identifier = "cf";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dCoin Flips");
        guiPage.onCloseGoToMenu = "gamble";
        guiPage.register();
    }
    //Token Flip menu
    public static void tf() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                for (int i = 0; i < 53; i++) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
                menuItems.set(0, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(8, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(9, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(17, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(18, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(26, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(27, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(35, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(36, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                menuItems.set(44, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));

                menuItems.set(45, GUI.createPlaceholderItem(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
                int index = 0;
                for (String key : GambleHandler.tokenFlips.keySet()) {
                    TokenFlip tf = GambleHandler.tokenFlips.get(key);
                    Material icon;
                    String side;
                    if (tf.isHeads) {
                        icon = tf.headsIcon;
                        side = "Heads";
                    } else {
                        icon = tf.tailsIcon;
                        side = "Tails";
                    }
                    if ((index + 1) % 7 == 0) {
                        index += 3;
                    }
                    if (player.equals(tf.owner)) {
                        menuItems.set(index + 1, GUI.createMenuItem(identifier, icon, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + tf.owner.getName(), ChatColor.DARK_GRAY + "Bet: " + ChatColor.GOLD + Utils.addCommasToNumber(tf.amount) + " Tokens", ChatColor.DARK_GRAY + "Side: " + ChatColor.AQUA + side, "", ChatColor.RED + "Click to cancel"));
                        ItemMeta meta = menuItems.get(index + 1).getItemMeta();
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, tf.uuid);
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "isYours"), PersistentDataType.INTEGER, 1);
                        menuItems.get(index + 1).setItemMeta(meta);
                    } else {
                        menuItems.set(index + 1, GUI.createMenuItem(identifier, icon, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + tf.owner.getName(), ChatColor.DARK_GRAY + "Bet: " + ChatColor.GOLD + Utils.addCommasToNumber(tf.amount) + " Tokens", ChatColor.DARK_GRAY + "Side: " + ChatColor.AQUA + side, "", ChatColor.GREEN + "Click to accept bet"));
                        ItemMeta meta = menuItems.get(index + 1).getItemMeta();
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, tf.uuid);
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "isYours"), PersistentDataType.INTEGER, 0);
                        menuItems.get(index + 1).setItemMeta(meta);
                    }
                    index++;
                }
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "isYours"), PersistentDataType.INTEGER) == 1) {
                    TokenFlip.removeAFlip(uuid);
                    open(player);
                    return;
                }
                if (!TokenFlip.checkIfExists(uuid)) {
                    player.sendMessage(ChatColor.RED + "This TokenFlip no longer exists!");
                    open(player);
                    return;
                }
                TokenFlip tf = GambleHandler.tokenFlips.get(uuid);
                if (tf.amount.compareTo(new PlayerData(player.getUniqueId()).getTokens()) > 0) {
                    player.sendMessage(ChatColor.RED + "You do not have enough money to make this bet!");
                    return;
                }
                tf.runBet(player);
            }
        };
        guiPage.identifier = "tf";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dToken Flips");
        guiPage.onCloseGoToMenu = "gamble";
        guiPage.register();
    }
    //Placeholder menu for when the animation for a bet is running (CF)
    public static void animationCF() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                if (args.equals("h")) {
                    menuItems.set(13, GUI.createPlaceholderItem(CoinFlip.HEADS_ICON, ChatColor.WHITE + "" + ChatColor.BOLD + "Heads"));
                } else menuItems.set(13, GUI.createPlaceholderItem(CoinFlip.TAILS_ICON, ChatColor.WHITE + "" + ChatColor.BOLD + "Tails"));
            }
        };
        guiPage.identifier = "gambleAnimationCF";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dFlipping (CF)");
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Placeholder menu for when the animation for a bet is running (TF)
    public static void animationTF() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                if (args.equals("h")) {
                    menuItems.set(13, GUI.createPlaceholderItem(TokenFlip.HEADS_ICON, ChatColor.WHITE + "" + ChatColor.BOLD + "Heads"));
                } else menuItems.set(13, GUI.createPlaceholderItem(TokenFlip.TAILS_ICON, ChatColor.WHITE + "" + ChatColor.BOLD + "Tails"));
            }
        };
        guiPage.identifier = "gambleAnimationTF";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dFlipping (TF)");
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.menuItems.add(GUI.createPlaceholderItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + ""));
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
}
