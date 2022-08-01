package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;

import java.math.BigInteger;

public class FortuneEnchant extends BaseEnchant {
    public FortuneEnchant() {
        super("fortune", "&b&lFortune", 2500, BigInteger.valueOf(1000), "&7Increase your blocks from mining");

        setTiers(
                new EnchantTier(50, 0),
                new EnchantTier(100, 1),
                new EnchantTier(150, 2),
                new EnchantTier(250, 3),
                new EnchantTier(400, 5),
                new EnchantTier(500, 6),
                new EnchantTier(750, 8),
                new EnchantTier(1000, 10),
                new EnchantTier(1250, 12),
                new EnchantTier(1500, 14),
                new EnchantTier(1750, 16),
                new EnchantTier(2000, 18),
                new EnchantTier(2250, 20),
                new EnchantTier(2500, 25)
                );
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        blockBreak.getStats().setBlocksBrokenMultiplier(blockBreak.getStats().getBlocksBrokenMultiplier() * blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID));
    }
}
