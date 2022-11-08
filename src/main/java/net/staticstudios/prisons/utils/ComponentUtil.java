package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;

public class ComponentUtil {

    public static final Component BLANK = Component.empty()
            .decoration(TextDecoration.ITALIC, false);

    public static final TextColor DARK_RED = TextColor.color(170, 0, 0);
    public static final TextColor RED = TextColor.color(255, 85, 85);
    public static final TextColor GOLD = TextColor.color(255, 170, 0);
    public static final TextColor YELLOW = TextColor.color(255, 255, 85);
    public static final TextColor DARK_GREEN = TextColor.color(0, 170, 0);
    public static final TextColor GREEN = TextColor.color(85, 255, 85);
    public static final TextColor AQUA = TextColor.color(85, 255, 255);
    public static final TextColor DARK_AQUA = TextColor.color(0, 170, 170);
    public static final TextColor DARK_BLUE = TextColor.color(0, 0, 170);
    public static final TextColor BLUE = TextColor.color(85, 85, 255);
    public static final TextColor LIGHT_PURPLE = TextColor.color(255, 85, 255);
    public static final TextColor PURPLE = TextColor.color(170, 0, 170);
    public static final TextColor WHITE = TextColor.color(255, 255, 255);
    public static final TextColor GRAY = TextColor.color(170, 170, 170);
    public static final TextColor DARK_GRAY = TextColor.color(85, 85, 85);
    public static final TextColor BLACK = TextColor.color(0, 0, 0);
    public static final TextColor ORANGE = TextColor.color(255, 127, 0);
    public static final TextColor VIOLET = TextColor.color(127, 0, 255);
    public static final TextColor LIGHT_GRAY = TextColor.color(200, 200, 200);
    public static final TextColor PINK = TextColor.color(205, 127, 148);
    public static final TextColor BROWN = TextColor.color(106, 58, 40);
    public static final TextColor CRIMSON = TextColor.color(150, 15, 41);
    public static final TextColor DARK_ORANGE = TextColor.color(214, 141, 2);
    public static final TextColor COAL = TextColor.color(35, 25, 21);
    public static final TextColor SILVER = TextColor.color(192, 192, 192);
    public static final TextColor BRONZE = TextColor.color(205, 127, 50);

    public static String toString(Component component) {
        return StaticPrisons.miniMessage().serialize(component);
    }

    public static Component fromString(String string) {
        return StaticPrisons.miniMessage().deserialize(string);
    }

}