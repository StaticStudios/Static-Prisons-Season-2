package me.staticstudios.prisons.gameplay.islands.invites;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class IslandInvites {
    //Key = islandUUID
    public Map<String, IslandInvite> invites = new HashMap<>();

    public void addInvite(String inviterUUID, String islandUUID) {
        invites.put(islandUUID, new IslandInvite(Instant.now().getEpochSecond() + SkyblockIslandInviteManager.inviteExpireDelay, inviterUUID));
    }
    public void updateExpiredInvites() {
        for (String key : invites.keySet()) {
            if (invites.get(key).expireAt < Instant.now().getEpochSecond()) {
                invites.remove(key);
            }
        }
    }
}
