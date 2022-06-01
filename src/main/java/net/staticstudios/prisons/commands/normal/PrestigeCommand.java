package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.gui.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrestigeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        GUI.getGUIPage("prestige").open((Player) sender);
        return false;
    }
}
