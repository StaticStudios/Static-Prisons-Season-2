package me.staticstudios.prisons.auctionHouse;

import com.google.gson.Gson;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

public class AuctionHouseManager {
    public static LinkedHashMap<UUID, Auction> auctions = new LinkedHashMap<>();
    public static Map<UUID, List<UUID>> playerAuctionUUIDs = new HashMap<>();
    public static Auction createAuction(Player player, ItemStack item, BigInteger price) {
        //Check if item is able to be auctioned
        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(ChatColor.RED + "You cannot auction this item!");
            return null;
        }
        if (price.compareTo(BigInteger.ZERO) < 1) {
            player.sendMessage(ChatColor.RED + "You cannot sell an item for this price!");
            return null;
        }
        Auction auction = new Auction(player.getUniqueId(), new ItemStack(item), price);
        auctions.put(auction.getUuid(), auction);
        if (!playerAuctionUUIDs.containsKey(player.getUniqueId())) playerAuctionUUIDs.put(player.getUniqueId(), new ArrayList<>());
        playerAuctionUUIDs.get(player.getUniqueId()).add(auction.getUuid());
        player.sendMessage(ChatColor.AQUA + "You have successfully auctioned an item!");
        item.setAmount(0);
        return auction;
    }
    public static boolean checkIfAuctionExists(UUID auctionUuid) {
        AuctionHouseManager.expireAllExpiredAuctions();
        return AuctionHouseManager.auctions.containsKey(auctionUuid);
    }
    public static boolean checkIfAuctionShouldExpire(UUID auctionUuid) {
        if (!auctions.containsKey(auctionUuid)) return false;
        return auctions.get(auctionUuid).getExpiresAt() <= Instant.now().getEpochSecond();
    }
    public static void expireAllExpiredAuctions() {
        for (UUID uuid : auctions.keySet()) {
            if (!checkIfAuctionShouldExpire(uuid)) continue;
            if (Bukkit.getPlayer(uuid) != null) Bukkit.getPlayer(uuid).sendMessage(ChatColor.AQUA + "One of your auctions (" + ChatColor.WHITE + Utils.getPrettyItemName(getAuction(uuid).getItem()) + ChatColor.AQUA + ") has expired!");
            removeAuction(uuid);

            //TODO: add to the player's expired auctions
        }
    }
    public static void removeAuction(UUID auctionUuid) {
        if (!auctions.containsKey(auctionUuid)) return;
        getPlayerAuctions(getAuction(auctionUuid).getOwnerUUID()).remove(auctionUuid);
        auctions.remove(auctionUuid);
    }
    public static Auction getAuction(UUID auctionUuid) {
        return auctions.get(auctionUuid);
    }
    public static List<UUID> getPlayerAuctions(UUID playerUuid) {
        return playerAuctionUUIDs.get(playerUuid);
    }


    //TODO: use YAML/JSON
    public static void saveAllAuctions() {
        Utils.writeToAFile("data/auctionHouse/all.json", new Gson().toJson(auctions));
    }
    public static void loadAllAuctions() {
        auctions = (LinkedHashMap<UUID, Auction>) new Gson().fromJson(Utils.getFileContents("data/auctionHouse/all.json"), HashMap.class);
    }

}
