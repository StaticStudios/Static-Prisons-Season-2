package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.customItems.Vouchers;
import me.staticstudios.prisons.data.dataHandling.DataWriter;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.leaderboards.BlocksMinedTop;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        ItemStack i = Utils.createNewPickaxe();
        CustomEnchants.addEnchantLevel(i, "doubleWammy", 50000);
        player.getInventory().addItem(i);
        return false;
    }
}
