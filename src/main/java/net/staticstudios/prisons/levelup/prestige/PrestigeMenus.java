package net.staticstudios.prisons.levelup.prestige;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.gui.GUIPlaceholders;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.gui.builder.ButtonBuilder;
import net.staticstudios.gui.builder.GUIBuilder;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PrestigeMenus {

    public static void open(Player player, boolean fromCommand) {
        PlayerData playerData = new PlayerData(player);
        StaticGUI gui = GUIBuilder.builder()
                .title("Prestige | Requires Mine Rank: " + ChatColor.BOLD + "Z")
                .size(27)
                .fillWith(GUIPlaceholders.GRAY)
                .onClose((plr, g) -> {
                    if (!fromCommand) {
                        MainMenus.open(plr);
                    }
                })
                .build();

        gui.setButton(10, ButtonBuilder.builder()
                .icon(Material.EMERALD)
                .enchanted(true)
                .name("&a&lPrestige " + PrisonUtils.addCommasToNumber(playerData.getPrestige()))
                .lore(List.of(
                        Component.empty().append(Component.text("Prestiging will cost you "))
                                .append(Component.text("$" + PrisonUtils.prettyNum(Prestige.getPrestigePrice(playerData.getPrestige(), 1))).color(ComponentUtil.RED))
                                .append(Component.text(" and")).color(ComponentUtil.LIGHT_GRAY),
                        Component.empty().append(Component.text(PrisonUtils.prettyNum(Prestige.TOKENS_PER_PRESTIGE) + " Tokens. ").color(ComponentUtil.YELLOW))
                                .append(Component.text("You will also need to")).color(ComponentUtil.LIGHT_GRAY),
                        Component.empty().append(Component.text("be at least ").color(ComponentUtil.LIGHT_GRAY))
                                .append(Component.text("Level " + PlayerData.getLevelFromXP(Prestige.XP_PER_PRESTIGE * (playerData.getPrestige() + 1)) + ". (" + PrisonUtils.prettyNum(Prestige.XP_PER_PRESTIGE * (playerData.getPrestige() + 1)) + " XP)").color(ComponentUtil.AQUA))
                ))
                .build());

        gui.setButton(13, ButtonBuilder.builder()
                .icon(Material.NETHER_STAR)
                .enchanted(true)
                .name("&b&lLet's do it!")
                .lore(List.of(
                        Component.empty().append(Component.text("Ready to prestige? Click here!").color(ComponentUtil.LIGHT_GRAY)).decorate(TextDecoration.ITALIC),
                        Component.empty(),
                        Component.empty().append(Component.text("| ").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                                .append(Component.text("Costs: ").color(ComponentUtil.LIGHT_PURPLE))
                                .append(Component.text("$" + PrisonUtils.prettyNum(Prestige.getPrestigePrice(playerData.getPrestige(), 1))).color(ComponentUtil.WHITE)),
                        Component.empty().append(Component.text("| ").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                                .append(Component.text("Costs: ").color(ComponentUtil.LIGHT_PURPLE))
                                .append(Component.text(PrisonUtils.prettyNum(Prestige.TOKENS_PER_PRESTIGE) + " Tokens").color(ComponentUtil.WHITE)),
                        Component.empty(),
                        Component.empty().append(Component.text("Left-click to prestige").color(ComponentUtil.YELLOW))
                ))
                .onLeftClick(plr -> {
                    PlayerData plrData = new PlayerData(plr);
                    //Check mine rank
                    if (plrData.getMineRank() < 25) {
                        plr.sendMessage(Prefix.PRESTIGE.append(Component.text("You need to be at Mine Rank Z to prestige!").color(ComponentUtil.RED)));
                        return;
                    }

                    //Check level
                    int requiredLevel = PlayerData.getLevelFromXP(Prestige.XP_PER_PRESTIGE * (plrData.getPrestige() + 1));
                    if (plrData.getPlayerLevel() < requiredLevel) {
                        plr.sendMessage(Prefix.PRESTIGE.append(Component.text("You need to be at least Level " + requiredLevel + " to prestige!").color(ComponentUtil.RED)));
                        return;
                    }

                    //Check money
                    long requiredMoney = Prestige.getPrestigePrice(plrData.getPrestige(), 1);
                    if (plrData.getMoney() < requiredMoney) {
                        plr.sendMessage(Prefix.PRESTIGE.append(Component.text("You need $" + PrisonUtils.prettyNum(requiredMoney) + " to prestige!").color(ComponentUtil.RED)));
                        return;
                    }

                    //Check Tokens
                    if (plrData.getTokens() < Prestige.TOKENS_PER_PRESTIGE) {
                        plr.sendMessage(Prefix.PRESTIGE.append(Component.text("You need " + PrisonUtils.prettyNum(Prestige.TOKENS_PER_PRESTIGE) + " Tokens to prestige!").color(ComponentUtil.RED)));
                        return;
                    }

                    //Prestige the player
                    Prestige.playerPrestige(player, 1);
                    plr.sendMessage(Prefix.PRESTIGE.append(Component.text("You have prestiged!").color(ComponentUtil.GREEN)));

                    Bukkit.broadcast(Component.empty().append(Component.text(plr.getName()).decorate(TextDecoration.BOLD).color(ComponentUtil.LIGHT_PURPLE))
                            .append(Component.text(" just prestiged! " + plr.getName() + " has prestiged a total of " + plrData.getPrestige() + " times!").color(ComponentUtil.WHITE)));

                    if (plr.getWorld().equals(Constants.MINES_WORLD)) {
                        Warps.warpToSpawn(plr);
                    } else {
                        open(plr, fromCommand);
                    }
                })
                .build());

        gui.setButton(16, ButtonBuilder.builder()
                .icon(Material.ANVIL)
                .enchanted(true)
                .name("&d&lWhat happens when I prestige?")
                .lore(List.of(
                        Component.empty().append(Component.text("You must be mine rank Z to prestige.")).color(ComponentUtil.LIGHT_GRAY),
                        Component.empty().append(Component.text("Each time you prestige, rank up costs will")).color(ComponentUtil.LIGHT_GRAY),
                        Component.empty().append(Component.text("increase by ").color(ComponentUtil.LIGHT_GRAY))
                                .append(Component.text("+25% ").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD))
                                .append(Component.text("Prestiging will give you").color(ComponentUtil.LIGHT_GRAY)),
                        Component.empty().append(Component.text("access to new mines and will unlock new features.")).color(ComponentUtil.LIGHT_GRAY),
                        Component.empty().append(Component.text("After you prestige, you will go back to rank A.")).color(ComponentUtil.LIGHT_GRAY),
                        Component.empty(),
                        Component.empty().append(Component.text("Ready to do it?").color(ComponentUtil.LIGHT_GRAY))
                ))
                .build());

        gui.open(player);
    }
}
