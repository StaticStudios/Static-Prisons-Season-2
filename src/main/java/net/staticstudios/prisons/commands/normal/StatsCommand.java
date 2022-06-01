package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.newGui.StatsMenus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) {
            StatsMenus.viewStats(player, player.getUniqueId());
            return true;
        }
        UUID uuid = ServerData.PLAYERS.getUUIDIgnoreCase(args[0]);
        if (uuid == null) {
            player.sendMessage(ChatColor.RED + "That player could not be found!");
        } else StatsMenus.viewStats(player, uuid);
        return false;
    }
}
