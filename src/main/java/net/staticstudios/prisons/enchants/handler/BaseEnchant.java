package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.data.PlayerData;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

public abstract class BaseEnchant implements Listener {
    public static void init() {
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (PrisonUtils.Players.isHoldingRightClick(player)) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (!PrisonUtils.checkIsPrisonPickaxe(item)) continue;
                    PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(item);
                    for (BaseEnchant enchant : pickaxe.getEnchants()) {
                        if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
                        enchant.whileRightClicking(player, pickaxe);
                    }
                }
            }
        }, 20, 7);
    }

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

    public boolean tryToBuyLevels(Player player, PrisonPickaxe pickaxe, int levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        if (levelsToBuy <= 0) {
            player.sendMessage(org.bukkit.ChatColor.RED + "You do not have enough tokens to buy this!");
            return false;
        }
        levelsToBuy = Math.min(MAX_LEVEL, pickaxe.getEnchantLevel(ENCHANT_ID) + levelsToBuy) - pickaxe.getEnchantLevel(ENCHANT_ID);
        if (levelsToBuy <= 0) {
            player.sendMessage(org.bukkit.ChatColor.RED + "This enchant is already at its max level!");
            return false;
        }
        if (playerData.getTokens().compareTo(PRICE.multiply(BigInteger.valueOf(levelsToBuy))) > -1) {
            playerData.removeTokens(PRICE.multiply(BigInteger.valueOf(levelsToBuy)));
            int oldLevel = pickaxe.getEnchantLevel(ENCHANT_ID);
            pickaxe.addEnchantLevel(ENCHANT_ID, levelsToBuy);
            int newLevel = pickaxe.getEnchantLevel(ENCHANT_ID);
            pickaxe.tryToUpdateLore();
            if (pickaxe.getIsEnchantEnabled(ENCHANT_ID)) onUpgrade(player, pickaxe, oldLevel, newLevel);
            player.sendMessage(org.bukkit.ChatColor.AQUA + "You successfully upgraded your pickaxe!");
            return true;
        }
        player.sendMessage(org.bukkit.ChatColor.RED + "You do not have enough tokens to buy this!");
        return false;
    }


    public void onBlockBreak(PrisonBlockBroken bb) {}
    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {}
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {}
    public void whileRightClicking(Player player, PrisonPickaxe pickaxe) {}
    public void onUpgrade(Player player, PrisonPickaxe pickaxe, int oldLevel, int newLevel) {}

}

