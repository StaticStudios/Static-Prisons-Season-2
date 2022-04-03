package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.enchants.PrisonPickaxe;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPickaxeXPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!Utils.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) return false;
        PrisonPickaxe.addXP(player.getInventory().getItemInMainHand(), Long.parseLong(args[0]));
        return false;
    }
}
