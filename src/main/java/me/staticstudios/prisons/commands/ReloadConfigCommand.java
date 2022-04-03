package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.chat.ChatTags;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Main.getMain().loadConfig();
        sender.sendMessage("Successfully reloaded the plugin's config!");
        return false;
    }
}
