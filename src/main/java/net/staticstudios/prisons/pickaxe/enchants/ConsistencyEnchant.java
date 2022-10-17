package net.staticstudios.prisons.pickaxe.enchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConsistencyEnchant extends PickaxeEnchant {

    private static final Map<Player, ConsistentData> TOKEN_MULTIPLIERS = new HashMap<>();
    private static final Map<Player, Long> LAST_BREAK = new HashMap<>();
    private static final Map<Player, Long> TIME_MINING = new HashMap<>();

    private static long MS_TIME_OUT = 1000 * 60 * 2;
    private static long MS_TO_LEVEL_UP = 1000 * 60 * 5;
    private static double MULTI_PER_LEVEL = 0.01;
    private static int MULTIPLIERS_PER_LEVEL = 3;

    private static BukkitTask timerTask;

    private static final DecimalFormat MULTI_FORMAT = new DecimalFormat("0.00");

    public ConsistencyEnchant() {
        super(ConsistencyEnchant.class, "pickaxe-consistency");

        TOKEN_MULTIPLIERS.clear();
        LAST_BREAK.clear();
        TIME_MINING.clear();

        MS_TIME_OUT = getConfig().getLong("timeout", MS_TIME_OUT);
        MS_TO_LEVEL_UP = getConfig().getLong("level_up_time", MS_TO_LEVEL_UP);
        MULTI_PER_LEVEL = getConfig().getDouble("multi_per_level", MULTI_PER_LEVEL);
        MULTIPLIERS_PER_LEVEL = getConfig().getInt("multipliers_per_level", MULTIPLIERS_PER_LEVEL);


        if (timerTask != null) {
            timerTask.cancel();
        }

        timerTask = Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {

            List<Player> toRemove = new LinkedList<>();

            LAST_BREAK.forEach((plr, time) -> {
                if (System.currentTimeMillis() - time < MS_TIME_OUT) return;

                if (TOKEN_MULTIPLIERS.containsKey(plr)) {
                    plr.sendMessage(Component.empty()
                            .decoration(TextDecoration.ITALIC, false)
                            .append(getDisplayName())
                            .append(Component.text(" >> ")
                                    .color(ComponentUtil.DARK_GRAY)
                                    .decorate(TextDecoration.BOLD))
                            .append(Component.text("Your token multiplier of ")
                                    .color(ComponentUtil.WHITE))
                            .append(Component.text("+" + MULTI_FORMAT.format(TOKEN_MULTIPLIERS.get(plr).multi * 100) + "%")
                                    .color(ComponentUtil.RED)
                                    .decorate(TextDecoration.BOLD))
                            .append(Component.text(" has expired due to you not mining for " + (MS_TIME_OUT / 1000 / 60) + " minutes!")
                                    .color(ComponentUtil.WHITE))
                            .append(Component.text(" Total time mining: ")
                                    .color(ComponentUtil.LIGHT_GRAY)
                                    .decorate(TextDecoration.ITALIC))
                            .append(Component.text(PrisonUtils.formatTime(TIME_MINING.get(plr)))
                                    .color(ComponentUtil.GREEN)
                                    .decorate(TextDecoration.ITALIC)));
                }
                toRemove.add(plr);
            });

            toRemove.forEach(plr -> {
                TOKEN_MULTIPLIERS.remove(plr);
                LAST_BREAK.remove(plr);
                TIME_MINING.remove(plr);
            });

        }, 20, 20);
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        Player player = blockBreak.getPlayer();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();

        long currentTime = System.currentTimeMillis();
        long totalTimeMining = TIME_MINING.getOrDefault(player, 0L) + (currentTime - LAST_BREAK.getOrDefault(player, currentTime));

        //Update the player's last break time
        LAST_BREAK.put(player, currentTime);
        TIME_MINING.put(player, totalTimeMining);

        ConsistentData data = TOKEN_MULTIPLIERS.getOrDefault(player, new ConsistentData(1, 0));
        if (totalTimeMining / data.tier() < MS_TO_LEVEL_UP) return;

        //They can't have a multiplier higher than their enchant level * MULTIPLIERS_PER_LEVEL
        if (data.tier > pickaxe.getEnchantmentLevel(ConsistencyEnchant.class) * MULTIPLIERS_PER_LEVEL) return;

        data = new ConsistentData(data.tier() + 1, data.multi() + MULTI_PER_LEVEL);
        TOKEN_MULTIPLIERS.put(player, data);

        Component message = Component.empty().decoration(TextDecoration.ITALIC, false)
                .append(getDisplayName())
                .append(Component.text(" >> ")
                        .color(ComponentUtil.DARK_GRAY)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text("+" + MULTI_FORMAT.format(MULTI_PER_LEVEL * 100) + "% Token Multiplier")
                        .color(ComponentUtil.GOLD))
                .append(Component.text(" due to your consistent mining activity! Current multiplier: ")
                        .color(ComponentUtil.WHITE))
                .append(Component.text("+" + MULTI_FORMAT.format(data.multi() * 100) + "%!")
                        .color(ComponentUtil.GREEN))
                .append(Component.text(" Total time mining: ")
                        .color(ComponentUtil.LIGHT_GRAY)
                        .decorate(TextDecoration.ITALIC))
                .append(Component.text(PrisonUtils.formatTime(totalTimeMining))
                        .color(ComponentUtil.GREEN)
                        .decorate(TextDecoration.ITALIC));

        if (data.tier() - 1 >= pickaxe.getEnchantmentLevel(ConsistencyEnchant.class) * MULTIPLIERS_PER_LEVEL) {
            message = message.append(Component.newline())
                    .append(Component.newline())
                    .append(Component.text("Your Token multiplier has maxed out! Upgrade the ")
                            .color(ComponentUtil.AQUA)
                            .decorate(TextDecoration.ITALIC))
                    .append(getDisplayName())
                    .append(Component.text(" enchantment to increase your the max multiplier you can get!")
                            .color(ComponentUtil.AQUA)
                            .decorate(TextDecoration.ITALIC));
        }

        player.sendMessage(message);
        blockBreak.stats().setTokenMultiplier(blockBreak.stats().getTokenMultiplier() + data.multi());
    }

    private record ConsistentData(int tier, double multi) {
    }
}
