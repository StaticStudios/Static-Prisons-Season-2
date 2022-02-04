package me.staticstudios.prisons.misc.scoreboard;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.misc.scoreboard.fastBoard.FastBoard;
import me.staticstudios.prisons.rankup.RankUpPrices;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.Map;

public class CustomScoreboard {
    public static Map<String, CustomScoreboard> boards = new Hashtable<>();
    public Player player;
    public FastBoard board;
    private static final ChatColor colorDark = ChatColor.of("#3dc2ff"); //ChatColor.of("#3dc2ff")
    private static final ChatColor colorLight = ChatColor.AQUA;
    private static final ChatColor colorBase = ChatColor.WHITE;
    private static int titlePos = 0;
    private static final String[] titles = new String[] {
            colorBase + "" + ChatColor.BOLD + "STATIC PRISONS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISONS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISONS",
            colorLight + "" + ChatColor.BOLD + "S" + colorBase + "" + ChatColor.BOLD +  "TATIC PRISONS",
            colorDark + "" + ChatColor.BOLD + "S" + colorLight + "" + ChatColor.BOLD +  "T" + colorBase + "" + ChatColor.BOLD +  "ATIC PRISONS",
            colorDark + "" + ChatColor.BOLD + "ST" + colorLight + "" + ChatColor.BOLD +  "A" + colorBase + "" + ChatColor.BOLD +  "TIC PRISONS",
            colorDark + "" + ChatColor.BOLD + "STA" + colorLight + "" + ChatColor.BOLD +  "T" + colorBase + "" + ChatColor.BOLD +  "IC PRISONS",
            colorLight + "" + ChatColor.BOLD + "S" + colorDark + "" + ChatColor.BOLD +  "TAT" + colorLight + "" + ChatColor.BOLD +  "I" + colorBase + "" + ChatColor.BOLD +  "C PRISONS",
            colorBase + "" + ChatColor.BOLD + "S" + colorLight + "" + ChatColor.BOLD +  "T" + colorDark + "" + ChatColor.BOLD +  "ATI" + colorLight + "" + ChatColor.BOLD +  "C" + colorBase + "" + ChatColor.BOLD +  " PRISONS",
            colorBase + "" + ChatColor.BOLD + "ST" + colorLight + "" + ChatColor.BOLD +  "A" + colorDark + "" + ChatColor.BOLD +  "TIC" + colorLight + "" + ChatColor.BOLD +  " " + colorBase + "" + ChatColor.BOLD +  "PRISONS",
            colorBase + "" + ChatColor.BOLD + "STA" + colorLight + "" + ChatColor.BOLD +  "T" + colorDark + "" + ChatColor.BOLD +  "IC " + colorLight + "" + ChatColor.BOLD +  "P" + colorBase + "" + ChatColor.BOLD +  "RISONS",
            colorBase + "" + ChatColor.BOLD + "STAT" + colorLight + "" + ChatColor.BOLD +  "I" + colorDark + "" + ChatColor.BOLD +  "C P" + colorLight + "" + ChatColor.BOLD +  "R" + colorBase + "" + ChatColor.BOLD +  "ISONS",
            colorBase + "" + ChatColor.BOLD + "STATI" + colorLight + "" + ChatColor.BOLD +  "C" + colorDark + "" + ChatColor.BOLD +  " PR" + colorLight + "" + ChatColor.BOLD +  "I" + colorBase + "" + ChatColor.BOLD +  "SONS",
            colorBase + "" + ChatColor.BOLD + "STATIC" + colorLight + "" + ChatColor.BOLD +  " " + colorDark + "" + ChatColor.BOLD +  "PRI" + colorLight + "" + ChatColor.BOLD +  "S" + colorBase + "" + ChatColor.BOLD +  "ONS",
            colorBase + "" + ChatColor.BOLD + "STATIC " + colorLight + "" + ChatColor.BOLD +  "P" + colorDark + "" + ChatColor.BOLD +  "RIS" + colorLight + "" + ChatColor.BOLD +  "O" + colorBase + "" + ChatColor.BOLD +  "NS",
            colorBase + "" + ChatColor.BOLD + "STATIC P" + colorLight + "" + ChatColor.BOLD +  "R" + colorDark + "" + ChatColor.BOLD +  "ISO" + colorLight + "" + ChatColor.BOLD +  "N" + colorBase + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC PR" + colorLight + "" + ChatColor.BOLD +  "I" + colorDark + "" + ChatColor.BOLD +  "SON" + colorLight + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC PRI" + colorLight + "" + ChatColor.BOLD +  "S" + colorDark + "" + ChatColor.BOLD +  "ONS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRIS" + colorLight + "" + ChatColor.BOLD +  "O" + colorDark + "" + ChatColor.BOLD +  "NS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISO" + colorLight + "" + ChatColor.BOLD +  "N" + colorDark + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISON" + colorLight + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISONS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISONS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISONS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISON" + colorLight + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC PRISO" + colorLight + "" + ChatColor.BOLD +  "N" + colorDark + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC PRIS" + colorLight + "" + ChatColor.BOLD +  "O" + colorDark + "" + ChatColor.BOLD +  "NS",
            colorBase + "" + ChatColor.BOLD + "STATIC PRI" + colorLight + "" + ChatColor.BOLD +  "S" + colorDark + "" + ChatColor.BOLD +  "ONS",
            colorBase + "" + ChatColor.BOLD + "STATIC PR" + colorLight + "" + ChatColor.BOLD +  "I" + colorDark + "" + ChatColor.BOLD +  "SON" + colorLight + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC P" + colorLight + "" + ChatColor.BOLD +  "R" + colorDark + "" + ChatColor.BOLD +  "ISO" + colorLight + "" + ChatColor.BOLD +  "N" + colorBase + "" + ChatColor.BOLD +  "S",
            colorBase + "" + ChatColor.BOLD + "STATIC " + colorLight + "" + ChatColor.BOLD +  "P" + colorDark + "" + ChatColor.BOLD +  "RIS" + colorLight + "" + ChatColor.BOLD +  "O" + colorBase + "" + ChatColor.BOLD +  "NS",
            colorBase + "" + ChatColor.BOLD + "STATIC" + colorLight + "" + ChatColor.BOLD +  " " + colorDark + "" + ChatColor.BOLD +  "PRI" + colorLight + "" + ChatColor.BOLD +  "S" + colorBase + "" + ChatColor.BOLD +  "ONS",
            colorBase + "" + ChatColor.BOLD + "STATI" + colorLight + "" + ChatColor.BOLD +  "C" + colorDark + "" + ChatColor.BOLD +  " PR" + colorLight + "" + ChatColor.BOLD +  "I" + colorBase + "" + ChatColor.BOLD +  "SONS",
            colorBase + "" + ChatColor.BOLD + "STAT" + colorLight + "" + ChatColor.BOLD +  "I" + colorDark + "" + ChatColor.BOLD +  "C P" + colorLight + "" + ChatColor.BOLD +  "R" + colorBase + "" + ChatColor.BOLD +  "ISONS",
            colorBase + "" + ChatColor.BOLD + "STA" + colorLight + "" + ChatColor.BOLD +  "T" + colorDark + "" + ChatColor.BOLD +  "IC " + colorLight + "" + ChatColor.BOLD +  "P" + colorBase + "" + ChatColor.BOLD +  "RISONS",
            colorBase + "" + ChatColor.BOLD + "ST" + colorLight + "" + ChatColor.BOLD +  "A" + colorDark + "" + ChatColor.BOLD +  "TIC" + colorLight + "" + ChatColor.BOLD +  " " + colorBase + "" + ChatColor.BOLD +  "PRISONS",
            colorBase + "" + ChatColor.BOLD + "S" + colorLight + "" + ChatColor.BOLD +  "T" + colorDark + "" + ChatColor.BOLD +  "ATI" + colorLight + "" + ChatColor.BOLD +  "C" + colorBase + "" + ChatColor.BOLD +  " PRISONS",
            colorLight + "" + ChatColor.BOLD + "S" + colorDark + "" + ChatColor.BOLD +  "TAT" + colorLight + "" + ChatColor.BOLD +  "I" + colorBase + "" + ChatColor.BOLD +  "C PRISONS",
            colorDark + "" + ChatColor.BOLD + "STA" + colorLight + "" + ChatColor.BOLD +  "T" + colorBase + "" + ChatColor.BOLD +  "IC PRISONS",
            colorDark + "" + ChatColor.BOLD + "ST" + colorLight + "" + ChatColor.BOLD +  "A" + colorBase + "" + ChatColor.BOLD +  "TIC PRISONS",
            colorDark + "" + ChatColor.BOLD + "S" + colorLight + "" + ChatColor.BOLD +  "T" + colorBase + "" + ChatColor.BOLD +  "ATIC PRISONS",
            colorLight + "" + ChatColor.BOLD + "S" + colorBase + "" + ChatColor.BOLD +  "TATIC PRISONS",

    };
    private static String boardTitle = titles[0];


