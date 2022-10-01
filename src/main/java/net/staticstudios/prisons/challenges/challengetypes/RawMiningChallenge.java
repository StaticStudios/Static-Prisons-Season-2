package net.staticstudios.prisons.challenges.challengetypes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.challenges.ChallengeType;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.List;

public class RawMiningChallenge extends ChallengeType<BlockBreakProcessEvent> {

    public RawMiningChallenge() {
        super("RAW_MINING", Component.text("Raw Mining Challenge").color(ComponentUtil.RED).decorate(TextDecoration.BOLD), List.of(
                        Component.empty().append(Component.text("Mine a set amount of blocks to complete this")),
                        Component.empty().append(Component.text("challenge. This challenge does not count blocks")),
                        Component.empty().append(Component.text("that are broken from enchants, bombs, or")),
                        Component.empty().append(Component.text("anything other than a pickaxe."))
                ), Material.RAW_IRON, (event, challenge) -> challenge.addProgress(1));
    }

    @EventHandler
    void onBlockBreakProcess(BlockBreakProcessEvent e) {
        onEvent(e, e.getPlayer());
    }
}
