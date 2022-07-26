package net.staticstudios.prisons.pvp.koth.commands;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public class KingOfTheHillCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        if (args.length < 1) {
            player.sendMessage("Usage: /koth <set|remove>");
            return true;
        }

        if ("set".equalsIgnoreCase(args[0])) {
            if (!PVP_WORLD.equals(player.getWorld())) {
                sender.sendMessage(Prefix.KOTH.append(Component.text("You must be in the PVP world to use this command!")));
                return false;
            }

            KingOfTheHillManager.setKothBlock(player.getLocation());
            player.sendMessage(Prefix.KOTH.append(Component.text("Successfully set the King of the Hill area!")));
        }

        if ("remove".equalsIgnoreCase(args[0])) {
            if (!PVP_WORLD.equals(player.getWorld())) {
                sender.sendMessage(Prefix.KOTH.append(Component.text("You must be in the PVP world to use this command!")));
                return false;
            }

            if (!KingOfTheHillManager.isKothArea(player.getLocation(), 5)) {
                player.sendMessage(Prefix.PVP.append(Component.text("There is no King of the Hill area here!")));
                return false;
            }

            KingOfTheHillManager.removeKothBlock();
        }
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return args.length == 1 ? List.of("set", "remove") : Collections.emptyList();
    }
}
