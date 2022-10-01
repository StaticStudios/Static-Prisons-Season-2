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

public class DeathsChallenge extends ChallengeType<PlayerDeathEvent> {

    public DeathsChallenge() {
        super("PVP_DEATHS", Component.text("Deaths Challenge").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD), List.of(
                Component.empty().append(Component.text("Die to players in PvP to complete this challenge."))
        ), Material.SKELETON_SKULL, (event, challenge) -> challenge.addProgress(1));
    }

    @EventHandler
    void onBlockBreakProcess(PlayerDeathEvent e) {
        onEvent(e, e.getPlayer());
    }
}
