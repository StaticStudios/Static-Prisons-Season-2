package net.staticstudios.prisons.data;


import net.staticstudios.prisons.data.dataHandling.DataSet;
import net.staticstudios.prisons.data.dataHandling.DataTypes;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.islands.SkyBlockIsland;
import net.staticstudios.prisons.islands.SkyBlockIslands;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
    //Player XP
    public long getPlayerXP() {
        return getLong("playerXP");
    }
    public PlayerData setPlayerXP(long value) {
        setLong("playerXP", value);
        updatePlayerLevel();
        return this;
    }
    public PlayerData addPlayerXP(long value) {
        return setPlayerXP(getPlayerXP() + value);
    }
    public PlayerData removePlayerXP(long value) {
        return setPlayerXP(getPlayerXP() - value);
    }
    //Player Level
    private static final int BASE_XP_PER_LEVEL = 1000;
    private static final double LEVEL_RATE_OF_INCREASE = 2.4;
    public static long getLevelRequirement(int level) {
        if (level <= 0) return BASE_XP_PER_LEVEL;
        return (long) ((long) BASE_XP_PER_LEVEL * level + level * Math.pow(LEVEL_RATE_OF_INCREASE * level, LEVEL_RATE_OF_INCREASE));
    }
    public long getNextLevelRequirement() {
        return getLevelRequirement(getPlayerLevel() + 1);
    }
    void updatePlayerLevel() {
        //Calculate player level
        while (true) {
            long xp = getPlayerXP();
            if (getPlayerLevel() > 10000) break;
            if (getPlayerLevel() < 0) {
                setPlayerLevel(0);
                break;
            }
            if (xp >= getNextLevelRequirement()) {
                addPlayerLevel(1);
                if (Bukkit.getPlayer(uuid) != null) Bukkit.getPlayer(uuid).sendMessage("You leveled up to level " + ChatColor.YELLOW + ChatColor.BOLD + PrisonUtils.addCommasToNumber(getPlayerLevel()) + "!" + ChatColor.RESET +
                        "\nNext level: " + ChatColor.YELLOW + ChatColor.BOLD + PrisonUtils.prettyNum(getPlayerXP()) + "/" +PrisonUtils.prettyNum(getNextLevelRequirement()) + " XP");
                if (getPlayerLevel() % 10 == 0) {
                    String msg = ServerData.PLAYERS.getName(uuid) + " has reached level " + ChatColor.YELLOW + ChatColor.BOLD + PrisonUtils.addCommasToNumber(getPlayerLevel()) + "!";
                    for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(msg);
                }
            } else if (xp < getLevelRequirement(getPlayerLevel())) {
                removePlayerLevel(1);
            }
            else break;
        }

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
        setStringList("tempMoneyMultipliers", value);
        return this;
    }
    public BigDecimal addTempMoneyMultiplier(BigDecimal amount, long lengthInMS) {
        List<String> multipliers = getTempMoneyMultiplierList();
        multipliers.add(amount.toString() + "|" + (Instant.now().toEpochMilli() + lengthInMS));
        setTempMoneyMultiplierList(multipliers);
        return getTempMoneyMultiplier();
    }
    List<String> getTempMoneyMultiplierList() {
        List<String> initialMultipliers = getStringList("tempMoneyMultipliers");
        List<String> multipliers = new ArrayList<>(initialMultipliers);
        List<String> multipliersToRemove = new ArrayList<>();
        for (String multi : multipliers) {
            //amount|time
            if (Long.parseLong(multi.split("\\|")[1]) < Instant.now().toEpochMilli()) {
                multipliersToRemove.add(multi);
            }
        }
        multipliers.removeAll(multipliersToRemove);
        if (!multipliers.equals(initialMultipliers)) setStringList("tempMoneyMultipliers", multipliers);
        return multipliers;
    }

    /**
     * This does nothing for the time being
     */
    public BigDecimal getTokenMultiplier() {
        BigDecimal multi = BigDecimal.ZERO;
        //Factor in the consistency enchant
        return multi;
    }
    //Backpack
