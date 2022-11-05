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
    public static final Voucher AUTO_SELL = new Voucher("autoSell", Material.PAPER, ChatColor.YELLOW + "" + ChatColor.BOLD + "AUTO SELL VOUCHER", ChatColor.GREEN + "Claiming this will give you the ability to auto sell!") {
        @Override
        void onClaim(Player player) {
            PlayerData playerData = new PlayerData(player);
            if (playerData.getCanExplicitlyEnableAutoSell()) {
                player.sendMessage(ChatColor.RED + "You can already enable auto sell!");
                return;
            }
            playerData.setCanEnableAutoSell(true);
            player.sendMessage(ChatColor.AQUA + "You can now enable auto sell!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher WARRIOR_RANK = new Voucher("rankWarrior", Material.PAPER, ChatColor.AQUA + "" + ChatColor.BOLD + "WARRIOR RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Warrior", ChatColor.GREEN + "prefix and all of the rank benefits!") {
        @Override
        void onClaim(Player player) {
            PlayerData playerData = new PlayerData(player);
            if (playerData.getPlayerRanks().contains("warrior")) {
                player.sendMessage(ChatColor.RED + "You already have a rank higher than this!");
                return;
            }
            playerData.setPlayerRank("warrior");
            player.sendMessage(ChatColor.AQUA + "You were given Warrior rank!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher MASTER_RANK = new Voucher("rankMaster", Material.PAPER, ChatColor.AQUA + "" + ChatColor.BOLD + "MASTER RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Master", ChatColor.GREEN + "prefix and all of the rank benefits!") {
        @Override
        void onClaim(Player player) {
            PlayerData playerData = new PlayerData(player);
            if (playerData.getPlayerRanks().contains("master")) {
                player.sendMessage(ChatColor.RED + "You already have a rank higher than this!");
                return;
            }
            playerData.setPlayerRank("master");
            player.sendMessage(ChatColor.AQUA + "You were given Master rank!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher MYTHIC_RANK = new Voucher("rankMythic", Material.PAPER, ChatColor.AQUA + "" + ChatColor.BOLD + "MYTHIC RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Mythic", ChatColor.GREEN + "prefix and all of the rank benefits!") {
        @Override
        void onClaim(Player player) {
            PlayerData playerData = new PlayerData(player);
            if (playerData.getPlayerRanks().contains("mythic")) {
                player.sendMessage(ChatColor.RED + "You already have a rank higher than this!");
                return;
            }
            playerData.setPlayerRank("mythic");
            player.sendMessage(ChatColor.AQUA + "You were given Mythic rank!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher STATIC_RANK = new Voucher("rankStatic", Material.PAPER, ChatColor.AQUA + "" + ChatColor.BOLD + "STATIC RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Static", ChatColor.GREEN + "prefix and all of the rank benefits!") {
        @Override
        void onClaim(Player player) {
            PlayerData playerData = new PlayerData(player);
            if (playerData.getPlayerRanks().contains("static")) {
                player.sendMessage(ChatColor.RED + "You already have a rank higher than this!");
                return;
            }
            playerData.setPlayerRank("static");
            player.sendMessage(ChatColor.AQUA + "You were given Static rank!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher STATICP_RANK = new Voucher("rankStaticp", Material.PAPER, ChatColor.AQUA + "" + ChatColor.BOLD + "STATIC+ RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Static+", ChatColor.GREEN + "prefix and all of the rank benefits!") {
        @Override
        void onClaim(Player player) {
            PlayerData playerData = new PlayerData(player);
            if (playerData.getPlayerRanks().contains("staticp")) {
                player.sendMessage(ChatColor.RED + "You already have a rank higher than this!");
                return;
            }
            playerData.setPlayerRank("staticp");
            player.sendMessage(ChatColor.AQUA + "You were given Static+ rank!");
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_1 = new Voucher("kit1", Material.IRON_CHESTPLATE, ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 1", Kits.TIER1.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER1.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_2 = new Voucher("kit2", Material.IRON_CHESTPLATE, ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 2", Kits.TIER2.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER2.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_3 = new Voucher("kit3", Material.DIAMOND_CHESTPLATE, ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 3", Kits.TIER3.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER3.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_4 = new Voucher("kit4", Material.DIAMOND_CHESTPLATE, ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 4", Kits.TIER4.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER4.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_5 = new Voucher("kit5", Material.DIAMOND_CHESTPLATE, ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 5", Kits.TIER5.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER5.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_6 = new Voucher("kit6", Material.DIAMOND_CHESTPLATE, ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 6", Kits.TIER6.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER6.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_POTIONS = new Voucher("kitPotions", Material.SPLASH_POTION, ChatColor.RED + "" + ChatColor.BOLD + "Potion Kit", Kits.POTION.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.POTION.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_WEAPONS = new Voucher("kitWeapons", Material.NETHERITE_SWORD, ChatColor.RED + "" + ChatColor.BOLD + "Weapon Kit", Kits.WEAPON.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.WEAPON.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
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
            case "rankWarrior" -> WARRIOR_RANK.onClaim(e.getPlayer());
            case "rankMaster" -> MASTER_RANK.onClaim(e.getPlayer());
            case "rankMythic" -> MYTHIC_RANK.onClaim(e.getPlayer());
            case "rankStatic" -> STATIC_RANK.onClaim(e.getPlayer());
            case "rankStaticp" -> STATICP_RANK.onClaim(e.getPlayer());
            case "kit1" -> KIT_TIER_1.onClaim(e.getPlayer());
            case "kit2" -> KIT_TIER_2.onClaim(e.getPlayer());
            case "kit3" -> KIT_TIER_3.onClaim(e.getPlayer());
            case "kit4" -> KIT_TIER_4.onClaim(e.getPlayer());
            case "kit5" -> KIT_TIER_5.onClaim(e.getPlayer());
            case "kit6" -> KIT_TIER_6.onClaim(e.getPlayer());
            case "kitPotions" -> KIT_POTIONS.onClaim(e.getPlayer());
            case "kitWeapons" -> KIT_WEAPONS.onClaim(e.getPlayer());
            case "autoSell" -> AUTO_SELL.onClaim(e.getPlayer());
//            case "privateMine1" -> PRIVATE_MINE_T1.onClaim(e.getPlayer());
//            case "privateMine2" -> PRIVATE_MINE_T2.onClaim(e.getPlayer());
//            case "privateMine3" -> PRIVATE_MINE_T3.onClaim(e.getPlayer());
//            case "privateMine4" -> PRIVATE_MINE_T4.onClaim(e.getPlayer());
//            case "privateMine5" -> PRIVATE_MINE_T5.onClaim(e.getPlayer());
//            case "privateMine6" -> PRIVATE_MINE_T6.onClaim(e.getPlayer());
//            case "privateMine7" -> PRIVATE_MINE_T7.onClaim(e.getPlayer());
//            case "privateMine8" -> PRIVATE_MINE_T8.onClaim(e.getPlayer());
//            case "privateMine9" -> PRIVATE_MINE_T9.onClaim(e.getPlayer());
//            case "privateMine10" -> PRIVATE_MINE_T10.onClaim(e.getPlayer());
//            case "privateMine11" -> PRIVATE_MINE_T11.onClaim(e.getPlayer());
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
