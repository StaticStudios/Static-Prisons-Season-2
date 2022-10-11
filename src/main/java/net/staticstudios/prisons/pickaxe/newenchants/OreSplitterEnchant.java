package net.staticstudios.prisons.pickaxe.newenchants;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.newenchants.handler.PickaxeEnchant;

public class OreSplitterEnchant extends PickaxeEnchant<BlockBreakProcessEvent> {

    public OreSplitterEnchant() {
        super(BlockBreakProcessEvent.class, "pickaxe-doublefortune");
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        PrisonPickaxe pickaxe = event.getBlockBreak().getPickaxe();
        if (pickaxe != null && shouldActivate(pickaxe)) return;

        BlockBreak blockBreak = event.getBlockBreak();
        blockBreak.getStats().setBlocksBrokenMultiplier(
                blockBreak.getStats().getBlocksBrokenMultiplier() * 2
        );
    }
}
