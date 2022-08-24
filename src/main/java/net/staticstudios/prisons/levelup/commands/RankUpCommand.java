package net.staticstudios.prisons.levelup.commands;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.levelup.LevelUp;
import net.staticstudios.prisons.utils.PrisonUtils;
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

public class RankUpCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);
        if (playerData.getMineRank() >= 25) {
            player.sendMessage(ChatColor.RED + "You cannot rank up any more as you are already max rank! You can prestige with \"/prestige\"");
            return false;
        }
        if (playerData.getMoney() > LevelUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1)) {
            playerData.removeMoney(LevelUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1));
            player.sendMessage(ChatColor.GREEN + "You have just ranked up! " + ChatColor.AQUA + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank()) + " -> " + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank() + 1));
            playerData.addMineRank(1);
        } else {
            player.sendMessage(ChatColor.RED + "You do not have enough money to rank up! To rank up, it will cost: $" + PrisonUtils.addCommasToNumber(LevelUp.calculatePriceToRankUpTo(playerData, playerData.getMineRank() + 1)));
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
