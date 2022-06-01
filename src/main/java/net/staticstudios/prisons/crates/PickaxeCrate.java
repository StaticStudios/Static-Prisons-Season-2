package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PickaxeCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), 12, 80, -137);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(CustomItems.getPickaxeTier1(), 25),
            new CrateReward(CustomItems.getPickaxeTier2(), 20),
            new CrateReward(CustomItems.getPickaxeTier3(), 15),
            new CrateReward(CustomItems.getPickaxeTier4(), 12.5),
            new CrateReward(CustomItems.getPickaxeTier5(), 10),
            new CrateReward(CustomItems.getPickaxeTier6(), 7.5),
            new CrateReward(CustomItems.getPickaxeTier7(), 6),
            new CrateReward(CustomItems.getPickaxeTier8(), 2.5),
            new CrateReward(CustomItems.getPickaxeTier9(), 1),
            new CrateReward(CustomItems.getPickaxeTier10(), 0.5),
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
        Utils.Players.addToInventory(player, new ItemStack(reward));
    }
}
