package net.staticstudios.prisons.mines;

import org.bukkit.Material;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MineBlock {

    //private static final Map<Material, BigInteger> SELL_VALUES = new HashMap<>();

    public Material blockType;
    public double multiplier = 1d;

    public MineBlock(Material blockType) {
        this.blockType = blockType;
    }

//    public BigInteger getSellValue() {
//        return SELL_VALUES.get(blockType);
//    }
//
//    public static void defineNewBlock(Material blockType, long sellValue) {
//        SELL_VALUES.put(blockType, BigInteger.valueOf(sellValue));
//    }
}
