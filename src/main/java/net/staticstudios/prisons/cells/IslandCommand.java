package net.staticstudios.prisons.cells;

import net.staticstudios.prisons.cells.Cell;
import net.staticstudios.prisons.cells.CellMenus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandCommand implements CommandExecutor {
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
}
