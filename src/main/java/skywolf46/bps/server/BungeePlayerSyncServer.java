package skywolf46.bps.server;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import skywolf46.bps.global.BPSVariable;
import skywolf46.bps.global.api.BPSGlobalAPI;
import skywolf46.bps.global.packets.PacketPlayerSyncEnd;
import skywolf46.bps.server.listener.PlayerJoinListener;
import skywolf46.bsl.global.api.BSLCoreAPI;
import skywolf46.bsl.global.util.BSLChannel;

import java.net.InetSocketAddress;

public class BungeePlayerSyncServer extends Plugin {
    @Override
    public void onEnable() {
        BPSGlobalAPI.init();
        BungeeCord.getInstance().getPluginManager().registerListener(this, new PlayerJoinListener());
        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_END).attachListener((chan, packet) -> {
            PacketPlayerSyncEnd end = (PacketPlayerSyncEnd) packet;
            ProxiedPlayer pp = BungeeCord.getInstance().getPlayer(end.getTarget());
            if (pp == null) {
//                BPSGlobalAPI.writer().printError("Cannot process sync of player " + end.getTarget() + " : Player is offline");
                return;
            }
            int port = ((InetSocketAddress) pp.getServer().getInfo().getSocketAddress()).getPort();
            BSLChannel bslc = BSLCoreAPI.getServer(port);
            if (bslc == null) {
                BPSGlobalAPI.writer().printError("Cannot process sync of player " + end.getTarget() + " : Target server not connected to BungeeSwitchHandler");
                return;
            }
            packet.resetBuffer();
            packet.retainBuffer();
            bslc.send(packet);
        });

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_FULLY_END).attachListener((chan, packet) -> {
            PacketPlayerSyncEnd end = (PacketPlayerSyncEnd) packet;
            ProxiedPlayer pp = BungeeCord.getInstance().getPlayer(end.getTarget());
            if (pp == null) {
//                BPSGlobalAPI.writer().printError("Cannot process sync of player " + end.getTarget() + " : Player is offline");
                return;
            }
            int port = ((InetSocketAddress) pp.getServer().getInfo().getSocketAddress()).getPort();
            BSLChannel bslc = BSLCoreAPI.getServer(port);
            if (bslc == null) {
                BPSGlobalAPI.writer().printError("Cannot process sync of player " + end.getTarget() + " : Target server not connected to BungeeSwitchHandler");
                return;
            }
            packet.resetBuffer();
            packet.retainBuffer();
            bslc.send(packet);
        });
    }
}
