package me.staticstudios.prisons;

import me.staticstudios.prisons.crates.CrateManager;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.enchants.EnchantEffects;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.misc.Warps;
import me.staticstudios.prisons.misc.chat.CustomChatMessage;
import me.staticstudios.prisons.misc.scoreboard.CustomScoreboard;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.math.BigInteger;

public class Events implements Listener {
    @EventHandler
    void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Warps.warpToSpawn(player);
        //Check if they have joined before
        if (!new ServerData().checkIfPlayerHasJoinedBeforeByUUID(player.getUniqueId().toString())) {
            Bukkit.broadcastMessage(org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.GREEN + " has joined the server for the first time! " + org.bukkit.ChatColor.GRAY + "(" + org.bukkit.ChatColor.AQUA + "#" + Utils.addCommasToInt(new ServerData().getPlayerUUIDsToNamesMap().size() + 1) + org.bukkit.ChatColor.GRAY + ")");
            Utils.addItemToPlayersInventoryAndDropExtra(player, Utils.createNewPickaxe());
            PlayerData playerData = new PlayerData(player);
            playerData.setPlayerRank("member");
            playerData.setStaffRank("member");
        }
        //Update the playernames
        new ServerData().putPlayerNamesToUUID(player.getName(), player.getUniqueId().toString());
        new ServerData().putPlayerUUIDsToName(player.getUniqueId().toString(), player.getName());

        //Set up the player's tablist
        TabList.onJoin(player);
        //Set up the player's scoreboard
        CustomScoreboard.updateSingleScoreboard(player);

        //Update lukperms for player ranks
        Utils.updateLuckPermsForPlayerRanks(player);

        //Update potion effects based off custom enchants
        EnchantEffects.giveEffect(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
    }
    @EventHandler
    void playerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        //Remove player from the scoreboard map to prevent updating an offline player's scoreboard
        CustomScoreboard.playerLeft(player.getUniqueId().toString());
    }
    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        new CustomChatMessage(e).sendFormatted();
    }
    @EventHandler
    void onChangeHeld(PlayerItemHeldEvent e) {
        EnchantEffects.giveEffect(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }
    @EventHandler
    void invClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        EnchantEffects.giveEffect((Player) e.getWhoClicked(), e.getWhoClicked().getInventory().getItemInMainHand());
    }
    @EventHandler
    void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (e.getTo().getY() < 0) {
            Warps.warpToSpawn(player);
            return;
        }

        //Prevent players from flying between mines
        if (player.getWorld().getName().equals("mines")) {
            int x = ((int) e.getTo().getX());
            int z = ((int) e.getTo().getZ());
            int thresh = 50;
            if (x % 750 >= 750 - thresh || (x % 750 <= thresh && Math.abs(x) > thresh * 2)) {
                //e.setCancelled(true);
            } else if (z > 250 || z < -250) e.setCancelled(true);
        }
    }

    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getHand() != null) {
            if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        }
        if (e.getClickedBlock() != null && !e.getClickedBlock().getType().equals(Material.AIR)) {
            //Check if it is also a block place event
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getType().isBlock() && !e.getClickedBlock().getType().isInteractable()) {
                    return;
                }
            }
        }
        //Check if the player is holding a pickaxe and is trying to open the enchants menu
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Utils.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) {
                if (new PlayerData(player).getIsMobile()) return;
                GUI.getGUIPage("enchantsMain").args = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING);
                GUI.getGUIPage("enchantsMain").open(player);
                e.setCancelled(true);
                return;
            }
        }
        if (CrateManager.checkIfCrateWasClicked(e)) return;

    }
}
