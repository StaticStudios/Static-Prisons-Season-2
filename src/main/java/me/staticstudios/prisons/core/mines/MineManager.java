package me.staticstudios.prisons.core.mines;

import me.staticstudios.prisons.gameplay.Warps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

public class MineManager {
    public static final int defaultMineRefillTime = 10 * 900; //in ticks (10/sec) not 20
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

    public static boolean checkIfMineExists(String id) {
        return allMines.containsKey(id);
    }
    public static PrivateMine getPrivateMine(String id) {
        return (PrivateMine) allMines.get(id);
    }


    public static void initialize() {
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
        mine.setIfRefillsOnTimer(true);
        //Mine B
        mine = new PublicMine("publicMine-B", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 30),
                new MineBlock(Material.COBBLESTONE, 30),
                new MineBlock(Material.COAL_ORE, 40),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_B);
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
        mine.setIfRefillsOnTimer(true);
        //Mine Z
        mine = new PublicMine("publicMine-Z", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DIAMOND_BLOCK, 60),
                new MineBlock(Material.EMERALD_BLOCK, 40),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.MINE_Z);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 1
        mine = new PublicMine("prestigeMine-1", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DIAMOND_BLOCK, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_1);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 2
        mine = new PublicMine("prestigeMine-2", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.EMERALD_BLOCK, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_2);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 3
        mine = new PublicMine("prestigeMine-3", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.NETHERRACK, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_3);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 4
        mine = new PublicMine("prestigeMine-4", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.NETHER_BRICKS, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_4);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 5
        mine = new PublicMine("prestigeMine-5", new Location(Bukkit.getWorld("mines"), publicMineMinX, publicMineMinY, publicMineMinZ), new Location(Bukkit.getWorld("mines"), publicMineMaxX, publicMineMaxY, publicMineMaxZ));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.QUARTZ_BLOCK, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_5);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 6
        mine = new PublicMine("prestigeMine-6", new Location(Bukkit.getWorld("mines"), -44, publicMineMinY, -44), new Location(Bukkit.getWorld("mines"), 44, publicMineMaxY, 44));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.END_STONE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_6);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 7
        mine = new PublicMine("prestigeMine-7", new Location(Bukkit.getWorld("mines"), -44, publicMineMinY, -44), new Location(Bukkit.getWorld("mines"), 44, publicMineMaxY, 44));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.OBSIDIAN, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_7);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 8
        mine = new PublicMine("prestigeMine-8", new Location(Bukkit.getWorld("mines"), -44, publicMineMinY, -44), new Location(Bukkit.getWorld("mines"), 44, publicMineMaxY, 44));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.CRYING_OBSIDIAN, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_8);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 9
        mine = new PublicMine("prestigeMine-9", new Location(Bukkit.getWorld("mines"), -44, publicMineMinY, -44), new Location(Bukkit.getWorld("mines"), 44, publicMineMaxY, 44));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.PRISMARINE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_9);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 10
        mine = new PublicMine("prestigeMine-10", new Location(Bukkit.getWorld("mines"), -44, publicMineMinY, -44), new Location(Bukkit.getWorld("mines"), 44, publicMineMaxY, 44));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.AMETHYST_BLOCK, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_10);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 11
        mine = new PublicMine("prestigeMine-11", new Location(Bukkit.getWorld("mines"), -54, publicMineMinY, -54), new Location(Bukkit.getWorld("mines"), 54, publicMineMaxY, 54));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DEEPSLATE_COAL_ORE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_11);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 12
        mine = new PublicMine("prestigeMine-12", new Location(Bukkit.getWorld("mines"), -54, publicMineMinY, -54), new Location(Bukkit.getWorld("mines"), 54, publicMineMaxY, 54));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DEEPSLATE_IRON_ORE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_12);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 13
        mine = new PublicMine("prestigeMine-13", new Location(Bukkit.getWorld("mines"), -54, publicMineMinY, -54), new Location(Bukkit.getWorld("mines"), 54, publicMineMaxY, 54));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DEEPSLATE_GOLD_ORE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_13);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 14
        mine = new PublicMine("prestigeMine-14", new Location(Bukkit.getWorld("mines"), -54, publicMineMinY, -54), new Location(Bukkit.getWorld("mines"), 54, publicMineMaxY, 54));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DEEPSLATE_DIAMOND_ORE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_14);
        mine.setIfRefillsOnTimer(true);
        //Prestige Mine 15
        mine = new PublicMine("prestigeMine-15", new Location(Bukkit.getWorld("mines"), -75, publicMineMinY, -75), new Location(Bukkit.getWorld("mines"), 75, publicMineMaxY, 75));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.DEEPSLATE_EMERALD_ORE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.PRESTIGE_MINE_15);
        mine.setIfRefillsOnTimer(true);
        //Rank Mine 1
        mine = new PublicMine("rankMine-1", new Location(Bukkit.getWorld("mines"), -39, publicMineMinY, -39), new Location(Bukkit.getWorld("mines"), 39, publicMineMaxY, 39));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.NETHERRACK, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.RANK_MINE_1);
        mine.setIfRefillsOnTimer(true);
        //Rank Mine 2
        mine = new PublicMine("rankMine-2", new Location(Bukkit.getWorld("mines"), -44, publicMineMinY, -44), new Location(Bukkit.getWorld("mines"), 44, publicMineMaxY, 44));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.NETHER_BRICKS, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.RANK_MINE_2);
        mine.setIfRefillsOnTimer(true);
        //Rank Mine 3
        mine = new PublicMine("rankMine-3", new Location(Bukkit.getWorld("mines"), -49, publicMineMinY, -49), new Location(Bukkit.getWorld("mines"), 49, publicMineMaxY, 49));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.QUARTZ_BLOCK, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.RANK_MINE_3);
        mine.setIfRefillsOnTimer(true);
        //Rank Mine 4
        mine = new PublicMine("rankMine-4", new Location(Bukkit.getWorld("mines"), -64, publicMineMinY, -64), new Location(Bukkit.getWorld("mines"), 64, publicMineMaxY, 64));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.END_STONE, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.RANK_MINE_4);
        mine.setIfRefillsOnTimer(true);
        //Rank Mine 5
        mine = new PublicMine("rankMine-5", new Location(Bukkit.getWorld("mines"), -75, publicMineMinY, -75), new Location(Bukkit.getWorld("mines"), 75, publicMineMaxY, 75));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.OBSIDIAN, 100),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.RANK_MINE_5);
        mine.setIfRefillsOnTimer(true);
        //Event Mine
        mine = new PublicMine("eventMine", new Location(Bukkit.getWorld("mines"), -50, publicMineMinY, -50), new Location(Bukkit.getWorld("mines"), 50, publicMineMaxY, 50));
        mine.setBlockPattern(new MineBlock[]{
                new MineBlock(Material.STONE, 11),
                new MineBlock(Material.COBBLESTONE, 11),
                new MineBlock(Material.COAL_ORE, 3),
                new MineBlock(Material.IRON_ORE, 3),
                new MineBlock(Material.GOLD_ORE, 3),
                new MineBlock(Material.LAPIS_ORE, 3),
                new MineBlock(Material.REDSTONE_ORE, 3),
                new MineBlock(Material.DIAMOND_ORE, 3),
                new MineBlock(Material.EMERALD_ORE, 3),
                new MineBlock(Material.COAL_BLOCK, 3),
                new MineBlock(Material.IRON_BLOCK, 3),
                new MineBlock(Material.GOLD_BLOCK, 3),
                new MineBlock(Material.LAPIS_BLOCK, 3),
                new MineBlock(Material.REDSTONE_BLOCK, 3),
                new MineBlock(Material.DIAMOND_BLOCK, 3),
                new MineBlock(Material.EMERALD_BLOCK, 3),
                new MineBlock(Material.NETHERRACK, 3),
                new MineBlock(Material.NETHER_BRICKS, 3),
                new MineBlock(Material.QUARTZ_BLOCK, 3),
                new MineBlock(Material.END_STONE, 3),
                new MineBlock(Material.OBSIDIAN, 3),
                new MineBlock(Material.CRYING_OBSIDIAN, 3),
                new MineBlock(Material.PRISMARINE, 3),
                new MineBlock(Material.DEEPSLATE_COAL_ORE, 3),
                new MineBlock(Material.DEEPSLATE_IRON_ORE, 3),
                new MineBlock(Material.DEEPSLATE_GOLD_ORE, 3),
                new MineBlock(Material.DEEPSLATE_DIAMOND_ORE, 3),
                new MineBlock(Material.DEEPSLATE_EMERALD_ORE, 3),
        });
        mine.setWhereToTpPlayerOnRefill(Warps.EVENT_MINE);
        mine.setRefillWhenMineIsEmpty(false);
        mine.setIfRefillsOnTimer(true);
        mine.setTimeBetweenRefills(20 * 60 * 60 * 3);
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
