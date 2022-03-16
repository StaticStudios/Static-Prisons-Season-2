package me.staticstudios.prisons.external.discord_old.commands;

import me.staticstudios.prisons.core.data.serverData.ServerData;
import me.staticstudios.prisons.external.discord_old.BaseDiscordCommand;
import me.staticstudios.prisons.external.discord_old.DiscordCommandHandler;
import me.staticstudios.prisons.external.discord_old.LinkHandler;

public class DiscordGetMinecraftAccountCommand extends BaseDiscordCommand {
    public DiscordGetMinecraftAccountCommand() {
        commandName = "getminecraftaccount";
        aliases = new String[]{"getmcaccount", "getlinkedaccount", "getlinkedacc", "getmcacc"};
        description = "Running this command will try to get a mentioned user's minecraft account if they have linked it";
        codeToRun = () -> {
            if (e.getMessage().getMentionedMembers().isEmpty()) {
                sendMessage("Invalid usage, " + DiscordCommandHandler.prefix + "getminecraftaccount <mention a user to get the account of>");
                return;
            }
            if (!LinkHandler.checkIfLinkedFromDiscordID(e.getMessage().getMentionedMembers().get(0).getId())) {
                sendMessage("User has not linked their account!");
            } else {
                sendMessage("User is linked with the Minecraft account: " + new ServerData().getPlayerNameFromUUID(LinkHandler.getLinkedUUIDFromDiscordID(e.getMessage().getMentionedMembers().get(0).getId())));
            }
        };
        register();
    }
}
