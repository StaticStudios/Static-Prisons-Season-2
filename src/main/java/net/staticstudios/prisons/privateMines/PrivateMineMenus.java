package net.staticstudios.prisons.privateMines;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIRunnable;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.auctionHouse.AuctionManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.*;

public class PrivateMineMenus extends GUIUtils {
    public static void open(Player player, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        PlayerData playerData = new PlayerData(player);
        if (playerData.getPlayerLevel() < 5) {
            if (fromCommand) cannotView(player, null);
            else cannotView(player, (p, t) -> MainMenus.open(p));
            return;
        }
        GUICreator c = new GUICreator(27, "Private Mines");
        c.setItem(11, c.createButton(Material.NETHER_STAR, "&b&lPublic Mines", List.of("View mines that have", "been opened to the public."), (p, t) -> {
            publicMines(p, 0, fromCommand);
        }));
        if (playerData.getPlayerLevel() < 10) {
            c.setItem(13, ench(c.createButtonOfPlayerSkull(player, "&a&lYour Mine", List.of("Manage your private mine.", "", "&cYou must be level 10 before you can access this!"))));
        } else {
            c.setItem(13, c.createButtonOfPlayerSkull(player, "&a&lYour Mine", List.of("Manage your private mine."), (p, t) -> {
                manageMine(p, fromCommand);
            }));
        }
        c.setItem(15, c.createButton(Material.ENCHANTED_BOOK, "&d&lInvited Mines", List.of("View mines that you have", "been invited to by other players."), (p, t) -> {
            myInvites(p, 0, fromCommand);
        }));
        if (!fromCommand) MainMenus.open(player);
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
    }

