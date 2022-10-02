package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Prefix {

    public static final Component PVP = Component.empty()
            .append(Component.text("PVP").color(ComponentUtil.RED).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component KOTH = Component.empty()
            .append(Component.text("KOTH").color(ComponentUtil.DARK_RED).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component PRESTIGE = Component.empty()
            .append(Component.text("Prestige").color(ComponentUtil.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component OUTPOST = Component.empty()
            .append(Component.text("Outpost").color(ComponentUtil.DARK_GREEN).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component VANISH = Component.empty()
            .append(Component.text("Vanish").color(ComponentUtil.GREEN).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component LOOT_BOX = Component.empty()
            .append(Component.text("Loot Box").color(ComponentUtil.AQUA).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component TIPS = Component.empty()
            .append(Component.text("Tips").color(ComponentUtil.AQUA).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component BACKPACKS = Component.empty()
            .append(Component.text("Backpacks").color(ComponentUtil.YELLOW).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component AUTO_SELL = Component.empty()
            .append(Component.text("Auto Sell").color(ComponentUtil.GREEN).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component CHALLENGES = Component.empty()
            .append(Component.text("Challenges").color(ComponentUtil.RED).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component MINES = Component.empty()
            .append(Component.text("Mines").color(ComponentUtil.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component CRATES = Component.empty()
            .append(Component.text("Crates").color(ComponentUtil.GOLD).decoration(TextDecoration.BOLD, true))
            .append(Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));

    public static final Component BROADCAST = Component.empty()
            .append(Component.text("[").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("Server Broadcast").color(NamedTextColor.LIGHT_PURPLE))
            .append(Component.text("] ").color(NamedTextColor.DARK_GRAY));

}
