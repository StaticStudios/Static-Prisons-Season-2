package net.staticstudios.prisons.leaderboards;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BalanceTop {
    static final String npc1Name = "leaderboardsMoney1";
    static final String npc2Name = "leaderboardsMoney2";
    static final String npc3Name = "leaderboardsMoney3";
    public static List<UUID> top100UUIDs = new ArrayList<>();

    public static void calculateLeaderBoard() {
        top100UUIDs = ServerData.PLAYERS.getAllUUIDs().stream()
                .map(PlayerData::new)
                .sorted((o1, o2) -> Long.compare(o2.getMoney(), o1.getMoney()))
                .map(PlayerData::getUUID)
                .limit(100)
                .toList();
        updateNPC();
    }

    static void updateNPC() {
        if (top100UUIDs.size() >= 1)
            LeaderboardManager.updateLeaderboardNPC(npc1Name, "#1 Balance Top", ServerData.PLAYERS.getName(top100UUIDs.get(0)), PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(0)).getMoney()));
        if (top100UUIDs.size() >= 2)
            LeaderboardManager.updateLeaderboardNPC(npc2Name, "#2 Balance Top", ServerData.PLAYERS.getName(top100UUIDs.get(1)), PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(1)).getMoney()));
        if (top100UUIDs.size() >= 3)
            LeaderboardManager.updateLeaderboardNPC(npc3Name, "#3 Balance Top", ServerData.PLAYERS.getName(top100UUIDs.get(2)), PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(2)).getMoney()));
    }
}
