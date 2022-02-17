package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.crates.CommonCrate;
import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        PlayerData playerData = new PlayerData(player);
        playerData.setHasPrivateMine(true);
        playerData.setPrivateMineSquareSize(250);
        playerData.setPrivateMineMat(Material.STONE);
        return false;
    }
}