    public CustomScoreboard(Player player) {
        this.player = player;
        board = new FastBoard(player);
    }

    public static void updateSingleScoreboard(Player player) {
        String uuid = player.getUniqueId().toString();
        if (!boards.containsKey(uuid)) {
            boards.put(uuid, new CustomScoreboard(player));
        }
        boards.get(uuid).updateBoard();
    }
    public static void playerLeft(String uuid) {
        CustomScoreboard.boards.remove(uuid);
    }
    public static void updateAllScoreboards() {
        updateTitle();
        for (Player player : Bukkit.getOnlinePlayers()) {
            updateSingleScoreboard(player);
        }
    }


    static void updateTitle() {
        titlePos++;
        if (titlePos + 1 > titles.length) titlePos = 0;
        boardTitle = titles[titlePos];
    }
    void updateBoard() {
        PlayerData playerData = new PlayerData(player);

        board.updateTitle(boardTitle);
        board.updateLines(
                ChatColor.RED + "",
                colorDark + "" + ChatColor.BOLD + "Mine Rank",
                colorLight + "│ " + colorBase + "Current Rank: " + colorLight + Utils.getMineRankLetterFromMineRank(playerData.getMineRank()),
                colorLight + "│ " + colorBase + "RankUp Cost: " + colorLight + "$" + Utils.prettyNum(RankUpPrices.getBaseRankUpPriceForRank(playerData.getMineRank() + 1)),
                ChatColor.BLUE + "",
                colorDark + "" + ChatColor.BOLD + player.getName() ,
                colorLight + "│ " + colorBase + "Rank: " + colorLight + playerData.getSidebarRank(),
                colorLight + "│ " + colorBase + "Money: " + colorLight + "$" + Utils.prettyNum(playerData.getMoney()),
                colorLight + "│ " + colorBase + "Tokens: " + colorLight + Utils.prettyNum(playerData.getTokens()),
                colorLight + "│ " + colorBase + "Prestige: " + colorLight + Utils.prettyNum(playerData.getPrestige()),
                colorLight + "│ " + colorBase + "Vote Party: " + colorLight + 0 + "/" + 50,
                ChatColor.GREEN + "",
                colorLight + " play.static-studios.net"
        );
    }
}