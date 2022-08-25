package net.staticstudios.prisons.admin.commands;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class KeyallCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        ItemStack item;
        if (args.length == 0) {
            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/keyall <common|rare|epic|legendary|static|staticp|vote|kit|pickaxe>"));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "common" -> item = CustomItems.getCommonCrateKey(1);
            case "rare" -> item = CustomItems.getRareCrateKey(1);
            case "epic" -> item = CustomItems.getEpicCrateKey(1);
            case "legendary" -> item = CustomItems.getLegendaryCrateKey(1);
            case "static" -> item = CustomItems.getStaticCrateKey(1);
            case "staticp" -> item = CustomItems.getStaticpCrateKey(1);
            case "vote" -> item = CustomItems.getVoteCrateKey(1);
            case "kit" -> item = CustomItems.getKitCrateKey(1);
            case "pickaxe" -> item = CustomItems.getPickaxeCrateKey(1);
            default -> {
                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/keyall <common|rare|epic|legendary|static|staticp|vote|kit|pickaxe>"));
                return false;
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Key All] &fYou have received 1x " + PrisonUtils.Items.getPrettyItemName(item) + "&f from a key all!"));
            PrisonUtils.Players.addToInventory(p, item);
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) list.addAll(StaticMineUtils.filterStrings(List.of("common", "rare", "epic", "legendary", "static", "staticp", "vote", "kit", "pickaxe"), args[0]));
        return list;
    }
}
