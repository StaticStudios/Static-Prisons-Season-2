package net.staticstudios.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.utils.ComponentUtil;

public class Prefix {

    public static final Component PVP = Component.text("PvP ").color(ComponentUtil.RED)
            .append(Component.text(" >>" ).color(ComponentUtil.DARK_GRAY))
            .decoration(TextDecoration.BOLD, true)
            .append(Component.text(" "));

    public static final Component KOTH = Component.text("KOTH ").color(ComponentUtil.DARK_RED)
            .append(Component.text(" >>" ).color(ComponentUtil.DARK_GRAY))
            .decoration(TextDecoration.BOLD, true)
            .append(Component.text(" "));


}
