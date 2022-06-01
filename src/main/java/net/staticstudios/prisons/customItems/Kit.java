package net.staticstudios.prisons.customItems;

import net.staticstudios.prisons.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.ArrayList;

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
        if (helmet != null) Utils.Players.addToInventory(player, helmet);
        if (chestplate != null) Utils.Players.addToInventory(player, chestplate);
        if (leggings != null) Utils.Players.addToInventory(player, leggings);
        if (boots != null) Utils.Players.addToInventory(player, boots);
        if (sword != null) Utils.Players.addToInventory(player, sword);
        if (axe != null) Utils.Players.addToInventory(player, axe);
        if (bow != null) Utils.Players.addToInventory(player, bow);
        if (pickaxe != null) Utils.Players.addToInventory(player, pickaxe);
        if (arrow != null) Utils.Players.addToInventory(player, arrow);
        if (food1 != null) Utils.Players.addToInventory(player, food1);
        if (food2 != null) Utils.Players.addToInventory(player, food2);
        if (food3 != null) Utils.Players.addToInventory(player, food3);
        if (potion1 != null) Utils.Players.addToInventory(player, potion1);
        if (potion2 != null) Utils.Players.addToInventory(player, potion2);
        if (potion3 != null) Utils.Players.addToInventory(player, potion3);
        if (potion4 != null) Utils.Players.addToInventory(player, potion4);
        if (potion5 != null) Utils.Players.addToInventory(player, potion5);
        if (potion6 != null) Utils.Players.addToInventory(player, potion6);
        if (potion7 != null) Utils.Players.addToInventory(player, potion7);
        if (potion8 != null) Utils.Players.addToInventory(player, potion8);
        if (potion9 != null) Utils.Players.addToInventory(player, potion9);
        if (potion10 != null) Utils.Players.addToInventory(player, potion10);
        if (potion11 != null) Utils.Players.addToInventory(player, potion11);
        if (potion12 != null) Utils.Players.addToInventory(player, potion12);
        if (potion13 != null) Utils.Players.addToInventory(player, potion13);
        if (misc1 != null) Utils.Players.addToInventory(player, misc1);
        if (misc2 != null) Utils.Players.addToInventory(player, misc2);
    }
}
