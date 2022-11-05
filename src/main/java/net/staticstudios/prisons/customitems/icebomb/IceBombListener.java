package net.staticstudios.prisons.customitems.icebomb;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class IceBombListener implements Listener {

    @EventHandler
    void projectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof IceBombSource source) {
            if (e.getHitEntity() != null && e.getHitEntity().equals(source.player())) {
                e.setCancelled(true);
                return;
            }
            if (e.getHitBlock() == null) return;

            source.explode(e.getHitBlock().getLocation());
        }
    }

}
