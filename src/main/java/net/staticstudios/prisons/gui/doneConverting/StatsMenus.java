package net.staticstudios.prisons.gui.doneConverting;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.GUIPage;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.ArrayList;

public class StatsMenus {
    //View a player's stats
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args.toLowerCase())) {
                    guiTitle = "Player not found";
                    return;
                }
                PlayerData playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args));
                guiTitle = ChatColor.LIGHT_PURPLE + args + "'s Stats";
                String rankTag = "Member";
                switch (playerData.getPlayerRank()) {
                    case "warrior" -> rankTag = "Warrior";
                    case "master" -> rankTag = "Master";
                    case "mythic" -> rankTag = "Mythic";
                    case "static" -> rankTag = "Static";
                    case "staticp" -> rankTag = "Static+";
                }
                menuItems.add(GUI.createPlaceholderItem(Material.DIAMOND, ChatColor.AQUA + "Player Rank: " + ChatColor.WHITE + rankTag));
                menuItems.add(GUI.createPlaceholderItem(Material.COBBLESTONE, ChatColor.AQUA + "Raw Blocks Mined: " + ChatColor.WHITE + PrisonUtils.prettyNum(playerData.getRawBlocksMined()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(playerData.getRawBlocksMined())));
                menuItems.add(GUI.createPlaceholderItem(Material.STONE, ChatColor.AQUA + "Blocks Mined: " + ChatColor.WHITE + PrisonUtils.prettyNum(playerData.getBlocksMined()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(playerData.getBlocksMined())));
                menuItems.add(GUI.createPlaceholderItem(Material.PAPER, ChatColor.AQUA + "Current Balance: " + ChatColor.WHITE + PrisonUtils.prettyNum(playerData.getMoney()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(playerData.getMoney())));
                menuItems.add(GUI.createPlaceholderItem(Material.SUNFLOWER, ChatColor.AQUA + "Current Tokens: " + ChatColor.WHITE + PrisonUtils.prettyNum(playerData.getTokens()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(playerData.getTokens())));
                menuItems.add(GUI.createPlaceholderItem(Material.AMETHYST_SHARD, ChatColor.AQUA + "Current Prestige: " + ChatColor.WHITE + PrisonUtils.prettyNum(playerData.getPrestige()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(playerData.getPrestige())));
                menuItems.add(GUI.createPlaceholderItem(Material.CLOCK, ChatColor.AQUA + "Time Played: " + ChatColor.WHITE + PrisonUtils.prettyNum(playerData.getTimePlayed().divide(BigInteger.valueOf(3600))) + " hour(s)", ChatColor.AQUA + "Seconds Played: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(playerData.getTimePlayed())));
                menuItems.add(GUI.createPlaceholderItem(Material.DIAMOND, ChatColor.AQUA + "Votes: " + ChatColor.WHITE + PrisonUtils.prettyNum(playerData.getVotes())));
                menuItems.add(GUI.createPlaceholderItem(Material.BLUE_DYE, ChatColor.AQUA + "Discord Name: " + ChatColor.BLUE + playerData.getDiscordName()));
            }
        };
        guiPage.identifier = "stats";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dStats");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
