package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.PrisonUtils;

public class TokenatorEnchant extends BaseEnchant {
    public TokenatorEnchant() {
        super("tokenator", "&6&lTokenator", 5000, 1250, "&7Increases the chance to find tokens while mining");

        setTiers(
                new EnchantTier(100, 0),
                new EnchantTier(200, 2),
                new EnchantTier(300, 2),
                new EnchantTier(400, 2),
                new EnchantTier(500, 2),
                new EnchantTier(750, 3),
                new EnchantTier(1000, 3),
                new EnchantTier(1250, 3),
                new EnchantTier(1500, 4),
                new EnchantTier(1750, 4),
                new EnchantTier(2000, 4),
                new EnchantTier(2250, 5),
                new EnchantTier(2500, 5),
                new EnchantTier(2750, 6),
                new EnchantTier(3000, 7),
                new EnchantTier(3250, 8),
                new EnchantTier(3500, 9),
                new EnchantTier(3750, 10),
                new EnchantTier(4000, 11),
                new EnchantTier(4250, 12),
                new EnchantTier(4500, 13),
                new EnchantTier(4750, 14),
                new EnchantTier(5000, 15)
        );

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
