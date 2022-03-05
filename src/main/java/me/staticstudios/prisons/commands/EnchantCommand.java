package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.misc.BroadcastMessage;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class EnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        GUI.getGUIPage("enchantsSelectPickaxe").open(player);
        return false;
    }
}
