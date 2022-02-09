package me.staticstudios.prisons.data.serverData;


import me.staticstudios.prisons.data.PlayerBackpack;
import me.staticstudios.prisons.data.dataHandling.Data;
import me.staticstudios.prisons.data.dataHandling.DataSet;
import me.staticstudios.prisons.data.dataHandling.DataTypes;
import me.staticstudios.prisons.utils.Utils;
import org.apache.commons.lang.SerializationUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if (getData("money").bigInteger == null) {
            Data newData = new Data();
            newData.bigInteger = BigInteger.ZERO;
            setData("money", newData);
        }
        return (getData("money").bigInteger);
    }
    public Data setMoney(BigInteger value) {
        Data newData = new Data();
        newData.bigInteger = value;
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
        if (getData("tokens").bigInteger == null) {
            Data newData = new Data();
            newData.bigInteger = BigInteger.ZERO;
            setData("tokens", newData);
        }
        return (getData("tokens").bigInteger);
    }
    public Data setTokens(BigInteger value) {
        Data newData = new Data();
        newData.bigInteger = value;
        return setData("tokens", newData);
    }
    public Data addTokens(BigInteger value) {
        return setTokens(getTokens().add(value));
    }
    public Data removeTokens(BigInteger value) {
        return setTokens(getTokens().subtract(value));
    }

    //Time played
    public BigInteger getTimePlayed() {
        if (getData("timePlayed").bigInteger == null) {
            Data newData = new Data();
            newData.bigInteger = BigInteger.ZERO;
            setData("timePlayed", newData);
        }
        return (getData("timePlayed").bigInteger);
    }
    public Data setTimePlayed(BigInteger value) {
        Data newData = new Data();
        newData.bigInteger = value;
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
        if (getData("rawBlocksMined").bigInteger == null) {
            Data newData = new Data();
            newData.bigInteger = BigInteger.ZERO;
            setData("rawBlocksMined", newData);
        }
        return (getData("rawBlocksMined").bigInteger);
    }
    public Data setRawBlocksMined(BigInteger value) {
        Data newData = new Data();
        newData.bigInteger = value;
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
        if (getData("blocksMined").bigInteger == null) {
            Data newData = new Data();
            newData.bigInteger = BigInteger.ZERO;;
            setData("blocksMined", newData);
        }
        return (getData("blocksMined").bigInteger);
    }
    public Data setBlocksMined(BigInteger value) {
        Data newData = new Data();
        newData.bigInteger = value;
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
        if (getData("prestige").bigInteger == null) return BigInteger.ZERO;
        return getData("prestige").bigInteger;
    }
    public Data setPrestige(BigInteger value) {
        Data newData = new Data();
        newData.bigInteger = value;
        return setData("prestige", newData);
    }
    public Data addPrestige(BigInteger value) {
        return setPrestige(getPrestige().add(value));
    }
    public Data removePrestige(BigInteger value) {
        return setPrestige(getPrestige().subtract(value));
    }

    //Backpack
    public PlayerBackpack getBackpack() {
        if (getData("backpack").byteArr == null) return new PlayerBackpack();
            try {
                return (PlayerBackpack) new ObjectInputStream(new ByteArrayInputStream(getData("backpack").byteArr)).readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
    }
    public Data setBackpack(PlayerBackpack value) {
        Data newData = new Data();
        newData.byteArr = SerializationUtils.serialize(value);
        return setData("backpack", newData);
    }
    public void addBlocksToBackpack(Material type, BigInteger amount) {
        PlayerBackpack backpack = getBackpack();
        backpack.addAmountOf(type, amount);
        setBackpack(backpack);
    }
    public boolean checkIfBackpackIsFull() {
        return (getBackpack().isFull());
    }
    public void sellBackpack(Player player, boolean sendChatMessage) {
        PlayerBackpack backpack = getBackpack();
        backpack.sellBackpack(player, sendChatMessage);
        setBackpack(backpack);
    }
    public void updateTabListPrefixID() {
        if (!getStaffRank().equals("member") && !getStaffRank().equals("")) {
            setTabListPrefixID(getStaffRank());
        } else if (!getPlayerRank().equals("member")) {
            setTabListPrefixID(getPlayerRank());
        }
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




}
