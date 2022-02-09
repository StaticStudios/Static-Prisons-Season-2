package me.staticstudios.prisons.auctionHouse;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

public class Auction {
    public static final long SECONDS_FOR_AUCTIONS_TO_STAY_LIVE = 60 * 60 * 24 * 3;
    final UUID uuid;
    final UUID ownerUUID;
    final ItemStack item;
    final BigInteger price;
    final long expiresAt; //Stored in seconds
    public UUID getUuid() {
        return uuid;
    }
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    public ItemStack getItem() {
        return item;
    }
    public BigInteger getPrice() {
        return price;
    }
    public long getExpiresAt() {
        return expiresAt;
    }
    public Auction(UUID ownerUUID, ItemStack item, BigInteger price) {
        uuid = UUID.randomUUID();
        this.ownerUUID = ownerUUID;
        this.item = item;
        this.price = price;
        expiresAt = Instant.now().getEpochSecond() + SECONDS_FOR_AUCTIONS_TO_STAY_LIVE;
    }
}
