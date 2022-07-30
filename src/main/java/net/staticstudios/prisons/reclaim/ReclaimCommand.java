package net.staticstudios.prisons.reclaim;

import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReclaimCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        List<String> reclaim = ServerData.RECLAIM.getReclaim(player.getUniqueId());
        if (reclaim.isEmpty()) {
            player.sendMessage(ChatColor.RED + "You have no packages to reclaim.");
            return true;
        }
        PlayerData playerData = new PlayerData(player);
        for (String packageID : reclaim) {
            switch (packageID) {
                case "warrior" -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) playerData.setPlayerRank("warrior");
                    player.sendMessage(ChatColor.GREEN + "You have reclaimed your Warrior rank.");
                }
                case "master" -> {
                    if (!playerData.getPlayerRanks().contains("master")) playerData.setPlayerRank("master");
                    player.sendMessage(ChatColor.GREEN + "You have reclaimed your Master rank.");
                }
                case "mythic" -> {
                    if (!playerData.getPlayerRanks().contains("mythic")) playerData.setPlayerRank("mythic");
                    player.sendMessage(ChatColor.GREEN + "You have reclaimed your Mythic rank.");
                }
                case "static" -> {
                    if (!playerData.getPlayerRanks().contains("static")) playerData.setPlayerRank("static");
                    player.sendMessage(ChatColor.GREEN + "You have reclaimed your Static rank.");
                }
                case "staticp" -> {
                    if (!playerData.getPlayerRanks().contains("staticp")) playerData.setPlayerRank("staticp");
                    player.sendMessage(ChatColor.GREEN + "You have reclaimed your Static+ rank.");
                }
                default -> {
                    if (packageID.startsWith("tag-")) {
                        String tagName = packageID.split("tag-")[1];
                        String tagDisplay = ChatTags.getFromID(tagName);
                        playerData.addChatTag(tagName);
                        player.sendMessage(ChatColor.GREEN + "You have reclaimed your " + tagDisplay + ChatColor.GREEN + " tag.");
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
