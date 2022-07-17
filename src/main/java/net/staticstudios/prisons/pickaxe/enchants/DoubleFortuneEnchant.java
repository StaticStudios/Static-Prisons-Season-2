package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.math.BigInteger;

public class DoubleFortuneEnchant extends BaseEnchant {
    public DoubleFortuneEnchant() {
        super("doubleFortune", "&d&lOre Splitter", 1000, BigInteger.valueOf(5000), "&7Chance to get 2x blocks from fortune");
        setPickaxeLevelRequirement(25);
    }
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (PrisonUtils.randomInt(0, MAX_LEVEL) < bb.pickaxe.getEnchantLevel(ENCHANT_ID))
        bb.blocksBrokenMultiplier *= 2;
    }
}
