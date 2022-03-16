package me.staticstudios.prisons.gameplay.customItems;

import me.staticstudios.prisons.utils.Utils;
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
        if (helmet != null) Utils.addItemToPlayersInventoryAndDropExtra(player, helmet);
        if (chestplate != null) Utils.addItemToPlayersInventoryAndDropExtra(player, chestplate);
        if (leggings != null) Utils.addItemToPlayersInventoryAndDropExtra(player, leggings);
        if (boots != null) Utils.addItemToPlayersInventoryAndDropExtra(player, boots);
        if (sword != null) Utils.addItemToPlayersInventoryAndDropExtra(player, sword);
        if (axe != null) Utils.addItemToPlayersInventoryAndDropExtra(player, axe);
        if (bow != null) Utils.addItemToPlayersInventoryAndDropExtra(player, bow);
        if (pickaxe != null) Utils.addItemToPlayersInventoryAndDropExtra(player, pickaxe);
        if (arrow != null) Utils.addItemToPlayersInventoryAndDropExtra(player, arrow);
        if (food1 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, food1);
        if (food2 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, food2);
        if (food3 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, food3);
        if (potion1 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion1);
        if (potion2 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion2);
        if (potion3 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion3);
        if (potion4 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion4);
        if (potion5 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion5);
        if (potion6 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion6);
        if (potion7 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion7);
        if (potion8 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion8);
        if (potion9 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion9);
        if (potion10 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion10);
        if (potion11 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion11);
        if (potion12 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion12);
        if (potion13 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, potion13);
        if (misc1 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, misc1);
        if (misc2 != null) Utils.addItemToPlayersInventoryAndDropExtra(player, misc2);
    }
}
