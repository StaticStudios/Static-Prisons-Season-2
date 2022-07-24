package net.staticstudios.utils;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.utils.ComponentUtil;

public class Prefix {

    public static final Component PVP = Component.text("[").color(ComponentUtil.GRAY)
            .append(Component.text("PVP").color(ComponentUtil.RED))
            .append(Component.text("] ").color(ComponentUtil.GRAY));


}
