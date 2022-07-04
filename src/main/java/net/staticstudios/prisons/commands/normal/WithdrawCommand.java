package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithdrawCommand implements CommandExecutor, TabCompleter {

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
                if (playerData.getMoney().compareTo(amount.multiply(BigInteger.valueOf(stackCount))) > -1) {
                    BigInteger removed = BigInteger.ZERO;
                    ItemStack note = Vouchers.getMoneyNote(player.getName(), amount);

                    for (int i = 0; i < stackCount; i++) {
                        Map<Integer, ItemStack> result = player.getInventory().addItem(note);
                        if (!result.isEmpty()) {
                            result.clear();
                            break;
                        }
                        removed = removed.add(amount);
                    }
                    playerData.removeMoney(removed);
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
                if (playerData.getTokens().compareTo(amount.multiply(BigInteger.valueOf(stackCount))) > -1) {
                    BigInteger removed = BigInteger.ZERO;
                    ItemStack note = Vouchers.getTokenNote(player.getName(), amount);

                    for (int i = 0; i < stackCount; i++) {
                        Map<Integer, ItemStack> result = player.getInventory().addItem(note);
                        if (!result.isEmpty()) {
                            result.clear();
                            break;
                        }
                        removed = removed.add(amount);
                    }
                    playerData.removeTokens(removed);
                } else player.sendMessage(ChatColor.RED + "Insufficient Funds!");
            }
            default -> player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.addAll(StaticMineUtils.filterStringList(ServerData.PLAYERS.getAllNames(), args[0]));
        if (args.length == 2) {
            list.add("money");
            list.add("tokens");
        }
        if (args.length == 3) list.add("<amount>");
        if (args.length == 4) list.add("<stack count (optional)>");
        return list;
    }
}
