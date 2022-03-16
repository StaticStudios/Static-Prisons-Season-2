package me.staticstudios.prisons.core.enchants;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrisonEnchants {
    public static Map<UUID, String> playerUUIDToPickaxeID = new HashMap<>();
    public static final PrisonEnchant FORTUNE = new PrisonEnchant("fortune", 50000, BigInteger.valueOf(500), 50);
    public static final PrisonEnchant MERCHANT = new PrisonEnchant("merchant",25000, BigInteger.valueOf(5000), 75);
    public static final PrisonEnchant TOKENATOR = new PrisonEnchant("tokenator", 5000, BigInteger.valueOf(500), 1000);
    public static final PrisonEnchant JACK_HAMMER = new PrisonEnchant("jackHammer", 50000, BigInteger.valueOf(500), 200);
    public static final PrisonEnchant MULTI_DIRECTIONAL = new PrisonEnchant("multiDirectional",50000, BigInteger.valueOf(1000), 200);
    public static final PrisonEnchant DOUBLE_WAMMY = new PrisonEnchant("doubleWammy", 50000, BigInteger.valueOf(2500), 150) {
        @Override
        public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
            PlayerData playerData = new PlayerData(player);
            levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
            if (levelsToBuy <= 0) {
                player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
                return false;
            }
            //Check pickaxe level
            int pickaxeLevel = PrisonPickaxe.getLevel(pickaxe);
            int softMaxLevel = 0;
            int levelRequired = 1;
            if (pickaxeLevel >= 1) {
                levelRequired = 50;
                softMaxLevel = 100;
            }
            if (pickaxeLevel >= 50) {
                levelRequired = 100;
                softMaxLevel = 5000;
            }
            if (pickaxeLevel >= 100) {
                levelRequired = 150;
                softMaxLevel = 10000;
            }
            if (pickaxeLevel >= 150) {
                levelRequired = 200;
                softMaxLevel = 15000;
            }
            if (pickaxeLevel >= 200) {
                levelRequired = 250;
                softMaxLevel = 20000;
            }
            if (pickaxeLevel >= 250) {
                levelRequired = 300;
                softMaxLevel = 30000;
            }
            if (pickaxeLevel >= 300) {
                levelRequired = 350;
                softMaxLevel = 40000;
            }
            if (pickaxeLevel >= 350) {
                levelRequired = 999;
                softMaxLevel = MAX_LEVEL + 10001;
            }
            if (CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy > softMaxLevel) {
                player.sendMessage(ChatColor.RED + "You cannot upgrade this enchant past level " + Utils.addCommasToNumber(softMaxLevel) + "! To be able to upgrade it further, your pickaxe level must be at least " + levelRequired);
                return false;
            }
            BigInteger cost = calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID), levelsToBuy);
            if (playerData.getTokens().compareTo(cost) > -1) {
                playerData.removeTokens(cost);
                CustomEnchants.addEnchantLevel(pickaxe, ENCHANT_ID, levelsToBuy);
                player.sendMessage(ChatColor.AQUA + "You have successfully upgraded your pickaxe!");
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have enough tokens to buy this!");
            }
            return false;
        }
    };
    public static final PrisonEnchant CASH_GRAB = new PrisonEnchant("cashGrab",10, BigInteger.valueOf(25000000000L), 100) {
        @Override
        public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
            PlayerData playerData = new PlayerData(player);
            levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
            if (levelsToBuy <= 0) {
                player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
                return false;
            }
            //Check pickaxe level
            int pickaxeLevel = PrisonPickaxe.getLevel(pickaxe);
            int softMaxLevel = 0;
            int levelRequired = 50;
            if (pickaxeLevel >= 50) {
                levelRequired = 100;
                softMaxLevel = 1;
            }
            if (pickaxeLevel >= 100) {
                levelRequired = 150;
                softMaxLevel = 2;
            }
            if (pickaxeLevel >= 150) {
                levelRequired = 200;
                softMaxLevel = 3;
            }
            if (pickaxeLevel >= 200) {
                levelRequired = 250;
                softMaxLevel = 4;
            }
            if (pickaxeLevel >= 250) {
                levelRequired = 300;
                softMaxLevel = 5;
            }
            if (pickaxeLevel >= 300) {
                levelRequired = 350;
                softMaxLevel = 6;
            }
            if (pickaxeLevel >= 350) {
                levelRequired = 400;
                softMaxLevel = 7;
            }
            if (pickaxeLevel >= 400) {
                levelRequired = 450;
                softMaxLevel = 8;
            }
            if (pickaxeLevel >= 450) {
                levelRequired = 500;
                softMaxLevel = 9;
            }
            if (pickaxeLevel >= 500) {
                levelRequired = 999;
                softMaxLevel = 10;
            }
            if (CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy > softMaxLevel) {
                player.sendMessage(ChatColor.RED + "You cannot upgrade this enchant past level " + Utils.addCommasToNumber(softMaxLevel) + "! To be able to upgrade it further, your pickaxe level must be at least " + levelRequired);
                return false;
            }
            BigInteger cost = calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID), levelsToBuy);
            if (playerData.getTokens().compareTo(cost) > -1) {
                playerData.removeTokens(cost);
                CustomEnchants.addEnchantLevel(pickaxe, ENCHANT_ID, levelsToBuy);
                player.sendMessage(ChatColor.AQUA + "You have successfully upgraded your pickaxe!");
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have enough tokens to buy this!");
            }
            return false;
        }
    };
    public static final PrisonEnchant ORE_SPLITTER = new PrisonEnchant("oreSplitter", 50000, BigInteger.valueOf(1000), 250) {
        @Override
        public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
            PlayerData playerData = new PlayerData(player);
            levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
            if (levelsToBuy <= 0) {
                player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
                return false;
            }
            if (CustomEnchants.getEnchantLevel(pickaxe, "fortune") < 25000) {
                player.sendMessage(ChatColor.RED + "Before you can upgrade this, your pickaxe's fortune level must be at least 25,000!");
                return false;
            }
            //Check pickaxe level
            int pickaxeLevel = PrisonPickaxe.getLevel(pickaxe);
            int softMaxLevel = 0;
            int levelRequired = 150;
            if (pickaxeLevel >= 150) {
                levelRequired = 200;
                softMaxLevel = 20000;
            }
            if (pickaxeLevel >= 200) {
                levelRequired = 250;
                softMaxLevel = 30000;
            }
            if (pickaxeLevel >= 250) {
                levelRequired = 300;
                softMaxLevel = 40000;
            }
            if (pickaxeLevel >= 300) {
                levelRequired = 350;
                softMaxLevel = 50000;
            }
            if (pickaxeLevel >= 350) {
                levelRequired = 400;
                softMaxLevel = 60000;
            }
            if (pickaxeLevel >= 400) {
                levelRequired = 450;
                softMaxLevel = 75000;
            }
            if (pickaxeLevel >= 450) {
                levelRequired = 500;
                softMaxLevel = 85000;
            }
            if (pickaxeLevel >= 500) {
                levelRequired = 999;
                softMaxLevel = MAX_LEVEL + 10001;
            }
            if (CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy > softMaxLevel) {
                player.sendMessage(ChatColor.RED + "You cannot upgrade this enchant past level " + Utils.addCommasToNumber(softMaxLevel) + "! To be able to upgrade it further, your pickaxe level must be at least " + levelRequired);
                return false;
            }
            BigInteger cost = calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID), levelsToBuy);
            if (playerData.getTokens().compareTo(cost) > -1) {
                playerData.removeTokens(cost);
                CustomEnchants.addEnchantLevel(pickaxe, ENCHANT_ID, levelsToBuy);
                player.sendMessage(ChatColor.AQUA + "You have successfully upgraded your pickaxe!");
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have enough tokens to buy this!");
            }
            return false;
        }
    };
    public static final PrisonEnchant TOKEN_POLISHER = new PrisonEnchant("tokenPolisher",5, BigInteger.valueOf(10000000000L), 1000) {
        @Override
        public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
            PlayerData playerData = new PlayerData(player);
            levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
            if (levelsToBuy <= 0) {
                player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
                return false;
            }
            //Check pickaxe level
            int pickaxeLevel = PrisonPickaxe.getLevel(pickaxe);
            int softMaxLevel = 0;
            int levelRequired = 100;
            if (pickaxeLevel >= 100) {
                levelRequired = 200;
                softMaxLevel = 1;
            }
            if (pickaxeLevel >= 200) {
                levelRequired = 300;
                softMaxLevel = 2;
            }
            if (pickaxeLevel >= 300) {
                levelRequired = 400;
                softMaxLevel = 3;
            }
            if (pickaxeLevel >= 400) {
                levelRequired = 500;
                softMaxLevel = 4;
            }
            if (pickaxeLevel >= 500) {
                levelRequired = 999;
                softMaxLevel = 5;
            }
            if (CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy > softMaxLevel) {
                player.sendMessage(ChatColor.RED + "You cannot upgrade this enchant past level " + Utils.addCommasToNumber(softMaxLevel) + "! To be able to upgrade it further, your pickaxe level must be at least " + levelRequired);
                return false;
            }
            BigInteger cost = calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID), levelsToBuy);
            if (playerData.getTokens().compareTo(cost) > -1) {
                playerData.removeTokens(cost);
                CustomEnchants.addEnchantLevel(pickaxe, ENCHANT_ID, levelsToBuy);
                player.sendMessage(ChatColor.AQUA + "You have successfully upgraded your pickaxe!");
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have enough tokens to buy this!");
            }
            return false;
        }
    };
    public static final PrisonEnchant CONSISTENCY  = new PrisonEnchant("consistency",5, BigInteger.valueOf(100000000000L), 2500) {
        @Override
        public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
            PlayerData playerData = new PlayerData(player);
            levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
            if (levelsToBuy <= 0) {
                player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
                return false;
            }
            //Check pickaxe level
            int pickaxeLevel = PrisonPickaxe.getLevel(pickaxe);
            int softMaxLevel = 0;
            int levelRequired = 200;
            if (pickaxeLevel >= 200) {
                levelRequired = 250;
                softMaxLevel = 1;
            }
            if (pickaxeLevel >= 250) {
                levelRequired = 300;
                softMaxLevel = 2;
            }
            if (pickaxeLevel >= 300) {
                levelRequired = 350;
                softMaxLevel = 3;
            }
            if (pickaxeLevel >= 350) {
                levelRequired = 400;
                softMaxLevel = 4;
            }
            if (pickaxeLevel >= 400) {
                levelRequired = 999;
                softMaxLevel = 5;
            }
            if (CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy > softMaxLevel) {
                player.sendMessage(ChatColor.RED + "You cannot upgrade this enchant past level " + Utils.addCommasToNumber(softMaxLevel) + "! To be able to upgrade it further, your pickaxe level must be at least " + levelRequired);
                return false;
            }
            BigInteger cost = calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID), levelsToBuy);
            if (playerData.getTokens().compareTo(cost) > -1) {
                playerData.removeTokens(cost);
                CustomEnchants.addEnchantLevel(pickaxe, ENCHANT_ID, levelsToBuy);
                player.sendMessage(ChatColor.AQUA + "You have successfully upgraded your pickaxe!");
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have enough tokens to buy this!");
            }
            return false;
        }
    };

    public static final PrisonEnchant KEY_FINDER = new PrisonEnchant("keyFinder",10000, BigInteger.valueOf(5000), 100);
    public static final PrisonEnchant XP_FINDER = new PrisonEnchant("xpFinder",5, BigInteger.valueOf(25000000000L), 1000);

    public static final PrisonEnchant HASTE = new PrisonEnchant("haste",3, BigInteger.ONE, 1);
    public static final PrisonEnchant SPEED = new PrisonEnchant("speed",3, BigInteger.ONE, 1);
    public static final PrisonEnchant NIGHT_VISION = new PrisonEnchant("nightVision",1, BigInteger.ONE, 1);
}
