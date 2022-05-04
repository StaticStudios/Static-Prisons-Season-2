package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.enchants.handler.PrisonEnchants;
import me.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.Statement;

public class Test2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        player.getInventory().addItem(Utils.createNewPickaxe());
        return false;
    }
}
