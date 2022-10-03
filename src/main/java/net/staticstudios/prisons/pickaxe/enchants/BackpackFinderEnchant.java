package net.staticstudios.prisons.pickaxe.enchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.backpacks.Backpack;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.backpacks.config.BackpackConfig;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;


public class BackpackFinderEnchant extends BaseEnchant {
    public BackpackFinderEnchant() {
        super("backpackFinder", "&6&lDuffle Bag", 10000, 750, "&7Chance to find a random backpack while mining");
        setPickaxeLevelRequirement(25);

        //20 Tiers
        setTiers(
                new EnchantTier(500, 0),
                new EnchantTier(1000, 1),
                new EnchantTier(1500, 1),
                new EnchantTier(2000, 1),
                new EnchantTier(2500, 1),
                new EnchantTier(3000, 1),
                new EnchantTier(3500, 1),
                new EnchantTier(4000, 1),
                new EnchantTier(4500, 1),
                new EnchantTier(5000, 2),
                new EnchantTier(5500, 2),
                new EnchantTier(6000, 2),
                new EnchantTier(6500, 3),
                new EnchantTier(7000, 3),
                new EnchantTier(7500, 3),
                new EnchantTier(8000, 5),
                new EnchantTier(8500, 5),
                new EnchantTier(9000, 5),
                new EnchantTier(9500, 5),
                new EnchantTier(10000, 10)
        );

        setUseChances(true);
        setDefaultPercentChance(1d / 25000 * 100); //1 out of 25,000
        setPercentChancePerLevel((1d / 15000 * 100 - getDefaultPercentChance()) / MAX_LEVEL); //it will activate 1 out of 15,000 times at max level
    }

    public static final Component PREFIX = Component.empty()
            .append(Component.text("Duffle Bag").color(ComponentUtil.GOLD).decorate(TextDecoration.BOLD)
            .append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY)));

    private static final WeightedElements<Integer> TIERS = new WeightedElements<Integer>()
            .add(1, 20)
            .add(2, 22)
            .add(3, 50)
            .add(4, 5)
            .add(5, 2)
            .add(6, 0.85)
            .add(7, 0.1)
            .add(8, 0.05);
    private static final WeightedElements<Boolean> IS_EMPTY = new WeightedElements<Boolean>()
            .add(true, 40)
            .add(false, 60);
    private static final WeightedElements<Double> PERCENT_FULL = new WeightedElements<Double>()
            .add(.1, 25)
            .add(.25, 50)
            .add(.5, 20)
            .add(.75, 4)
            .add(1d, 1);

    public void onBlockBreak(BlockBreak blockBreak) {
        if (!activate(blockBreak.getPickaxe())) return;
        int tier = TIERS.getRandom();
        tier = Math.min(tier, blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID) / (MAX_LEVEL / 10) + 1);
        boolean isEmpty = IS_EMPTY.getRandom();
        double percentFull = isEmpty ? 0 : PERCENT_FULL.getRandom();

        Backpack backpack = BackpackManager.createBackpack(tier);
        backpack.setCapacity((long) (BackpackConfig.tier(tier).maxSize() * percentFull), false);
        backpack.updateItemNow();
        PlayerUtils.addToInventory(blockBreak.getPlayer(), backpack.getItem());
        blockBreak.getPlayer().sendMessage(PREFIX.append(Component.text("Found 1x ")).append(BackpackConfig.tier(tier).name()).append(Component.text(" with a max capacity of " + PrisonUtils.addCommasToNumber(backpack.getCapacity()) + "!")));
    }
}
