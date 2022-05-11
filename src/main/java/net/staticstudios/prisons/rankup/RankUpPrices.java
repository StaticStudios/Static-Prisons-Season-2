package net.staticstudios.prisons.rankup;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RankUpPrices {
    public static List<BigInteger> rankPrices = new ArrayList<>();
    public static BigInteger getBaseRankUpPriceForRank(int rank) {
        return rankPrices.get(rank);
    }

    public static BigInteger INITIAL_PRESTIGE_PRICE;
    public static BigInteger getPrestigePrice(BigInteger currentPrestige, int prestigesToBuy) {
        BigInteger price = INITIAL_PRESTIGE_PRICE;
        price = price.multiply(BigInteger.valueOf(prestigesToBuy));
        return price;
    }

}
