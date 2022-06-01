package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoteCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), -54, 80, -125);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(CustomItems.getCommonCrateKey(1), 13.4),
            new CrateReward(CustomItems.getCommonCrateKey(2), 25),
            new CrateReward(CustomItems.getRareCrateKey(1), 20),
            new CrateReward(CustomItems.getRareCrateKey(2), 10),
            new CrateReward(CustomItems.getEpicCrateKey(1), 7.5),
            new CrateReward(CustomItems.getEpicCrateKey(2), 2.5),
            new CrateReward(CustomItems.getLegendaryCrateKey(1), 5),
            new CrateReward(CustomItems.getLegendaryCrateKey(2), 2),
            new CrateReward(CustomItems.getStaticCrateKey(1), 1.5),
            new CrateReward(CustomItems.getStaticCrateKey(2), 2.5),
            new CrateReward(CustomItems.getStaticCrateKey(3), 0.5),
            new CrateReward(CustomItems.getStaticpCrateKey(1), 0.075),
            new CrateReward(CustomItems.getStaticpCrateKey(3), 0.025),
            new CrateReward(CustomItems.getKitCrateKey(1), 2.5),
            new CrateReward(CustomItems.getPickaxeCrateKey(1), 5),
            new CrateReward(CustomItems.getPickaxeCrateKey(2), 2.5),
    };

    public static void open(Player player) {
        //Supports up to 3 decimal places
        int randomChance = PrisonUtils.randomInt(1, 100 * 1000);
        ItemStack reward = new ItemStack(Material.AIR);
        int chances = 0;
        for (CrateReward crateReward : rewards) {
            if (randomChance >= chances && randomChance <= chances + crateReward.chance * 1000) {
                reward = crateReward.reward;
                break;
            } else chances += crateReward.chance * 1000;
        }
        LOCATION.getWorld().spawnParticle(Particle.TOTEM, LOCATION.getBlockX() + 0.5d, LOCATION.getBlockY() + 1, LOCATION.getBlockZ() + 0.5d, 100);
        player.sendMessage(ChatColor.AQUA + "You have just won " + ChatColor.WHITE + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward) + "!");
        PrisonUtils.Players.addToInventory(player, new ItemStack(reward));
    }
}
