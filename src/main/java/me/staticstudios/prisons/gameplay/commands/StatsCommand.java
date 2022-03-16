package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.gameplay.gui.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) {
            GUI.getGUIPage("stats").args = player.getName();
        } else GUI.getGUIPage("stats").args = args[0];
        GUI.getGUIPage("stats").open(player);
        return false;
    }
}
