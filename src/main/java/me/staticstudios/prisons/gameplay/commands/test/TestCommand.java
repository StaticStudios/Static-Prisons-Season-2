package me.staticstudios.prisons.gameplay.commands.test;

import me.staticstudios.prisons.core.mines.MineManager;
import me.staticstudios.prisons.gameplay.mineBombs.MineBomb;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
        System.out.println(new MineBomb(player.getLocation(), 20).explode(MineManager.allMines.get(MineManager.getMineIDFromLocation(player.getLocation()))).get(Material.STONE));
        return false;
    }
}
