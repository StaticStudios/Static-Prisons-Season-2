package net.staticstudios.prisons.pickaxe.enchants;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ConsistencyEnchant extends BaseEnchant {
    public ConsistencyEnchant() {
        super("consistency", "&e&lConsistency", 15, 1_000_000, "&7+1% token multi every 2 mins of consistent mining", "&7Increases your max multi by 3% for every level", "&7Multiplier expires after 2 minutes of not mining");
        setPickaxeLevelRequirement(62);

        setTiers(
                new EnchantTier(1, 0),
                new EnchantTier(2, 5),
                new EnchantTier(3, 10),
                new EnchantTier(4, 15),
                new EnchantTier(5, 20),
                new EnchantTier(6, 25),
                new EnchantTier(7, 30),
                new EnchantTier(8, 35),
                new EnchantTier(9, 40),
                new EnchantTier(10, 50),
                new EnchantTier(11, 60),
                new EnchantTier(12, 75),
                new EnchantTier(13, 100),
                new EnchantTier(14, 125),
                new EnchantTier(15, 150)
        );
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

    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.getPlayer() == null) return;
        if (!msMining.containsKey(blockBreak.getPlayer())) { //If the player has not been mining with consistency, start their timer
            msMining.put(blockBreak.getPlayer(), 0L);
        }
        if (!lastTimeBroken.containsKey(blockBreak.getPlayer())) { //If the player has not been mining with consistency, start their timer
            lastTimeBroken.put(blockBreak.getPlayer(), System.currentTimeMillis());
        }
        long totalTimeMining = msMining.get(blockBreak.getPlayer()) + (System.currentTimeMillis() - lastTimeBroken.get(blockBreak.getPlayer()));

        lastTimeBroken.put(blockBreak.getPlayer(), System.currentTimeMillis()); //Update the last time the player broke a block (now)
        msMining.put(blockBreak.getPlayer(), totalTimeMining); //Update the total time the player has been mining

        ConsistentData data = tokenMultiplier.getOrDefault(blockBreak.getPlayer(), new ConsistentData(1, 0));
        if (totalTimeMining / data.tier >= levelUpTime) { //Check if the player has been mining enough to increase their multiplier
            if (data.tier > blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID) * 3) return; //They can't have a multiplier higher than their enchant level * 5

            data = new ConsistentData(data.tier + 1, data.currentMulti + MULTI_INCREASE_PER_LEVEL);
            tokenMultiplier.put(blockBreak.getPlayer(), data);

            blockBreak.messagePlayer(PREFIX +
                    "&6+1.00% Token Multiplier &fdue to your consistent mining activity! Current Multiplier:&a +" +
                    new DecimalFormat("0.00").format(data.currentMulti * 100) + "%! &7&oConsistent time mining: " + PrisonUtils.formatTime(totalTimeMining));
            if (data.tier - 1 == blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID) * 3) {
                blockBreak.messagePlayer(PREFIX + "&bYour Token multiplier has maxed out! Upgrading the consistency enchant will increase your max multiplier!");
            }
        }
        blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + data.currentMulti);
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
