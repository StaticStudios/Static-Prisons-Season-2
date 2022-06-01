package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.gambling.CoinFlip;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class CoinFlipCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            GUI.getGUIPage("cf").open(player);
            return false;
        } else if (args.length == 1) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/coinflip <amount> <heads|tails>"));
            return false;
        }
        BigInteger amount;
        boolean isHeads;
        try {
            amount = new BigInteger(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/coinflip <amount> <heads|tails>"));
            return false;
        }
        if (args[1].equalsIgnoreCase("heads")) {
            isHeads = true;
        } else if (args[1].equalsIgnoreCase("tails")) {
            isHeads = false;
        } else {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/coinflip <amount> <heads|tails>"));
            return false;
        }
        if (CoinFlip.checkIfThereAreTooManyActiveFlips()) {
            player.sendMessage(ChatColor.RED + "There are too many active CoinFlips for you to create a new one.");
            return false;
        }
        if (amount.compareTo(new PlayerData(player).getMoney()) > 0) {
            player.sendMessage(ChatColor.RED + "You do not have enough money to create this bet!");
            return false;
        }
        if (amount.compareTo(BigInteger.ZERO) < 1) {
            player.sendMessage(ChatColor.RED + "You cannot create a CoinFlip for less than $1!");
            return false;
        }
        CoinFlip.createCoinFlip(player, amount, isHeads);
        player.sendMessage(ChatColor.AQUA + "You have successfully created a CoinFlip for " + ChatColor.GREEN + "$" + PrisonUtils.prettyNum(amount));

        return true;
    }
}
