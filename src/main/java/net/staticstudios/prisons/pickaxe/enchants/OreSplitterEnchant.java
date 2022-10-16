package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;

public class OreSplitterEnchant extends PickaxeEnchant {

    public OreSplitterEnchant() {
        super(OreSplitterEnchant.class, "pickaxe-doublefortune");
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        blockBreak.stats().setBlocksBrokenMultiplier(
                blockBreak.stats().getBlocksBrokenMultiplier() * 2
        );
    }
}
