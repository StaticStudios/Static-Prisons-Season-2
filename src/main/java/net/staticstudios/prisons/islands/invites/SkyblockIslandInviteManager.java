package net.staticstudios.prisons.islands.invites;

import java.util.HashMap;
import java.util.Map;

public class SkyblockIslandInviteManager {
    //Clear this map on player relog
    private static Map<String, IslandInvites> alliInvites = new HashMap<>();
    static final int inviteExpireDelay = 300; //In seconds
    public static void addIslandInvite(String playerInvitedUUID, String playerInviterUUID, String islandUUID) {
        if (!alliInvites.containsKey(playerInvitedUUID)) {
            alliInvites.put(playerInvitedUUID, new IslandInvites());
        }
        IslandInvites invites = alliInvites.get(playerInvitedUUID);
        invites.addInvite(playerInviterUUID, islandUUID);
        alliInvites.put(playerInvitedUUID, invites);
    }
    public static IslandInvites getIslandInvites(String playerUUID) {
        if (!alliInvites.containsKey(playerUUID)) {
            alliInvites.put(playerUUID, new IslandInvites());
        }
        return alliInvites.get(playerUUID);
    }
    public static void updateExpiredIslandInvites(String playerUUID) {
        IslandInvites invites = getIslandInvites(playerUUID);
        invites.updateExpiredInvites();
        alliInvites.put(playerUUID, invites);
    }

}

