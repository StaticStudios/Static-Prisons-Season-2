package net.staticstudios.prisons.data;

import org.bukkit.Material;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Prices {
    public static Map<Material, BigDecimal> BLOCK_SELL_PRICES = new HashMap<>();
    public static BigDecimal getSellPriceOf(Material mat) {
        if (!BLOCK_SELL_PRICES.containsKey(mat)) return BigDecimal.ZERO;
        return BLOCK_SELL_PRICES.get(mat);
    }
}
