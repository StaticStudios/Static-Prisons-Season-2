package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;

public class XPFinderEnchant extends BaseEnchant {
    public XPFinderEnchant() {
        super("xpFinder", "&a&lXP Finder", 1000, 1000, "&7Increase the chance to find XP whilst mining");
        setPickaxeLevelRequirement(999);

        setUseChances(true);
        setDefaultPercentChance(1d / 250 * 100); //1 out of 250
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (!activate(blockBreak.getPickaxe())) return;
        int xpFound = Math.max(1, PrisonUtils.randomInt(blockBreak.getPickaxe().getEnchantLevel(this) / 10, 100));
        blockBreak.getPlayerData().addPlayerXP(xpFound);
        blockBreak.messagePlayer(DISPLAY_NAME + " &8&l>> &fFound " + PrisonUtils.addCommasToNumber(xpFound) + " experience!");

    }
}
