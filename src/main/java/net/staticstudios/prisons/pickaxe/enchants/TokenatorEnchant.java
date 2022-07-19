package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.math.BigInteger;

public class TokenatorEnchant extends BaseEnchant {
    public TokenatorEnchant() {
        super("tokenator", "&6&lTokenator", 5000, BigInteger.valueOf(450), "&7Increases the chance to find tokens while mining");
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        int chance = (PrisonUtils.randomInt(1, 350 - blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID) / 25)); //Max level requires 150 blocks on average
        if (chance == 1) {
            blockBreak.getStats().setTokensEarned(blockBreak.getStats().getTokensEarned() + PrisonUtils.randomInt(200, 800));
        }
        blockBreak.addAfterProcess(bb -> {
            if (bb.getStats().getTokensEarned() > 0) {
                bb.messagePlayer(DISPLAY_NAME + " &8&l>> &fFound " + PrisonUtils.addCommasToNumber((long) (bb.getStats().getTokensEarned() * bb.getStats().getTokenMultiplier())) + " tokens!");
            }
        });
    }
}
