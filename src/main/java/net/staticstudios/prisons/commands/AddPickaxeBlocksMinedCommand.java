package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPickaxeBlocksMinedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!Utils.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) return false;
        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).addBlocksBroken(Long.parseLong(args[0]));
        return false;
    }
}
