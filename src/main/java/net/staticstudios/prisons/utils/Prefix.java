package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import static net.kyori.adventure.text.format.TextDecoration.BOLD;

public class Prefix {

    public static final Component PVP = Component.textOfChildren(
                    Component.text("PVP").color(ComponentUtil.RED).decoration(TextDecoration.BOLD, true),
                    Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
            );

    //koth - dark red
    public static final Component KOTH = Component.textOfChildren(
            Component.text("KOTH").color(ComponentUtil.DARK_RED).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //prestige - light purple
    public static final Component PRESTIGE = Component.textOfChildren(
            Component.text("Prestige").color(ComponentUtil.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //outpost - dark green

    public static final Component OUTPOST = Component.textOfChildren(
            Component.text("Outpost").color(ComponentUtil.DARK_GREEN).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //vanish - green
    public static final Component VANISH = Component.textOfChildren(
            Component.text("Vanish").color(ComponentUtil.GREEN).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //loot box - aqua
    public static final Component LOOT_BOX = Component.textOfChildren(
            Component.text("Loot Box").color(ComponentUtil.AQUA).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //Backpacks - yellow
    public static final Component BACKPACKS = Component.textOfChildren(
            Component.text("Backpacks").color(ComponentUtil.YELLOW).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //tips - aqua
    public static final Component TIPS = Component.textOfChildren(
            Component.text("Tips").color(ComponentUtil.AQUA).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //Auto Sell - green
    public static final Component AUTO_SELL = Component.textOfChildren(
            Component.text("Auto Sell").color(ComponentUtil.GREEN).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //Challanges - red
    public static final Component CHALLENGES = Component.textOfChildren(
            Component.text("CHALLENGES").color(ComponentUtil.RED).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //mines - light purple
    public static final Component MINES = Component.textOfChildren(
            Component.text("Mines").color(ComponentUtil.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //Crates - Gold

    public static final Component CRATES = Component.textOfChildren(
            Component.text("Crates").color(ComponentUtil.GOLD).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    //Trading - gold

    public static final Component TRADING = Component.textOfChildren(
            Component.text("Trading").color(ComponentUtil.GOLD).decoration(TextDecoration.BOLD, true),
            Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
    );

    public static final Component BROADCAST = Component.textOfChildren(
            Component.text("[").color(NamedTextColor.DARK_GRAY),
            Component.text("Server Broadcast").color(NamedTextColor.LIGHT_PURPLE),
            Component.text("] ").color(NamedTextColor.DARK_GRAY)
    );

    public static final Component FISHING = Component.textOfChildren(
                    Component.text("Fishing").color(ComponentUtil.YELLOW).decoration(TextDecoration.BOLD, true),
                    Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
            );

    public static final Component RANKS = Component.textOfChildren(
                    Component.text("Ranks").color(ComponentUtil.RED).decoration(TextDecoration.BOLD, true),
                    Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
            );

    public static final Component STATIC_PRISONS = Component.textOfChildren(
            Component.text("Static Prisons").color(ComponentUtil.LIGHT_PURPLE).decoration(TextDecoration.BOLD, true),
            Component.text(" >> " ).color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true)
            );

    public static final Component STAFF_CHAT = Component.textOfChildren(
            Component.text("[").color(NamedTextColor.DARK_GRAY),
            Component.text("STAFF CHAT").color(NamedTextColor.RED).decorate(BOLD),
            Component.text("] ").color(NamedTextColor.DARK_GRAY)
    );
}
