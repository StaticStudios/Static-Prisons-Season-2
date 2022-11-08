package net.staticstudios.prisons.customitems.vouchers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.old.Kits;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitVouchers {
    public static Voucher TIER_1 = new Voucher("kit_tier_1",
            () -> makeItem(Material.IRON_CHESTPLATE, "Kit Tier 1", ComponentUtil.RED),
            player -> {
                Kits.TIER1.addItemsToPlayersInventory(player);
                return true;
            });

    public static Voucher TIER_2 = new Voucher("kit_tier_2",
            () -> makeItem(Material.IRON_CHESTPLATE, "Kit Tier 2", ComponentUtil.RED),
            player -> {
                Kits.TIER2.addItemsToPlayersInventory(player);
                return true;
            });

    public static Voucher TIER_3 = new Voucher("kit_tier_3",
            () -> makeItem(Material.GOLDEN_CHESTPLATE, "Kit Tier 3", ComponentUtil.RED),
            player -> {
                Kits.TIER3.addItemsToPlayersInventory(player);
                return true;
            });

    public static Voucher TIER_4 = new Voucher("kit_tier_4",
            () -> makeItem(Material.GOLDEN_CHESTPLATE, "Kit Tier 4", ComponentUtil.RED),
            player -> {
                Kits.TIER4.addItemsToPlayersInventory(player);
                return true;
            });

    public static Voucher TIER_5 = new Voucher("kit_tier_5",
            () -> makeItem(Material.DIAMOND_CHESTPLATE, "Kit Tier 5", ComponentUtil.RED),
            player -> {
                Kits.TIER5.addItemsToPlayersInventory(player);
                return true;
            });

    public static Voucher TIER_6 = new Voucher("kit_tier_6",
            () -> makeItem(Material.NETHERITE_CHESTPLATE, "Kit Tier 6", ComponentUtil.RED),
            player -> {
                Kits.TIER6.addItemsToPlayersInventory(player);
                return true;
            });

    public static Voucher POTIONS = new Voucher("kit_potions",
            () -> makeItem(Material.SPLASH_POTION, "Potion Kit", ComponentUtil.GREEN),
            player -> {
                Kits.POTION.addItemsToPlayersInventory(player);
                return true;
            });

    public static Voucher WEAPONS = new Voucher("kit_weapons",
            () -> makeItem(Material.DIAMOND_SWORD, "Weapons Kit", ComponentUtil.DARK_RED),
            player -> {
                Kits.WEAPON.addItemsToPlayersInventory(player);
                return true;
            });




    private static ItemStack makeItem(Material icon, String kitDisplayName, TextColor color) {
        ItemStack item = new ItemStack(icon);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.BLANK
                    .append(Component.text(kitDisplayName))
                    .color(color)
                    .decorate(TextDecoration.BOLD));
            meta.lore(List.of(
                    ComponentUtil.BLANK
                            .append(Component.text("Claim me to get 1x " + kitDisplayName + "!"))
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
}
