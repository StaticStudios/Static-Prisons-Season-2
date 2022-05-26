package net.staticstudios.prisons.newAuctionHouse;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

public class AuctionManager {

    public static final int MAX_AUCTIONS_PER_PLAYER = 3;
    public static final String AH_PREFIX = ChatColor.translateAlternateColorCodes('&', "&b[Auction House] &r");

    public static Map<UUID, List<Auction>> playerAuctions = new HashMap<>();
    public static List<Auction> auctions = new ArrayList<>();


    public static boolean attemptToBuyAuction(Auction auction, Player player) {
        if (!auctions.contains(auction)) {
            player.sendMessage(ChatColor.RED + "Something went wrong...");
            return false;
        }
        PlayerData playerData = new PlayerData(player);
        if (playerData.getMoney().compareTo(auction.price()) < 0) {
            player.sendMessage(ChatColor.RED + "You do not have enough money to buy this! Costs: $" + Utils.addCommasToNumber(auction.price()));
            return false;
        }
        //Player has enough money to buy the auction
        auctions.remove(auction);
        playerData.removeMoney(auction.price());
        new PlayerData(auction.owner()).addMoney(auction.price());
        Utils.addItemToPlayersInventoryAndDropExtra(player, auction.item());
        if (Bukkit.getPlayer(auction.owner()) != null) Bukkit.getPlayer(auction.owner()).sendMessage(AH_PREFIX + "" + player.getName() + " bought " + auction.item().getAmount() + "x " + Utils.getPrettyItemName(auction.item()) + ChatColor.WHITE + " from you for " + ChatColor.GREEN + "$" + Utils.addCommasToNumber(auction.price()));
        player.sendMessage(AH_PREFIX + "You bought " + auction.item().getAmount() + "x " + Utils.getPrettyItemName(auction.item()) + ChatColor.WHITE + " from " + ServerData.PLAYERS.getName(auction.owner()) + " for " + ChatColor.GREEN + "$" + Utils.addCommasToNumber(auction.price()));
        return true;
    }

    public static void updateExpiredAuctions() {
        List<Auction> expire = new ArrayList<>();
        for (Auction auction : auctions) if (auction.expireAt() <= Instant.now().getEpochSecond()) expire.add(auction);
        auctions.removeAll(expire);
        for (Auction auction : expire) {
            Player player = Bukkit.getPlayer(auction.owner());
            ExpiredAuction expiredAuction = new ExpiredAuction(auction);
            expiredAuction.saveToFile();
            if (player != null) {
                player.sendMessage(ChatColor.RED + "One of your auctions has expired");
                //TODO add a way to claim expired auctions
            }

        }
    }

    public static void saveAllAuctions() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), AuctionManager::saveAllAuctions);
    }

    public static void saveAllAuctionsSync() {
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "auctionHouse.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration fileData = new YamlConfiguration();
        for (Auction auction : auctions) {
            fileData.set("auctions." + auction.id().toString() + ".item", ExpiredAuction.toBase64(auction.item()));
            fileData.set("auctions." + auction.id() + ".owner", auction.owner().toString());
            fileData.set("auctions." + auction.id() + ".expireAt", auction.expireAt());
            fileData.set("auctions." + auction.id() + ".price", auction.price());
        }
        try {
            fileData.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadAllAuctions() {
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "auctionHouse.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(file);
        auctions.clear();
        if (fileData.getConfigurationSection("auctions") == null) return;
        for (String key : fileData.getConfigurationSection("auctions").getKeys(false)) {
            auctions.add(new Auction(
                    UUID.fromString(key),
                    ExpiredAuction.fromBase64(fileData.getString("auctions." + key + ".item")),
                    UUID.fromString(fileData.getString("auctions." + key + ".owner")),
                    fileData.getLong("auctions." + key + ".expireAt"),
                    BigInteger.valueOf(fileData.getLong("auctions." + key + ".price"))
            ));
        }
    }

}
