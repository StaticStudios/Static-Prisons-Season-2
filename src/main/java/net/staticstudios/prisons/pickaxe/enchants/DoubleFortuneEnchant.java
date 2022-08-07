package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;


public class DoubleFortuneEnchant extends BaseEnchant {
    public DoubleFortuneEnchant() {
        super("doubleFortune", "&d&lOre Splitter", 1000, 5000, "&7Chance to get 2x blocks from fortune");

        setTiers(
                new EnchantTier(5, 0),
                new EnchantTier(75, 1),
                new EnchantTier(150, 2),
                new EnchantTier(200, 3),
                new EnchantTier(300, 4),
                new EnchantTier(400, 5),
                new EnchantTier(500, 6),
                new EnchantTier(600, 7),
                new EnchantTier(750, 8),
                new EnchantTier(900, 9),
                new EnchantTier(1000, 10)
        );

        setUseChances(true);
        setDefaultPercentChance(0);
        setPercentChancePerLevel(1d / MAX_LEVEL * 100);
    }
    public void onBlockBreak(BlockBreak blockBreak) {
        if (!activate(blockBreak.getPickaxe())) return;
        blockBreak.getStats().setBlocksBrokenMultiplier(blockBreak.getStats().getBlocksBrokenMultiplier() * 2);
    }
}
