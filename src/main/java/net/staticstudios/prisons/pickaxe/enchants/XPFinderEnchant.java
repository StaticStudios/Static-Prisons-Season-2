package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.PrisonUtils;

public class XPFinderEnchant extends BaseEnchant {
    public XPFinderEnchant() {
        super("xpFinder", "&a&lXP Finder", 3000, 2500, "&7Change to fine a lump-some of", "&7experience while mining");
        setPickaxeLevelRequirement(20);

        setTiers(
                new EnchantTier(1000, 0),
                new EnchantTier(2000, 25),
                new EnchantTier(3000, 50)
        );

        setUseChances(true);
        setDefaultPercentChance(1d / 5000 * 100); //1 out of 5000
        setPercentChancePerLevel((1d / 1000 * 100 - getDefaultPercentChance()) / MAX_LEVEL); //it will activate 1 out of 1000 times at max level
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (!activate(blockBreak.getPickaxe())) return;
        int xpFound = PrisonUtils.randomInt(1, 15_000);
        blockBreak.getPlayerData().addPlayerXP(xpFound);
        blockBreak.messagePlayer(DISPLAY_NAME + " &8&l>> &fFound " + PrisonUtils.addCommasToNumber(xpFound) + " experience!");
    }
}
