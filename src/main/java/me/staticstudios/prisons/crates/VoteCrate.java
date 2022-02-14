package me.staticstudios.prisons.crates;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoteCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), 6, 100, 0);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(CustomItems.getCommonCrateKey(1), 5),
            new CrateReward(CustomItems.getCommonCrateKey(4), 15),
            new CrateReward(CustomItems.getRareCrateKey(2), 10),
            new CrateReward(CustomItems.getRareCrateKey(3), 5),
            new CrateReward(CustomItems.getEpicCrateKey(1), 7.5),
            new CrateReward(CustomItems.getEpicCrateKey(4), 2.5),
            new CrateReward(CustomItems.getLegendaryCrateKey(1), 5),
            new CrateReward(CustomItems.getLegendaryCrateKey(2), 5),
            new CrateReward(CustomItems.getLegendaryCrateKey(2), 4.4),
            new CrateReward(CustomItems.getStaticCrateKey(1), 2.5),
            new CrateReward(CustomItems.getStaticCrateKey(2), 2.5),
            new CrateReward(CustomItems.getStaticCrateKey(5), 0.5),
            new CrateReward(CustomItems.getStaticpCrateKey(1), 0.075),
            new CrateReward(CustomItems.getStaticpCrateKey(3), 0.025),
            new CrateReward(CustomItems.getValueCrateKey(1), 5),
            new CrateReward(CustomItems.getValueCrateKey(3), 5),
            new CrateReward(CustomItems.getValueCrateKey(5), 5),
            new CrateReward(CustomItems.getKitCrateKey(1), 2.5),
            new CrateReward(CustomItems.getPickaxeCrateKey(1), 10),
            new CrateReward(CustomItems.getPickaxeCrateKey(2), 5),
            new CrateReward(CustomItems.getPickaxeCrateKey(3), 2.5),
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
