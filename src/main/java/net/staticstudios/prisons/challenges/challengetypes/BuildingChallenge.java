package net.staticstudios.prisons.challenges.challengetypes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.challenges.ChallengeType;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class BuildingChallenge extends ChallengeType<BlockPlaceEvent> {

    public BuildingChallenge() {
        super("BUILDING", Component.text("Building Challenge").color(ComponentUtil.BLUE).decorate(TextDecoration.BOLD), List.of(
                Component.empty().append(Component.text("Build something in your cell with enough")),
                Component.empty().append(Component.text("blocks to complete this challenge."))
        ), Material.BOOKSHELF, (event, challenge) -> challenge.addProgress(1));
    }

    @EventHandler
    void onBlockBreakProcess(BlockPlaceEvent e) {
        onEvent(e, e.getPlayer());
    }
}
