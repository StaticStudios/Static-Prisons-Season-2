package me.staticstudios.prisons.rankup;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RankUpPrices {
    private static final List<BigInteger> rankPrices = new ArrayList<>();
    static {
        rankPrices.add(BigInteger.valueOf(0));
        rankPrices.add(BigInteger.valueOf(25000));
        rankPrices.add(BigInteger.valueOf(40000));
        rankPrices.add(BigInteger.valueOf(60000));
        rankPrices.add(BigInteger.valueOf(80000));
        rankPrices.add(BigInteger.valueOf(140000));
        rankPrices.add(BigInteger.valueOf(250000));
        rankPrices.add(BigInteger.valueOf(350000));
        rankPrices.add(BigInteger.valueOf(400000));
        rankPrices.add(BigInteger.valueOf(700000));
        rankPrices.add(BigInteger.valueOf(1500000));
        rankPrices.add(BigInteger.valueOf(1750000));
        rankPrices.add(BigInteger.valueOf(2000000));
        rankPrices.add(BigInteger.valueOf(2250000));
        rankPrices.add(BigInteger.valueOf(2500000));
        rankPrices.add(BigInteger.valueOf(3000000));
        rankPrices.add(BigInteger.valueOf(3500000));
        rankPrices.add(BigInteger.valueOf(4000000));
        rankPrices.add(BigInteger.valueOf(5000000));
        rankPrices.add(BigInteger.valueOf(6000000));
        rankPrices.add(BigInteger.valueOf(7000000));
        rankPrices.add(BigInteger.valueOf(8000000));
        rankPrices.add(BigInteger.valueOf(9000000));
        rankPrices.add(BigInteger.valueOf(10000000));
        rankPrices.add(BigInteger.valueOf(15000000));
        rankPrices.add(BigInteger.valueOf(25000000));
    }
    public static BigInteger getBaseRankUpPriceForRank(int rank) {
        return rankPrices.get(rank);
    }

    public static final BigInteger INITIAL_PRESTIGE_PRICE = BigInteger.valueOf(500000000);
    public static final int PRESTIGE_RATE_OF_INCREASE = 7; //percentage
    public static BigInteger getPrestigePrice(BigInteger currentPrestige, int prestigesToBuy) {
        BigInteger price = INITIAL_PRESTIGE_PRICE;
        for (int i = 0; i < prestigesToBuy; i++) {
            price = price.add(INITIAL_PRESTIGE_PRICE.multiply(currentPrestige.add(BigInteger.valueOf(i + 1))).divide(BigInteger.valueOf(100).divide(BigInteger.valueOf(PRESTIGE_RATE_OF_INCREASE))));
        }
        return price;
    }

}