    public static void cannotView(Player player, GUIRunnable onClose) {
        if (!PrivateMine.finishedInitTasks) return;
        GUICreator c = new GUICreator(27, "Private Mines");
        c.setItem(11, c.createButton(Material.NETHER_STAR, "&b&lPublic Mines", List.of("View private mines that have", "been opened to the public.", "", "&cYou must be level 5 before you can access this!")));
        c.setItem(13, ench(c.createButtonOfPlayerSkull(player, "&a&lYour Mine", List.of("Manage your private mine.", "", "&cYou must be level 10 before you can access this!"))));
        c.setItem(15, c.createButton(Material.ENCHANTED_BOOK, "&d&lInvited Mines", List.of("View mines that you have", "been invited to by other players.", "", "&cYou must be level 5 before you can access this!")));
        c.fill(createGrayPlaceHolder());
        c.setOnCloseRun(onClose);
        c.open(player);
    }
    public static void manageMine(Player player, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        GUICreator c = new GUICreator(27, "Your Private Mine");
        PlayerData playerData = new PlayerData(player);
        if (!PrivateMine.playerHasPrivateMine(player)) {
            c.setItem(13, ench(c.createButton(Material.GOLDEN_PICKAXE, "&a&lCreate Mine", List.of("Create a new private mine."), (p, t) -> {
                p.closeInventory();
                PrivateMine.createPrivateMine(p).thenAccept(pm -> pm.warpTo(p));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCreating your private mine..."));
            })));
        } else {
            PrivateMine unloadedPrivateMine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);
            c.setItem(11, ench(c.createButton(Material.COMPASS, "&b&lWarp to Mine", List.of("Warp to your private mine"), (p, t) -> {
                p.closeInventory();
                if (!PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).isLoaded) p.sendMessage(ChatColor.AQUA + "Loading your private mine...");
                PrivateMine.getPrivateMineFromPlayer(p).thenAccept(pm -> pm.warpTo(p));
            })));
            c.setItem(12, ench(c.createButton(Material.AMETHYST_SHARD, "&d&lRefill Mine", List.of("Refill your private mine (/pmine refill)", "", "&c&oThis action can only be done once every 30 seconds!"), (p, t) -> {
                p.closeInventory();
                if (!PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).isLoaded) {
                    p.sendMessage(ChatColor.AQUA + "Loading your private mine...");
                    PrivateMine.getPrivateMineFromPlayer(p).thenAccept(pm -> pm.warpTo(p));
                } else PrivateMine.getPrivateMineFromPlayer(p).thenAccept(pm -> pm.manualRefill(p));
            })));
            c.setItem(13, ench(c.createButton(Material.REDSTONE_TORCH, "&c&lSettings", List.of("- Make public/private", "- Raise/lower tax"), (p, t) -> {
                settings(p, fromCommand);
            })));
            c.setItem(14, ench(c.createButton(Material.WRITABLE_BOOK, ChatColor.YELLOW + "" + ChatColor.BOLD + unloadedPrivateMine.name, unloadedPrivateMine.buildInfoAsList(), (p, t) -> {
                PrivateMine.getPrivateMineFromPlayerWithoutLoading(p).sendInfo(p);
                p.closeInventory();
            })));
            c.setItem(15, ench(c.createButton(Material.ENCHANTED_BOOK, "&a&lInvited Players", List.of(
                    "Invite another player to your private mine.",
                    "Players that you have invited will be able to",
                    "use your private mine even when it isn't public.",
                    "",
                    "&cInvited players do not pay any taxes."
            ), (p, t) -> {
                whitelist(p, fromCommand);
            })));
        }
        c.fill(createGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> open(p, fromCommand));
        c.open(player);
    }

    public static void whitelist(Player player, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        GUICreator c = new GUICreator(54, "Invited Players");
        PrivateMine privateMine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);
        for (UUID member : privateMine.getWhitelist()) {
            c.addItem(c.createButtonOfPlayerSkull(Bukkit.getOfflinePlayer(member).getPlayer(), ServerData.PLAYERS.getName(member), List.of("This player has been invited to your mine", "", "&c&oClick to revoke their invitation"), (p, t) -> {
                privateMine.removeFromWhitelist(member);
                Player target = Bukkit.getPlayer(member);
                if (!privateMine.isPublic && privateMine.isLoaded && target != null) {
                    if (privateMine.getAllPlayersInPrivateMine().contains(target)) {
                        Warps.warpToSpawn(target); //preform a check to see if this player is in this private mine, if so, tp them to spawn and tell them they have been un-invited, otherwise revoke the invite silently
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have been un-invited from this private mine"));
                    }
                }
                whitelist(p, fromCommand);
            }));
        }
        c.fill(createLightGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> manageMine(p, fromCommand));
        c.open(player);
    }

    public static void myInvites(Player player, int page, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        int startIndex = page * MINES_PER_PAGE;
        GUICreator c = new GUICreator(54, "Your Invites");
        List<PrivateMine> mines = new ArrayList<>(PrivateMineManager.INVITED_MINES.getOrDefault(player.getUniqueId(), new HashSet<>()));
        mines = mines.stream().sorted(Comparator.comparingInt(PrivateMine::getLevel)).toList(); //Sort mines by level

        for (int i = 0; i < MINES_PER_PAGE; i++) {
            if (mines.size() - 1 < startIndex + i) break;
            PrivateMine privateMine = mines.get(startIndex + i);
            c.setItem(i, c.createButtonOfPlayerSkull(Bukkit.getOfflinePlayer(privateMine.owner), ChatColor.YELLOW + "" + ChatColor.BOLD + privateMine.name, privateMine.buildInfoAsList(), (p, t) -> {
                viewMine(p, privateMine, page, fromCommand);
            }));
        }
        c.setItem(45, createGrayPlaceHolder());
        c.setItem(46, createGrayPlaceHolder());
        c.setItem(47, createGrayPlaceHolder());
        if (page > 0) {
            c.setItem(48, c.createButton(Material.ARROW, "&aPrevious Page", List.of(), (p, t) -> {
                myInvites(p, page - 1, fromCommand);
            }));
        } else c.setItem(48, createGrayPlaceHolder());
        c.setItem(49, c.createButton(Material.PAPER, "&bCurrent Page: &f" + (page + 1), List.of()));
        if (AuctionManager.auctions.size() - 1 > (page + 1) * MINES_PER_PAGE) {
            c.setItem(50, c.createButton(Material.ARROW, "&aNext Page", List.of(), (p, t) -> {
                myInvites(p, page + 1, fromCommand);
            }));
        } else c.setItem(50, createGrayPlaceHolder());
        c.setItem(51, createGrayPlaceHolder());
        c.setItem(52, createGrayPlaceHolder());
        c.setItem(53, createGrayPlaceHolder());
        c.fill(createLightGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> open(p, fromCommand));
        c.open(player);
    }

    public static void settings(Player player, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        GUICreator c = new GUICreator(27, "Private Mine Settings");
        PrivateMine privateMine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);

        c.setItem(11, c.createButton(Material.RED_TERRACOTTA, "&c&lLower Tax (By 1%)", List.of("&6Current Tax: &f" + new DecimalFormat("0").format(privateMine.visitorTax * 100) + "%", "Lower the tax that others will", "pay when mining in your mine."), (p, t) -> {
            privateMine.visitorTax = Math.max(0, privateMine.visitorTax - 0.01);
            settings(p, fromCommand);
        }));

        if (privateMine.isPublic) {
            c.setItem(13, c.createButton(Material.RED_DYE, "&c&lMake Private", List.of("Making this mine private will not allow", "visitors to warp here and mine."), (p, t) -> {
                privateMine.isPublic = false;
                for (Player p1 : privateMine.getAllPlayersInPrivateMine()) {
                    p1.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis private mine was made private"));
                    Warps.warpToSpawn(p1);
                }
                settings(p, fromCommand);
            }));
        } else {
            c.setItem(13, c.createButton(Material.LIME_DYE, "&a&lMake Public", List.of("Making this mine public will allow", "visitors to warp here and mine."), (p, t) -> {
                privateMine.isPublic = true;
                settings(p, fromCommand);
            }));
        }

        c.setItem(15, c.createButton(Material.GREEN_TERRACOTTA, "&a&lRaise Tax (By 1%)", List.of("&6Current Tax: &f" + new DecimalFormat("0").format(privateMine.visitorTax * 100) + "%", "Raise the tax that others will", "pay when mining in your mine."), (p, t) -> {
            privateMine.visitorTax = Math.min(0.25, privateMine.visitorTax + 0.01);
            settings(p, fromCommand);
        }));

        c.fill(createGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> manageMine(p, fromCommand));
        c.open(player);

    }

    public static final int MINES_PER_PAGE = 45;
    public static void publicMines(Player player, int page, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        int startIndex = page * MINES_PER_PAGE;
        GUICreator c = new GUICreator(54, "Public Private Mines (Page " + (page + 1) + ")");
        List<PrivateMine> mines = new ArrayList<>();
        List<Set<PrivateMine>> orderedMines = new ArrayList<>(PrivateMine.PRIVATE_MINES_SORTED_BY_LEVEL.values());
        for (Set<PrivateMine> set : orderedMines) {
            mines.addAll(set);
        }
        mines = PrisonUtils.removeDuplicatesInArrayList(mines);
        Collections.reverse(mines);
        List<PrivateMine> publicMines = new ArrayList<>();
        for (PrivateMine mine : mines) if (mine.isPublic) publicMines.add(mine);


        for (int i = 0; i < MINES_PER_PAGE; i++) {
            if (publicMines.size() - 1 < startIndex + i) break;
            PrivateMine privateMine = publicMines.get(startIndex + i);

            List<String> lore = new ArrayList<>(privateMine.buildInfoAsList());
            lore.add("");
            lore.add("Click to warp to this mine");
            c.setItem(i, c.createButtonOfPlayerSkull(Bukkit.getOfflinePlayer(privateMine.owner), ChatColor.YELLOW + "" + ChatColor.BOLD + privateMine.name, lore, (p, t) -> {
                viewMine(p, privateMine, page, fromCommand);
            }));
        }
        c.setItem(45, createGrayPlaceHolder());
        c.setItem(46, createGrayPlaceHolder());
        c.setItem(47, createGrayPlaceHolder());
        if (page > 0) {
            c.setItem(48, c.createButton(Material.ARROW, "&aPrevious Page", List.of(), (p, t) -> {
                publicMines(p, page - 1, fromCommand);
            }));
        } else c.setItem(48, createGrayPlaceHolder());
        c.setItem(49, c.createButton(Material.PAPER, "&bCurrent Page: &f" + (page + 1), List.of()));
        if (AuctionManager.auctions.size() - 1 > (page + 1) * MINES_PER_PAGE) {
            c.setItem(50, c.createButton(Material.ARROW, "&aNext Page", List.of(), (p, t) -> {
                publicMines(p, page + 1, fromCommand);
            }));
        } else c.setItem(50, createGrayPlaceHolder());
        c.setItem(51, createGrayPlaceHolder());
        c.setItem(52, createGrayPlaceHolder());
        c.setItem(53, createGrayPlaceHolder());
        c.fill(createLightGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> open(p, fromCommand));
        c.open(player);
    }

    public static void viewMine(Player player, PrivateMine privateMine, int page, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        GUICreator c = new GUICreator(27, privateMine.name);
        c.setItem(11, ench(c.createButton(Material.COMPASS, "&a&lWarp to Mine", List.of("Warp to " + privateMine.name), (p, t) -> {
            p.closeInventory();
            if (!privateMine.isPublic && !privateMine.getWhitelist().contains(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "This mine is no longer public.");
                return;
            }
            if (!privateMine.isLoaded) {
                p.sendMessage(ChatColor.AQUA + "Loading " + privateMine.name + "...");
                PrivateMine.getPrivateMine(privateMine.privateMineId).thenAccept(pm -> pm.warpTo(p));
            } else privateMine.warpTo(p);
        })));
        c.setItem(13, c.createButtonOfPlayerSkull(Bukkit.getOfflinePlayer(privateMine.owner), ChatColor.YELLOW + "" + ChatColor.BOLD + privateMine.name, privateMine.buildInfoAsList(), (p, t) -> privateMine.sendInfo(p)));

        c.setItem(15, c.createButton(Material.ENCHANTED_BOOK, "&a&lWhat Are Public Private-Mines?", List.of(
                "Private mines can be created by",
                "any player who has reached level 10.",
                "",
                "Players can open private mines to",
                "the public and allow others to mine",
                "at their mine. Private mine owners",
                "can set a tax for visitors so that",
                "when someone else uses their mine,",
                "a percentage of the blocks' value",
                "will be given to the mine owner.",
                "",
                "Private mines can sometime have a different",
                "sell percentage, this means that when",
                "a block is mined in this private mine,",
                "it will only be sold for " + new DecimalFormat("0.0").format(privateMine.sellPercentage * 100) + "% of",
                "its value (before the owner's tax)."
        )));
        c.setOnCloseRun((p, t) -> publicMines(player, page, fromCommand));
        c.fill(createGrayPlaceHolder());
        c.open(player);
    }
    public static void upgrade(Player player, boolean fromCommand) {
        if (!PrivateMine.finishedInitTasks) return;
        PrivateMine privateMine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);
        GUICreator c = new GUICreator(27, "Upgrade");
        if (privateMine.getAvailableUpgrades().isEmpty()) {
            c.setItem(13, c.createButton(Material.NETHER_STAR, "&c&lNo Upgrades Available", List.of("This mine cannot be upgraded"), (p, t) -> {
                PrivateMine.sendMessage(p, "&cYour private mine does not have any upgrades available.");
            }));
        } else {
            PrivateMineStats upgrade = PrivateMineConfigManager.STATS_PER_LEVEL[privateMine.getAvailableUpgrades().get(0)];
            c.setItem(13, c.createButton(Material.NETHER_STAR, "&d&l" + privateMine.getAvailableUpgrades().size() + " Upgrade(s) Available", List.of(
                    "&7&oClick to upgrade your private mine!",
                    "",
                    "&aCosts: &f$" + PrisonUtils.addCommasToNumber(upgrade.getUpgradeCost())
            ), (p, t) -> {
                privateMine.upgrade();
                upgrade(p, fromCommand);
            }));
        }
        c.fill(createGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> manageMine(p, fromCommand));
        c.open(player);
    } //todo use the prefix
}
