package net.staticstudios.prisons.customitems.currency;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class MultiplierVoucher implements CustomItem {

    public static final MultiplierVoucher INSTANCE = new MultiplierVoucher();

    private static final NamespacedKey MULTI_AMOUNT = new NamespacedKey(StaticPrisons.getInstance(), "multiplier_amount");
    private static final NamespacedKey MULTI_DURATION = new NamespacedKey(StaticPrisons.getInstance(), "multiplier_duration");

    private static final DecimalFormat formatter = new DecimalFormat("#,###.##");

    MultiplierVoucher() {
        register();
    }

    public static ItemStack getMultiplierNote(double amount, int lengthInMins) {
        return INSTANCE.getItem(null, new String[]{String.valueOf(amount), String.valueOf(lengthInMins)});
    }

    @Override
    public String getId() {
        return "multiplier_voucher";
    }

    @Override
    public ItemStack getItem(Player player) {
        throw new RuntimeException("This requires args! Please use MultiplierVoucher#getItem(Player, String[])");
    }

    @Override
    public ItemStack getItem(Player player, String[] args) {
        double amount = 0;
        int duration = 0;
        try {
            amount = Double.parseDouble(args[0]);
            duration = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            StaticPrisons.log("Invalid args for multiplier voucher! Expected [double(amount), int(duration)] but got: " + Arrays.toString(args));
            if (player != null) {
                player.sendMessage("Invalid args for multiplier voucher! Expected [double(amount), int(duration)] but got: " + Arrays.toString(args));
            }
        }

        final double _amount = amount;
        final int _duration = duration;

        ItemStack item = new ItemStack(Material.EMERALD);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.BLANK
                    .append(Component.text("Multiplier: ")
                            .color(ComponentUtil.LIGHT_PURPLE)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text(formatter.format(_amount) + "x for " + PrisonUtils.formatTime(_duration * 60 * 1000L))
                            .color(ComponentUtil.WHITE)
                            .decoration(TextDecoration.BOLD, false)));
            meta.lore(List.of(
                    empty(),
                    text("Amount: ").color(LIGHT_PURPLE)
                            .append(text("+")
                                    .append(text(_amount))
                                    .append(text("x")).color(WHITE))
                            .decoration(TextDecoration.ITALIC, false),
                    text("Duration: ").color(LIGHT_PURPLE)
                            .append(text(PrisonUtils.formatTime(_duration * 60 * 1000L))
                                    .color(WHITE))
                            .decoration(TextDecoration.ITALIC, false),
                    empty(),
                    ComponentUtil.BLANK.append(text("Right click to claim!").color(YELLOW))
            ));

            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            meta.getPersistentDataContainer().set(MULTI_AMOUNT, PersistentDataType.DOUBLE, _amount);
            meta.getPersistentDataContainer().set(MULTI_DURATION, PersistentDataType.INTEGER, _duration);
        });
        setCustomItem(item, this);
        return item;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) {
            return false;
        }
        if (e.getItem() == null || !e.getItem().hasItemMeta()) {
            return false;
        }

        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(MULTI_AMOUNT, PersistentDataType.DOUBLE) ||
                !e.getItem().getItemMeta().getPersistentDataContainer().has(MULTI_DURATION, PersistentDataType.INTEGER)) {
            return false;
        }

        double amount = e.getItem().getItemMeta().getPersistentDataContainer().get(MULTI_AMOUNT, PersistentDataType.DOUBLE);
        int duration = e.getItem().getItemMeta().getPersistentDataContainer().get(MULTI_DURATION, PersistentDataType.INTEGER);

        PlayerData playerData = new PlayerData(e.getPlayer());
        playerData.addTempMoneyMultiplier(amount, duration * 60 * 1000L);

        e.getPlayer().sendMessage(Prefix.STATIC_PRISONS
                .append(Component.text("You activated a ")
                        .color(ComponentUtil.LIGHT_PURPLE)
                        .append(text('+' + formatter.format(amount) + "x")
                                .color(ComponentUtil.WHITE))
                        .append(text(" multiplier for ")
                                .color(ComponentUtil.LIGHT_PURPLE))
                        .append(text(PrisonUtils.formatTime(duration * 60 * 1000L))
                                .color(ComponentUtil.WHITE))));

        e.getItem().setAmount(e.getItem().getAmount() - 1);

        return true;
    }
}