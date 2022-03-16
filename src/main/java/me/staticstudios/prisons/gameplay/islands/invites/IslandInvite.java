package me.staticstudios.prisons.gameplay.islands.invites;

public class IslandInvite {
    public long expireAt;
    public String inviterUUID;

    public IslandInvite(long expireAt, String inviterUUID) {
        this.expireAt = expireAt;
        this.inviterUUID = inviterUUID;
    }
}
