package me.staticstudios.prisons.gameplay.leaderboards;

public class LeaderboardManager {
    public static void updateAll() {
        try {
            BlocksMinedTop.calculateLeaderBoard();
            RawBlocksMinedTop.calculateLeaderBoard();
            BalanceTop.calculateLeaderBoard();
            TokensTop.calculateLeaderBoard();
            TimePlayedTop.calculateLeaderBoard();
            PrestigeTop.calculateLeaderBoard();
            VotesTop.calculateLeaderBoard();
        } catch (IndexOutOfBoundsException ignore) {
        }
    }
}
