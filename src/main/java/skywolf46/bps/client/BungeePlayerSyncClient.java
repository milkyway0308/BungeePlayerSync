package skywolf46.bps.client;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.bps.client.event.impl.PlayerInitialJoinEvent;
import skywolf46.bps.client.event.impl.PlayerSyncCompleteEvent;
import skywolf46.bps.client.event.impl.PlayerSyncEndEvent;
import skywolf46.bps.client.event.impl.PlayerSyncIncompleteEvent;
import skywolf46.bps.global.BPSVariable;
import skywolf46.bps.global.api.BPSGlobalAPI;
import skywolf46.bps.global.packets.PacketPlayerSyncEnd;
import skywolf46.bps.global.packets.PacketPlayerSyncFullyEnd;
import skywolf46.bps.global.packets.PacketPlayerSyncStart;
import skywolf46.bsl.global.api.BSLCoreAPI;
import skywolf46.commandannotation.CommandAnnotation;

public class BungeePlayerSyncClient extends JavaPlugin {
    @Override
    public void onEnable() {
        CommandAnnotation.init(this);
        BPSGlobalAPI.init();
//        BSLCoreAPI.registerPacket(0x9817, new TestPacket());
//        BSLCoreAPI.getPacket(0x9817).register(buf -> new TestPacket());
//        BSLCoreAPI.getPacket(0x9817).register((packet, buf) -> {
//
//        });
//
//        BSLCoreAPI.getPacket(0x9817).attachListener((chan, packet) -> {
//            System.out.println("Listened!");
//        });

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_END).attachListener((chan, packet) -> {
            PacketPlayerSyncEnd end = (PacketPlayerSyncEnd) packet;

            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                Bukkit.getPluginManager().callEvent(new PlayerSyncCompleteEvent(end.getTarget(), end.getSection()));
            });
        });

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_START).attachListener((chan, packet) -> {
            PacketPlayerSyncStart end = (PacketPlayerSyncStart) packet;

            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                Bukkit.getPluginManager().callEvent(new PlayerInitialJoinEvent(end.getTarget()));
            });
        });

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_FULLY_END).attachListener((chan, packet) -> {
            PacketPlayerSyncFullyEnd full = (PacketPlayerSyncFullyEnd) packet;
            for (String x : BPSGlobalAPI.getSyncTargets()) {
                if (!full.getSyncTarget().contains(x)) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                        Bukkit.getPluginManager().callEvent(new PlayerSyncIncompleteEvent(full.getTarget(), x));
                    });
                }
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                Bukkit.getPluginManager().callEvent(new PlayerSyncEndEvent(full.getTarget(), full.getSyncTarget()));
            });
        });
    }

}
