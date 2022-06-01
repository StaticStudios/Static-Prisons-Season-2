package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EpicCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), -33, 80, -137);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(Vouchers.MONEY_POUCH_T1.item, 7),
            new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 2), 5),
            new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 3), 5),
            new CrateReward(Vouchers.TOKEN_POUCH_T1.item, 8),
            new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 2), 10),
            new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 3), 5),
            new CrateReward(Vouchers.MULTI_POUCH_T1.item, 5),
            new CrateReward(Vouchers.MULTI_POUCH_T2.item, 5),
            new CrateReward(PrisonUtils.setItemCount(CustomItems.getMineBombTier1(), 7), 10),
            new CrateReward(PrisonUtils.setItemCount(CustomItems.getMineBombTier2(), 3), 5),
            new CrateReward(CustomItems.getMineBombTier3(), 8),
            new CrateReward(CustomItems.getMineBombTier4(), 2),
            new CrateReward(CustomItems.getEpicCrateKey(2), 5),
            new CrateReward(CustomItems.getEpicCrateKey(3), 3),
            new CrateReward(CustomItems.getLegendaryCrateKey(1), 4.5),
            new CrateReward(CustomItems.getLegendaryCrateKey(2), 2),
            new CrateReward(CustomItems.getStaticCrateKey(1), 1),
            new CrateReward(CustomItems.getPickaxeCrateKey(1), 4),
            new CrateReward(CustomItems.getPickaxeCrateKey(2), 2.99),
            new CrateReward(CustomItems.getKitCrateKey(1), 2.5),
            new CrateReward(Vouchers.AUTO_SELL.item, 0.005),
            new CrateReward(Vouchers.PRIVATE_MINE_T1.item, 0.005),
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
