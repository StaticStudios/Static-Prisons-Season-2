package me.staticstudios.prisons.gameplay.crates;

import me.staticstudios.prisons.gameplay.customItems.CustomItems;
import me.staticstudios.prisons.gameplay.customItems.Vouchers;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LegendaryCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), -24, 80, -137);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(Vouchers.MONEY_POUCH_T1.item, 5),
            new CrateReward(Utils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 2), 5),
            new CrateReward(Utils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 3), 5),
            new CrateReward(Vouchers.MONEY_POUCH_T2.item, 2.5),
            new CrateReward(Vouchers.TOKEN_POUCH_T1.item, 2.5),
            new CrateReward(Utils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 2), 7),
            new CrateReward(Utils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 3), 7),
            new CrateReward(Vouchers.TOKEN_POUCH_T2.item, 5),
            new CrateReward(Vouchers.MULTI_POUCH_T1.item, 7),
            new CrateReward(Utils.setItemCount(Vouchers.MULTI_POUCH_T1.item, 2), 6),
            new CrateReward(Vouchers.MULTI_POUCH_T2.item, 7),
            new CrateReward(Utils.setItemCount(CustomItems.getMineBombTier2(), 5), 7.5),
            new CrateReward(Utils.setItemCount(CustomItems.getMineBombTier3(), 2), 7.5),
            new CrateReward(CustomItems.getMineBombTier4(), 5),
            new CrateReward(CustomItems.getCommonCrateKey(8), 1),
            new CrateReward(CustomItems.getRareCrateKey(3), 5),
            new CrateReward(CustomItems.getLegendaryCrateKey(2), 3),
            new CrateReward(CustomItems.getStaticCrateKey(1), 2),
            new CrateReward(CustomItems.getStaticCrateKey(2), 1),
            new CrateReward(CustomItems.getPickaxeCrateKey(1), 4),
            new CrateReward(CustomItems.getPickaxeCrateKey(2), 2.975),
            new CrateReward(CustomItems.getKitCrateKey(1), 2),
            new CrateReward(Vouchers.AUTO_SELL.item, 0.005),
            new CrateReward(Vouchers.WARRIOR_RANK.item, 0.005),
            new CrateReward(Vouchers.PRIVATE_MINE_T1.item, 0.01),
            new CrateReward(Vouchers.PRIVATE_MINE_T2.item, 0.005),
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
