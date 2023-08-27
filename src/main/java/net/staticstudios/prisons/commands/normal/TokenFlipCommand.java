package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gambling.GamblingMenus;
import net.staticstudios.prisons.gambling.TokenFlip;
import net.staticstudios.prisons.utils.CommandUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
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

public class TokenFlipCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {
            GamblingMenus.tokenFlip(player);
            return false;
        }
        if (args.length == 1) {
            player.sendMessage(CommandUtils.getCorrectUsage("/tokenflip <amount> <heads|tails>"));
            return false;
        }
        long amount;
        boolean isHeads;
        try {
            amount = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(CommandUtils.getCorrectUsage("/tokenflip <amount> <heads|tails>"));
            return false;
        }
        if (args[1].equalsIgnoreCase("heads")) {
            isHeads = true;
        } else if (args[1].equalsIgnoreCase("tails")) {
            isHeads = false;
        } else {
            player.sendMessage(CommandUtils.getCorrectUsage("/tokenflip <amount> <heads|tails>"));
            return false;
        }
        if (TokenFlip.checkIfThereAreTooManyActiveFlips()) {
            player.sendMessage(ChatColor.RED + "There are too many active TokenFlips for you to create a new one.");
            return false;
        }
        if (amount > new PlayerData(player).getTokens()) {
            player.sendMessage(ChatColor.RED + "You do not have enough tokens to create this bet!");
            return false;
        }
        if (amount < 1) {
            player.sendMessage(ChatColor.RED + "You cannot create a TokenFlip for less than 1 token!");
            return false;
        }
        TokenFlip.createTokenFlip(player, amount, isHeads);
        player.sendMessage(ChatColor.AQUA + "You have successfully created a TokenFlip for " + ChatColor.GREEN + PrisonUtils.prettyNum(amount) + " Tokens");

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.add("<amount>");
        if (args.length == 2) {
            list.add("heads");
            list.add("tails");
        }
        return list;
    }
}
