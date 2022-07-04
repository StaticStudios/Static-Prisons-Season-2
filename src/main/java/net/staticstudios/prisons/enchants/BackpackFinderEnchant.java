package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;

import java.math.BigInteger;

public class BackpackFinderEnchant extends BaseEnchant {
    public BackpackFinderEnchant() {
        super("backpackFinder", "&6&lDuffle Bag", 10000, BigInteger.valueOf(500), "&7Chance to find an additional 1% to 4% of", "&7your backpack's max space while mining");
        setPickaxeLevelRequirement(30);
        setPlayerLevelRequirement(15);
    }

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&6&lDuffle Bag &8&l>> &r");

    public void onBlockBreak(PrisonBlockBroken bb) {
        if (PrisonUtils.randomInt(1, 25000) != 1) return;
        int percent = PrisonUtils.randomInt(1, 4);
        if (percent > bb.pickaxe.getEnchantLevel(ENCHANT_ID) / (MAX_LEVEL / 4)) percent = Math.max(1, bb.pickaxe.getEnchantLevel(ENCHANT_ID) / (MAX_LEVEL / 4));
        PlayerData playerData = new PlayerData(bb.player);
        long oldSize = playerData.getBackpackSize();
        playerData.setBackpackSize(oldSize + oldSize * percent / 100);
        bb.player.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', "You've found &b" + percent + "% &fof your backpack's max size while mining! &a" + PrisonUtils.addCommasToNumber(oldSize) + " -> " + PrisonUtils.addCommasToNumber(playerData.getBackpackSize())));
    }
}
