package me.staticstudios.prisons.discord;

import me.staticstudios.prisons.Main;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;

public class DiscordAddRoles {
    public static final String OWNER = "789966589750804491";
    public static final String SRADMIN = "587422778035273741";
    public static final String ADMIN = "587422698825580545";
    public static final String SRMOD = "587422634921295872";
    public static final String MOD = "587422604365922304";
    public static final String HELPER = "587422525374595083";
    public static final String NITRO = "629662637625442305";
    public static final String LINKED_MC_ACCOUNT = "929859435050962955";
    //public static final String STATICP = "929859741583282186";
    //public static final String STATIC = "929859811783364688";
    //public static final String MYTHIC = "929859831744045057";
    //public static final String MASTER = "929859858331746314";
    //public static final String WARRIOR = "929859877826871386";
    //public static final String DONATOR = "929859898483818576";

    public static void giveRolesFromUUID(String uuid) {
        if (!LinkHandler.checkIfLinkedFromUUID(uuid)) return;
        giveRolesFromDiscordID(LinkHandler.getLinkedDiscordIDFromUUID(uuid));
    }
    public static void giveRolesFromDiscordID(String id) {
        if (!LinkHandler.checkIfLinkedFromDiscordID(id)) return;
        addRole(id, LINKED_MC_ACCOUNT);
    }

    public static void removeAllRoles(String memberID) {
        removeRole(memberID, LINKED_MC_ACCOUNT);
    }
    public static void removeRole(String memberID, String roleID) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> DiscordBot.jda.getGuildById("587372348294955009").retrieveMemberById(memberID).queue(member -> {
            for (Role role : member.getRoles()) {
                if (role.getId().equals(roleID)) {
                    DiscordBot.jda.getGuildById("587372348294955009").removeRoleFromMember(memberID, DiscordBot.jda.getGuildById("587372348294955009").getRoleById(roleID)).queue();
                }
            }
        }));
    }
    public static void addRole(String memberID, String roleID) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> DiscordBot.jda.getGuildById("587372348294955009").retrieveMemberById(memberID).queue(member -> {
            for (Role role : member.getRoles()) {
                if (role.getId().equals(roleID)) {
                    return;
                }
            }
            DiscordBot.jda.getGuildById("587372348294955009").addRoleToMember(memberID, DiscordBot.jda.getGuildById("587372348294955009").getRoleById(roleID)).queue();
        }));
    }
}
