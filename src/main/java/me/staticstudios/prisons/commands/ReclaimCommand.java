package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ReclaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        List<String> reclaims = Utils.getAllLinesInAFile("./data/reclaim.txt");
        for (int i = 0; i < reclaims.size(); i++) {
            if (reclaims.get(i).split(" \\|\\?\\? ")[0].equals(player.getUniqueId().toString())) {
                PlayerData playerData = new PlayerData(player);
                switch (reclaims.get(i).split(" \\|\\?\\? ")[2]) {
                    case "warrior" -> player.sendMessage(ChatColor.LIGHT_PURPLE + "You have just reclaimed Warrior rank!");
                    case "master" -> player.sendMessage(ChatColor.LIGHT_PURPLE + "You have just reclaimed Master rank!");
                    case "mythic" -> player.sendMessage(ChatColor.LIGHT_PURPLE + "You have just reclaimed Mythic rank!");
                    case "static" -> player.sendMessage(ChatColor.LIGHT_PURPLE + "You have just reclaimed Static rank!");
                    case "staticp" -> player.sendMessage(ChatColor.LIGHT_PURPLE + "You have just reclaimed Static+ rank!");
                }
                if (!playerData.getPlayerRanks().contains(reclaims.get(i).split(" \\|\\?\\? ")[2])) {
                    playerData.setPlayerRank(reclaims.get(i).split(" \\|\\?\\? ")[2]);
                }
                reclaims.remove(i);
                Utils.writeToAFile("./data/reclaim.txt", reclaims, false);
                return false;
            }
        }
        player.sendMessage(ChatColor.RED + "You do not have a rank to reclaim.");
        return true;
    }
}
