package me.staticstudios.prisons.rankup;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.rankup.RankUpPrices;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RankUp {
    public static BigInteger calculatePriceToRankUpTo(PlayerData playerData, int rankToGoTo) {
        BigInteger price = new BigInteger("0");
        BigInteger offset;
        if (playerData.getPrestige().equals(BigInteger.ZERO)) {
            offset = BigInteger.ONE;
        } else offset = playerData.getPrestige().divide(BigInteger.TEN);
        for (int i = playerData.getMineRank() + 1; i <= rankToGoTo; i++) {
            price = price.add(RankUpPrices.getBaseRankUpPriceForRank(i));
        }
        price = price.multiply(offset);
        return price;
    }
}
