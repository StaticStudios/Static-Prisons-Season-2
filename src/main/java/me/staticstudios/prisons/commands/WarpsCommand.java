package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.misc.Warps;
import me.staticstudios.prisons.utils.StaticVars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class WarpsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) {
            GUI.getGUIPage("warps").open(player);
            return false;
        }
        String notUnlocked = ChatColor.RED + "You do not have this mine unlocked!";
        PlayerData playerData = new PlayerData(player);
        switch (args[0].toLowerCase()) {
            case "spawn" -> Warps.warpToSpawn(player);
            case "crates" -> Warps.warpToCrates(player);
            case "leaderboards" -> Warps.warpToLeaderboards(player);
            case "event" -> Warps.warEventMine(player);
            case "a" -> {
                if (playerData.getMineRank() >= 0) {
                    Warps.warpToMine(player, 0);
                } else player.sendMessage(notUnlocked);
            }
            case "b" -> {
                if (playerData.getMineRank() >= 1) {
                    Warps.warpToMine(player, 1);
                } else player.sendMessage(notUnlocked);
            }
            case "c" -> {
                if (playerData.getMineRank() >= 2) {
                    Warps.warpToMine(player, 2);
                } else player.sendMessage(notUnlocked);
            }
            case "d" -> {
                if (playerData.getMineRank() >= 3) {
                    Warps.warpToMine(player, 3);
                } else player.sendMessage(notUnlocked);
            }
            case "e" -> {
                if (playerData.getMineRank() >= 4) {
                    Warps.warpToMine(player, 4);
                } else player.sendMessage(notUnlocked);
            }
            case "f" -> {
                if (playerData.getMineRank() >= 5) {
                    Warps.warpToMine(player, 5);
                } else player.sendMessage(notUnlocked);
            }
            case "g" -> {
                if (playerData.getMineRank() >= 6) {
                    Warps.warpToMine(player, 6);
                } else player.sendMessage(notUnlocked);
            }
            case "h" -> {
                if (playerData.getMineRank() >= 7) {
                    Warps.warpToMine(player, 7);
                } else player.sendMessage(notUnlocked);
            }
            case "i" -> {
                if (playerData.getMineRank() >= 8) {
                    Warps.warpToMine(player, 8);
                } else player.sendMessage(notUnlocked);
            }
            case "j" -> {
                if (playerData.getMineRank() >= 9) {
                    Warps.warpToMine(player, 9);
                } else player.sendMessage(notUnlocked);
            }
            case "k" -> {
                if (playerData.getMineRank() >= 10) {
                    Warps.warpToMine(player, 10);
                } else player.sendMessage(notUnlocked);
            }
            case "l" -> {
                if (playerData.getMineRank() >= 11) {
                    Warps.warpToMine(player, 11);
                } else player.sendMessage(notUnlocked);
            }
            case "m" -> {
                if (playerData.getMineRank() >= 12) {
                    Warps.warpToMine(player, 12);
                } else player.sendMessage(notUnlocked);
            }
            case "n" -> {
                if (playerData.getMineRank() >= 13) {
                    Warps.warpToMine(player, 13);
                } else player.sendMessage(notUnlocked);
            }
            case "o" -> {
                if (playerData.getMineRank() >= 14) {
                    Warps.warpToMine(player, 14);
                } else player.sendMessage(notUnlocked);
            }
            case "p" -> {
                if (playerData.getMineRank() >= 15) {
                    Warps.warpToMine(player, 15);
                } else player.sendMessage(notUnlocked);
            }
            case "q" -> {
                if (playerData.getMineRank() >= 16) {
                    Warps.warpToMine(player, 16);
                } else player.sendMessage(notUnlocked);
            }
            case "r" -> {
                if (playerData.getMineRank() >= 17) {
                    Warps.warpToMine(player, 17);
                } else player.sendMessage(notUnlocked);
            }
            case "s" -> {
                if (playerData.getMineRank() >= 18) {
                    Warps.warpToMine(player, 18);
                } else player.sendMessage(notUnlocked);
            }
            case "t" -> {
                if (playerData.getMineRank() >= 19) {
                    Warps.warpToMine(player, 19);
                } else player.sendMessage(notUnlocked);
            }
            case "u" -> {
                if (playerData.getMineRank() >= 20) {
                    Warps.warpToMine(player, 20);
                } else player.sendMessage(notUnlocked);
            }
            case "v" -> {
                if (playerData.getMineRank() >= 21) {
                    Warps.warpToMine(player, 21);
                } else player.sendMessage(notUnlocked);
            }
            case "w" -> {
                if (playerData.getMineRank() >= 22) {
                    Warps.warpToMine(player, 22);
                } else player.sendMessage(notUnlocked);
            }
            case "x" -> {
                if (playerData.getMineRank() >= 23) {
                    Warps.warpToMine(player, 23);
                } else player.sendMessage(notUnlocked);
            }
            case "y" -> {
                if (playerData.getMineRank() >= 24) {
                    Warps.warpToMine(player, 24);
                } else player.sendMessage(notUnlocked);
            }
            case "z" -> {
                if (playerData.getMineRank() >= 25) {
                    Warps.warpToMine(player, 25);
                } else player.sendMessage(notUnlocked);
            }
            case "p1" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[0])) > -1) {
                    Warps.warpPrestigeMine(player, 0);
                } else player.sendMessage(notUnlocked);
            }
            case "p2" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[1])) > -1) {
                    Warps.warpPrestigeMine(player, 1);
                } else player.sendMessage(notUnlocked);
            }
            case "p3" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[2])) > -1) {
                    Warps.warpPrestigeMine(player, 2);
                } else player.sendMessage(notUnlocked);
            }
            case "p4" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[3])) > -1) {
                    Warps.warpPrestigeMine(player, 3);
                } else player.sendMessage(notUnlocked);
            }
            case "p5" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[4])) > -1) {
                    Warps.warpPrestigeMine(player, 4);
                } else player.sendMessage(notUnlocked);
            }
            case "p6" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[5])) > -1) {
                    Warps.warpPrestigeMine(player, 5);
                } else player.sendMessage(notUnlocked);
            }
            case "p7" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[6])) > -1) {
                    Warps.warpPrestigeMine(player, 6);
                } else player.sendMessage(notUnlocked);
            }
            case "p8" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[7])) > -1) {
                    Warps.warpPrestigeMine(player, 7);
                } else player.sendMessage(notUnlocked);
            }
            case "p9" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[8])) > -1) {
                    Warps.warpPrestigeMine(player, 8);
                } else player.sendMessage(notUnlocked);
            }
            case "p10" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[9])) > -1) {
                    Warps.warpPrestigeMine(player, 9);
                } else player.sendMessage(notUnlocked);
            }
            case "p11" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[10])) > -1) {
                    Warps.warpPrestigeMine(player, 10);
                } else player.sendMessage(notUnlocked);
            }
            case "p12" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[11])) > -1) {
                    Warps.warpPrestigeMine(player, 11);
                } else player.sendMessage(notUnlocked);
            }
            case "p13" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[12])) > -1) {
                    Warps.warpPrestigeMine(player, 12);
                } else player.sendMessage(notUnlocked);
            }
            case "p14" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[13])) > -1) {
                    Warps.warpPrestigeMine(player, 13);
                } else player.sendMessage(notUnlocked);
            }
            case "p15" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[14])) > -1) {
                    Warps.warpPrestigeMine(player, 14);
                } else player.sendMessage(notUnlocked);
            }
            case "r1" -> {
                if (playerData.getPlayerRanks().contains("warrior")) {
                    Warps.warpRankMine(player, 0);
                } else player.sendMessage(notUnlocked);
            }
            case "r2" -> {
                if (playerData.getPlayerRanks().contains("master")) {
                    Warps.warpRankMine(player, 1);
                } else player.sendMessage(notUnlocked);
            }
            case "r3" -> {
                if (playerData.getPlayerRanks().contains("mythic")) {
                    Warps.warpRankMine(player, 2);
                } else player.sendMessage(notUnlocked);
            }
            case "r4" -> {
                if (playerData.getPlayerRanks().contains("static")) {
                    Warps.warpRankMine(player, 3);
                } else player.sendMessage(notUnlocked);
            }
            case "r5" -> {
                if (playerData.getPlayerRanks().contains("staticp")) {
                    Warps.warpRankMine(player, 4);
                } else player.sendMessage(notUnlocked);
            }
        }
        return false;
    }
}
