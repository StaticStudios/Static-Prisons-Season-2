package net.staticstudios.prisons.misc;

import io.papermc.lib.PaperLib;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.islands.IslandManager;
import net.staticstudios.prisons.mines.old.BaseMine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class Warps {
    public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5, -145, 0);
    public static final Location CRATES = new Location(Bukkit.getWorld("world"), -49.5, 79, -128.5, -90, 0);
    public static final Location LEADERBOARDS = new Location(Bukkit.getWorld("world"), 147.5, 87, -213.5, -90, 0);

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
    public static CompletableFuture<Void> warpSomewhere(Player player, Location where, boolean flight) {
        return PaperLib.teleportAsync(player, where).thenRun(() -> player.setAllowFlight(flight));
    }
    public static void warpToSpawn(Player player) {
        PaperLib.teleportAsync(player, SPAWN).thenRun(() -> player.setAllowFlight(true));
    }
    public static void warpToCrates(Player player) {
        PaperLib.teleportAsync(player, CRATES).thenRun(() -> player.setAllowFlight(true));
    }
    public static void warpToLeaderboards(Player player) {
        PaperLib.teleportAsync(player, LEADERBOARDS).thenRun(() -> player.setAllowFlight(true));
    }
    public static void warpToMine(Player player, int mine) {
        switch (mine) {
            case 0 -> PaperLib.teleportAsync(player, MINE_A).thenRun(() -> player.setAllowFlight(true));
            case 1 -> PaperLib.teleportAsync(player, MINE_B).thenRun(() -> player.setAllowFlight(true));
            case 2 -> PaperLib.teleportAsync(player, MINE_C).thenRun(() -> player.setAllowFlight(true));
            case 3 -> PaperLib.teleportAsync(player, MINE_D).thenRun(() -> player.setAllowFlight(true));
            case 4 -> PaperLib.teleportAsync(player, MINE_E).thenRun(() -> player.setAllowFlight(true));
            case 5 -> PaperLib.teleportAsync(player, MINE_F).thenRun(() -> player.setAllowFlight(true));
            case 6 -> PaperLib.teleportAsync(player, MINE_G).thenRun(() -> player.setAllowFlight(true));
            case 7 -> PaperLib.teleportAsync(player, MINE_H).thenRun(() -> player.setAllowFlight(true));
            case 8 -> PaperLib.teleportAsync(player, MINE_I).thenRun(() -> player.setAllowFlight(true));
            case 9 -> PaperLib.teleportAsync(player, MINE_J).thenRun(() -> player.setAllowFlight(true));
            case 10 -> PaperLib.teleportAsync(player, MINE_K).thenRun(() -> player.setAllowFlight(true));
            case 11 -> PaperLib.teleportAsync(player, MINE_L).thenRun(() -> player.setAllowFlight(true));
            case 12 -> PaperLib.teleportAsync(player, MINE_M).thenRun(() -> player.setAllowFlight(true));
            case 13 -> PaperLib.teleportAsync(player, MINE_N).thenRun(() -> player.setAllowFlight(true));
            case 14 -> PaperLib.teleportAsync(player, MINE_O).thenRun(() -> player.setAllowFlight(true));
            case 15 -> PaperLib.teleportAsync(player, MINE_P).thenRun(() -> player.setAllowFlight(true));
            case 16 -> PaperLib.teleportAsync(player, MINE_Q).thenRun(() -> player.setAllowFlight(true));
            case 17 -> PaperLib.teleportAsync(player, MINE_R).thenRun(() -> player.setAllowFlight(true));
            case 18 -> PaperLib.teleportAsync(player, MINE_S).thenRun(() -> player.setAllowFlight(true));
            case 19 -> PaperLib.teleportAsync(player, MINE_T).thenRun(() -> player.setAllowFlight(true));
            case 20 -> PaperLib.teleportAsync(player, MINE_U).thenRun(() -> player.setAllowFlight(true));
            case 21 -> PaperLib.teleportAsync(player, MINE_V).thenRun(() -> player.setAllowFlight(true));
            case 22 -> PaperLib.teleportAsync(player, MINE_W).thenRun(() -> player.setAllowFlight(true));
            case 23 -> PaperLib.teleportAsync(player, MINE_X).thenRun(() -> player.setAllowFlight(true));
            case 24 -> PaperLib.teleportAsync(player, MINE_Y).thenRun(() -> player.setAllowFlight(true));
            case 25 -> PaperLib.teleportAsync(player, MINE_Z).thenRun(() -> player.setAllowFlight(true));
        }
        IslandManager.playerLeftIsland(player);
    }
    public static void warpToPrestigeMine(Player player, int mine) {
        switch (mine) {
            case 0 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_1).thenRun(() -> player.setAllowFlight(true));
            case 1 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_2).thenRun(() -> player.setAllowFlight(true));
            case 2 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_3).thenRun(() -> player.setAllowFlight(true));
            case 3 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_4).thenRun(() -> player.setAllowFlight(true));
            case 4 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_5).thenRun(() -> player.setAllowFlight(true));
            case 5 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_6).thenRun(() -> player.setAllowFlight(true));
            case 6 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_7).thenRun(() -> player.setAllowFlight(true));
            case 7 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_8).thenRun(() -> player.setAllowFlight(true));
            case 8 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_9).thenRun(() -> player.setAllowFlight(true));
            case 9 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_10).thenRun(() -> player.setAllowFlight(true));
            case 10 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_11).thenRun(() -> player.setAllowFlight(true));
            case 11 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_12).thenRun(() -> player.setAllowFlight(true));
            case 12 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_13).thenRun(() -> player.setAllowFlight(true));
            case 13 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_14).thenRun(() -> player.setAllowFlight(true));
            case 14 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_15).thenRun(() -> player.setAllowFlight(true));
        }
        IslandManager.playerLeftIsland(player);
    }
    public static void warpToRankMine(Player player, int mine) {
        switch (mine) {
            case 0 -> PaperLib.teleportAsync(player, RANK_MINE_1).thenRun(() -> player.setAllowFlight(true));
            case 1 -> PaperLib.teleportAsync(player, RANK_MINE_2).thenRun(() -> player.setAllowFlight(true));
            case 2 -> PaperLib.teleportAsync(player, RANK_MINE_3).thenRun(() -> player.setAllowFlight(true));
            case 3 -> PaperLib.teleportAsync(player, RANK_MINE_4).thenRun(() -> player.setAllowFlight(true));
            case 4 -> PaperLib.teleportAsync(player, RANK_MINE_5).thenRun(() -> player.setAllowFlight(true));
        }
        IslandManager.playerLeftIsland(player);
    }
    public static void warEventMine(Player player) {
        PaperLib.teleportAsync(player, EVENT_MINE).thenRun(() -> player.setAllowFlight(true));
        IslandManager.playerLeftIsland(player);
    }
}