package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
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

    public void onBlockBreak(BlockBreak blockBreak) {
        if (PrisonUtils.randomInt(1, 25000) != 1) return; //Chance to activate enchant


        int percentOfBackpackToFind = PrisonUtils.randomInt(1, 4);
        if (percentOfBackpackToFind > blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID) / (MAX_LEVEL / 4)) { //Make sure that the enchant level is high enough to find this much of the backpack, if not, find less
            percentOfBackpackToFind = Math.max(1, blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID) / (MAX_LEVEL / 4));
        }

        PlayerData playerData = blockBreak.getPlayerData();
        long oldSize = playerData.getBackpackSize();
        playerData.setBackpackSize(oldSize + oldSize * percentOfBackpackToFind / 100);

        blockBreak.messagePlayer(PREFIX +"You've found &b" + percentOfBackpackToFind + "% &fof your backpack's max size while mining! &a" + PrisonUtils.addCommasToNumber(oldSize) + " -> " + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()));
    }
}
