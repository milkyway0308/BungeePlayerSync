package skywolf46.bps.client.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import skywolf46.bps.global.api.BPSGlobalAPI;
import skywolf46.bps.global.packets.PacketPlayerSyncFullyEnd;
import skywolf46.bsl.global.BungeeSwitchListenerCore;
import skywolf46.bsl.global.api.BSLCoreAPI;

public class PlayerQuitListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void sync(PlayerQuitEvent e) {
        BungeeSwitchListenerCore.getChannel(-1).send(new PacketPlayerSyncFullyEnd(BPSGlobalAPI.getSyncTargets(), e.getPlayer().getUniqueId()));
    }
}
