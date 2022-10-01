package net.staticstudios.prisons.challenges.challengetypes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.challenges.ChallengeType;
import net.staticstudios.prisons.levelup.rankup.RankUpEvent;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.List;

public class RankUpChallenge extends ChallengeType<RankUpEvent> {

    public RankUpChallenge() {
        super("RANK_UP", Component.text("Rank Up Challenge").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD), List.of(
                Component.empty().append(Component.text("Rank up to complete this challenge."))
        ), Material.EMERALD, (event, challenge) -> challenge.addProgress(event.getTo() - event.getFrom()));
    }

    @EventHandler
    void onBlockBreakProcess(RankUpEvent e) {
        onEvent(e, e.getPlayer());
    }
}
