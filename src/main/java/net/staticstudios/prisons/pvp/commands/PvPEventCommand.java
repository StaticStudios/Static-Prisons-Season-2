package net.staticstudios.prisons.pvp.commands;

import net.kyori.adventure.text.Component;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PvPEventCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Prefix.PVP.append(Component.text("Usage: /pvpevent <start|stop> koth")));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> {
                if (args.length == 1) {
                    sender.sendMessage(Prefix.PVP.append(Component.text("Usage: /pvpevent <start|stop> koth")));
                    return true;
                }

                if ("koth".equalsIgnoreCase(args[1])) {
                    if (KingOfTheHillManager.isEventRunning()) {
                        sender.sendMessage(Prefix.PVP.append(Component.text("A PVP event is already running!")));
                        return false;
                    }

                    if (KingOfTheHillManager.getKothBlock() == null) {
                        sender.sendMessage(Prefix.PVP.append(Component.text("There is no King of the Hill area set!")));
                        return false;
                    }

                    KingOfTheHillManager.startEvent();
                    sender.sendMessage(Prefix.PVP.append(Component.text("Successfully started a PVP event!")));
                    Bukkit.broadcast(Prefix.PVP.append(Component.text("A PVP event has started!")));
                }
            }
            case "stop" -> {
                if (KingOfTheHillManager.isEventRunning()) {
                    KingOfTheHillManager.stopEvent(true);
                    sender.sendMessage(Prefix.PVP.append(Component.text("Successfully stopped the PVP event!")));
                } else {
                    sender.sendMessage(Prefix.PVP.append(Component.text("There is no PVP event running!")));
                }
            }
            default -> sender.sendMessage(Prefix.PVP.append(Component.text("Usage: /pvpevent <start|stop> koth")));
        }

        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            return StaticMineUtils.filterStrings(List.of("start", "stop"), args[0]);
        }

        if (args.length == 2 && "start".equalsIgnoreCase(args[0])) {
            return StaticMineUtils.filterStrings(List.of("koth"), args[1]);
        }

        return Collections.emptyList();
    }
}
