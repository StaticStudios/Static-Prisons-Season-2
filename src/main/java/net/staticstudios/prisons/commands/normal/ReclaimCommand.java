package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReclaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        List<String> reclaims = PrisonUtils.getAllLinesInAFile("./data/reclaim.txt");
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
                PrisonUtils.writeToAFile("./data/reclaim.txt", reclaims, false);
                return false;
            }
        }
        player.sendMessage(ChatColor.RED + "You do not have a rank to reclaim.");
        return true;
    }
}
