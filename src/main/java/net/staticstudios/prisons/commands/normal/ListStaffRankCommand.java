package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
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

public class ListStaffRankCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) {
            sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/liststaffrank <rank>"));
            return false;
        }
        List<String> names = new ArrayList<>();
        for (UUID uuid : ServerData.PLAYERS.getAllUUIDs()) if (new PlayerData(uuid).getStaffRank().equals(args[0])) names.add(ServerData.PLAYERS.getName(uuid));
        sender.sendMessage("The following players have this rank: " + names);
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        List<String> ranks = List.of("member", "moderator", "admin", "sradmin", "manager", "owner");
        if (args.length == 1) list.addAll(StaticMineUtils.filterStringList(ranks, args[0]));
        return list;
    }
}
