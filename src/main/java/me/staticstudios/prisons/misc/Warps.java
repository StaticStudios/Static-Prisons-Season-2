package me.staticstudios.prisons.misc;

import me.staticstudios.prisons.mines.BaseMine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warps {
    public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 0, 100, 0);

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
    public static void warpToSpawn(Player player) {
        player.teleport(SPAWN);
        player.setAllowFlight(true);
    }
    public static void warpToMine(Player player, int mine) {
        switch (mine) {
            case 0 -> player.teleport(MINE_A);
            case 1 -> player.teleport(MINE_B);
            case 2 -> player.teleport(MINE_C);
            case 3 -> player.teleport(MINE_D);
            case 4 -> player.teleport(MINE_E);
            case 5 -> player.teleport(MINE_F);
            case 6 -> player.teleport(MINE_G);
            case 7 -> player.teleport(MINE_H);
            case 8 -> player.teleport(MINE_I);
            case 9 -> player.teleport(MINE_J);
            case 10 -> player.teleport(MINE_K);
            case 11 -> player.teleport(MINE_L);
            case 12 -> player.teleport(MINE_M);
            case 13 -> player.teleport(MINE_N);
            case 14 -> player.teleport(MINE_O);
            case 15 -> player.teleport(MINE_P);
            case 16 -> player.teleport(MINE_Q);
            case 17 -> player.teleport(MINE_R);
            case 18 -> player.teleport(MINE_S);
            case 19 -> player.teleport(MINE_T);
            case 20 -> player.teleport(MINE_U);
            case 21 -> player.teleport(MINE_V);
            case 22 -> player.teleport(MINE_W);
            case 23 -> player.teleport(MINE_X);
            case 24 -> player.teleport(MINE_Y);
            case 25 -> player.teleport(MINE_Z);
        }
        player.setAllowFlight(true);
    }
    public static void warpPrestigeMine(Player player, int mine) {
        switch (mine) {
            case 0 -> player.teleport(MINE_A);
            case 1 -> player.teleport(MINE_B);
            case 2 -> player.teleport(MINE_C);
            case 3 -> player.teleport(MINE_D);
            case 4 -> player.teleport(MINE_E);
            case 5 -> player.teleport(MINE_F);
            case 6 -> player.teleport(MINE_G);
            case 7 -> player.teleport(MINE_H);
            case 8 -> player.teleport(MINE_I);
            case 9 -> player.teleport(MINE_J);
            case 10 -> player.teleport(MINE_K);
            case 11 -> player.teleport(MINE_L);
            case 12 -> player.teleport(MINE_M);
            case 13 -> player.teleport(MINE_N);
            case 14 -> player.teleport(MINE_O);
        }
        player.setAllowFlight(true);
    }
    public static void warpRankMine(Player player, int mine) {
        switch (mine) {
            case 0 -> player.teleport(MINE_A);
            case 1 -> player.teleport(MINE_B);
            case 2 -> player.teleport(MINE_C);
            case 3 -> player.teleport(MINE_D);
            case 4 -> player.teleport(MINE_E);
        }
        player.setAllowFlight(true);
    }
}
