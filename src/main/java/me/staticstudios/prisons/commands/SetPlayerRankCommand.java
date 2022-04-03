package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetPlayerRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/setplayerrank <player> <rank>"));
            return false;
        }
        if (!new ServerData().getPlayerNamesToUUIDsMap().containsKey(args[0])) {
            System.out.println("\"" + args[0] + "\"");
            sender.sendMessage(ChatColor.RED + "Player not found, make sure their name is spelled and capitalized correctly!");
            return false;
        }
        PlayerData playerData = new PlayerData(new ServerData().getPlayerUUIDFromName(args[0]));
        playerData.setPlayerRank(args[1]);
        playerData.updateTabListPrefixID();
        return false;
    }
}
