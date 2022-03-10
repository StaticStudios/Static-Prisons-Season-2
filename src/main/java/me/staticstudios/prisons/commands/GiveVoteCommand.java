package me.staticstudios.prisons.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import me.staticstudios.prisons.utils.CommandUtils;
import me.staticstudios.prisons.data.ServerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveVoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/givevote <who>"));
            return false;
        }
        if (new ServerData().getPlayerNamesToUUIDsMap().containsKey(args[0])) {
            new PlayerData(new SerevrData().getPlayerUUIDFromName(args[0])).addVOtes(BigInteger.ONE);
        } else player.sendMessage(ChatColor.RED + "Player not found!");
        return true;
    }
}
