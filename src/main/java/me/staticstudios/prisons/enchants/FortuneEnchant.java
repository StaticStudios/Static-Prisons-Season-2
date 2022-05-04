package me.staticstudios.prisons.enchants;

import me.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import me.staticstudios.prisons.enchants.handler.BaseEnchant;

import java.math.BigInteger;

public class FortuneEnchant extends BaseEnchant {
    public FortuneEnchant() {
        super("fortune", "&b&lFortune", 15000, BigInteger.valueOf(200), "&7Increase your blocks from mining");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        bb.blocksBrokenMultiplier *= bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        bb.player.sendMessage(bb.pickaxe.getEnchantLevel(ENCHANT_ID) + "");
    }

    @Override
    public void onPickaxeHeld(PrisonBlockBroken bb) {

    }

    @Override
    public void onPickaxeUnHeld(PrisonBlockBroken bb) {

    }
}
