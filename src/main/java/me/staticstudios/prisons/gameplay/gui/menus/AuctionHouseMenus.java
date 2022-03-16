package me.staticstudios.prisons.gameplay.gui.menus;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.gameplay.auctionHouse.Auction;
import me.staticstudios.prisons.gameplay.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.data.serverData.ServerData;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.gui.GUIPage;
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

import java.util.*;

public class AuctionHouseMenus {
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            int page;
            @Override
            public void onOpen(Player player) {
                page = Integer.parseInt(args);
                menuItems = new ArrayList<>();
                int amountPerPage = 9 * 5;
                int startIndex = page * amountPerPage;
                int endIndex;
                if (AuctionHouseManager.auctions.size() <= startIndex + amountPerPage) {
                    endIndex = AuctionHouseManager.auctions.size() - 1;
                } else endIndex = startIndex + amountPerPage - 1;
                for (int i = startIndex; i < endIndex + 1; i++) {
                    List<UUID> list = Arrays.asList(AuctionHouseManager.auctions.keySet().toArray(new UUID[0]));
                    Collections.reverse(list);
                    Auction auction = AuctionHouseManager.auctions.get(list.get(i));
                    menuItems.add(createAuctionItem(player, auction));
                }
                while (menuItems.size() < 9 * 5) menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.ENDER_CHEST, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Claim Expired Auctions", ChatColor.GRAY + "Claim your previous expired auction(s)", ChatColor.GRAY + "You have " + new PlayerData(player).getExpiredAuctionsAmount() + " expired auction(s)"));
                for (int i = 0; i < 7; i++) menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.set(49, GUI.createPlaceholderItem(Material.PAPER, ChatColor.AQUA + "Current Page: " + ChatColor.WHITE + (page + 1)));
                if (AuctionHouseManager.auctions.size() > (page + 1) * amountPerPage) menuItems.set(50, GUI.createMenuItem(identifier, Material.ARROW, ChatColor.GREEN + "Go to next page"));
                if (page > 0) menuItems.set(48, GUI.createMenuItem(identifier, Material.ARROW, ChatColor.GREEN + "Go to previous page"));
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (e.getSlot() >= 45 && e.getSlot() <= 53) return;
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
                if (playerData.getMoney().compareTo(auction.getPrice()) < 0) {
                    player.sendMessage(ChatColor.RED + "You do not have enough money to buy this! This item costs: $" + Utils.addCommasToNumber(auction.getPrice()));
                    open(player);
                    return;
                }
                if (Bukkit.getPlayer(auction.getOwnerUUID()) != null) Bukkit.getPlayer(auction.getOwnerUUID()).sendMessage(ChatColor.GREEN + player.getName() + " has just bought one of your auctions! They bought: " + ChatColor.WHITE + Utils.getPrettyItemName(auction.getItem()) + ChatColor.GREEN + " for $" + Utils.addCommasToNumber(auction.getPrice()));
                player.sendMessage(ChatColor.AQUA + "You have just purchased an auction! You purchased " + ChatColor.WHITE + Utils.getPrettyItemName(auction.getItem()) + ChatColor.AQUA + " for $" + Utils.addCommasToNumber(auction.getPrice()));
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
            @Override
            public void item45Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                List<ItemStack> items = playerData.getExpiredAuctions();
                if (items.size() == 0) {
                    player.sendMessage(ChatColor.RED + "You do not have any auctions to claim!");
                    return;
                }
                for (ItemStack item : items) {
                    Utils.addItemToPlayersInventoryAndDropExtra(player, item);
                }
                playerData.setExpiredAuctions(new ArrayList<>());
                player.sendMessage(ChatColor.AQUA + "You've just claimed all of your expired auctions!");
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
