package net.staticstudios.prisons.chat.nicknames;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class NicknameCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);
        if (!playerData.getPlayerRanks().contains("warrior") || "".equals(playerData.getStaffRank())) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this!");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Component.text("Usage: /nickname <new name|reset> <color>").color(NamedTextColor.AQUA));
            return false;
        }

        String newName = args[0];

        if (newName.equalsIgnoreCase("reset")) {
            playerData.useNickname(false);
            player.sendMessage(Component.text("Your nickname has been reset!").color(NamedTextColor.GREEN));
            return false;
        }

        if (newName.length() > 16) {
            player.sendMessage(Component.text("You cannot set a nickname longer than 16 characters!").color(NamedTextColor.RED));
            return false;
        }

        TextColor color = NamedTextColor.WHITE;

        if (args.length == 2) {
            if (NickColors.getKeySet().contains(args[1].toLowerCase())) {
                color = NickColors.getColor(args[1].toLowerCase());
            } else {
                player.sendMessage(Component.text("Invalid color!").color(NamedTextColor.RED));
                return false;
            }
        }

        playerData.setNickname(StaticPrisons.miniMessage().serialize(Component.text(newName).color(color)));
        playerData.useNickname(true);
        player.sendMessage(Component.text("Nickname successfully changed!").color(NamedTextColor.GREEN));
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return StaticMineUtils.filterStrings(List.of("reset", "<nickname>"), args[0]);
        }

        if (args.length == 2) {
            return StaticMineUtils.filterStrings(NickColors.getKeySet(), args[1]);
        }

        return Collections.emptyList();
    }
}
