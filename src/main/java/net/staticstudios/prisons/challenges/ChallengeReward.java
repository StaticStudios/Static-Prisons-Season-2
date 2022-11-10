package net.staticstudios.prisons.challenges;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChallengeReward {
    public final ChallengeRewardType TYPE;

    private final long amount;
    private final String id;

    public ChallengeReward(ChallengeRewardType type, long amount, @Nullable String id) {
        this.TYPE = type;
        this.amount = amount;
        this.id = id;
    }

    public static void loadRewards(ConfigurationSection config) {
        Challenge.CHALLENGE_REWARDS.clear();
        for (String tierKey : config.getKeys(false)) {
            int tier;
            try {
                tier = Integer.parseInt(tierKey.replace("tier_", ""));
            } catch (NumberFormatException e) {
                continue;
            }
            List<ChallengeReward> rewards = new ArrayList<>();
            ConfigurationSection tierConfig = config.getConfigurationSection(tierKey);
            assert tierConfig != null;
            for (String k : tierConfig.getKeys(false)) {
                ConfigurationSection rewardConfig = tierConfig.getConfigurationSection(k);
                assert rewardConfig != null;
                ChallengeReward reward = new ChallengeReward(
                        ChallengeRewardType.valueOf(rewardConfig.getString("type")),
                        rewardConfig.getLong("amount"),
                        rewardConfig.getString("id")
                );
                rewards.add(reward);
            }
            Challenge.CHALLENGE_REWARDS.put(tier, rewards);
        }
    }

    public void giveReward(Player player) {
        switch (TYPE) {
            case MONEY -> {
                new PlayerData(player).addMoney(amount);
                player.sendMessage(Prefix.CHALLENGES.append(Component.text("You were given $" + PrisonUtils.addCommasToNumber(amount) + " for completing a challenge!").color(ComponentUtil.WHITE)));
            }
            case TOKENS -> {
                new PlayerData(player).addTokens(amount);
                player.sendMessage(Prefix.CHALLENGES.append(Component.text("You were given " + PrisonUtils.addCommasToNumber(amount) + " Tokens for completing a challenge!").color(ComponentUtil.WHITE)));
            }
            case ITEM -> {
                ItemStack reward = CustomItems.getItem(id, player);
                assert reward != null;
                PlayerUtils.addToInventory(player, reward);
                player.sendMessage(Prefix.CHALLENGES.append(Component.text("You were given " + reward.getAmount() + "x ").color(ComponentUtil.WHITE))
                        .append(Objects.requireNonNull(reward.getItemMeta().displayName()))
                        .append(Component.text(" for completing a challenge!").color(ComponentUtil.WHITE)));
            }
        }
    }
}
