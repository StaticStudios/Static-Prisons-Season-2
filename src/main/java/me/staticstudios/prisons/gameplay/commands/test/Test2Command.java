package me.staticstudios.prisons.gameplay.commands.test;

import com.jeff_media.customblockdata.CustomBlockData;
import me.staticstudios.prisons.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class Test2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        Block block = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ()).getBlock();
        final PersistentDataContainer container = new CustomBlockData(block, Main.getMain());
        player.sendMessage(container.get(new NamespacedKey(Main.getMain(), "test"), PersistentDataType.STRING));
        return false;
    }
}
