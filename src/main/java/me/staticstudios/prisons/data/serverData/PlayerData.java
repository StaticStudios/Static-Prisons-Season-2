package me.staticstudios.prisons.data.serverData;


import me.staticstudios.prisons.data.Prices;
import me.staticstudios.prisons.enchants.PrisonEnchants;
import me.staticstudios.prisons.auctionHouse.SerializableAuctionItem;
import me.staticstudios.prisons.data.dataHandling.Data;
import me.staticstudios.prisons.data.dataHandling.DataSet;
import me.staticstudios.prisons.data.dataHandling.DataTypes;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.islands.SkyBlockIsland;
import me.staticstudios.prisons.islands.SkyBlockIslands;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

public class PlayerData extends DataSet {
    private static final DataTypes type = DataTypes.PLAYERS;

    public String getUUID() {
        return uuid;
    }

    String uuid;


    public PlayerData(Player player) {
        super(type, player.getUniqueId().toString());
        this.uuid = player.getUniqueId().toString();
    }
    public PlayerData(UUID uuid) {
        super(type, uuid.toString());
        this.uuid = uuid.toString();
    }
    public PlayerData(String uuid) {
        super(type, uuid);
        this.uuid = uuid;
    }

    //Money
    public BigInteger getMoney() {
        if (getData("money")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("money", newData);
        }
        return new BigInteger(getData("money")._string);
    }
    public Data setMoney(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("money", newData);
    }
    public Data addMoney(BigInteger value) {
        return setMoney(getMoney().add(value));
    }
    public Data removeMoney(BigInteger value) {
        return setMoney(getMoney().subtract(value));
    }

    //Tokens
    public BigInteger getTokens() {
        if (getData("tokens")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("tokens", newData);
        }
        return new BigInteger(getData("tokens")._string);
    }
    public Data setTokens(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("tokens", newData);
    }
    public Data addTokens(BigInteger value) {
        return setTokens(getTokens().add(value));
    }
    public Data removeTokens(BigInteger value) {
        return setTokens(getTokens().subtract(value));
    }

    //Shards
    public BigInteger getShards() {
        if (getData("shards")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("shards", newData);
        }
        return new BigInteger(getData("shards")._string);
    }
    public Data setShards(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("shards", newData);
    }
    public Data addShards(BigInteger value) {
        return setShards(getShards().add(value));
    }
    public Data removeShards(BigInteger value) {
        return setShards(getShards().subtract(value));
    }

    //Time played
    public BigInteger getTimePlayed() {
        if (getData("timePlayed")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("timePlayed", newData);
        }
        return new BigInteger(getData("timePlayed")._string);
    }
    public Data setTimePlayed(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("timePlayed", newData);
    }
    public Data addTimePlayed(BigInteger value) {
        return setTimePlayed(getTimePlayed().add(value));
    }
    public Data removeTimePlayed(BigInteger value) {
        return setTimePlayed(getTimePlayed().subtract(value));
    }

    //Raw blocks mined
    public BigInteger getRawBlocksMined() {
        if (getData("rawBlocksMined")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("rawBlocksMined", newData);
        }
        return new BigInteger(getData("rawBlocksMined")._string);
    }
    public Data setRawBlocksMined(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("rawBlocksMined", newData);
    }
    public Data addRawBlocksMined(BigInteger value) {
        return setRawBlocksMined(getRawBlocksMined().add(value));
    }
    public Data removeRawBlocksMined(BigInteger value) {
        return setRawBlocksMined(getRawBlocksMined().subtract(value));
    }

    //Blocks Mined
    public BigInteger getBlocksMined() {
        if (getData("blocksMined")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("blocksMined", newData);
        }
        return new BigInteger(getData("blocksMined")._string);
    }
    public Data setBlocksMined(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("blocksMined", newData);
    }
    public Data addBlocksMined(BigInteger value) {
        return setBlocksMined(getBlocksMined().add(value));
    }
    public Data removeBlocksMined(BigInteger value) {
        return setBlocksMined(getBlocksMined().subtract(value));
    }

