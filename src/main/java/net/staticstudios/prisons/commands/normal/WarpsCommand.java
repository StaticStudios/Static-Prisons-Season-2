package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.gui.newGui.WarpMenus;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class WarpsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) {
            WarpMenus.mainMenu(player);
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
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[0])) > -1) {
                    Warps.warpToPrestigeMine(player, 0);
                } else player.sendMessage(notUnlocked);
            }
            case "p2" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[1])) > -1) {
                    Warps.warpToPrestigeMine(player, 1);
                } else player.sendMessage(notUnlocked);
            }
            case "p3" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[2])) > -1) {
                    Warps.warpToPrestigeMine(player, 2);
                } else player.sendMessage(notUnlocked);
            }
            case "p4" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[3])) > -1) {
                    Warps.warpToPrestigeMine(player, 3);
                } else player.sendMessage(notUnlocked);
            }
            case "p5" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[4])) > -1) {
                    Warps.warpToPrestigeMine(player, 4);
                } else player.sendMessage(notUnlocked);
            }
            case "p6" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[5])) > -1) {
                    Warps.warpToPrestigeMine(player, 5);
                } else player.sendMessage(notUnlocked);
            }
            case "p7" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[6])) > -1) {
                    Warps.warpToPrestigeMine(player, 6);
                } else player.sendMessage(notUnlocked);
            }
            case "p8" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[7])) > -1) {
                    Warps.warpToPrestigeMine(player, 7);
                } else player.sendMessage(notUnlocked);
            }
            case "p9" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[8])) > -1) {
                    Warps.warpToPrestigeMine(player, 8);
                } else player.sendMessage(notUnlocked);
            }
            case "p10" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[9])) > -1) {
                    Warps.warpToPrestigeMine(player, 9);
                } else player.sendMessage(notUnlocked);
            }
            case "p11" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[10])) > -1) {
                    Warps.warpToPrestigeMine(player, 10);
                } else player.sendMessage(notUnlocked);
            }
            case "p12" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[11])) > -1) {
                    Warps.warpToPrestigeMine(player, 11);
                } else player.sendMessage(notUnlocked);
            }
            case "p13" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[12])) > -1) {
                    Warps.warpToPrestigeMine(player, 12);
                } else player.sendMessage(notUnlocked);
            }
            case "p14" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[13])) > -1) {
                    Warps.warpToPrestigeMine(player, 13);
                } else player.sendMessage(notUnlocked);
            }
            case "p15" -> {
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[14])) > -1) {
                    Warps.warpToPrestigeMine(player, 14);
                } else player.sendMessage(notUnlocked);
            }
            case "r1" -> {
                if (playerData.getPlayerRanks().contains("warrior")) {
                    Warps.warpToRankMine(player, 0);
                } else player.sendMessage(notUnlocked);
            }
            case "r2" -> {
                if (playerData.getPlayerRanks().contains("master")) {
                    Warps.warpToRankMine(player, 1);
                } else player.sendMessage(notUnlocked);
            }
            case "r3" -> {
                if (playerData.getPlayerRanks().contains("mythic")) {
                    Warps.warpToRankMine(player, 2);
                } else player.sendMessage(notUnlocked);
            }
            case "r4" -> {
                if (playerData.getPlayerRanks().contains("static")) {
                    Warps.warpToRankMine(player, 3);
                } else player.sendMessage(notUnlocked);
            }
            case "r5" -> {
                if (playerData.getPlayerRanks().contains("staticp")) {
                    Warps.warpToRankMine(player, 4);
                } else player.sendMessage(notUnlocked);
            }
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("p1");
            list.add("p2");
            list.add("p3");
            list.add("p4");
            list.add("p5");
            list.add("p6");
            list.add("p7");
            list.add("p8");
            list.add("p9");
            list.add("p10");
            list.add("p11");
            list.add("p12");
            list.add("p13");
            list.add("p14");
            list.add("p15");
            list.add("r1");
            list.add("r2");
            list.add("r3");
            list.add("r4");
            list.add("r5");
            list.add("a");
            list.add("b");
            list.add("c");
            list.add("d");
            list.add("e");
            list.add("f");
            list.add("g");
            list.add("h");
            list.add("i");
            list.add("j");
            list.add("k");
            list.add("l");
            list.add("m");
            list.add("n");
            list.add("o");
            list.add("p");
            list.add("q");
            list.add("r");
            list.add("s");
            list.add("t");
            list.add("u");
            list.add("v");
            list.add("w");
            list.add("x");
            list.add("y");
            list.add("z");
        }
        return list;
    }
}
