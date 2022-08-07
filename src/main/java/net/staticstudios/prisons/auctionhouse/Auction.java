package net.staticstudios.prisons.auctionhouse;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public record Auction(UUID id, ItemStack item, UUID owner, long expireAt, long price) {}
