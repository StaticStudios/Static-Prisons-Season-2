package net.staticstudios.prisons.data;

import net.staticstudios.prisons.data.dataHandling.DataSet;
import net.staticstudios.prisons.data.dataHandling.DataTypes;
import net.staticstudios.prisons.islands.IslandManager;
import net.staticstudios.prisons.data.serverData.ServerData;

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
        return getInt("gridNum");
    }
    public IslandData setGridNumber(int value) {
        setInt("gridNum", value);
        return this;
    }
    public int[] getCenterPosOfIslandOnGrid() {
        return IslandManager.getPosOfIslandOnGrid(getGridNumber());
    }
    //Island Bank - Cash
    public BigInteger getBankMoney() {
        return getBigInt("bankMoney");
    }
    public IslandData setBankMoney(BigInteger value) {
        setBigInt("bankMoney", value);
        return this;
    }
    public IslandData addBankMoney(BigInteger value) {
        return setBankMoney(getBankMoney().add(value));
    }
    public IslandData removeBankMoney(BigInteger value) {
        return setBankMoney(getBankMoney().subtract(value));
    }

    //Island members warp location
    public double getIslandMemberWarpX() {
        return getDouble("memberWarpLocationX");
    }
    public double getIslandMemberWarpY() {
        return getDouble("memberWarpLocationY");
    }
    public double getIslandMemberWarpZ() {
        return getDouble("memberWarpLocationZ");
    }
    public IslandData setIslandMemberWarpX(double value) {
        setDouble("memberWarpLocationX", value);
        return this;
    }
    public IslandData setIslandMemberWarpY(double value) {
        setDouble("memberWarpLocationY", value);
        return this;
    }
    public IslandData setIslandMemberWarpZ(double value) {
        setDouble("memberWarpLocationZ", value);
        return this;
    }

    //Island visitor warp location
    public double getIslandVisitorWarpX() {
        return getDouble("visitorWarpLocationX");
    }
    public double getIslandVisitorWarpY() {
        return getDouble("visitorWarpLocationY");
    }
    public double getIslandVisitorWarpZ() {
        return getDouble("visitorWarpLocationZ");
    }
    public IslandData setIslandVisitorWarpX(double value) {
        setDouble("visitorWarpLocationX", value);
        return this;
    }
    public IslandData setIslandVisitorWarpY(double value) {
        setDouble("visitorWarpLocationY", value);
        return this;
    }
    public IslandData setIslandVisitorWarpZ(double value) {
        setDouble("visitorWarpLocationZ", value);
        return this;
    }

    //Island Name
    public String getIslandName() {
        return getString("islandName");
    }
    public IslandData setIslandName(String value) {
        ServerData.ISLANDS.updateNameAndUUID(getIslandName(), UUID.fromString(getUUID()));
        setString("islandName", value);
        return this;
    }

    //Island Owner
    public String getIslandOwnerUUID() {
        return getString("islandOwner");
    }
    public IslandData setIslandOwnerUUID(String value) {
        setString("islandOwner", value);
        return this;
    }

    //Island Manager
    public String getIslandManagerUUID() {
        return getString("islandManager");
    }
    public IslandData setIslandManagerUUID(String value) {
        setString("islandManager", value);
        return this;
    }

    //Island Admins
    public List<String> getIslandAdminUUIDS() {
        return getStringList("islandAdmins");
    }
    public IslandData setIslandAdminUUIDS(List<String> value) {
        setStringList("islandAdmins", value);
        return this;
    }
    public IslandData addIslandAdminUUID(String value) {
        List<String> uuids = getIslandAdminUUIDS();
        uuids.add(value);
        return setIslandAdminUUIDS(uuids);
    }
    public IslandData removeIslandAdminUUID(String value) {
        List<String> uuids = getIslandAdminUUIDS();
        uuids.remove(value);
        return setIslandAdminUUIDS(uuids);
    }
    //Island Members - Management perms, not players
    public List<String> getIslandMemberUUIDS() {
        return getStringList("islandMembers");
    }
    public IslandData setIslandMemberUUIDS(List<String> value) {
        setStringList("islandMembers", value);
        return this;
    }
    public IslandData addIslandMemberUUID(String value) {
        List<String> uuids = getIslandMemberUUIDS();
        uuids.add(value);
        return setIslandMemberUUIDS(uuids);
    }
    public IslandData removeIslandMemberUUID(String value) {
        List<String> uuids = getIslandMemberUUIDS();
        uuids.remove(value);
        return setIslandMemberUUIDS(uuids);
    }

    //Island Players - all players on the island
    public List<String> getIslandPlayerUUIDS() {
        return getStringList("islandPlayers");
    }
    public IslandData setIslandPlayerUUIDS(List<String> value) {
        setStringList("islandPlayers", value);
        return this;
    }
    public IslandData addIslandPlayerUUID(String value) {
        List<String> uuids = getIslandPlayerUUIDS();
        uuids.add(value);
        return setIslandPlayerUUIDS(uuids);
    }
    public IslandData removeIslandPlayerUUID(String value) {
        List<String> uuids = getIslandPlayerUUIDS();
        uuids.remove(value);
        return setIslandPlayerUUIDS(uuids);
    }

    //Banned players
    public List<String> getBannedPlayerUUIDS() {
        return getStringList("bannedPlayers");
    }
    public IslandData setBannedPlayerUUIDS(List<String> value) {
        setStringList("bannedPlayers", value);
        return this;
    }
    public IslandData addBannedPlayerUUID(String value) {
        List<String> uuids = getBannedPlayerUUIDS();
        uuids.add(value);
        return setBannedPlayerUUIDS(uuids);
    }
    public IslandData removeBannedPlayerUUID(String value) {
        List<String> uuids = getBannedPlayerUUIDS();
        uuids.remove(value);
        return setBannedPlayerUUIDS(uuids);
    }

    //Island Settings
    public IslandData setAllowInvites(boolean value) {
        setBoolean("allowInvites", value);
        return this;
    }
    public boolean getAllowInvites() {
        return getBoolean("allowInvites");
    }
    public IslandData setAllowVisitors(boolean value) {
        setBoolean("allowVisitors", value);
        return this;
    }
    public boolean getAllowVisitors() {
        return getBoolean("allowVisitors");
    }
}
