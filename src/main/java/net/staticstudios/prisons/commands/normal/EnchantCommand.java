package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.enchants.handler.EnchantMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        EnchantMenus.selectPickaxe(player);
        return false;
    }
}
