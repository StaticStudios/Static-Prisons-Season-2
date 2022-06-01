package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddPlayerChatTagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/addchattag <player> <chat tag>"));
            return false;
        }
        if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        if (ChatTags.getChatTagFromID(args[1]).equals("")) {
            sender.sendMessage(ChatColor.RED + "A chat tag with that ID does not exist!");
            return false;
        }
        PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[0]));
        playerData.addChatTag(args[1]);
        return false;
    }
}
