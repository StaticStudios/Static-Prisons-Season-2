package net.staticstudios.prisons.newAuctionHouse;

import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.UUID;

public record Auction(UUID id, ItemStack item, UUID owner, long expireAt, BigInteger price) {
}
