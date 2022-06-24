package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.*;

public class AutoSellEnchant extends BaseEnchant {
    public AutoSellEnchant() {
        super("autoSell", "&a&lAuto Sell", 50000, BigInteger.valueOf(75), "&7Decrease the time between the", "&7automatic selling of your backpack", "&7Minimum interval: 60 seconds");
    }

    public static Map<PrisonPickaxe, Integer> autoSellTimeLeft = new HashMap<>();
    public static Map<PrisonPickaxe, Player> activePickaxes = new HashMap<>();

    static void onSell(PrisonPickaxe pickaxe, Player player) {
        PlayerData playerData = new PlayerData(player);
        if (playerData.getBackpackItemCount() == 0) return;
        playerData.sellBackpack(player, true, ChatColor.GREEN + "[Auto Sell] " + ChatColor.WHITE + "(x%MULTI%) Sold " + ChatColor.AQUA + "%TOTAL_BACKPACK_COUNT% " + ChatColor.WHITE + "blocks for: " + ChatColor.GREEN + "$%TOTAL_SELL_PRICE%");
    }
    static final int MAX_INTERVAL = 600;
    static final int STEP = 93;
    static int getSecBetweenInterval(int level) {
        return MAX_INTERVAL - level / STEP;
    }

    public static void initTimer() {
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (PrisonPickaxe pickaxe : activePickaxes.keySet()) {
                autoSellTimeLeft.put(pickaxe, autoSellTimeLeft.get(pickaxe) - 3);
                if (autoSellTimeLeft.get(pickaxe) <= 0) {
                    autoSellTimeLeft.put(pickaxe, getSecBetweenInterval(pickaxe.getEnchantLevel(PrisonEnchants.AUTO_SELL.ENCHANT_ID)));
                    onSell(pickaxe, activePickaxes.get(pickaxe));
                }
            }
        }, 20, 20 * 3);
    }


    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {
        if (!autoSellTimeLeft.containsKey(pickaxe)) autoSellTimeLeft.put(pickaxe, getSecBetweenInterval(pickaxe.getEnchantLevel(ENCHANT_ID)));
        activePickaxes.put(pickaxe, player);
    }
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {
        activePickaxes.remove(pickaxe);
    }
    public void onUpgrade(Player player, PrisonPickaxe pickaxe, int oldLevel, int newLevel) {
        int intervalDecrease = getSecBetweenInterval(oldLevel) - getSecBetweenInterval(newLevel);
        if (!autoSellTimeLeft.containsKey(pickaxe)) autoSellTimeLeft.put(pickaxe, getSecBetweenInterval(pickaxe.getEnchantLevel(ENCHANT_ID)));
        autoSellTimeLeft.put(pickaxe, autoSellTimeLeft.get(pickaxe) - intervalDecrease);
    }
}
