package net.staticstudios.prisons.customitems.vouchers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RankVouchers {
    public static Voucher WARRIOR = new Voucher("warrior_rank",
            () -> makeItem(Material.IRON_HELMET, "Warrior", ComponentUtil.AQUA),
            player -> tryToClaim(player, "warrior", "Warrior"));

    public static Voucher MASTER = new Voucher("master_rank",
            () -> makeItem(Material.GOLDEN_HELMET, "Master", ComponentUtil.YELLOW),
            player -> tryToClaim(player, "master", "Master"));

    public static Voucher MYTHIC = new Voucher("mythic_rank",
            () -> makeItem(Material.DIAMOND_HELMET, "Mythic", ComponentUtil.RED),
            player -> tryToClaim(player, "mythic", "Mythic"));

    public static Voucher STATIC = new Voucher("static_rank",
            () -> makeItem(Material.NETHERITE_HELMET, "Static", ComponentUtil.BLUE),
            player -> tryToClaim(player, "static", "Static"));

    public static Voucher STATICP = new Voucher("staticp_rank", () -> {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.BLANK
                    .append(StaticPrisons.miniMessage().deserialize("<gradient:#00fbec:#042afa>Static+ Rank Voucher"))
                    .decorate(TextDecoration.BOLD));
            meta.lore(List.of(
                    ComponentUtil.BLANK
                            .append(Component.text("Claim me to get the Static+ rank!"))
                            .color(ComponentUtil.LIGHT_GRAY),
                    Component.empty(),
                    ComponentUtil.BLANK
                            .append(Component.text("Right-click to claim!"))
                            .color(ComponentUtil.YELLOW)
            ));
            meta.addEnchant(Enchantment.LURE, 1, true);
        });
        return item;
    },
            player -> tryToClaim(player, "staticp", "Static+"));


    private static ItemStack makeItem(Material icon, String rankDisplayName, TextColor color) {
        ItemStack item = new ItemStack(icon);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.BLANK
                    .append(Component.text(rankDisplayName + " Rank Voucher"))
                    .color(color)
                    .decorate(TextDecoration.BOLD));
            meta.lore(List.of(
                    ComponentUtil.BLANK
                            .append(Component.text("Claim me to get the " + rankDisplayName + " rank!"))
                            .color(ComponentUtil.LIGHT_GRAY),
                    Component.empty(),
                    ComponentUtil.BLANK
                            .append(Component.text("Right-click to claim!"))
                            .color(ComponentUtil.YELLOW)
            ));
            meta.addEnchant(Enchantment.LURE, 1, true);
        });
        return item;
    }

    private static boolean tryToClaim(Player player, String rankId, String rankDisplayName) {
        PlayerData playerData = new PlayerData(player);

        if (playerData.getPlayerRanks().contains(rankId)) {
            player.sendMessage(Prefix.RANKS
                    .append(Component.text("You already have this rank!"))
                    .color(ComponentUtil.RED));
            return false;
        }

        playerData.setPlayerRank(rankId);
        player.sendMessage(Prefix.RANKS
                .append(Component.text("You've been given " + rankDisplayName + " rank!"))
                .color(ComponentUtil.GREEN));
        return true;
    }
}
