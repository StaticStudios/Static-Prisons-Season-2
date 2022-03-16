package me.staticstudios.prisons.core.rankup;

import me.staticstudios.prisons.core.data.serverData.PlayerData;

import java.math.BigInteger;

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
