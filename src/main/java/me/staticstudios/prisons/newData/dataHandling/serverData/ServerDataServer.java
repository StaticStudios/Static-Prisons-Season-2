package me.staticstudios.prisons.newData.dataHandling.serverData;

import me.staticstudios.prisons.newData.dataHandling.DataSet;
import me.staticstudios.prisons.newData.dataHandling.DataTypes;

import java.util.*;

public class ServerDataServer extends DataSet {
    public ServerDataServer() {
        super(DataTypes.SERVER, "server");
    }

    public int getVoteParty() {
        return getInt("voteParty");
    }
    public ServerDataServer setVoteParty(int value) {
        setInt("voteParty", value);
        return this;
    }
}
