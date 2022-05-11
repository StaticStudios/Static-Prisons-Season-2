package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveVoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/givevote <who>"));
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "_ vote " + Bukkit.getPlayer(args[0]).getName());
        return true;
    }
}
