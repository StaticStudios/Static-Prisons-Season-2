package me.staticstudios.prisons.data;

import org.bukkit.Material;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Prices {
    public static Map<Material, BigInteger> BLOCK_SELL_PRICES = new HashMap<>();
    public static BigInteger getSellPriceOf(Material mat) {
        if (!BLOCK_SELL_PRICES.containsKey(mat)) return BigInteger.ZERO;
        return BLOCK_SELL_PRICES.get(mat);
    }
}
