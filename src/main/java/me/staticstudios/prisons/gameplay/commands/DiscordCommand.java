package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.external.DiscordLink;
import me.staticstudios.prisons.external.discord_old.DiscordBot;
import me.staticstudios.prisons.external.discord_old.DiscordLinkHandler;
import me.staticstudios.prisons.external.discord_old.LinkHandler;
import net.dv8tion.jda.api.entities.User;
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
            case "unlink" -> {
                if (LinkHandler.checkIfLinkedFromUUID(player.getUniqueId().toString())) {
                    if (LinkHandler.getTimeLinkedFromUUID(player.getUniqueId().toString()) + 24 * 60 * 60 * 1000 < Instant.now().toEpochMilli()) {
                        player.sendMessage(ChatColor.RED + "You must wait 24h after linking your account before you can unlink it.");
                        return false;
                    }
                    LinkHandler.unlinkFromUUID(player.getUniqueId().toString());
                    player.sendMessage(ChatColor.GREEN + "You account(s) have been successfully unlinked!");
                } else {
                    player.sendMessage(ChatColor.RED + "Your account is not linked to a discord account!\n" + ChatColor.AQUA + "To link your discord account to your Minecraft account, please join our discord and type in the following in #bot-commands: " + ChatColor.LIGHT_PURPLE + "prisons!link " + DiscordLinkHandler.addInvite(player.getUniqueId().toString()));
                }
            }
        }

        return true;
    }
}
