package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.chat.ChatTags;
import me.staticstudios.prisons.newData.dataHandling.serverData.ServerData;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddAllPlayerChatTagsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/addallchattags <player>"));
            return false;
        }
        if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[0]));
        for (String key : ChatTags.chatTags.keySet()) {
            playerData.addChatTag(key);
        }
        return false;
    }
}
