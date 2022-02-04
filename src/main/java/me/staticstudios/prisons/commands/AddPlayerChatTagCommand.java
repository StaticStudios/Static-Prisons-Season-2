package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.misc.chat.ChatTags;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddPlayerChatTagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/addchattag <player> <chat tag>"));
            return false;
        }
        if (!new ServerData().getPlayerNamesToUUIDsMap().containsKey(args[0])) {
            System.out.println("\"" + args[0] + "\"");
            sender.sendMessage(ChatColor.RED + "Player not found, make sure their name is spelled and capitalized correctly!");
            return false;
        }
        if (ChatTags.getChatTagFromID(args[1]).equals("")) {
            sender.sendMessage(ChatColor.RED + "A chat tag with that ID does not exist!");
            return false;
        }
        PlayerData playerData = new PlayerData(new ServerData().getPlayerUUIDFromName(args[0]));
        playerData.addChatTag(args[1]);
        return false;
    }
}
