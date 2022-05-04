package me.staticstudios.prisons.blockBroken;

import me.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import me.staticstudios.prisons.mines.BaseMine;
import me.staticstudios.prisons.newData.dataHandling.PlayerData;
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
    public BaseMine mine;
    public Location blockLocation;
    public Map<Material, BigInteger> blockTypesBroken = new HashMap<>();
    public long blocksBroken = 1;
    public double blocksBrokenMultiplier = 1d;
    public long totalMoneyGained = 1;
    public long totalTokensGained = 1;
    public double moneyMultiplier = 1d;
    public double tokenMultiplier = 1d;
    public double xpMultiplier = 1d;
    public PrisonBlockBroken(Player player, PlayerData playerData, PrisonPickaxe pickaxe, BaseMine mine, Block block) {
        this.player = player;
        this.playerData = playerData;
        this.pickaxe = pickaxe;
        this.mine = mine;
        this.blockLocation = block.getLocation();
        blockTypesBroken.put(block.getType(), BigInteger.ONE);
    }
}
