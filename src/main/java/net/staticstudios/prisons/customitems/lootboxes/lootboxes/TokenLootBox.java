package net.staticstudios.prisons.customitems.lootboxes.lootboxes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.customitems.lootboxes.LootBox;
import net.staticstudios.prisons.customitems.lootboxes.LootBoxType;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TokenLootBox extends LootBox {

    public TokenLootBox(int tier){
        this(tier, UUID.randomUUID(), true);
    }

    public TokenLootBox(int tier, UUID uuid, boolean createItem) {
        super(tier, LootBoxType.TOKEN, uuid, createItem, 51);
    }

    public TokenLootBox(ConfigurationSection section) {
        this(section.getInt("tier"),
                UUID.fromString(Objects.requireNonNull(section.getString("uuid"))),
                false);
    }

    @Override
    public List<Component> getLore() {
        return List.of(
                LORE_PREFIX,
                LORE_PREFIX.append(Component.text("Open this loot box to receive a random").color(ComponentUtil.LIGHT_GRAY)),
                LORE_PREFIX.append(Component.text("amount of tokens! This loot box's challenge").color(ComponentUtil.LIGHT_GRAY)),
                LORE_PREFIX.append(Component.text("requires you to mine " + PrisonUtils.addCommasToNumber(goal) + " blocks").color(ComponentUtil.LIGHT_GRAY)),
                LORE_PREFIX.append(Component.text("You are currently ").color(ComponentUtil.LIGHT_GRAY))
                        .append(Component.text(BigDecimal.valueOf((double) progress / goal * 100).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD))
                        .append(Component.text(" done with this!").color(ComponentUtil.LIGHT_GRAY)),
                LORE_PREFIX,
                LORE_PREFIX.append(Component.text("Tier: ").color(ComponentUtil.GOLD))
                        .append(Component.text(tier + "").color(ComponentUtil.WHITE)),
                LORE_PREFIX.append(Component.text("Reward: ").color(ComponentUtil.GOLD))
                        .append(Component.text(PrisonUtils.addCommasToNumber(getType().getMinCurrencyReward(tier)) + " - " + PrisonUtils.addCommasToNumber(getType().getMaxCurrencyReward(tier)) + " Tokens").color(ComponentUtil.WHITE))
        );
    }

    @Override
    public void onClaim(Player player) {
        long tokensToAdd = PrisonUtils.randomLong(getType().getMinCurrencyReward(tier), getType().getMaxCurrencyReward(tier));
        new PlayerData(player).addTokens(tokensToAdd);
        player.sendMessage(Prefix.LOOT_BOX.append(Component.text("You've been given " + PrisonUtils.addCommasToNumber(tokensToAdd) + " tokens!")));
    }
}
