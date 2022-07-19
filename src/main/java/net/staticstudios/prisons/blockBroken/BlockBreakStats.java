package net.staticstudios.prisons.blockBroken;

import net.staticstudios.prisons.mines.MineBlock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BlockBreakStats {
    private double moneyMultiplier = 1d;
    private double xpMultiplier = 1d;
    private double tokenMultiplier = 1d;
    private double blocksBrokenMultiplier = 1d;

    private long moneyEarned = 0;
    private long xpEarned = 0;
    private long tokensEarned = 0;

    private long rawBlockBroken = 0;
    private long blocksBroken = 0;

    private Map<MineBlock, Long> minedBlocks = new HashMap<>();

    public BlockBreakStats() {}

    public double getMoneyMultiplier() {
        return moneyMultiplier;
    }

    public void setMoneyMultiplier(double moneyMultiplier) {
        this.moneyMultiplier = moneyMultiplier;
    }

    public double getXpMultiplier() {
        return xpMultiplier;
    }

    public void setXpMultiplier(double xpMultiplier) {
        this.xpMultiplier = xpMultiplier;
    }

    public double getTokenMultiplier() {
        return tokenMultiplier;
    }

    public void setTokenMultiplier(double tokenMultiplier) {
        this.tokenMultiplier = tokenMultiplier;
    }

    public double getBlocksBrokenMultiplier() {
        return blocksBrokenMultiplier;
    }

    public void setBlocksBrokenMultiplier(double blocksBrokenMultiplier) {
        this.blocksBrokenMultiplier = blocksBrokenMultiplier;
    }

    public long getMoneyEarned() {
        return moneyEarned;
    }

    public void setMoneyEarned(long moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public long getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(long xpEarned) {
        this.xpEarned = xpEarned;
    }

    public long getTokensEarned() {
        return tokensEarned;
    }

    public void setTokensEarned(long tokensEarned) {
        this.tokensEarned = tokensEarned;
    }

    public long getRawBlockBroken() {
        return rawBlockBroken;
    }

    public void setRawBlockBroken(long rawBlockBroken) {
        this.rawBlockBroken = rawBlockBroken;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(long blocksBroken) {
        this.blocksBroken = blocksBroken;
    }

    public Map<MineBlock, Long> getMinedBlocks() {
        return minedBlocks;
    }
}
