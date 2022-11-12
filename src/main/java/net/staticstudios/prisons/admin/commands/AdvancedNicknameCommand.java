package net.staticstudios.prisons.admin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public class AdvancedNicknameCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        PlayerData playerData = new PlayerData(player);

        if ("".equals(playerData.getStaffRank())) return false;

        if (args.length != 1) {
            player.sendMessage(text("Usage: /advancednickname <minimessage ").append(text("(click me)").clickEvent(ClickEvent.openUrl("https://webui.adventure.kyori.net/"))).append(text(" | reset>")));
            return false;
        }

        if ("reset".equalsIgnoreCase(args[0])) {
            playerData.useNickname(false);
            player.sendMessage(text("Your nickname has been reset!").color(GREEN));
            return false;
        }

        if (args[0].matches(".*<click:.*>.*")) {
            player.sendMessage(text("You can't use click events in your nickname!").color(RED));
            return false;
        }

        Component nickname = StaticPrisons.miniMessage().deserialize(args[0]);

        if (PlainTextComponentSerializer.plainText().serialize(nickname).contains("<") || PlainTextComponentSerializer.plainText().serialize(nickname).contains(">")) {
            player.sendMessage(text("Invalid MiniMessage!").color(RED));
            return false;
        }

        String nicknameString = PlainTextComponentSerializer.plainText().serialize(nickname);

        if (nicknameString.length() > 16) {
            player.sendMessage(text("You cannot set a nickname longer than 16 characters!").color(RED));
            return false;
        }

        if (nicknameString.isEmpty()) {
            player.sendMessage(text("You cannot have a blank nickname!").color(RED));
            return false;
        }

        playerData.useNickname(true);
        playerData.setNickname(StaticPrisons.miniMessage().serialize(nickname));

        player.sendMessage(text("Set nickname to ").color(GREEN).append(nickname).append(text("!")));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
