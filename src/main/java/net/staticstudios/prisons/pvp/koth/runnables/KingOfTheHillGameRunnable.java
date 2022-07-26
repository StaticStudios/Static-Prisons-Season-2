package net.staticstudios.prisons.pvp.koth.runnables;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public class KingOfTheHillGameRunnable implements Runnable {
    private int timeInSeconds;

    public KingOfTheHillGameRunnable(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    @Override
    public void run() {

        if (!KingOfTheHillManager.isEventRunning()) return;

        if (timeInSeconds <= 0) {
            evaluateWinner();
            return;
        }

        PVP_WORLD.getPlayers().stream()
                .filter(player -> KingOfTheHillManager.isKothArea(player.getLocation(), 5.0))
                .forEach(KingOfTheHillManager::incrementPlayerInKothArea);

        if (timeInSeconds % 120 == 0 || timeInSeconds <= 10) {
            Bukkit.broadcast(Prefix.KOTH
                    .append(Component.text("King of the Hill event is ending in "))
                    .append(Component.text(timeInSeconds >= 60 ? timeInSeconds / 60 : timeInSeconds)
                            .append(Component.text(timeInSeconds >= 60 ? " minutes" : " seconds")).color(ComponentUtil.GOLD))
                    .append(Component.text(".")));
        }

        timeInSeconds--;

        Bukkit.getScheduler().runTaskLaterAsynchronously(StaticPrisons.getInstance(), this, 20);
    }

    public void evaluateWinner() {

        Optional<Player> possibleWinner = KingOfTheHillManager.getTimeInKothAreaPerPlayer().entrySet().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                .map(Map.Entry::getKey)
                .filter(Player::isOnline)
                .findFirst();

        if (possibleWinner.isEmpty()) {
            Bukkit.broadcast(Prefix.KOTH.append(Component.text("No one has been selected as the winner of the King of the Hill event!")));
        } else {
            Bukkit.broadcast(Prefix.KOTH
                    .append(Component.text(possibleWinner.get().getName()).color(ComponentUtil.GOLD))
                    .append(Component.text(" is the King of the Hill!")));

            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                KingOfTheHillManager.onWin(possibleWinner.get());
            });
        }
        KingOfTheHillManager.stopEvent();

        Bukkit.broadcast(Prefix.KOTH.append(Component.text("King of the Hill event has ended!")));
    }

    public int getTimeLeft() {
        return timeInSeconds;
    }
}
