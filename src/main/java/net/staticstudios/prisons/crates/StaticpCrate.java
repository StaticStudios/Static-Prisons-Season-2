package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaticpCrate {
    public static Location LOCATION = new Location(Bukkit.getWorld("world"), 3, 80, -137);
    public static CrateReward[] rewards = new CrateReward[] {
            new CrateReward(Utils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 4), 2),
            new CrateReward(Utils.setItemCount(Vouchers.MONEY_POUCH_T2.item, 3), 2),
            new CrateReward(Utils.setItemCount(Vouchers.MONEY_POUCH_T2.item, 4), 2.5),
            new CrateReward(Vouchers.MONEY_POUCH_T3.item, 6),
            new CrateReward(Utils.setItemCount(Vouchers.MONEY_POUCH_T3.item, 2), 2),
            new CrateReward(Utils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 4), 3),
            new CrateReward(Utils.setItemCount(Vouchers.TOKEN_POUCH_T2.item, 3), 4.5),
            new CrateReward(Utils.setItemCount(Vouchers.TOKEN_POUCH_T2.item, 4), 3),
            new CrateReward(Vouchers.TOKEN_POUCH_T3.item, 7),
            new CrateReward(Utils.setItemCount(Vouchers.TOKEN_POUCH_T3.item, 2), 2),
            new CrateReward(Vouchers.MULTI_POUCH_T1.item, 1),
            new CrateReward(Utils.setItemCount(Vouchers.MULTI_POUCH_T1.item, 3), 3),
            new CrateReward(Vouchers.MULTI_POUCH_T2.item, 2),
            new CrateReward(Utils.setItemCount(Vouchers.MULTI_POUCH_T2.item, 2), 2),
            new CrateReward(Vouchers.MULTI_POUCH_T3.item, 3),
            new CrateReward(Utils.setItemCount(Vouchers.MULTI_POUCH_T3.item, 2), 3),
            new CrateReward(Utils.setItemCount(CustomItems.getMineBombTier1(), 32), 2),
            new CrateReward(Utils.setItemCount(CustomItems.getMineBombTier3(), 4), 5),
            new CrateReward(Utils.setItemCount(CustomItems.getMineBombTier3(), 6), 5),
            new CrateReward(CustomItems.getMineBombTier4(), 2),
            new CrateReward(Utils.setItemCount(CustomItems.getMineBombTier4(), 2), 2),
            new CrateReward(Utils.setItemCount(CustomItems.getMineBombTier4(), 4), 1),
            new CrateReward(CustomItems.getLegendaryCrateKey(5), 8.5),
            new CrateReward(CustomItems.getStaticCrateKey(2), 10),
            new CrateReward(CustomItems.getStaticCrateKey(3), 9),
            new CrateReward(CustomItems.getStaticpCrateKey(2), 1.5),
            new CrateReward(CustomItems.getPickaxeCrateKey(3), 3),
            new CrateReward(CustomItems.getKitCrateKey(4), 1),
            new CrateReward(Vouchers.AUTO_SELL.item, 0.1),
            new CrateReward(Vouchers.WARRIOR_RANK.item, 0.1),
            new CrateReward(Vouchers.MASTER_RANK.item, 0.1),
            new CrateReward(Vouchers.MYTHIC_RANK.item, 0.1),
            new CrateReward(Vouchers.STATIC_RANK.item, 0.1),
            new CrateReward(Vouchers.STATICP_RANK.item, 0.4),
            new CrateReward(Vouchers.PRIVATE_MINE_T1.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T2.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T3.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T4.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T5.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T6.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T7.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T8.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T9.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T10.item, 0.1),
            new CrateReward(Vouchers.PRIVATE_MINE_T11.item, 0.1),
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
