package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Locale;

public enum TokenPouch implements Pouch<Long>{
    TIER_1(Component.text("Tier 1"), "pouches.token.1.min", "pouches.token.1.max"),
    TIER_2(Component.text("Tier 2"), "pouches.token.2.min", "pouches.token.2.max"),
    TIER_3(Component.text("Tier 3"), "pouches.token.3.min", "pouches.token.3.max");

    private final Component tier;
    private final long minValue;
    private final long maxValue;

    TokenPouch(Component tier, String configMinValue, String configMaxValue) {
        this.tier = tier;
        this.minValue = StaticPrisons.getInstance().getConfig().getLong(configMinValue);
        this.maxValue = StaticPrisons.getInstance().getConfig().getLong(configMaxValue);
    }

    @Override
    public void open(Player player) {
        long reward = PrisonUtils.randomLong(minValue, maxValue);

        String formattedRewardValue = NumberFormat.getNumberInstance(Locale.US).format(reward);

        Component rewardMessage = Component.text("You won ").append(Component.text(formattedRewardValue)).append(Component.text(" from a Token Pouch").append(tier).color(NamedTextColor.GREEN));

        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(),
                () -> animateFrame(player, reward, formattedRewardValue, rewardMessage, PouchTypes.TOKEN,0, formattedRewardValue.length() + 1),
                0);
    }

    @Override
    public void addReward(Player player, Long reward) {
        new PlayerData(player).addTokens(reward);
    }
}
