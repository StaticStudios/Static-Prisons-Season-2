package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        ItemStack item;
        if (args.length == 0) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/customitems <common_key|rare_key|epic_key|legendary_key|static_key|staticp_key|vote_key|kit_key|pickaxe_key|money_pouch_1|money_pouch_2|money_pouch_3|token_pouch_1|token_pouch_2|token_pouch_3|multi_pouch_1|multi_pouch_2|multi_pouch_3|pickaxe_1|pickaxe_2|pickaxe_3|pickaxe_4|pickaxe_5|pickaxe_6|pickaxe_7|pickaxe_8|pickaxe_9|pickaxe_10|pmine_voucher_1|pmine_voucher_2|pmine_voucher_3|pmine_voucher_4|pmine_voucher_5|pmine_voucher_6|pmine_voucher_7|pmine_voucher_8|pmine_voucher_9|pmine_voucher_10|pmine_voucher_11|warrior_voucher|master_voucher|mythic_voucher|static_voucher|staticp_voucher|kit_1|kit_2|kit_3|kit_4|kit_5|kit_6|kit_weapons|kit_potions|mine_bomb_1|mine_bomb_2|mine_bomb_3|mine_bomb_4>"));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "common_key" -> item = CustomItems.getCommonCrateKey(1);
            case "rare_key" -> item = CustomItems.getRareCrateKey(1);
            case "epic_key" -> item = CustomItems.getEpicCrateKey(1);
            case "legendary_key" -> item = CustomItems.getLegendaryCrateKey(1);
            case "static_key" -> item = CustomItems.getStaticCrateKey(1);
            case "staticp_key" -> item = CustomItems.getStaticpCrateKey(1);
            case "vote" -> item = CustomItems.getVoteCrateKey(1);
            case "kit_key" -> item = CustomItems.getKitCrateKey(1);
            case "pickaxe_key" -> item = CustomItems.getPickaxeCrateKey(1);
            case "money_pouch_1" -> item = Vouchers.MONEY_POUCH_T1.item;
            case "money_pouch_2" -> item = Vouchers.MONEY_POUCH_T2.item;
            case "money_pouch_3" -> item = Vouchers.MONEY_POUCH_T3.item;
            case "token_pouch_1" -> item = Vouchers.TOKEN_POUCH_T1.item;
            case "token_pouch_2" -> item = Vouchers.TOKEN_POUCH_T2.item;
            case "token_pouch_3" -> item = Vouchers.TOKEN_POUCH_T3.item;
            case "multi_pouch_1" -> item = Vouchers.MULTI_POUCH_T1.item;
            case "multi_pouch_2" -> item = Vouchers.MULTI_POUCH_T2.item;
            case "multi_pouch_3" -> item = Vouchers.MULTI_POUCH_T3.item;
//            case "pickaxe_1" -> item = CustomItems.getPickaxeTier1();
//            case "pickaxe_2" -> item = CustomItems.getPickaxeTier2();
//            case "pickaxe_3" -> item = CustomItems.getPickaxeTier3();
//            case "pickaxe_4" -> item = CustomItems.getPickaxeTier4();
//            case "pickaxe_5" -> item = CustomItems.getPickaxeTier5();
//            case "pickaxe_6" -> item = CustomItems.getPickaxeTier6();
//            case "pickaxe_7" -> item = CustomItems.getPickaxeTier7();
//            case "pickaxe_8" -> item = CustomItems.getPickaxeTier8();
//            case "pickaxe_9" -> item = CustomItems.getPickaxeTier9();
//            case "pickaxe_10" -> item = CustomItems.getPickaxeTier10();
            case "pmine_voucher_1" -> item = Vouchers.PRIVATE_MINE_T1.item;
            case "pmine_voucher_2" -> item = Vouchers.PRIVATE_MINE_T2.item;
            case "pmine_voucher_3" -> item = Vouchers.PRIVATE_MINE_T3.item;
            case "pmine_voucher_4" -> item = Vouchers.PRIVATE_MINE_T4.item;
            case "pmine_voucher_5" -> item = Vouchers.PRIVATE_MINE_T5.item;
            case "pmine_voucher_6" -> item = Vouchers.PRIVATE_MINE_T6.item;
            case "pmine_voucher_7" -> item = Vouchers.PRIVATE_MINE_T7.item;
            case "pmine_voucher_8" -> item = Vouchers.PRIVATE_MINE_T8.item;
            case "pmine_voucher_9" -> item = Vouchers.PRIVATE_MINE_T9.item;
            case "pmine_voucher_10" -> item = Vouchers.PRIVATE_MINE_T10.item;
            case "pmine_voucher_11" -> item = Vouchers.PRIVATE_MINE_T11.item;
            case "warrior_voucher" -> item = Vouchers.WARRIOR_RANK.item;
            case "master_voucher" -> item = Vouchers.MASTER_RANK.item;
            case "mythic_voucher" -> item = Vouchers.MYTHIC_RANK.item;
            case "static_voucher" -> item = Vouchers.STATIC_RANK.item;
            case "staticp_voucher" -> item = Vouchers.STATICP_RANK.item;
            case "kit_1" -> item = Vouchers.KIT_TIER_1.item;
            case "kit_2" -> item = Vouchers.KIT_TIER_2.item;
            case "kit_3" -> item = Vouchers.KIT_TIER_3.item;
            case "kit_4" -> item = Vouchers.KIT_TIER_4.item;
            case "kit_5" -> item = Vouchers.KIT_TIER_5.item;
            case "kit_6" -> item = Vouchers.KIT_TIER_6.item;
            case "kit_weapons" -> item = Vouchers.KIT_WEAPONS.item;
            case "mine_bomb_1" -> item = CustomItems.getMineBombTier1();
            case "mine_bomb_2" -> item = CustomItems.getMineBombTier2();
            case "mine_bomb_3" -> item = CustomItems.getMineBombTier3();
            case "mine_bomb_4" -> item = CustomItems.getMineBombTier4();
            default -> {
                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/customitems <common_key|rare_key|epic_key|legendary_key|static_key|staticp_key|vote_key|kit_key|pickaxe_key|money_pouch_1|money_pouch_2|money_pouch_3|token_pouch_1|token_pouch_2|token_pouch_3|multi_pouch_1|multi_pouch_2|multi_pouch_3|pickaxe_1|pickaxe_2|pickaxe_3|pickaxe_4|pickaxe_5|pickaxe_6|pickaxe_7|pickaxe_8|pickaxe_9|pickaxe_10|pmine_voucher_1|pmine_voucher_2|pmine_voucher_3|pmine_voucher_4|pmine_voucher_5|pmine_voucher_6|pmine_voucher_7|pmine_voucher_8|pmine_voucher_9|pmine_voucher_10|pmine_voucher_11|warrior_voucher|master_voucher|mythic_voucher|static_voucher|staticp_voucher|kit_1|kit_2|kit_3|kit_4|kit_5|kit_6|kit_weapons|kit_potions|mine_bomb_1|mine_bomb_2|mine_bomb_3|mine_bomb_4>"));
                return false;
            }
        }
        player.getInventory().addItem(item);
        return false;
    }
}
