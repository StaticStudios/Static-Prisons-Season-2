package net.staticstudios.prisons.misc;

import io.papermc.lib.PaperLib;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.islands.IslandManager;
import net.staticstudios.prisons.mines.old.BaseMine;
import net.staticstudios.prisons.pvp.PvPManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class Warps {
    public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, -145, 0);
    public static final Location CRATES = new Location(Bukkit.getWorld("world"), -49.5, 79, -128.5, -90, 0);
    public static final Location LEADERBOARDS = new Location(Bukkit.getWorld("world"), 147.5, 87, -213.5, -90, 0);
    public static final Location PVP = new Location(Bukkit.getWorld("pvp"), 43.5, 142, 47.5, 165, 0);

    //Mines should be spaced 500 blocks away from each other on the X access

    public static final Location MINE_A = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 0, 102, -25.5, -90, 0);
    public static final Location MINE_B = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 1, 102, -25.5, -90, 0);
    public static final Location MINE_C = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 2, 102, -25.5, -90, 0);
    public static final Location MINE_D = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 3, 102, -25.5, -90, 0);
    public static final Location MINE_E = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 4, 102, -25.5, -90, 0);
    public static final Location MINE_F = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 5, 102, -25.5, -90, 0);
    public static final Location MINE_G = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 6, 102, -25.5, -90, 0);
    public static final Location MINE_H = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 7, 102, -25.5, -90, 0);
    public static final Location MINE_I = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 8, 102, -25.5, -90, 0);
    public static final Location MINE_J = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 9, 102, -25.5, -90, 0);
    public static final Location MINE_K = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 10, 102, -25.5, -90, 0);
    public static final Location MINE_L = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 11, 102, -25.5, -90, 0);
    public static final Location MINE_M = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 12, 102, -25.5, -90, 0);
    public static final Location MINE_N = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 13, 102, -25.5, -90, 0);
    public static final Location MINE_O = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 14, 102, -25.5, -90, 0);
    public static final Location MINE_P = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 15, 102, -25.5, -90, 0);
    public static final Location MINE_Q = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 16, 102, -25.5, -90, 0);
    public static final Location MINE_R = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 17, 102, -25.5, -90, 0);
    public static final Location MINE_S = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 18, 102, -25.5, -90, 0);
    public static final Location MINE_T = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 19, 102, -25.5, -90, 0);
    public static final Location MINE_U = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 20, 102, -25.5, -90, 0);
    public static final Location MINE_V = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 21, 102, -25.5, -90, 0);
    public static final Location MINE_W = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 22, 102, -25.5, -90, 0);
    public static final Location MINE_X = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 23, 102, -25.5, -90, 0);
    public static final Location MINE_Y = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 24, 102, -25.5, -90, 0);
    public static final Location MINE_Z = new Location(Bukkit.getWorld("mines"), -61.5 + BaseMine.distanceBetweenMines * 25, 102, -25.5, -90, 0);

    public static final Location PRESTIGE_MINE_1 = new Location(Bukkit.getWorld("mines"), -48.5 + BaseMine.distanceBetweenMines * 26, 102, 30.5, -135, 0);
    public static final Location PRESTIGE_MINE_2 = new Location(Bukkit.getWorld("mines"), -48.5 + BaseMine.distanceBetweenMines * 27, 102, 30.5, -135, 0);
    public static final Location PRESTIGE_MINE_3 = new Location(Bukkit.getWorld("mines"), -48.5 + BaseMine.distanceBetweenMines * 28, 102, 30.5, -135, 0);
    public static final Location PRESTIGE_MINE_4 = new Location(Bukkit.getWorld("mines"), -48.5 + BaseMine.distanceBetweenMines * 29, 102, 30.5, -135, 0);
    public static final Location PRESTIGE_MINE_5 = new Location(Bukkit.getWorld("mines"), -48.5 + BaseMine.distanceBetweenMines * 30, 102, 30.5, -135, 0);
    public static final Location PRESTIGE_MINE_6 = new Location(Bukkit.getWorld("mines"), 54.5 + BaseMine.distanceBetweenMines * 31, 104, -36.5, 45, 0);
    public static final Location PRESTIGE_MINE_7 = new Location(Bukkit.getWorld("mines"), 54.5 + BaseMine.distanceBetweenMines * 32, 104, -36.5, 45, 0);
    public static final Location PRESTIGE_MINE_8 = new Location(Bukkit.getWorld("mines"), 54.5 + BaseMine.distanceBetweenMines * 33, 104, -36.5, 45, 0);
    public static final Location PRESTIGE_MINE_9 = new Location(Bukkit.getWorld("mines"), 54.5 + BaseMine.distanceBetweenMines * 34, 104, -36.5, 45, 0);
    public static final Location PRESTIGE_MINE_10 = new Location(Bukkit.getWorld("mines"), 54.5 + BaseMine.distanceBetweenMines * 35, 104, -36.5, 45, 0);
    public static final Location PRESTIGE_MINE_11 = new Location(Bukkit.getWorld("mines"), 63.5 + BaseMine.distanceBetweenMines * 36, 103, -21.5, 90, 0);
    public static final Location PRESTIGE_MINE_12 = new Location(Bukkit.getWorld("mines"), 63.5 + BaseMine.distanceBetweenMines * 37, 103, -21.5, 90, 0);
    public static final Location PRESTIGE_MINE_13 = new Location(Bukkit.getWorld("mines"), 63.5 + BaseMine.distanceBetweenMines * 38, 103, -21.5, 90, 0);
    public static final Location PRESTIGE_MINE_14 = new Location(Bukkit.getWorld("mines"), 63.5 + BaseMine.distanceBetweenMines * 39, 103, -21.5, 90, 0);
    public static final Location PRESTIGE_MINE_15 = new Location(Bukkit.getWorld("mines"), 41.5 + BaseMine.distanceBetweenMines * 40, 103, 87.5, 135, 0);


    public static final Location RANK_MINE_1 = new Location(Bukkit.getWorld("mines"), -33.5 + BaseMine.distanceBetweenMines * 41, 101, -44.5, -45, 0);
    public static final Location RANK_MINE_2 = new Location(Bukkit.getWorld("mines"), 57.5 + BaseMine.distanceBetweenMines * 42, 109, 39.5, 135, 0);
    public static final Location RANK_MINE_3 = new Location(Bukkit.getWorld("mines"), -60.5 + BaseMine.distanceBetweenMines * 43, 103, -10.5, -90, 0);
    public static final Location RANK_MINE_4 = new Location(Bukkit.getWorld("mines"), -75.5 + BaseMine.distanceBetweenMines * 44, 102, 28.5, -120, 0);
    public static final Location RANK_MINE_5 = new Location(Bukkit.getWorld("mines"), 41.5 + BaseMine.distanceBetweenMines * 45, 103, 87.5, 135, 0);

    public static final Location EVENT_MINE = new Location(Bukkit.getWorld("mines"), -72.5 + BaseMine.distanceBetweenMines * 46, 103, -39.5, -45, 0);

    public static CompletableFuture<Boolean> warpSomewhere(Player player, Location where, boolean flight) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        PaperLib.teleportAsync(player, where).thenRun(() -> player.setAllowFlight(flight)).thenRun(() -> future.complete(true));
        return future;
    }
    public static void warpToSpawn(Player player) {
        warpSomewhere(player, SPAWN, true);
    }
    public static void warpToPvP(Player player) {
        warpSomewhere(player, PVP, true);
    }
    public static void warpToCrates(Player player) {
        warpSomewhere(player, CRATES, true);
    }
    public static void warpToLeaderboards(Player player) {
        warpSomewhere(player, LEADERBOARDS, true);
    }
    public static void warpToMine(Player player, int mine) {
        switch (mine) {
            case 0 -> warpSomewhere(player, MINE_A, true);
            case 1 -> warpSomewhere(player, MINE_B, true);
            case 2 -> warpSomewhere(player, MINE_C, true);
            case 3 -> warpSomewhere(player, MINE_D, true);
            case 4 -> warpSomewhere(player, MINE_E, true);
            case 5 -> warpSomewhere(player, MINE_F, true);
            case 6 -> warpSomewhere(player, MINE_G, true);
            case 7 -> warpSomewhere(player, MINE_H, true);
            case 8 -> warpSomewhere(player, MINE_I, true);
            case 9 -> warpSomewhere(player, MINE_J, true);
            case 10 -> warpSomewhere(player, MINE_K, true);
            case 11 -> warpSomewhere(player, MINE_L, true);
            case 12 -> warpSomewhere(player, MINE_M, true);
            case 13 -> warpSomewhere(player, MINE_N, true);
            case 14 -> warpSomewhere(player, MINE_O, true);
            case 15 -> warpSomewhere(player, MINE_P, true);
            case 16 -> warpSomewhere(player, MINE_Q, true);
            case 17 -> warpSomewhere(player, MINE_R, true);
            case 18 -> warpSomewhere(player, MINE_S, true);
            case 19 -> warpSomewhere(player, MINE_T, true);
            case 20 -> warpSomewhere(player, MINE_U, true);
            case 21 -> warpSomewhere(player, MINE_V, true);
            case 22 -> warpSomewhere(player, MINE_W, true);
            case 23 -> warpSomewhere(player, MINE_X, true);
            case 24 -> warpSomewhere(player, MINE_Y, true);
            case 25 -> warpSomewhere(player, MINE_Z, true);
        }
        IslandManager.playerLeftIsland(player);
    }
    public static void warpToPrestigeMine(Player player, int mine) {
        switch (mine) {
            case 0 -> warpSomewhere(player, PRESTIGE_MINE_1, true);
            case 1 -> warpSomewhere(player, PRESTIGE_MINE_2, true);
            case 2 -> warpSomewhere(player, PRESTIGE_MINE_3, true);
            case 3 -> warpSomewhere(player, PRESTIGE_MINE_4, true);
            case 4 -> warpSomewhere(player, PRESTIGE_MINE_5, true);
            case 5 -> warpSomewhere(player, PRESTIGE_MINE_6, true);
            case 6 -> warpSomewhere(player, PRESTIGE_MINE_7, true);
            case 7 -> warpSomewhere(player, PRESTIGE_MINE_8, true);
            case 8 -> warpSomewhere(player, PRESTIGE_MINE_9, true);
            case 9 -> warpSomewhere(player, PRESTIGE_MINE_10, true);
            case 10 -> warpSomewhere(player, PRESTIGE_MINE_11, true);
            case 11 -> warpSomewhere(player, PRESTIGE_MINE_12, true);
            case 12 -> warpSomewhere(player, PRESTIGE_MINE_13, true);
            case 13 -> warpSomewhere(player, PRESTIGE_MINE_14, true);
            case 14 -> warpSomewhere(player, PRESTIGE_MINE_15, true);
        }
        IslandManager.playerLeftIsland(player);
    }
    public static void warpToRankMine(Player player, int mine) {
        switch (mine) {
            case 0 -> warpSomewhere(player, RANK_MINE_1, true);
            case 1 -> warpSomewhere(player, RANK_MINE_2, true);
            case 2 -> warpSomewhere(player, RANK_MINE_3, true);
            case 3 -> warpSomewhere(player, RANK_MINE_4, true);
            case 4 -> warpSomewhere(player, RANK_MINE_5, true);
        }
        IslandManager.playerLeftIsland(player);
    }
    public static void warEventMine(Player player) {
        warpSomewhere(player, EVENT_MINE, true);
        IslandManager.playerLeftIsland(player);
    }
}