package net.staticstudios.prisons.challenges.challengetypes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.challenges.ChallengeType;
import net.staticstudios.prisons.levelup.prestige.PrestigeEvent;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.List;

public class PrestigeChallenge extends ChallengeType<PrestigeEvent> {

    public PrestigeChallenge() {
        super("PRESTIGE", Component.text("Prestige Challenge").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD), List.of(
                Component.empty().append(Component.text("Prestige to complete this challenge."))
        ), Material.NETHER_STAR, (event, challenge) -> challenge.addProgress(event.getTo() - event.getFrom()));
    }

    @EventHandler
    void onBlockBreakProcess(PrestigeEvent e) {
        onEvent(e, e.getPlayer());
    }
}
