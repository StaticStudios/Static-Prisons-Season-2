package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MultiplierCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        player.sendMessage(ChatColor.AQUA + "Current money multiplier: " + ChatColor.WHITE + "x" + new PlayerData(player).getMoneyMultiplier());
        return true;
    }
}
