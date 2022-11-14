package net.staticstudios.prisons.admin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UuidCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("Please provide a player name. /uuid <player>");
            return true;
        }

        String name = args[0];

        UUID uuid = ServerData.PLAYERS.getUUIDIgnoreCase(name);

        if (uuid == null) {
            sender.sendMessage("Player not found!");
            return true;
        }

        sender.sendMessage(ComponentUtil.BLANK
                .append(Component.text(uuid.toString())
                        .color(ComponentUtil.RED)
                        .decorate(TextDecoration.UNDERLINED))
                .hoverEvent(Component.text("Click to copy")
                        .color(ComponentUtil.GREEN))
                .clickEvent(ClickEvent.copyToClipboard(uuid.toString())));

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length != 1) {
            return Collections.emptyList();
        }

        return StaticMineUtils.filterStrings(ServerData.PLAYERS.getAllNames(), args[0]);
    }
}
