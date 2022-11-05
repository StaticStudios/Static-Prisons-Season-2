package net.staticstudios.prisons.fishing;

import org.bukkit.inventory.ItemStack;

public record FishingReward(CaughtType type, long playerXp, long tokens, long shards, ItemStack item) {
}
