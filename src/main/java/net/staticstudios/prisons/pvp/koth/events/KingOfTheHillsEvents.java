package net.staticstudios.prisons.pvp.koth.events;

import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public class KingOfTheHillsEvents implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld() != PVP_WORLD) return;

        if (KingOfTheHillManager.isEventRunning()) {
            if (KingOfTheHillManager.isKothBlock(player.getLocation(), 5)) {
                KingOfTheHillManager.newKoth(player);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (KingOfTheHillManager.isEventRunning()) {
            KingOfTheHillManager.removePlayerFromMaps(event.getPlayer());
        }
    }
}