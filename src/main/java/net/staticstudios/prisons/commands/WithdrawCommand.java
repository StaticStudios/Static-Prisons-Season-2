package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class WithdrawCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player);
        if (args.length < 2) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "money" -> {
                BigInteger amount;
                int stackCount = 1;
                try {
                    amount = new BigInteger(args[1]);
                } catch (NumberFormatException err) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
                    return false;
                }
                if (amount.compareTo(BigInteger.ZERO) < 1) {
                    player.sendMessage(ChatColor.RED + "Amount must be greater than 0");
                    return false;
                }
                if (args.length > 2) {
                    try {
                        stackCount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException err) {
                        player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
                        return false;
                    }
                }
                if (stackCount > 1000) stackCount = 1000;
                if (playerData.getMoney().compareTo(amount.multiply(BigInteger.valueOf(stackCount))) > -1) {
                    playerData.removeMoney(amount.multiply(BigInteger.valueOf(stackCount)));
                    ItemStack note = Vouchers.getMoneyNote(player.getName(), amount);
                    for (int i = 0; i < stackCount; i++) {
                        player.getInventory().addItem(note);
                    }
                } else player.sendMessage(ChatColor.RED + "Insufficient Funds!");
            }
            case "tokens" -> {
                BigInteger amount;
                int stackCount = 1;
                try {
                    amount = new BigInteger(args[1]);
                } catch (NumberFormatException err) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
                    return false;
                }
                if (amount.compareTo(BigInteger.ZERO) < 1) {
                    player.sendMessage(ChatColor.RED + "Amount must be greater than 0");
                    return false;
                }
                if (args.length > 2) {
                    try {
                        stackCount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException err) {
                        player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
                        return false;
                    }
                }
                if (stackCount > 1000) stackCount = 1000;
                if (playerData.getTokens().compareTo(amount.multiply(BigInteger.valueOf(stackCount))) > -1) {
                    playerData.removeTokens(amount.multiply(BigInteger.valueOf(stackCount)));
                    ItemStack note = Vouchers.getTokenNote(player.getName(), amount);
                    for (int i = 0; i < stackCount; i++) {
                        player.getInventory().addItem(note);
                    }
                } else player.sendMessage(ChatColor.RED + "Insufficient Funds!");
            }
            default -> player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
        }
        return true;
    }
}
