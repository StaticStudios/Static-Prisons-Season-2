package net.staticstudios.prisons.gangs;

import net.staticstudios.gui.legacy.GUICreator;
import net.staticstudios.gui.legacy.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GangMenus extends GUIUtils {

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
        c.setItem(11, createLightBluePlaceHolder());
        c.setItem(13, c.createButton(Material.STONE_SWORD, "&eCreate a Prison Gang", List.of(), (p, t) -> {
            Gang.createGang(p.getUniqueId(), p.getName() + "'s Gang");
            p.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You created a Gang! Invite your friends to take part in team based activities! &7&o/gang invite <player>"));
            openYourGang(p, fromCommand);
        }));
        c.setItem(15, c.createButton(Material.ENCHANTED_BOOK, "&d&lGang Invites", List.of("View your pending gang invites"), (p, t) -> {
            openGangInvites(p, fromCommand);
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
            openGangStats(p, fromCommand);
        }));
        c.setItem(11, createLightGrayPlaceHolder());
        c.setItem(12, c.createButtonOfPlayerSkull(player, "&9&lGang Members", List.of("- Kick someone", "- Transfer ownership"), (p, t) -> {
            openGangMembers(p, fromCommand);
        }));
        c.setItem(13, createLightGrayPlaceHolder());
        c.setItem(14, c.createButton(Material.MAP, "&a&lGang Bank", List.of("- Deposit money or tokens", "- Withdraw money or tokens"), (p, t) -> {
            openGangBank(p, fromCommand);
        }));
        c.setItem(15, createLightGrayPlaceHolder());
        c.setItem(16, c.createButton(Material.CHEST, "&6&lGang Chest", List.of("View a shared chest for your gang"), (p, t) -> {
            Gang _gang = Gang.getGang(p);
            if (_gang == null) {
                openCreateGang(p, fromCommand);
                return;
            }
            p.openInventory(_gang.getGangChest().getInventory());
        }));
        c.setItem(19, createLightGrayPlaceHolder());
        c.setItem(20, createLightGrayPlaceHolder());
        c.setItem(21, createLightGrayPlaceHolder());
        c.setItem(22, createLightGrayPlaceHolder());
        c.setItem(23, createLightGrayPlaceHolder());
        c.setItem(24, createLightGrayPlaceHolder());
        c.setItem(25, createLightGrayPlaceHolder());
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
        c.setItem(16, c.createButton(Material.MAP, (gang.canMembersWithdrawFomBank() ? "&cDisallow" : "&aAllow") + " Members To Withdraw From Bank", List.of("When this is allowed, members", "can withdraw money/tokens from", "your gang's bank. Anyone can deposit", "currency at anytime regardless of this setting."), (p, t) -> {
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
        Gang gang = Gang.getGang(player);
        GUICreator c = new GUICreator(9, "Your Gang Members");
        UUID owner = gang.getOwner();
        c.setItem(2, c.createButtonOfPlayerSkull(Bukkit.getOfflinePlayer(owner), "&8[&c&lOwner&8] &b" + ServerData.PLAYERS.getName(owner), List.of()));
        int i = 3;
        List<String> lore = new ArrayList<>();
        if (player.getUniqueId().equals(owner)) lore.add("&7&oClick to manage this player!");
        for (UUID member : gang.getMembers()) {
            if (member.equals(owner)) continue;
            c.setItem(i, c.createButtonOfPlayerSkull(Bukkit.getOfflinePlayer(member), "&8[&9Member&8] &b" + ServerData.PLAYERS.getName(member), lore, (p, t) -> {
                if (!gang.getOwner().equals(p.getUniqueId())) {
                    p.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cOnly the gang owner can do this action!"));
                    return;
                }
                openGangManageMember(member, p, fromCommand);
            }));
            i++;
        }
        while (i < 7) {
            c.setItem(i, createLightGrayPlaceHolder());
            i++;
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            openYourGang(p, fromCommand);
        });
    }
    public static void openGangManageMember(UUID member, Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
        Gang gang = Gang.getGang(player);
        GUICreator c = new GUICreator(27, "Manage " + ServerData.PLAYERS.getName(member));
        c.setItem(11, c.createButton(Material.RED_STAINED_GLASS, "&cKick " + ServerData.PLAYERS.getName(member), List.of(
                "Remove this member from your gang",
                "They will be able to join back if",
                "they are re-invited!"
        ), (p, t) -> {
            gang.removeMember(member);
            gang.messageAllMembers(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&c" + ServerData.PLAYERS.getName(member) + "&f was kicked from your gang!"));
            if (Bukkit.getPlayer(member) != null) Bukkit.getPlayer(member).sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You were kicked from &c" + gang.getName()));
            openGangMembers(p, fromCommand);
        }));
        c.setItem(12, createLightGrayPlaceHolder());
        c.setItem(13, c.createButtonOfPlayerSkull(Bukkit.getOfflinePlayer(member), "&8[&9Member&8] &b" + ServerData.PLAYERS.getName(member), List.of()));
        c.setItem(14, createLightGrayPlaceHolder());
        c.setItem(15, c.createButton(Material.DIAMOND_HELMET, "&bTransfer Ownership To " + ServerData.PLAYERS.getName(member), List.of(
                "Transfer ownership of this gang to",
                "this member! You will no longer be",
                "able to manage this gang!"
        ), (p, t) -> {
            gang.setOwner(member);
            gang.messageAllMembers(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&b" + ServerData.PLAYERS.getName(member) + "&f is now the owner of your gang!"));
            if (Bukkit.getPlayer(member) != null) Bukkit.getPlayer(member).sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You are now the owner of &b" + gang.getName()));
            p.closeInventory();
        }));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            openGangMembers(p, fromCommand);
        });
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
        Gang gang = Gang.getGang(player);
        GUICreator c = new GUICreator(27, "Your Gang's Bank");
        c.setItem(11, ench(c.createButton(Material.PAPER, "&a&lMoney ($" + PrisonUtils.prettyNum(gang.getBankMoney()) + ")", List.of("Deposit or withdraw money from your gang's bank."), (p, t) -> {
            openGangBankMoney(p, fromCommand);
        })));
        c.setItem(15, ench(c.createButton(Material.SUNFLOWER, "&e&lTokens (" + PrisonUtils.prettyNum(gang.getBankTokens()) + " Tokens)", List.of("Deposit or withdraw tokens from your gang's bank."), (p, t) -> {
            openGangBankTokens(p, fromCommand);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> openYourGang(p, fromCommand));
    }
    public static void openGangBankMoney(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
        Gang gang = Gang.getGang(player);
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(27, "Gang Bank | Money");
        c.setItem(11, ench(c.createButton(Material.LIME_STAINED_GLASS, "&a&lDeposit All ($" + PrisonUtils.prettyNum(playerData.getMoney()) + ")", List.of("Deposit all of your money"), (p, t) -> {
            gang.addBankMoney(playerData.getMoney());
            playerData.setMoney(0);
            openGangBankMoney(p, fromCommand);
        })));
        c.setItem(13, ench(c.createButton(Material.MAP, "&b&lYour Gang's Bank", List.of("&bMoney: &f$" + PrisonUtils.prettyNum(gang.getBankMoney()), "&bTokens: &f" + PrisonUtils.prettyNum(gang.getBankTokens())))));
        c.setItem(15, ench(c.createButton(Material.RED_STAINED_GLASS, "&c&lWithdraw All ($" + PrisonUtils.prettyNum(gang.getBankMoney()) + ")", List.of("Withdraw all your gang bank's money"), (p, t) -> {
            if (!gang.canMembersWithdrawFomBank() && !gang.getOwner().equals(p.getUniqueId())) {
                p.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You can't withdraw from the bank as the owner has disabled it for your gang!"));
                return;
            }
            playerData.addMoney(gang.getBankMoney());
            gang.removeBankMoney(gang.getBankMoney());
            openGangBankMoney(p, fromCommand);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> openGangBank(p, fromCommand));
    }
    public static void openGangBankTokens(Player player, boolean fromCommand) {
        if (!Gang.hasGang(player)) {
            openCreateGang(player, fromCommand);
            return;
        }
        Gang gang = Gang.getGang(player);
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(27, "Gang Bank | Tokens");
        c.setItem(11, ench(c.createButton(Material.LIME_STAINED_GLASS, "&a&lDeposit All (" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens)", List.of("Deposit all of your tokens"), (p, t) -> {
            gang.addBankTokens(playerData.getTokens());
            playerData.setTokens(0);
            openGangBankTokens(p, fromCommand);
        })));
        c.setItem(13, ench(c.createButton(Material.MAP, "&b&lYour Gang's Bank", List.of("&bMoney: &f$" + PrisonUtils.prettyNum(gang.getBankMoney()), "&bTokens: &f" + PrisonUtils.prettyNum(gang.getBankTokens())))));
        c.setItem(15, ench(c.createButton(Material.RED_STAINED_GLASS, "&c&lWithdraw All (" + PrisonUtils.prettyNum(gang.getBankTokens()) + " Tokens)", List.of("Withdraw all your gang bank's tokens"), (p, t) -> {
            if (!gang.canMembersWithdrawFomBank() && !gang.getOwner().equals(p.getUniqueId())) {
                p.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You can't withdraw from the bank as the owner has disabled it for your gang!"));
                return;
            }
            playerData.addTokens(gang.getBankTokens());
            gang.removeBankTokens(gang.getBankTokens());
            openGangBankTokens(p, fromCommand);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> openGangBank(p, fromCommand));
    }



}
