package net.staticstudios.prisons.pickaxe.enchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;

public class XPFinderEnchant extends PickaxeEnchant {

    private static long MIN_XP = 1;
    private static long MAX_XP = 5;

    public XPFinderEnchant() {
        super(XPFinderEnchant.class, "pickaxe-xpfinder");

        MIN_XP = getConfig().getLong("min_xp", MIN_XP);
        MAX_XP = getConfig().getLong("max_xp", MAX_XP);
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        long xpFound = PrisonUtils.randomLong(MIN_XP, MAX_XP);

        event.getPlayer().sendMessage(getDisplayName()
                .append(Component.text(" >> ")
                        .color(ComponentUtil.DARK_GRAY)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text("Found " + PrisonUtils.addCommasToNumber(xpFound) + " experience!")));

        event.getBlockBreak().getPlayerData().addPlayerXP(xpFound);
    }
}
