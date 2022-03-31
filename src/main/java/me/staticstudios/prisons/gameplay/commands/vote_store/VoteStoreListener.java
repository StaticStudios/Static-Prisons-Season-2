package me.staticstudios.prisons.gameplay.commands.vote_store;

import me.staticstudios.prisons.gameplay.customItems.CustomItems;
import me.staticstudios.prisons.gameplay.customItems.Vouchers;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.data.serverData.ServerData;
import me.staticstudios.prisons.gameplay.chat.ChatTags;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class VoteStoreListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) return false;
        if (args.length < 2) return false;
        switch (args[0].toLowerCase()) {
            case "vote" -> {
                for (String key: new ServerData().getPlayerNamesToUUIDsMap().keySet()) {
                    if (key.equalsIgnoreCase(args[1])) {
                        String uuid = new ServerData().getPlayerUUIDFromName(key);
                        PlayerData playerData = new PlayerData(uuid);
                        playerData.addVotes(BigInteger.ONE);
                        playerData.setLastVotedAt(Instant.now().toEpochMilli());
                        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getVoteCrateKey(1));
                        for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " voted for the server with " + ChatColor.GREEN + "/vote");
                        player.sendMessage(ChatColor.AQUA + "You have received 1x Vote Key!");
                        VoteParty.addVoteToVoteParty();
                        return true;
                    }
                }
            }
            case "tebex" -> {
                if (args.length < 3) {
                    Bukkit.getLogger().warning("Received an incorrect Tebex request!");
                    return false;
                }
                Player player = Bukkit.getPlayer(args[2]);
                PlayerData playerData = new PlayerData(player);
                String whatWasPurchased = "";
                boolean wasRankUpgrade = false;
                switch (args[1]) {
                    case "starterPackageT1" -> {
                        whatWasPurchased = "Starter Package Tier 1";
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getStaticCrateKey(2));
                        if (!playerData.getPlayerRanks().contains("warrior")) {
                            playerData.setPlayerRank("warrior");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.WARRIOR_RANK.item);
                    }
                    case "starterPackageT2" -> {
                        whatWasPurchased = "Starter Package Tier 2";
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getStaticCrateKey(3));
                        if (playerData.getPrivateMineSquareSize() >= 70) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T3.item);
                        } else {
                            playerData.setPrivateMineSquareSize(70);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                        if (!playerData.getPlayerRanks().contains("master")) {
                            playerData.setPlayerRank("master");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.MASTER_RANK.item);
                    }
                    case "starterPackageT3" -> {
                        whatWasPurchased = "Starter Package Tier 3";
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getStaticpCrateKey(1));
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getStaticCrateKey(2));
                        if (playerData.getPrivateMineSquareSize() >= 90) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T5.item);
                        } else {
                            playerData.setPrivateMineSquareSize(90);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                        if (!playerData.getPlayerRanks().contains("mythic")) {
                            playerData.setPlayerRank("mythic");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.MYTHIC_RANK.item);
                    }
                    case "warriorPackage" -> {
                        whatWasPurchased = "Warrior Rank";
                        if (!playerData.getPlayerRanks().contains("warrior")) {
                            playerData.setPlayerRank("warrior");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.WARRIOR_RANK.item);
                    }
                    case "masterPackage" -> {
                        whatWasPurchased = "Master Rank";
                        if (!playerData.getPlayerRanks().contains("master")) {
                            playerData.setPlayerRank("master");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.MASTER_RANK.item);
                    }
                    case "mythicPackage" -> {
                        whatWasPurchased = "Mythic Rank";
                        if (!playerData.getPlayerRanks().contains("mythic")) {
                            playerData.setPlayerRank("mythic");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.MYTHIC_RANK.item);
                    }
                    case "staticPackage" -> {
                        whatWasPurchased = "Static Rank";
                        if (!playerData.getPlayerRanks().contains("static")) {
                            playerData.setPlayerRank("static");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.STATIC_RANK.item);
                    }
                    case "staticpPackage" -> {
                        whatWasPurchased = "Static+ Rank";
                        if (!playerData.getPlayerRanks().contains("staticp")) {
                            playerData.setPlayerRank("staticp");
                        } else Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.STATICP_RANK.item);
                    }
                    case "warriorToMaster" -> {
                        whatWasPurchased = "Master Rank";
                        if (playerData.getPlayerRank().equals("warrior")) {
                            playerData.setPlayerRank("master");
                            wasRankUpgrade = true;
                        }
                    }
                    case "masterToMythic" -> {
                        whatWasPurchased = "Mythic Rank";
                        if (playerData.getPlayerRank().equals("master")) {
                            playerData.setPlayerRank("mythic");
                            wasRankUpgrade = true;
                        }
                    }
                    case "mythicToStatic" -> {
                        whatWasPurchased = "Static Rank";
                        if (playerData.getPlayerRank().equals("mythic")) {
                            playerData.setPlayerRank("static");
                            wasRankUpgrade = true;
                        }
                    }
                    case "staticToStaticp" -> {
                        whatWasPurchased = "Static+ Rank";
                        if (playerData.getPlayerRank().equals("static")) {
                            playerData.setPlayerRank("staticp");
                            wasRankUpgrade = true;
                        }
                    }
                    case "legendaryKey" -> {
                        whatWasPurchased = "1x Legendary Crate Key";
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getLegendaryCrateKey(1));
                    }
                    case "staticKey" -> {
                        whatWasPurchased = "1x Static Crate Key";
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getStaticCrateKey(1));
                    }
                    case "staticpKey" -> {
                        whatWasPurchased = "1x Static+ Crate Key";
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getStaticpCrateKey(1));
                    }
                    case "privateMineTier1" -> {
                        whatWasPurchased = "Private Mine Tier 1";
                        if (playerData.getPrivateMineSquareSize() >= 50) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T1.item);
                        } else {
                            playerData.setPrivateMineSquareSize(50);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier2" -> {
                        whatWasPurchased = "Private Mine Tier 2";
                        if (playerData.getPrivateMineSquareSize() >= 60) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T2.item);
                        } else {
                            playerData.setPrivateMineSquareSize(60);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier3" -> {
                        whatWasPurchased = "Private Mine Tier 3";
                        if (playerData.getPrivateMineSquareSize() >= 70) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T3.item);
                        } else {
                            playerData.setPrivateMineSquareSize(70);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier4" -> {
                        whatWasPurchased = "Private Mine Tier 4";
                        if (playerData.getPrivateMineSquareSize() >= 80) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T4.item);
                        } else {
                            playerData.setPrivateMineSquareSize(80);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier5" -> {
                        whatWasPurchased = "Private Mine Tier 5";
                        if (playerData.getPrivateMineSquareSize() >= 90) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T5.item);
                        } else {
                            playerData.setPrivateMineSquareSize(90);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier6" -> {
                        whatWasPurchased = "Private Mine Tier 6";
                        if (playerData.getPrivateMineSquareSize() >= 100) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T6.item);
                        } else {
                            playerData.setPrivateMineSquareSize(100);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier7" -> {
                        whatWasPurchased = "Private Mine Tier 7";
                        if (playerData.getPrivateMineSquareSize() >= 110) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T7.item);
                        } else {
                            playerData.setPrivateMineSquareSize(110);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier8" -> {
                        whatWasPurchased = "Private Mine Tier 8";
                        if (playerData.getPrivateMineSquareSize() >= 120) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T8.item);
                        } else {
                            playerData.setPrivateMineSquareSize(120);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier9" -> {
                        whatWasPurchased = "Private Mine Tier 9";
                        if (playerData.getPrivateMineSquareSize() >= 130) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T9.item);
                        } else {
                            playerData.setPrivateMineSquareSize(130);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier10" -> {
                        whatWasPurchased = "Private Mine Tier 10";
                        if (playerData.getPrivateMineSquareSize() >= 140) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T10.item);
                        } else {
                            playerData.setPrivateMineSquareSize(140);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    case "privateMineTier11" -> {
                        whatWasPurchased = "Private Mine Tier 11";
                        if (playerData.getPrivateMineSquareSize() >= 150) {
                            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.PRIVATE_MINE_T11.item);
                        } else {
                            playerData.setPrivateMineSquareSize(150);
                            playerData.setPrivateMineMat(Material.STONE);
                            playerData.setHasPrivateMine(true);
                        }
                    }
                    default -> {
                        if (args[1].startsWith("tag-")) {
                            String tagName = args[1].split("tag-")[1];
                            String tagDisplay = ChatTags.getChatTagFromID(tagName);
                            if (tagDisplay.equals("")) {
                                UUID errorCode = UUID.randomUUID();
                                Bukkit.getLogger().warning("Got a Tebex package request for an invalid package! Package ID: " + args[1] + " | Error code: " + errorCode);
                                player.sendMessage(ChatColor.GRAY + "We received an invalid Tebex request for your account, please contact a staff member and provide them with a screenshot containing the code below.\n\n" + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Error code: " + errorCode);
                                List<String> errList = Utils.getAllLinesInAFile("./data/tebexErrors.txt");
                                errList.add(errorCode + " | " + Arrays.toString(args));
                                Utils.writeToAFile("./data/tebexErrors.txt", errList, false);
                                return false;
                            }
                            whatWasPurchased = tagDisplay + ChatColor.GREEN + ChatColor.BOLD + "Chat Tag";
                            playerData.addChatTag(tagName);
                        } else {
                            UUID errorCode = UUID.randomUUID();
                            Bukkit.getLogger().warning("Got a Tebex package request for an invalid package! Package ID: " + args[1] + " | Error code: " + errorCode);
                            player.sendMessage(ChatColor.GRAY + "We received an invalid Tebex request for your account, please contact a staff member and provide them with a screenshot containing the code below.\n\n" + ChatColor.DARK_GRAY + ChatColor.ITALIC + "Error code: " + errorCode);
                            List<String> errList = Utils.getAllLinesInAFile("./data/tebexErrors.txt");
                            errList.add(errorCode + " | " + Arrays.toString(args));
                            Utils.writeToAFile("./data/tebexErrors.txt", errList, false);
                            return false;
                        }
                    }
                }
                boolean logToFile = true;
                if (args.length > 3) {
                    if (args[3].equals("--l")) logToFile = false; //--l is a flag that means do not log to file
                }
                if (logToFile) {
                    List<String> packages = new ArrayList<>();
                    packages.add(player.getName() + " | " + player.getUniqueId() + " | " + args[1] + " | " + Arrays.toString(args));
                    Utils.writeToAFile("./data/tebexPurchases.txt", packages, true);
                }
                List<String> lines = Utils.getAllLinesInAFile("./data/nextReclaim.txt");
                boolean done = false;
                if (wasRankUpgrade) for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.split(" \\|\\?\\? ")[0].equals(playerData.getUUID())) {
                        //check if a rank was upgraded
                        String oldRank = line.split(" \\|\\?\\? ")[2];
                        switch (oldRank) {
                            case "warrior" -> {
                                if (args[1].equals("warriorToMaster")) {
                                    lines.set(i, line.replaceAll(oldRank, "master"));
                                    done = true;
                                }
                            }
                            case "master" -> {
                                if (args[1].equals("masterToMythic")) {
                                    lines.set(i, line.replaceAll(oldRank, "mythic"));
                                    done = true;
                                }
                            }
                            case "mythic" -> {
                                if (args[1].equals("mythicToStatic")) {
                                    lines.set(i, line.replaceAll(oldRank, "static"));
                                    done = true;
                                }
                            }
                            case "static" -> {
                                if (args[1].equals("staticToStaticp")) {
                                    lines.set(i, line.replaceAll(oldRank, "staticp"));
                                    done = true;
                                }
                            }
                        }
                        if (done) break;
                    }
                }
                if (!done && wasRankUpgrade) {
                    lines.add(playerData.getUUID() + " |?? " + new ServerData().getPlayerNameFromUUID(playerData.getUUID()) + " |?? " + args[1].split("To")[1].toLowerCase());
                }
                if (!wasRankUpgrade) {
                    boolean log;
                    switch (args[1]) {
                        case "warriorPackage", "masterPackage", "mythicPackage", "staticPackage", "staticpPackage" -> log = true;
                        default -> log = args[1].startsWith("tag-");
                    }
                    if (log) lines.add(playerData.getUUID() + " |?? " + new ServerData().getPlayerNameFromUUID(playerData.getUUID()) + " |?? " + args[1].replaceAll("Package", ""));
                }
                Utils.writeToAFile("./data/nextReclaim.txt", lines, false);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName(), ChatColor.GRAY + "Purchased: " + ChatColor.GREEN + ChatColor.BOLD + whatWasPurchased, 5, 60, 5);
                    p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.WHITE + " purchased " + ChatColor.GREEN + whatWasPurchased);
                }
            }
        }
        return false;
    }
}
