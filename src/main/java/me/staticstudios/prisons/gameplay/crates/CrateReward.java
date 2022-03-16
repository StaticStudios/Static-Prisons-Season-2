package me.staticstudios.prisons.gameplay.crates;

import org.bukkit.inventory.ItemStack;

public class CrateReward {
    public ItemStack reward;
    public double chance;
    public CrateReward(ItemStack reward, double chance) {
        this.reward = reward;
        this.chance = chance;
    }
}