//    public PlayerData setBackpackContents(Map<BigDecimal, BigInteger> value) {
//        Map<String, String> map = new HashMap<>();
//        for (Map.Entry<BigDecimal, BigInteger> entry : value.entrySet()) {
//            map.put(entry.getKey().toString(), entry.getValue().toString());
//        }
//        setMap("backpackContents", map);
//        return this;
//    }


    public PlayerData setBackpackValue(long value) {
        setLong("backpackValue", value);
        return this;
    }
    public PlayerData addBackpackValue(long value) {
        return setBackpackValue(getBackpackValue() + value);
    }
    public long getBackpackValue() {
        return getLong("backpackValue");
    }
    public PlayerData setBackpackItemCount(long value) {
        setLong("backpackItemCount", value);
        return this;
    }
    public long getBackpackItemCount() {
        return getLong("backpackItemCount");
    }
    public PlayerData setBackpackSize(long value) {
        setLong("backpackSize", value);
        return this;
    }
    public long getBackpackSize() {
        return getLong("backpackSize");
    }
    public boolean getBackpackIsFull() {
        return getBackpackSize() <= getBackpackItemCount();
    }
//    public Map<String, String> getBackpackContents() {
//        return (Map<String, String>) getMap("backpackContents");
//    }

//    public BigInteger getBackpackAmountOf(Material mat) {
//        if (getBackpackContents().containsKey(mat.name())) return new BigInteger(getBackpackContents().get(mat.name()));
//        return BigInteger.ZERO;
//    }


    public void addAllToBackpack(Map<BigDecimal, BigInteger> whatToAdd) {
        if (getBackpackIsFull()) return;
        long itemCount = getBackpackItemCount();
        long size = getBackpackSize();
        long value = 0;
        for (Map.Entry<BigDecimal, BigInteger> entry : whatToAdd.entrySet()) {
            if (itemCount >= size) break; //The backpack is full
            long amount = entry.getValue().longValue();
            double key = entry.getKey().doubleValue();
            if (size >= itemCount + amount) { //The backpack can add the whole entry
                value += key * amount;
                itemCount += amount;
            } else { //The backpack is not currently full but will be full after adding this entry, figure out how much can fit in the backpack and add only that amount
                amount = size - itemCount;
                value += key * amount;
                itemCount = size;
                break; //The backpack is now full
            }
        }
        addBackpackValue(value);
        setBackpackItemCount(itemCount);
    }

