package net.staticstudios.prisons.blockBroken;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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
    public Map<Material, BigInteger> blockTypesBroken = new HashMap<>();
    public long blocksBroken = 1;
    public double blocksBrokenMultiplier = 1d;
    public long totalMoneyGained = 1;
    public long totalTokensGained = 0;
    public double moneyMultiplier = 1d;
    public double tokenMultiplier = 1d;
    public double xpMultiplier = 1d;
    public PrisonBlockBroken(Player player, PlayerData playerData, PrisonPickaxe pickaxe, StaticMine mine, Block block) {
        this.player = player;
        this.playerData = playerData;
        this.pickaxe = pickaxe;
        this.mine = mine;
        this.blockLocation = block.getLocation();
        this.block = block;
        blockTypesBroken.put(block.getType(), BigInteger.ONE);
    }
}
