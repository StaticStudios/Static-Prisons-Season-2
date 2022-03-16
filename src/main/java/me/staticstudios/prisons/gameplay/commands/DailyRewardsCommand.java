package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.gameplay.gui.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyRewardsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        GUI.getGUIPage("dailyRewards").open(player);
        return false;
    }
}
