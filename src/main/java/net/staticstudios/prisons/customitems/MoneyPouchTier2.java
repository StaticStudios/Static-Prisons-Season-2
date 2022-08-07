package net.staticstudios.prisons.customitems;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MoneyPouchTier2 {
    public static long minValue;
    public static long maxValue;
    public static void open(Player player) {
        MoneyPouch pouch = new MoneyPouch();
        pouch.reward = PrisonUtils.randomLong(minValue, maxValue);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won ${reward} from a Money Pouch Tier 2"));
    }
}