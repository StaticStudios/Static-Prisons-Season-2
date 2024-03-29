package net.staticstudios.prisons.admin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SetPlayerRankCommand extends SetRankCommand implements CommandExecutor, TabCompleter {

    private final List<String> ranks = List.of("member", "warrior", "master", "mythic", "static", "staticp");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(CommandUtils.getCorrectUsage("/setplayerrank <player> <rank>"));
            return false;
        }

        if (!validateArgs(sender, args)) {
            return false;
        }

        PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[0]));
        playerData.setPlayerRank(args[1]);

        sender.sendMessage(Component.text("Set player rank of ")
                .append(Component.text(args[0]))
                .append(Component.text(" to ")).color(NamedTextColor.GREEN)
                .append(TeamPrefix.getFromIdDeserialized(args[1])));

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return StaticMineUtils.filterStrings(ServerData.PLAYERS.getAllNames(), args[0]);
        if (args.length == 2) return StaticMineUtils.filterStrings(ranks, args[1]);
        return Collections.emptyList();
    }
}
