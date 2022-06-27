package net.staticstudios.prisons.gangs;

import org.bukkit.entity.Player;

import java.util.UUID;

public class GangMenus { //todo: this

    public static void open(Player player, boolean fromCommand) {
        if (PrisonGang.hasGang(player)) openYourGang(player, fromCommand);
        else openCreateGang(player, fromCommand);
    }

    public static void openCreateGang(Player player, boolean fromCommand) {
        if (PrisonGang.hasGang(player)) {
            openYourGang(player, fromCommand);
            return;
        }
    }
    public static void openYourGang(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
    }
    public static void openGangInfo(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangSettings(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangInvite(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangInvites(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangMembers(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangManageMember(UUID member, Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangStats(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangBank(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangChest(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }



}
