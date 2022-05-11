package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.gui.newGui.EnchantMenus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        EnchantMenus.mainMenu(player, PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()));
        return false;
    }
}
