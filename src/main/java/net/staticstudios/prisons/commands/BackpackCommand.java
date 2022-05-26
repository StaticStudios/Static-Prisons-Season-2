package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.newGui.BackpackMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackpackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        BackpackMenus.upgradeBag(player);
        return false;
    }
}
