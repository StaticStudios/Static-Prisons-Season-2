package net.staticstudios.prisons.rankup;

import net.staticstudios.prisons.data.PlayerData;

import java.math.BigInteger;

public class RankUp {
    public static BigInteger calculatePriceToRankUpTo(PlayerData playerData, int rankToGoTo) {
        BigInteger price = new BigInteger("0");
        for (int i = playerData.getMineRank() + 1; i <= rankToGoTo; i++) price = price.add(RankUpPrices.getBaseRankUpPriceForRank(i).multiply(playerData.getPrestige().divide(BigInteger.valueOf(10)).add(BigInteger.ONE)));
        return price;
    }
}
