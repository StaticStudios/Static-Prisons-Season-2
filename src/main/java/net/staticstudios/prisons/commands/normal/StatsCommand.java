package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.gui.StatsMenus;
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
import java.util.UUID;

public class StatsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) {
            StatsMenus.viewStats(player, player.getUniqueId());
            return true;
        }
        UUID uuid = ServerData.PLAYERS.getUUIDIgnoreCase(args[0]);
        if (uuid == null) {
            player.sendMessage(ChatColor.RED + "That player could not be found!");
        } else StatsMenus.viewStats(player, uuid);
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.addAll(StaticMineUtils.filterStringList(ServerData.PLAYERS.getAllNames(), args[0]));
        return list;
    }
}
