package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
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

public class PayCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);
        if (args.length < 3 ) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/pay <who> <money/tokens> <amount>"));
            return false;
        }
        PlayerData whoToPay;
        Player p = null;
        String whoToPayName;
        if (Bukkit.getPlayer(args[0]) == null) {
            if (ServerData.PLAYERS.getAllNamesLowercase().contains(args[0].toLowerCase())) {
                whoToPay = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[0]));
                whoToPayName = args[0];
            } else {
                player.sendMessage(ChatColor.RED + "Could not find \"" + args[0] + "\", are they online?");
                return false;
            }
        } else {
            p = Bukkit.getPlayer(args[0]);
            whoToPayName = p.getName();
            whoToPay = new PlayerData(Bukkit.getPlayer(args[0]));
        }
        long amount;
        try {
            amount = Long.parseLong(args[2]);
        } catch (NumberFormatException ignore) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/pay <who> <money/tokens> <amount>"));
            return false;
        }
        if (amount < 1) {
            player.sendMessage(ChatColor.RED + "Amount must be more then 0!");
            return false;
        }
        switch (args[1].toLowerCase()) {
            case "money" -> {
                if (playerData.getMoney() > amount) {
                    playerData.removeMoney(amount);
                    whoToPay.addMoney(amount);
                    if (p != null) {
                        p.sendMessage(ChatColor.GREEN + "You have just received $" + PrisonUtils.addCommasToNumber(amount) + " from " + player.getName());
                    }
                    player.sendMessage(ChatColor.GREEN + "You have just sent $" + PrisonUtils.addCommasToNumber(amount) + " to " + whoToPayName);
                } else player.sendMessage(ChatColor.RED + "You do not have enough money for this!");
            }
            case "tokens" -> {
                if (playerData.getTokens() > amount) {
                    playerData.removeTokens(amount);
                    whoToPay.addTokens(amount);
                    if (p != null) {
                        p.sendMessage(ChatColor.GREEN + "You have just received " + PrisonUtils.addCommasToNumber(amount) + " tokens from " + player.getName());
                    }
                    player.sendMessage(ChatColor.GREEN + "You have just sent " + PrisonUtils.addCommasToNumber(amount) + " tokens to " + whoToPayName);
                } else player.sendMessage(ChatColor.RED + "You do not have enough money for this!");
            }
            default -> player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/pay <who> <money/tokens> <amount>"));
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
        return list;
    }
}
