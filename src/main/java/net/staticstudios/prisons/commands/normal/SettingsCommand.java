package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.newGui.SettingsMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        SettingsMenus.open(player, true);
        return false;
    }
}
