package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.chat.MessageHandler;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/reply <what>"));
            return false;
        }
        if (!MessageHandler.playerToRecentMessaged.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Could not find who to send the message to! Initiate a conversation with /msg");
            return false;
        }
        Player receiver = Bukkit.getPlayer(MessageHandler.playerToRecentMessaged.get(player.getUniqueId()));
        if (receiver == null) {
            player.sendMessage(ChatColor.RED + "Could not find who to send the message to! Initiate a conversation with /msg");
            return false;
        }
        StringBuilder message = new StringBuilder();
        for (String arg : args) message.append(arg).append(" ");
        MessageHandler.playerToRecentMessaged.put(player.getUniqueId(), receiver.getUniqueId());
        MessageHandler.playerToRecentMessaged.put(receiver.getUniqueId(), player.getUniqueId());
        MessageHandler.sendMessage(player, receiver, message.toString());
        return false;
    }
}