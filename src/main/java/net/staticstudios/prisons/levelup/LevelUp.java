package net.staticstudios.prisons.levelup;

import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.levelup.commands.RankUpMaxCommand;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.levelup.commands.LevelCommand;
import net.staticstudios.prisons.levelup.commands.PrestigeCommand;
import net.staticstudios.prisons.levelup.commands.RankUpCommand;

public class LevelUp {
    public static long[] rankPrices = new long[26];
    public static long INITIAL_PRESTIGE_PRICE;

    public static void init() {
        CommandManager.registerCommand("rankup", new RankUpCommand());
        CommandManager.registerCommand("rankupmax", new RankUpMaxCommand());
        CommandManager.registerCommand("level", new LevelCommand());
        CommandManager.registerCommand("prestige", new PrestigeCommand());
    }

    public static long calculatePriceToRankUpTo(PlayerData playerData, int rankToGoTo) {
        long price = 0;
        for (int i = playerData.getMineRank() + 1; i <= rankToGoTo; i++) {
            price += getBaseRankUpPriceForRank(i);
        }
        return price * playerData.getPrestige() / 4 + price; // +25% per prestige
    }

    public static long getBaseRankUpPriceForRank(int rank) {
        return rankPrices[rank];
    }

    public static long getPrestigePrice(long currentPrestige, int prestigesToBuy) {
        return INITIAL_PRESTIGE_PRICE * (currentPrestige + prestigesToBuy);
    }
}