//    public void addAllToBackpack(Map<BigDecimal, BigInteger> whatToAdd) {
//        if (getBackpackIsFull()) return;
//        BigInteger itemCount = getBackpackItemCount();
//        BigInteger size = getBackpackSize();
//
//        //Build the backpack's contents
//        Map<BigDecimal, BigInteger> backpackContents = new HashMap<>();
//        for (Map.Entry<String, String> entry : getBackpackContents().entrySet()) backpackContents.put(new BigDecimal(entry.getKey()).setScale(2, RoundingMode.HALF_UP), new BigInteger(entry.getValue()));
//
//        //Add what the backpack has room for
//        for (Map.Entry<BigDecimal, BigInteger> entry : whatToAdd.entrySet()) {
//            if (itemCount.equals(size)) break; //The backpack is full
//            BigInteger amount = entry.getValue();
//            if (size.compareTo(itemCount.add(amount)) > -1) { //The backpack can add the whole entry
//                BigDecimal key = entry.getKey().setScale(2, RoundingMode.HALF_UP);
//                if (backpackContents.containsKey(key)) amount = amount.add(backpackContents.get(key));
//                backpackContents.put(key, amount);
//                itemCount = itemCount.add(entry.getValue());
//            } else { //The backpack is not currently full but will be full after adding this entry, figure out how much can fit in the backpack and add only that amount
//                BigDecimal key = entry.getKey().setScale(2, RoundingMode.HALF_UP);
//                amount = size.subtract(itemCount);
//                if (backpackContents.containsKey(key)) amount = amount.add(backpackContents.get(key));
//                backpackContents.put(key, amount);
//                itemCount = size;
//                break; //The backpack is now full
//            }
//        }
//        setBackpackContents(backpackContents);
//        setBackpackItemCount(itemCount);
//    }
    public void sellBackpack(Player player, boolean sendChatMessage) {
        sellBackpack(player, sendChatMessage, "(x%MULTI%) Sold " + ChatColor.AQUA + "%TOTAL_BACKPACK_COUNT% " + ChatColor.WHITE + "blocks for: " + ChatColor.GREEN + "$%TOTAL_SELL_PRICE%");
    }
    public void sellBackpack(Player player, boolean sendChatMessage, String chatMessage) {
        BigDecimal multi = getMoneyMultiplier();
        //Factor in the merchant enchant
        ItemStack item = player.getInventory().getItemInMainHand();
        if (PrisonUtils.checkIsPrisonPickaxe(item)) {
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(item);
            if (pickaxe.getIsEnchantEnabled(PrisonEnchants.MERCHANT)) multi = multi.add(BigDecimal.valueOf(pickaxe.getEnchantLevel(PrisonEnchants.MERCHANT) / 1250d));
        }
        //multi = multi.add(BigDecimal.valueOf(0.5 / PrisonEnchants.MERCHANT.MAX_LEVEL * CustomEnchants.getEnchantLevel(player.getInventory().getItemInMainHand(), "merchant")));;
//        if (getBackpackItemCount().compareTo(BigInteger.ZERO) > 0) {
//            for (Map.Entry<String, String> entry : getBackpackContents().entrySet()) {
//                totalSellPrice = totalSellPrice.add(new BigDecimal(entry.getKey()).multiply(new BigDecimal(entry.getValue())));
//            }
//        }
        BigInteger soldFor = BigDecimal.valueOf(getBackpackValue()).multiply(multi).toBigInteger();
        new PlayerData(player).addMoney(soldFor);
        if (Gang.hasGang(player)) Gang.getGang(player).addMoneyMade(soldFor); //Track gang stats
        if (sendChatMessage) {
            chatMessage = chatMessage.replaceAll("%MULTI%", multi + "");
            chatMessage = chatMessage.replaceAll("%TOTAL_BACKPACK_COUNT%", PrisonUtils.prettyNum(getBackpackItemCount()) + "");
            chatMessage = chatMessage.replaceAll("%TOTAL_SELL_PRICE%", PrisonUtils.addCommasToNumber(soldFor) + "");
            player.sendMessage(chatMessage);
        }
        setBackpackItemCount(0);
        setBackpackValue(0);
    }




    //TabList
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
        Player player = Bukkit.getPlayer(getUUID());
        if (player != null) {
            PrisonUtils.updateLuckPermsForPlayerRanks(player);
        }
        return this;
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
        return getString("staffRank");
    }
    public PlayerData setStaffRank(String value) {
        setString("staffRank", value);
        return this;
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

    public PlayerData setChatTags(List<String> value) {
        setStringList("chatTags", PrisonUtils.removeDuplicatesInArrayList(value));
        return this;
    }
    public List<String> getChatTags() {
        return getStringList("chatTags");
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
        if (str.isEmpty()) return ChatColor.WHITE;
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
    @Deprecated
    public Material getUITheme() {
        String theme = getString("UITheme");
        if (theme.equals("")) {
            setUITheme(Material.LIGHT_BLUE_DYE);
            return Material.LIGHT_BLUE_DYE;
        }
        return Material.valueOf(theme);
    }
    @Deprecated
    public PlayerData setUITheme(Material value) {
        setString("UITheme", value.name());
        return this;
    }


    public ChatColor getPrimaryUITheme() {
        return getPrimaryUITheme(getUIThemeID());
    }
    public ChatColor getSecondaryUITheme() {
        return getSecondaryUITheme(getUIThemeID());
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
    public String getUIThemeID() {
        String str = getString("UIThemeID");
        if (str.isEmpty()) str = "b";
        return str;
    }
    public PlayerData setUIThemeID(String value) {
        setString("UIThemeID", value);
        return this;
    }
}
