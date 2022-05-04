package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.enchants.handler.PrisonEnchants;
import me.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        new PrisonPickaxe(player.getInventory().getItemInMainHand());
        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).addEnchantLevel(PrisonEnchants.FORTUNE, 1);
        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).addEnchantLevel(PrisonEnchants.EXPLOSION, PrisonEnchants.EXPLOSION.MAX_LEVEL);
        return false;
    }
}
