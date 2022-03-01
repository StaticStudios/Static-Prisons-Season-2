package me.staticstudios.prisons.discord.commands;

import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.discord.BaseDiscordCommand;
import me.staticstudios.prisons.discord.DiscordLinkHandler;
import me.staticstudios.prisons.discord.LinkHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.UUID;

public class DiscordLinkCommand extends BaseDiscordCommand {
    public DiscordLinkCommand() {
        commandName = "link";
        description = "This command will allow users to link their discord accounts to their Minecraft accounts.";
        codeToRun = () -> {
            if (args.length == 0) {
                sendMessage("Invalid command! prisons!link <code>");
            } else if (!DiscordLinkHandler.checkIfCodeExists(args[0])) {
                sendMessage("Invalid code! Please run the following command in the Static Prisons Minecraft server: /discord link");
            } else if (LinkHandler.checkIfLinkedFromDiscordID(e.getAuthor().getId())) {
                sendMessage("Account is already linked if you wish to unlink it do prisons!unlink");
            } else {
                LinkHandler.linkAccount(e.getAuthor().getId(), DiscordLinkHandler.getUUIDFromCode(args[0]));
                if (Bukkit.getPlayer(UUID.fromString(DiscordLinkHandler.getUUIDFromCode(args[0]))) != null) {
                    Bukkit.getPlayer(UUID.fromString(DiscordLinkHandler.getUUIDFromCode(args[0]))).sendMessage(ChatColor.BLUE + "[Discord] " + ChatColor.GREEN + "Your account has successfully been linked to the discord account: " + ChatColor.LIGHT_PURPLE + e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator());
                }
                sendMessage("You have successfully linked your discord account (" + e.getAuthor().getName() + "#" + e.getAuthor().getDiscriminator() + ") to the Minecraft account (" + new ServerData().getPlayerNameFromUUID(DiscordLinkHandler.getUUIDFromCode(args[0])) + ")! If this was a mistake, please type prisons!unlink");
                DiscordLinkHandler.removeInvite(args[0]);
            }
        };
        register();
    }
}
