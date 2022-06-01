package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.chat.MessageHandler;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/msg <who> <what>"));
            return false;
        }
        Player receiver = Bukkit.getPlayer(args[0]);
        if (receiver == null) {
            player.sendMessage(ChatColor.RED + "Could not find the player specified!");
            return false;
        }
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) message.append(args[i]).append(" ");
        MessageHandler.playerToRecentMessaged.put(player.getUniqueId(), receiver.getUniqueId());
        MessageHandler.playerToRecentMessaged.put(receiver.getUniqueId(), player.getUniqueId());
        MessageHandler.sendMessage(player, receiver, message.toString());
        return false;
    }
}
