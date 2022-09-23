package net.staticstudios.prisons.admin.commands;

import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.CommandUtils;
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
import java.util.Objects;

public class GiveVoteCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) {
            player.sendMessage(CommandUtils.getCorrectUsage("/givevote <who>"));
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "_ vote " + Objects.requireNonNull(Bukkit.getPlayer(args[0])).getName());
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.addAll(StaticMineUtils.filterStrings(ServerData.PLAYERS.getAllNames(), args[0]));
        return list;
    }
}
