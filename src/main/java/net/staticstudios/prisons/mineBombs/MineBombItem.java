package net.staticstudios.prisons.mineBombs;

import com.sk89q.worldedit.math.BlockVector3;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.PrisonBackpacks;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class MineBombItem { //todo: clean this file up, precompute minebombs
    private static final long VIRTUAL_FORTUNE = 1000;
    public static void blockPlaced(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!e.getPlayer().getLocation().getWorld().equals(Constants.MINES_WORLD) && !e.getPlayer().getLocation().getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return;
        if (e.getItem() == null) return;
        if (e.getItem().getItemMeta() == null) return;
        if (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER)) return;
        int size = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER);
        double radius = 0;
        switch (size) {
            case 1 -> radius = 15;
            case 2 -> radius = 20;
            case 3 -> radius = 27;
            case 4 -> radius = 40;
        }
        double finalRadius = radius;
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            PlayerData playerData = new PlayerData(e.getPlayer());
            Location loc = e.getInteractionPoint();
            StaticMine mine = null;
            for (StaticMine m : StaticMine.getAllMines()) {
                if (!loc.getWorld().equals(m.getWorld())) continue;
                BlockVector3 minPoint = m.getMinVector();
                BlockVector3 maxPoint = m.getMaxVector();
                if (minPoint.getBlockX() <= loc.getBlockX() && minPoint.getBlockZ() <= loc.getBlockZ() && maxPoint.getBlockX() >= loc.getBlockX() && maxPoint.getBlockZ() >= loc.getBlockZ()) {
                    mine = m;
                    break;
                }
            }
            if (mine == null) {
                e.setCancelled(true);
                return;
            }
            MineBomb bomb = new MineBomb(loc, finalRadius);
            Map<Material, Long> blocksBroken = bomb.explode(mine);
            StaticMine finalMine = mine;
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                finalMine.removeBlocksBrokenInMine(bomb.blocksChanged);
                e.getItem().setAmount(e.getItem().getAmount() - 1);
                Map<MineBlock, Long> map = new HashMap<>();
                for (Map.Entry<Material, Long> entry: blocksBroken.entrySet()) {
                    map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * VIRTUAL_FORTUNE);
                }
                PrisonBackpacks.addToBackpacks(e.getPlayer(), map);
//                boolean backpackWasFull = playerData.getBackpackIsFull();
//                if (!backpackWasFull) {
//
//                    playerData.addAllToBackpack(map);
//                }
//                PrisonUtils.Players.backpackFullCheck(backpackWasFull, e.getPlayer(), playerData);
            });
        });
    }
    public static void itemDropped(PlayerDropItemEvent e) {
        //Check if the item was a mine bomb, if so start a timer for it and set the pickup delay to a big number
        if (!e.getPlayer().getLocation().getWorld().equals(Constants.MINES_WORLD) && !e.getPlayer().getLocation().getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return;
        if (!e.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "mineBomb"), PersistentDataType.INTEGER)) return;
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
                if (!loc.getWorld().equals(m.getWorld())) continue;
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
            Map<Material, Long> blocksBroken = bomb.explode(mine);
            StaticMine finalMine = mine;
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                finalMine.removeBlocksBrokenInMine(bomb.blocksChanged);
                e.getItemDrop().remove();
                Map<MineBlock, Long> map = new HashMap<>();
                for (Map.Entry<Material, Long> entry: blocksBroken.entrySet()) {
                    map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * VIRTUAL_FORTUNE);
                }
                PrisonBackpacks.addToBackpacks(e.getPlayer(), map);
//                boolean backpackWasFull = playerData.getBackpackIsFull();
//                if (!backpackWasFull) {
//                    Map<MineBlock, Long> map = new HashMap<>();
//                    for (Map.Entry<Material, Long> entry: blocksBroken.entrySet()) {
//                        map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * VIRTUAL_FORTUNE);
//                    }
//                    playerData.addAllToBackpack(map);
//                }
//                PrisonUtils.Players.backpackFullCheck(backpackWasFull, e.getPlayer(), playerData);
            });
        }, 20 * 5);
    }
}
