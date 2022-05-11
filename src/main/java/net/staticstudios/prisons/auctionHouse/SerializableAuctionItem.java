package net.staticstudios.prisons.auctionHouse;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

public class SerializableAuctionItem implements Serializable {
    public SerializableAuctionItem() { //Constructor is needed for JSON deserialization
        uuid = null;
        ownerUUID = null;
        item = null;
        price = null;
        expiresAt = 0L;
    }
    final UUID uuid;
    final UUID ownerUUID;
    final String item;
    final BigInteger price;
    final long expiresAt;
    public SerializableAuctionItem(UUID uuid, UUID ownerUUID, ItemStack item, BigInteger price, long expiresAt) {
        this.uuid = uuid;
        this.ownerUUID = ownerUUID;
        this.item = itemStackToBase64(item);
        this.price = price;
        this.expiresAt = expiresAt;
    }
    public static SerializableAuctionItem fromAuction(Auction auction) {
        return new SerializableAuctionItem(auction.getUuid(), auction.getOwnerUUID(), auction.getItem(), auction.getPrice(), auction.getExpiresAt());
    }
    public static Auction toAuction(SerializableAuctionItem serializableAuctionItem) {
        return new Auction(serializableAuctionItem.uuid, serializableAuctionItem.ownerUUID, itemStackFromBase64(serializableAuctionItem.item), serializableAuctionItem.price, serializableAuctionItem.expiresAt);
    }
    public static String itemStackToBase64(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(item);
            dataOutput.close();
            String encodedLines = Base64Coder.encodeLines(outputStream.toByteArray());
            return encodedLines.replaceAll("\n", "?LB?").replace("\r", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ItemStack itemStackFromBase64(String data) {
        try {
            data = data.replace("?LB?", "\n");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
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
