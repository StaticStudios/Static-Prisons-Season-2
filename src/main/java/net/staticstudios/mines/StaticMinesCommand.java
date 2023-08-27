package net.staticstudios.mines;

import net.staticstudios.mines.utils.StaticMineUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StaticMinesCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) return false;
        switch (args[0].toLowerCase()) {
            case "reload" -> {
                JavaPlugin parent = StaticMines.getParent();
                StaticMines.disable();
                StaticMines.enable(parent);
                sender.sendMessage(ChatColor.GREEN + "Successfully reloaded StaticMines");
            }
            case "refillall" -> {
                for (StaticMine mine : StaticMines.MINES.values()) {
                    mine.refill();
                }
                sender.sendMessage(ChatColor.GREEN + "Refilled all mines");
            }
            case "refill" -> {
                if (args.length == 1) {
                    sender.sendMessage("refill <mine ID>");
                    return false;
                }
                if (StaticMine.getMine(args[1]) == null) {
                    sender.sendMessage(ChatColor.RED + "Could not find the mine specified");
                    return false;
                }
                StaticMine.getMine(args[1]).refill();
                sender.sendMessage(ChatColor.GREEN + "Refilled " + ChatColor.ITALIC + args[1] + ChatColor.GREEN + " mine!");
            }
            default -> sender.sendMessage("Unknown sub-command");
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Collection<String> tab = new ArrayList<>();
        switch (args.length) {
            case 1 -> {
                tab.add("reload");
                tab.add("refillall");
                tab.add("refill");
            }
            case 2 -> {
                if ("refill".equalsIgnoreCase(args[0])) {
                    tab = new ArrayList<>(StaticMines.MINES.keySet());
                }
            }
        }
        return StaticMineUtils.filterStrings(tab, args[args.length - 1]);
    }
}
