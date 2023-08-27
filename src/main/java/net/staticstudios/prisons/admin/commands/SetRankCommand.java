package net.staticstudios.prisons.admin.commands;

import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class SetRankCommand {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validateArgs(CommandSender sender, String[] args) {
        if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }

        String prefix = TeamPrefix.getFromId(args[1]);

        if (Objects.equals(prefix, "")) {
            sender.sendMessage(ChatColor.RED + "Rank does not exist!");
            return false;
        }

        return true;
    }
}
