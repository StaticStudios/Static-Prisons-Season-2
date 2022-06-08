package net.staticstudios.prisons.data.dataHandling.serverData;

import net.staticstudios.prisons.data.dataHandling.DataSet;
import net.staticstudios.prisons.data.dataHandling.DataTypes;

import java.util.*;

public class ServerDataPrivateMines extends DataSet {
    public ServerDataPrivateMines() {
        super(DataTypes.SERVER, "privateMines");
    }

    //Name, UUID
    private Map<String, String> getNamesToUUIDs() {
        return (Map<String, String>) getMap("namesToUUIDs");
    }
    private ServerDataPrivateMines setNamesToUUIDs(Map<String, String> value) {
        setMap("namesToUUIDs", value);
        return this;
    }
    public ServerDataPrivateMines putNameToUUID(String name, UUID uuid) {
        getNamesToUUIDs().put(name, uuid.toString());
        return this;
    }
    public ServerDataPrivateMines putUUIDToName(UUID uuid, String name) {
        getUUIDsToNames().put(uuid.toString(), name);
        return this;
    }
    //UUID, Name
    private Map<String, String> getUUIDsToNames() {
        return (Map<String, String>) getMap("uuidToNames");
    }
    private ServerDataPrivateMines setUUIDSToNames(Map<String, String> value) {
        setMap("uuidToNames", value);
        return this;
    }
    //Name, UUID
    private Map<String, String> getNamesToUUIDsLowercase() {
        return (Map<String, String>) getMap("namesToUUIDsLowerCase");
    }
    private ServerDataPrivateMines setNamesToUUIDsLowercase(Map<String, String> value) {
        setMap("namesToUUIDs", value);
        return this;
    }

    public ServerDataPrivateMines updateNameAndUUID(String name, UUID uuid) {
        String oldName = getName(uuid);
        getNamesToUUIDs().remove(oldName);
        getNamesToUUIDs().put(name, uuid.toString());
        if (oldName != null ) getNamesToUUIDsLowercase().remove(oldName.toLowerCase());
        getNamesToUUIDsLowercase().put(name.toLowerCase(), uuid.toString());
        getUUIDsToNames().remove(uuid.toString());
        getUUIDsToNames().put(uuid.toString(), name);
        return this;
    }
    public ServerDataPrivateMines delete(UUID uuid) {
        getNamesToUUIDs().remove(getName(uuid));
        getNamesToUUIDsLowercase().remove(getName(uuid).toLowerCase());
        getUUIDsToNames().remove(uuid.toString());
        return this;
    }
    public UUID getUUID(String name) { return UUID.fromString(getNamesToUUIDs().get(name)); }
    public String getName(UUID uuid) { return getUUIDsToNames().get(uuid.toString()); }
    public UUID getUUIDIgnoreCase(String name) { return UUID.fromString(getNamesToUUIDsLowercase().get(name.toLowerCase())); }
    public List<String> getAllNames() {
        return new ArrayList<>(getNamesToUUIDs().keySet());
    }
    public Set<String> getAllNamesLowercase() {
        return getNamesToUUIDsLowercase().keySet();
    }
    public List<String> getAllUUIDsAsStrings() {
        return new ArrayList<>(getUUIDsToNames().keySet());
    }

    public int getAmountOfMinesOnGrid() { return getInt("amountOfIslandsOnGrid"); }
    public ServerDataPrivateMines setAmountOfMinesOnGrid(int value) {
        setInt("amountOfIslandsOnGrid", value);
        return this;
    }
}
