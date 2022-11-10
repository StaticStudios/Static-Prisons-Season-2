package net.staticstudios.prisons.gui;

import net.kyori.adventure.text.Component;
import net.staticstudios.gui.legacy.GUIUtils;
import net.staticstudios.gui.GUIPlaceholders;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.gui.builder.ButtonBuilder;
import net.staticstudios.gui.builder.GUIBuilder;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Logger;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;

public class StatsMenus extends GUIUtils {

    public static void viewStats(Player player, UUID uuidOfPlayerToView) {
        PlayerData playerData = new PlayerData(uuidOfPlayerToView);

        Logger.getLogger("StatsMenus").info(playerData.getPlayerRank());
        Logger.getLogger("StatsMenus").info(playerData.getStaffRank());

        StaticGUI gui = GUIBuilder.builder()
                .title(text(ServerData.PLAYERS.getName(uuidOfPlayerToView) + "'s Stats"))
                .size(36)
                .onClose((p, t) -> MainMenus.open(p))
                .fillWith(GUIPlaceholders.GRAY)
                .build();


        Component playerRank = TeamPrefix.getFromIdDeserialized(playerData.getPlayerRank());

        // TODO: 02/10/2022 get away from String "null" and actually return null if not linked
        String discordName = playerData.getDiscordName();

        gui.setButton(10, ButtonBuilder.builder().icon(Material.NETHER_STAR).name(text("Player Rank: ").color(AQUA).append(playerRank)).build());
        gui.setButton(11, ButtonBuilder.builder().icon(Material.EXPERIENCE_BOTTLE).name(text("Player Level: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getPlayerLevel())).color(WHITE))).build());
        gui.setButton(12, ButtonBuilder.builder().icon(Material.PRISMARINE_SHARD).name(text("Shards: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getShards())).color(WHITE))).build());
        gui.setButton(13, ButtonBuilder.builder().icon(Material.PAPER).name(text("Balance: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getMoney())).color(WHITE))).build());
        gui.setButton(14, ButtonBuilder.builder().icon(Material.SUNFLOWER).name(text("Tokens: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getTokens())).color(WHITE))).build());
        gui.setButton(15, ButtonBuilder.builder().icon(Material.AMETHYST_SHARD).name(text("Prestige: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getPrestige())).color(WHITE))).build());
        gui.setButton(16, ButtonBuilder.builder().icon(Material.GOLDEN_HELMET).name(text("Mine Rank: ").color(AQUA).append(text(PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank())).color(WHITE))).build());

        gui.setButton(20, ButtonBuilder.builder().icon(Material.DIAMOND).name(text("Votes: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getVotes())).color(WHITE))).build());
        gui.setButton(21, ButtonBuilder.builder().icon(Material.BLUE_DYE).name(text("Discord: ").color(AQUA).append(text("null".equals(discordName) ? "Not Linked" : discordName).color(WHITE))).build());
        gui.setButton(22, ButtonBuilder.builder().icon(Material.COBBLESTONE).name(text("Raw Blocks Mined: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getBlocksMined())).color(WHITE))).build());
        gui.setButton(23, ButtonBuilder.builder().icon(Material.STONE).name(text("Blocks Mined: ").color(AQUA).append(text(PrisonUtils.addCommasToNumber(playerData.getBlocksMined())).color(WHITE))).build());
        gui.setButton(24, ButtonBuilder.builder().icon(Material.CLOCK).name(text("Time Played: ").color(AQUA).append(text(PrisonUtils.formatTime(playerData.getTimePlayed() * 1000)).color(WHITE))).build());


        gui.open(player);
    }
}
