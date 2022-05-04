package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(ChatColor.RED + "You cannot drop this item!");
            return false;
        }
        if (Utils.checkIsPrisonPickaxe(item)) PrisonPickaxe.dumpStatsToPickaxe(item);
        player.getWorld().dropItem(player.getLocation(), item).setPickupDelay(60);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've just dropped an item"));
        item.setAmount(0);
        return true;
    }
}
