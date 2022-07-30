package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MobileSupportCommand implements CommandExecutor, TabCompleter { //todo currently this command works but the stat doesnt do anything
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);
        playerData.setIsMobile(!playerData.getIsMobile());

        if (playerData.getIsMobile()) {
            player.sendMessage(ChatColor.GREEN + "You have ENABLED mobile support. This will disable a few features however it will allows mobile users to play without unintended issues. Not a mobile user? Retype the command to disable.");
        } else player.sendMessage(ChatColor.GREEN + "You have disabled mobile support.");
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
