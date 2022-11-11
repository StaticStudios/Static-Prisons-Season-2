package net.staticstudios.prisons.leaderboards;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TokensTop {
    static final String npc1Name = "leaderboardsTokens1";
    static final String npc2Name = "leaderboardsTokens2";
    static final String npc3Name = "leaderboardsTokens3";
    public static List<UUID> top100UUIDs = new ArrayList<>();

    public static void calculateLeaderBoard() {
        top100UUIDs = ServerData.PLAYERS.getAllUUIDs().stream()
                .map(PlayerData::new)
                .filter(playerData -> !playerData.getIsExemptFromLeaderboards())
                .sorted((o1, o2) -> Long.compare(o2.getTokens(), o1.getTokens()))
                .map(PlayerData::getUUID)
                .limit(100)
                .toList();
        updateNPC();
    }

    static void updateNPC() {
        if (top100UUIDs.size() >= 1)
            LeaderboardManager.updateLeaderboardNPC(npc1Name, "#1 Tokens", ServerData.PLAYERS.getName(top100UUIDs.get(0)), PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(0)).getTokens()));
        if (top100UUIDs.size() >= 2)
            LeaderboardManager.updateLeaderboardNPC(npc2Name, "#2 Tokens", ServerData.PLAYERS.getName(top100UUIDs.get(1)), PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(1)).getTokens()));
        if (top100UUIDs.size() >= 3)
            LeaderboardManager.updateLeaderboardNPC(npc3Name, "#3 Tokens", ServerData.PLAYERS.getName(top100UUIDs.get(2)), PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(2)).getTokens()));
    }
}
