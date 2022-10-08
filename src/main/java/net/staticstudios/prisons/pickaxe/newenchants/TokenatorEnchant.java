package net.staticstudios.prisons.pickaxe.newenchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.newenchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;

public class TokenatorEnchant extends PickaxeEnchant<BlockBreakProcessEvent> {

    public TokenatorEnchant() {
        super(BlockBreakProcessEvent.class, "pickaxe-tokenator");
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();
        if (pickaxe == null) return;

        int level = pickaxe.getEnchantmentLevel(TokenatorEnchant.class);
        if (level == 0) {
            pickaxe.setEnchantment(TokenatorEnchant.class, 1);
        }


        if (shouldActivate(pickaxe)) {
            blockBreak.getStats().setTokensEarned(blockBreak.getStats().getTokensEarned() + PrisonUtils.randomInt(200, 800)); //Average of 500 tokens per block
        }
        blockBreak.addAfterProcess(bb -> {
            if (bb.getStats().getTokensEarned() <= 0) return;

            bb.getPlayer().sendMessage(getDisplayName()
                    .append(Component.text(" >> ")
                            .color(ComponentUtil.DARK_GRAY)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text("Found " + PrisonUtils.addCommasToNumber((long) (bb.getStats().getTokensEarned() * bb.getStats().getTokenMultiplier())) + " tokens!")));
        });
    }
}
