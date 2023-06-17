package net.staticstudios.prisons.levelup.rankup;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class RankUpMaxCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);

        if (playerData.getMineRank() >= 25) {
            player.sendMessage(text("You cannot rank up any more as you are already max rank! You can prestige with \"/prestige\"").color(RED));
            return false;
        }

        if (playerData.getMoney() < RankUp.calculatePriceToRankUp(playerData, playerData.getMineRank() + 1)) {
            player.sendMessage(text("You do not have enough money to rank up! To rank up, it will cost: $")
                    .append(text(PrisonUtils.addCommasToNumber(RankUp.calculatePriceToRankUp(playerData, playerData.getMineRank() + 1))))
                    .color(RED));
            return false;
        }

        int rankBefore = playerData.getMineRank();
        boolean canRankUp = true;

        while (playerData.getMineRank() < 25 && canRankUp) {
            canRankUp = RankUp.rankUp(player, playerData.getMineRank() + 1);
        }


        player.sendMessage(text("You just ranked up! ").color(GREEN)
                .append(text(PrisonUtils.getMineRankLetterFromMineRank(rankBefore)).color(AQUA))
                .append(text(" -> "))
                .append(text(PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank())).color(AQUA))
        );
        return false;
    }
}
