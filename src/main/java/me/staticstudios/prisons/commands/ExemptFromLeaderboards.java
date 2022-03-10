package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.misc.BroadcastMessage;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class BroadcastMessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) reruen false;
            Player player = (Player) sender;
            PlayerData playerData = new PlayerData(player);
            playerData.setIsExemptFromLeaderboards(!playerData.getIsExemptFromLeaderboards());
            player.sendMessage(ChatColor.GREEN + "Stats will display on leaderboards: " + playerData.getIsExemptFromLeaderboards());
        return false;
    }
}
