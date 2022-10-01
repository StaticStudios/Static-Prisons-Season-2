package net.staticstudios.prisons.levelup.rankup;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.levelup.rankup.RankUp;
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
import java.util.Collections;
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
        if (RankUp.rankUp(player, playerData.getMineRank() + 1)) {
            player.sendMessage(ChatColor.GREEN + "You just ranked up! " + ChatColor.AQUA + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank() - 1) + " -> " + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank()));
        } else {
            player.sendMessage(ChatColor.RED + "You do not have enough money to rank up! To rank up, it will cost: $" + PrisonUtils.addCommasToNumber(RankUp.calculatePriceToRankUp(playerData, playerData.getMineRank() + 1)));
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
