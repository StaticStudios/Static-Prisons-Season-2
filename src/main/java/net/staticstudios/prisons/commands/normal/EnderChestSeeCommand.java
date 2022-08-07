package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.serverdata.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnderChestSeeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) return false;
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(ChatColor.RED + "This player is currently offline.");
            return false;
        }
        player.openInventory(Bukkit.getPlayer(args[0]).getEnderChest());
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.addAll(StaticMineUtils.filterStringList(ServerData.PLAYERS.getAllNames(), args[0]));
        return list;
    }
}
