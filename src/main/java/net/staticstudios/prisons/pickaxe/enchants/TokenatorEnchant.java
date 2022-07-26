package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.math.BigInteger;

public class TokenatorEnchant extends BaseEnchant {
    public TokenatorEnchant() {
        super("tokenator", "&6&lTokenator", 5000, BigInteger.valueOf(1250), "&7Increases the chance to find tokens while mining");

        setUseChances(true);
        setDefaultPercentChance(1d / 400 * 100); //1 out of 400
        setPercentChancePerLevel((1d / 200 * 100 - getDefaultPercentChance()) / MAX_LEVEL); //it will activate 1 out of 200 times at max level
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (activate(blockBreak.getPickaxe())) {
            blockBreak.getStats().setTokensEarned(blockBreak.getStats().getTokensEarned() + PrisonUtils.randomInt(200, 800)); //Average of 500 tokens per block
        }
        blockBreak.addAfterProcess(bb -> {
            if (bb.getStats().getTokensEarned() > 0) {
                bb.messagePlayer(DISPLAY_NAME + " &8&l>> &fFound " + PrisonUtils.addCommasToNumber((long) (bb.getStats().getTokensEarned() * bb.getStats().getTokenMultiplier())) + " tokens!");
            }
        });
    }
}
