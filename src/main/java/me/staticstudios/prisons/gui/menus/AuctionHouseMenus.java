package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.auctionHouse.Auction;
import me.staticstudios.prisons.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.data.PlayerBackpack;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.stringtemplate.v4.ST;

import java.math.BigInteger;
import java.util.*;

public class AuctionHouseMenus {
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            int page;
            @Override
            public void onOpen(Player player) {
                page = Integer.parseInt(args);
                int amountPerPage = 9 * 5 - 1;
                int startIndex = page * amountPerPage;
                if (page != 0) startIndex += 1;
                int endIndex;
                if (AuctionHouseManager.auctions.size() < startIndex + amountPerPage - 1) {
                    endIndex = AuctionHouseManager.auctions.size() - 1;
                } else endIndex = startIndex + amountPerPage;
                menuItems = new ArrayList<>();
                for (int i = startIndex; i < endIndex + 1; i++) {
                    List<UUID> list = Arrays.asList(AuctionHouseManager.auctions.keySet().toArray(new UUID[0]));
                    Collections.reverse(list);
                    Auction auction = AuctionHouseManager.auctions.get(list.get(i));
                    menuItems.add(createAuctionItem(player, auction));
                }
                while (menuItems.size() < 9 * 5) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
                for (int i = 0; i < 8; i++) {
                    menuItems.add(GUI.createDarkGrayPlaceholderItem());
                }
                menuItems.set(49, GUI.createPlaceholderItem(Material.PAPER, ChatColor.AQUA + "Current Page: " + ChatColor.WHITE + (page + 1)));
                if (AuctionHouseManager.auctions.size() > (page + 1) * amountPerPage) {
                    //add next page button
                    menuItems.set(50, GUI.createMenuItem(identifier, Material.ARROW, ChatColor.GREEN + "Go to next page"));
                }
                if (page > 0) {
                    //add page back button
                    menuItems.set(48, GUI.createMenuItem(identifier, Material.ARROW, ChatColor.GREEN + "Go to previous page"));
                }
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (e.getSlot() >= 48 && e.getSlot() <= 50) return;
                UUID auctionUUID = UUID.fromString(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING));
                AuctionHouseManager.expireAllExpiredAuctions();
                if (!AuctionHouseManager.checkIfAuctionExists(auctionUUID)) {
                    player.sendMessage(ChatColor.RED + "This auction no longer exists!");
                    open(player);
                    return;
                }
                Auction auction = AuctionHouseManager.getAuction(auctionUUID);
                if (auction.getOwnerUUID().equals(player.getUniqueId())) {
                    player.sendMessage(ChatColor.AQUA + "You have just reclaimed one of your auctions!");
                    Utils.addItemToPlayersInventoryAndDropExtra(player, auction.getItem());
                    AuctionHouseManager.removeAuction(auctionUUID);
                    open(player);
                    return;
                }
                if (playerData.getMoney().compareTo(auction.getPrice()) < 1) {
                    player.sendMessage(ChatColor.RED + "You do not have enough money to buy this! This item costs: $" + Utils.addCommasToBigInteger(auction.getPrice()));
                    open(player);
                    return;
                }
                if (Bukkit.getPlayer(auction.getOwnerUUID()) != null) Bukkit.getPlayer(auction.getOwnerUUID()).sendMessage(ChatColor.GREEN + player.getName() + " has just bought one of your auctions! They bought: " + ChatColor.WHITE + Utils.getPrettyItemName(auction.getItem()) + ChatColor.GREEN + " for $" + Utils.addCommasToBigInteger(auction.getPrice()));
                player.sendMessage(ChatColor.AQUA + "You have just purchased an auction! You purchased " + ChatColor.WHITE + Utils.getPrettyItemName(auction.getItem()) + ChatColor.AQUA + " for $" + Utils.addCommasToBigInteger(auction.getPrice()));
                playerData.removeMoney(auction.getPrice());
                new PlayerData(auction.getOwnerUUID()).addMoney(auction.getPrice());
                Utils.addItemToPlayersInventoryAndDropExtra(player, auction.getItem());
                AuctionHouseManager.removeAuction(auctionUUID);
                open(player);
            }
            @Override
            public void item48Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                args = (page - 1) + "";
                open(player);
            }
            @Override
            public void item50Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                args = (page + 1) + "";
                open(player);
            }

            ItemStack createAuctionItem(Player player, Auction auction) {
                List<String> lore = new ArrayList<>();
                if (auction.getItem().hasItemMeta() && auction.getItem().getItemMeta().hasLore()) lore = auction.getItem().getItemMeta().getLore();
                lore.add("");
                lore.add(ChatColor.GREEN + "Sold By: " + ChatColor.WHITE + new ServerData().getPlayerNameFromUUID(auction.getOwnerUUID().toString()));
                lore.add(ChatColor.GREEN + "Price: " + ChatColor.WHITE + "$" + Utils.prettyNum(auction.getPrice()));
                lore.add("");
                if (auction.getOwnerUUID().equals(player.getUniqueId())) {
                    lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click this to reclaim this!");
                } else lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to buy!");
                ItemStack item;
                if (auction.getItem().hasItemMeta() && auction.getItem().getItemMeta().hasEnchants()) {
                    item = GUI.createEnchantedMenuItem(identifier, auction.getItem().getType(), Utils.getPrettyItemName(auction.getItem()), lore.toArray(new String[0]));
                } else item = GUI.createMenuItem(identifier, auction.getItem().getType(), Utils.getPrettyItemName(auction.getItem()), lore.toArray(new String[0]));
                ItemMeta meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, auction.getUuid().toString());
                item.setItemMeta(meta);
                item.setAmount(auction.getItem().getAmount());
                return item;
            }
        };
        guiPage.identifier = "auctionHouse";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dAuction House");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
