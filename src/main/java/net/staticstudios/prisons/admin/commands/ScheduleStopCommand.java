package net.staticstudios.prisons.admin.commands;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.BroadcastMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ScheduleStopCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        BroadcastMessage.send("Automatically stopping the server in 15 minute(s)");
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 10 minute(s)"), 20 * 60 * 5);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 5 minute(s)"), 20 * 60 * 10);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 1 minute(s)"), 20 * 60 * 14);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 30 second(s)"), 20 * (60 * 14 + 30));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 15 second(s)"), 20 * (60 * 14 + 45));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 10 second(s)"), 20 * (60 * 14 + 50));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 5 second(s)"), 20 * (60 * 14 + 55));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 4 second(s)"), 20 * (60 * 14 + 56));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 3 second(s)"), 20 * (60 * 14 + 57));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 2 second(s)"), 20 * (60 * 14 + 58));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> BroadcastMessage.send("Automatically stopping the server in 1 second(s)"), 20 * (60 * 14 + 59));
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            BroadcastMessage.send("Automatically stopping the server");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
        }, 20 * 60 * 15);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}