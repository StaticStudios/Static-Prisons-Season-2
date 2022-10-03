package net.staticstudios.prisons.enchants.testing;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.enchants.Enchantment;

public class TestEnchant implements Enchantment<BlockBreakProcessEvent> {

    public TestEnchant() {

        register();
    }

    @Override
    public Class<BlockBreakProcessEvent> getListeningFor() {
        return BlockBreakProcessEvent.class;
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        event.getBlockBreak().addAfterProcess(bb -> {
            bb.getPlayer().sendMessage("You broke " + bb.getStats().getBlocksBroken() + " blocks!");
        });
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public Component getUnformattedDisplayName() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public long getUpgradeCost() {
        return 0;
    }
}
