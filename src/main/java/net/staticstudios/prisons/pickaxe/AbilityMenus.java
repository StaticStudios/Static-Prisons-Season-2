package net.staticstudios.prisons.pickaxe;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.pickaxe.abilities.handler.PickaxeAbilities;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AbilityMenus extends GUIUtils {

    public static void selectPickaxe(Player player) {
        GUICreator c = new GUICreator(36, "Select a pickaxe to manage");
        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonUtils.checkIsPrisonPickaxe(item)) {
                c.addItem(c.createButton(item, (p, t) -> {
                    mainMenu(p, PrisonPickaxe.fromItem(item));
                }));
            }
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
    }

    public static void mainMenu(Player player, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(27, "Pickaxe Abilities"); //todo: pricing
        c.setItem(11, createAbilityButton(playerData, pickaxe, PickaxeAbilities.LIGHTNING_STRIKE, c, Material.TRIDENT, true));
        c.setItem(12, createAbilityButton(playerData, pickaxe, PickaxeAbilities.SNOW_FALL, c, Material.SNOWBALL, true));
        c.setItem(13, createAbilityButton(playerData, pickaxe, PickaxeAbilities.BEAM_OF_LIGHT, c, Material.AMETHYST_SHARD, true));
        c.setItem(14, createAbilityButton(playerData, pickaxe, PickaxeAbilities.METEOR_STRIKE, c, Material.FIRE_CHARGE, true));
        c.setItem(15, createAbilityButton(playerData, pickaxe, PickaxeAbilities.BLACK_HOLE, c, Material.FIREWORK_STAR, true));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> PickaxeMenus.open(p, pickaxe));
    }


    //---------- vvv Util methods vvv ----------
    static ItemStack createAbilityButton(PlayerData playerData, PrisonPickaxe pickaxe, BaseAbility ability, GUICreator c, Material icon) {
        List<String> desc = new ArrayList<>(ability.DESCRIPTION);
        desc.add("");
        desc.add("&cCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getAbilityLevel(ability)));
        desc.add("&cUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(ability.getPrice(pickaxe.getAbilityLevel(ability))) + " Shards");
        desc.add("&cYour Shards: &f" + PrisonUtils.prettyNum(playerData.getShards()));
        desc.add("");
        desc.add("&cMax Level: &f" + PrisonUtils.addCommasToNumber(ability.MAX_LEVEL));
        if (ability.getPickaxeLevelRequirement() > pickaxe.getLevel() || ability.getPlayerLevelRequirement() > playerData.getPlayerLevel()) {
            desc.add("");
            if (ability.getPlayerLevelRequirement() > playerData.getPlayerLevel()) desc.add("&aMinimum Player Level: &f" + ability.getPlayerLevelRequirement());
            if (ability.getPickaxeLevelRequirement() > pickaxe.getLevel()) desc.add("&aMinimum Pickaxe Level: &f" + ability.getPickaxeLevelRequirement());
            return c.createButton(icon, ability.DISPLAY_NAME, desc, (p, t) -> {
                if (ability.getPickaxeLevelRequirement() > pickaxe.getLevel()) p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour pickaxe is not high enough level to unlock this ability!"));
                else p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a high enough level to unlock this ability!"));
            });
        }
        return c.createButton(icon, ability.DISPLAY_NAME, desc, (p, t) -> {
            GUICreator _c = new GUICreator(27, ability.DISPLAY_NAME);
            _c.setOnCloseRun((_p, _t) -> mainMenu(p, pickaxe));
            buildMenuContent(_c, p, ability, pickaxe, icon);
            _c.open(p);
        });
    }

    static ItemStack createAbilityButton(PlayerData playerData, PrisonPickaxe pickaxe, BaseAbility ability, GUICreator c, Material icon, boolean enchantIcon) {
        ItemStack item = createAbilityButton(playerData, pickaxe, ability, c, icon);
        if (enchantIcon) {
            return ench(item);
        }
        return item;
    }

    static void buildMenuContent(GUICreator c, Player player, BaseAbility ability, PrisonPickaxe pickaxe, Material currentAbilityIcon) {
        PlayerData playerData = new PlayerData(player);

        if (playerData.canUsePickaxeAbility()) {
            c.setItem(10, ench(c.createButtonOfPlayerSkull(player, "&aYou can use abilities!", List.of(
                    "You can only activate pickaxe abilities once every",
                    "15m! This cool down is not specific to this ability."
            ))));
        } else {
            c.setItem(10, c.createButtonOfPlayerSkull(player, "&cYou cannot use abilities!", List.of(
                    "You can only activate pickaxe abilities once every",
                    "15m! This cool down is not specific to this ability.",
                    "",
                    "&cYou must wait " + PrisonUtils.formatTime(playerData.getLastUsedPickaxeAbility() + 1000 * 60 * 15 - System.currentTimeMillis())
            )));
        }

        List<String> lore = new ArrayList<>(ability.DESCRIPTION);
        if (pickaxe.getAbilityLevel(ability) <= 0) {
            c.setItem(12, c.createButton(Material.BLAZE_POWDER, "&c&lAbility Not Unlocked!", lore));
        } else if (pickaxe.getLastActivatedAbilityAt(ability) + ability.COOL_DOWN > System.currentTimeMillis()) {
            c.setItem(12, c.createButton(Material.BLAZE_POWDER, "&cActivate Ability In: " + PrisonUtils.formatTime(pickaxe.getLastActivatedAbilityAt(ability) + ability.COOL_DOWN - System.currentTimeMillis()), lore, (p, t) -> {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must wait " + PrisonUtils.formatTime(pickaxe.getLastActivatedAbilityAt(ability) + ability.COOL_DOWN - System.currentTimeMillis()) + "before you can activate this ability again!"));
            }));
        } else {
            c.setItem(12, ench(c.createButton(Material.BLAZE_POWDER, "&d&lActivate Ability", lore, (p, t) -> {
                if (!playerData.canUsePickaxeAbility()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must wait " + PrisonUtils.formatTime(playerData.getLastUsedPickaxeAbility() + 1000 * 60 * 15 - System.currentTimeMillis()) + "before you can use pickaxe abilities again!"));
                    return;
                }
                if (pickaxe.getLevel() < ability.getPickaxeLevelRequirement()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour pickaxe is not high enough level to use this ability!"));
                    return;
                }
                if (playerData.getPlayerLevel() < ability.getPlayerLevelRequirement()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a high enough level to use this ability!"));
                    return;
                }

                StaticMine mine = null;
                if (ability.requiresMineOnActivate) {
                    mine = StaticMine.fromLocation(p.getLocation());
                    if (mine == null) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be standing in a mine to activate this ability!"));
                        return;
                    }
                }
                ability.beginActivation(p, pickaxe, mine);
                playerData.setLastUsedPickaxeAbility(System.currentTimeMillis());
                p.closeInventory();
            })));
        }

        lore = new ArrayList<>(ability.DESCRIPTION);
        lore.add("");
        lore.add("&cCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getAbilityLevel(ability)));
        lore.add("&cUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(ability.getPrice(pickaxe.getAbilityLevel(ability))) + " Shards");
        lore.add("&cYour Shards: &f" + PrisonUtils.prettyNum(playerData.getShards()));
        lore.add("");
        lore.add("&cMax Level: &f" + PrisonUtils.addCommasToNumber(ability.MAX_LEVEL));
        c.setItem(14, c.createButton(Material.EMERALD, "&a&lUpgrade Ability", lore, (p, t) -> {
            if (ability.tryToBuyLevels(p, pickaxe, 1)) {
                buildMenuContent(c, p, ability, pickaxe, currentAbilityIcon);
            }
        }));

        c.setItem(16, ench(c.createButton(currentAbilityIcon, ability.DISPLAY_NAME, new ArrayList<>(ability.DESCRIPTION))));
        c.fill(createGrayPlaceHolder());
    }
}