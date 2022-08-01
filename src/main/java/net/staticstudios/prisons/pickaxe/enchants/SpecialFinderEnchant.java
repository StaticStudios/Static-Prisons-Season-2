package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SpecialFinderEnchant extends BaseEnchant {
    public SpecialFinderEnchant() {
        super("specialFinder", "&c&lMetal Detector", 5000, BigInteger.valueOf(400), "&7Find special items while mining");
        setPickaxeLevelRequirement(38);

        //20 Tiers
        setTiers(
                new EnchantTier(250, 0),
                new EnchantTier(500, 1),
                new EnchantTier(750, 2),
                new EnchantTier(1000, 3),
                new EnchantTier(1250, 4),
                new EnchantTier(1500, 5),
                new EnchantTier(1750, 6),
                new EnchantTier(2000, 7),
                new EnchantTier(2250, 8),
                new EnchantTier(2500, 9),
                new EnchantTier(2750, 10),
                new EnchantTier(3000, 11),
                new EnchantTier(3250, 12),
                new EnchantTier(3500, 13),
                new EnchantTier(3750, 14),
                new EnchantTier(4000, 15),
                new EnchantTier(4250, 16),
                new EnchantTier(4500, 17),
                new EnchantTier(4750, 18),
                new EnchantTier(5000, 19)
        );

        setUseChances(true);
        setDefaultPercentChance(1d / 10000 * 100); //1 out of 10,000
        setPercentChancePerLevel((1d / 3000 * 100 - getDefaultPercentChance()) / MAX_LEVEL); //it will activate 1 out of 3,000 times at max level
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.getPlayer() == null) return;
        ItemStack reward = new WeightedElements<ItemStack>()
                .add(Vouchers.getMultiplierNote(BigDecimal.valueOf(PrisonUtils.randomInt(12, 75)).divide(BigDecimal.valueOf(100)), PrisonUtils.randomInt(20, 120)), 50)
                .add(CustomItems.getMineBombTier1(), 25)
                .add(CustomItems.getMineBombTier2(), 15)
                .add(CustomItems.getMineBombTier3(), 5)
                .add(CustomItems.getMineBombTier4(), 5)
                .getRandom();
        blockBreak.messagePlayer(DISPLAY_NAME + " &8&l>> &fFound " + reward.getAmount() + "x " + PrisonUtils.Items.getPrettyItemName(reward) + "&f while mining!");
        PrisonUtils.Players.addToInventory(blockBreak.getPlayer(), reward);
    }
}
