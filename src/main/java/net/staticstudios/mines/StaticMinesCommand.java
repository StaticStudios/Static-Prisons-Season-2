package net.staticstudios.mines;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StaticMinesCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        switch (args[0].toLowerCase()) {
            case "reload" -> {
                StaticMines.getInstance().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the plugin's config (config.yml)");
            }
            case "load" -> {
                StaticMine.loadMines();
                sender.sendMessage(ChatColor.GREEN + "Successfully reloaded all mines (mines.yml)");
            }
            case "save" -> {
                StaticMine.saveMinesSync();
                sender.sendMessage(ChatColor.GREEN + "Successfully saved all mines (mines.yml)");
            }
            case "refillall" -> {
                for (StaticMine mine : StaticMine.getAllMines()) mine.refill();
                sender.sendMessage(ChatColor.GREEN + "Refilled all mines!");
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
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tab = new ArrayList<>();
        switch (args.length) {
            case 1 -> {
                tab.add("reload");
                tab.add("load");
                tab.add("save");
                tab.add("refillall");
                tab.add("refill");
            }
            case 2 -> {
                switch (args[0].toLowerCase()) {
                    case "refill" -> tab = new ArrayList<>(StaticMine.getAllMineIDS());
                }
            }
        }
        return StaticMineUtils.filterStringList(tab, args[args.length - 1]);
    }
}
