package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.enchants.CustomEnchants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SellCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        new PlayerData(player).sellBackpack(player, true);
        return false;
    }
}
