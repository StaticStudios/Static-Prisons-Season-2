package net.staticstudios.prisons.customitems.pouches;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.customitems.currency.MultiplierVoucher;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.util.List;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;

public enum MultiPouch implements Pouch<ItemStack>, CustomItem {
    TIER_1(1, text("Multiplier Pouch"), "pouches.multi.1.amount.min", "pouches.multi.1.amount.max", "pouches.multi.1.time.min", "pouches.multi.1.time.max"),
    TIER_2(2, text("Multiplier Pouch"), "pouches.multi.2.amount.min", "pouches.multi.2.amount.max", "pouches.multi.2.time.min", "pouches.multi.2.time.max"),
    TIER_3(3, text("Multiplier Pouch"), "pouches.multi.3.amount.min", "pouches.multi.3.amount.max", "pouches.multi.3.time.min", "pouches.multi.3.time.max");

    private static final DecimalFormat formatter = new DecimalFormat("#,###.##");

    private final String TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzljN2YxZGIxY2UyMWFkMGQyYzVkMTEyNDY2ZWVhNzk4NGRhM2EwMTMzMzBlMTBhYzljMWU3OWQxNjAyNWU5MiJ9fX0=";
    private final NamespacedKey MULTI_POUCH_KEY = new NamespacedKey(StaticPrisons.getInstance(), "multi_pouch");

    private final Component name;
    private final int minAmount;
    private final int maxAmount;
    private final int minTime;
    private final int maxTime;
    private final int tier;

    MultiPouch(int tier, Component name, String configMinAmount, String configMaxAmount, String configMinTime, String configMaxTime) {
        this.name = empty().append(name).color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false);
        minAmount = getConfig().getInt(configMinAmount);
        maxAmount = getConfig().getInt(configMaxAmount);
        minTime = getConfig().getInt(configMinTime);
        maxTime = getConfig().getInt(configMaxTime);
        this.tier = tier;

        register();
    }

    @Override
    public TextColor getTitleColor() {
        return ComponentUtil.LIGHT_PURPLE;
    }

    @Override
    public void open(Player player) {
        double multiplierAmount = PrisonUtils.randomInt(minAmount, maxAmount) / 100d;
        int multiplierTime = PrisonUtils.randomInt(minTime, maxTime);

        String formattedRewardValue = "+" + new DecimalFormat("#.00").format(multiplierAmount * 100) + "% For: " + multiplierTime + " Minutes";

        Component rewardMessage = Component.empty()
                .append(name)
                .append(text(" >> ").color(ComponentUtil.DARK_GRAY).decorate(TextDecoration.BOLD))
                .append(Component.text("You won a ")
                .color(ComponentUtil.LIGHT_PURPLE)
                .append(text('+' + formatter.format(multiplierAmount) + "x")
                        .color(ComponentUtil.WHITE))
                .append(text(" multiplier for ")
                        .color(ComponentUtil.LIGHT_PURPLE))
                .append(text(PrisonUtils.formatTime(multiplierTime * 60 * 1000L))
                        .color(ComponentUtil.WHITE)));

        ItemStack reward = MultiplierVoucher.getMultiplierNote(multiplierAmount, multiplierTime);

        animateFrame(player, reward, formattedRewardValue, rewardMessage, empty(), 0, formattedRewardValue.length() + 1);
    }

    @Override
    public void addReward(Player player, ItemStack reward) {
        PlayerUtils.addToInventory(player, reward);
    }

    @Override
    public String getId() {
        return "multi_pouch_" + tier;
    }

    @Override
    public ItemStack getItem(Audience audience) {
        ItemStack item = setCustomItem(ItemUtils.createCustomSkull(TEXTURE), this);

        item.editMeta(meta -> {
            meta.displayName(name);
            meta.lore(List.of(
                    Component.empty().append(Component.text("Open to get a random multiplier!")).color(ComponentUtil.LIGHT_GRAY).decoration(TextDecoration.ITALIC, false),
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                            .append(Component.text("Tier: ")).color(ComponentUtil.LIGHT_PURPLE)
                            .append(Component.text(tier).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                            .append(Component.text("Duration: ")).color(ComponentUtil.LIGHT_PURPLE)
                            .append(Component.text(minTime + " mins - " + maxTime + " mins").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                            .append(Component.text("Multiplier: ")).color(ComponentUtil.LIGHT_PURPLE)
                            .append(Component.text("+" + minAmount + ".00% - +" + maxAmount + ".00%").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false)
            ));
            meta.getPersistentDataContainer().set(MULTI_POUCH_KEY, PersistentDataType.INTEGER, tier);
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
        if (!meta.getPersistentDataContainer().has(MULTI_POUCH_KEY, PersistentDataType.INTEGER)) {
            return false;
        }
        if (meta.getPersistentDataContainer().get(MULTI_POUCH_KEY, PersistentDataType.INTEGER) != tier) {
            return false;
        }
        e.getItem().setAmount(e.getItem().getAmount() - 1);
        open(e.getPlayer());
        return true;
    }
}
