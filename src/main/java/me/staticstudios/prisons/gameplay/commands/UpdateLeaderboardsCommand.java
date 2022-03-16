package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.gameplay.leaderboards.LeaderboardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpdateLeaderboardsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        LeaderboardManager.updateAll();
        return false;
    }
}
