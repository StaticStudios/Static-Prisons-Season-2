package net.staticstudios.prisons.leaderboards;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.leaderboards.commands.ExemptFromLeaderboardsCommand;
import net.staticstudios.prisons.leaderboards.commands.LeaderboardsCommand;
import net.staticstudios.prisons.leaderboards.commands.UpdateLeaderboardsCommand;
import org.bukkit.Bukkit;

public class LeaderboardManager { //todo switch to using api

    public static void init() {
        CommandManager.registerCommand("updateleaderboards", new UpdateLeaderboardsCommand());
        CommandManager.registerCommand("exemptfromleaderboards", new ExemptFromLeaderboardsCommand());
        CommandManager.registerCommand("leaderboards", new LeaderboardsCommand());

        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), LeaderboardManager::updateAll, 20, 20 * 60 * 30);

    }

    public  static void updateAll() {
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
