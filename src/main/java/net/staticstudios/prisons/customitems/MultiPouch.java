package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

public enum MultiPouch implements Pouch<ItemStack> {
    TIER_1(text("Tier 1"), "pouches.multi.1.amount.min", "pouches.multi.1.amount.max", "pouches.multi.1.time.min", "pouches.multi.1.time.max"),
    TIER_2(text("Tier 2"), "pouches.multi.2.amount.min", "pouches.multi.2.amount.max", "pouches.multi.2.time.min", "pouches.multi.2.time.max"),
    TIER_3(text("Tier 3"), "pouches.multi.3.amount.min", "pouches.multi.3.amount.max", "pouches.multi.3.time.min", "pouches.multi.3.time.max");

    private final Component tier;
    private final int minAmount;
    private final int maxAmount;
    private final int minTime;
    private final int maxTime;

    MultiPouch(Component tier, String configMinAmount, String configMaxAmount, String configMinTime, String configMaxTime) {
        this.tier = tier;
        minAmount = getConfig().getInt(configMinAmount);
        maxAmount = getConfig().getInt(configMaxAmount);
        minTime = getConfig().getInt(configMinTime);
        maxTime = getConfig().getInt(configMaxTime);
    }

    @Override
    public void open(Player player) {
        double multiplierAmount = PrisonUtils.randomInt(minAmount, maxAmount) / 100d;
        int multiplierTime = PrisonUtils.randomInt(minTime, maxTime);

        String formattedRewardValue = "+" + multiplierAmount + "x For: " + multiplierTime + " Minutes";

        Component rewardMessage = text("You won ")
                .append(text(formattedRewardValue))
                .append(text(" from a Multiplier Pouch "))
                .append(tier)
                .color(GREEN);

        ItemStack reward = Vouchers.getMultiplierNote(multiplierAmount, multiplierTime);

        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(),
                () -> animateFrame(player, reward, formattedRewardValue, rewardMessage, text("x"), 0, formattedRewardValue.length() + 1),
                0);

    }

    @Override
    public void addReward(Player player, ItemStack reward) {
        PrisonUtils.Players.addToInventory(player, reward);
    }
}
