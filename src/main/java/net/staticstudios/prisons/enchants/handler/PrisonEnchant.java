package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

/*
public class PrisonEnchant {
    public int MAX_LEVEL;
    public BigInteger PRICE;
    public String ENCHANT_ID;
    public String DISPLAY_NAME;
    public List<String> DESCRIPTION;
    //Price calculations
    public PrisonEnchant(String enchantID, int maxLevel, BigInteger initialPrice) {
        ENCHANT_ID = enchantID;
        MAX_LEVEL = maxLevel;
        PRICE = initialPrice;
    }

    public boolean tryToBuyLevels(Player player, ItemStack item, int levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(item);
        levelsToBuy = Math.min(MAX_LEVEL, pickaxe.getEnchantLevel(ENCHANT_ID) + levelsToBuy) - pickaxe.getEnchantLevel(ENCHANT_ID);
        if (levelsToBuy <= 0) {
            player.sendMessage(ChatColor.RED + "This enchant is already at its max level!");
            return false;
        }
        if (playerData.getTokens().compareTo(PRICE.multiply(BigInteger.valueOf(levelsToBuy))) > -1) {
            playerData.removeTokens(PRICE.multiply(BigInteger.valueOf(levelsToBuy)));
            pickaxe.addEnchantLevel(ENCHANT_ID, levelsToBuy);
            PrisonPickaxe.updateLore(item);
            player.sendMessage(ChatColor.AQUA + "You successfully upgraded your pickaxe!");
            return true;
        } else player.sendMessage(ChatColor.RED + "You do not have enough tokens to buy this!");
        return false;
    }

}

 */
