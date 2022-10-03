package net.staticstudios.prisons.fishing.enchants;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.fishing.DefaultFishingRod;

import java.util.List;

public interface FishingEnchant {

    int getMaxLevel();

    int getLevel(DefaultFishingRod fishingRod);

    long getUpgradePrice();

    String getEnchantId();

    Component getDisplayName();

    Component getUnformattedDisplayName();

    List<Component> getDescription();


}
