package me.staticstudios.prisons.auctionHouse;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

public class SerializableAuctionItem implements Serializable {
    final UUID uuid;
    final UUID ownerUUID;
    final String item;
    final BigInteger price;
    final long expiresAt;
    public SerializableAuctionItem(UUID uuid, UUID ownerUUID, ItemStack item, BigInteger price, long expiresAt) {
        this.uuid = uuid;
        this.ownerUUID = ownerUUID;
        this.item = itemStackArrayToBase64(item);
        this.price = price;
        this.expiresAt = expiresAt;
    }
    public static SerializableAuctionItem fromAuction(Auction auction) {
        return new SerializableAuctionItem(auction.getUuid(), auction.getOwnerUUID(), auction.getItem(), auction.getPrice(), auction.getExpiresAt());
    }


    static String itemStackArrayToBase64(ItemStack item) {
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
}
