package net.staticstudios.prisons.data.serverdata;

import net.staticstudios.prisons.data.datahandling.DataSet;
import net.staticstudios.prisons.data.datahandling.DataTypes;

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
