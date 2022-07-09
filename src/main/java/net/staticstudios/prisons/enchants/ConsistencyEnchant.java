package net.staticstudios.prisons.enchants;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ConsistencyEnchant extends BaseEnchant {
    public ConsistencyEnchant() {
        super("consistency", "&e&lConsistency", 10, BigInteger.valueOf(1000000), "&7+1% token multi every 2 mins of consistent mining", "&7Increases your max multi by 5% for every level", "&7Multiplier expires after 2 minutes of not mining");
        setPickaxeLevelRequirement(60);
        setPlayerLevelRequirement(25);
    }


    static final int MAX_POSSIBLE_MS_BETWEEN_BREAKS = 1000 * 60 * 2;
    static final int levelUpTime = 1000 * 60 * 2;
    static final double MULTI_INCREASE_PER_LEVEL = 0.01;
    static String PREFIX = ChatColor.YELLOW + "" + ChatColor.BOLD + "Consistency " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">> " + ChatColor.RESET;

    public static void init() {
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Map.Entry<Player, Long> entry : lastTimeBroken.entrySet()) {
                if (System.currentTimeMillis() - entry.getValue() > MAX_POSSIBLE_MS_BETWEEN_BREAKS) {
                    if (tokenMultiplier.containsKey(entry.getKey())) {
                        entry.getKey().sendMessage(PREFIX + ChatColor.WHITE + "Your token multiplier of" + ChatColor.RED + " +" + new DecimalFormat("0.00").format(tokenMultiplier.get(entry.getKey()).currentMulti * 100) + "%" +
                                ChatColor.WHITE + " has expired due to you not mining for 2 minutes!" +
                                ChatColor.GRAY + ChatColor.ITALIC + " Total time consistently mining: " + PrisonUtils.formatTime(msMining.get(entry.getKey())));
                        tokenMultiplier.remove(entry.getKey());
                    }
                    msMining.remove(entry.getKey());
                    lastTimeBroken.remove(entry.getKey());
                    return;
                }
            }
        }, 20, 20);
    }

    static Map<Player, ConsistentData> tokenMultiplier = new HashMap<>();
    static Map<Player, Long> lastTimeBroken = new HashMap<>();
    static Map<Player, Long> msMining = new HashMap<>();

    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (!msMining.containsKey(bb.player)) msMining.put(bb.player, 0L);
        if (!lastTimeBroken.containsKey(bb.player)) lastTimeBroken.put(bb.player, System.currentTimeMillis());
        long totalTimeMining = msMining.get(bb.player) + (System.currentTimeMillis() - lastTimeBroken.get(bb.player));
        lastTimeBroken.put(bb.player, System.currentTimeMillis());
        msMining.put(bb.player, totalTimeMining);
        ConsistentData data = tokenMultiplier.getOrDefault(bb.player, new ConsistentData(1, 0));
        if (totalTimeMining / data.tier >= levelUpTime) {
            if (data.tier > bb.pickaxe.getEnchantLevel(ENCHANT_ID) * 5) return;
            data = new ConsistentData(data.tier + 1, data.currentMulti + MULTI_INCREASE_PER_LEVEL);
            tokenMultiplier.put(bb.player, data);
            bb.player.sendMessage(PREFIX + ChatColor.WHITE +
                    ChatColor.GOLD + "+1.00% Token Multiplier" + ChatColor.WHITE + " due to your consistent mining activity! Current Multiplier:" + ChatColor.GREEN + " +" +
                    new DecimalFormat("0.00").format(data.currentMulti * 100) + "%! " + ChatColor.GRAY + ChatColor.ITALIC + "Consistent time mining: " + PrisonUtils.formatTime(totalTimeMining));
            if (data.tier - 1 == bb.pickaxe.getEnchantLevel(ENCHANT_ID) * 5) bb.player.sendMessage(PREFIX + ChatColor.AQUA + "Your Token multiplier has maxed out! Upgrading the consistency enchant will increase your max multiplier!");
        }
        bb.tokenMultiplier += data.currentMulti;
    }

    static class ConsistentData {
        int tier = 0;
        double currentMulti = 0;
        public ConsistentData(int tier, double currentMulti) {
            this.tier = tier;
            this.currentMulti = currentMulti;
        }
    }
}
