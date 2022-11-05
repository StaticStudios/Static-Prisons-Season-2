package net.staticstudios.prisons.fishing;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.fishing.events.FishCaughtEvent;
import net.staticstudios.prisons.pvp.PvPManager;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.function.Consumer;

public class FishingListener implements Listener {

    static WeightedElements<Consumer<PlayerFishEvent>> FISHING_REWARDS = null;

    @EventHandler
    void onFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().equals(PvPManager.PVP_WORLD)) {
            e.setCancelled(true);
            player.sendMessage(Prefix.FISHING
                    .append(Component.text("You can only fish in the PvP arena!")
                    .color(ComponentUtil.RED)));
            return;
        }

        PrisonFishingRod fishingRod = PrisonFishingRod.fromItem(e.getPlayer().getInventory().getItemInMainHand());
        if (fishingRod == null) {
            //This is not a prison fishing rod so make it unable to catch fish
            e.getHook().setMaxWaitTime(Integer.MAX_VALUE);
        }
    }

    @EventHandler
    void onCraft(CraftItemEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType().equals(Material.FISHING_ROD)) e.setCancelled(true);
    }
}
