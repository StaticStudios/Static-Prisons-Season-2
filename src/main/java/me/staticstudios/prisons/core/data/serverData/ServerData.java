package me.staticstudios.prisons.core.data.serverData;

import me.staticstudios.prisons.core.data.dataHandling.Data;
import me.staticstudios.prisons.core.data.dataHandling.DataSet;
import me.staticstudios.prisons.core.data.dataHandling.DataSets;
import me.staticstudios.prisons.core.data.dataHandling.DataTypes;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class ServerData extends DataSet {
    private static final DataTypes type = DataTypes.SERVER;
    String uuid = "server";



    public ServerData() {
        super(type, "server");
    }

    public String getUUID() {
        return uuid;
    }

    public void firstTimePlayerJoined(String playerName, String playerUUID) {
        putPlayerNamesToUUID(playerName, playerUUID);
        putPlayerUUIDsToName(playerUUID, playerName);
        addJoinedPlayersUUID(playerUUID);
        Bukkit.getLogger().log(Level.INFO, "Player has joined for the first time with the uuid: " + playerUUID + " and the name: " + playerName);
        PlayerData playerData = new PlayerData(playerUUID);
        playerData.setPrivateMineMat(Material.STONE);
    }

    //PlayersWhoHaveJoined
    public List<String> getAllJoinedPlayersUUIDs() {
        if (getData("allJoinedPlayersUUIDs").list == null) {
            Data newData = new Data();
            newData.list = new ArrayList<>();
            setData("allJoinedPlayersUUIDs", newData);
        }
        return (List<String>) getData("allJoinedPlayersUUIDs").list;
    }
    public Data setAllJoinedPlayersUUIDs(List<String> value) {
        Data newData = new Data();
        newData.list = value;
        return setData("allJoinedPlayersUUIDs", newData);
    }
    public Data addJoinedPlayersUUID(String value) {
        List<String> allJoins = getAllJoinedPlayersUUIDs();
        allJoins.add(value);
        return setAllJoinedPlayersUUIDs(allJoins);
    }
    public Data removeJoinedPlayersUUID(String value) {
        List<String> allJoins = getAllJoinedPlayersUUIDs();
        allJoins.remove(value);
        return setAllJoinedPlayersUUIDs(allJoins);
    }

    public Map<String, String> getPlayerNamesToUUIDsMap() {
        if (getData("playerNamesToUUIDs").map == null) {
            Data newData = new Data();
            newData.map = new HashMap<>();
            setData("playerNamesToUUIDs", newData);
        }
        return (Map<String, String>) getData("playerNamesToUUIDs").map;
    }

    public Map<String, String> getPlayerUUIDsToNamesMap() {
        if (getData("playerUUIDsToNames").map == null) {
            Data newData = new Data();
            newData.map = new HashMap<>();
            setData("playerUUIDsToNames", newData);
        }
        return (Map<String, String>) getData("playerUUIDsToNames").map;
    }

    public Data setPlayerNamesToUUIDsMap(Map<String, String> value) {
        Data newData = new Data();
        newData.map = value;
        return setData("playerNamesToUUIDs", newData);
    }
    public Data setPlayerUUIDsToNamesMap(Map<String, String> value) {
        Data newData = new Data();
        newData.map = value;
        return setData("playerUUIDsToNames", newData);
    }

    public Data putPlayerNamesToUUID(String name, String uuid) {
        Map<String, String> map = getPlayerNamesToUUIDsMap();
        map.put(name, uuid);
        return setPlayerNamesToUUIDsMap(map);
    }
    public Data putPlayerUUIDsToName(String uuid, String name) {
        Map<String, String> map = getPlayerUUIDsToNamesMap();
        map.put(uuid, name);
        return setPlayerUUIDsToNamesMap(map);
    }
    public Data removePlayerNamesToUUID(String key) {
        Map<String, String> map = getPlayerNamesToUUIDsMap();
        map.remove(key);
        return setPlayerNamesToUUIDsMap(map);
    }
    public Data removePlayerUUIDsToName(String key) {
        Map<String, String> map = getPlayerUUIDsToNamesMap();
        map.remove(key);
        return setPlayerUUIDsToNamesMap(map);
    }

    public boolean checkIfPlayerHasJoinedBeforeByUUID(String value) {
        return getPlayerUUIDsToNamesMap().containsKey(value);
    }
    public boolean checkIfPlayerHasJoinedBeforeByName(String value) {
        return getPlayerNamesToUUIDsMap().containsKey(value);
    }
    public String getPlayerNameFromUUID(String value) {
        return getPlayerUUIDsToNamesMap().get(value);
    }
    public String getPlayerUUIDFromName(String value) {
        return getPlayerNamesToUUIDsMap().get(value);
    }

    public Map<String, String> getSkyblockIslandNamesToUUIDsMap() {
        return getData("skyblockIslandNamesToUUIDsMap").map;
    }
    public Data setSkyblockIslandNamesToUUIDsMap(Map<String, String> value) {
        Data newData = new Data();
        newData.map = value;
        return setData("skyblockIslandNamesToUUIDsMap", newData);
    }
    public Data putSkyblockIslandNameToUUID(String name, String uuid) {
        Map<String, String> map = getSkyblockIslandNamesToUUIDsMap();
        map.put(name, uuid);
        return setSkyblockIslandNamesToUUIDsMap(map);
    }
    public Data removeSkyblockIslandNameToUUID(String name) {
        Map<String, String> map = getSkyblockIslandNamesToUUIDsMap();
        map.remove(name);
        return setSkyblockIslandNamesToUUIDsMap(map);
    }

    public Map<String, String> getSkyblockIslandUUIDsToNamesMap() {
        return getData("skyblockIslandUUIDsToNamesMap").map;
    }
    public Data setSkyblockIslandUUIDsToNamesMap(Map<String, String> value) {
        Data newData = new Data();
        newData.map = value;
        return setData("skyblockIslandUUIDsToNamesMap", newData);
    }
    public Data putSkyblockIslandUUIDToName(String uuid, String name) {
        Map<String, String> map = getSkyblockIslandUUIDsToNamesMap();
        map.put(uuid, name);
        return setSkyblockIslandUUIDsToNamesMap(map);
    }
    public Data removeSkyblockIslandUUIDToName(String uuid) {
        Map<String, String> map = getSkyblockIslandUUIDsToNamesMap();
        map.remove(uuid);
        return setSkyblockIslandUUIDsToNamesMap(map);
    }

    public String getIslandNameFromUUID(String value) {
        return getSkyblockIslandUUIDsToNamesMap().get(value);
    }
    public String getIslandUUIDFromName(String value) {
        return getSkyblockIslandNamesToUUIDsMap().get(value);
    }

    public void removeSkyblockIslandFromUUID(String value) {
        DataSets.getDataSet(DataTypes.ISLAND, value).dataSet.remove(value);
    }

    public int getVoteParty() {
        return getData("voteParty")._int;
    }
    public Data setVoteParty(int value) {
        Data newData = new Data();
        newData._int = value;
        return setData("voteParty", newData);
    }
    public int getAmountOfIslands() {
        return getData("amountIslands")._int;
    }
    public Data setAmountOfIslands(int value) {
        Data newData = new Data();
        newData._int = value;
        return setData("amountIslands", newData);
    }


}
