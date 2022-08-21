package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.customitems.Vouchers;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithdrawCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        PlayerData playerData = new PlayerData(player);
        if (args.length < 2) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "money" -> {
                long amount;
                int stackCount = 1;
                try {
                    amount = Long.parseLong(args[1]);
                } catch (NumberFormatException err) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
                    return false;
                }
                if (amount < 1) {
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
                if (playerData.getMoney() >= amount * stackCount) {
                    long removed = 0;
                    ItemStack note = Vouchers.getMoneyNote(player.getName(), amount);

                    for (int i = 0; i < stackCount; i++) {
                        Map<Integer, ItemStack> result = player.getInventory().addItem(note);
                        if (!result.isEmpty()) {
                            result.clear();
                            break;
                        }
                        removed += amount;
                    }
                    playerData.removeMoney(removed);
                } else player.sendMessage(ChatColor.RED + "Insufficient Funds!");
            }
            case "tokens" -> {
                long amount;
                int stackCount = 1;
                try {
                    amount = Long.parseLong(args[1]);
                } catch (NumberFormatException err) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/withdraw <money/tokens> <amount> <stack count (optional)>"));
                    return false;
                }
                if (amount < 1) {
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
                if (playerData.getTokens() >= amount * stackCount) {
                    long removed = 0;
                    ItemStack note = Vouchers.getTokenNote(player.getName(), amount);

                    for (int i = 0; i < stackCount; i++) {
                        Map<Integer, ItemStack> result = player.getInventory().addItem(note);
                        if (!result.isEmpty()) {
                            result.clear();
                            break;
                        }
                        removed += amount;
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
        if (args.length == 1) list.addAll(StaticMineUtils.filterStrings(ServerData.PLAYERS.getAllNames(), args[0]));
        if (args.length == 2) {
            list.add("money");
            list.add("tokens");
        }
        if (args.length == 3) list.add("<amount>");
        if (args.length == 4) list.add("<stack count (optional)>");
        return list;
    }
}
