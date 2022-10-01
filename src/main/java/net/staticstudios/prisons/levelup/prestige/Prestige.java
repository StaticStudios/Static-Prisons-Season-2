package net.staticstudios.prisons.levelup.prestige;

import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.entity.Player;

public class Prestige {
    public static long INITIAL_PRESTIGE_PRICE;
    public static long TOKENS_PER_PRESTIGE = 50_000;
    public static long XP_PER_PRESTIGE = 30_000;

    public static long getPrestigePrice(long currentPrestige, int prestigesToBuy) {
        return INITIAL_PRESTIGE_PRICE * (currentPrestige + prestigesToBuy);
    }


    /**
     * Attempt to prestige a player
     * @param player The player to prestige
     * @param amount The amount of prestiges to buy
     * @return true if the player was able to prestige, false otherwise
     */
    public static boolean playerPrestige(Player player, int amount) {
        PlayerData playerData = new PlayerData(player);
        long prestigePrice = getPrestigePrice(playerData.getPrestige(), amount);
        if (playerData.getMoney() >= prestigePrice) {
            playerData.removeMoney(prestigePrice);
            playerData.removeTokens(50_000L * amount);
            new PrestigeEvent(player, playerData.getPrestige(), playerData.getPrestige() + amount, prestigePrice).callEvent();
            playerData.addPrestige(amount);
            playerData.addPrestigeTokens(amount);
            playerData.setMineRank(0);

            return true;
        }
        return false;
    }
}
