package me.staticstudios.prisons.mines;

import me.staticstudios.prisons.misc.Warps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

public class MineManager {
    public static final int defaultMineRefillTime = 20 * 600; //in ticks (10/sec) not 20
    public static Map<String, BaseMine> allMines = new HashMap<>();
    public static Map<String, MineMinMaxX> mineIDsToMinMaxLocation = new HashMap<>();
    public static Map<String, BaseMine> minesThatShouldRefillOnTimer = new HashMap<>();

    public static String getMineIDFromLocation(Location loc) {
        for (String key : mineIDsToMinMaxLocation.keySet()) {
            MineMinMaxX mineMinMaxX = mineIDsToMinMaxLocation.get(key);
            if (loc.getX() >= mineMinMaxX.minX && loc.getX() <= mineMinMaxX.maxX) return key;
        }
        return "";
    }

    public static void registerMine(BaseMine mine, boolean shouldRefillOnTimer) {
        mineIDsToMinMaxLocation.put(mine.mineID, new MineMinMaxX(mine.mineOffset));
        allMines.put(mine.mineID, mine);
        if (shouldRefillOnTimer) minesThatShouldRefillOnTimer.put(mine.mineID, mine);
    }

    //Runs every second, refills all mines on the refill list if their timer has hit 0, if not, it will decrement their timer
    public static void refillManager() {
        for (BaseMine mine : minesThatShouldRefillOnTimer.values()) {
            mine.timeUntilNextRefill--;
            if (mine.timeUntilNextRefill <= 0) {
                if (mine.getMaxBlocksInMine() == mine.getBlocksInMine()) {
                    mine.timeUntilNextRefill = mine.getTimeBetweenRefills();
                } else mine.refill();
            }
        }
    }


