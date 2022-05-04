package me.staticstudios.prisons.enchants;

import me.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import me.staticstudios.prisons.enchants.handler.BaseEnchant;
import me.staticstudios.prisons.mineBombs.MineBomb;
import me.staticstudios.prisons.utils.Utils;

import java.math.BigInteger;

public class ExplosionEnchant extends BaseEnchant {
    public ExplosionEnchant() {
        super("explosion", "&c&lExplosion", 5000, BigInteger.valueOf(450), "&7Change to explode part of a mine");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        int explosionLevel = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        if (Utils.randomInt(1, 4500 - (int) (explosionLevel / 3.75)) != 1) return;
        int radius = 5 + explosionLevel / 1500;
        radius += Utils.randomDouble(0, 0.4) * radius;
        bb.blockTypesBroken.putAll(new MineBomb(bb.blockLocation, radius).explode(bb.mine));
    }

    @Override
    public void onPickaxeHeld(PrisonBlockBroken bb) {

    }

    @Override
    public void onPickaxeUnHeld(PrisonBlockBroken bb) {

    }
}
