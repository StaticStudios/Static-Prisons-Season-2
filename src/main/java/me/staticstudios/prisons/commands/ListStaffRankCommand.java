package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListStaffRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) {
            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/liststaffrank <rank>"));
            return false;
        }
        List<String> names = new ArrayList<>();
        for (String uuid : new ServerData().getPlayerUUIDsToNamesMap().keySet()) {
            if (new PlayerData(uuid).getStaffRank().equals(args[0])) names.add(new ServerData().getPlayerNameFromUUID(uuid));
        }
        sender.sendMessage("The following players have this rank: " + names);
        return false;
    }
}
