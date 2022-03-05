package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.customItems.Vouchers;
import me.staticstudios.prisons.data.dataHandling.DataWriter;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.events.EventManager;
import me.staticstudios.prisons.leaderboards.BlocksMinedTop;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(Utils.randomBigInt(BigInteger.ZERO, new BigInteger("1000000000000000")).toString());
        }
        System.out.println(Instant.now().toEpochMilli());
        for (String str : list) {
            new BigInteger(str);
        }
        System.out.println(Instant.now().toEpochMilli());
        return false;
    }
}
