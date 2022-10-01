package net.staticstudios.prisons.levelup.rankup;

import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.entity.Player;

public class RankUp {
    public static long[] RANK_PRICES = new long[26];

    /**
     * Calculates the price for a player to rank up to a specified rank.
     * This will adjust the price based on the player's current prestige (+25% per prestige).
     *
     * @param playerData The PlayerData for the player who is ranking up.
     * @param rankToGoTo The rank the player is going to.
     * @return The price for the player to rank up to the specified rank.
     */
    public static long calculatePriceToRankUp(PlayerData playerData, int rankToGoTo) {
        long price = 0;
        for (int i = playerData.getMineRank() + 1; i <= rankToGoTo; i++) {
            price += RANK_PRICES[i];
        }
        return price * playerData.getPrestige() / 4 + price; // +25% per prestige
    }

    /**
     * Attempts to rank up a player to a specified rank.
     *
     * @param player     The player who is ranking up.
     * @param rankToGoTo The rank the player is going to.
     * @return True if the player was able to rank up, false if they were not.
     */
    public static boolean rankUp(Player player, int rankToGoTo) {
        PlayerData playerData = new PlayerData(player);
        if (playerData.getMineRank() >= 25) return false;

        long rankUpCost = calculatePriceToRankUp(playerData, rankToGoTo);
        if (playerData.getMoney() >= rankUpCost) {
            playerData.removeMoney(rankUpCost);
            new RankUpEvent(player, playerData.getMineRank(), rankToGoTo).callEvent();
            playerData.setMineRank(rankToGoTo);
            return true;
        }
        return false;
    }
}
