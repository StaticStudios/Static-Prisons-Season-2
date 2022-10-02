package net.staticstudios.prisons.lootboxes.lootboxes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.lootboxes.LootBox;
import net.staticstudios.prisons.lootboxes.LootBoxType;
import net.staticstudios.prisons.lootboxes.rewards.LootBoxItemReward;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class MineBombLootBox extends LootBox {

    public MineBombLootBox(int tier) {
        this(tier, UUID.randomUUID(), true);
    }

    public MineBombLootBox(int tier, UUID uuid, boolean createItem) {
        super(tier, LootBoxType.MINE_BOMB, uuid, createItem);
    }

    @Override
    public List<Component> getLore() {
        return List.of(
                Component.empty(),
                LORE_PREFIX.append(Component.text("Open this loot box to receive a random").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("mine bomb! This loot box's challenge").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("requires you to mine " + PrisonUtils.addCommasToNumber(goal) + " blocks").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("You are currently ").color(ComponentUtil.LIGHT_GRAY))
                        .append(Component.text(BigDecimal.valueOf((double) progress / goal * 100).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD))
                        .append(Component.text(" done with this!").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Tier: ").color(ComponentUtil.RED))
                        .append(Component.text(tier + "").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Reward: ").color(ComponentUtil.RED))
                        .append(Component.text("AAAAA").decorate(TextDecoration.OBFUSCATED).color(ComponentUtil.WHITE))
                        .append(Component.text(" Mine Bomb").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false)
        );
    }

    @Override
    public void onClaim(Player player) {
        LootBoxItemReward reward = getType().getItemRewards(tier).getRandom();
        ItemStack item = Objects.requireNonNull(CustomItems.getItem(reward.id(), player));
        item.setAmount(reward.amount());
        PlayerUtils.addToInventory(player, item);
        player.sendMessage(getPrefix().append(Component.text("You've been given " + reward.amount() + "x "))
                .append(Objects.requireNonNull(item.getItemMeta().displayName())));
    }

}