    public static void createAllPublicMines() {
        PublicMine mine;
        int publicMineMinX = -34;
        int publicMineMinY = 1;
        int publicMineMinZ = -34;
        int publicMineMaxX = 34;
        int publicMineMaxY = 99;
        int publicMineMaxZ = 34;
        //Mine A
        mine = new PublicMine("publicMine-A", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 45),
                new MineBlock(Material.COBBLESTONE, 45),
                new MineBlock(Material.COAL_ORE, 10),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_A);
        mine.autoRefillTimeOffset = 0;
        mine.setIfRefillsOnTimer(true);
        //Mine B
        mine = new PublicMine("publicMine-B", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 30),
                new MineBlock(Material.COBBLESTONE, 30),
                new MineBlock(Material.COAL_ORE, 40),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_B);
        mine.autoRefillTimeOffset = 1 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine C
        mine = new PublicMine("publicMine-C", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 30),
                new MineBlock(Material.COBBLESTONE, 22),
                new MineBlock(Material.COAL_ORE, 35),
                new MineBlock(Material.IRON_ORE, 15),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_C);
        mine.autoRefillTimeOffset = 2 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine D
        mine = new PublicMine("publicMine-D", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 22),
                new MineBlock(Material.COBBLESTONE, 15),
                new MineBlock(Material.COAL_ORE, 35),
                new MineBlock(Material.IRON_ORE, 30),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_D);
        mine.autoRefillTimeOffset = 3 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine E
        mine = new PublicMine("publicMine-E", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 22),
                new MineBlock(Material.COBBLESTONE, 5),
                new MineBlock(Material.COAL_ORE, 35),
                new MineBlock(Material.IRON_ORE, 35),
                new MineBlock(Material.GOLD_ORE, 5),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_E);
        mine.autoRefillTimeOffset = 4 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine F
        mine = new PublicMine("publicMine-F", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 15),
                new MineBlock(Material.COBBLESTONE, 5),
                new MineBlock(Material.COAL_ORE, 22),
                new MineBlock(Material.IRON_ORE, 30),
                new MineBlock(Material.GOLD_ORE, 30),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_F);
        mine.autoRefillTimeOffset = 5 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine G
        mine = new PublicMine("publicMine-G", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 10),
                new MineBlock(Material.COBBLESTONE, 5),
                new MineBlock(Material.COAL_ORE, 22),
                new MineBlock(Material.IRON_ORE, 23),
                new MineBlock(Material.GOLD_ORE, 30),
                new MineBlock(Material.LAPIS_ORE, 10),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_G);
        mine.autoRefillTimeOffset = 6 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine H
        mine = new PublicMine("publicMine-H", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 10),
                new MineBlock(Material.COBBLESTONE, 5),
                new MineBlock(Material.COAL_ORE, 10),
                new MineBlock(Material.IRON_ORE, 22),
                new MineBlock(Material.GOLD_ORE, 23),
                new MineBlock(Material.LAPIS_ORE, 30),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_H);
        mine.autoRefillTimeOffset = 7 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine I
        mine = new PublicMine("publicMine-I", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 5),
                new MineBlock(Material.COBBLESTONE, 5),
                new MineBlock(Material.COAL_ORE, 10),
                new MineBlock(Material.IRON_ORE, 15),
                new MineBlock(Material.GOLD_ORE, 20),
                new MineBlock(Material.LAPIS_ORE, 30),
                new MineBlock(Material.REDSTONE_ORE, 15),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_I);
        mine.autoRefillTimeOffset = 8 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine J
        mine = new PublicMine("publicMine-J", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 3),
                new MineBlock(Material.COBBLESTONE, 2),
                new MineBlock(Material.COAL_ORE, 5),
                new MineBlock(Material.IRON_ORE, 15),
                new MineBlock(Material.GOLD_ORE, 15),
                new MineBlock(Material.LAPIS_ORE, 20),
                new MineBlock(Material.REDSTONE_ORE, 40),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_J);
        mine.autoRefillTimeOffset = 9 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine K
        mine = new PublicMine("publicMine-K", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 3),
                new MineBlock(Material.COBBLESTONE, 2),
                new MineBlock(Material.COAL_ORE, 5),
                new MineBlock(Material.IRON_ORE, 10),
                new MineBlock(Material.GOLD_ORE, 10),
                new MineBlock(Material.LAPIS_ORE, 15),
                new MineBlock(Material.REDSTONE_ORE, 35),
                new MineBlock(Material.DIAMOND_ORE, 20),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_K);
        mine.autoRefillTimeOffset = 10 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine L
        mine = new PublicMine("publicMine-L", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 3),
                new MineBlock(Material.COBBLESTONE, 2),
                new MineBlock(Material.COAL_ORE, 5),
                new MineBlock(Material.IRON_ORE, 5),
                new MineBlock(Material.GOLD_ORE, 10),
                new MineBlock(Material.LAPIS_ORE, 18),
                new MineBlock(Material.REDSTONE_ORE, 22),
                new MineBlock(Material.DIAMOND_ORE, 35),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_L);
        mine.autoRefillTimeOffset = 11 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine M
        mine = new PublicMine("publicMine-M", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 3),
                new MineBlock(Material.COAL_ORE, 7),
                new MineBlock(Material.IRON_ORE, 5),
                new MineBlock(Material.GOLD_ORE, 10),
                new MineBlock(Material.LAPIS_ORE, 10),
                new MineBlock(Material.REDSTONE_ORE, 20),
                new MineBlock(Material.DIAMOND_ORE, 35),
                new MineBlock(Material.EMERALD_ORE, 10),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_M);
        mine.autoRefillTimeOffset = 12 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine N
        mine = new PublicMine("publicMine-N", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 3),
                new MineBlock(Material.COAL_ORE, 7),
                new MineBlock(Material.IRON_ORE, 7),
                new MineBlock(Material.GOLD_ORE, 8),
                new MineBlock(Material.LAPIS_ORE, 5),
                new MineBlock(Material.REDSTONE_ORE, 15),
                new MineBlock(Material.DIAMOND_ORE, 35),
                new MineBlock(Material.EMERALD_ORE, 20),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_N);
        mine.autoRefillTimeOffset = 13 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine O
        mine = new PublicMine("publicMine-O", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 2),
                new MineBlock(Material.COAL_ORE, 3),
                new MineBlock(Material.IRON_ORE, 2),
                new MineBlock(Material.GOLD_ORE, 3),
                new MineBlock(Material.LAPIS_ORE, 5),
                new MineBlock(Material.REDSTONE_ORE, 17),
                new MineBlock(Material.DIAMOND_ORE, 23),
                new MineBlock(Material.EMERALD_ORE, 35),
                new MineBlock(Material.COAL_BLOCK, 10),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_O);
        mine.autoRefillTimeOffset = 14 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine P
        mine = new PublicMine("publicMine-P", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 2),
                new MineBlock(Material.COAL_ORE, 3),
                new MineBlock(Material.IRON_ORE, 2),
                new MineBlock(Material.GOLD_ORE, 3),
                new MineBlock(Material.LAPIS_ORE, 5),
                new MineBlock(Material.REDSTONE_ORE, 10),
                new MineBlock(Material.DIAMOND_ORE, 23),
                new MineBlock(Material.EMERALD_ORE, 23),
                new MineBlock(Material.COAL_BLOCK, 24),
                new MineBlock(Material.IRON_BLOCK, 5),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_P);
        mine.autoRefillTimeOffset = 15 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine Q
        mine = new PublicMine("publicMine-Q", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 2),
                new MineBlock(Material.COAL_ORE, 3),
                new MineBlock(Material.IRON_ORE, 2),
                new MineBlock(Material.GOLD_ORE, 3),
                new MineBlock(Material.LAPIS_ORE, 5),
                new MineBlock(Material.REDSTONE_ORE, 5),
                new MineBlock(Material.DIAMOND_ORE, 15),
                new MineBlock(Material.EMERALD_ORE, 21),
                new MineBlock(Material.COAL_BLOCK, 22),
                new MineBlock(Material.IRON_BLOCK, 22),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_Q);
        mine.autoRefillTimeOffset = 16 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine R
        mine = new PublicMine("publicMine-R", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 1),
                new MineBlock(Material.COAL_ORE, 2),
                new MineBlock(Material.IRON_ORE, 2),
                new MineBlock(Material.GOLD_ORE, 2),
                new MineBlock(Material.LAPIS_ORE, 3),
                new MineBlock(Material.REDSTONE_ORE, 5),
                new MineBlock(Material.DIAMOND_ORE, 15),
                new MineBlock(Material.EMERALD_ORE, 15),
                new MineBlock(Material.COAL_BLOCK, 22),
                new MineBlock(Material.IRON_BLOCK, 23),
                new MineBlock(Material.GOLD_BLOCK, 10),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_R);
        mine.autoRefillTimeOffset = 17 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine S
        mine = new PublicMine("publicMine-S", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 1),
                new MineBlock(Material.COAL_ORE, 2),
                new MineBlock(Material.IRON_ORE, 2),
                new MineBlock(Material.GOLD_ORE, 2),
                new MineBlock(Material.LAPIS_ORE, 3),
                new MineBlock(Material.REDSTONE_ORE, 5),
                new MineBlock(Material.DIAMOND_ORE, 10),
                new MineBlock(Material.EMERALD_ORE, 10),
                new MineBlock(Material.COAL_BLOCK, 22),
                new MineBlock(Material.IRON_BLOCK, 22),
                new MineBlock(Material.GOLD_BLOCK, 22),
                new MineBlock(Material.LAPIS_BLOCK, 5),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_S);
        mine.autoRefillTimeOffset = 18 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine T
        mine = new PublicMine("publicMine-T", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.COAL_ORE, 1),
                new MineBlock(Material.IRON_ORE, 1),
                new MineBlock(Material.GOLD_ORE, 2),
                new MineBlock(Material.LAPIS_ORE, 3),
                new MineBlock(Material.REDSTONE_ORE, 3),
                new MineBlock(Material.DIAMOND_ORE, 5),
                new MineBlock(Material.EMERALD_ORE, 10),
                new MineBlock(Material.COAL_BLOCK, 20),
                new MineBlock(Material.IRON_BLOCK, 20),
                new MineBlock(Material.GOLD_BLOCK, 20),
                new MineBlock(Material.LAPIS_BLOCK, 15),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_T);
        mine.autoRefillTimeOffset = 19 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine U
        mine = new PublicMine("publicMine-U", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.COAL_ORE, 1),
                new MineBlock(Material.IRON_ORE, 1),
                new MineBlock(Material.GOLD_ORE, 2),
                new MineBlock(Material.LAPIS_ORE, 2),
                new MineBlock(Material.REDSTONE_ORE, 2),
                new MineBlock(Material.DIAMOND_ORE, 3),
                new MineBlock(Material.EMERALD_ORE, 4),
                new MineBlock(Material.COAL_BLOCK, 15),
                new MineBlock(Material.IRON_BLOCK, 20),
                new MineBlock(Material.GOLD_BLOCK, 20),
                new MineBlock(Material.LAPIS_BLOCK, 22),
                new MineBlock(Material.REDSTONE_BLOCK, 10),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_U);
        mine.autoRefillTimeOffset = 20 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine V
        mine = new PublicMine("publicMine-V", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.COAL_ORE, 1),
                new MineBlock(Material.IRON_ORE, 1),
                new MineBlock(Material.GOLD_ORE, 1),
                new MineBlock(Material.LAPIS_ORE, 2),
                new MineBlock(Material.REDSTONE_ORE, 2),
                new MineBlock(Material.DIAMOND_ORE, 2),
                new MineBlock(Material.EMERALD_ORE, 2),
                new MineBlock(Material.COAL_BLOCK, 9),
                new MineBlock(Material.IRON_BLOCK, 15),
                new MineBlock(Material.GOLD_BLOCK, 20),
                new MineBlock(Material.LAPIS_BLOCK, 20),
                new MineBlock(Material.REDSTONE_BLOCK, 20),
                new MineBlock(Material.DIAMOND_BLOCK, 5),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_V);
        mine.autoRefillTimeOffset = 21 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine W
        mine = new PublicMine("publicMine-W", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.COAL_ORE, 1),
                new MineBlock(Material.IRON_ORE, 1),
                new MineBlock(Material.GOLD_ORE, 1),
                new MineBlock(Material.LAPIS_ORE, 1),
                new MineBlock(Material.REDSTONE_ORE, 1),
                new MineBlock(Material.DIAMOND_ORE, 1),
                new MineBlock(Material.EMERALD_ORE, 1),
                new MineBlock(Material.COAL_BLOCK, 5),
                new MineBlock(Material.IRON_BLOCK, 13),
                new MineBlock(Material.GOLD_BLOCK, 15),
                new MineBlock(Material.LAPIS_BLOCK, 20),
                new MineBlock(Material.REDSTONE_BLOCK, 20),
                new MineBlock(Material.DIAMOND_BLOCK, 20),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_W);
        mine.autoRefillTimeOffset = 22 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine X
        mine = new PublicMine("publicMine-X", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.COAL_ORE, 1),
                new MineBlock(Material.IRON_ORE, 1),
                new MineBlock(Material.GOLD_ORE, 1),
                new MineBlock(Material.LAPIS_ORE, 1),
                new MineBlock(Material.REDSTONE_ORE, 1),
                new MineBlock(Material.DIAMOND_ORE, 1),
                new MineBlock(Material.EMERALD_ORE, 1),
                new MineBlock(Material.COAL_BLOCK, 5),
                new MineBlock(Material.IRON_BLOCK, 5),
                new MineBlock(Material.GOLD_BLOCK, 10),
                new MineBlock(Material.LAPIS_BLOCK, 15),
                new MineBlock(Material.REDSTONE_BLOCK, 15),
                new MineBlock(Material.DIAMOND_BLOCK, 30),
                new MineBlock(Material.EMERALD_BLOCK, 13),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_X);
        mine.autoRefillTimeOffset = 23 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine Y
        mine = new PublicMine("publicMine-Y", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.COAL_ORE, 1),
                new MineBlock(Material.IRON_ORE, 1),
                new MineBlock(Material.GOLD_ORE, 1),
                new MineBlock(Material.LAPIS_ORE, 1),
                new MineBlock(Material.REDSTONE_ORE, 1),
                new MineBlock(Material.DIAMOND_ORE, 1),
                new MineBlock(Material.EMERALD_ORE, 1),
                new MineBlock(Material.COAL_BLOCK, 1),
                new MineBlock(Material.IRON_BLOCK, 1),
                new MineBlock(Material.GOLD_BLOCK, 5),
                new MineBlock(Material.LAPIS_BLOCK, 5),
                new MineBlock(Material.REDSTONE_BLOCK, 5),
                new MineBlock(Material.DIAMOND_BLOCK, 35),
                new MineBlock(Material.EMERALD_BLOCK, 41),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_Y);
        mine.autoRefillTimeOffset = 24 * 5;
        mine.setIfRefillsOnTimer(true);
        //Mine Z
        mine = new PublicMine("publicMine-Z", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DIAMOND_BLOCK, 60),
                new MineBlock(Material.EMERALD_BLOCK, 40),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_Z);
        mine.autoRefillTimeOffset = 25 * 5;
        mine.setIfRefillsOnTimer(true);
    }
}

class MineMinMaxX {

    public final int minX;
    public final int maxX;

    public MineMinMaxX(int offset) {
        minX = offset - BaseMine.distanceBetweenMines / 2;
        maxX = offset + BaseMine.distanceBetweenMines / 2;
    }

}
