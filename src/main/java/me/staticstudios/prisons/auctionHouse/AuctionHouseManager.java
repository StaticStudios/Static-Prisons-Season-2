package me.staticstudios.prisons.auctionHouse;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            if (Bukkit.getPlayer(getAuction(uuid).getOwnerUUID()) != null) Bukkit.getPlayer(getAuction(uuid).getOwnerUUID()).sendMessage(ChatColor.AQUA + "One of your auctions (" + ChatColor.WHITE + Utils.getPrettyItemName(getAuction(uuid).getItem()) + ChatColor.AQUA + ") has expired!");
            PlayerData playerData = new PlayerData(getAuction(uuid).getOwnerUUID());
            playerData.addExpiredAuction(getAuction(uuid).getItem());
            removeAuction(uuid);
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

    public static void saveAllAuctions() {
        List<SerializableAuctionItem> list = new ArrayList<>();
        for (UUID key : auctions.keySet()) {
            list.add(SerializableAuctionItem.fromAuction(auctions.get(key)));
        }
        Utils.writeToAFile("data/auctionHouse.json", new Genson().serialize(list, new GenericType<List<SerializableAuctionItem>>(){}));
        Utils.writeToAFile("data/auctionHouse-playerAuctions.json", new Genson().serialize(playerAuctionUUIDs, new GenericType<Map<UUID, List<UUID>>>(){}));
    }
    public static void loadAllAuctions() {
        try {
            List<SerializableAuctionItem> list = new Genson().deserialize(Utils.getFileContents("data/auctionHouse.json"), new GenericType<>() {});
            for (SerializableAuctionItem s : list) {
                auctions.put(s.uuid, SerializableAuctionItem.toAuction(s));
            }
            playerAuctionUUIDs = new Genson().deserialize(Utils.getFileContents("data/auctionHouse-playerAuctions.json"), new GenericType<>() {});
        } catch (Exception e) {
            e.printStackTrace();
            auctions = new LinkedHashMap<>();
        }
    }

}
