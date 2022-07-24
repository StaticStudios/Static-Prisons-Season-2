package net.staticstudios.prisons.pvp.koth.runnables;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public class KingOfTheHillGameRunnable implements Runnable {
    int timeInSeconds;

    int taskId;

    public KingOfTheHillGameRunnable(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    @Override
    public void run() {

        if (timeInSeconds != 0) {
            List<Player> playersInArea = PVP_WORLD.getPlayers().stream()
                    .filter(player -> KingOfTheHillManager.getKothBlockNearLocation(player.getLocation(), 5.0).isPresent())
                    .toList();
        }

        if (timeInSeconds > 0) {
            timeInSeconds--;
            taskId = Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), this, 20).getTaskId();
        } else {
            Bukkit.getScheduler().cancelTask(taskId);
        }


        System.out.println("aaaa");
    }
}
