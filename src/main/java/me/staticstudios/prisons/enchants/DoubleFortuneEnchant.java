package me.staticstudios.prisons.enchants;

import me.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import me.staticstudios.prisons.enchants.handler.BaseEnchant;
import me.staticstudios.prisons.utils.Utils;

import java.math.BigInteger;

public class DoubleFortuneEnchant extends BaseEnchant {
    public DoubleFortuneEnchant() {
        super("doubleFortune", "&d&lOre Splitter", 1000, BigInteger.valueOf(5000), "&7Chance to get 2x blocks from fortune");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (Utils.randomInt(0, MAX_LEVEL) < bb.pickaxe.getEnchantLevel(ENCHANT_ID))
        bb.blocksBrokenMultiplier *= 2;
    }

    @Override
    public void onPickaxeHeld(PrisonBlockBroken bb) {

    }

    @Override
    public void onPickaxeUnHeld(PrisonBlockBroken bb) {

    }
}
