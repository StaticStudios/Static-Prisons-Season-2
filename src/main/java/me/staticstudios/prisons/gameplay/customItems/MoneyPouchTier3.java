package me.staticstudios.prisons.gameplay.customItems;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class MoneyPouchTier3 {
    public static void open(Player player) {
        MoneyPouch pouch = new MoneyPouch();
        pouch.minReward = new BigInteger("100000000000000"); //100T
        pouch.maxReward = new BigInteger("2000000000000000"); //2Qd
        pouch.reward = Utils.randomBigInt(pouch.minReward, pouch.maxReward);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou have won ${reward} from a Money Pouch Tier 3"));
    }
}