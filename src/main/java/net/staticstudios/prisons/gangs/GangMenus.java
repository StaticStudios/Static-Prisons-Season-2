package net.staticstudios.prisons.gangs;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.gui.newGui.MainMenus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class GangMenus extends GUIUtils { //todo: this

    public static void open(Player player, boolean fromCommand) {
        if (PrisonGang.hasGang(player)) openYourGang(player, fromCommand);
        else openCreateGang(player, fromCommand);
    }

    public static void openCreateGang(Player player, boolean fromCommand) {
        if (PrisonGang.hasGang(player)) {
            openYourGang(player, fromCommand);
            return;
        }
        GUICreator c = new GUICreator(27, "Create A Gang");
        c.setItem(13, c.createButton(Material.STONE_SWORD, "&eCreate a Prison Gang", List.of(), (p, t) -> {
            PrisonGang.createGang(p.getUniqueId(), p.getName() + "'s Gang");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', PrisonGang.PREFIX + "You created a Gang! Invite your friends to take part in team based activities! &7&o/gang invite <player>"));
            openYourGang(p, fromCommand);
        }));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
    }
    public static void openYourGang(Player player, boolean fromCommand) {
        if (!PrisonGang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
        PrisonGang gang = PrisonGang.getGang(player);
        GUICreator c = new GUICreator(36, gang.getName());
        c.setItem(31, ench(c.createButton(Material.GUNPOWDER, "&c&lGang Settings", List.of(
                "- Friendly Fire: " + (gang.isFriendlyFire() ? "Enabled" : "Disabled"),
                "- Accepting Invites: " + (gang.isAcceptingInvites() ? "Yes" : "No"),
                "- Can Withdraw From Bank: " + (gang.canMembersWithdrawFomBank() ? "Yes" : "No")
                ), (p, t) -> {
            if (!gang.getOwner().equals(p.getUniqueId())) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', PrisonGang.PREFIX + "Only the gang owner can do this action!"));
                return;
            }
            openGangSettings(p, fromCommand);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
        /*
        settings -
        chest
        members
        stats
        bank
         */
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
        PrisonGang gang = PrisonGang.getGang(player);
        if (!gang.getOwner().equals(player.getUniqueId())) {
            openYourGang(player, fromCommand);
            return;
        }
        GUICreator c = new GUICreator(27, "Gang Settings");
        c.setItem(10, c.createButton(Material.DIAMOND_SWORD, (gang.isFriendlyFire() ? "&cDisable" : "&aEnable") + " Friendly Fire", List.of("When friendly fire is disabled", "members of the same gang cannot", "deal damage to each other!"), (p, t) -> {
            gang.setFriendlyFire(!gang.isFriendlyFire());
            openGangSettings(p, fromCommand);
        }));
        c.setItem(13, c.createButton(Material.WRITABLE_BOOK, (gang.isAcceptingInvites() ? "&cDisable" : "&aEnable") + " Gang Invites", List.of("When enabling invites, gang", "members can invite other", "players to your gang."), (p, t) -> {
            gang.setAcceptingInvites(!gang.isAcceptingInvites());
            openGangSettings(p, fromCommand);
        }));
        c.setItem(16, c.createButton(Material.MAP, (gang.canMembersWithdrawFomBank() ? "&cDisallow" : "&aAllow") + " Members To Withdraw From The Gang Bank", List.of("When this is allowed, members", "can withdraw money/tokens from", "your gang's bank. Anyone can deposit", "currency at anytime regardless of this setting."), (p, t) -> {
            gang.setCanMembersWithdrawFomBank(!gang.canMembersWithdrawFomBank());
            openGangSettings(p, fromCommand);
        }));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> openYourGang(p, fromCommand));

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
