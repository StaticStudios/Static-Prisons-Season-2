package net.staticstudios.prisons.commands.normal;

import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.kyori.adventure.text.Component.text;

public class ResetRankCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 2) {
            sender.sendMessage(text("/resetrank <staff|player> <player>"));
            return false;
        }

        if (args[0].equalsIgnoreCase("staff")) {
            if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[1].toLowerCase())) {
                sender.sendMessage(text("Player not found!"));
                return false;
            }

            PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[1]));
            playerData.setStaffRank("member");
        }

        if (args[0].equalsIgnoreCase("player")) {
            if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[1].toLowerCase())) {
                sender.sendMessage(text("Player not found!"));
                return false;
            }

            PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[1]));
            playerData.setPlayerRank("member");
        }

        sender.sendMessage(text("Reset ").append(text(args[0])).append(text(" rank of ")).append(text(args[1])).color(NamedTextColor.GREEN));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("staff", "player");
        }

        return null;
    }
}
