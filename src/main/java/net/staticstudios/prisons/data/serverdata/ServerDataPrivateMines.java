package net.staticstudios.prisons.data.serverdata;

import net.staticstudios.prisons.data.datahandling.DataSet;
import net.staticstudios.prisons.data.datahandling.DataTypes;

import java.util.*;

public class ServerDataPrivateMines extends DataSet {
    public ServerDataPrivateMines() {
        super(DataTypes.SERVER, "privateMines");
    }

    //Name, UUID
    private Map<String, UUID> getNamesToUUIDs() {
        return getStringUUIDMap("namesToUUIDs");
    }

    private ServerDataPrivateMines setNamesToUUIDs(Map<String, UUID> value) {
        setStringUUIDMap("namesToUUIDs", value);
        return this;
    }

    public ServerDataPrivateMines putNameToUUID(String name, UUID uuid) {
        getNamesToUUIDs().put(name, uuid);
        return this;
    }

    public ServerDataPrivateMines putUUIDToName(UUID uuid, String name) {
        getUUIDsToNames().put(uuid, name);
        return this;
    }

    //UUID, Name
    private Map<UUID, String> getUUIDsToNames() {
        return getUUIDStringMap("uuidToNames");
    }

    private ServerDataPrivateMines setUUIDSToNames(Map<UUID, String> value) {
        setUUIDStringMap("uuidToNames", value);
        return this;
    }

    //Name, UUID
    private Map<String, UUID> getNamesToUUIDsLowercase() {
        return getStringUUIDMap("namesToUUIDsLowerCase");
    }

    private ServerDataPrivateMines setNamesToUUIDsLowercase(Map<String, UUID> value) {
        setStringUUIDMap("namesToUUIDs", value);
        return this;
    }

    public ServerDataPrivateMines updateNameAndUUID(String name, UUID uuid) {
        String oldName = getName(uuid);
        getNamesToUUIDs().remove(oldName);
        getNamesToUUIDs().put(name, uuid);
        if (oldName != null) getNamesToUUIDsLowercase().remove(oldName.toLowerCase());
        getNamesToUUIDsLowercase().put(name.toLowerCase(), uuid);
        getUUIDsToNames().remove(uuid);
        getUUIDsToNames().put(uuid, name);
        return this;
    }

    public ServerDataPrivateMines delete(UUID uuid) {
        getNamesToUUIDs().remove(getName(uuid));
        getNamesToUUIDsLowercase().remove(getName(uuid).toLowerCase());
        getUUIDsToNames().remove(uuid);
        return this;
    }

    public UUID getUUID(String name) {
        return getNamesToUUIDs().get(name);
    }

    public String getName(UUID uuid) {
        return getUUIDsToNames().get(uuid);
    }

    public UUID getUUIDIgnoreCase(String name) {
        return getNamesToUUIDsLowercase().get(name.toLowerCase());
    }

    public List<String> getAllNames() {
        return new ArrayList<>(getNamesToUUIDs().keySet());
    }

    public Set<String> getAllNamesLowercase() {
        return getNamesToUUIDsLowercase().keySet();
    }

    public List<UUID> getAllUUIDs() {
        return new ArrayList<>(getUUIDsToNames().keySet());
    }

    public int getAmountOfMinesOnGrid() {
        return getInt("amountOfIslandsOnGrid");
    }

    public ServerDataPrivateMines setAmountOfMinesOnGrid(int value) {
        setInt("amountOfIslandsOnGrid", value);
        return this;
    }
}
