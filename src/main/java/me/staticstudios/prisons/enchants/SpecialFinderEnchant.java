package me.staticstudios.prisons.enchants;

import me.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.customItems.Vouchers;
import me.staticstudios.prisons.enchants.handler.BaseEnchant;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SpecialFinderEnchant extends BaseEnchant {
    public SpecialFinderEnchant() {
        super("specialFinder", "&c&lMetal Detector", 5000, BigInteger.valueOf(400), "&7Find special items while mining");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (Utils.randomInt(0, 1750) != 1) return;
        int metalDetectorEnchant = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        if (Utils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) <= metalDetectorEnchant + MAX_LEVEL / 10) {
            ItemStack reward = CustomItems.getCommonCrateKey(1);
            switch (Utils.randomInt(0, 3)) {
                case 0, 1, 2 -> reward = Vouchers.getMultiplierNote(BigDecimal.valueOf(Utils.randomInt(12, 75)).divide(BigDecimal.valueOf(100)), Utils.randomInt(20, 120));
                case 3 -> {
                    switch (Utils.randomInt(1, 4)) {
                        case 1 -> reward = CustomItems.getMineBombTier1();
                        case 2 -> reward = CustomItems.getMineBombTier2();
                        case 3 -> reward = CustomItems.getMineBombTier3();
                        case 4 -> reward = CustomItems.getMineBombTier4();
                    }
                }
            }
            bb.player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Metal Detector] " + ChatColor.AQUA + "You found " + reward.getAmount() + "x " + Utils.getPrettyItemName(reward) + ChatColor.AQUA + " while mining!");
            Utils.addItemToPlayersInventoryAndDropExtra(bb.player, reward);
        }
    }

    @Override
    public void onPickaxeHeld(PrisonBlockBroken bb) {

    }

    @Override
    public void onPickaxeUnHeld(PrisonBlockBroken bb) {

    }
}
