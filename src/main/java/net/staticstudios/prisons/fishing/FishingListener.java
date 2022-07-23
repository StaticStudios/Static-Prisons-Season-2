package net.staticstudios.prisons.fishing;

import net.staticstudios.prisons.pvp.PvPManager;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class FishingListener implements Listener {

    static WeightedElements<Consumer<PlayerFishEvent>> FISHING_REWARDS = null;

    @EventHandler
    void onFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().equals(PvPManager.PVP_WORLD)) {
            e.setCancelled(true);
            player.sendMessage(FishingManager.PREFIX + ChatColor.RED + "You can only fish in the PvP arena!");
            return;
        }
        e.getHook().setMinWaitTime(1);
        e.getHook().setMaxWaitTime(3);
        if (e.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        if (e.getCaught() == null) return;
        FISHING_REWARDS.getRandom().accept(e);
        e.setExpToDrop(0);
    }
}
