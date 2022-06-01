package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class StatsMenus extends GUIUtils {

    public static void viewStats(Player player, UUID uuidOfPlayerToView) {
        GUICreator c = new GUICreator(9, ServerData.PLAYERS.getName(uuidOfPlayerToView) + "'s Stats");
        PlayerData playerData = new PlayerData(uuidOfPlayerToView);
        String playerRank = "Member";
        switch (playerData.getPlayerRank()) {
            case "warrior" -> playerRank = "Warrior";
            case "master" -> playerRank = "Master";
            case "mythic" -> playerRank = "Mythic";
            case "static" -> playerRank = "Static";
            case "staticp" -> playerRank = "Static+";
        }
        c.setItems(
                c.createButton(Material.NETHER_STAR, "&bPlayer Rank:&f " + playerRank, List.of()),
                c.createButton(Material.COBBLESTONE, "&bRaw Blocks Mined:&f " + PrisonUtils.addCommasToNumber(playerData.getRawBlocksMined()), List.of()),
                c.createButton(Material.STONE, "&bBlocks Mined:&f " + PrisonUtils.addCommasToNumber(playerData.getBlocksMined()), List.of()),
                c.createButton(Material.PAPER, "&bBalance:&f $" + PrisonUtils.addCommasToNumber(playerData.getMoney()), List.of()),
                c.createButton(Material.SUNFLOWER, "&bTokens:&f " + PrisonUtils.addCommasToNumber(playerData.getTokens()), List.of()),
                c.createButton(Material.AMETHYST_SHARD, "&bPrestige:&f " + PrisonUtils.addCommasToNumber(playerData.getPrestige()), List.of()),
                c.createButton(Material.CLOCK, "&bTime Played:&f " + PrisonUtils.formatTime(playerData.getTimePlayed().longValue()), List.of()),
                c.createButton(Material.DIAMOND, "&bVotes:&f " + PrisonUtils.addCommasToNumber(playerData.getVotes()), List.of()),
                c.createButton(Material.BLUE_DYE, "&bDiscord:&f " + playerData.getDiscordName(), List.of())
        );
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }
}
