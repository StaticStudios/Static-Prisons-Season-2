package net.staticstudios.prisons.pickaxe.enchants;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class AutoSellEnchant extends PickaxeEnchant {

    private static final Map<PrisonPickaxe, Integer> PICKAXE_TIME_LEFT = new HashMap<>();
    private static final Map<PrisonPickaxe, Player> ACTIVE_PICKAXES = new HashMap<>();
    private static int MAX_INTERVAL = 600;
    private static int STEP = 93;

    private static BukkitTask timerTask;

    public AutoSellEnchant() {
        super(AutoSellEnchant.class, "pickaxe-autosell");

        PICKAXE_TIME_LEFT.clear();
        ACTIVE_PICKAXES.clear();

        MAX_INTERVAL = getConfig().getInt("max_interval", MAX_INTERVAL);
        STEP = getConfig().getInt("step", STEP);

        if (timerTask != null) {
            timerTask.cancel();
        }

        timerTask = Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (PrisonPickaxe pickaxe : ACTIVE_PICKAXES.keySet()) {

                //Check if the enchantment has been just disabled
                if (pickaxe.isDisabled(AutoSellEnchant.class)) {
                    ACTIVE_PICKAXES.remove(pickaxe);
                    PICKAXE_TIME_LEFT.remove(pickaxe);
                    continue;
                }

                PICKAXE_TIME_LEFT.put(pickaxe, PICKAXE_TIME_LEFT.get(pickaxe) - 3);
                if (PICKAXE_TIME_LEFT.get(pickaxe) <= 0) {
                    PICKAXE_TIME_LEFT.put(pickaxe, getSecBetweenInterval(pickaxe.getEnchantmentLevel(AutoSellEnchant.class)));
                    onSell(ACTIVE_PICKAXES.get(pickaxe));
                }
            }
        }, 20, 20 * 3);
    }

    private static int getSecBetweenInterval(int level) {
        return MAX_INTERVAL - level / STEP;
    }

    private static void onSell(Player player) {
        BackpackManager.sell(player, (p, r) -> {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lAuto Sell &7&o(Enchant) &8&l>> &f(x" + r.multiplier().setScale(2, RoundingMode.FLOOR) + ") Sold &b" + PrisonUtils.prettyNum(r.blocksSold()) + " &fblocks for: &a$" + PrisonUtils.prettyNum(r.soldFor())));
        });
    }

    @Override
    public void onHold(Enchantable enchantable, Player player) {
        PrisonPickaxe pickaxe = (PrisonPickaxe) enchantable;
        if (!PICKAXE_TIME_LEFT.containsKey(pickaxe)) {
            PICKAXE_TIME_LEFT.put(pickaxe, getSecBetweenInterval(pickaxe.getEnchantmentLevel(AutoSellEnchant.class)));
        }
        ACTIVE_PICKAXES.put(pickaxe, player);
    }

    @Override
    public void onUnHold(Enchantable enchantable, Player player) {
        ACTIVE_PICKAXES.remove((PrisonPickaxe) enchantable);
    }

    @Override
    public void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel) {
        PrisonPickaxe pickaxe = (PrisonPickaxe) enchantable;
        int intervalDecrease = getSecBetweenInterval(oldLevel) - getSecBetweenInterval(newLevel);
        if (!PICKAXE_TIME_LEFT.containsKey(pickaxe)) {
            PICKAXE_TIME_LEFT.put(pickaxe, getSecBetweenInterval(pickaxe.getEnchantmentLevel(AutoSellEnchant.class)));
        }
        PICKAXE_TIME_LEFT.put(pickaxe, PICKAXE_TIME_LEFT.get(pickaxe) - intervalDecrease);
    }
}
