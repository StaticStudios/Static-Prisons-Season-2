package net.staticstudios.prisons.utils;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.ui.scoreboard.CustomScoreboard;
import net.staticstudios.prisons.ui.tablist.TabList;
import net.staticstudios.prisons.admin.AdminManager;
import net.staticstudios.prisons.customitems.old.Vouchers;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.external.DiscordLink;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class EventListener implements Listener {

    @EventHandler
    void onNewTick(ServerTickStartEvent e) {
        StaticPrisons.currentTick += 1;
    }
    @EventHandler(priority = EventPriority.LOW)
    void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setInvisible(false);

        if (AdminManager.checkIfPlayerInVanishAtJoin(event.getPlayer())) {
            event.joinMessage(Component.empty());
        } else {
            event.joinMessage(Component.text("Joined", ComponentUtil.GREEN).decorate(TextDecoration.BOLD)
                    .append(Component.text(" -> ")).decoration(TextDecoration.BOLD, false)
                    .append(player.name().color(ComponentUtil.WHITE)));
        }

        Warps.warpToSpawn(player);
        //Update the player name to UUID mappings
        ServerData.playerJoined(player);

        TabList.addPlayer(player);
        CustomScoreboard.updatePlayerScoreboard(player);
        PrisonUtils.updateLuckPermsForPlayerRanks(player); //Gives the player permission to use certain features if they have a rank
        //EnchantEffects.giveEffect(player, Utils.getItemInMainHand(player));

        //Updates a player's discord name
        DiscordLink.playerJoined(player);
    }
    @EventHandler
    void playerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (AdminManager.containedInHiddenPlayers(player)) {
            e.quitMessage(Component.empty());
        } else {
//            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&a&lLeft&a -> &f" + e.getPlayer().getName()));
            e.quitMessage(Component.text("Left").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)
                    .append(Component.text(" -> ")).decoration(TextDecoration.BOLD, false)
                    .append(player.name().color(ComponentUtil.WHITE)));
        }

        //Remove player from the scoreboard map to prevent updating an offline player's scoreboard
        CustomScoreboard.playerLeft(player.getUniqueId().toString());
        PlayerData playerData = new PlayerData(player);
        playerData.setIsNitroBoosting(false);
    }
    @EventHandler
    void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (e.getTo().getY() < 0) {
            if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE) || e.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;
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
        if (Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND)) return;
        if (Vouchers.onInteract(e)) return;
        //if (CrateManager.checkIfCrateWasClicked(e)) return;

        if (e.getClickedBlock() != null && !e.getClickedBlock().getType().equals(Material.AIR)) {
            //Check if it is also a block place event
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getType().isBlock() && !e.getClickedBlock().getType().isInteractable()) return;
            }
        }
    }

}
