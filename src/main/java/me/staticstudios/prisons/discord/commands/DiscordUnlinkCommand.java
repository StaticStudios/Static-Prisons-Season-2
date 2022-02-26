package me.staticstudios.prisons.discord.commands;

import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.discord.BaseDiscordCommand;
import me.staticstudios.prisons.discord.LinkHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.time.Instant;
import java.util.UUID;

public class DiscordUnlinkCommand extends BaseDiscordCommand {
    public DiscordUnlinkCommand() {
        commandName = "unlink";
        description = "This command will allow users to unlink their discord accounts to their Minecraft accounts.";
        codeToRun = () -> {
            if (LinkHandler.checkIfLinkedFromDiscordID(e.getAuthor().getId())) {
                if (LinkHandler.getTimeLinkedFromDiscordID(e.getAuthor().getId()) + 24 * 60 * 60 * 1000 < Instant.now().toEpochMilli()) {
                    sendMessage("You must wait 24h after linking your account before you can unlink it.");
                    return;
                }
                sendMessage("You have successfully unlinked your accounts! Your discord account was previously linked to the Minecraft account: " + new ServerData().getPlayerNameFromUUID(LinkHandler.getLinkedUUIDFromDiscordID(e.getAuthor().getId())));
                if (Bukkit.getPlayer(UUID.fromString(LinkHandler.getLinkedUUIDFromDiscordID(e.getAuthor().getId()))) != null) {
                    Bukkit.getPlayer(UUID.fromString(LinkHandler.getLinkedUUIDFromDiscordID(e.getAuthor().getId()))).sendMessage(ChatColor.RED + "You have successfully unlinked your accounts! Your account was previously linked to the Discord account: " + ChatColor.BLUE + e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator());
                }
                LinkHandler.unlinkFromDiscordID(e.getAuthor().getId());
            } else {
                sendMessage("You do not have a Minecraft account currently linked to your discord account. Run the command `/discord link` in game to begin the linking process!");
            }
        };
        register();
    }
}
