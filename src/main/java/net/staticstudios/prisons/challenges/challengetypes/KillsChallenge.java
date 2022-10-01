package net.staticstudios.prisons.challenges.challengetypes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.challenges.ChallengeType;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class KillsChallenge extends ChallengeType<PlayerDeathEvent> {

    public KillsChallenge() {
        super("PVP_KILLS", Component.text("Kills Challenge").color(ComponentUtil.DARK_RED).decorate(TextDecoration.BOLD), List.of(
                Component.empty().append(Component.text("Kill players in PvP to complete this challenge."))
        ), Material.IRON_SWORD, (event, challenge) -> challenge.addProgress(1));
    }

    @EventHandler
    void onBlockBreakProcess(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer != null) {
            onEvent(e, killer);
        }
    }
}
