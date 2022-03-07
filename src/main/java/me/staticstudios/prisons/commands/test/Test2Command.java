package me.staticstudios.prisons.commands.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeff_media.customblockdata.CustomBlockData;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.data.dataHandling.DataSet;
import me.staticstudios.prisons.data.dataHandling.DataSets;
import me.staticstudios.prisons.data.dataHandling.DataTypes;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.mines.PrivateMine;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
