package net.staticstudios.prisons.fishing.enchants;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.fishing.DefaultFishingRod;

import java.util.Collections;
import java.util.List;

public class LootBoxFinderFishingEnchant implements FishingEnchant {

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getLevel(DefaultFishingRod fishingRod) {
        return 0;
    }

    @Override
    public long getUpgradePrice() {
        return 10_000;
    }

    @Override
    public String getEnchantId() {
        return "lootBoxFinder";
    }

    @Override
    public Component getDisplayName() {
        return Component.text("Loot Box Finder");
    }

    @Override
    public Component getUnformattedDisplayName() {
        return null;
    }

    @Override
    public List<Component> getDescription() {
        return Collections.emptyList();
    }
}
