package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.utils.BroadcastMessage;
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
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/broadcast <message>"));
                return false;
            }
        } else {
            if (args.length == 0) {
                Bukkit.getLogger().log(Level.INFO, CommandUtils.getIncorrectCommandUsageMessage("/broadcast <message>"));
                return false;
            }
        }
        StringBuilder msg = new StringBuilder();
        for (String arg : args) {
            msg.append(ChatColor.translateAlternateColorCodes('&', arg)).append(" ");
        }
        BroadcastMessage.send(msg.toString());
        Bukkit.getLogger().log(Level.INFO, msg.toString());
        return false;
    }
}
