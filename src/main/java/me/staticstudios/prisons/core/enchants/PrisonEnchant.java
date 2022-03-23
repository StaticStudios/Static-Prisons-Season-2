package me.staticstudios.prisons.core.enchants;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class PrisonEnchant {
    public final int MAX_LEVEL;
    public final BigInteger PRICE;
    public final String ENCHANT_ID;
    //Price calculations
    public PrisonEnchant(String enchantID, int maxLevel, BigInteger initialPrice) {
        ENCHANT_ID = enchantID;
        MAX_LEVEL = maxLevel;
        PRICE = initialPrice;
    }


    public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
        if (levelsToBuy <= 0) {
            player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
            return false;
        }
        if (playerData.getTokens().compareTo(PRICE.multiply(BigInteger.valueOf(levelsToBuy))) > -1) {
            playerData.removeTokens(PRICE.multiply(BigInteger.valueOf(levelsToBuy)));
            CustomEnchants.addEnchantLevel(player, pickaxe, ENCHANT_ID, levelsToBuy);
            player.sendMessage(ChatColor.AQUA + "You have successfully upgraded your pickaxe!");
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have enough tokens to buy this!");
        }
        return false;
    }
}
