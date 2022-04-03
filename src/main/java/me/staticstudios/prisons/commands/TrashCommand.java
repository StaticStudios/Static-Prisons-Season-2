package me.staticstudios.prisons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        player.openInventory(Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "Throw away items. " + ChatColor.RED + "PERMANENTLY!"));
        return false;
    }
}
