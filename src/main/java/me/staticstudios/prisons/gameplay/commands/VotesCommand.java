package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VotesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        player.sendMessage("You have voted " + Utils.addCommasToNumber(new PlayerData(player).getVotes()) + " time(s)");
        return false;
    }
}
