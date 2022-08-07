package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;
import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomItemsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) { //todo: this from map | lootboxes
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) {
            player.sendMessage(Component.text("Usage: /customitems <item>\nType \"/customitems items\" for all available custom items").color(ComponentUtil.YELLOW));
            return true;
        }
        if (args[0].equalsIgnoreCase("items")) {
            Component msg = Component.empty().append(Component.text("Available custom items:\n").color(ComponentUtil.RED));
            for (String id : CustomItems.ITEMS.keySet()) {
                msg = msg.append(Component.text(id).color(ComponentUtil.GOLD)).append(Component.text(" "));
            }
            player.sendMessage(msg);
            return true;
        }
        ItemStack item = CustomItems.getItem(args[0].toLowerCase(), player);
        if (item == null) {
            player.sendMessage(Component.text("Item not found!").color(ComponentUtil.RED));
            return true;
        }
        player.getInventory().addItem(item);
//        switch (args[0].toLowerCase()) {
//            case "common_key" -> item = CustomItems.getCommonCrateKey(1);
//            case "rare_key" -> item = CustomItems.getRareCrateKey(1);
//            case "epic_key" -> item = CustomItems.getEpicCrateKey(1);
//            case "legendary_key" -> item = CustomItems.getLegendaryCrateKey(1);
//            case "static_key" -> item = CustomItems.getStaticCrateKey(1);
//            case "staticp_key" -> item = CustomItems.getStaticpCrateKey(1);
//            case "vote" -> item = CustomItems.getVoteCrateKey(1);
//            case "kit_key" -> item = CustomItems.getKitCrateKey(1);
//            case "pickaxe_key" -> item = CustomItems.getPickaxeCrateKey(1);
//            case "money_pouch_1" -> item = Vouchers.MONEY_POUCH_T1.item;
//            case "money_pouch_2" -> item = Vouchers.MONEY_POUCH_T2.item;
//            case "money_pouch_3" -> item = Vouchers.MONEY_POUCH_T3.item;
//            case "token_pouch_1" -> item = Vouchers.TOKEN_POUCH_T1.item;
//            case "token_pouch_2" -> item = Vouchers.TOKEN_POUCH_T2.item;
//            case "token_pouch_3" -> item = Vouchers.TOKEN_POUCH_T3.item;
//            case "multi_pouch_1" -> item = Vouchers.MULTI_POUCH_T1.item;
//            case "multi_pouch_2" -> item = Vouchers.MULTI_POUCH_T2.item;
//            case "multi_pouch_3" -> item = Vouchers.MULTI_POUCH_T3.item;
////            case "pickaxe_1" -> item = CustomItems.getPickaxeTier1();
////            case "pickaxe_2" -> item = CustomItems.getPickaxeTier2();
////            case "pickaxe_3" -> item = CustomItems.getPickaxeTier3();
////            case "pickaxe_4" -> item = CustomItems.getPickaxeTier4();
////            case "pickaxe_5" -> item = CustomItems.getPickaxeTier5();
////            case "pickaxe_6" -> item = CustomItems.getPickaxeTier6();
////            case "pickaxe_7" -> item = CustomItems.getPickaxeTier7();
////            case "pickaxe_8" -> item = CustomItems.getPickaxeTier8();
////            case "pickaxe_9" -> item = CustomItems.getPickaxeTier9();
////            case "pickaxe_10" -> item = CustomItems.getPickaxeTier10();
////            case "pmine_voucher_1" -> item = Vouchers.PRIVATE_MINE_T1.item;
////            case "pmine_voucher_2" -> item = Vouchers.PRIVATE_MINE_T2.item;
////            case "pmine_voucher_3" -> item = Vouchers.PRIVATE_MINE_T3.item;
////            case "pmine_voucher_4" -> item = Vouchers.PRIVATE_MINE_T4.item;
////            case "pmine_voucher_5" -> item = Vouchers.PRIVATE_MINE_T5.item;
////            case "pmine_voucher_6" -> item = Vouchers.PRIVATE_MINE_T6.item;
////            case "pmine_voucher_7" -> item = Vouchers.PRIVATE_MINE_T7.item;
////            case "pmine_voucher_8" -> item = Vouchers.PRIVATE_MINE_T8.item;
////            case "pmine_voucher_9" -> item = Vouchers.PRIVATE_MINE_T9.item;
////            case "pmine_voucher_10" -> item = Vouchers.PRIVATE_MINE_T10.item;
////            case "pmine_voucher_11" -> item = Vouchers.PRIVATE_MINE_T11.item;
//            case "warrior_voucher" -> item = Vouchers.WARRIOR_RANK.item;
//            case "master_voucher" -> item = Vouchers.MASTER_RANK.item;
//            case "mythic_voucher" -> item = Vouchers.MYTHIC_RANK.item;
//            case "static_voucher" -> item = Vouchers.STATIC_RANK.item;
//            case "staticp_voucher" -> item = Vouchers.STATICP_RANK.item;
//            case "kit_1" -> item = Vouchers.KIT_TIER_1.item;
//            case "kit_2" -> item = Vouchers.KIT_TIER_2.item;
//            case "kit_3" -> item = Vouchers.KIT_TIER_3.item;
//            case "kit_4" -> item = Vouchers.KIT_TIER_4.item;
//            case "kit_5" -> item = Vouchers.KIT_TIER_5.item;
//            case "kit_6" -> item = Vouchers.KIT_TIER_6.item;
//            case "kit_weapons" -> item = Vouchers.KIT_WEAPONS.item;
////            case "mine_bomb_1" -> item = CustomItems.getMineBombTier1();
////            case "mine_bomb_2" -> item = CustomItems.getMineBombTier2();
////            case "mine_bomb_3" -> item = CustomItems.getMineBombTier3();
////            case "mine_bomb_4" -> item = CustomItems.getMineBombTier4();
//            default -> {
//                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/customitems <common_key|rare_key|epic_key|legendary_key|static_key|staticp_key|vote_key|kit_key|pickaxe_key|money_pouch_1|money_pouch_2|money_pouch_3|token_pouch_1|token_pouch_2|token_pouch_3|multi_pouch_1|multi_pouch_2|multi_pouch_3|pickaxe_1|pickaxe_2|pickaxe_3|pickaxe_4|pickaxe_5|pickaxe_6|pickaxe_7|pickaxe_8|pickaxe_9|pickaxe_10|pmine_voucher_1|pmine_voucher_2|pmine_voucher_3|pmine_voucher_4|pmine_voucher_5|pmine_voucher_6|pmine_voucher_7|pmine_voucher_8|pmine_voucher_9|pmine_voucher_10|pmine_voucher_11|warrior_voucher|master_voucher|mythic_voucher|static_voucher|staticp_voucher|kit_1|kit_2|kit_3|kit_4|kit_5|kit_6|kit_weapons|kit_potions|mine_bomb_1|mine_bomb_2|mine_bomb_3|mine_bomb_4>"));
//                return false;
//            }
//        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
//        if (args.length == 1) {
//            list.add("common_key");
//            list.add("rare_key");
//            list.add("epic_key");
//            list.add("legendary_key");
//            list.add("static_key");
//            list.add("staticp_key");
//            list.add("vote_key");
//            list.add("kit_key");
//            list.add("pickaxe_key");
//            list.add("money_pouch_1");
//            list.add("money_pouch_2");
//            list.add("money_pouch_3");
//            list.add("token_pouch_1");
//            list.add("token_pouch_2");
//            list.add("token_pouch_3");
//            list.add("multi_pouch_1");
//            list.add("multi_pouch_2");
//            list.add("multi_pouch_3");
//            list.add("pickaxe_1");
//            list.add("pickaxe_2");
//            list.add("pickaxe_3");
//            list.add("pickaxe_4");
//            list.add("pickaxe_5");
//            list.add("pickaxe_6");
//            list.add("pickaxe_7");
//            list.add("pickaxe_8");
//            list.add("pickaxe_9");
//            list.add("pickaxe_10");
//        }
        return StaticMineUtils.filterStringList(CustomItems.ITEMS.keySet(), args[0]);
    }
}
