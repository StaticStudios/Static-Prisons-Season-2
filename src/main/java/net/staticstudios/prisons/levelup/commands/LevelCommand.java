package net.staticstudios.prisons.levelup.commands;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LevelCommand extends GUIUtils implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(27, "Your Level");
        String levelUpPercent = new DecimalFormat("0.00").format(BigDecimal.valueOf(
                (double) playerData.getPlayerXP() / playerData.getNextLevelRequirement() * 100).setScale(2, RoundingMode.FLOOR)) + "%";
        c.setItem(11, c.createButton(Material.NETHER_STAR, "&a&lCurrent Level: " + PrisonUtils.prettyNum(playerData.getPlayerLevel()), List.of("", "&aNext Level: &f" + PrisonUtils.prettyNum(playerData.getNextLevelRequirement()) + " XP", "&aLevel up progress: &f" + levelUpPercent)));
        c.setItem(13, c.createButton(Material.ANVIL, "&e&lPlayer Levels", List.of("", "Leveling up allows players to unlock new", "things such as: private mines, enchants, and more!", "", "Player can earn XP from mining", "with the &aXP Finder &7enchantment.")));
        c.setItem(15, ench(c.createButton(Material.EXPERIENCE_BOTTLE, "&b&lCurrent XP: " + PrisonUtils.prettyNum(playerData.getPlayerXP()), List.of("", "&bNext Level: &f" + PrisonUtils.prettyNum(playerData.getNextLevelRequirement()) + " XP", "&bLevel up progress: &f" + levelUpPercent))));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
