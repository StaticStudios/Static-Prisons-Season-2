package me.staticstudios.prisons.gameplay.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RulesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&bView our &lglobal &brules: &fstatic-studios.net/rules/global"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://static-studios.net/rules/global"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&aClick here go to our rules!"))));
        player.spigot().sendMessage(message);
        return true;
    }
}
