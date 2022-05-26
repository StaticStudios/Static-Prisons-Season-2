package net.staticstudios.prisons.newAuctionHouse;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        File folder = new File(StaticPrisons.getInstance().getDataFolder(), "auctionHouse");
        folder.mkdir();
        File playerFile = new File(folder, playerUUID + ".yml");
        if (!playerFile.exists()) return expiredAuctions;
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(playerFile);
        fileData.addDefault("expiredAuctions", new ArrayList<String>());
        for (String str : fileData.getStringList("expiredAuctions")) expiredAuctions.add(new ExpiredAuction(playerUUID, str));
        return expiredAuctions;
    }

    public ExpiredAuction(Auction auction) {
        encodedItemStack = toBase64(auction.item());
        ownerUUID = auction.owner();
    }

    public ExpiredAuction(UUID owner, String encodedItemStack) {
        this.ownerUUID = owner;
        this.encodedItemStack = encodedItemStack;
    }

    public ItemStack getItem() {
        return fromBase64(encodedItemStack);
    }

    public void removeFromFile() {
        File folder = new File(StaticPrisons.getInstance().getDataFolder(), "auctionHouse");
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
        File folder = new File(StaticPrisons.getInstance().getDataFolder(), "auctionHouse");
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




    static String toBase64(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(item);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static ItemStack fromBase64(String data) {
        try {
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));
            ItemStack item = (ItemStack) dataInput.readObject();
            dataInput.close();
            return item;
        } catch (ClassNotFoundException e) {
            try {
                throw new IOException("Unable to decode class type.", e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
