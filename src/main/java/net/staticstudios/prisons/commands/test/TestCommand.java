package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.utils.WeightedElements;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        assert player != null;
        //AuctionHouseMenus.openMenu(player, 0);
        player.sendMessage(
                new WeightedElements<String>()
                        .add("10%", 10)
                        .add("20%", 20)
                        .add("20% again", 20)
                        .add("50%", 50)
                        .getRandom()
                );
        return false;
    }
}
