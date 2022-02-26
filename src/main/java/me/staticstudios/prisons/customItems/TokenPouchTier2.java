package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class TokenPouchTier2 {
    public static void open(Player player) {
        TokenPouch pouch = new TokenPouch();
        pouch.minReward = new BigInteger("500000000"); //500M
        pouch.maxReward = new BigInteger("1000000000"); //1B
        pouch.reward = Utils.randomBigInt(pouch.minReward, pouch.maxReward);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou have won {reward} tokens from a Token Pouch Tier 2"));
    }
}
