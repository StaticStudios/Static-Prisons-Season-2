package net.staticstudios.prisons.data.dataHandling.serverData;

import net.staticstudios.prisons.data.dataHandling.DataSet;
import net.staticstudios.prisons.data.dataHandling.DataTypes;

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
