package me.staticstudios.prisons.gameplay.commands.test;

import me.staticstudios.prisons.core.enchants.CustomEnchants;
import me.staticstudios.prisons.core.enchants.PrisonEnchants;
import me.staticstudios.prisons.external.DiscordLink;
import me.staticstudios.prisons.gameplay.islands.special.robots.BaseRobot;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.time.Instant;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        /*
        Location loc = player.getLocation().getBlock().getLocation();
        loc.add(0.5, 0, 0.5);
        BaseRobot.spawnRobot(loc, "money", Color.GREEN, ChatColor.GREEN + "Money Miner");

         */
        return false;
    }
}
