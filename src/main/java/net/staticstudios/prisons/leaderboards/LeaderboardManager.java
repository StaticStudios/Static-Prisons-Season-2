package net.staticstudios.prisons.leaderboards;

public class LeaderboardManager { //todo switch to using api
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
