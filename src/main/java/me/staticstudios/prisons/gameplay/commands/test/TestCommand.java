package me.staticstudios.prisons.gameplay.commands.test;

import me.staticstudios.prisons.external.DiscordLink;
import me.staticstudios.prisons.gameplay.islands.special.robots.BaseRobot;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //if (!(commandSender instanceof Player)) return false;
        //Player player = (Player) commandSender;
        /*
        Location loc = player.getLocation().getBlock().getLocation();
        loc.add(0.5, 0, 0.5);
        BaseRobot.spawnRobot(loc, "money", Color.GREEN, ChatColor.GREEN + "Money Miner");

         */


        return false;
    }
}
