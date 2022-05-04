package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class AuctionHouseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length < 2) {
            GUI.getGUIPage("auctionHouse").args = "0";
            GUI.getGUIPage("auctionHouse").open(player);
            return false;
        }
        if (args[0].equalsIgnoreCase("hand")) {
            try {
                BigInteger price = new BigInteger(args[1]);
                //AuctionHouseManager.createAuction(player, player.getInventory().getItemInMainHand(), price);
            } catch (NumberFormatException e) {
                player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/auctionhousse hand <price>"));
            }
        }
        return false;
    }
}
