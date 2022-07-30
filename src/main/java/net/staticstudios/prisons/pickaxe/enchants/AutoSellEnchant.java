package net.staticstudios.prisons.pickaxe.enchants;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.PrisonBackpacks;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class AutoSellEnchant extends BaseEnchant {
    public AutoSellEnchant() {
        super("autoSell", "&d&lAuto Sell", 50000, BigInteger.valueOf(75), "&7Decrease the time between the", "&7automatic selling of your backpack", "&7Minimum interval: 60 seconds");
        setPickaxeLevelRequirement(0);
    }

    public static Map<PrisonPickaxe, Integer> autoSellTimeLeft = new HashMap<>();
    public static Map<PrisonPickaxe, Player> activePickaxes = new HashMap<>();

    static void onSell(PrisonPickaxe pickaxe, Player player) {
        PlayerData playerData = new PlayerData(player);
        PrisonBackpacks.sell(player, (p, r) -> {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lAuto Sell &7&o(Enchant) &8&l>> &f(x" + r.multiplier().setScale(2, RoundingMode.FLOOR) + ") Sold &b" + PrisonUtils.prettyNum(r.blocksSold()) + " &fblocks for: &a$" + PrisonUtils.prettyNum(r.soldFor())));
        });
    }
    static final int MAX_INTERVAL = 600;
    static final int STEP = 93;
    static int getSecBetweenInterval(int level) {
        return MAX_INTERVAL - level / STEP;
    }

    public static void initTimer() {
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (PrisonPickaxe pickaxe : activePickaxes.keySet()) {
                if (!pickaxe.getIsEnchantEnabled(PickaxeEnchants.AUTO_SELL)) { //The enchantment was just disabled
                    activePickaxes.remove(pickaxe);
                    autoSellTimeLeft.remove(pickaxe);
                    continue;
                }
                autoSellTimeLeft.put(pickaxe, autoSellTimeLeft.get(pickaxe) - 3);
                if (autoSellTimeLeft.get(pickaxe) <= 0) {
                    autoSellTimeLeft.put(pickaxe, getSecBetweenInterval(pickaxe.getEnchantLevel(PickaxeEnchants.AUTO_SELL.ENCHANT_ID)));
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
