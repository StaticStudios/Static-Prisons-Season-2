package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.newGui.MainMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GUICommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        MainMenus.open(player);
        return false;
    }
}
