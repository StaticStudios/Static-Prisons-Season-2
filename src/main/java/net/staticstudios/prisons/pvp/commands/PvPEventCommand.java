package net.staticstudios.prisons.pvp.commands;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PvPEventCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Prefix.PVP.append(Component.text("Usage: /pvpevent start koth")));
            return true;
        }

        if ("start".equalsIgnoreCase(args[0])) {
            if ("koth".equalsIgnoreCase(args[1])) {
                if (KingOfTheHillManager.isEventRunning()) {
                    sender.sendMessage(Prefix.PVP.append(Component.text("A PVP event is already running!")));
                    return false;
                }

                KingOfTheHillManager.startEvent();
                sender.sendMessage(Prefix.PVP.append(Component.text("Successfully started a PVP event!")));
                Bukkit.broadcast(Prefix.PVP.append(Component.text("A PVP event has started!")));
            }
        }
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
