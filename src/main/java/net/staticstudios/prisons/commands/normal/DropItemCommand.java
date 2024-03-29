package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.enchants.EnchantItemStackListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DropItemCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(ChatColor.RED + "You cannot drop this item!");
            return false;
        }
        EnchantItemStackListener.dropItemCommand(player);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
