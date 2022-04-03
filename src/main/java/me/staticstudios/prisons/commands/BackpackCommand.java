package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.gui.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackpackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        GUI.getGUIPage("upgradeBackpack").open(player);
        return false;
    }
}
