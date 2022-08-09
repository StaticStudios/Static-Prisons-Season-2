package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum MultiPouch implements Pouch<ItemStack> {
    TIER_1(Component.text("Tier 1"), "pouches.multi.1.amount.min", "pouches.multi.1.amount.max", "pouches.multi.1.time.min", "pouches.multi.1.time.max"),
    TIER_2(Component.text("Tier 2"), "pouches.multi.2.amount.min", "pouches.multi.2.amount.max", "pouches.multi.2.time.min", "pouches.multi.2.time.max"),
    TIER_3(Component.text("Tier 3"), "pouches.multi.3.amount.min", "pouches.multi.3.amount.max", "pouches.multi.3.time.min", "pouches.multi.3.time.max");

    private final Component tier;
    private final int minAmount;
    private final int maxAmount;
    private final int minTime;
    private final int maxTime;

    MultiPouch(Component tier, String configMinAmount, String configMaxAmount, String configMinTime, String configMaxTime) {
        this.tier = tier;
        minAmount = StaticPrisons.getInstance().getConfig().getInt(configMinAmount);
        maxAmount = StaticPrisons.getInstance().getConfig().getInt(configMaxAmount);
        minTime = StaticPrisons.getInstance().getConfig().getInt(configMinTime);
        maxTime = StaticPrisons.getInstance().getConfig().getInt(configMaxTime);
    }


    @Override
    public void open(Player player) {
        int multiplierAmount = PrisonUtils.randomInt(minAmount, maxAmount);
        int multiplierTime = PrisonUtils.randomInt(minTime, maxTime);

        String formattedRewardValue = "+" + (multiplierAmount / 100d) + "x For: " + multiplierTime + " Minutes";

        Component rewardMessage = Component.text("You won ").append(Component.text(formattedRewardValue)).append(Component.text(" from a Multiplier Pouch").append(tier).color(NamedTextColor.GREEN));

        ItemStack reward = Vouchers.getMultiplierNote(multiplierAmount, multiplierTime);

        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(),
                () -> animateFrame(player, reward, formattedRewardValue, rewardMessage, PouchTypes.MULTIPLIER,0, formattedRewardValue.length() + 1),
                0);

    }

    @Override
    public void addReward(Player player, ItemStack reward) {
        PrisonUtils.Players.addToInventory(player, reward);
    }
}
