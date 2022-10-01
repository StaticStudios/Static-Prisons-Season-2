package net.staticstudios.prisons.challenges.challengetypes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.challenges.ChallengeType;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.util.List;

public class MiningChallenge extends ChallengeType<BlockBreakProcessEvent> {

    public MiningChallenge() {
        super("MINING", Component.text("Mining Challenge").color(ComponentUtil.GOLD).decorate(TextDecoration.BOLD), List.of(
                Component.empty().append(Component.text("Mine a set amount of blocks to complete this")),
                Component.empty().append(Component.text("challenge. This challenge will count blocks")),
                Component.empty().append(Component.text("broken by enchants."))
        ), Material.COBBLESTONE, (event, challenge) -> event.getBlockBreak().addAfterProcess(blockBreak -> challenge.addProgress(blockBreak.getStats().getBlocksBroken())));
    }

    @EventHandler
    void onBlockBreakProcess(BlockBreakProcessEvent e) {
        onEvent(e, e.getPlayer());
    }
}
