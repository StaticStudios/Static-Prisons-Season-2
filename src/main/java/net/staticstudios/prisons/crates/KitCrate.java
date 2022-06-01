package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), -54, 80, -134);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(Vouchers.KIT_TIER_1.item, 50),
            new CrateReward(Vouchers.KIT_TIER_2.item, 25),
            new CrateReward(Vouchers.KIT_TIER_3.item, 15),
            new CrateReward(Vouchers.KIT_TIER_4.item, 5),
            new CrateReward(Vouchers.KIT_TIER_5.item, 2.5),
            new CrateReward(Vouchers.KIT_TIER_6.item, 0.5),
            new CrateReward(Vouchers.KIT_POTIONS.item, 1),
            new CrateReward(Vouchers.KIT_WEAPONS.item, 1),
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
