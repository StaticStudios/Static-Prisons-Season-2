package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.gameplay.Warps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Warps.warpToSpawn(player);
        return false;
    }
}
