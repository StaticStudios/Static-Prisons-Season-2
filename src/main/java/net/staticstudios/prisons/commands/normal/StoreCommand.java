package net.staticstudios.prisons.commands.normal;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StoreCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&l&bCheck out our store: &r&fstore.static-studios.net"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://store.static-studios.net"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&aClick here to visit our store!"))));
        player.spigot().sendMessage(message);
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
