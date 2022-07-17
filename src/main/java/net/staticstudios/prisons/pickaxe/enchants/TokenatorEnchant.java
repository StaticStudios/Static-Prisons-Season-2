package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.math.BigInteger;

public class TokenatorEnchant extends BaseEnchant {
    public TokenatorEnchant() {
        super("tokenator", "&6&lTokenator", 5000, BigInteger.valueOf(450), "&7Increases the chance to find tokens while mining");
    }

    public void onBlockBreak(PrisonBlockBroken bb) {
        int chance = (PrisonUtils.randomInt(1, 350 - bb.pickaxe.getEnchantLevel(ENCHANT_ID) / 25)); //Max level requires 150 blocks on average
        if (chance == 1) bb.totalTokensGained += PrisonUtils.randomInt(200, 800);
    }
}
