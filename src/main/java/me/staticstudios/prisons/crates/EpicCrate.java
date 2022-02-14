package me.staticstudios.prisons.crates;

import me.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EpicCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), 2, 100, 0);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(new ItemStack(Material.DIRT), 50),
            new CrateReward(new ItemStack(Material.STONE), 50),
    };

    public static void open(Player player) {
        //Supports up to 3 decimal places
        int randomChance = Utils.randomInt(1, 100 * 1000);
        ItemStack reward = new ItemStack(Material.AIR);
        int chances = 0;
        for (CrateReward crateReward : rewards) {
            if (randomChance >= chances && randomChance <= chances + crateReward.chance * 1000) {
                reward = crateReward.reward;
                break;
            } else chances += crateReward.chance * 1000;
        }
        LOCATION.getWorld().spawnParticle(Particle.TOTEM, LOCATION.getBlockX() + 0.5d, LOCATION.getBlockY() + 1, LOCATION.getBlockZ() + 0.5d, 100);
        player.sendMessage(ChatColor.AQUA + "You have just won " + ChatColor.WHITE + reward.getAmount() + "x " + Utils.getPrettyItemName(reward) + "!");
        Utils.addItemToPlayersInventoryAndDropExtra(player, new ItemStack(reward));
    }
}
