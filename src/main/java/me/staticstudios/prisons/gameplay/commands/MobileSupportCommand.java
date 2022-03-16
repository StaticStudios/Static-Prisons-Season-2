package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobileSupportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player);
        playerData.setIsMobile(!playerData.getIsMobile());

        if (playerData.getIsMobile()) {
            player.sendMessage(ChatColor.GREEN + "You have ENABLED mobile support. This will disable a few features however it will allows mobile users to play without unintended issues. Not a mobile user? Retype the command to disable.");
        } else player.sendMessage(ChatColor.GREEN + "You have disabled mobile support.");
        return false;
    }
}
