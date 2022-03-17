package me.staticstudios.prisons.core;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.core.enchants.EnchantEffects;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.data.serverData.ServerData;
import me.staticstudios.prisons.core.enchants.PrisonEnchants;
import me.staticstudios.prisons.external.DiscordLink;
import me.staticstudios.prisons.external.discord_old.DiscordAddRoles;
import me.staticstudios.prisons.external.discord_old.DiscordBot;
import me.staticstudios.prisons.external.discord_old.LinkHandler;
import me.staticstudios.prisons.gameplay.Warps;
import me.staticstudios.prisons.gameplay.crates.CrateManager;
import me.staticstudios.prisons.gameplay.customItems.Vouchers;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.scoreboard.CustomScoreboard;
import me.staticstudios.prisons.gameplay.tablist.TabList;
import me.staticstudios.prisons.utils.Utils;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    void blockBreak(BlockBreakEvent e) {
        me.staticstudios.prisons.core.blockBroken.BlockBreakEvent.onBlockBreak(e);
    }
    @EventHandler
    void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&a&lJoined&a -> &f" + player.getName()));
        Warps.warpToSpawn(player);
        Utils.checkIfPlayerHasJoinedBefore(player);
        //Update the player name to UUID makkpings
        new ServerData().putPlayerNamesToUUID(player.getName(), player.getUniqueId().toString());
        new ServerData().putPlayerUUIDsToName(player.getUniqueId().toString(), player.getName());

        TabList.addPlayer(player);
        CustomScoreboard.updatePlayerScoreboard(player);
        Utils.updateLuckPermsForPlayerRanks(player); //Gives the player permission to use certain features if they have a rank
        EnchantEffects.giveEffect(player, Utils.getItemInMainHand(player));

        //Updates a player's discord name
        DiscordLink.playerJoined(player);
    }
    @EventHandler
    void playerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&a&lLeft&a -> &f" + e.getPlayer().getName()));
        Player player = e.getPlayer();
        //Remove player from the scoreboard map to prevent updating an offline player's scoreboard
        CustomScoreboard.playerLeft(player.getUniqueId().toString());
        PlayerData playerData = new PlayerData(player);
        playerData.setIsNitroBoosting(false);
    }
    @EventHandler
    void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (e.getTo().getY() < 0) {
            Warps.warpToSpawn(player);
            return;
        }

        //Prevent players from flying between virtual grids
        int gridSize = 0; //Size of the grid
        int distanceAllowed = 0; //The distance that players may go from the center of the grid before the event gets canceled
        boolean useGrid = false; //Set if a world should lock people in a grid
        switch (player.getWorld().getName()) {
            case "mines" -> {
                gridSize = 500;
                distanceAllowed = 200;
                useGrid = true;
            }
            case "islands" -> {
                gridSize = 1000;
                distanceAllowed = 100;
                useGrid = true;
            }
        }
        if (useGrid) {
            double distanceX = Math.abs(e.getTo().getX()) % gridSize;
            double distanceZ = Math.abs(e.getTo().getZ()) % gridSize;
            if ((distanceX + distanceAllowed) / gridSize >= 1) distanceX = gridSize - distanceX;
            if ((distanceZ + distanceAllowed) / gridSize >= 1) distanceZ = gridSize - distanceZ;
            if (distanceX > distanceAllowed || distanceZ > distanceAllowed) e.setCancelled(true);
        }
    }

    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getHand() != null) {
            if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        }
        if (Vouchers.onInteract(e)) return;
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
                String id = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING);
                PrisonEnchants.playerUUIDToPickaxeID.put(player.getUniqueId(), id);
                GUI.getGUIPage("enchantsMain").open(player);
                e.setCancelled(true);
                return;
            }
        }
        if (CrateManager.checkIfCrateWasClicked(e)) return;
    }
}
