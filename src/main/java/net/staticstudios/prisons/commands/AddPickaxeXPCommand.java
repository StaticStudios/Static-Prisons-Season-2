package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPickaxeXPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!PrisonUtils.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) return false;
        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).addXp(Long.parseLong(args[0]));
        return false;
    }
}
