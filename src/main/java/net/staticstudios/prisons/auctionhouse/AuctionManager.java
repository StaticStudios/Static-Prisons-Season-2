package net.staticstudios.prisons.auctionhouse;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.auctionhouse.commands.AuctionHouseCommand;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionManager {

    public static final String AH_PREFIX = ChatColor.translateAlternateColorCodes('&', "&a&lAuction House &8&l>> &r");
    public static final int SEC_TO_LIVE = 2 * 24 * 3600;
    public static final Material[] BLACKLISTED_TYPES = {
            Material.AIR
    };

    public static List<Auction> auctions = new ArrayList<>();

    public static void init() {
        CommandManager.registerCommand("auctionhouse", new AuctionHouseCommand());
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "data/auction_house.yml");
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(file);
        auctions.clear();
        if (fileData.getConfigurationSection("auctions") == null) return;
        for (String key : fileData.getConfigurationSection("auctions").getKeys(false)) {
            auctions.add(new Auction(
                    UUID.fromString(key),
                    (ItemStack) ItemUtils.fromBase64(fileData.getString("auctions." + key + ".item")),
                    UUID.fromString(fileData.getString("auctions." + key + ".owner")),
                    fileData.getLong("auctions." + key + ".expireAt"),
                    fileData.getLong("auctions." + key + ".price")
            ));
        }
    }

    public static int getMaxAuctionsPerPlayer(Player player) {
        PlayerData playerData = new PlayerData(player);
        int amount = 3;
        switch (playerData.getPlayerRank()) {
            case "warrior" -> amount = 4;
            case "master" -> amount = 6;
            case "mythic" -> amount = 9;
            case "static" -> amount = 12;
            case "staticp" -> amount = 15;
        }
        return amount;
    }


    public static boolean createAuction(Player player, ItemStack item, long price) {
        int playerAuctions = 0;
        for (Auction auction : auctions) {
            if (auction.owner().equals(player.getUniqueId())) playerAuctions++;
        }
        for (Material blacklisted : BLACKLISTED_TYPES) {
            if (item.getType().equals(blacklisted)) {
                player.sendMessage(AH_PREFIX + ChatColor.RED + "You cannot auction this item");
                return false;
            }
        }
        int maxAuctions = getMaxAuctionsPerPlayer(player);
        if (playerAuctions >= maxAuctions) {
            player.sendMessage(AH_PREFIX + ChatColor.RED + "You already have the max amount of auctions on the auction house, you cannot have more than " + maxAuctions + " active auctions at a time");
            return false;
        }
        auctions.add(0, new Auction(UUID.randomUUID(), item.clone(), player.getUniqueId(), Instant.now().getEpochSecond() + SEC_TO_LIVE, price));
        player.sendMessage(AH_PREFIX + "You auctioned an item, view it in the Auction House. " + ChatColor.GRAY + ChatColor.ITALIC + "/ah");
        return true;
    }

    public static boolean attemptToBuyAuction(Auction auction, Player player) {
        if (!auctions.contains(auction)) {
            player.sendMessage(ChatColor.RED + "Something went wrong...");
            return false;
        }
        PlayerData playerData = new PlayerData(player);
        if (auction.owner().equals(player.getUniqueId())) {
            auctions.remove(auction);
            PlayerUtils.addToInventory(player, auction.item());
            player.sendMessage(AH_PREFIX + "You reclaimed " + auction.item().getAmount() + "x " + ItemUtils.getPrettyItemName(auction.item()));
            return true;
        }
        if (playerData.getMoney() < auction.price()) {
            player.sendMessage(ChatColor.RED + "You do not have enough money to buy this! Costs: $" + PrisonUtils.addCommasToNumber(auction.price()));
            return false;
        }
        //Player has enough money to buy the auction
        auctions.remove(auction);
        playerData.removeMoney(auction.price());
        new PlayerData(auction.owner()).addMoney(auction.price());
        PlayerUtils.addToInventory(player, auction.item());
        if (Bukkit.getPlayer(auction.owner()) != null) {
            Bukkit.getPlayer(auction.owner()).sendMessage(AH_PREFIX +
                    player.getName() + " bought " + auction.item().getAmount() + "x " + ItemUtils.getPrettyItemName(auction.item()) + ChatColor.WHITE + " from you for " + ChatColor.GREEN + "$" + PrisonUtils.addCommasToNumber(auction.price()));
        }
        player.sendMessage(AH_PREFIX + "You bought " + auction.item().getAmount() + "x " + ItemUtils.getPrettyItemName(auction.item()) + ChatColor.WHITE + " from " + ServerData.PLAYERS.getName(auction.owner()) + " for " + ChatColor.GREEN + "$" + PrisonUtils.addCommasToNumber(auction.price()));
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
            if (player != null) player.sendMessage(ChatColor.RED + "One of your auctions has expired");
        }
    }

    public static void reclaimExpiredAuctions(Player player) {
        List<ExpiredAuction> expiredAuctions = ExpiredAuction.getPlayerExpiredAuctions(player.getUniqueId());
        if (expiredAuctions.isEmpty()) {
            player.sendMessage(AH_PREFIX + ChatColor.RED + "You do not have any auction(s) to claim");
            return;
        }
        for (ExpiredAuction expiredAuction : expiredAuctions)
            PlayerUtils.addToInventory(player, expiredAuction.getItem());
        ExpiredAuction.clearPlayerExpiredAuctions(player.getUniqueId());
        player.sendMessage(AH_PREFIX + "You claimed all of your expired auctions");
    }

    public static void saveAllAuctions() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> saveAllAuctions(new ArrayList<>(auctions)));
    }

    public static void saveAllAuctionsSync() {
        saveAllAuctions(auctions);
    }

    private static void saveAllAuctions(List<Auction> auctions) {
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "data/auction_house.yml");
        FileConfiguration fileData = new YamlConfiguration();
        for (Auction auction : auctions) {
            fileData.set("auctions." + auction.id().toString() + ".item", ItemUtils.toBase64(auction.item()));
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


}
