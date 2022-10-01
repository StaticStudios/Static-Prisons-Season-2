package net.staticstudios.prisons.challenges.challengetypes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.challenges.ChallengeType;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.List;

public class FishingChallenge extends ChallengeType<PlayerFishEvent> {

    public FishingChallenge() {
        super("FISHING", Component.text("Fishing Challenge").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD), List.of(
                Component.empty().append(Component.text("Fish in the PvP arena to complete this challenge."))
        ), Material.COD, (event, challenge) -> challenge.addProgress(1));
    }

    @EventHandler
    void onBlockBreakProcess(PlayerFishEvent e) {
        if (e.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        if (e.getCaught() == null) return;
        onEvent(e, e.getPlayer());
    }
}
