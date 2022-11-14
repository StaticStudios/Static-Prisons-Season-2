package net.staticstudios.prisons.admin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.ComponentUtil;
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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

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

        String uuidString = uuid.toString();

        if (sender instanceof Player) {
            sender.sendMessage(Component
                    .text("Click to copy UUID of player ")
                    .append(Component.text(name).color(NamedTextColor.GOLD))
                    .color(NamedTextColor.RED)
                    .hoverEvent(HoverEvent.showText(Component.text(uuidString)))
                    .clickEvent(ClickEvent.copyToClipboard(uuidString))
            );
        } else {
            sender.sendMessage(ComponentUtil.BLANK
                    .append(Component.text(uuidString)
                            .color(ComponentUtil.RED)
                            .decorate(TextDecoration.UNDERLINED))
                    .hoverEvent(Component.text("Click to copy")
                            .color(ComponentUtil.GREEN))
                    .clickEvent(ClickEvent.copyToClipboard(uuidString)));
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return StaticMineUtils.filterStrings(ServerData.PLAYERS.getAllNames(), args[0]);
        }

        return Collections.emptyList();
    }
}