    //Mine rank
    public int getMineRank() {
        return getData("mineRank")._int;
    }
    public Data setMineRank(int value) {
        Data newData = new Data();
        newData._int = value;
        return setData("mineRank", newData);
    }
    public Data addMineRank(int value) {
        return setMineRank(getMineRank() + value);
    }
    public Data removeMineRank(int value) {
        return setMineRank(getMineRank() - value);
    }

    //Prestige
    public BigInteger getPrestige() {
        if (getData("prestige")._string.equals("")) return BigInteger.ZERO;
        return new BigInteger(getData("prestige")._string);
    }
    public Data setPrestige(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("prestige", newData);
    }
    public Data addPrestige(BigInteger value) {
        return setPrestige(getPrestige().add(value));
    }
    public Data removePrestige(BigInteger value) {
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
        multi = multi.add(getTempMoneyMultiplier());
        return multi;
    }
    List<String> getTempMoneyMultiplierList() {
        if (getData("tempMoneyMultipliers").list == null) {
            Data data = new Data();
            data.list = new ArrayList<String>();
            setData("tempMoneyMultipliers", data);
        }
        List<String> initialMultipliers = getData("tempMoneyMultipliers").list;
        List<String> multipliers = new ArrayList<>(initialMultipliers);
        List<String> multipliersToRemove = new ArrayList<>();
        for (String multi : multipliers) {
            //amount|time
            if (Long.parseLong(multi.split("\\|")[1]) < Instant.now().toEpochMilli()) {
                multipliersToRemove.add(multi);
            }
        }
        multipliers.removeAll(multipliersToRemove);
        if (!multipliers.equals(initialMultipliers)) {
            Data data = new Data();
            data.list = multipliers;
            setData("tempMoneyMultipliers", data);
        }
        return multipliers;
    }
    void setTempMoneyMultiplierList(List<String> value) {
        Data data = new Data();
        data.list = value;
        setData("tempMoneyMultipliers", data);
    }
    public BigDecimal getTempMoneyMultiplier() {
        List<String> multipliers = getTempMoneyMultiplierList();
        BigDecimal multiplier = BigDecimal.ZERO;
        for (String multi : multipliers) multiplier = multiplier.add(new BigDecimal(multi.split("\\|")[0]));
        return multiplier;
    }
    public BigDecimal addTempMoneyMultiplier(BigDecimal amount, long lengthInMS) {
        List<String> multipliers = getTempMoneyMultiplierList();
        multipliers.add(amount.toString() + "|" + (Instant.now().toEpochMilli() + lengthInMS));
        setTempMoneyMultiplierList(multipliers);
        return getTempMoneyMultiplier();
    }


    public BigDecimal getTokenMultiplier() {
        BigDecimal multi = BigDecimal.ZERO;
        //Factor in the consistency enchant
        //TODO
        return multi;
    }





