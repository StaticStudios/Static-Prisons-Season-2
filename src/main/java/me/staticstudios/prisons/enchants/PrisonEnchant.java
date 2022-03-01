package me.staticstudios.prisons.enchants;

import me.staticstudios.prisons.data.serverData.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class PrisonEnchant {
    public final int MAX_LEVEL;
    public final BigInteger INITIAL_PRICE;
    public final String ENCHANT_ID;
    public final int RATE_OF_INCREASE;
    //Price calculations
    public PrisonEnchant(String enchantID, int maxLevel, BigInteger initialPrice, int rateOfIncrease) {
        ENCHANT_ID = enchantID;
        MAX_LEVEL = maxLevel;
        INITIAL_PRICE = initialPrice;
        RATE_OF_INCREASE = rateOfIncrease;
    }

    public BigInteger calculatePrice(int currentLevel, int levelsToBuy) {
        BigInteger price = INITIAL_PRICE;
        for (int i = 0; i < levelsToBuy; i++) {
            price = price.add(new BigDecimal(INITIAL_PRICE.multiply(BigInteger.valueOf(currentLevel + i))).multiply(BigDecimal.valueOf(RATE_OF_INCREASE).divide(BigDecimal.valueOf(100)).setScale(4, RoundingMode.CEILING)).toBigInteger());
        }
        return price;
    }

    public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
        if (levelsToBuy <= 0) {
            player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
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
}
