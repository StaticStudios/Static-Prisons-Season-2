package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.rankup.RankUp;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankUpMaxCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player);
        if (playerData.getMineRank() >= 25) {
            player.sendMessage(ChatColor.RED + "You cannot rank up any more as you are already max rank! You can prestige with \"/prestige\"");
            return false;
        }
        if (playerData.getMoney().compareTo(RankUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1)) < 0) {
            player.sendMessage(ChatColor.RED + "You do not have enough money to rank up! To rank up, it will cost: $" + Utils.addCommasToBigInteger(RankUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1)));
            return false;
        }
        while (playerData.getMineRank() < 25 && playerData.getMoney().compareTo(RankUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1)) > -1) {
            playerData.removeMoney(RankUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1));
            player.sendMessage(ChatColor.GREEN + "You have just ranked up! " + ChatColor.AQUA + Utils.getMineRankLetterFromMineRank(playerData.getMineRank()) + " -> " + Utils.getMineRankLetterFromMineRank(playerData.getMineRank() + 1));
            playerData.addMineRank(1);
        }
        return false;
    }
}
