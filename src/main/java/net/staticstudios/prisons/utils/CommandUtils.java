package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class CommandUtils {
    public static final Component noPermissionsMessage = Component.text("You do not have permission to use this command!").color(RED);
    public static final Component commandCannotBeUsedInConsole = text("This command cannot be run from the console!").color(AQUA);

    public static Component getCorrectUsage(String correctUsage) {
        return text("Incorrect command usage! ").color(RED).decorate(TextDecoration.BOLD).append(text(correctUsage).color(GRAY).decoration(TextDecoration.BOLD, false));
    }

    public static void logConsoleCannotUseThisCommand() {
        Bukkit.getServer().getConsoleSender().sendMessage(commandCannotBeUsedInConsole);
    }
}
