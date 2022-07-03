package net.staticstudios.prisons.gangs;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.gui.newGui.MainMenus;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GangMenus extends GUIUtils { //todo: this

    public static void open(Player player, boolean fromCommand) {
        if (Gang.hasGang(player)) openYourGang(player, fromCommand);
        else openCreateGang(player, fromCommand);
    }

    public static void openCreateGang(Player player, boolean fromCommand) {
        if (Gang.hasGang(player)) {
            openYourGang(player, fromCommand);
            return;
        }
        GUICreator c = new GUICreator(27, "Create A Gang");
        c.setItem(13, c.createButton(Material.STONE_SWORD, "&eCreate a Prison Gang", List.of(), (p, t) -> {
            Gang.createGang(p.getUniqueId(), p.getName() + "'s Gang");
            p.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You created a Gang! Invite your friends to take part in team based activities! &7&o/gang invite <player>"));
            openYourGang(p, fromCommand);
        }));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
    }
    public static void openYourGang(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
        Gang gang = Gang.getGang(player);
        GUICreator c = new GUICreator(36, gang.getName());
        c.setItem(10, c.createButton(Material.DIAMOND, "&e&lGang Stats", List.of("View your gang's stats"), (p, t) -> {
            openGangStats(p, fromCommand); //todo: track stats
        }));
        c.setItem(12, c.createButtonOfPlayerSkull(player, "&9&lGang Members", List.of("- Kick someone", "- Transfer ownership"), (p, t) -> {
        }));
        c.setItem(14, c.createButton(Material.MAP, "&a&lGang Bank", List.of("- Deposit money or tokens", "- Withdraw money or tokens"), (p, t) -> {
        }));
        c.setItem(16, c.createButton(Material.CHEST, "&6&lGang Chest", List.of("View a shared chest for your gang"), (p, t) -> {
        }));
        c.setItem(31, ench(c.createButton(Material.GUNPOWDER, "&c&lGang Settings", List.of(
                "- Friendly Fire: " + (gang.isFriendlyFire() ? "Enabled" : "Disabled"),
                "- Accepting Invites: " + (gang.isAcceptingInvites() ? "Yes" : "No"),
                "- Can Withdraw From Bank: " + (gang.canMembersWithdrawFomBank() ? "Yes" : "No")
                ), (p, t) -> {
            if (!gang.getOwner().equals(p.getUniqueId())) {
                p.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cOnly the gang owner can do this action!"));
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
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
    }
    public static void openGangSettings(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
        Gang gang = Gang.getGang(player);
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
    public static void openGangInvite(Player player, GangInvite invite, boolean fromCommand) {
        if (Gang.hasGang(player)) {
            openYourGang(player, fromCommand);
            return;
        }
        GUICreator c = new GUICreator(27, "Gang Invite");

        c.setItem(11, c.createButton(Material.LIME_STAINED_GLASS, "&a&lAccept Invite", List.of("&bInvited by: &f" + invite.getSender().getName(), "", "&7&oClick to accept this invite!"), (p, t) -> {
            invite.acceptInvite();
            openYourGang(p, fromCommand);
        }));

        c.setItem(13, c.createButton(Material.ENCHANTED_BOOK, "&d&l" + invite.getWhat().getName(), List.of("&bInvited by: &f" + invite.getSender().getName())));

        c.setItem(15, c.createButton(Material.RED_STAINED_GLASS, "&c&lDecline Invite", List.of("&bInvited by: &f" + invite.getSender().getName(), "", "&7&oClick to decline this invite!"), (p, t) -> {
            invite.declineInvite();
            p.closeInventory();
        }));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            openGangInvites(p, fromCommand);
        });
    }
    public static void openGangInvites(Player player, boolean fromCommand) {
        if (Gang.hasGang(player)) {
            openYourGang(player, fromCommand);
            return;
        }
        GUICreator c = new GUICreator(54, "Your Gang Invites");
        int i = 0;
        for (GangInvite invite : GangInvite.PLAYER_INVITES.getOrDefault(player.getUniqueId(), new ArrayList<>())) {
            if (i >= 53) break;
            c.setItem(i, c.createButtonOfPlayerSkull(invite.getSender(), "&d&l" + invite.getWhat().getName(), List.of("&bInvited by &f" + invite.getSender().getName(), "", "&7&oClick to accept or decline"), (p, t) -> {
                openGangInvite(p, invite, fromCommand);
            }));
            i++;
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) openCreateGang(p, false);
        });

    }
    public static void openGangMembers(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangManageMember(UUID member, Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangStats(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
        Gang gang = Gang.getGang(player);
        GUICreator c = new GUICreator(27, "Your Gang's Stats");
        c.setItem(10, c.createButton(Material.COBBLESTONE, "&bRaw Blocks Mined: &f" + PrisonUtils.addCommasToNumber(gang.getRawBlocksMined()), List.of()));
        c.setItem(11, c.createButton(Material.STONE, "&bBlocks Mined: &f" + PrisonUtils.addCommasToNumber(gang.getBlocksMined()), List.of()));
        c.setItem(12, c.createButton(Material.CLOCK, "&bTime Played: &f" + PrisonUtils.formatTime(gang.getSecondsPlayed() * 1000), List.of()));
        c.setItem(13, c.createButton(Material.PAPER, "&bMoney Earned: &f$" + PrisonUtils.prettyNum(gang.getMoneyMade()), List.of()));
        c.setItem(14, c.createButton(Material.SUNFLOWER, "&bTokens Found: &f" + PrisonUtils.prettyNum(gang.getTokensFound()), List.of()));
        c.setItem(16, c.createButton(Material.ENCHANTED_BOOK, "&d&lHow Are Stats Tracked?", List.of(
                "Stats are tracked whenever a gang member's",
                "own stats are affected. However, players'",
                "stats prior to joining the gang have no",
                "effect. For example, time played will",
                "increase with the individual players'",
                "time played however its initial,",
                "value will be zero and will not jump up or",
                "down when a player joins or leaves the gang.")));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> openYourGang(p, fromCommand));
    }
    public static void openGangBank(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }
    public static void openGangChest(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }

    }



}
