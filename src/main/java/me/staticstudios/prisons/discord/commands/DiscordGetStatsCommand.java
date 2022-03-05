package me.staticstudios.prisons.discord.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.discord.BaseDiscordCommand;
import me.staticstudios.prisons.discord.DiscordCommandHandler;
import me.staticstudios.prisons.discord.LinkHandler;
import me.staticstudios.prisons.utils.Utils;

import java.util.concurrent.TimeUnit;

public class DiscordGetStatsCommand extends BaseDiscordCommand {
    public DiscordGetStatsCommand() {
        commandName = "getstats";
        aliases = new String[]{"stats"};
        description = "This command will get a user's stats from their linked discord account or their in-game name";
        codeToRun = () -> {
            String uuid = null;
            if (args.length == 0) {
                sendMessage("Invalid usage, " + DiscordCommandHandler.prefix + "stats `<@mention|MCUsername>`");
                return;
            }
            if (e.getMessage().getMentionedMembers().isEmpty()) {
                for (String key : new ServerData().getPlayerNamesToUUIDsMap().keySet()) {
                    if (key.equalsIgnoreCase(args[0])) {
                        uuid = new ServerData().getPlayerUUIDFromName(key);
                        break;
                    }
                }
                if (uuid == null) {
                    sendMessage("Could not find the following player: " + args[0]);
                    return;
                }
            } else if (!LinkHandler.checkIfLinkedFromDiscordID(e.getMessage().getMentionedMembers().get(0).getId())) {
                sendMessage("User has not linked their account!");
                return;
            } else {
                uuid = LinkHandler.getLinkedUUIDFromDiscordID(e.getMessage().getMentionedMembers().get(0).getId());
            }
            PlayerData playerData = new PlayerData(uuid);
            StringBuilder msg = new StringBuilder("**__" + new ServerData().getPlayerNameFromUUID(playerData.getUUID()) + "'s Stats:__**\n```");
            String rank = "member";
            switch (playerData.getPlayerRank()) {
                case "warrior" -> rank = "Warrior";
                case "master" -> rank = "Master";
                case "mythic" -> rank = "Mythic";
                case "static" -> rank = "Static";
                case "staticp" -> rank = "Static+";
            }
            msg.append("Rank: ").append(rank).append("\n");
            msg.append("Mine Rank: ").append(Utils.getMineRankLetterFromMineRank(playerData.getMineRank())).append("\n");
            msg.append("Current Prestige: ").append(Utils.addCommasToNumber(playerData.getPrestige())).append("\n");
            msg.append("Current Balance: $").append(Utils.addCommasToNumber(playerData.getMoney())).append(" ($").append(Utils.prettyNum(playerData.getMoney())).append(")\n");
            msg.append("Current Token Balance: ").append(Utils.addCommasToNumber(playerData.getTokens())).append(" (").append(Utils.prettyNum(playerData.getTokens())).append(")\n");
            msg.append("Blocks Mined: ").append(Utils.addCommasToNumber(playerData.getBlocksMined())).append(" (").append(Utils.prettyNum(playerData.getBlocksMined())).append(")\n");
            msg.append("Raw Blocks Mined: ").append(Utils.addCommasToNumber(playerData.getRawBlocksMined())).append(" (").append(Utils.prettyNum(playerData.getRawBlocksMined())).append(")\n");
            long duration = playerData.getTimePlayed().longValue() * 1000;
            long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
            long diffInHours = TimeUnit.MILLISECONDS.toHours(duration) % 24;
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
            msg.append("Time Played: ").append(diffInDays).append(" day(s), ").append(diffInHours).append(" hour(s) and ").append(diffInMinutes).append(" minute(s)");
            msg.append("```");
            sendMessage(msg.toString());
        };
        register();
    }
}
