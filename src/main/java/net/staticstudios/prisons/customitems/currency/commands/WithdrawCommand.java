package net.staticstudios.prisons.customitems.currency.commands;

import net.staticstudios.prisons.customitems.currency.MoneyNote;
import net.staticstudios.prisons.customitems.currency.TokenNote;
import net.staticstudios.prisons.utils.CommandUtils;
import net.staticstudios.prisons.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WithdrawCommand implements TabExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length < 2) {
            player.sendMessage(CommandUtils.getCorrectUsage("/withdraw <money/tokens> <amount>"));
            return true;
        }

        long amount;

        try {
            amount = Long.parseLong(args[1]);
        } catch (NumberFormatException err) {
            player.sendMessage(CommandUtils.getCorrectUsage("/withdraw <money/tokens> <amount>"));
            return false;
        }

        if (amount < 1) {
            player.sendMessage("Amount must be greater than 0");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "money" ->
                    PlayerUtils.addToInventory(player, MoneyNote.INSTANCE.getItem(player, new String[]{String.valueOf(amount)}));
            case "tokens" ->
                    PlayerUtils.addToInventory(player, TokenNote.INSTANCE.getItem(player, new String[]{String.valueOf(amount)}));
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