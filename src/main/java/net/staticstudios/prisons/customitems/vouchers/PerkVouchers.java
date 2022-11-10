package net.staticstudios.prisons.customitems.vouchers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PerkVouchers {
    public static Voucher AUTO_SELL = new Voucher("autosell", () -> {
        ItemStack item = new ItemStack(Material.BLAZE_POWDER);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.BLANK
                    .append(Component.text("Auto-Sell Voucher"))
                    .color(ComponentUtil.GREEN)
                    .decorate(TextDecoration.BOLD));
            meta.lore(List.of(
                    ComponentUtil.BLANK
                            .append(Component.text("Claim me for the ability to auto-sell!"))
                            .color(ComponentUtil.LIGHT_GRAY),
                    Component.empty(),
                    ComponentUtil.BLANK
                            .append(Component.text("Right-click to claim!"))
                            .color(ComponentUtil.YELLOW)
            ));
            meta.addEnchant(Enchantment.LURE, 1, true);
        });
        return item;
    }, player -> {
        PlayerData playerData = new PlayerData(player);

        if (playerData.getCanExplicitlyEnableAutoSell()) {
            player.sendMessage(Prefix.AUTO_SELL
                    .append(Component.text("You can already enable auto-sell!"))
                    .color(ComponentUtil.RED));
            return false;
        }

        playerData.setCanEnableAutoSell(true);
        player.sendMessage(Prefix.AUTO_SELL
                .append(Component.text("You can now enable auto-sell!"))
                .color(ComponentUtil.GREEN));
        return true;
    });
}
