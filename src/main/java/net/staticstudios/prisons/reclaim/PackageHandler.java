package net.staticstudios.prisons.reclaim;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class PackageHandler {

    public static void claimPackage(UUID playerUUID, String packageID, String[] extra) {
        PlayerData playerData = new PlayerData(playerUUID);
        switch (packageID) {

            //Ranks
            case "warriorPackage" -> {
                handle(playerUUID, packageID, "&b&lWarrior Rank", p -> {}, true);
                if (!playerData.getPlayerRanks().contains("warrior")) playerData.setPlayerRank("warrior");
            }
            case "masterPackage" -> {
                handle(playerUUID, packageID, "&e&lMaster Rank", p -> {}, true);
                if (!playerData.getPlayerRanks().contains("master")) playerData.setPlayerRank("master");
            }
            case "mythicPackage" -> {
                handle(playerUUID, packageID, "&c&lMythic Rank", p -> {}, true);
                if (!playerData.getPlayerRanks().contains("mythic")) playerData.setPlayerRank("mythic");
            }
            case "staticPackage" -> {
                handle(playerUUID, packageID, "&4&lStatic Rank", p -> {}, true);
                if (!playerData.getPlayerRanks().contains("static")) playerData.setPlayerRank("static");
            }
            case "staticpPackage" -> {
                handle(playerUUID, packageID, "&d&lStatic+ Rank", p -> {}, true);
                if (!playerData.getPlayerRanks().contains("staticp")) playerData.setPlayerRank("staticp");
            }

            //Rank upgrades
            case "warriorToMaster" -> {
                handle(playerUUID, packageID, "&e&lMaster Rank", p -> {}, true);
                if (playerData.getPlayerRank().equals("warrior")) playerData.setPlayerRank("master");
            }
            case "masterToMythic" -> {
                handle(playerUUID, packageID, "&c&lMythic Rank", p -> {}, true);
                if (playerData.getPlayerRank().equals("master")) playerData.setPlayerRank("mythic");
            }
            case "mythicToStatic" -> {
                handle(playerUUID, packageID, "&4&lStatic Rank", p -> {}, true);
                if (playerData.getPlayerRank().equals("mythic")) playerData.setPlayerRank("static");
            }
            case "staticToStaticp" -> {
                handle(playerUUID, packageID, "&d&lStatic+ Rank", p -> {}, true);
                if (playerData.getPlayerRank().equals("static")) playerData.setPlayerRank("staticp");
            }


            //Crate Keys
            case "legendaryKey" -> {
                handle(playerUUID, packageID, "&6&lLegendary Crate Key", p -> {
                    PrisonUtils.Players.addToInventory(p, CustomItems.getLegendaryCrateKey(1));
                }, false);
            }
            case "staticKey" -> {
                handle(playerUUID, packageID, "&a&lStatic Crate Key", p -> {
                    PrisonUtils.Players.addToInventory(p, CustomItems.getStaticCrateKey(1));
                }, false);
            }
            case "staticpKey" -> {
                handle(playerUUID, packageID, "&d&lStatic+ Crate Key", p -> {
                    PrisonUtils.Players.addToInventory(p, CustomItems.getStaticpCrateKey(1));
                }, false);
            }

            //Starter packs
            case "starterPackageT1" -> {
                handle(playerUUID, packageID, "&a&lStarter Package: Tier 1", p -> {
                    PrisonUtils.Players.addToInventory(p, CustomItems.getStaticCrateKey(2));
                }, true);
                if (!playerData.getPlayerRanks().contains("warrior")) playerData.setPlayerRank("warrior");
            }
            case "starterPackageT2" -> {
                handle(playerUUID, packageID, "&a&lStarter Package: Tier 2", p -> {
                    PrisonUtils.Players.addToInventory(p, CustomItems.getStaticCrateKey(3));
                }, true);
                if (!playerData.getPlayerRanks().contains("master")) playerData.setPlayerRank("master");
            }
            case "starterPackageT3" -> {
                handle(playerUUID, packageID, "&a&lStarter Package: Tier 3", p -> {
                    PrisonUtils.Players.addToInventory(p, CustomItems.getStaticCrateKey(2));
                    PrisonUtils.Players.addToInventory(p, CustomItems.getStaticpCrateKey(1));
                }, true);
                if (!playerData.getPlayerRanks().contains("mythic")) playerData.setPlayerRank("mythic");
            }
            default -> {
                if (packageID.startsWith("tag-")) {
                    String tagName = packageID.split("tag-")[1];
                    String tagDisplay = ChatTags.getFromID(tagName);
                    handle(playerUUID, packageID, "&a&lChat Tag: &r" + tagDisplay, p -> {}, true);
                    playerData.addChatTag(tagName);
                }
            }
        }
    }

    private static void handle(UUID playerUUID, String packageID, String prettyPackageName, Consumer<Player> runIfPlayerIsOnline, boolean reclaim) {

        prettyPackageName = ChatColor.translateAlternateColorCodes('&', prettyPackageName);

        String playerName = ServerData.PLAYERS.getName(playerUUID);
        String title = ChatColor.AQUA + "" + ChatColor.BOLD + playerName;
        String subtitle = prettyPackageName;

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showTitle(Title.title(Component.text(title), Component.text(subtitle), Title.Times.of(Duration.ofMillis(500), Duration.ofMillis(4000), Duration.ofMillis(500))));
            p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + playerName + ChatColor.WHITE + " purchased " + prettyPackageName);
        }

        if (Bukkit.getPlayer(playerUUID) != null) runIfPlayerIsOnline.accept(Bukkit.getPlayer(playerUUID));


        String logID = packageID;
        switch (packageID) {
            case "starterPackageT1" -> logID = "warrior";
            case "starterPackageT2" -> logID = "master";
            case "starterPackageT3" -> logID = "mythic";
            case "warriorToMaster" -> logID = "master";
            case "masterToMythic" -> logID = "mythic";
            case "mythicToStatic" -> logID = "static";
            case "staticToStaticp" -> logID = "staticp";
            case "warriorPackage" -> logID = "warrior";
            case "masterPackage" -> logID = "master";
            case "mythicPackage" -> logID = "mythic";
            case "staticPackage" -> logID = "static";
            case "staticpPackage" -> logID = "staticp";
        }
        if (reclaim) ServerData.RECLAIM.addNextReclaim(playerUUID, logID);
        List<String> packages = new ArrayList<>(); //log to file
        packages.add(playerName + " | " + playerUUID + " | " + packageID + " | ");
        PrisonUtils.writeToAFile("./data/tebexPurchases.txt", packages, true);
    }


}
