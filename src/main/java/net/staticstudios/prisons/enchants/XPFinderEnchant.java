package net.staticstudios.prisons.enchants;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.math.BigInteger;

public class XPFinderEnchant extends BaseEnchant {
    public XPFinderEnchant() {
        super("xpFinder", "&a&lXP Finder", 1000, BigInteger.valueOf(1000), "&7Increase the chance to find XP whilst mining");
        setPickaxeLevelRequirement(30);
    }

    public void onBlockBreak(PrisonBlockBroken bb) {
        if (PrisonUtils.randomInt(1, 250) != 1) return;
        int enchLevel = bb.pickaxe.getEnchantLevel(this);
        int xpFound = Math.max(1, PrisonUtils.randomInt(enchLevel / 10, 100));
        new PlayerData(bb.player).addPlayerXP(xpFound);
//        bb.player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "+ " + PrisonUtils.addCommasToNumber(xpFound) + " Experience" + ChatColor.GRAY + ChatColor.ITALIC + " (XP Finder)");
        bb.player.sendMessage(ChatColor.translateAlternateColorCodes('&', DISPLAY_NAME + " &8&l>> &fFound " + PrisonUtils.addCommasToNumber(xpFound) + " experience!"));

    }
}
