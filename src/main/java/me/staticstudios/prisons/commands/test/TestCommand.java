package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.customItems.Vouchers;
import me.staticstudios.prisons.data.dataHandling.DataWriter;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.events.EventManager;
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
        System.out.println(Instant.now().toEpochMilli());
        int yLevel = 98;
        int howDeepToGo = 2;
        int totalBlocksBroken = 0;
        World world = Bukkit.getWorld("mines");
        BaseMine mine = MineManager.allMines.get(MineManager.getMineIDFromLocation(player.getLocation()));
        Map<Material, BigInteger> blocksBroken = new HashMap<>();
        for (int y = Math.max(1, yLevel - howDeepToGo + 1); y <= yLevel; y++) {
            for (int x = (int) mine.minLocation.getX(); x <= mine.maxLocation.getX(); x++) {
                for (int z = (int) mine.minLocation.getZ(); z <= mine.maxLocation.getZ(); z++) {
                    Material mat = new Location(world, x, y, z).getBlock().getType();
                    if (!mat.equals(Material.AIR)) {
                        totalBlocksBroken++;
                        if (!blocksBroken.containsKey(mat)) {
                            blocksBroken.put(mat, BigInteger.ONE);
                        } else blocksBroken.put(mat, blocksBroken.get(mat).add(BigInteger.ONE));
                    }
                }
            }
        }
        System.out.println(Instant.now().toEpochMilli());
        return false;
    }
}
