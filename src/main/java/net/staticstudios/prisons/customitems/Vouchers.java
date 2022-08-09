package net.staticstudios.prisons.customitems;

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

public class Vouchers {
    public static final Voucher AUTO_SELL = new Voucher("autoSell", Material.PAPER,ChatColor.YELLOW + "" + ChatColor.BOLD + "AUTO SELL VOUCHER", ChatColor.GREEN + "Claiming this will give you the ability to auto sell!") {
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
    public static final Voucher WARRIOR_RANK = new Voucher("rankWarrior", Material.PAPER,ChatColor.AQUA + "" + ChatColor.BOLD + "WARRIOR RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Warrior", ChatColor.GREEN + "prefix and all of the rank benefits!") {
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
    public static final Voucher MASTER_RANK = new Voucher("rankMaster", Material.PAPER,ChatColor.AQUA + "" + ChatColor.BOLD + "MASTER RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Master", ChatColor.GREEN + "prefix and all of the rank benefits!") {
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
    public static final Voucher MYTHIC_RANK = new Voucher("rankMythic", Material.PAPER,ChatColor.AQUA + "" + ChatColor.BOLD + "MYTHIC RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Mythic", ChatColor.GREEN + "prefix and all of the rank benefits!") {
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
    public static final Voucher STATIC_RANK = new Voucher("rankStatic", Material.PAPER,ChatColor.AQUA + "" + ChatColor.BOLD + "STATIC RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Static", ChatColor.GREEN + "prefix and all of the rank benefits!") {
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
    public static final Voucher STATICP_RANK = new Voucher("rankStaticp", Material.PAPER,ChatColor.AQUA + "" + ChatColor.BOLD + "STATIC+ RANK VOUCHER", ChatColor.GREEN + "Claiming this will give you the Static+", ChatColor.GREEN + "prefix and all of the rank benefits!") {
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
    public static final Voucher KIT_TIER_1 = new Voucher("kit1", Material.IRON_CHESTPLATE,ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 1", Kits.TIER1.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER1.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_2 = new Voucher("kit2", Material.IRON_CHESTPLATE,ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 2", Kits.TIER2.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER2.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_3 = new Voucher("kit3", Material.DIAMOND_CHESTPLATE,ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 3", Kits.TIER3.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER3.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_4 = new Voucher("kit4", Material.DIAMOND_CHESTPLATE,ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 4", Kits.TIER4.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER4.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_5 = new Voucher("kit5", Material.DIAMOND_CHESTPLATE,ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 5", Kits.TIER5.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER5.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_TIER_6 = new Voucher("kit6", Material.DIAMOND_CHESTPLATE,ChatColor.RED + "" + ChatColor.BOLD + "Kit Tier 6", Kits.TIER6.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.TIER6.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_POTIONS = new Voucher("kitPotions", Material.SPLASH_POTION,ChatColor.RED + "" + ChatColor.BOLD + "Potion Kit", Kits.POTION.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.POTION.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher KIT_WEAPONS = new Voucher("kitWeapons", Material.NETHERITE_SWORD,ChatColor.RED + "" + ChatColor.BOLD + "Weapon Kit", Kits.WEAPON.whatComesWithTheKit.toArray(new String[0])) {
        @Override
        void onClaim(Player player) {
            Kits.WEAPON.addItemsToPlayersInventory(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
//    public static final Voucher PRIVATE_MINE_T1 = new Voucher("privateMine1", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 1 (51x148x51)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 50) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(50);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T2 = new Voucher("privateMine2", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 2 (61x148x61)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 60) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(60);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T3 = new Voucher("privateMine3", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 3 (71x148x71)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 70) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(70);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T4 = new Voucher("privateMine4", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 4 (81x148x81)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 80) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(80);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T5 = new Voucher("privateMine5", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 5 (91x148x91)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 90) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(90);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T6 = new Voucher("privateMine6", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 6 (101x148x101)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 100) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(100);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T7 = new Voucher("privateMine7", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 7 (111x148x111)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 110) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(110);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T8 = new Voucher("privateMine8", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 8 (121x148x121)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 120) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(120);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T9 = new Voucher("privateMine9", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 9 (131x148x131)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 130) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(130);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T10 = new Voucher("privateMine10", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 10 (141x148x141)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 140) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(140);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
//    public static final Voucher PRIVATE_MINE_T11 = new Voucher("privateMine11", Material.DEEPSLATE_DIAMOND_ORE,ChatColor.GREEN + "" + ChatColor.BOLD + "Private Mine Tier 11 (151x148x151)", ChatColor.GREEN + "Claim this to unlock a mine all to yourself.", ChatColor.GREEN + "+20% token multiplier when mining in this mine.", ChatColor.GREEN + "You can refill this mine at any time.", ChatColor.GREEN + "You can make the block any block that you have unlocked!") {
//        @Override
//        void onClaim(Player player) {
//            PlayerData playerData = new PlayerData(player);
//            if (playerData.getPrivateMineSquareSize() >= 150) {
//                player.sendMessage(ChatColor.RED + "You already have a private mine equivalent to, or bigger than, this!");
//                return;
//            }
//            playerData.setPrivateMineSquareSize(150);
//            playerData.setPrivateMineMat(Material.STONE);
//            playerData.setHasPrivateMine(true);
//            player.sendMessage(ChatColor.AQUA + "You can now go to your private mine! Type \"/privatemine\" to get started!");
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        }
//    };
    public static final Voucher MONEY_POUCH_T1 = new Voucher("pouchMoney1", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Money Pouch Tier 1", ChatColor.GREEN + "Claim this to win a random amount of money!") {
        @Override
        void onClaim(Player player) {
            MoneyPouch.TIER_1.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher MONEY_POUCH_T2 = new Voucher("pouchMoney2", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Money Pouch Tier 2", ChatColor.GREEN + "Claim this to win a random amount of money!") {
        @Override
        void onClaim(Player player) {
            MoneyPouch.TIER_2.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher MONEY_POUCH_T3 = new Voucher("pouchMoney3", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Money Pouch Tier 3", ChatColor.GREEN + "Claim this to win a random amount of money!") {
        @Override
        void onClaim(Player player) {
            MoneyPouch.TIER_3.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher TOKEN_POUCH_T1 = new Voucher("pouchToken1", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Token Pouch Tier 1", ChatColor.GREEN + "Claim this to win a random amount of tokens!") {
        @Override
        void onClaim(Player player) {
            TokenPouch.TIER_1.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher TOKEN_POUCH_T2 = new Voucher("pouchToken2", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Token Pouch Tier 2", ChatColor.GREEN + "Claim this to win a random amount of tokens!") {
        @Override
        void onClaim(Player player) {
            TokenPouch.TIER_2.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher TOKEN_POUCH_T3 = new Voucher("pouchToken3", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Token Pouch Tier 3", ChatColor.GREEN + "Claim this to win a random amount of tokens!") {
        @Override
        void onClaim(Player player) {
            TokenPouch.TIER_3.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher MULTI_POUCH_T1 = new Voucher("pouchMulti1", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Multiplier Pouch Tier 1", ChatColor.GREEN + "Claim this to win a random multiplier!") {
        @Override
        void onClaim(Player player) {
            MultiPouch.TIER_1.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher MULTI_POUCH_T2 = new Voucher("pouchMulti2", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Multiplier Pouch Tier 2", ChatColor.GREEN + "Claim this to win a random multiplier!") {
        @Override
        void onClaim(Player player) {
            MultiPouch.TIER_2.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    public static final Voucher MULTI_POUCH_T3 = new Voucher("pouchMulti3", Material.ENDER_CHEST,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Multiplier Pouch Tier 3", ChatColor.GREEN + "Claim this to win a random multiplier!") {
        @Override
        void onClaim(Player player) {
            MultiPouch.TIER_3.open(player);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        }
    };
    private static final Voucher MONEY_NOTE = new Voucher("moneyNote", Material.PAPER,ChatColor.GREEN + "" + ChatColor.BOLD + "Money Note") {
        @Override
        void onClaim(Player player) {
            int count = 1;
            System.out.println("aaa");
            if (player.isSneaking()) count = player.getInventory().getItemInMainHand().getAmount();
            System.out.println("c");
            long value = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.LONG);
            System.out.println("aadfda");
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
    private static final Voucher TOKEN_NOTE = new Voucher("tokenNote", Material.SUNFLOWER,ChatColor.GOLD + "" + ChatColor.BOLD + "Token Note") {
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


    private static final Voucher MULTIPLIER_NOTE = new Voucher(true, "multiNote", Material.EMERALD,ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Multiplier:" + ChatColor.WHITE + " +{amount}x for {timeInMins} minutes") {
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
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Multiplier Amount: " + ChatColor.WHITE + "+" + amount + "x");
        lore.add(ChatColor.AQUA + "Multiplier Length: " + ChatColor.WHITE + lengthInMins + " minutes");
        lore.add("");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to claim!");
        ItemMeta meta = voucher.getItemMeta();
        String displayName = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Multiplier:" + ChatColor.WHITE + " +{amount}x for {timeInMins} minutes";
        displayName = displayName.replaceAll("\\{amount}", amount + "");
        displayName = displayName.replaceAll("\\{timeInMins}", lengthInMins + "");
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "noteValue"), PersistentDataType.DOUBLE, amount);
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "noteDuration"), PersistentDataType.INTEGER, lengthInMins);
        voucher.setItemMeta(meta);
        return voucher;
    }
    public static boolean onInteract(PlayerInteractEvent e) {
        if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return false;
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
            case "pouchMoney1" -> MONEY_POUCH_T1.onClaim(e.getPlayer());
            case "pouchMoney2" -> MONEY_POUCH_T2.onClaim(e.getPlayer());
            case "pouchMoney3" -> MONEY_POUCH_T3.onClaim(e.getPlayer());
            case "pouchToken1" -> TOKEN_POUCH_T1.onClaim(e.getPlayer());
            case "pouchToken2" -> TOKEN_POUCH_T2.onClaim(e.getPlayer());
            case "pouchToken3" -> TOKEN_POUCH_T3.onClaim(e.getPlayer());
            case "pouchMulti1" -> MULTI_POUCH_T1.onClaim(e.getPlayer());
            case "pouchMulti2" -> MULTI_POUCH_T2.onClaim(e.getPlayer());
            case "pouchMulti3" -> MULTI_POUCH_T3.onClaim(e.getPlayer());
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
        if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "customItemGroup"), PersistentDataType.STRING)) return false;
        return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "customItemGroup"), PersistentDataType.STRING).equals("voucher");
    }
}
