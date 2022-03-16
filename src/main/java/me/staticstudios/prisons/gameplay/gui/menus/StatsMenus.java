package me.staticstudios.prisons.gameplay.gui.menus;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.data.serverData.ServerData;
import me.staticstudios.prisons.external.discord_old.LinkHandler;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.gui.GUIPage;
import me.staticstudios.prisons.utils.Utils;
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
                if (!new ServerData().checkIfPlayerHasJoinedBeforeByName(args)) {
                    guiTitle = "Player not found";
                    return;
                }
                PlayerData playerData = new PlayerData(new ServerData().getPlayerUUIDFromName(args));
                String discordName = "not linked";
                if (LinkHandler.checkIfLinkedFromUUID(playerData.getUUID())) discordName = playerData.getDiscordAccountName();
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
                menuItems.add(GUI.createPlaceholderItem(Material.COBBLESTONE, ChatColor.AQUA + "Raw Blocks Mined: " + ChatColor.WHITE + Utils.prettyNum(playerData.getRawBlocksMined()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getRawBlocksMined())));
                menuItems.add(GUI.createPlaceholderItem(Material.STONE, ChatColor.AQUA + "Blocks Mined: " + ChatColor.WHITE + Utils.prettyNum(playerData.getBlocksMined()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBlocksMined())));
                menuItems.add(GUI.createPlaceholderItem(Material.PAPER, ChatColor.AQUA + "Current Balance: " + ChatColor.WHITE + Utils.prettyNum(playerData.getMoney()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getMoney())));
                menuItems.add(GUI.createPlaceholderItem(Material.SUNFLOWER, ChatColor.AQUA + "Current Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getTokens())));
                menuItems.add(GUI.createPlaceholderItem(Material.AMETHYST_SHARD, ChatColor.AQUA + "Current Prestige: " + ChatColor.WHITE + Utils.prettyNum(playerData.getPrestige()), ChatColor.AQUA + "Actual value: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getPrestige())));
                menuItems.add(GUI.createPlaceholderItem(Material.CLOCK, ChatColor.AQUA + "Time Played: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTimePlayed().divide(BigInteger.valueOf(3600))) + " hour(s)", ChatColor.AQUA + "Seconds Played: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getTimePlayed())));
                menuItems.add(GUI.createPlaceholderItem(Material.DIAMOND, ChatColor.AQUA + "Votes: " + ChatColor.WHITE + Utils.prettyNum(playerData.getVotes())));
                menuItems.add(GUI.createPlaceholderItem(Material.BLUE_DYE, ChatColor.AQUA + "Discord Name: " + ChatColor.BLUE + discordName));
            }
        };
        guiPage.identifier = "stats";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dStats");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
