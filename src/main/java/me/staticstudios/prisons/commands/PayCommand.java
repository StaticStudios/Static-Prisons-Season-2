package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.utils.CommandUtils;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class PayCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player);
        if (args.length < 3 ) {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/pay <who> <money/tokens> <amount>"));
            return false;
        }
        PlayerData whoToPay;
        Player p = null;
        String whoToPayName;
        if (Bukkit.getPlayer(args[0]) == null) {
            if (new ServerData().getPlayerNamesToUUIDsMap().containsKey(args[0])) {
                whoToPay = new PlayerData(new ServerData().getPlayerUUIDFromName(args[0]));
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
        BigInteger amount;
        try {
            amount = new BigInteger(args[2]);
        } catch (NumberFormatException ignore) {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/pay <who> <money/tokens> <amount>"));
            return false;
        }
        if (amount.compareTo(BigInteger.ZERO) < 1) {
            player.sendMessage(ChatColor.RED + "Amount must be more then 0!");
            return false;
        }
        switch (args[1].toLowerCase()) {
            case "money" -> {
                if (playerData.getMoney().compareTo(amount) > -1) {
                    playerData.removeMoney(amount);
                    whoToPay.addMoney(amount);
                    if (p != null) {
                        p.sendMessage(ChatColor.GREEN + "You have just received $" + Utils.addCommasToNumber(amount) + " from " + player.getName());
                    }
                    player.sendMessage(ChatColor.GREEN + "You have just sent $" + Utils.addCommasToNumber(amount) + " to " + whoToPayName);
                } else player.sendMessage(ChatColor.RED + "You do not have enough money for this!");
            }
            case "tokens" -> {
                if (playerData.getTokens().compareTo(amount) > -1) {
                    playerData.removeTokens(amount);
                    whoToPay.addTokens(amount);
                    if (p != null) {
                        p.sendMessage(ChatColor.GREEN + "You have just received " + Utils.addCommasToNumber(amount) + " tokens from " + player.getName());
                    }
                    player.sendMessage(ChatColor.GREEN + "You have just sent " + Utils.addCommasToNumber(amount) + " tokens to " + whoToPayName);
                } else player.sendMessage(ChatColor.RED + "You do not have enough money for this!");
            }
            default -> player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/pay <who> <money/tokens> <amount>"));
        }
        return true;
    }
}
