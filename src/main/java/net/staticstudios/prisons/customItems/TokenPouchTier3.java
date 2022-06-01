package net.staticstudios.prisons.customItems;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class TokenPouchTier3 {
    public static BigInteger minValue;
    public static BigInteger maxValue;
    public static void open(Player player) {
        TokenPouch pouch = new TokenPouch();
        pouch.reward = PrisonUtils.randomBigInt(minValue, maxValue);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won {reward} tokens from a Token Pouch Tier 3"));
    }
}
