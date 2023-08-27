package net.staticstudios.prisons.utils;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.function.Consumer;

public abstract class Invite<U, W> {
    static Map<UUID, Invite> invites = new HashMap<>();

    final UUID inviteID = UUID.randomUUID();
    final U sender;
    final U receiver;
    final W what;
    Consumer<Invite<U, W>> onAccept = (i) -> {
    };
    Consumer<Invite<U, W>> onDecline = (i) -> {
    };
    Consumer<Invite<U, W>> onExpire = (i) -> {
    };
    Consumer<Invite<U, W>> onDelete = (i) -> {
    };
    long expireAt;
    protected boolean hasExpired = false;

    static boolean workerStarted = false;

    static void startWorker() {
        if (workerStarted) return;
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            List<Invite> invitesToRemove = new ArrayList<>();
            for (Map.Entry<UUID, Invite> entry : invites.entrySet()) {
                if (entry.getValue().isExpired()) {
                    entry.getValue().expire();
                    invitesToRemove.add(entry.getValue());
                }
            }
            for (Invite invite : invitesToRemove) {
                invites.remove(invite.inviteID);
            }
        }, 20, 20);
        workerStarted = true;
    }

    public Invite(W what, U sender, U receiver, long secondsToLive) {
        this(what, sender, receiver, secondsToLive, i -> {
        });
    }

    public Invite(W what, U sender, U receiver, long secondsToLive, Consumer<Invite<U, W>> onDelete) {
        this.what = what;
        this.sender = sender;
        this.receiver = receiver;
        this.expireAt = System.currentTimeMillis() + secondsToLive * 1000;
        this.onDelete = onDelete;
        invites.put(inviteID, this);
        startWorker();
    }

    public void setOnAccept(Consumer<Invite<U, W>> onAccept) {
        this.onAccept = onAccept;
    }

    public void setOnDecline(Consumer<Invite<U, W>> onDecline) {
        this.onDecline = onDecline;
    }

    public void setOnExpire(Consumer<Invite<U, W>> onExpire) {
        this.onExpire = onExpire;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expireAt;
    }

    public void acceptInvite() {
        if (isExpired() && !hasExpired) {
            expire();
            return;
        } else if (isExpired()) {
            return;
        }
        onAccept.accept(this);
        onDelete.accept(this);
        invites.remove(inviteID);
    }

    public void declineInvite() {
        if (isExpired() && !hasExpired) {
            expire();
            return;
        } else if (isExpired()) {
            return;
        }
        onDecline.accept(this);
        onDelete.accept(this);
        invites.remove(inviteID);
    }

    protected void expire() {
        if (hasExpired) return;
        onExpire.accept(this);
        onDelete.accept(this);
        hasExpired = true;
    }

    public W getWhat() {
        return what;
    }

    public U getSender() {
        return sender;
    }

    public U getReceiver() {
        return receiver;
    }
}
