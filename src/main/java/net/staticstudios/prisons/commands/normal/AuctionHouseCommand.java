package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.auctionHouse.AuctionHouseMenus;
import net.staticstudios.prisons.auctionHouse.AuctionManager;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AuctionHouseCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length < 1) {
            AuctionHouseMenus.openMenu(player, 0);
            return false;
        }
        if (args[0].equalsIgnoreCase("hand")) {
            if (args.length == 1) {
                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/ah hand <price>"));
                return false;
            }
            try {
                long price = Long.parseLong(args[1]);
                if (AuctionManager.createAuction(player, player.getInventory().getItemInMainHand(), price)) {
                    player.getInventory().getItemInMainHand().setAmount(0);
                }
            } catch (NumberFormatException e) {
                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/ah hand <price>"));
            }
        } else {
            AuctionHouseMenus.openMenu(player, 0);
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("hand");
            list.add("view");
        }
        if (args.length == 2) list.add("<price>");
        return list;
    }
}
