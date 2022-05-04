package me.staticstudios.prisons.enchants.handler;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

public abstract class BaseEnchant implements IPrisonEnchant {
    public final int MAX_LEVEL;
    public final BigInteger PRICE;
    public final String ENCHANT_ID;
    public final String DISPLAY_NAME;
    public final String UNFORMATTED_DISPLAY_NAME;
    public final List<String> DESCRIPTION;


    public BaseEnchant(String id, String name, int maxLevel, BigInteger price, String... desc) {
        ENCHANT_ID = id;
        DISPLAY_NAME = ChatColor.translateAlternateColorCodes('&', name);
        UNFORMATTED_DISPLAY_NAME= ChatColor.stripColor(DISPLAY_NAME);
        MAX_LEVEL = maxLevel;
        PRICE = price;
        for (int i = 0; i < desc.length; i++) desc[i] = ChatColor.translateAlternateColorCodes('&', desc[i]);
        DESCRIPTION = List.of(desc);
        PrisonEnchants.enchantIDToEnchant.put(ENCHANT_ID, this);
    }

    public boolean tryToBuyLevels(Player player, ItemStack pickaxe, int levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        levelsToBuy = Math.min(MAX_LEVEL, (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID) + levelsToBuy) - (int) CustomEnchants.getEnchantLevel(pickaxe, ENCHANT_ID);
        if (levelsToBuy <= 0) {
            player.sendMessage(org.bukkit.ChatColor.RED + "This enchant is already at its max level!");
            return false;
        }
        if (playerData.getTokens().compareTo(PRICE.multiply(BigInteger.valueOf(levelsToBuy))) > -1) {
            playerData.removeTokens(PRICE.multiply(BigInteger.valueOf(levelsToBuy)));
            CustomEnchants.addEnchantLevel(pickaxe, ENCHANT_ID, levelsToBuy);
            player.sendMessage(org.bukkit.ChatColor.AQUA + "You have successfully upgraded your pickaxe!");
            return true;
        } else player.sendMessage(org.bukkit.ChatColor.RED + "You do not have enough tokens to buy this!");
        return false;
    }

}
