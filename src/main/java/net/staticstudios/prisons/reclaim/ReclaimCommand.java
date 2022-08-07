package net.staticstudios.prisons.reclaim;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReclaimCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        List<String> reclaim = ServerData.RECLAIM.getReclaim(player.getUniqueId());
        if (reclaim.isEmpty()) {
            player.sendMessage(Component.text("You have no packages to reclaim.").color(NamedTextColor.RED));
            return true;
        }
        PlayerData playerData = new PlayerData(player);
        for (String packageID : reclaim) {
            switch (packageID) {
                case "warrior" -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) playerData.setPlayerRank("warrior");
                    player.sendMessage(Component.text("You have reclaimed your ").color(NamedTextColor.GREEN)
                            .append(TeamPrefix.getFromID("warrior"))
                            .append(Component.text(" rank.").color(NamedTextColor.GREEN)));
                }
                case "master" -> {
                    if (!playerData.getPlayerRanks().contains("master")) playerData.setPlayerRank("master");
                    player.sendMessage(Component.text("You have reclaimed your ").color(NamedTextColor.GREEN)
                            .append(TeamPrefix.getFromID("master"))
                            .append(Component.text(" rank.").color(NamedTextColor.GREEN)));
                }
                case "mythic" -> {
                    if (!playerData.getPlayerRanks().contains("mythic")) playerData.setPlayerRank("mythic");
                    player.sendMessage(Component.text("You have reclaimed your ").color(NamedTextColor.GREEN)
                            .append(TeamPrefix.getFromID("mythic"))
                            .append(Component.text(" rank.").color(NamedTextColor.GREEN)));
                }
                case "static" -> {
                    if (!playerData.getPlayerRanks().contains("static")) playerData.setPlayerRank("static");
                    player.sendMessage(Component.text("You have reclaimed your ").color(NamedTextColor.GREEN)
                            .append(TeamPrefix.getFromID("static"))
                            .append(Component.text(" rank.").color(NamedTextColor.GREEN)));
                }
                case "staticp" -> {
                    if (!playerData.getPlayerRanks().contains("staticp")) playerData.setPlayerRank("staticp");
                    player.sendMessage(Component.text("You have reclaimed your ").color(NamedTextColor.GREEN)
                            .append(TeamPrefix.getFromID("staticp"))
                            .append(Component.text(" rank.").color(NamedTextColor.GREEN)));
                }
                default -> {
                    if (packageID.startsWith("tag-")) {
                        String tagName = packageID.split("tag-")[1];
                        Component tagDisplay = ChatTags.getFromID(tagName);
                        playerData.addChatTag(tagName);
                        player.sendMessage(Component.text("You have reclaimed your ").color(NamedTextColor.GREEN)
                                .append(tagDisplay)
                                .append(Component.text(" tag.").color(NamedTextColor.GREEN)));
                    }
                }
            }
        }
        ServerData.RECLAIM.setReclaim(player.getUniqueId(), new ArrayList<>());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
