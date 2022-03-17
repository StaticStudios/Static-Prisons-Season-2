package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.external.DiscordLink;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&9Join our Discord: &fdiscord.gg/staticmc"));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/9S6K9E5"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&aClick here to join our discord!"))));
            player.spigot().sendMessage(message);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "link" -> DiscordLink.initiateLinkRequest(player.getUniqueId());
            case "unlink" -> DiscordLink.unlinkAccount(player.getUniqueId());
        }

        return true;
    }
}
