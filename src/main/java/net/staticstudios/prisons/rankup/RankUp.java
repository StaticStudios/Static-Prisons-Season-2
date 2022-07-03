package net.staticstudios.prisons.rankup;

import net.staticstudios.prisons.data.PlayerData;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RankUp {
    public static List<BigInteger> rankPrices = new ArrayList<>();
    public static BigInteger INITIAL_PRESTIGE_PRICE;

    public static BigInteger calculatePriceToRankUpTo(PlayerData playerData, int rankToGoTo) {
        BigInteger price = new BigInteger("0");
        for (int i = playerData.getMineRank() + 1; i <= rankToGoTo; i++) price = price.add(getBaseRankUpPriceForRank(i).multiply(playerData.getPrestige().divide(BigInteger.valueOf(10)).add(BigInteger.ONE)));
        return price;
    }

    public static BigInteger getBaseRankUpPriceForRank(int rank) {
        return rankPrices.get(rank);
    }

    public static BigInteger getPrestigePrice(BigInteger currentPrestige, int prestigesToBuy) {
        BigInteger price = INITIAL_PRESTIGE_PRICE;
        price = price.multiply(BigInteger.valueOf(prestigesToBuy));
        return price;
    }
}
