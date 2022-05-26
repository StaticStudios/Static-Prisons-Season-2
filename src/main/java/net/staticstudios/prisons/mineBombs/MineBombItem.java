package net.staticstudios.prisons.mineBombs;

import com.sk89q.worldedit.math.BlockVector3;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.StaticVars;
import net.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigInteger;
import java.util.Map;

public class MineBombItem {
    public static void itemDropped(PlayerDropItemEvent e) {
        //Check if the item was a mine bomb, if so start a timer for it and set the pickup delay to a big number
        if (!e.getPlayer().getLocation().getWorld().equals(StaticVars.MINES_WORLD)) return;
        if (!e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER))
            return;
        e.getItemDrop().setPickupDelay(20 * 10);
        int size = e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER);
        double radius = 0;
        switch (size) {
            case 1 -> radius = 15;
            case 2 -> radius = 20;
            case 3 -> radius = 27;
            case 4 -> radius = 40;
        }
        double finalRadius = radius;
        Bukkit.getScheduler().runTaskLaterAsynchronously(StaticPrisons.getInstance(), () -> {
            PlayerData playerData = new PlayerData(e.getPlayer());
            Location loc = e.getItemDrop().getLocation();
            StaticMine mine = null;
            for (StaticMine m : StaticMine.getAllMines()) {
                BlockVector3 minPoint = m.getMinVector();
                BlockVector3 maxPoint = m.getMaxVector();
                if (minPoint.getBlockX() <= loc.getBlockX() && minPoint.getBlockZ() <= loc.getBlockZ() && maxPoint.getBlockX() >= loc.getBlockX() && maxPoint.getBlockZ() >= loc.getBlockZ()) {
                    mine = m;
                    break;
                }
            }
            if (mine == null) {
                e.getItemDrop().setPickupDelay(0);
                return;
            }
            MineBomb bomb = new MineBomb(e.getItemDrop().getLocation(), finalRadius);
            Map<Material, BigInteger> blocksBroken = bomb.explode(mine);
            StaticMine finalMine = mine;
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                finalMine.removeBlocksBrokenInMine(bomb.blocksChanged);
                e.getItemDrop().remove();
                boolean backpackWasFull = playerData.getBackpackIsFull();
                if (!backpackWasFull) for (Material key : blocksBroken.keySet())
                    playerData.addBackpackAmountOf(key, blocksBroken.get(key).multiply(BigInteger.valueOf(10000)));
                if (playerData.getBackpackIsFull()) {
                    if (!backpackWasFull) {
                        if (Utils.checkIfPlayerCanAutoSell(playerData) && playerData.getIsAutoSellEnabled())
                            playerData.sellBackpack(e.getPlayer(), true);
                        e.getPlayer().sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + Utils.prettyNum(playerData.getBackpackSize()) + "/" + Utils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                        e.getPlayer().sendMessage(ChatColor.RED + "Your backpack is full!");
                    }
                }
            });
        }, 20 * 5);
    }
}
