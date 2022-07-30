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
                new EnchantTier(150, 1),
                new EnchantTier(250, 1),
                new EnchantTier(400, 2),
                new EnchantTier(500, 2),
                new EnchantTier(750, 3),
                new EnchantTier(1000, 3),
                new EnchantTier(1250, 3),
                new EnchantTier(1500, 4),
                new EnchantTier(1750, 4),
                new EnchantTier(2000, 5),
                new EnchantTier(2250, 6),
                new EnchantTier(2500, 7)
                );
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        blockBreak.getStats().setBlocksBrokenMultiplier(blockBreak.getStats().getBlocksBrokenMultiplier() * blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID));
    }
}
