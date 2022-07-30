package net.staticstudios.prisons.commands.admin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class VanishCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        if (AdminManager.removeFromHiddenPlayers(player)) {

            Bukkit.broadcast(Component.text("Joined").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD)
                    .append(Component.text(" -> ")).decoration(TextDecoration.BOLD, false)
                    .append(player.name().color(ComponentUtil.WHITE)));

            StaticPrisons.getInstance().getLogger().warning(player.getName() + " exited vanish mode");

            player.sendMessage(Prefix.VANISH
                    .append(Component.text("Disabled ").color(ComponentUtil.RED))
                    .append(Component.text("vanish mode").color(ComponentUtil.GRAY)));

            AdminManager.showPlayer(player);
        } else {

            Bukkit.broadcast(Component.text("Left").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)
                    .append(Component.text(" -> ")).decoration(TextDecoration.BOLD, false)
                    .append(player.name().color(ComponentUtil.WHITE)));

            StaticPrisons.getInstance().getLogger().warning(player.getName() + " entered vanish mode");

            AdminManager.addToHiddenPlayers(player);

            AdminManager.hidePlayer(player);

            player.sendMessage(Prefix.VANISH
                    .append(Component.text("Enabled ").color(ComponentUtil.GREEN))
                    .append(Component.text("vanish mode").color(ComponentUtil.GRAY)));
        }
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
