package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;

import java.math.BigInteger;

public class FortuneEnchant extends BaseEnchant {
    public FortuneEnchant() {
        super("fortune", "&b&lFortune", 25000, BigInteger.valueOf(200), "&7Increase your blocks from mining");
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        blockBreak.getStats().setBlocksBrokenMultiplier(blockBreak.getStats().getBlocksBrokenMultiplier() * blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID));
    }
}
