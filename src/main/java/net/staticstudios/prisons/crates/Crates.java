package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.customitems.Vouchers;
import net.staticstudios.prisons.customitems.minebombs.MineBomb;
import net.staticstudios.prisons.customitems.pickaxes.PickaxeTemplates;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.mines.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class Crates {

    public static Crate COMMON;
    public static Crate RARE;
    public static Crate EPIC;
    public static Crate LEGENDARY;
    public static Crate STATIC;
    public static Crate STATICP;
    public static Crate PICKAXE;
    public static Crate VOTE;
    public static Crate KIT;

    public static void init() {
        Crate.init();
        COMMON = new Crate("common", "Common Crate", "common", new Location(Bukkit.getWorld("world"), -51, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T1.item), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 2)), 10)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T1.item), 15)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_1.getItem(null), 1)), 20)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_1.getItem(null), 2)), 10)
                        .add(new CrateReward(MineBomb.TIER_2.getItem(null)), 5)
                        .add(new CrateReward(CustomItems.getCommonCrateKey(3)), 7.5)
                        .add(new CrateReward(CustomItems.getRareCrateKey(1)), 7.5)
                        .add(new CrateReward(CustomItems.getRareCrateKey(2)), 5)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(1)), 2.5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 5)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2.5)
        );
        RARE = new Crate("rare", "Rare Crate", "rare", new Location(Bukkit.getWorld("world"), -42, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T1.item), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 2)), 10)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T1.item), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 2)), 15)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T1.item), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_1.getItem(null), 3)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_2.getItem(null), 1)), 15)
                        .add(new CrateReward(MineBomb.TIER_3.getItem(null)), 10)
                        .add(new CrateReward(CustomItems.getRareCrateKey(2)), 4)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(1)), 2)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(2)), 1)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(1)), 2)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2)
        );

        EPIC = new Crate("epic", "Epic Crate", "epic", new Location(Bukkit.getWorld("world"), -33, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T1.item), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 2)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 3)), 5)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T1.item), 8)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 2)), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 3)), 5)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T1.item), 5)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T2.item), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_1.getItem(null), 4)), 10)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_2.getItem(null), 2)), 5)
                        .add(new CrateReward(MineBomb.TIER_3.getItem(null)), 8)
                        .add(new CrateReward(MineBomb.TIER_4.getItem(null)), 2)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(2)), 5)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(3)), 3)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(1)), 4.5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(2)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(1)), 1)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.99)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2.5)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.01)
        );

        LEGENDARY = new Crate("legendary", "Legendary Crate", "legendary", new Location(Bukkit.getWorld("world"), -24, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T1.item), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 2)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 3)), 5)
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T2.item), 2.5)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T1.item), 2.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 2)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 3)), 7)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T2.item), 5)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T1.item), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MULTI_POUCH_T1.item, 2)), 6)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T2.item), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_2.getItem(null), 4)), 7.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_3.getItem(null), 2)), 7.5)
                        .add(new CrateReward(MineBomb.TIER_4.getItem(null)), 5)
                        .add(new CrateReward(CustomItems.getCommonCrateKey(8)), 1)
                        .add(new CrateReward(CustomItems.getRareCrateKey(3)), 5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(2)), 3)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(1)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 1)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.975)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.015)
                        .add(new CrateReward(Vouchers.WARRIOR_RANK.item), 0.01)
        );

        STATIC = new Crate("static", "Static Crate", "static", new Location(Bukkit.getWorld("world"), -15, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T1.item), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 2)), 2.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 3)), 2)
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T2.item), 7.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T2.item, 2)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T2.item, 3)), 3)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T1.item), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 2)), 2.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 3)), 2)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T2.item), 3.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T2.item, 2)), 4)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T2.item, 3)), 5)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T1.item), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MULTI_POUCH_T1.item, 2)), 4)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T2.item), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MULTI_POUCH_T2.item, 2)), 5)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T3.item), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_2.getItem(null), 5)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_3.getItem(null), 2)), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_3.getItem(null), 3)), 5)
                        .add(new CrateReward(MineBomb.TIER_4.getItem(null)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_4.getItem(null), 2)), 1)
                        .add(new CrateReward(CustomItems.getCommonCrateKey(15)), 3)
                        .add(new CrateReward(CustomItems.getRareCrateKey(7)), 2)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(4)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 1)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(1)), 2)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(2)), 0.5)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(3)), 0.5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 4)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.970)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.01)
                        .add(new CrateReward(Vouchers.WARRIOR_RANK.item), 0.01)
                        .add(new CrateReward(Vouchers.MASTER_RANK.item), 0.005)
                        .add(new CrateReward(Vouchers.MYTHIC_RANK.item), 0.005)
        );

        STATICP = new Crate("staticp", "Static+ Crate", "staticp", new Location(Bukkit.getWorld("world"), 3, 80, -137),
                new WeightedElements<CrateReward>() //todo: get rid of the worse items
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T1.item, 4)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T2.item, 3)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T2.item, 4)), 2.5)
                        .add(new CrateReward(Vouchers.MONEY_POUCH_T3.item), 6)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MONEY_POUCH_T3.item, 2)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T1.item, 4)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T2.item, 3)), 4.5)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T2.item, 4)), 3)
                        .add(new CrateReward(Vouchers.TOKEN_POUCH_T3.item), 7)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.TOKEN_POUCH_T3.item, 2)), 2)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T1.item), 1)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MULTI_POUCH_T1.item, 3)), 3)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T2.item), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MULTI_POUCH_T2.item, 2)), 2)
                        .add(new CrateReward(Vouchers.MULTI_POUCH_T3.item), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(Vouchers.MULTI_POUCH_T3.item, 2)), 3)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_1.getItem(null), 24)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_3.getItem(null), 4)), 5)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_3.getItem(null), 6)), 5)
                        .add(new CrateReward(MineBomb.TIER_4.getItem(null)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_4.getItem(null), 2)), 2)
                        .add(new CrateReward(PrisonUtils.setItemCount(MineBomb.TIER_4.getItem(null), 4)), 1)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(5)), 8.5)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 10)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(3)), 9)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(2)), 2.5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(3)), 3.3)
                        .add(new CrateReward(CustomItems.getKitCrateKey(4)), 1)
                        .add(new CrateReward(Vouchers.AUTO_SELL.item), 0.1)
                        .add(new CrateReward(Vouchers.WARRIOR_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.MASTER_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.MYTHIC_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.STATIC_RANK.item), 0.1)
                        .add(new CrateReward(Vouchers.STATICP_RANK.item), 0.2)
        );

        VOTE = new Crate("vote", "Vote Crate", "vote", new Location(Bukkit.getWorld("world"), -54, 80, -125),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(CustomItems.getCommonCrateKey(1)), 13.4)
                        .add(new CrateReward(CustomItems.getCommonCrateKey(2)), 25)
                        .add(new CrateReward(CustomItems.getRareCrateKey(1)), 20)
                        .add(new CrateReward(CustomItems.getRareCrateKey(2)), 10)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(1)), 7.5)
                        .add(new CrateReward(CustomItems.getEpicCrateKey(2)), 2.5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(1)), 5)
                        .add(new CrateReward(CustomItems.getLegendaryCrateKey(2)), 2)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(1)), 1.5)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(2)), 2.5)
                        .add(new CrateReward(CustomItems.getStaticCrateKey(3)), 0.5)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(1)), 0.075)
                        .add(new CrateReward(CustomItems.getStaticpCrateKey(3)), 0.025)
                        .add(new CrateReward(CustomItems.getKitCrateKey(1)), 2.5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(1)), 5)
                        .add(new CrateReward(CustomItems.getPickaxeCrateKey(2)), 2.5)
        );

        KIT = new Crate("kit", "Kit Crate", "kit", new Location(Bukkit.getWorld("world"), -54, 80, -134),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(Vouchers.KIT_TIER_1.item), 50)
                        .add(new CrateReward(Vouchers.KIT_TIER_2.item), 25)
                        .add(new CrateReward(Vouchers.KIT_TIER_3.item), 15)
                        .add(new CrateReward(Vouchers.KIT_TIER_4.item), 5)
                        .add(new CrateReward(Vouchers.KIT_TIER_5.item), 2.5)
                        .add(new CrateReward(Vouchers.KIT_TIER_6.item), 0.5)
                        .add(new CrateReward(Vouchers.KIT_POTIONS.item), 1)
                        .add(new CrateReward(Vouchers.KIT_WEAPONS.item), 1)
        );

        PICKAXE = new Crate("pickaxe", "Pickaxe Crate", "pickaxe", new Location(Bukkit.getWorld("world"), 12, 80, -137),
                new WeightedElements<CrateReward>()
                        .add(new CrateReward(PickaxeTemplates.TIER_1.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_1.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_1.DISPLAY_NAME), 25)
                        .add(new CrateReward(PickaxeTemplates.TIER_2.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_2.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_2.DISPLAY_NAME), 20)
                        .add(new CrateReward(PickaxeTemplates.TIER_3.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_3.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_3.DISPLAY_NAME), 15)
                        .add(new CrateReward(PickaxeTemplates.TIER_4.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_4.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_4.DISPLAY_NAME), 12.5)
                        .add(new CrateReward(PickaxeTemplates.TIER_5.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_5.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_5.DISPLAY_NAME), 10)
                        .add(new CrateReward(PickaxeTemplates.TIER_6.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_6.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_6.DISPLAY_NAME), 7.5)
                        .add(new CrateReward(PickaxeTemplates.TIER_7.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_7.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_7.DISPLAY_NAME), 6)
                        .add(new CrateReward(PickaxeTemplates.TIER_8.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_8.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_8.DISPLAY_NAME), 2.5)
                        .add(new CrateReward(PickaxeTemplates.TIER_9.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_9.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_9.DISPLAY_NAME), 1)
                        .add(new CrateReward(PickaxeTemplates.TIER_10.buildPickaxe().delete().item, p -> PlayerUtils.addToInventory(p, PickaxeTemplates.TIER_10.buildPickaxe().setBottomLore(List.of("", "&7Won from a &aPickaxe &7crate")).tryToUpdateLore().item), "1x " + PickaxeTemplates.TIER_10.DISPLAY_NAME), 0.5)
        );

    }

}
