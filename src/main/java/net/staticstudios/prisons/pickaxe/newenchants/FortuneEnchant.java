package net.staticstudios.prisons.pickaxe.newenchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.ComponentUtil;

import java.util.List;

public class FortuneEnchant extends PickaxeEnchant<BlockBreakProcessEvent> {

    public FortuneEnchant() {
        super(BlockBreakProcessEvent.class, "pickaxe-fortune");
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        blockBreak.getStats().setBlocksBrokenMultiplier(blockBreak.getStats().getBlocksBrokenMultiplier() * blockBreak.getPickaxe().getEnchantmentLevel(FortuneEnchant.class));
    }
}
