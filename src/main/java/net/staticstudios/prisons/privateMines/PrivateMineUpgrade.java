package net.staticstudios.prisons.privateMines;

import java.math.BigInteger;

public class PrivateMineUpgrade {
    public int level;
    public BigInteger cost = BigInteger.ZERO;
    public boolean isSizeUpgrade;
    public boolean isSchemUpgrade;
    public boolean isBlocksUpgrade;

    public PrivateMineUpgrade(int level) {
        this.level = level;
    }
}
