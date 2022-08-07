package net.staticstudios.prisons.customItems;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TokenPouchTier2 {
    public static long minValue;
    public static long maxValue;
    public static void open(Player player) {
        TokenPouch pouch = new TokenPouch();
        pouch.reward = PrisonUtils.randomLong(minValue, maxValue);
        pouch.announceRewardInChat = true;
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won {reward} tokens from a Token Pouch Tier 2"));
    }
}
