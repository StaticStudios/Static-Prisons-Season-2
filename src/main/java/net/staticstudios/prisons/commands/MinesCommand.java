package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.newGui.WarpMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        WarpMenus.minesMenuAZ(player);
        return false;
    }
}
