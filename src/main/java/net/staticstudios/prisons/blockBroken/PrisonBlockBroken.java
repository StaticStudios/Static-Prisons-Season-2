package net.staticstudios.prisons.blockBroken;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.data.Prices;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PrisonBlockBroken {
    public Player player;
    public PlayerData playerData;
    public PrisonPickaxe pickaxe;
    public StaticMine mine;
    public Location blockLocation;
    public Block block;
    public Map<Material, BigInteger> legacySellValues = new HashMap<>();
    public Map<BigInteger, BigDecimal> blocksBroken = new HashMap<>();
    public long amountOfBlocksBroken = 1;
    public long blocksBrokenMultiplier = 1;
    public long totalTokensGained = 0;
    public double tokenMultiplier = 1d;
    public double moneyMultiplier = 1d;
    public double xpMultiplier = 1d;
    public PrisonBlockBroken(Player player, PlayerData playerData, PrisonPickaxe pickaxe, StaticMine mine, Block block) {
        this.player = player;
        this.playerData = playerData;
        this.pickaxe = pickaxe;
        this.mine = mine;
        this.blockLocation = block.getLocation();
        this.block = block;
        blocksBroken.put(BigInteger.ONE, Prices.getSellPriceOf(block.getType()));
    }

    public void convertFromLegacySellValues() {
        for (Map.Entry<Material, BigInteger> entry : legacySellValues.entrySet()) {
            blocksBroken.put(entry.getValue(), Prices.getSellPriceOf(entry.getKey()));
        }
        legacySellValues.clear();
    }

    public void applyMoneyMulti() {
        if (!legacySellValues.isEmpty()) convertFromLegacySellValues();
        Map<BigInteger, BigDecimal> temp = new HashMap<>(blocksBroken);
        blocksBroken.clear();
        for (Map.Entry<BigInteger, BigDecimal> entry : temp.entrySet()) {
            blocksBroken.put(entry.getKey().multiply(BigInteger.valueOf(blocksBrokenMultiplier)), entry.getValue());
        }
        blocksBroken.replaceAll((k, v) -> v.multiply(BigDecimal.valueOf(moneyMultiplier)));
    }
}
