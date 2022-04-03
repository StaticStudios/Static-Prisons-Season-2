package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.utils.Utils;
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
        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getMineBombTier1());
        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getMineBombTier2());
        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getMineBombTier3());
        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getMineBombTier4());
        return false;
    }
}