    //Backpack
    public boolean updateBackpackIsFull() {
        return getBackpackSize().equals(getBackpackItemCount());
    }
    public Data setBackpackContents(Map<String, String> value) {
        Data newData = new Data();
        newData.map = value;
        return setData("backpackContents", newData);
    }
    public Data setBackpackItemCount(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("backpackItemCount", newData);
    }
    public BigInteger getBackpackItemCount() {
        if (getData("backpackItemCount")._string.equals("")) setBackpackItemCount(BigInteger.ZERO);
        return new BigInteger(getData("backpackItemCount")._string);
    }
    public Data setBackpackSize(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("backpackSize", newData);
    }
    public BigInteger getBackpackSize() {
        if (getData("backpackSize")._string.equals("")) setBackpackSize(BigInteger.valueOf(25000));
        return new BigInteger(getData("backpackSize")._string);
    }
    public Data setBackpackIsFull(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("backpackIsFull", newData);
    }
    public boolean getBackpackIsFull() {
        return getData("backpackIsFull")._boolean;
    }
    public Map<String, String> getBackpackContents() {
        if (getData("backpackContents").map == null) setBackpackContents(new HashMap<>());
        return getData("backpackContents").map;
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
        multi = multi.add(BigDecimal.valueOf(0.5 / PrisonEnchants.MERCHANT.MAX_LEVEL * CustomEnchants.getEnchantLevel(player.getInventory().getItemInMainHand(), "merchant")));
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




    public void updateTabListPrefixID() {
        if (!getStaffRank().equals("member") && !getStaffRank().equals("")) {
            setTabListPrefixID(getStaffRank());
        } else if (!getPlayerRank().equals("member")) {
            setTabListPrefixID(getPlayerRank());
        } else if (getIsNitroBoosting()) {
            setTabListPrefixID("nitro");
        } else setTabListPrefixID("member");
    }
    public String getTabListPrefixID() {
        if (getData("tabListPrefixID")._string == null) {
            Data newData = new Data();
            newData._string = "";
            setData("tabListPrefixID", newData);
        }
        return getData("tabListPrefixID")._string;
    }
    public Data setTabListPrefixID(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("tabListPrefixID", newData);
    }


    public String getPlayerRank() {
        if (getData("playerRank")._string == null) {
            Data newData = new Data();
            newData._string = "member";
            setData("playerRank", newData);
        }
        return getData("playerRank")._string;
    }

    public Data setPlayerRank(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("playerRank", newData);
    }

    public List<String> getPlayerRanks() {
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
        if (getData("staffRank")._string == null) {
            Data newData = new Data();
            newData._string = "member";
            setData("staffRank", newData);
        }
        return getData("staffRank")._string;
    }

    public Data setStaffRank(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("staffRank", newData);
    }

    public String getSidebarRank() {
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

    public Data setChatTags(List<String> value) {
        Data newData = new Data();
        value = Utils.removeDuplicatesInArrayList(value);
        newData.list = value;
        setData("chatTags", newData);
        return getData("chatTags");
    }
    public List<String> getChatTags() {
        Data newData = new Data();
        if (getData("chatTags").list == null) {
            setChatTags(new ArrayList<>());
        }
        return (List<String>) getData("chatTags").list;
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

    public Data setChatTag1(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("chatTag1", newData);
    }
    public Data setChatTag2(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("chatTag2", newData);
    }

    public String getChatTag1() {
        return getData("chatTag1")._string;
    }

    public String getChatTag2() {
        return getData("chatTag2")._string;
    }

    //This method is inefficient and can take multiple milliseconds to execute, this should eventually be changed
    public List<ItemStack> getExpiredAuctions() {
        if (getData("expiredAuctions").list == null) {
            Data newDate = new Data();
            newDate.list = new ArrayList<>();
            setData("expiredAuctions", newDate);
        }
        List<ItemStack> items = new ArrayList<>();
        for (String str : (List<String>) getData("expiredAuctions").list) {
            items.add(SerializableAuctionItem.itemStackFromBase64(str));
        }
        return items;
    }
    public int getExpiredAuctionsAmount() {
        return getData("expiredAuctionsAmount")._int;
    }

    private void setExpiredAuctionsAmount(int value) {
        Data newData = new Data();
        newData._int = value;
        setData("expiredAuctionsAmount", newData);
    }

    //This method is inefficient and can take multiple milliseconds to execute, this should eventually be changed
    public Data setExpiredAuctions(List<ItemStack> value) {
        setExpiredAuctionsAmount(value.size());
        //Limits this list to 64 items, removes the first item(s) from the list while its big
        while (value.size() > 64) value.remove(0);
        Data newDate = new Data();
        List<String> strs = new ArrayList<>();
        for (ItemStack item : value) {
            strs.add(SerializableAuctionItem.itemStackToBase64(item));
        }
        newDate.list = strs;
        return setData("expiredAuctions", newDate);
    }

    //This method is inefficient and can take multiple milliseconds to execute, this should eventually be changed
    public Data removeExpiredAuction(int index) {
        List<ItemStack> items = getExpiredAuctions();
        items.remove(index);
        return setExpiredAuctions(items);
    }

    //This method is inefficient and can take multiple milliseconds to execute, this should eventually be changed
    public Data addExpiredAuction(ItemStack item) {
        List<ItemStack> items = getExpiredAuctions();
        items.add(item);
        return setExpiredAuctions(items);
    }
    //Settings
    public boolean getIsAutoSellEnabled() {
        return getData("autoSell")._boolean;
    }
    public Data setIsAutoSellEnabled(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("autoSell", newData);
    }
    public boolean getCanExplicitlyEnableAutoSell() {
        return getData("canEnableAutoSell")._boolean;
    }
    public Data setCanEnableAutoSell(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("canEnableAutoSell", newData);
    }
    public boolean getAreTipsDisabled() {
        return getData("tipsEnabled")._boolean;
    }
    public Data setAreTipsDisabled(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("tipsEnabled", newData);
    }
    public boolean getIsMobile() {
        return getData("mobile")._boolean;
    }
    public Data setIsMobile(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("mobile", newData);
    }
    public boolean getIsChatFilterEnabled() {
        return getData("chatFilter")._boolean;
    }
    public Data setIsChatFilterEnabled(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("chatFilter", newData);
    }


    //P mines
    public Data setPrivateMineMat(Material value) {
        Data newData = new Data();
        newData._string = value.name();
        return setData("privateMineMat", newData);
    }
    public Material getPrivateMineMat() {
        return Material.valueOf(getData("privateMineMat")._string);
    }

    public Data setHasPrivateMine(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("hasPrivateMine", newData);
    }
    public boolean getHasPrivateMine() {
        return getData("hasPrivateMine")._boolean;
    }
    public Data setPrivateMineSquareSize(int value) {
        Data newData = new Data();
        newData._int = value;
        return setData("privateMineSquareSize", newData);
    }
    public int getPrivateMineSquareSize() {
        return getData("privateMineSquareSize")._int;
    }


    //Chat settings
    public boolean getIsChatBold() {
        return getData("chatBold")._boolean;
    }
    public Data setIsChatBold(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("chatBold", newData);
    }
    public boolean getIsChatItalic() {
        return getData("chatItalic")._boolean;
    }
    public Data setIsChatItalic(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("chatItalic", newData);
    }
    public boolean getIsChatUnderlined() {
        return getData("chatUnderlined")._boolean;
    }
    public Data setIsChatUnderlined(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("chatUnderlined", newData);
    }
    public ChatColor getChatColor() {
        String str = getData("chatColor")._string;
        if (str.length() < 6) {
            return ChatColor.getByChar(str.toCharArray()[0]);
        } else return ChatColor.of(str);
    }
    public Data setChatColor(ChatColor value) {
        Data newData = new Data();
        newData._string = value.toString().replace("§", "").replace('x', '#');
        return setData("chatColor", newData);
    }
    public boolean getIsChatColorEnabled() {
        return getData("chatUseColor")._boolean;
    }
    public Data setIsChatColorEnabled(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("chatUseColor", newData);
    }
    public String getChatNickname() {
        return getData("chatNickName")._string;
    }
    public Data setChatNickname(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("chatNickName", newData);
    }
    public boolean getIsChatNicknameEnabled() {
        return getData("chatUseNickName")._boolean;
    }
    public Data setIsChatNickNameEnabled(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("chatUseNickName", newData);
    }

    //Discord
    public String getDiscordName() {
        return getData("discordName")._string;
    }
    public Data setDiscordName(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("discordName", newData);
    }
    public String getDiscordID() {
        return getData("discordID")._string;
    }
    public Data setDiscordID(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("discordID", newData);
    }
    public boolean getIsDiscordLinked() {
        return getData("discordIsLinked")._boolean;
    }
    public Data setIsDiscordLinked(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("discordIsLinked", newData);
    }
    public boolean getIsNitroBoosting() {
        return getData("discordIsBoosting")._boolean;
    }
    public Data setIsNitroBoosting(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("discordIsBoosting", newData);
    }
    //Votes
    public BigInteger getVotes() {
        if (getData("votes")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("votes", newData);
        }
        return new BigInteger(getData("votes")._string);
    }
    public Data setVotes(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("votes", newData);
    }
    public Data addVotes(BigInteger value) {
        return setVotes(getVotes().add(value));
    }
    public Data removeVotes(BigInteger value) {
        return setVotes(getVotes().subtract(value));
    }
    public long getLastVotedAt() {
        return getData("lastVotedAt")._long;
    }
    public Data setLastVotedAt(long value) {
        Data newData = new Data();
        newData._long = value;
        return setData("lastVotedAt", newData);
    }
    public long getClaimedDailyRewardsAt() {
        return getData("claimedDailyRewardsAt")._long;
    }
    public Data setClaimedDailyRewardsAt(long value) {
        Data newData = new Data();
        newData._long = value;
        return setData("claimedDailyRewardsAt", newData);
    }

    public long getClaimedDailyRewardsRank1At() {
        return getData("claimedDailyRewardsAtRank1")._long;
    }
    public Data setClaimedDailyRewardsRank1At(long value) {
        Data newData = new Data();
        newData._long = value;
        return setData("claimedDailyRewardsAtRank1", newData);
    }

    public long getClaimedDailyRewardsRank2At() {
        return getData("claimedDailyRewardsAtRank2")._long;
    }
    public Data setClaimedDailyRewardsRank2At(long value) {
        Data newData = new Data();
        newData._long = value;
        return setData("claimedDailyRewardsAtRank2", newData);
    }

    //Player Island UUIDs
    public String getPlayerIslandUUID() {
        if (getData("playerIslandUUID")._string == null) {
            Data newData = new Data();
            newData._string = "";
            setData("playerIslandUUID", newData);
        }
        return getData("playerIslandUUID")._string;
    }
    public Data setPlayerIslandUUID(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("playerIslandUUID", newData);
    }

    //sets if a player is in an island
    public boolean getIfPlayerHasIsland() {
        return getData("ifPlayerHasIsland")._boolean;
    }
    public Data setIfPlayerHasIsland(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("ifPlayerHasIsland", newData);
    }


    //Does not read/write from/to a file

    /**
     * @return The SkyBlockIsland that the player is a part of, they do not have to own the island.
     * Returns null if player is not part of an island
     */
    public SkyBlockIsland getPlayerIsland() {
        if (!SkyBlockIslands.checkIfPlayerHasIsland(uuid)) return null;
        return SkyBlockIslands.getSkyBlockIsland(getPlayerIslandUUID());
    }
    public boolean getIsExemptFromLeaderboards() {
        return getData("exeptFromLeaderboards")._boolean;
    }
    public Data setIsExemptFromLeaderboards(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("exeptFromLeaderboards", newData);
    }

    public boolean getIsWatchingMessages() {
        return getData("isWatchingMessages")._boolean;
    }
    public Data setIsWatchingMessages(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        return setData("isWatchingMessages", newData);
    }


    public Material getUITheme() {
        String theme = getData("UITheme")._string;
        if (theme.equals("")) {
            setUITheme(Material.LIGHT_BLUE_DYE);
            return Material.LIGHT_BLUE_DYE;
        }
        return Material.valueOf(theme);
    }
    public Data setUITheme(Material value) {
        Data newData = new Data();
        newData._string = value.name();
        return setData("UITheme", newData);
    }
}