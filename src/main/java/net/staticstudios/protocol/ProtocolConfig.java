package net.staticstudios.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.admin.AdminManager;
import org.bukkit.Bukkit;

import java.util.stream.Collectors;

public class ProtocolConfig {

    private final ProtocolManager protocolManager;

    public ProtocolConfig() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        initServerPingListener();
    }

    private void initServerPingListener() {
        protocolManager.addPacketListener(new PacketAdapter(
                StaticPrisons.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Status.Server.SERVER_INFO
        ) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                ping.setPlayersOnline(Bukkit.getOnlinePlayers().size() - AdminManager.getHiddenPlayers().stream().filter(uuid -> Bukkit.getPlayer(uuid) != null).toList().size());
                ping.setPlayers(Bukkit.getOnlinePlayers().stream().filter(player -> !AdminManager.isHidden(player)).map(WrappedGameProfile::fromPlayer).collect(Collectors.toList()));
            }
        });
    }
}
