package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageSpyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player);
        playerData.setIsWatchingMessages(!playerData.getIsWatchingMessages());
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Currently watching messages: " + playerData.getIsWatchingMessages());
        return false;
    }
}
