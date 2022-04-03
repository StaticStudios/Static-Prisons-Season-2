package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class TokenPouchTier1 {
    public static BigInteger minValue;
    public static BigInteger maxValue;
    public static void open(Player player) {
        TokenPouch pouch = new TokenPouch();
        pouch.reward = Utils.randomBigInt(minValue, maxValue);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won {reward} tokens from a Token Pouch Tier 1"));
    }
}