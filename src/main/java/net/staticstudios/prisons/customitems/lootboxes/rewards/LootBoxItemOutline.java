package net.staticstudios.prisons.customitems.lootboxes.rewards;

import net.staticstudios.mines.utils.WeightedElements;

public record LootBoxItemOutline(int tier, long goal, WeightedElements<LootBoxItemReward> rewards) {
}
