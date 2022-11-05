package net.staticstudios.prisons.pickaxe.enchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.customitems.icebomb.IceBomb;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;

public class TokenatorEnchant extends PickaxeEnchant {

    public TokenatorEnchant() {
        super( TokenatorEnchant.class, "pickaxe-tokenator");
    }

    @Override
    public boolean beforeEvent(BlockBreakProcessEvent event) {
        if (event.getBlockBreak().getBlock().getType() == Material.PACKED_ICE) {
            return event.getBlockBreak().getPickaxe() != null && event.getBlockBreak().getPickaxe().getEnchantmentLevel(TokenatorEnchant.class) > 0 && Math.random() <= IceBomb.TOKENATOR_PROC_CHANCE;
        }
        return super.beforeEvent(event);
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();

        int level = pickaxe.getEnchantmentLevel(TokenatorEnchant.class);
        if (level == 0) {
            pickaxe.setEnchantment(TokenatorEnchant.class, 1);
        }

        blockBreak.stats().setTokensEarned(blockBreak.stats().getTokensEarned() + PrisonUtils.randomInt(200, 800)); //Average of 500 tokens per block
        blockBreak.addAfterProcess(bb -> {
            if (bb.stats().getTokensEarned() <= 0) return;

            bb.getPlayer().sendMessage(getDisplayName()
                    .append(Component.text(" >> ")
                            .color(ComponentUtil.DARK_GRAY)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text("Found " + PrisonUtils.addCommasToNumber((long) (bb.stats().getTokensEarned() * bb.stats().getTokenMultiplier())) + " tokens!")
                            .color(ComponentUtil.WHITE)
                            .decoration(TextDecoration.BOLD, false)));
        });
    }
}
