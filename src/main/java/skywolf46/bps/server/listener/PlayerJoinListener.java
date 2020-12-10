package skywolf46.bps.server.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import skywolf46.bps.global.packets.PacketPlayerSyncStart;
import skywolf46.bsl.global.api.BSLClientProxy;
import skywolf46.bsl.global.api.BSLCoreAPI;
import skywolf46.bsl.global.util.BSLChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class PlayerJoinListener implements Listener {
    private HashMap<ProxiedPlayer, Boolean> dat = new HashMap<>();

    @EventHandler
    public void ev(ServerConnectEvent e) {
        switch (e.getReason()) {
            case JOIN_PROXY:
            case LOBBY_FALLBACK: {
                dat.put(e.getPlayer(), true);
            }
            break;
        }
    }
    @EventHandler
    public void ev(ServerConnectedEvent e) {
        if (e.getServer() != null) {
            if (dat.containsKey(e.getPlayer())) {
                dat.remove(e.getPlayer());
                int port = ((InetSocketAddress) e.getServer().getSocketAddress()).getPort();
                BSLClientProxy bsl = BSLCoreAPI.server(port);
                bsl.send(new PacketPlayerSyncStart(e.getPlayer().getUniqueId()));
                return;
            }
        }
    }
}