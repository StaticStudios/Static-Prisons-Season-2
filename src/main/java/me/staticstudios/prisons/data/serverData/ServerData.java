package me.staticstudios.prisons.data.serverData;

import me.staticstudios.prisons.data.dataHandling.Data;
import me.staticstudios.prisons.data.dataHandling.DataSet;
import me.staticstudios.prisons.data.dataHandling.DataSets;
import me.staticstudios.prisons.data.dataHandling.DataTypes;
import org.bukkit.Bukkit;

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
    }

    //PlayersWhoHaveJoined
    public List<String> getAllJoinedPlayersUUIDs() {
        if (getData("allJoinedPlayersUUIDs").object == null) {
            Data newData = new Data();
            newData.object = new ArrayList<>();
            setData("allJoinedPlayersUUIDs", newData);
        }
        return (List<String>) getData("allJoinedPlayersUUIDs").object;
    }
    public Data setAllJoinedPlayersUUIDs(List<String> value) {
        Data newData = new Data();
        newData.object = value;
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
        if (getData("playerNamesToUUIDs").object == null) {
            Data newData = new Data();
            newData.object = new HashMap<>();
            setData("playerNamesToUUIDs", newData);
        }
        return (Map<String, String>) getData("playerNamesToUUIDs").object;
    }

    public Map<String, String> getPlayerUUIDsToNamesMap() {
        if (getData("playerUUIDsToNames").object == null) {
            Data newData = new Data();
            newData.object = new HashMap<>();
            setData("playerUUIDsToNames", newData);
        }
        return (Map<String, String>) getData("playerUUIDsToNames").object;
    }

    public Data setPlayerNamesToUUIDsMap(Map<String, String> value) {
        Data newData = new Data();
        newData.object = value;
        return setData("playerNamesToUUIDs", newData);
    }
    public Data setPlayerUUIDsToNamesMap(Map<String, String> value) {
        Data newData = new Data();
        newData.object = value;
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


    public Map<String, String> getCellNamesToUUIDsMap() {
        if (getData("skyblockIslandNamesToUUIDsMap").object == null) {
            Data newData = new Data();
            newData.object = new HashMap<>();
            setData("skyblockIslandNamesToUUIDsMap", newData);
        }
        return (Map<String, String>) getData("skyblockIslandNamesToUUIDsMap").object;
    }
    public Data setCellNamesToUUIDsMap(Map<String, String> value) {
        Data newData = new Data();
        newData.object = value;
        return setData("skyblockIslandNamesToUUIDsMap", newData);
    }
    public Data putCellNameToUUID(String name, String uuid) {
        Map<String, String> map = getCellNamesToUUIDsMap();
        map.put(name, uuid);
        return setCellNamesToUUIDsMap(map);
    }
    public Data removeCellNameToUUID(String name) {
        Map<String, String> map = getCellNamesToUUIDsMap();
        map.remove(name);
        return setCellNamesToUUIDsMap(map);
    }


    public Map<String, String> getCellUUIDsToNamesMap() {
        if (getData("cellUUIDsToNamesMap").object == null) {
            Data newData = new Data();
            newData.object = new HashMap<>();
            setData("cellUUIDsToNamesMap", newData);
        }
        return (Map<String, String>) getData("cellUUIDsToNamesMap").object;
    }
    public Data setCellUUIDsToNamesMap(Map<String, String> value) {
        Data newData = new Data();
        newData.object = value;
        return setData("cellUUIDsToNamesMap", newData);
    }
    public Data putCellUUIDToName(String uuid, String name) {
        Map<String, String> map = getCellUUIDsToNamesMap();
        map.put(uuid, name);
        return setCellUUIDsToNamesMap(map);
    }
    public Data removeCellUUIDToName(String uuid) {
        Map<String, String> map = getCellUUIDsToNamesMap();
        map.remove(uuid);
        return setCellUUIDsToNamesMap(map);
    }

    public String getIslandNameFromUUID(String value) {
        return getCellUUIDsToNamesMap().get(value);
    }
    public String getIslandUUIDFromName(String value) {
        return getCellNamesToUUIDsMap().get(value);
    }

    public void removeCellFromUUID(String value) {
        DataSets.getDataSet(DataTypes.CELLS, value).dataSet.remove(value);
    }
}
