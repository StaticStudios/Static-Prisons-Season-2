package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VotesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        player.sendMessage("You have voted " + PrisonUtils.addCommasToNumber(new PlayerData(player).getVotes()) + " time(s)");
        return false;
    }
}
