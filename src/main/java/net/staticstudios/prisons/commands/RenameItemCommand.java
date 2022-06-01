package net.staticstudios.prisons.commands;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Utils.CommandUtils.getIncorrectCommandUsageMessage("/renameitem <name>"));
            return false;
        }
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            player.sendMessage(ChatColor.RED + "You cannot name this item.");
            return false;
        }
        String name = String.join(" ", args);
        name = ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', name);
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        player.sendMessage(ChatColor.GREEN + "Done!");
        return false;
    }
}
