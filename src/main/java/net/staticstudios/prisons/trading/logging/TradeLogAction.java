package net.staticstudios.prisons.trading.logging;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.trading.domain.Trade;
import net.staticstudios.prisons.utils.ItemUtils;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class TradeLogAction {

    private static final String SPACING = "    ";
    private static final String INDENT = SPACING + "| ";
    private static final String DOUBLE_INDENT = SPACING + INDENT;
    private static final String TRIPLE_INDENT = SPACING + DOUBLE_INDENT;


    private static final char NEW_LINE = '\n';

    public static String startTrade(Trade trade) {
        return getBuilder(TradeAction.REQUEST)
                .append(' ')
                .append(trade.initiator().getName())
                .append(NEW_LINE)
                .append(getBuilder(TradeAction.ACCEPT))
                .append(' ')
                .append(trade.trader().getName())
                .append(NEW_LINE)
                .append(getBuilder(TradeAction.BEGIN))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .toString();
    }

    public static String addItem(Player player, ItemStack item) {
        return getBuilder(TradeAction.ADD_ITEM)
                .append(' ')
                .append(player.getName())
                .append(':')
                .append(NEW_LINE)
                .append(getItemSection(item))
                .toString();
    }

    public static String removeItem(Player player, ItemStack item) {
        return getBuilder(TradeAction.REMOVE_ITEM)
                .append(' ')
                .append(player.getName())
                .append(':')
                .append(NEW_LINE)
                .append(getItemSection(item))
                .toString();
    }


    private static StringBuilder getBuilder(TradeAction action) {
        return new StringBuilder('[' + action.getName() + ']');
    }

    private static StringBuilder getItemSection(ItemStack item) {
        StringBuilder builder = new StringBuilder();
        builder.append(INDENT)
                .append("material: ")
                .append(item.getType())
                .append(NEW_LINE);

        builder.append(INDENT)
                .append("amount: ")
                .append(item.getAmount())
                .append(NEW_LINE);

        if (!item.hasItemMeta()) {
            return builder;
        }

        builder.append(INDENT)
                .append("name: ")
                .append(StaticPrisons.miniMessage().serialize(Objects.requireNonNull(item.getItemMeta().displayName())))
                .append(NEW_LINE);

        List<Component> lore = item.getItemMeta().lore();
        if (lore != null && !lore.isEmpty()) {
            builder.append(INDENT)
                    .append("lore: ")
                    .append("[ ")
                    .append(Strings.join(
                            lore.stream()
                                    .map(StaticPrisons.miniMessage()::serialize)
                                    .map(s -> '"' + s + '"')
                                    .iterator(),
                            ','
                    ))
                    .append(" ]")
                    .append(NEW_LINE);
        }

        builder.append(INDENT)
                .append("base64: ")
                .append(ItemUtils.toBase64(item))
                .append(NEW_LINE);

        builder.append(getPrisonPickaxeSection(item));

        return builder;
    }

    private static StringBuilder getPrisonPickaxeSection(ItemStack item) {
        if (!PrisonPickaxe.checkIsPrisonPickaxe(item)) {
            return new StringBuilder();
        }
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(item);

        StringBuilder builder = new StringBuilder(INDENT)
                .append("PrisonPickaxe: ")
                .append(NEW_LINE);

        builder.append(DOUBLE_INDENT)
                .append("uuid: ")
                .append(pickaxe.getUid())
                .append(NEW_LINE)
                .append(DOUBLE_INDENT)
                .append("name: ")
                .append(StaticPrisons.miniMessage().serialize(pickaxe.getName()))
                .append(NEW_LINE)
                .append("level: ")
                .append(pickaxe.getLevel())
                .append(NEW_LINE)
                .append(DOUBLE_INDENT)
                .append("xp: ")
                .append(pickaxe.getXp())
                .append(NEW_LINE)
                .append(DOUBLE_INDENT)
                .append("raw_blocks_mined: ")
                .append(pickaxe.getRawBlocksBroken())
                .append(NEW_LINE)
                .append(DOUBLE_INDENT)
                .append("blocks_broken: ")
                .append(pickaxe.getBlocksBroken())
                .append(NEW_LINE)
                .append(DOUBLE_INDENT)
                .append("enchants: ")
                .append(NEW_LINE);

        pickaxe.getEnchantmentMap().forEach((clazz, holder) -> builder.append(TRIPLE_INDENT)
                .append(holder.enchantment().getName())
                .append(": ")
                .append(holder.level())
                .append(" | ")
                .append("Tier: ")
                .append(pickaxe.getEnchantmentTier(clazz))
                .append(NEW_LINE));

        return builder;
    }
}
