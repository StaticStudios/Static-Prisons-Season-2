package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.data.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PlayerData playerData = new PlayerData(player);
        if (!playerData.getPlayerRanks().contains("warrior")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this!");
            return false;
        }
        if (args.length == 0) {
            playerData.setIsChatNickNameEnabled(false);
            player.sendMessage(ChatColor.GREEN + "Your nickname has been removed!");
            return false;
        }
        String newName = args[0];
        if (newName.equalsIgnoreCase("reset") || newName.equalsIgnoreCase("remove")) {
            playerData.setIsChatNickNameEnabled(false);
            player.sendMessage(ChatColor.GREEN + "Your nickname has been removed!");
            return false;
        }
        if (ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', newName)).length() > 16) {
            player.sendMessage(ChatColor.RED + "You cannot set a nickname longer than 16 characters!");
            return false;
        }
        playerData.setChatNickname(ChatColor.translateAlternateColorCodes('&', newName));
        playerData.setIsChatNickNameEnabled(true);
        player.sendMessage(ChatColor.GREEN + "Nickname successfully changed!");
        return false;
    }
}
