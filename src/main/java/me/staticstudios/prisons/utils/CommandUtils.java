package me.staticstudios.prisons.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class CommandUtils {
    public static final String noPermissionsMessage = ChatColor.RED + "You do not have permission to use this command!";
    public static final String commandCannotBeUsedInConsole = ChatColor.AQUA + "This command cannot be run from the console!";
    public static String getIncorrectCommandUsageMessage(String correctUsage) {
        return ChatColor.BOLD + "" + ChatColor.RED + "Incorrect command usage!\n" + ChatColor.RESET + "" + ChatColor.GRAY + correctUsage;
    }
    public static void logConsoleCannotUseThisCommand() {
        Bukkit.getLogger().log(Level.INFO, commandCannotBeUsedInConsole);
    }
}
