package net.staticstudios.prisons.UI.scoreboard;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.rankup.RankUpPrices;
import net.staticstudios.prisons.UI.scoreboard.fastBoard.FastBoard;
import net.staticstudios.prisons.rankup.RankUp;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.Map;

public class CustomScoreboard { //todo make a custom thing instead of fastboard
    public static Map<String, CustomScoreboard> boards = new Hashtable<>();
    public Player player;
    public FastBoard board;
    private ChatColor colorDark = ChatColor.of("#3dc2ff"); //ChatColor.of("#3dc2ff")
    private ChatColor colorLight = ChatColor.AQUA;
    private static final ChatColor colorBase = ChatColor.WHITE;
    private int titlePos = 0;
    private String boardTitle;


    public CustomScoreboard(Player player) {
        this.player = player;
        board = new FastBoard(player);
    }

    public static void updatePlayerScoreboard(Player player) {
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
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerScoreboard(player);
        }
    }


    void updateTitle() {
        String[] titles = new String[] {
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
        titlePos++;
        if (titlePos + 1 > titles.length) titlePos = 0;
        boardTitle = titles[titlePos];
    }
    void updateBoard() {
        PlayerData playerData = new PlayerData(player);

        switch (playerData.getUITheme()) {
            case PURPLE_DYE -> {
                colorDark = ChatColor.of("#b638ff");
                colorLight = ChatColor.LIGHT_PURPLE;
            }
            case LIME_DYE -> {
                colorDark = ChatColor.of("#00ba31");
                colorLight = ChatColor.GREEN;
            }
            case RED_DYE -> {
                colorDark = ChatColor.DARK_RED;
                colorLight = ChatColor.RED;
            }
            case ORANGE_DYE -> {
                colorDark = ChatColor.of("#ffcc00");
                colorLight = ChatColor.GOLD;
            }
            default -> {
                colorDark = ChatColor.of("#3dc2ff");
                colorLight = ChatColor.AQUA;
            }
        }
        updateTitle();

        board.updateTitle(boardTitle);
        String cost = PrisonUtils.prettyNum(RankUp.calculatePriceToRankUpTo(playerData, Math.min(25, playerData.getMineRank() + 1)));
        if (playerData.getMineRank() == 25) cost = PrisonUtils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), 1));

        board.updateLines(
                ChatColor.RED + "",
                colorDark + "" + ChatColor.BOLD + "Mine Rank",
                colorLight + "│ " + colorBase + "Current Rank: " + colorLight + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank()),
                colorLight + "│ " + colorBase + "RankUp Cost: " + colorLight + "$" + cost,
                ChatColor.BLUE + "",
                colorDark + "" + ChatColor.BOLD + player.getName() ,
                colorLight + "│ " + colorBase + "Rank: " + colorLight + playerData.getSidebarRank(),
                //colorLight + "│ " + colorBase + "Shards: " + colorLight + "⬧" + Utils.prettyNum(playerData.getShards()),
                colorLight + "│ " + colorBase + "Balance: " + colorLight + "$" + PrisonUtils.prettyNum(playerData.getMoney()),
                colorLight + "│ " + colorBase + "Tokens: " + colorLight + "⛃" + PrisonUtils.prettyNum(playerData.getTokens()),
                colorLight + "│ " + colorBase + "Prestige: " + colorLight + PrisonUtils.prettyNum(playerData.getPrestige()),
                colorLight + "│ " + colorBase + "Vote Party: " + colorLight + ServerData.SERVER.getVoteParty() + "/" + Constants.VOTES_NEEDED_FOR_VOTE_PARTY,
                ChatColor.GREEN + "",
                colorLight + " play.static-studios.net"
        );
    }
}