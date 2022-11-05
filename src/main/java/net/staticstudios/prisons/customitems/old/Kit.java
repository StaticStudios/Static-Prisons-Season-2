package net.staticstudios.prisons.customitems.old;

import net.staticstudios.prisons.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    public String name = "";
    public List<String> whatComesWithTheKit = new ArrayList<>();
    public ItemStack helmet = null;
    public ItemStack chestplate = null;
    public ItemStack leggings = null;
    public ItemStack boots = null;
    public ItemStack sword = null;
    public ItemStack axe = null;
    public ItemStack bow = null;
    public ItemStack pickaxe = null;
    public ItemStack arrow = null;
    public ItemStack food1 = null;
    public ItemStack food2 = null;
    public ItemStack food3 = null;
    public ItemStack potion1 = null;
    public ItemStack potion2 = null;
    public ItemStack potion3 = null;
    public ItemStack potion4 = null;
    public ItemStack potion5 = null;
    public ItemStack potion6 = null;
    public ItemStack potion7 = null;
    public ItemStack potion8 = null;
    public ItemStack potion9 = null;
    public ItemStack potion10 = null;
    public ItemStack potion11 = null;
    public ItemStack potion12 = null;
    public ItemStack potion13 = null;
    public ItemStack misc1 = null;
    public ItemStack misc2 = null;

    public void addItemsToPlayersInventory(Player player) {
        if (helmet != null) PlayerUtils.addToInventory(player, helmet);
        if (chestplate != null) PlayerUtils.addToInventory(player, chestplate);
        if (leggings != null) PlayerUtils.addToInventory(player, leggings);
        if (boots != null) PlayerUtils.addToInventory(player, boots);
        if (sword != null) PlayerUtils.addToInventory(player, sword);
        if (axe != null) PlayerUtils.addToInventory(player, axe);
        if (bow != null) PlayerUtils.addToInventory(player, bow);
        if (pickaxe != null) PlayerUtils.addToInventory(player, pickaxe);
        if (arrow != null) PlayerUtils.addToInventory(player, arrow);
        if (food1 != null) PlayerUtils.addToInventory(player, food1);
        if (food2 != null) PlayerUtils.addToInventory(player, food2);
        if (food3 != null) PlayerUtils.addToInventory(player, food3);
        if (potion1 != null) PlayerUtils.addToInventory(player, potion1);
        if (potion2 != null) PlayerUtils.addToInventory(player, potion2);
        if (potion3 != null) PlayerUtils.addToInventory(player, potion3);
        if (potion4 != null) PlayerUtils.addToInventory(player, potion4);
        if (potion5 != null) PlayerUtils.addToInventory(player, potion5);
        if (potion6 != null) PlayerUtils.addToInventory(player, potion6);
        if (potion7 != null) PlayerUtils.addToInventory(player, potion7);
        if (potion8 != null) PlayerUtils.addToInventory(player, potion8);
        if (potion9 != null) PlayerUtils.addToInventory(player, potion9);
        if (potion10 != null) PlayerUtils.addToInventory(player, potion10);
        if (potion11 != null) PlayerUtils.addToInventory(player, potion11);
        if (potion12 != null) PlayerUtils.addToInventory(player, potion12);
        if (potion13 != null) PlayerUtils.addToInventory(player, potion13);
        if (misc1 != null) PlayerUtils.addToInventory(player, misc1);
        if (misc2 != null) PlayerUtils.addToInventory(player, misc2);
    }
}
