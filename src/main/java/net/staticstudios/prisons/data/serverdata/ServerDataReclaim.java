package net.staticstudios.prisons.data.serverdata;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.datahandling.DataSet;
import net.staticstudios.prisons.data.datahandling.DataTypes;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerDataReclaim extends DataSet {
    public ServerDataReclaim() {
        super(DataTypes.SERVER, "reclaim");
    }

    public List<String> getReclaim(UUID playerUUID) {
        return getStringList(playerUUID.toString() + "-packageIDs");
    }
    public ServerDataReclaim setReclaim(UUID playerUUID, List<String> packageIDs) {
        setStringList(playerUUID.toString() + "-packageIDs", packageIDs);
        return this;
    }

    /**
     * This should only be called once at the start of the season to load the data into the main server data
     */
    @Deprecated
    public ServerDataReclaim addCurrentReclaim(UUID playerUUID, String packageIDs) {
        getReclaim(playerUUID).add(packageIDs);
        return this;
    }
    public ServerDataReclaim addNextReclaim(UUID playerUUID, String packageIDs) {
        List<String> lines = new ArrayList<>();
        lines.add(playerUUID + " | " + packageIDs);
        PrisonUtils.writeToAFile(new File(StaticPrisons.getInstance().getDataFolder(), "nextReclaim.txt"), lines, true);
        return this;
    }
    public ServerDataReclaim removeReclaim(UUID playerUUID, String packageIDs) {
        getStringList(playerUUID.toString() + "-packageIDs").remove(packageIDs);
        return this;
    }

}
