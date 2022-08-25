package net.staticstudios.prisons.admin.commands;

import net.staticstudios.prisons.utils.BroadcastMessage;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class BroadcastMessageCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/broadcast <message>"));
        }

        String message = String.join(" ", args);

        BroadcastMessage.send(message);
        Bukkit.getLogger().log(Level.INFO, message);
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.add("<message>");
        return list;
    }

}
