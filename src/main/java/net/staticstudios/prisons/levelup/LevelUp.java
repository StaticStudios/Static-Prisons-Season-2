package net.staticstudios.prisons.levelup;

import net.staticstudios.prisons.data.PlayerData;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LevelUp {
    public static long[] rankPrices = new long[26];
    public static long INITIAL_PRESTIGE_PRICE;

    public static BigInteger calculatePriceToRankUpTo(PlayerData playerData, int rankToGoTo) {
        long price = 0;
        for (int i = playerData.getMineRank() + 1; i <= rankToGoTo; i++) {
            price += getBaseRankUpPriceForRank(i);
        }
        BigInteger biPrice = BigInteger.valueOf(price);
        return biPrice.multiply(playerData.getPrestige()).divide(BigInteger.valueOf(4)).add(biPrice); //+25% per prestige
    }

    public static long getBaseRankUpPriceForRank(int rank) {
        return rankPrices[rank];
    }

    public static long getPrestigePrice(long currentPrestige, int prestigesToBuy) {
        long price = INITIAL_PRESTIGE_PRICE * (currentPrestige + prestigesToBuy);
//        for (int r = 1; r < prestigesToBuy - 1; r++) {
//            long rankUpPrice = 0;
//            for (int i = 1; i <= 25; i++) {
//                rankUpPrice += getBaseRankUpPriceForRank(i);
//            }
//            price += rankUpPrice * currentPrestige / 3 + rankUpPrice;
//        }
        return price;
    }
}
