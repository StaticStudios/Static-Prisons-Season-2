package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SpecialFinderEnchant extends BaseEnchant {
    public SpecialFinderEnchant() {
        super("specialFinder", "&c&lMetal Detector", 5000, BigInteger.valueOf(400), "&7Find special items while mining");
    }

    public void onBlockBreak(PrisonBlockBroken bb) {
        if (PrisonUtils.randomInt(0, 1750) != 1) return;
        int metalDetectorEnchant = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        if (PrisonUtils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) <= metalDetectorEnchant + MAX_LEVEL / 10) {
            ItemStack reward = CustomItems.getCommonCrateKey(1);
            switch (PrisonUtils.randomInt(0, 3)) {
                case 0, 1, 2 -> reward = Vouchers.getMultiplierNote(BigDecimal.valueOf(PrisonUtils.randomInt(12, 75)).divide(BigDecimal.valueOf(100)), PrisonUtils.randomInt(20, 120));
                case 3 -> {
                    switch (PrisonUtils.randomInt(1, 4)) {
                        case 1 -> reward = CustomItems.getMineBombTier1();
                        case 2 -> reward = CustomItems.getMineBombTier2();
                        case 3 -> reward = CustomItems.getMineBombTier3();
                        case 4 -> reward = CustomItems.getMineBombTier4();
                    }
                }
            }
            bb.player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Metal Detector] " + ChatColor.AQUA + "You found " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward) + ChatColor.AQUA + " while mining!");
            PrisonUtils.Players.addToInventory(bb.player, reward);
        }
    }
}