package net.staticstudios.prisons.customitems.pouches;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.handler.CustomItem;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

public enum MoneyPouch implements Pouch<Long>, CustomItem {
    TIER_1(1, text("Money Pouch"), "pouches.money.1.min", "pouches.money.1.max"),
    TIER_2(2, text("Money Pouch"), "pouches.money.2.min", "pouches.money.2.max"),
    TIER_3(3, text("Money Pouch"), "pouches.money.3.min", "pouches.money.3.max");

    private final String TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc1ZTc5NDg4ODQ3YmEwMmQ1ZTEyZTcwNDJkNzYyZTg3Y2UwOGZhODRmYjg5YzM1ZDZiNWNjY2I4YjlmNGJlZCJ9fX0=";
    private final NamespacedKey MONEY_POUCH_KEY = new NamespacedKey(StaticPrisons.getInstance(), "money_pouch");


    private final Component name;
    private final long minValue;
    private final long maxValue;
    private final int tier;

    MoneyPouch(int tier, Component name, String configMinValue, String configMaxValue) {
        this.name = empty().append(name).color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false);
        this.minValue = getConfig().getLong(configMinValue);
        this.maxValue = getConfig().getLong(configMaxValue);
        this.tier = tier;

        register();
    }

    @Override
    public void open(Player player) {
        long reward = PrisonUtils.randomLong(minValue, maxValue);

        String formattedRewardValue = "$" + NumberFormat.getNumberInstance(Locale.US).format(reward);

        Component rewardMessage = Component.empty()
                .append(name)
                .append(text(" >> ").color(ComponentUtil.DARK_GRAY).decorate(TextDecoration.BOLD))
                .append(text("You won "))
                .append(text("$" + PrisonUtils.prettyNum(reward)).color(GREEN));

        animateFrame(player, reward, formattedRewardValue, rewardMessage, empty(), 0, formattedRewardValue.length() + 1);
    }

    @Override
    public void addReward(Player player, Long reward) {
        new PlayerData(player).addMoney(reward);
    }

    @Override
    public String getId() {
        return "money_pouch_" + tier;
    }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack item = setCustomItem(ItemUtils.createCustomSkull(TEXTURE), this);
        item.editMeta(meta -> {
            meta.displayName(name);
            meta.lore(List.of(
                    Component.empty().append(Component.text("Open to get a random amount of money!")).color(ComponentUtil.LIGHT_GRAY).decoration(TextDecoration.ITALIC, false),
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD))
                            .append(Component.text("Tier: ")).color(ComponentUtil.GREEN)
                            .append(Component.text(tier).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD))
                            .append(Component.text("Reward: ")).color(ComponentUtil.GREEN)
                            .append(Component.text("$" + PrisonUtils.prettyNum(minValue) + " - $" + PrisonUtils.prettyNum(maxValue)).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false)
            ));
            meta.getPersistentDataContainer().set(MONEY_POUCH_KEY, PersistentDataType.INTEGER, tier);
        });
        return item;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return false;
        }
        ItemMeta meta = e.getItem().getItemMeta();
        if (meta == null) {
            return false;
        }
        if (!meta.getPersistentDataContainer().has(MONEY_POUCH_KEY, PersistentDataType.INTEGER)) {
            return false;
        }
        if (meta.getPersistentDataContainer().get(MONEY_POUCH_KEY, PersistentDataType.INTEGER) != tier) {
            return false;
        }
        e.getItem().setAmount(e.getItem().getAmount() - 1);
        open(e.getPlayer());
        return true;
    }
}
