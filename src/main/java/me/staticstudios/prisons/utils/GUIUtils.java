package me.staticstudios.prisons.utils;

import me.staticstudios.prisons.enchants.handler.PrisonEnchant;
import me.staticstudios.prisons.gui.GUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GUIUtils {
    public static ItemStack createEnchantGUIItem(String id, Material icon, String displayName, PrisonEnchant enchant, long currentLevel, BigInteger tokens) {
        List<String> lore = new ArrayList<>();
        for (String line : enchant.DESCRIPTION) lore.add(ChatColor.GRAY + line);
        lore.add("");
        lore.add(ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(currentLevel));
        lore.add(ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(enchant.PRICE));
        lore.add(ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(tokens));
        lore.add("");
        lore.add(ChatColor.AQUA + "Max Level: " + ChatColor.WHITE + Utils.prettyNum(enchant.MAX_LEVEL));
        return GUI.createEnchantedMenuItem(id, icon, displayName, lore.toArray(new String[0]));
    }
}
