package net.staticstudios.prisons.commands.normal;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.prisons.external.DiscordLink;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DiscordCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {
            Component message = Component.text("Join our Discord: ").color(NamedTextColor.BLUE).append(Component.text("discord.gg/static").color(NamedTextColor.WHITE))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/9S6K9E5"))
                    .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Click here to join our discord!").color(NamedTextColor.GREEN)));
            player.sendMessage(message);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "link" -> DiscordLink.initiateLinkRequest(player.getUniqueId());
            case "unlink" -> DiscordLink.unlinkAccount(player.getUniqueId());
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("link");
            list.add("unlink");
        }
        return list;
    }
}
