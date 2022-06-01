package net.staticstudios.prisons.misc;

import io.papermc.lib.PaperLib;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.islands.IslandManager;
import net.staticstudios.prisons.mines.old.BaseMine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    public static void warpSomewhere(Player player, Location where, boolean flight) {
        PaperLib.teleportAsync(player, where);
        player.setAllowFlight(flight);
        if (flight) {
            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 10);
            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 20);
            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 30);
        }
    }
    public static void warpToSpawn(Player player) {
        PaperLib.teleportAsync(player, SPAWN);
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 30);
    }
    public static void warpToCrates(Player player) {
        PaperLib.teleportAsync(player, CRATES);
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 30);
    }
    public static void warpToLeaderboards(Player player) {
        PaperLib.teleportAsync(player, LEADERBOARDS);
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 30);
    }
    public static void warpToMine(Player player, int mine) {
        switch (mine) {
            case 0 -> PaperLib.teleportAsync(player, MINE_A);
            case 1 -> PaperLib.teleportAsync(player, MINE_B);
            case 2 -> PaperLib.teleportAsync(player, MINE_C);
            case 3 -> PaperLib.teleportAsync(player, MINE_D);
            case 4 -> PaperLib.teleportAsync(player, MINE_E);
            case 5 -> PaperLib.teleportAsync(player, MINE_F);
            case 6 -> PaperLib.teleportAsync(player, MINE_G);
            case 7 -> PaperLib.teleportAsync(player, MINE_H);
            case 8 -> PaperLib.teleportAsync(player, MINE_I);
            case 9 -> PaperLib.teleportAsync(player, MINE_J);
            case 10 -> PaperLib.teleportAsync(player, MINE_K);
            case 11 -> PaperLib.teleportAsync(player, MINE_L);
            case 12 -> PaperLib.teleportAsync(player, MINE_M);
            case 13 -> PaperLib.teleportAsync(player, MINE_N);
            case 14 -> PaperLib.teleportAsync(player, MINE_O);
            case 15 -> PaperLib.teleportAsync(player, MINE_P);
            case 16 -> PaperLib.teleportAsync(player, MINE_Q);
            case 17 -> PaperLib.teleportAsync(player, MINE_R);
            case 18 -> PaperLib.teleportAsync(player, MINE_S);
            case 19 -> PaperLib.teleportAsync(player, MINE_T);
            case 20 -> PaperLib.teleportAsync(player, MINE_U);
            case 21 -> PaperLib.teleportAsync(player, MINE_V);
            case 22 -> PaperLib.teleportAsync(player, MINE_W);
            case 23 -> PaperLib.teleportAsync(player, MINE_X);
            case 24 -> PaperLib.teleportAsync(player, MINE_Y);
            case 25 -> PaperLib.teleportAsync(player, MINE_Z);
        }
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 30);
        IslandManager.playerLeftIsland(player);
    }
    public static void warpToPrestigeMine(Player player, int mine) {
        switch (mine) {
            case 0 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_1);
            case 1 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_2);
            case 2 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_3);
            case 3 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_4);
            case 4 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_5);
            case 5 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_6);
            case 6 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_7);
            case 7 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_8);
            case 8 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_9);
            case 9 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_10);
            case 10 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_11);
            case 11 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_12);
            case 12 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_13);
            case 13 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_14);
            case 14 -> PaperLib.teleportAsync(player, PRESTIGE_MINE_15);
        }
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 30);
        IslandManager.playerLeftIsland(player);
    }
    public static void warpToRankMine(Player player, int mine) {
        switch (mine) {
            case 0 -> PaperLib.teleportAsync(player, RANK_MINE_1);
            case 1 -> PaperLib.teleportAsync(player, RANK_MINE_2);
            case 2 -> PaperLib.teleportAsync(player, RANK_MINE_3);
            case 3 -> PaperLib.teleportAsync(player, RANK_MINE_4);
            case 4 -> PaperLib.teleportAsync(player, RANK_MINE_5);
        }
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 30);
        IslandManager.playerLeftIsland(player);
    }
    public static void warEventMine(Player player) {
        PaperLib.teleportAsync(player, EVENT_MINE);
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (player.getWorld().getName().equals("mines")) player.setAllowFlight(true);
        }, 30);
        IslandManager.playerLeftIsland(player);
    }
}