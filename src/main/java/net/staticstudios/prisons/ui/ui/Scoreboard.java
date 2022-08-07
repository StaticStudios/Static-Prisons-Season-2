package net.staticstudios.prisons.ui.ui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

public class Scoreboard { //todo Not done
    enum ScoreboardType {
        MAIN,
        PVP,
        KOTH
    }
    ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    public static final String MAIN_SCOREBOARD_ID = "gameSidebar";

    public Player player;
    public ScoreboardType type = ScoreboardType.MAIN;


    String getTitle() {
        return "title";
    }

    public void updateBoard() {
        switch (type) {
            case MAIN -> {
                PacketContainer displayPacket = protocolManager.createPacket(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
                displayPacket.getStrings().write(0,MAIN_SCOREBOARD_ID);
                displayPacket.getStrings().write(1,getTitle());
                //todo update values
            }
        }

    }

    void createBoards() {
        PacketContainer mainObjectivePacket = protocolManager.createPacket(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        mainObjectivePacket.getStrings().write(0, MAIN_SCOREBOARD_ID);
        mainObjectivePacket.getBytes().write(1, (byte) 0);
        //todo other boards
    }


}
