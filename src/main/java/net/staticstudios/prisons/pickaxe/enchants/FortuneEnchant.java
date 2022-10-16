package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;

public class FortuneEnchant extends PickaxeEnchant {

    public FortuneEnchant() {
        super(FortuneEnchant.class, "pickaxe-fortune");
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        blockBreak.stats().setBlocksBrokenMultiplier(blockBreak.stats().getBlocksBrokenMultiplier() * blockBreak.getPickaxe().getEnchantmentLevel(FortuneEnchant.class));
    }
}
