package net.staticstudios.prisons.customitems.old;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class Vouchers { //todo: phase out this class and use CustomItem instead
    private static final Voucher MONEY_NOTE = new Voucher("moneyNote", Material.PAPER, ChatColor.GREEN + "" + ChatColor.BOLD + "Money Note") {
        @Override
        void onClaim(Player player) {
            int count = 1;
            if (player.isSneaking()) count = player.getInventory().getItemInMainHand().getAmount();
            long value = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.LONG);
            new PlayerData(player).addMoney(value * count);
            player.sendMessage(ChatColor.GREEN + "You have just claimed a money note worth $" + PrisonUtils.addCommasToNumber(value * count) + "!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - count);
        }
    };

    public static ItemStack getMoneyNote(String creatorName, long value) {
        ItemStack voucher = MONEY_NOTE.item;
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Created By: " + ChatColor.WHITE + creatorName);
        lore.add(ChatColor.AQUA + "Redeem Amount: " + ChatColor.WHITE + "$" + PrisonUtils.addCommasToNumber(value));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to claim!");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shift-right click to claim a full stack!");
        ItemMeta meta = voucher.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Money Note: " + ChatColor.WHITE + "$" + PrisonUtils.prettyNum(value));
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.LONG, value);
        voucher.setItemMeta(meta);
        return voucher;
    }

    private static final Voucher TOKEN_NOTE = new Voucher("tokenNote", Material.SUNFLOWER, ChatColor.GOLD + "" + ChatColor.BOLD + "Token Note") {
        @Override
        void onClaim(Player player) {
            int count = 1;
            if (player.isSneaking()) count = player.getInventory().getItemInMainHand().getAmount();
            long value = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.LONG);
            new PlayerData(player).addTokens(value * count);
            player.sendMessage(ChatColor.GREEN + "You have just claimed a token note worth " + PrisonUtils.addCommasToNumber(value * count) + " tokens!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - count);
        }
    };

    public static ItemStack getTokenNote(String creatorName, long value) {
        ItemStack voucher = TOKEN_NOTE.item;
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Created By: " + ChatColor.WHITE + creatorName);
        lore.add(ChatColor.AQUA + "Redeem Amount: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(value));
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to claim!");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shift-right click to claim a full stack!");
        ItemMeta meta = voucher.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Token Note: " + ChatColor.WHITE + PrisonUtils.prettyNum(value));
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.LONG, value);
        voucher.setItemMeta(meta);
        return voucher;
    }


    private static final Voucher MULTIPLIER_NOTE = new Voucher(true, "multiNote", Material.EMERALD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Multiplier:" + ChatColor.WHITE + " +{amount}x for {timeInMins} minutes") {
        @Override
        void onClaim(Player player) {
            int count = 1;
            if (player.isSneaking()) count = player.getInventory().getItemInMainHand().getAmount();
            double value = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.DOUBLE);
            int duration = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "noteDuration"), PersistentDataType.INTEGER);
            PlayerData playerData = new PlayerData(player);
            playerData.addTempMoneyMultiplier(value, (long) duration * 60 * 1000);
            player.sendMessage(ChatColor.AQUA + "You activated a +" + value + "x multiplier for " + duration + " minutes! " + ChatColor.GRAY + ChatColor.ITALIC + "(Current multiplier: x" + playerData.getMoneyMultiplier() + ")");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - count);
        }
    };

    public static ItemStack getMultiplierNote(double amount, int lengthInMins) {
        ItemStack voucher = MULTIPLIER_NOTE.item;

        List<Component> lore = List.of(
                text("Multiplier Amount: ").color(AQUA)
                        .append(text("+")
                                .append(text(amount))
                                .append(text("x")).color(WHITE))
                        .decoration(TextDecoration.ITALIC, false),
                text("Multiplier Length: ").color(AQUA)
                        .append(text(lengthInMins)
                                .append(text(" minutes")).color(WHITE))
                        .decoration(TextDecoration.ITALIC, false),
                empty(),
                text("Right click to claim!").color(GRAY)
        );

        voucher.editMeta(itemMeta -> {
            itemMeta.displayName(text("Multiplier: ").color(LIGHT_PURPLE).decorate(TextDecoration.BOLD)
                    .append(text(amount)
                            .append(text("x"))
                            .append(text(" for "))
                            .append(text(lengthInMins))
                            .append(text(" minutes")).color(WHITE).decoration(TextDecoration.BOLD, false))
                    .decorations(Map.of(TextDecoration.ITALIC, TextDecoration.State.FALSE))
            );
            itemMeta.lore(lore);
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.DOUBLE, amount);
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "noteDuration"), PersistentDataType.INTEGER, lengthInMins);
        });

        return voucher.clone();
    }

    public static boolean onInteract(PlayerInteractEvent e) {
        if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
            return false;
        if (!isVoucher(e.getPlayer().getInventory().getItemInMainHand())) return false;
        switch (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "customItemType"), PersistentDataType.STRING)) {
            case "moneyNote" -> MONEY_NOTE.onClaim(e.getPlayer());
            case "tokenNote" -> TOKEN_NOTE.onClaim(e.getPlayer());
            case "multiNote" -> MULTIPLIER_NOTE.onClaim(e.getPlayer());
            default -> e.getPlayer().sendMessage(ChatColor.RED + "There was an error claiming this voucher.");
        }
        e.setCancelled(true);
        return true;
    }

    static boolean isVoucher(ItemStack item) {
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "customItemGroup"), PersistentDataType.STRING))
            return false;
        return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "customItemGroup"), PersistentDataType.STRING).equals("voucher");
    }
}
