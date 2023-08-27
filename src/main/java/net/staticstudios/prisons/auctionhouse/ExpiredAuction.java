package net.staticstudios.prisons.auctionhouse;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.ItemUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpiredAuction {

    public UUID ownerUUID;
    public String encodedItemStack;

    public static List<ExpiredAuction> getPlayerExpiredAuctions(UUID playerUUID) {
        List<ExpiredAuction> expiredAuctions = new ArrayList<>();
        File folder = new File(StaticPrisons.getInstance().getDataFolder(), "data/auctionHouse");
        File playerFile = new File(folder, playerUUID + ".yml");
        if (!playerFile.exists()) return expiredAuctions;
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(playerFile);
        fileData.addDefault("expiredAuctions", new ArrayList<String>());
        for (String str : fileData.getStringList("expiredAuctions"))
            expiredAuctions.add(new ExpiredAuction(playerUUID, str));
        return expiredAuctions;
    }

    public static void clearPlayerExpiredAuctions(UUID playerUUID) {
        new File(StaticPrisons.getInstance().getDataFolder(), "data/auctionHouse/" + playerUUID + ".yml").delete();
    }

    public ExpiredAuction(Auction auction) {
        encodedItemStack = ItemUtils.toBase64(auction.item());
        ownerUUID = auction.owner();
    }

    public ExpiredAuction(UUID owner, String encodedItemStack) {
        this.ownerUUID = owner;
        this.encodedItemStack = encodedItemStack;
    }

    public ItemStack getItem() {
        return (ItemStack) ItemUtils.fromBase64(encodedItemStack);
    }

    public void removeFromFile() {
        File folder = new File(StaticPrisons.getInstance().getDataFolder(), "data/auctionHouse");
        folder.mkdir();
        File playerFile = new File(folder, ownerUUID + ".yml");
        if (!playerFile.exists()) return;
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(playerFile);
        fileData.addDefault("expiredAuctions", new ArrayList<String>());
        List<String> encodedItems = fileData.getStringList("expiredAuctions");
        encodedItems.remove(encodedItemStack);
        if (encodedItems.isEmpty()) {
            playerFile.delete();
            return;
        }
        fileData.set("expiredAuctions", encodedItems);
        try {
            fileData.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        File folder = new File(StaticPrisons.getInstance().getDataFolder(), "data/auctionHouse");
        folder.mkdir();
        File playerFile = new File(folder, ownerUUID + ".yml");
        try {
            if (!playerFile.exists()) playerFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(playerFile);
        fileData.addDefault("expiredAuctions", new ArrayList<String>());
        List<String> encodedItems = fileData.getStringList("expiredAuctions");
        encodedItems.add(encodedItemStack);
        fileData.set("expiredAuctions", encodedItems);
        try {
            fileData.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
