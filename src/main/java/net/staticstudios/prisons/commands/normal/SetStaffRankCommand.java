package net.staticstudios.prisons.commands.normal;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SetStaffRankCommand implements CommandExecutor, TabCompleter {

    private List<String> ranks = List.of("member", "helper", "moderator", "admin", "sradmin", "manager", "owner");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/setstaffrank <player> <rank>"));
            return false;
        }
        if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[0]));
        playerData.setStaffRank(args[1]);
        playerData.updateTabListPrefixID();

        sender.sendMessage(Component.text("Set staff rank of ")
                .append(Component.text(args[0]))
                .append(Component.text(" to ")).color(NamedTextColor.GREEN)
                .append(TeamPrefix.getFromIdDeserialized(args[1])));

        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> ranks = List.of("member", "helper", "moderator", "admin", "sradmin", "manager", "owner");
        if (args.length == 1) return StaticMineUtils.filterStringList(ServerData.PLAYERS.getAllNames(), args[0]);
        if (args.length == 2) return StaticMineUtils.filterStringList(ranks, args[1]);
        return Collections.emptyList();
    }
}
