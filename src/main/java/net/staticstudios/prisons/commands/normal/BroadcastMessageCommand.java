package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.utils.BroadcastMessage;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
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
import java.util.logging.Level;

public class BroadcastMessageCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/broadcast <message>"));
                return false;
            }
        } else {
            if (args.length == 0) {
                Bukkit.getLogger().log(Level.INFO, PrisonUtils.Commands.getCorrectUsage("/broadcast <message>"));
                return false;
            }
        }
        StringBuilder msg = new StringBuilder();
        for (String arg : args) {
            msg.append(ChatColor.translateAlternateColorCodes('&', arg)).append(" ");
        }
        BroadcastMessage.send(msg.toString());
        Bukkit.getLogger().log(Level.INFO, msg.toString());
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.add("<message>");
        return list;
    }

}
