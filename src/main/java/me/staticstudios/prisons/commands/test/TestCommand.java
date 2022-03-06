package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.customItems.Vouchers;
import me.staticstudios.prisons.data.dataHandling.DataWriter;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.events.EventManager;
import me.staticstudios.prisons.islands.IslandManager;
import me.staticstudios.prisons.leaderboards.BlocksMinedTop;
import me.staticstudios.prisons.mines.BaseMine;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        return false;
    }
}
