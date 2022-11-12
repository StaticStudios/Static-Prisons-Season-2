package net.staticstudios.prisons.admin;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AdminListener implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (player.hasPermission("static.vanish")) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(p -> {
            if (AdminManager.isHidden(p)) {
                player.hidePlayer(StaticPrisons.getInstance(), p);
            }
        });
    }
}
