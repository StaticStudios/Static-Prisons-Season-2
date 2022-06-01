package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        StaticPrisons.getInstance().loadConfig();
        sender.sendMessage("Successfully reloaded the plugin's config!");
        return false;
    }
}
