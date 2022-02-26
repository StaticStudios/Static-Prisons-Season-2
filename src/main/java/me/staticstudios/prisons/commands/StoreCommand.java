package me.staticstudios.prisons.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&l&bCheck out our store: &r&fstaticstudios.tebex.io"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://staticstudios.tebex.io/"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&a&lClick here to visit our store!"))));
        player.spigot().sendMessage(message);
        return true;
    }
}
