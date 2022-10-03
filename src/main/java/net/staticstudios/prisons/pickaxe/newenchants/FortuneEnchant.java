package net.staticstudios.prisons.pickaxe.newenchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.enchants.Enchantment;
import net.staticstudios.prisons.utils.ComponentUtil;

public class FortuneEnchant implements Enchantment<BlockBreakProcessEvent> {

    public FortuneEnchant() {
        register();
    }

    @Override
    public Class<BlockBreakProcessEvent> getListeningFor() {
        return BlockBreakProcessEvent.class;
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        blockBreak.getStats().setBlocksBrokenMultiplier(blockBreak.getStats().getBlocksBrokenMultiplier() * blockBreak.getPickaxe().getEnchantmentLevel(FortuneEnchant.class));
    }

    @Override
    public String getId() {
        return "pickaxe-fortune";
    }

    @Override
    public String getName() {
        return "Fortune";
    }

    @Override
    public Component getNameAsComponent() {
        return Component.empty()
                .append(Component.text(getName()))
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public Component getDisplayName() {
        return getNameAsComponent()
                .color(ComponentUtil.AQUA)
                .decorate(TextDecoration.BOLD);
    }

    @Override
    public int getMaxLevel() {
        return 2_500;
    }

    @Override
    public long getUpgradeCost() {
        return 1_000;
    }

    @Override
    public double getDefaultChanceToActivate() {
        return 1;
    }

    @Override
    public double getChanceToActivateAtMaxLevel() {
        return 0;
    }
}
