package me.staticstudios.prisons.gameplay.mineBombs;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.mines.MineManager;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigInteger;
import java.util.Map;

public class MineBombItem {
    public static void itemDropped(PlayerDropItemEvent e) {
        //Check if the item was a mine bomb, if so start a timer for it and set the pickup delay to a big number
        if (!e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "mineBomb"), PersistentDataType.INTEGER)) return;
        e.getItemDrop().setPickupDelay(20 * 10);
        int size = e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "mineBomb"), PersistentDataType.INTEGER);
        double radius = 0;
        switch (size) {
            case 1 -> radius = 15;
            case 2 -> radius = 20;
            case 3 -> radius = 27;
            case 4 -> radius = 40;
        }
        double finalRadius = radius;
        Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
            PlayerData playerData = new PlayerData(e.getPlayer());
            Map<Material, BigInteger> blocksBroken = new MineBomb(e.getItemDrop().getLocation(), finalRadius).explode(MineManager.allMines.get(MineManager.getMineIDFromLocation(e.getItemDrop().getLocation())));
            e.getItemDrop().remove();
            boolean backpackWasFull = playerData.getBackpackIsFull();
            if (!backpackWasFull) for (Material key : blocksBroken.keySet()) playerData.addBackpackAmountOf(key, blocksBroken.get(key).multiply(BigInteger.valueOf(50000)));
            if (playerData.getBackpackIsFull()) {
                if (!backpackWasFull) {
                    if (Utils.checkIfPlayerCanAutoSell(playerData) && playerData.getIsAutoSellEnabled()) playerData.sellBackpack(e.getPlayer(), true);
                    e.getPlayer().sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + Utils.prettyNum(playerData.getBackpackSize()) + "/" + Utils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                    e.getPlayer().sendMessage(ChatColor.RED + "Your backpack is full!");
                }
            }
        }, 20 * 5);
    }
}
