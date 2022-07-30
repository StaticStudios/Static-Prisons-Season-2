package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RemovePlayerChatTagCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/removechattag <player> <chattag>"));
            return false;
        }
        if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[0].toLowerCase())) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
            return false;
        }
        if (ChatTags.getFromID(args[1]).equals("")) {
            sender.sendMessage(ChatColor.RED + "A chat tag with that ID does not exist!");
            return false;
        }
        PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[0]));
        playerData.removeChatTag(args[1]);
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.addAll(StaticMineUtils.filterStringList(ServerData.PLAYERS.getAllNames(), args[0]));
        if (args.length == 2) list.addAll(StaticMineUtils.filterStringList(ChatTags.TAGS.keySet(), args[1]));
        return list;
    }
}
