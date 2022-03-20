package me.staticstudios.prisons.core.rankup;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RankUpPrices {
    private static final List<BigInteger> rankPrices = new ArrayList<>();
    static {
        rankPrices.add(BigInteger.valueOf(0));
        rankPrices.add(BigInteger.valueOf(25000));
        rankPrices.add(BigInteger.valueOf(50000));
        rankPrices.add(BigInteger.valueOf(100000));
        rankPrices.add(BigInteger.valueOf(200000));
        rankPrices.add(BigInteger.valueOf(500000));
        rankPrices.add(BigInteger.valueOf(750000));
        rankPrices.add(BigInteger.valueOf(1000000));
        rankPrices.add(BigInteger.valueOf(1500000));
        rankPrices.add(BigInteger.valueOf(2000000));
        rankPrices.add(BigInteger.valueOf(3000000));
        rankPrices.add(BigInteger.valueOf(4000000));
        rankPrices.add(BigInteger.valueOf(5000000));
        rankPrices.add(BigInteger.valueOf(7000000));
        rankPrices.add(BigInteger.valueOf(9000000));
        rankPrices.add(BigInteger.valueOf(12000000));
        rankPrices.add(BigInteger.valueOf(15000000));
        rankPrices.add(BigInteger.valueOf(20000000));
        rankPrices.add(BigInteger.valueOf(30000000));
        rankPrices.add(BigInteger.valueOf(40000000));
        rankPrices.add(BigInteger.valueOf(50000000));
        rankPrices.add(BigInteger.valueOf(70000000));
        rankPrices.add(BigInteger.valueOf(80000000));
        rankPrices.add(BigInteger.valueOf(100000000));
        rankPrices.add(BigInteger.valueOf(150000000));
        rankPrices.add(BigInteger.valueOf(200000000));
    }
    public static BigInteger getBaseRankUpPriceForRank(int rank) {
        return rankPrices.get(rank);
    }

    public static final BigInteger INITIAL_PRESTIGE_PRICE = BigInteger.valueOf(500000000);
    public static BigInteger getPrestigePrice(BigInteger currentPrestige, int prestigesToBuy) {
        BigInteger price = INITIAL_PRESTIGE_PRICE;
        price = price.multiply(BigInteger.valueOf(prestigesToBuy));
        return price;
    }

}
