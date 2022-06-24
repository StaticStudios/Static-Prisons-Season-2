package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.rankup.RankUp;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankUpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player);
        if (playerData.getMineRank() >= 25) {
            player.sendMessage(ChatColor.RED + "You cannot rank up any more as you are already max rank! You can prestige with \"/prestige\"");
            return false;
        }
        if (playerData.getMoney().compareTo(RankUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1)) > -1) {
            playerData.removeMoney(RankUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1));
            player.sendMessage(ChatColor.GREEN + "You have just ranked up! " + ChatColor.AQUA + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank()) + " -> " + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank() + 1));
            playerData.addMineRank(1);
        } else {
            player.sendMessage(ChatColor.RED + "You do not have enough money to rank up! To rank up, it will cost: $" + PrisonUtils.addCommasToNumber(RankUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1)));
        }
        return false;
    }
}
