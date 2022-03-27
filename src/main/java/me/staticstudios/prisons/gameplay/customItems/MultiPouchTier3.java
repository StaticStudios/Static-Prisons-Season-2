package me.staticstudios.prisons.gameplay.customItems;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class MultiPouchTier3 {
    public static void open(Player player) {
        MultiPouch pouch = new MultiPouch();
        pouch.minMultiplierAmount = 20; //2.0
        pouch.maxMultiplierAmount = 40; //4.0
        pouch.minMultiplierTime = 10;
        pouch.maxMultiplierTime = 120;
        pouch.multiplierAmount = BigDecimal.valueOf(Utils.randomInt(pouch.minMultiplierAmount, pouch.maxMultiplierAmount)).divide(BigDecimal.TEN);
        pouch.multiplierTime = Utils.randomInt(pouch.minMultiplierTime, pouch.maxMultiplierTime);
        pouch.animateOpeningPouch(player, new PlayerData(player), ChatColor.translateAlternateColorCodes('&', "&aYou won {reward} from a Multiplier Pouch Tier 3"));
    }
}
