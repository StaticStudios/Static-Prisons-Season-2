package me.staticstudios.prisons.data.serverData;


import me.staticstudios.prisons.data.dataHandling.Data;
import me.staticstudios.prisons.data.dataHandling.DataSet;
import me.staticstudios.prisons.data.dataHandling.DataTypes;
import me.staticstudios.prisons.islands.IslandManager;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class IslandData extends DataSet {
    private static final DataTypes type = DataTypes.ISLAND;
    public static final double DEFAULT_WARP_X = 0.5;
    public static final double DEFAULT_WARP_Y = 100;
    public static final double DEFAULT_WARP_Z = 0.5;
    String uuid;



    public IslandData(UUID uuid) {
        super(type, uuid.toString());
        this.uuid = uuid.toString();
    }
    public IslandData(String uuid) {
        super(type, uuid);
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }


    public int getGridNumber() {
        return getData("gridNum")._int;
    }
    public Data setGridNumber(int value) {
        Data newData = new Data();
        newData._int = value;
        return setData("gridNum", newData);
    }
    public int[] getCenterPosOfIslandOnGrid() {
        return IslandManager.getPosOfIslandOnGrid(getGridNumber());
    }
    //Island Bank - Cash
    public BigInteger getBankMoney() {
        if (getData("bankMoney")._string.equals("")) {
            Data newData = new Data();
            newData._string = "0";
            setData("bankMoney", newData);
        }
        return new BigInteger(getData("bankMoney")._string);
    }
    public Data setBankMoney(BigInteger value) {
        Data newData = new Data();
        newData._string = value.toString();
        return setData("bankMoney", newData);
    }
    public Data addBankMoney(BigInteger value) {
        return setBankMoney(getBankMoney().add(value));
    }
    public Data removeBankMoney(BigInteger value) {
        return setBankMoney(getBankMoney().subtract(value));
    }

    //Island members warp location
    public double getIslandMemberWarpX() {
        return getData("memberWarpLocationX")._double;
    }
    public double getIslandMemberWarpY() {
        return getData("memberWarpLocationY")._double;
    }
    public double getIslandMemberWarpZ() {
        return getData("memberWarpLocationZ")._double;
    }
    public Data setIslandMemberWarpX(double value) {
        Data newData = new Data();
        newData._double = value;
        return setData("memberWarpLocationX", newData);
    }
    public Data setIslandMemberWarpY(double value) {
        Data newData = new Data();
        newData._double = value;
        return setData("memberWarpLocationY", newData);
    }
    public Data setIslandMemberWarpZ(double value) {
        Data newData = new Data();
        newData._double = value;
        return setData("memberWarpLocationZ", newData);
    }

    //Island visitor warp location
    public double getIslandVisitorWarpX() {
        return getData("visitorWarpLocationX")._double;
    }
    public double getIslandVisitorWarpY() {
        return getData("visitorWarpLocationY")._double;
    }
    public double getIslandVisitorWarpZ() {
        return getData("visitorWarpLocationZ")._double;
    }
    public Data setIslandVisitorWarpX(double value) {
        Data newData = new Data();
        newData._double = value;
        return setData("visitorWarpLocationX", newData);
    }
    public Data setIslandVisitorWarpY(double value) {
        Data newData = new Data();
        newData._double = value;
        return setData("visitorWarpLocationY", newData);
    }
    public Data setIslandVisitorWarpZ(double value) {
        Data newData = new Data();
        newData._double = value;
        return setData("visitorWarpLocationZ", newData);
    }

    //Island Name
    public String getIslandName() {
        return getData("islandName")._string;
    }
    public Data setIslandName(String value) {
        new ServerData().removeSkyblockIslandNameToUUID(getIslandName());
        new ServerData().removeSkyblockIslandUUIDToName(getUUID());
        Data newData = new Data();
        newData._string = value;
        setData("islandName", newData);
        new ServerData().putSkyblockIslandUUIDToName(uuid, value);
        new ServerData().putSkyblockIslandNameToUUID(value, uuid);
        return getData("islandName");
    }

    //Island Owner
    public String getIslandOwnerUUID() {
        return getData("islandOwner")._string;
    }
    public Data setIslandOwnerUUID(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("islandOwner", newData);
    }

    //Island Manager
    public String getIslandManagerUUID() {
        return getData("islandManager")._string;
    }
    public Data setIslandManagerUUID(String value) {
        Data newData = new Data();
        newData._string = value;
        return setData("islandManager", newData);
    }

    //Island Admins
    public List<String> getIslandAdminUUIDS() {
        return getData("islandAdmins").list;
    }
    public Data setIslandAdminUUIDS(List<String> value) {
        Data newData = new Data();
        newData.list = value;
        return setData("islandAdmins", newData);
    }
    public Data addIslandAdminUUID(String value) {
        List<String> uuids = getIslandAdminUUIDS();
        uuids.add(value);
        return setIslandAdminUUIDS(uuids);
    }
    public Data removeIslandAdminUUID(String value) {
        List<String> uuids = getIslandAdminUUIDS();
        uuids.remove(value);
        return setIslandAdminUUIDS(uuids);
    }
    //Island Members - Management perms, not players
    public List<String> getIslandMemberUUIDS() {
        return getData("islandMembers").list;
    }
    public Data setIslandMemberUUIDS(List<String> value) {
        Data newData = new Data();
        newData.list = value;
        return setData("islandMembers", newData);
    }
    public Data addIslandMemberUUID(String value) {
        List<String> uuids = getIslandMemberUUIDS();
        uuids.add(value);
        return setIslandMemberUUIDS(uuids);
    }
    public Data removeIslandMemberUUID(String value) {
        List<String> uuids = getIslandMemberUUIDS();
        uuids.remove(value);
        return setIslandMemberUUIDS(uuids);
    }

    //Island Players - all players on the island
    public List<String> getIslandPlayerUUIDS() {
        return getData("islandPlayers").list;
    }
    public Data setIslandPlayerUUIDS(List<String> value) {
        Data newData = new Data();
        newData.list = value;
        return setData("islandPlayers", newData);
    }
    public Data addIslandPlayerUUID(String value) {
        List<String> uuids = getIslandPlayerUUIDS();
        uuids.add(value);
        return setIslandPlayerUUIDS(uuids);
    }
    public Data removeIslandPlayerUUID(String value) {
        List<String> uuids = getIslandPlayerUUIDS();
        uuids.remove(value);
        return setIslandPlayerUUIDS(uuids);
    }

    //Banned players
    public List<String> getBannedPlayerUUIDS() {
        return getData("bannedPlayers").list;
    }
    public Data setBannedPlayerUUIDS(List<String> value) {
        Data newData = new Data();
        newData.list = value;
        return setData("bannedPlayers", newData);
    }
    public Data addBannedPlayerUUID(String value) {
        List<String> uuids = getBannedPlayerUUIDS();
        uuids.add(value);
        return setBannedPlayerUUIDS(uuids);
    }
    public Data removeBannedPlayerUUID(String value) {
        List<String> uuids = getBannedPlayerUUIDS();
        uuids.remove(value);
        return setBannedPlayerUUIDS(uuids);
    }

    //Island Settings
    public Data setAllowInvites(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        setData("allowInvites", newData);
        return getData("allowInvites");
    }
    public boolean getAllowInvites() {
        return getData("allowInvites")._boolean;
    }
    public Data setAllowVisitors(boolean value) {
        Data newData = new Data();
        newData._boolean = value;
        setData("allowVisitors", newData);
        return getData("allowVisitors");
    }
    public boolean getAllowVisitors() {
        return getData("allowVisitors")._boolean;
    }
}
