package net.staticstudios.prisons.mines;

import net.kyori.adventure.text.Component;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.mines.api.events.MineCreatedEvent;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.Warps;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MineManager implements Listener {
    static final Component MINE_REFILL_MESSAGE = Prefix.MINES.append(Component.text("The mine has been refilled").color(ComponentUtil.WHITE));

    public static void init() {
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new MineManager(), StaticPrisons.getInstance());
        StaticMines.enable(StaticPrisons.getInstance());
    }

    @EventHandler
    void onMineCreate(MineCreatedEvent e) {
        Location midPoint = new Location(e.getMine().getBukkitWorld(),
                (e.getMine().getMaxPoint().getBlockX() - e.getMine().getMinPoint().getBlockX()) / 2d + e.getMine().getMinPoint().getBlockX(),
                e.getMine().getMaxPoint().getBlockY() + 1,
                (e.getMine().getMaxPoint().getBlockZ() - e.getMine().getMinPoint().getBlockZ()) / 2d + e.getMine().getMinPoint().getBlockZ()
        );
        e.getMine().runOnRefill(mine -> {
            for (Player player : mine.getPlayersInMine()) {
                player.sendMessage(MINE_REFILL_MESSAGE);
                Warps.warpSomewhere(player, midPoint, true);
            }
        });
    }
}
