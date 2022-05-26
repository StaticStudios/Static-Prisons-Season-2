package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.gui.newGui.DailyRewardMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyRewardsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        DailyRewardMenus.mainMenu(player);
        return false;
    }
}
