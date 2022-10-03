package net.staticstudios.prisons.lootboxes.lootboxes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.lootboxes.LootBox;
import net.staticstudios.prisons.lootboxes.LootBoxType;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

public class MoneyLootBox extends LootBox {

    public MoneyLootBox(int tier) {
        this(tier, UUID.randomUUID(), true);
    }

    public MoneyLootBox(int tier, UUID uuid, boolean createItem) {
        super(tier, LootBoxType.MONEY, uuid, createItem);
    }

    @Override
    public List<Component> getLore() {
        return List.of(
                Component.empty(),
                LORE_PREFIX.append(Component.text("Open this loot box to receive a random").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("amount of money! This loot box's challenge").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("requires you to mine " + PrisonUtils.addCommasToNumber(goal) + " blocks").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("You are currently ").color(ComponentUtil.LIGHT_GRAY))
                        .append(Component.text(BigDecimal.valueOf((double) progress / goal * 100).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD))
                        .append(Component.text(" done with this!").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Tier: ").color(ComponentUtil.GREEN))
                        .append(Component.text(tier + "").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Reward: ").color(ComponentUtil.GREEN))
                        .append(Component.text("$" + PrisonUtils.addCommasToNumber(getType().getMinCurrencyReward(tier)) + " - $" + PrisonUtils.addCommasToNumber(getType().getMaxCurrencyReward(tier))).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false)
        );
    }

    @Override
    public void onClaim(Player player) {
        long moneyToAdd = PrisonUtils.randomLong(getType().getMinCurrencyReward(tier), getType().getMaxCurrencyReward(tier));
        new PlayerData(player).addMoney(moneyToAdd);
        player.sendMessage(Prefix.LOOT_BOX.append(Component.text("You've been given $" + PrisonUtils.addCommasToNumber(moneyToAdd) + "!")));
    }
}