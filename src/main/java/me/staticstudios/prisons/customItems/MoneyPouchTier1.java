package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class MoneyPouchTier1 {
    public static BigInteger minValue;
    public static BigInteger maxValue;
    public static void open(Player player) {
        MoneyPouch pouch = new MoneyPouch();
        pouch.reward = Utils.randomBigInt(minValue, maxValue);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won ${reward} from a Money Pouch Tier 1"));
    }
}
