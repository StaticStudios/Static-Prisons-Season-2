package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class MoneyPouchTier2 {
    public static void open(Player player) {
        MoneyPouch pouch = new MoneyPouch();
        pouch.minReward = new BigInteger("10000000000000"); //10T
        pouch.maxReward = new BigInteger("50000000000000"); //50T
        pouch.reward = Utils.randomBigInt(pouch.minReward, pouch.maxReward);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou have won ${reward} from a Money Pouch Tier 2"));
    }
}