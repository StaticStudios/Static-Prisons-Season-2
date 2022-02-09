package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EnderChestSeeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) return false;
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(ChatColor.RED + "This player is currently offline.");
            return false;
        }
        player.openInventory(Bukkit.getPlayer(args[0]).getEnderChest());
        return false;
    }
}
