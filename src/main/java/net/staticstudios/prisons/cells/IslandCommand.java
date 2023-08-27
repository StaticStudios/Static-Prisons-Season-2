package net.staticstudios.prisons.cells;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IslandCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            CellMenus.openMenu(player, true);
            return false;
        }
        switch (args[0].toLowerCase()) {
            default -> CellMenus.openMenu(player, true);
            case "go" -> {
                Cell cell = Cell.getCell(player);
                if (cell == null) {
                    player.sendMessage(ChatColor.RED + "You do not have a cell! Create one with " + ChatColor.GRAY + ChatColor.ITALIC + "/cell create");
                    return false;
                }
                cell.warpTo(player);
            }
            case "create" -> {
                Cell cell = Cell.getCell(player);
                if (cell != null) {
                    player.sendMessage(ChatColor.RED + "You already have a cell! Warp to it with " + ChatColor.GRAY + ChatColor.ITALIC + "/cell go");
                    return false;
                }
                Cell.createCell(player);
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("go");
            list.add("create");
            list.add("shop");
        }
        return list;
    }
}
