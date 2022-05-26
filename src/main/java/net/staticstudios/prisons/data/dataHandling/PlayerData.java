package net.staticstudios.prisons.data.dataHandling;


import net.staticstudios.prisons.data.Prices;
import net.staticstudios.prisons.enchants.handler.CustomEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.islands.SkyBlockIsland;
import net.staticstudios.prisons.islands.SkyBlockIslands;
import net.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

public class PlayerData extends DataSet {
    private static final DataTypes type = DataTypes.PLAYERS;

    public UUID getUUID() {
        return uuid;
    }

    UUID uuid;


    public PlayerData(Player player) {
        super(type, player.getUniqueId().toString());
        this.uuid = player.getUniqueId();
    }
    public PlayerData(UUID uuid) {
        super(type, uuid.toString());
        this.uuid = uuid;
    }
    public PlayerData(String uuid) {
        super(type, uuid);
        this.uuid = UUID.fromString(uuid);
    }



    //Money
    public BigInteger getMoney() {
        return getBigInt("money");
    }
    public PlayerData setMoney(BigInteger value) {
        setBigInt("money", value);
        return this;
    }
    public PlayerData addMoney(BigInteger value) {
        return setMoney(getMoney().add(value));
    }
    public PlayerData removeMoney(BigInteger value) {
        return setMoney(getMoney().subtract(value));
    }
    //Tokens
    public BigInteger getTokens() {
        return getBigInt("tokens");
    }
    public PlayerData setTokens(BigInteger value) {
        setBigInt("tokens", value);
        return this;
    }
    public PlayerData addTokens(BigInteger value) {
        return setTokens(getTokens().add(value));
    }
    public PlayerData removeTokens(BigInteger value) {
        return setTokens(getTokens().subtract(value));
    }
    //Shards
    public BigInteger getShards() {
        return getBigInt("shards");
    }
    public PlayerData setShards(BigInteger value) {
        setBigInt("shards", value);
        return this;
    }
    public PlayerData addShards(BigInteger value) {
        return setShards(getShards().add(value));
    }
    public PlayerData removeShards(BigInteger value) {
        return setShards(getShards().subtract(value));
    }
    //Time played
    public BigInteger getTimePlayed() {
        return getBigInt("timePlayed");
    }
    public PlayerData setTimePlayed(BigInteger value) {
        setBigInt("timePlayed", value);
        return this;
    }
    public PlayerData addTimePlayed(BigInteger value) {
        return setTimePlayed(getTimePlayed().add(value));
    }
    public PlayerData removeTimePlayed(BigInteger value) {
        return setTimePlayed(getTimePlayed().subtract(value));
    }
    //Raw Blocks Mined
    public BigInteger getRawBlocksMined() {
        return getBigInt("rawBlocksMined");
    }
    public PlayerData setRawBlocksMined(BigInteger value) {
        setBigInt("rawBlocksMined", value);
        return this;
    }
    public PlayerData addRawBlocksMined(BigInteger value) {
        return setRawBlocksMined(getRawBlocksMined().add(value));
    }
    public PlayerData removeRawBlocksMined(BigInteger value) {
        return setRawBlocksMined(getRawBlocksMined().subtract(value));
    }
    //Blocks Mined
    public BigInteger getBlocksMined() {
        return getBigInt("blocksMined");
    }
    public PlayerData setBlocksMined(BigInteger value) {
        setBigInt("blocksMined", value);
        return this;
    }
    public PlayerData addBlocksMined(BigInteger value) {
        return setBlocksMined(getBlocksMined().add(value));
    }
    public PlayerData removeBlocksMined(BigInteger value) {
        return setBlocksMined(getBlocksMined().subtract(value));
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
    //Blocks Mined
    public BigInteger getPrestige() {
        return getBigInt("prestige");
    }
    public PlayerData setPrestige(BigInteger value) {
        setBigInt("prestige", value);
        return this;
    }
    public PlayerData addPrestige(BigInteger value) {
        return setPrestige(getPrestige().add(value));
    }
    public PlayerData removePrestige(BigInteger value) {
        return setPrestige(getPrestige().subtract(value));
    }

    //Multipliers
    public BigDecimal getMoneyMultiplier() {
        BigDecimal multi = BigDecimal.ONE;
        //Factor in ranks
        switch (getPlayerRank()) {
            case "warrior" -> multi = multi.add(new BigDecimal("0.5"));
            case "master" -> multi = multi.add(new BigDecimal("0.8"));
            case "mythic" -> multi = multi.add(BigDecimal.ONE);
            case "static" -> multi = multi.add(new BigDecimal("1.6"));
            case "staticp" -> multi = multi.add(new BigDecimal("2.5"));
        }
        return multi.add(getTempMoneyMultiplier());
    }
    public BigDecimal getTempMoneyMultiplier() {
        List<String> multipliers = getTempMoneyMultiplierList();
        BigDecimal multiplier = BigDecimal.ZERO;
        for (String multi : multipliers) multiplier = multiplier.add(new BigDecimal(multi.split("\\|")[0]));
        return multiplier;
    }
    public PlayerData setTempMoneyMultiplierList(List<String> value) {
        setList("tempMoneyMultipliers", value);
        return this;
    }
    public BigDecimal addTempMoneyMultiplier(BigDecimal amount, long lengthInMS) {
        List<String> multipliers = getTempMoneyMultiplierList();
        multipliers.add(amount.toString() + "|" + (Instant.now().toEpochMilli() + lengthInMS));
        setTempMoneyMultiplierList(multipliers);
        return getTempMoneyMultiplier();
    }
    List<String> getTempMoneyMultiplierList() {
        List<String> initialMultipliers = (List<String>) getList("tempMoneyMultipliers");
        List<String> multipliers = new ArrayList<>(initialMultipliers);
        List<String> multipliersToRemove = new ArrayList<>();
        for (String multi : multipliers) {
            //amount|time
            if (Long.parseLong(multi.split("\\|")[1]) < Instant.now().toEpochMilli()) {
                multipliersToRemove.add(multi);
            }
        }
        multipliers.removeAll(multipliersToRemove);
        if (!multipliers.equals(initialMultipliers)) setList("tempMoneyMultipliers", multipliers);
        return multipliers;
    }
    public BigDecimal getTokenMultiplier() {
        BigDecimal multi = BigDecimal.ZERO;
        //Factor in the consistency enchant
        //TODO
        return multi;
    }
    //Backpack
    public PlayerData setBackpackContents(Map<String, String> value) {
        setMap("backpackContents", value);
        return this;
    }
    public PlayerData setBackpackItemCount(BigInteger value) {
        setBigInt("backpackItemCount", value);
        return this;
    }
    public BigInteger getBackpackItemCount() {
        return getBigInt("backpackItemCount");
    }
    public PlayerData setBackpackSize(BigInteger value) {
        setBigInt("backpackSize", value);
        return this;
    }
    public BigInteger getBackpackSize() {
        return getBigInt("backpackSize");
    }
    public PlayerData setBackpackIsFull(boolean value) {
        setBoolean("backpackIsFull", value);
        return this;
    }
    public boolean getBackpackIsFull() {
        return getBackpackSize().equals(getBackpackItemCount());
    }
    public Map<String, String> getBackpackContents() {
        return (Map<String, String>) getMap("backpackContents");
    }

    public BigInteger getBackpackAmountOf(Material mat) {
        if (getBackpackContents().containsKey(mat.name())) return new BigInteger(getBackpackContents().get(mat.name()));
        return BigInteger.ZERO;
    }

    public void addBackpackAmountOf(Material mat, BigInteger amount) {
        if (getBackpackIsFull()) return;
        BigInteger itemCount = getBackpackItemCount();
        BigInteger size = getBackpackSize();
        if (itemCount.add(amount).compareTo(size) > 0) {
            amount = size.subtract(itemCount);
            setBackpackIsFull(true);
        }
        Map<String, String> map = getBackpackContents();
        map.put(mat.name(), getBackpackAmountOf(mat).add(amount).toString());
        setBackpackContents(map);
        setBackpackItemCount(getBackpackItemCount().add(amount));
    }
    public void sellBackpack(Player player, boolean sendChatMessage) {
        BigDecimal multi = getMoneyMultiplier();
        //Factor in the merchant enchant
        //multi = multi.add(BigDecimal.valueOf(0.5 / PrisonEnchants.MERCHANT.MAX_LEVEL * CustomEnchants.getEnchantLevel(player.getInventory().getItemInMainHand(), "merchant")));
        BigInteger totalSellPrice = BigInteger.ZERO;
        if (getBackpackItemCount().compareTo(BigInteger.ZERO) > 0) {
            for (String key : getBackpackContents().keySet()) {
                totalSellPrice = totalSellPrice.add(new BigInteger(getBackpackContents().get(key)).multiply(Prices.getSellPriceOf(Material.valueOf(key))));
            }
        }
        totalSellPrice = new BigDecimal(totalSellPrice).multiply(multi).toBigInteger();
        new PlayerData(player).addMoney(totalSellPrice);
        if (sendChatMessage) {
            player.sendMessage(org.bukkit.ChatColor.GREEN + "(x" + multi + ") Sold " + Utils.addCommasToNumber(getBackpackItemCount()) + " blocks for: $" + Utils.addCommasToNumber(totalSellPrice));
        }
        setBackpackIsFull(false);
        setBackpackItemCount(BigInteger.ZERO);
        setBackpackContents(new HashMap<>());
    }




    //TabList
    public void updateTabListPrefixID() {
        if (!getStaffRank().equals("member") && !getStaffRank().equals("")) { //TODO move this out of this file
            setTabListPrefixID(getStaffRank());
        } else if (!getPlayerRank().equals("member")) {
            setTabListPrefixID(getPlayerRank());
        } else if (getIsNitroBoosting()) {
            setTabListPrefixID("nitro");
        } else setTabListPrefixID("member");
    }
    public String getTabListPrefixID() {
        return getString("tabListPrefixID");
    }
    public PlayerData setTabListPrefixID(String value) {
        setString("tabListPrefixID", value);
        return this;
    }


    public String getPlayerRank() {
        return getString("playerRank");
    }
    public PlayerData setPlayerRank(String value) {
        setString("playerRank", value);
        return this;
    }

    public List<String> getPlayerRanks() { //TODO use ints instead to make it more genaric
        List<String> ranks = new ArrayList<>();
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
    public PlayerData setStaffRank(String value) {
        setString("staffRank", value);
        return this;
    }

    public String getSidebarRank() { //TODO use ints
        switch (getPlayerRank()) {
            case "warrior" -> {
                return "Warrior";
            }
            case "master" -> {
                return "Master";
            }
            case "mythic" -> {
                return "Mythic";
            }
            case "static" -> {
                return "Static";
            }
            case "staticp" -> {
                return "Static+";
            }
            default -> {
                return "Member";
            }
        }
    }

    public PlayerData setChatTags(List<String> value) {
        setList("chatTags", Utils.removeDuplicatesInArrayList(value));
        return this;
    }
    public List<String> getChatTags() {
        return (List<String>) getList("chatTags");
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

    public PlayerData setChatTag1(String value) {
        setString("chatTag1", value);
        return this;
    }
    public PlayerData setChatTag2(String value) {
        setString("chatTag2", value);
        return this;
    }
    public String getChatTag1() {
        return getString("chatTag1");
    }
    public String getChatTag2() {
        return getString("chatTag2");
    }
    //Settings
    public boolean getIsAutoSellEnabled() {
        return getBoolean("autoSell");
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
    public PlayerData setIsMobile(boolean value) {
        setBoolean("mobile", value);
        return this;
    }
    //Private mines
    public PlayerData setPrivateMineMat(Material value) {
        setString("privateMineMat", value.name());
        return this;
    }
    public Material getPrivateMineMat() {
        return Material.valueOf(getString("privateMineMat"));
    }

    public PlayerData setHasPrivateMine(boolean value) {
        setBoolean("hasPrivateMine", value);
        return this;
    }
    public boolean getHasPrivateMine() {
        return getBoolean("hasPrivateMine");
    }
    public PlayerData setPrivateMineSquareSize(int value) {
        setInt("privateMineSquareSize", value);
        return this;
    }
    public int getPrivateMineSquareSize() {
        return getInt("privateMineSquareSize");
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
    public ChatColor getChatColor() {
        String str = getString("chatColor");
        if (str.length() < 6) return ChatColor.getByChar(str.toCharArray()[0]);
        return ChatColor.of(str);
    }
    public PlayerData setChatColor(ChatColor value) {
        setString("chatColor", value.toString().replace("§", "").replace('x', '#'));
        return this;
    }
    public boolean getIsChatColorEnabled() {
        return getBoolean("chatUseColor");
    }
    public PlayerData setIsChatColorEnabled(boolean value) {
        setBoolean("chatUseColor", value);
        return this;
    }
    public String getChatNickname() {
        return getString("chatNickName");
    }
    public PlayerData setChatNickname(String value) {
        setString("chatNickName", value);
        return this;
    }
    public boolean getIsChatNicknameEnabled() {
        return getBoolean("chatUseNickName");
    }
    public PlayerData setIsChatNickNameEnabled(boolean value) {
        setBoolean("chatUseNickName", value);
        return this;
    }

    //Discord
    public String getDiscordName() {
        return getString("discordName");
    }
    public PlayerData setDiscordName(String value) {
        setString("discordName", value);
        return this;
    }
    public String getDiscordID() {
        return getString("discordID");
    }
    public PlayerData setDiscordID(String value) {
        setString("discordID", value);
        return this;
    }
    public boolean getIsDiscordLinked() {
        return getBoolean("discordIsLinked");
    }
    public PlayerData setIsDiscordLinked(boolean value) {
        setBoolean("discordIsLinked", value);
        return this;
    }
    public boolean getIsNitroBoosting() {
        return getBoolean("discordIsBoosting");
    }
    public PlayerData setIsNitroBoosting(boolean value) {
        setBoolean("discordIsBoosting", value);
        return this;
    }
    //Votes
    public BigInteger getVotes() {
        return getBigInt("votes");
    }
    public PlayerData setVotes(BigInteger value) {
        setBigInt("votes", value);
        return this;
    }
    public PlayerData addVotes(BigInteger value) {
        return setVotes(getVotes().add(value));
    }
    public PlayerData removeVotes(BigInteger value) {
        return setVotes(getVotes().subtract(value));
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

    //Player Island UUIDs
    public String getPlayerIslandUUID() {
        return getString("playerIslandUUID");
    }
    public PlayerData setPlayerIslandUUID(String value) {
        setString("playerIslandUUID", value);
        return this;
    }
    public boolean getIfPlayerHasIsland() {
        return getBoolean("ifPlayerHasIsland");
    }
    public PlayerData setIfPlayerHasIsland(boolean value) {
        setBoolean("ifPlayerHasIsland", value);
        return this;
    }
    public SkyBlockIsland getPlayerIsland() {
        if (!SkyBlockIslands.checkIfPlayerHasIsland(uuid.toString())) return null;
        return SkyBlockIslands.getSkyBlockIsland(getPlayerIslandUUID());
    }

    public boolean getIsWatchingMessages() {
        return getBoolean("isWatchingMessages");
    }
    public PlayerData setIsWatchingMessages(boolean value) {
        setBoolean("isWatchingMessages", value);
        return this;
    }
    public Material getUITheme() {
        String theme = getString("UITheme");
        if (theme.equals("")) {
            setUITheme(Material.LIGHT_BLUE_DYE);
            return Material.LIGHT_BLUE_DYE;
        }
        return Material.valueOf(theme);
    }
    public PlayerData setUITheme(Material value) {
        setString("UITheme", value.name());
        return this;
    }
}
