package net.staticstudios.prisons.pickaxe.newenchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.backpacks.Backpack;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.backpacks.config.BackpackConfig;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.newenchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.configuration.ConfigurationSection;

public class BackpackFinderEnchant extends PickaxeEnchant<BlockBreakProcessEvent> {

    private static WeightedElements<Integer> BACKPACK_TIERS;
    private static WeightedElements<Boolean> CHANCE_TO_BE_EMPTY;
    private static WeightedElements<Double> PERCENT_FULL;

    public BackpackFinderEnchant() {
        super(BlockBreakProcessEvent.class, "pickaxe-backpackfinder");

        BACKPACK_TIERS = new WeightedElements<>();
        CHANCE_TO_BE_EMPTY = new WeightedElements<>();
        PERCENT_FULL = new WeightedElements<>();

        ConfigurationSection backpackTiers = getConfig().getConfigurationSection("backpack_tiers");
        if (backpackTiers == null) {
            throw new IllegalStateException("No backpack_tiers section found in the " + getId() + " enchant config!");
        }
        for (String key : backpackTiers.getKeys(false)) {
            BACKPACK_TIERS.add(Integer.parseInt(key.replace("tier_", "")), backpackTiers.getDouble(key));
        }

        ConfigurationSection chanceToBeEmpty = getConfig().getConfigurationSection("chance_to_be_empty");
        if (chanceToBeEmpty == null) {
            throw new IllegalStateException("No chance_to_be_empty section found in the " + getId() + " enchant config!");
        }
        for (String key : chanceToBeEmpty.getKeys(false)) {
            CHANCE_TO_BE_EMPTY.add(Boolean.parseBoolean(key), chanceToBeEmpty.getDouble(key));
        }

        ConfigurationSection percentFill = getConfig().getConfigurationSection("percent_full");
        if (percentFill == null) {
            throw new IllegalStateException("No percent_full section found in the " + getId() + " enchant config!");
        }
        for (String key : percentFill.getKeys(false)) {
            PERCENT_FULL.add(percentFill.getDouble(key + "percent"), percentFill.getDouble(key + "chance"));
        }
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        PrisonPickaxe pickaxe = event.getBlockBreak().getPickaxe();
        if (pickaxe != null && shouldActivate(pickaxe)) return;

        int tier = BACKPACK_TIERS.getRandom();
        double percentFull = CHANCE_TO_BE_EMPTY.getRandom() ? 0 : PERCENT_FULL.getRandom();

        Backpack backpack = BackpackManager.createBackpack(tier);
        backpack.setCapacity((long) (BackpackConfig.tier(tier).maxSize() * percentFull), false);
        backpack.updateItemNow();
        PlayerUtils.addToInventory(event.getPlayer(), backpack.getItem());

        event.getPlayer().sendMessage(getDisplayName()
                .append(Component.text(" >> ")
                        .color(ComponentUtil.DARK_GRAY)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text("Found 1x "))
                .append(BackpackConfig.tier(tier).name())
                .append(Component.text(" with a max capacity of " + PrisonUtils.addCommasToNumber(backpack.getCapacity()) + "!")));
    }
}
