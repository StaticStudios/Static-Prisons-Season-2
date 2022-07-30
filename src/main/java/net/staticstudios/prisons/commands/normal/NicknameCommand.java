package net.staticstudios.prisons.commands.normal;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NicknameCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);
        if (!playerData.getPlayerRanks().contains("warrior")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this!");
            return false;
        }
        if (args.length == 0) {
            playerData.setIsChatNickNameEnabled(false);
            player.sendMessage(ChatColor.GREEN + "Your nickname has been removed!");
            return false;
        }
        String newName = args[0];
        if (newName.equalsIgnoreCase("reset") || newName.equalsIgnoreCase("remove")) {
            playerData.setIsChatNickNameEnabled(false);
            player.sendMessage(ChatColor.GREEN + "Your nickname has been removed!");
            return false;
        }
        if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', newName)).length() > 16) {
            player.sendMessage(ChatColor.RED + "You cannot set a nickname longer than 16 characters!");
            return false;
        }
        playerData.setChatNickname(ChatColor.translateAlternateColorCodes('&', newName));
        playerData.setIsChatNickNameEnabled(true);
        player.sendMessage(ChatColor.GREEN + "Nickname successfully changed!");
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("reset");
            list.add("<nickname>");
        }
        return list;
    }
}
