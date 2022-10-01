package net.staticstudios.prisons.levelup.xp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.newgui.GUIPlaceholders;
import net.staticstudios.newgui.StaticGUI;
import net.staticstudios.newgui.builder.ButtonBuilder;
import net.staticstudios.newgui.builder.GUIBuilder;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
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
import java.util.Collections;
import java.util.List;

public class LevelCommand extends GUIUtils implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);

        double levelUpProgress = ((double) playerData.getPlayerXP() - PlayerData.getLevelRequirement(playerData.getPlayerLevel())) / (playerData.getNextLevelRequirement() - PlayerData.getLevelRequirement(playerData.getPlayerLevel()));
        String levelUpPercentage = new DecimalFormat("#.##").format(levelUpProgress * 100) + "%";

        StaticGUI gui = GUIBuilder.builder()
                .title("Your Level")
                .size(27)
                .fillWith(GUIPlaceholders.GRAY)
                .build();

        gui.setButton(10, ButtonBuilder.builder()
                .icon(Material.NETHER_STAR)
                .name("&b&lCurrent Level: " + playerData.getPlayerLevel())
                .lore(List.of(
                        Component.empty(),
                        Component.empty().append(Component.text("Next level: ").color(ComponentUtil.AQUA)).append(Component.text(PrisonUtils.addCommasToNumber(playerData.getNextLevelRequirement()) + " XP").color(ComponentUtil.WHITE)),
                        Component.empty().append(Component.text("Level up progress: ").color(ComponentUtil.AQUA)).append(Component.text(levelUpPercentage).color(ComponentUtil.WHITE))
                ))
                .build());

        gui.setButton(13, ButtonBuilder.builder()
                .icon(Material.ANVIL)
                .name("&e&lPlayer Levels")
                .lore(List.of(
                        Component.empty().append(Component.text("Leveling up allows players to unlock new").color(ComponentUtil.LIGHT_GRAY)),
                        Component.empty().append(Component.text("things such as: private mines, enchants, and more!").color(ComponentUtil.LIGHT_GRAY)),
                        Component.empty(),
                        Component.empty().append(Component.text("Player can earn XP from mining").color(ComponentUtil.LIGHT_GRAY)),
                        Component.empty().append(Component.text("and from the ").color(ComponentUtil.LIGHT_GRAY))
                                .append(Component.text("XP Finder").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD))
                                .append(Component.text(" enchantment.").color(ComponentUtil.LIGHT_GRAY))
                ))
                .build());

        gui.setButton(16, ButtonBuilder.builder()
                .icon(Material.EXPERIENCE_BOTTLE)
                .name("&a&lCurrent XP: " + PrisonUtils.prettyNum(playerData.getPlayerXP()) + " XP")
                .lore(List.of(
                        Component.empty(),
                        Component.empty().append(Component.text("Next level: ").color(ComponentUtil.GREEN)).append(Component.text( PrisonUtils.addCommasToNumber(playerData.getNextLevelRequirement()) + " XP").color(ComponentUtil.WHITE)),
                        Component.empty().append(Component.text("Level up progress: ").color(ComponentUtil.GREEN)).append(Component.text(levelUpPercentage).color(ComponentUtil.WHITE))
                ))
                .build());

        gui.open(player);

//        GUICreator c = new GUICreator(27, "Your Level");
//        String levelUpPercent = new DecimalFormat("0.00").format(BigDecimal.valueOf(
//                (double) playerData.getPlayerXP() / playerData.getNextLevelRequirement() * 100).setScale(2, RoundingMode.FLOOR)) + "%";
//        c.setItem(11, c.createButton(Material.NETHER_STAR, "&a&lCurrent Level: " + PrisonUtils.prettyNum(playerData.getPlayerLevel()), List.of("", "&aNext Level: &f" + PrisonUtils.prettyNum(playerData.getNextLevelRequirement()) + " XP", "&aLevel up progress: &f" + levelUpPercent)));
//        c.setItem(13, c.createButton(Material.ANVIL, "&e&lPlayer Levels", List.of("", "Leveling up allows players to unlock new", "things such as: private mines, enchants, and more!", "", "Player can earn XP from mining", "with the &aXP Finder &7enchantment.")));
//        c.setItem(15, ench(c.createButton(Material.EXPERIENCE_BOTTLE, "&b&lCurrent XP: " + PrisonUtils.prettyNum(playerData.getPlayerXP()), List.of("", "&bNext Level: &f" + PrisonUtils.prettyNum(playerData.getNextLevelRequirement()) + " XP", "&bLevel up progress: &f" + levelUpPercent))));
//        c.fill(createGrayPlaceHolder());
//        c.open(player);
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
