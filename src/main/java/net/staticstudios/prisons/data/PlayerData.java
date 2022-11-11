package net.staticstudios.prisons.data;


import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.challenges.ChallengeDuration;
import net.staticstudios.prisons.data.datahandling.DataSet;
import net.staticstudios.prisons.data.datahandling.DataTypes;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerData extends DataSet {
    private static final DataTypes type = DataTypes.PLAYERS;

    private static final int BASE_XP_PER_LEVEL = 1000;
    private static final double LEVEL_RATE_OF_INCREASE = 1.4;

    UUID uuid;

    public PlayerData(Player player) {
        super(type, player.getUniqueId().toString());
        this.uuid = player.getUniqueId();
    }

    public PlayerData(UUID uuid) {
        super(type, uuid.toString());
        this.uuid = uuid;
    }

    public static long getLevelRequirement(int level) {
        if (level < 0) {
            return BASE_XP_PER_LEVEL;
        }
        return (long) ((long) BASE_XP_PER_LEVEL * level + level * Math.pow(LEVEL_RATE_OF_INCREASE * level, LEVEL_RATE_OF_INCREASE));
    }

    public static int getLevelFromXP(long xp) {
        int lvl = 0;
        while (true) {
            if (lvl > 10000) break;
            if (xp >= getLevelRequirement(lvl + 1)) {
                lvl++;
            } else break;
        }
        return lvl;
    }

    public static ChatColor getPrimaryUITheme(String theme) {
        switch (theme) {
            default -> { //b
                return ChatColor.of("#3dc2ff");
            }
            case "5" -> {
                return ChatColor.of("#b638ff");
            }
            case "2" -> {
                return ChatColor.of("#00ba31");
            }
            case "4" -> {
                return ChatColor.DARK_RED;
            }
            case "6" -> {
                return ChatColor.of("#ffcc00");
            }
        }
    }

    public static TextColor getPrimaryUIThemeAsTextColor(String theme) {
        switch (theme) {
            default -> { //b
                return TextColor.fromHexString("#3dc2ff");
            }
            case "5" -> {
                return TextColor.fromHexString("#b638ff");
            }
            case "2" -> {
                return TextColor.fromHexString("#00ba31");
            }
            case "4" -> {
                return ComponentUtil.DARK_RED;
            }
            case "6" -> {
                return TextColor.fromHexString("#ffcc00");
            }
        }
    }

    public static ChatColor getSecondaryUITheme(String theme) {
        switch (theme) {
            default -> { //b
                return ChatColor.AQUA;
            }
            case "5" -> {
                return ChatColor.LIGHT_PURPLE;
            }
            case "2" -> {
                return ChatColor.GREEN;
            }
            case "4" -> {
                return ChatColor.RED;
            }
            case "6" -> {
                return ChatColor.GOLD;
            }
        }
    }

    public static TextColor getSecondaryUIThemeAsTextColor(String theme) {
        switch (theme) {
            default -> { //b
                return ComponentUtil.AQUA;
            }
            case "5" -> {
                return ComponentUtil.LIGHT_PURPLE;
            }
            case "2" -> {
                return ComponentUtil.GREEN;
            }
            case "4" -> {
                return ComponentUtil.RED;
            }
            case "6" -> {
                return ComponentUtil.GOLD;
            }
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    //Money
    public long getMoney() {
        return getLong("money");
    }

    public PlayerData setMoney(long value) {
        setLong("money", value);
        return this;
    }

    public PlayerData addMoney(long value) {
        return setMoney(getMoney() + value);
    }

    public PlayerData removeMoney(long value) {
        return setMoney(getMoney() - value);
    }

    //Tokens
    public long getTokens() {
        return getLong("tokens");
    }

    public PlayerData setTokens(long value) {
        setLong("tokens", value);
        return this;
    }

    public PlayerData addTokens(long value) {
        return setTokens(getTokens() + value);
    }

    public PlayerData removeTokens(long value) {
        return setTokens(getTokens() - value);
    }

    //Prestige Tokens
    public long getPrestigeTokens() {
        return getLong("prestigeTokens");
    }

    public PlayerData setPrestigeTokens(long value) {
        setLong("prestigeTokens", value);
        return this;
    }

    public PlayerData addPrestigeTokens(long value) {
        return setPrestigeTokens(getPrestigeTokens() + value);
    }

    public PlayerData removePrestigeTokens(long value) {
        return setPrestigeTokens(getPrestigeTokens() - value);
    }

    //Shards
    public long getShards() {
        return getLong("shards");
    }

    public PlayerData setShards(long value) {
        setLong("shards", value);
        return this;
    }

    public PlayerData addShards(long value) {
        return setShards(getShards() + value);
    }

    public PlayerData removeShards(long value) {
        return setShards(getShards() - value);
    }

    //Player XP & Level
    public long getPlayerXP() {
        return getLong("playerXP");
    }

    public PlayerData addPlayerXP(long value) {
        return setPlayerXP(getPlayerXP() + value);
    }

    public PlayerData removePlayerXP(long value) {
        return setPlayerXP(getPlayerXP() - value);
    }

    public PlayerData setPlayerXP(long value) {
        setLong("playerXP", value);

        //Re-calc the player's level
        while (true) {
            long xp = getPlayerXP();
            if (getPlayerLevel() > 10000) break;
            if (getPlayerLevel() < 0) {
                setPlayerLevel(0);
                break;
            }
            if (xp >= getNextLevelRequirement()) {
                addPlayerLevel(1);
            } else if (xp < getLevelRequirement(getPlayerLevel())) {
                removePlayerLevel(1);
            } else break;
        }


        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.setLevel(getPlayerLevel());
            player.setExp(Math.max(0f, (float) (getPlayerXP() - getLevelRequirement(getPlayerLevel())) / (getNextLevelRequirement() - getLevelRequirement(getPlayerLevel()))));
        }

        return this;
    }

    public long getNextLevelRequirement() {
        return getLevelRequirement(getPlayerLevel() + 1);
    }

    public int getPlayerLevel() {
        return getInt("playerLevel");
    }

    public PlayerData setPlayerLevel(int value) {
        setInt("playerLevel", value);
        return this;
    }

    public PlayerData addPlayerLevel(int value) {
        return setPlayerLevel(getPlayerLevel() + value);
    }

    public PlayerData removePlayerLevel(int value) {
        return setPlayerLevel(getPlayerLevel() - value);
    }

    //Time played
    public long getTimePlayed() {
        return getLong("timePlayed");
    }

    public PlayerData setTimePlayed(long value) {
        setLong("timePlayed", value);
        return this;
    }

    public PlayerData addTimePlayed(long value) {
        return setTimePlayed(getTimePlayed() + value);
    }

    public PlayerData removeTimePlayed(long value) {
        return setTimePlayed(getTimePlayed() - value);
    }

    //Raw Blocks Mined
    public long getRawBlocksMined() {
        return getLong("rawBlocksMined");
    }

    public PlayerData setRawBlocksMined(long value) {
        setLong("rawBlocksMined", value);
        return this;
    }

    public PlayerData addRawBlocksMined(long value) {
        return setRawBlocksMined(getRawBlocksMined() + value);
    }

    public PlayerData removeRawBlocksMined(long value) {
        return setRawBlocksMined(getRawBlocksMined() - value);
    }

    //Blocks Mined
    public long getBlocksMined() {
        return getLong("blocksMined");
    }

    public PlayerData setBlocksMined(long value) {
        setLong("blocksMined", value);
        return this;
    }

    public PlayerData addBlocksMined(long value) {
        return setBlocksMined(getBlocksMined() + value);
    }

    public PlayerData removeBlocksMined(long value) {
        return setBlocksMined(getBlocksMined() - value);
    }

    //Mine Rank
    public int getMineRank() {
        return getInt("mineRank");
    }

    public PlayerData setMineRank(int value) {
        setInt("mineRank", value);
        return this;
    }

    public PlayerData addMineRank(int value) {
        return setMineRank(getMineRank() + value);
    }

    public PlayerData removeMineRank(int value) {
        return setMineRank(getMineRank() - value);
    }

    //Prestige
    public long getPrestige() {
        return getLong("prestige");
    }

    public PlayerData setPrestige(long value) {
        setLong("prestige", value);
        return this;
    }

    public PlayerData addPrestige(long value) {
        return setPrestige(getPrestige() + value);
    }

    public PlayerData removePrestige(long value) {
        return setPrestige(getPrestige() - value);
    }

    public long getClaimedPrestigeRewardsAt() {
        return getLong("prestigeRewardsClaimed");
    }

    public PlayerData setClaimedPrestigeRewardsAt(long value) {
        setLong("prestigeRewardsClaimed", value);
        return this;
    }

    //Multipliers
    public double getMoneyMultiplier() {
        double multi = 1 + getTempMoneyMultiplier();
        return switch (getPlayerRank()) {
            default -> multi;
            case "warrior" -> multi + 0.05d; //+5%
            case "master" -> multi + 0.10d; //+10%
            case "mythic" -> multi + 0.15d; //+15%
            case "static" -> multi + 0.20d; //+20%
            case "staticp" -> multi + 0.25d; //+25%
        };
    }

    public double getTempMoneyMultiplier() {
        //Get the lists containing the multipliers and times
        List<Double> amounts = new ArrayList<>(getStringList("tempMoneyMultipliers-amounts").stream()
                .map(Double::parseDouble).toList());
        List<Long> times = new ArrayList<>(getStringList("tempMoneyMultipliers-times").stream()
                .map(Long::parseLong).toList());

        assert amounts.size() == times.size();
        List<Double> amountsToRemove = new ArrayList<>();
        List<Long> timesToRemove = new ArrayList<>();

        //Filter them to remove any that have expired
        for (int i = 0; i < times.size(); i++) {
            if (times.get(i) >= System.currentTimeMillis()) {
                continue;
            }

            amountsToRemove.add(amounts.get(i));
            timesToRemove.add(times.get(i));
        }

        amounts.removeAll(amountsToRemove);
        times.removeAll(timesToRemove);

        //Set the lists to the new filtered lists
        setStringList("tempMoneyMultipliers-amounts", new ArrayList<>(amounts.stream()
                .map(String::valueOf).toList()));
        setStringList("tempMoneyMultipliers-times", new ArrayList<>(times.stream()
                .map(String::valueOf).toList()));

        return amounts.stream().mapToDouble(Double::doubleValue).sum();
    }

    public void addTempMoneyMultiplier(double amount, long lengthInMS) {
        List<String> amounts = getStringList("tempMoneyMultipliers-amounts");
        List<String> times = getStringList("tempMoneyMultipliers-times");

        amounts.add(String.valueOf(amount));
        times.add(String.valueOf(System.currentTimeMillis() + lengthInMS));

        setStringList("tempMoneyMultipliers-amounts", amounts);
        setStringList("tempMoneyMultipliers-times", times);

        getTempMoneyMultiplier();
    }

    public String getPlayerRank() {
        return getString("playerRank");
    }

    public void setPlayerRank(String value) {
        setString("playerRank", value != null ? value : "member");
        Player player = Bukkit.getPlayer(getUUID());
        if (player != null) {
            PrisonUtils.updateLuckPermsForPlayerRanks(player);
        }
    }

    public HashSet<String> getPlayerRanks() {
        HashSet<String> ranks = new HashSet<>();
        ranks.add("member");
        switch (getPlayerRank()) {
            case "staticp":
                ranks.add("staticp");
            case "static":
                ranks.add("static");
            case "mythic":
                ranks.add("mythic");
            case "master":
                ranks.add("master");
            case "warrior":
                ranks.add("warrior");
        }
        return ranks;
    }

    public String getStaffRank() {
        return getString("staffRank");
    }

    public void setStaffRank(String value) {
        setString("staffRank", value != null ? value : "member");
    }

    public List<String> getChatTags() {
        return getStringList("chatTags");
    }

    public void setChatTags(List<String> value) {
        setStringList("chatTags", PrisonUtils.removeDuplicatesInArrayList(value));
    }

    public void removeChatTag(String value) {
        List<String> tags = getChatTags();
        tags.remove(value);
        setChatTags(tags);
    }

    public void addChatTag(String value) {
        List<String> tags = getChatTags();
        tags.add(value);
        setChatTags(tags);
    }

    public String getChatTag1() {
        return getString("chatTag1");
    }

    public PlayerData setChatTag1(String value) {
        setString("chatTag1", value);
        return this;
    }

    public String getChatTag2() {
        return getString("chatTag2");
    }

    public PlayerData setChatTag2(String value) {
        setString("chatTag2", value);
        return this;
    }

    //Settings
    public boolean getIsAutoSellEnabled() {
        boolean value = getBoolean("autoSell");
        if (value && !PlayerUtils.canAutoSell(this)) {
            setIsAutoSellEnabled(false);
        }
        return value;
    }

    public PlayerData setIsAutoSellEnabled(boolean value) {
        setBoolean("autoSell", value);
        return this;
    }

    public boolean getCanExplicitlyEnableAutoSell() {
        return getBoolean("canEnableAutoSell");
    }

    public PlayerData setCanEnableAutoSell(boolean value) {
        setBoolean("canEnableAutoSell", value);
        return this;
    }

    public boolean getAreTipsDisabled() {
        return getBoolean("tipsEnabled");
    }

    public PlayerData setAreTipsDisabled(boolean value) {
        setBoolean("tipsEnabled", value);
        return this;
    }

    public boolean getIsMobile() {
        return getBoolean("mobile");
    }

    public void setIsMobile(boolean value) {
        setBoolean("mobile", value);
    }

    //Chat settings
    public boolean getIsChatBold() {
        return getBoolean("chatBold");
    }

    public PlayerData setIsChatBold(boolean value) {
        setBoolean("chatBold", value);
        return this;
    }

    public boolean getIsChatItalic() {
        return getBoolean("chatItalic");
    }

    public PlayerData setIsChatItalic(boolean value) {
        setBoolean("chatItalic", value);
        return this;
    }

    public boolean getIsChatUnderlined() {
        return getBoolean("chatUnderlined");
    }

    public PlayerData setIsChatUnderlined(boolean value) {
        setBoolean("chatUnderlined", value);
        return this;
    }

    public TextColor getChatColor() {
        String str = getString("chatColor");
        if (str.isEmpty()) return NamedTextColor.WHITE;
        if (str.length() < 6) return NamedTextColor.WHITE;
        return TextColor.fromHexString(str);
    }

    public PlayerData setChatColor(TextColor value) {
        setString("chatColor", value.asHexString());
        return this;
    }

    public Map<TextDecoration, TextDecoration.State> getChatDecorations() {
        return Map.ofEntries(
                Map.entry(TextDecoration.BOLD, getIsChatBold() ? TextDecoration.State.TRUE : TextDecoration.State.FALSE),
                Map.entry(TextDecoration.ITALIC, getIsChatItalic() ? TextDecoration.State.TRUE : TextDecoration.State.FALSE),
                Map.entry(TextDecoration.UNDERLINED, getIsChatUnderlined() ? TextDecoration.State.TRUE : TextDecoration.State.FALSE)
        );
    }

    public String getNickname() {
        return getString("chatNickName");
    }

    public void setNickname(String value) {
        setString("chatNickName", value);
    }

    public boolean useNickname() {
        return getBoolean("chatUseNickName");
    }

    public void useNickname(boolean value) {
        setBoolean("chatUseNickName", value);
    }

    //Discord
    public String getDiscordName() {
        String discordName = getString("discordName");
        return "null".equals(discordName) ? null : discordName;
    }

    public void setDiscordName(String value) {
        setString("discordName", value);
    }

    public String getDiscordID() {
        return getString("discordID");
    }

    public void setDiscordID(String value) {
        setString("discordID", value);
    }

    public boolean getIsDiscordLinked() {
        return getBoolean("discordIsLinked");
    }

    public void setIsDiscordLinked(boolean value) {
        setBoolean("discordIsLinked", value);
    }

    public boolean getIsNitroBoosting() {
        return getBoolean("discordIsBoosting");
    }

    public void setIsNitroBoosting(boolean value) {
        setBoolean("discordIsBoosting", value);
    }

    //Votes
    public long getVotes() {
        return getLong("votes");
    }

    public PlayerData setVotes(long value) {
        setLong("votes", value);
        return this;
    }

    public PlayerData addVotes(long value) {
        return setVotes(getVotes() + value);
    }

    public PlayerData removeVotes(long value) {
        return setVotes(getVotes() - value);
    }

    public long getLastVotedAt() {
        return getLong("lastVotedAt");
    }

    public PlayerData setLastVotedAt(long value) {
        setLong("lastVotedAt", value);
        return this;
    }

    public long getClaimedDailyRewardsAt() {
        return getLong("claimedDailyRewardsAt");
    }

    public PlayerData setClaimedDailyRewardsAt(long value) {
        setLong("claimedDailyRewardsAt", value);
        return this;
    }

    public long getClaimedDailyRewardsRank1At() {
        return getLong("claimedDailyRewardsAtRank1");
    }

    public PlayerData setClaimedDailyRewardsRank1At(long value) {
        setLong("claimedDailyRewardsAtRank1", value);
        return this;
    }

    public long getClaimedDailyRewardsRank2At() {
        return getLong("claimedDailyRewardsAtRank2");
    }

    public PlayerData setClaimedDailyRewardsRank2At(long value) {
        setLong("claimedDailyRewardsAtRank2", value);
        return this;
    }

    public boolean getIsExemptFromLeaderboards() {
        return getBoolean("isExemptFromLeaderboards");
    }

    public PlayerData setIsExemptFromLeaderboards(boolean value) {
        setBoolean("isExemptFromLeaderboards", value);
        return this;
    }

    public boolean getIsWatchingMessages() {
        return getBoolean("isWatchingMessages");
    }

    public PlayerData setIsWatchingMessages(boolean value) {
        setBoolean("isWatchingMessages", value);
        return this;
    }

    @Deprecated
    public ChatColor getPrimaryUITheme() {
        return getPrimaryUITheme(getUIThemeID());
    }

    public TextColor getPrimaryUIThemeAsTextColor() {
        return getPrimaryUIThemeAsTextColor(getUIThemeID());
    }

    @Deprecated
    public ChatColor getSecondaryUITheme() {
        return getSecondaryUITheme(getUIThemeID());
    }

    public TextColor getSecondaryUIThemeAsTextColor() {
        return getSecondaryUIThemeAsTextColor(getUIThemeID());
    }

    public String getUIThemeID() {
        String str = getString("UIThemeID");
        if (str.isEmpty()) str = "b";
        return str;
    }

    public PlayerData setUIThemeID(String value) {
        setString("UIThemeID", value);
        return this;
    }

    public long getLastUsedPickaxeAbility() {
        return getLong("lastUsedPickaxeAbility");
    }

    public PlayerData setLastUsedPickaxeAbility(long value) {
        setLong("lastUsedPickaxeAbility", value);
        return this;
    }

    public boolean canUsePickaxeAbility() {
        return getLastUsedPickaxeAbility() + (1000 * 60 * 15) < System.currentTimeMillis();
    }

    public long getLastGotFreeChallengeAt(ChallengeDuration duration) {
        return getLong("lastGotFreeChallengeAt" + duration);
    }

    public PlayerData setLastGotFreeChallengeAt(ChallengeDuration duration, long value) {
        setLong("lastGotFreeChallengeAt" + duration, value);
        return this;
    }
}
