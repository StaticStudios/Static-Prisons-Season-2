package me.staticstudios.prisons.enchants;

import me.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import me.staticstudios.prisons.enchants.handler.BaseEnchant;
import me.staticstudios.prisons.utils.Utils;

import java.math.BigInteger;

public class TokenatorEnchant extends BaseEnchant {
    public TokenatorEnchant() {
        super("tokenator", "&e&lTokenator", 5000, BigInteger.valueOf(450), "&7Increases the chance to find tokens while mining");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        int chance = (Utils.randomInt(1, 350 - bb.pickaxe.getEnchantLevel(ENCHANT_ID) / 25)); //Max level requires 150 blocks on average
        if (chance == 1) bb.totalTokensGained += Utils.randomInt(200, 800);
    }

    @Override
    public void onPickaxeHeld(PrisonBlockBroken bb) {

    }

    @Override
    public void onPickaxeUnHeld(PrisonBlockBroken bb) {

    }
}
