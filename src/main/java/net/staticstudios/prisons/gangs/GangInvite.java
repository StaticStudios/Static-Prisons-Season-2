package net.staticstudios.prisons.gangs;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.utils.Invite;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class GangInvite extends Invite<Player, Gang> {
    public static final LinkedHashMap<UUID, List<GangInvite>> PLAYER_INVITES = new LinkedHashMap<>();

    public GangInvite(Player sender, Player receiver) {
        super(Gang.getGang(sender), sender, receiver, 300, i -> {
            if (!PLAYER_INVITES.containsKey(i.getReceiver().getUniqueId())) return;
            List<GangInvite> invites = PLAYER_INVITES.get(i.getReceiver().getUniqueId());
            invites.remove(i);
            if (invites.isEmpty()) PLAYER_INVITES.remove(i.getReceiver().getUniqueId());
            else PLAYER_INVITES.put(i.getReceiver().getUniqueId(), invites);
        });
        List<GangInvite> invites = PLAYER_INVITES.getOrDefault(receiver.getUniqueId(), new ArrayList<>());
        invites.add(this);
        PLAYER_INVITES.put(receiver.getUniqueId(), invites);

        receiver.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&b" + sender.getName() + " &finvited you to join &a" + Gang.getGang(sender).getName() + "! &fView this invite with &7&o/gang invites"));
        setOnAccept(i -> {
            if (i.getWhat().isFull()) {
                i.getReceiver().sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cThis gang is full!"));
                return;
            }
            i.getWhat().addMember(i.getReceiver().getUniqueId());
        });
        setOnDecline(i -> {
            i.getSender().sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&b" + i.getReceiver().getName() + "&f declined your invite to join &a" + i.getWhat().getName() + "!"));
        });
    }
}
