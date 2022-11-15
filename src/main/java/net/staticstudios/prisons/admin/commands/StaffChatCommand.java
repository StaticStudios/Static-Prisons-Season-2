package net.staticstudios.prisons.admin.commands;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.CommandUtils;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static net.kyori.adventure.text.format.NamedTextColor.*;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player && !player.hasPermission("static.staff")) {
            return false;
        }

        if (args.length < 1) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(CommandUtils.getCorrectUsage("/staffchat <message>"));
                return true;
            }

            PlayerData playerData = new PlayerData(player);

            playerData.setStaffChatEnabled(!playerData.isStaffChatEnabled());

            player.sendMessage((playerData.isStaffChatEnabled()
                    ? Component.text("Enabled").color(GREEN)
                    : Component.text("Disabled").color(RED))
                    .append(Component.text(" staff chat.").color(GRAY))
            );

            return true;
        }

        if (sender instanceof Player player && !new PlayerData(player).isStaffChatEnabled()) {
            new PlayerData(player).setStaffChatEnabled(true);

            player.sendMessage(Component.text("Enabled").color(GREEN)
                    .append(Component.text(" staff chat.").color(GRAY)));
        }

        ArrayList<Audience> audiences = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("static.staff") && new PlayerData(player).isStaffChatEnabled())
                .map(player -> (Audience) player).collect(Collectors.toCollection(ArrayList::new));

        audiences.add(Bukkit.getConsoleSender());

        Component senderName = sender instanceof Player player
                ? sender.name()
                .color(GREEN)
                .hoverEvent(HoverEvent
                        .showText(TeamPrefix.getFromIdDeserialized(new PlayerData(player).getStaffRank())))
                : Component.text("Console")
                .color(BLUE)
                .hoverEvent(HoverEvent.showText(Component.text("This message was sent from the console.")));

        Audience.audience(audiences).sendMessage(Component.textOfChildren(
                Prefix.STAFF_CHAT,
                senderName,
                Component.text(": ").color(DARK_GRAY),
                Component.text(String.join(" ", args)).color(YELLOW)
        ));

        return true;
    }
}
