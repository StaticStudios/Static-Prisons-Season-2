package net.staticstudios.prisons.lootboxes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.customitems.items.LootBoxCustomItem;
import net.staticstudios.prisons.lootboxes.lootboxes.MineBombLootBox;
import net.staticstudios.prisons.lootboxes.lootboxes.MoneyLootBox;
import net.staticstudios.prisons.lootboxes.lootboxes.PickaxeLootBox;
import net.staticstudios.prisons.lootboxes.lootboxes.TokenLootBox;
import net.staticstudios.prisons.lootboxes.rewards.LootBoxCurrencyOutline;
import net.staticstudios.prisons.lootboxes.rewards.LootBoxItemOutline;
import net.staticstudios.prisons.lootboxes.rewards.LootBoxItemReward;
import net.staticstudios.prisons.lootboxes.rewards.LootBoxRewardType;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.utils.StaticFileSystemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum LootBoxType {
    TOKEN(TokenLootBox.class, Component.empty().append(Component.text("Token Loot Box").color(ComponentUtil.GOLD).decoration(TextDecoration.BOLD, true))),
    MONEY(MoneyLootBox.class, Component.empty().append(Component.text("Money Loot Box").color(ComponentUtil.GREEN).decoration(TextDecoration.BOLD, true))),
    MINE_BOMB(MineBombLootBox.class, Component.empty().append(Component.text("Mine Bomb Loot Box").color(ComponentUtil.RED).decoration(TextDecoration.BOLD, true))),
    PICKAXE(PickaxeLootBox.class, Component.empty().append(Component.text("Pickaxe Loot Box").color(ComponentUtil.AQUA).decoration(TextDecoration.BOLD, true)));
//    MULTIPLIER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWUyZTRjZGYyN2UzMmRjMThiNjA2NGNkZGNmZTFmZmIxZjM4ZDE0ODFiZDdjNDcyZWExMjMwNzZhMDc4NTAzZiJ9fX0=",
//            Component.empty().append(Component.text("Pickaxe Loot Box").color(ComponentUtil.AQUA).decoration(TextDecoration.BOLD, true)), null),

    private final LootBoxRewardType rewardType;
    private final String base64Texture;
    private final Component displayName;
    private final Class<? extends LootBox> lootBoxClass;
    private int minTier;
    private int maxTier;
    private Map<Integer, LootBoxCurrencyOutline> currencyOutlines;
    private Map<Integer, LootBoxItemOutline> itemOutlines;

    LootBoxType(Class<? extends LootBox> clazz, Component displayName) {
        this.lootBoxClass = clazz;
        this.displayName = displayName;

        ConfigurationSection config = Objects.requireNonNull(getConfig().getConfigurationSection(toString().toLowerCase()));
        this.base64Texture = config.getString("texture");
        this.rewardType = LootBoxRewardType.valueOf(Objects.requireNonNull(config.getString("reward_type")).toUpperCase());
        loadRewards(config.getConfigurationSection("rewards"));
    }

    /**
     * Get the texture for this loot box type's item in base64 format.
     * @return The texture for this loot box type's item in base64 format.
     */
    public String getBase64Texture() {
        return base64Texture;
    }

    /**
     * Get the display name for this loot box type.
     * @return The display name for this loot box type.
     */
    public Component getDisplayName() {
        return displayName;
    }

    /**
     * Get the loot box class for this loot box type.
     * @return The loot box class for this loot box type.
     */
    public Class<? extends LootBox> getLootBoxClass() {
        return lootBoxClass;
    }

    /**
     * Get the goal for this loot box type for a given tier.
     * @param tier The tier to get the goal for.
     * @return The goal for this loot box type for the given tier.
     */
    public long getGoal(int tier) {
        return switch (rewardType) {
            case CURRENCY -> currencyOutlines.get(tier).goal();
            case ITEM -> itemOutlines.get(tier).goal();
        };
    }

    /**
     * Get the minimum tier for this loot box type.
     * @return The minimum tier for this loot box type.
     */
    public int getMinTier() {
        return minTier;
    }

    /**
     * Get the maximum tier for this loot box type.
     * @return The maximum tier for this loot box type.
     */
    public int getMaxTier() {
        return maxTier;
    }

    /**
     * Get the min reward for this loot box type for a given tier.
     * @param tier The tier to get the reward for.
     * @return The reward for this loot box type for the given tier.
     */
    public long getMinCurrencyReward(int tier) {
        if (rewardType != LootBoxRewardType.CURRENCY) {
            throw new RuntimeException("Cannot get min currency reward for non-currency lootbox");
        }
        return currencyOutlines.get(tier).minReward();
    }

    /**
     * Get the max reward for this loot box type for a given tier.
     * @param tier The tier to get the reward for.
     * @return The reward for this loot box type for the given tier.
     */
    public long getMaxCurrencyReward(int tier) {
        if (rewardType != LootBoxRewardType.CURRENCY) {
            throw new RuntimeException("Cannot get min currency reward for non-currency lootbox");
        }
        return currencyOutlines.get(tier).maxReward();
    }

    /**
     * Get the rewards for this loot box type for a given tier.
     * @param tier The tier to get the reward for.
     * @return The reward for this loot box type for the given tier.
     */
    public WeightedElements<LootBoxItemReward> getItemRewards(int tier) {
        if (rewardType != LootBoxRewardType.ITEM) {
            throw new RuntimeException("Cannot get item outline for non-item lootbox");
        }
        return itemOutlines.get(tier).rewards();
    }

    /**
     * Get the reward type for this loot box type.
     * @return The reward type for this loot box type.
     */
    public LootBoxRewardType getRewardType() {
        return rewardType;
    }

    /**
     * This method will load the outline and rewards for this loot box type.
     * @param config The configuration section to load from.
     */
    private void loadRewards(ConfigurationSection config) {
        switch (rewardType) {
            case CURRENCY -> {
                currencyOutlines = new HashMap<>();
                for (String key : config.getKeys(false)) {
                    int tier = Integer.parseInt(key);
                    loadedTier(tier);
                    long requires = config.getLong(key + ".requires");

                    long minReward = config.getLong(key + ".reward.min");
                    long maxReward = config.getLong(key + ".reward.max");

                    currencyOutlines.put(tier, new LootBoxCurrencyOutline(tier, requires, minReward, maxReward));
                }
            }
            case ITEM -> {
                itemOutlines = new HashMap<>();
                for (String key : config.getKeys(false)) {
                    int tier = Integer.parseInt(key);
                    loadedTier(tier);
                    long requires = config.getLong(key + ".requires");

                    ConfigurationSection itemRewardsConfig = Objects.requireNonNull(config.getConfigurationSection(key + ".items"));
                    WeightedElements<LootBoxItemReward> weightedRewards = new WeightedElements<>();
                    for (String k : itemRewardsConfig.getKeys(false)) {
                        String itemId = k;
                        if (itemId.startsWith("+")) {
                            itemId = itemId.replaceAll("\\+", "");
                        }
                        weightedRewards.add(
                                new LootBoxItemReward(itemId, itemRewardsConfig.getInt(k + ".amount", 1)),
                                itemRewardsConfig.getDouble(k + ".chance"));
                    }

                    itemOutlines.put(tier, new LootBoxItemOutline(tier, requires, weightedRewards));
                }
            }
        }
    }

    /**
     * This method will figure out the min and max tiers for this loot box type.
     * This will also register the custom item for loot boxes of this type.
     * @param tier The tier to load.
     */
    private void loadedTier(int tier) {
        if (minTier == 0 || tier < minTier) {
            minTier = tier;
        }
        if (maxTier == 0 || tier > maxTier) {
            maxTier = tier;
        }

        //Register the custom item
        new LootBoxCustomItem(tier, this);
    }

    private static YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(StaticFileSystemManager.getFile("lootboxes.yml").orElseThrow(() -> new RuntimeException("Could not find lootboxes.yml")));
    }
}
