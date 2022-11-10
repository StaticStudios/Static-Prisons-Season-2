package net.staticstudios.prisons.customitems.currency;

import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.CommandUtils;
import net.staticstudios.prisons.utils.PlayerUtils;
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

public class WithdrawCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        PlayerData playerData = new PlayerData(player);
        if (args.length < 2) {
            player.sendMessage(CommandUtils.getCorrectUsage("/withdraw <money/tokens> <amount>"));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "money" -> {
                long amount;
                try {
                    amount = Long.parseLong(args[1]);
                } catch (NumberFormatException err) {
                    player.sendMessage(CommandUtils.getCorrectUsage("/withdraw <money/tokens> <amount>"));
                    return false;
                }
                if (amount < 1) {
                    player.sendMessage(ChatColor.RED + "Amount must be greater than 0");
                    return false;
                }
                PlayerUtils.addToInventory(player, MoneyNote.INSTANCE.getItem(player, new String[]{String.valueOf(amount)}));
            }
            case "tokens" -> {
                long amount;
                try {
                    amount = Long.parseLong(args[1]);
                } catch (NumberFormatException err) {
                    player.sendMessage(CommandUtils.getCorrectUsage("/withdraw <money/tokens> <amount>"));
                    return false;
                }
                if (amount < 1) {
                    player.sendMessage(ChatColor.RED + "Amount must be greater than 0");
                    return false;
                }
                if (playerData.getTokens() < amount) {
                    player.sendMessage(ChatColor.RED + "You don't have enough tokens to withdraw that amount!");
                    return false;
                }
                playerData.removeTokens(amount);
                PlayerUtils.addToInventory(player, TokenNote.INSTANCE.getItem(player, new String[]{String.valueOf(amount)}));
            }
            default -> player.sendMessage(CommandUtils.getCorrectUsage("/withdraw <money/tokens> <amount>"));
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("money");
            list.add("tokens");
        }
        if (args.length == 2) {
            list.add("<amount>");
        }
        return list;
    }
}
