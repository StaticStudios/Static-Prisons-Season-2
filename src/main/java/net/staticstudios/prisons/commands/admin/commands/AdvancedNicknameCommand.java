package net.staticstudios.prisons.commands.admin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdvancedNicknameCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        PlayerData playerData = new PlayerData(player);

        if ("".equals(playerData.getStaffRank())) return false;

        if (args.length != 1) {
            player.sendMessage(Component.text("Usage: /advancednickname <minimessage ").append(Component.text("(click me)").clickEvent(ClickEvent.openUrl("https://webui.adventure.kyori.net/"))).append(Component.text(" | reset>")));
            return false;
        }

        if ("reset".equalsIgnoreCase(args[0])) {
            playerData.setIsChatNickNameEnabled(false);
            player.sendMessage(Component.text("Your nickname has been reset!").color(NamedTextColor.GREEN));
            return false;
        }

        Component nickname = MiniMessage.miniMessage().deserialize(args[0]);

        if (PlainTextComponentSerializer.plainText().serialize(nickname).contains("<") || PlainTextComponentSerializer.plainText().serialize(nickname).contains(">")) {
            player.sendMessage(Component.text("Invalid MiniMessage!").color(NamedTextColor.RED));
            return false;
        }

        if (PlainTextComponentSerializer.plainText().serialize(nickname).length() > 16) {
            player.sendMessage(Component.text("You cannot set a nickname longer than 16 characters!").color(NamedTextColor.RED));
            return false;
        }

        playerData.setIsChatNickNameEnabled(true);
        playerData.setChatNickname(MiniMessage.miniMessage().serialize(nickname));

        player.sendMessage(Component.text("Set nickname to ").color(NamedTextColor.GREEN).append(nickname).append(Component.text("!")));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
