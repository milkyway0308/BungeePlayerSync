package skywolf46.bps.global.api;

import skywolf46.bps.global.BPSVariable;
import skywolf46.bps.global.packets.PacketPlayerSyncEnd;
import skywolf46.bps.global.packets.PacketPlayerSyncFullyEnd;
import skywolf46.bps.global.packets.PacketPlayerSyncStart;
import skywolf46.bsl.global.abstraction.AbstractConsoleWriter;
import skywolf46.bsl.global.api.BSLCoreAPI;
import skywolf46.bsl.global.util.ByteBufUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BPSGlobalAPI {
    private static List<String> syncTargets = new ArrayList<>();
    private static AbstractConsoleWriter console = null;

    public static void addSync(Class target) {
        syncTargets.add(target.getName());
    }

    public static List<String> getSyncTargets() {
        return new ArrayList<>(syncTargets);
    }

    public static void init() {
        console = BSLCoreAPI.writer("BungeePlayerSync");
//        BSLCoreAPI.registerPacket(BPSVariable.PACKET_SYNC_START, new PacketPlayerSyncStart(null, null));
        BSLCoreAPI.registerPacket(BPSVariable.PACKET_SYNC_END, new PacketPlayerSyncEnd(null, null));
        BSLCoreAPI.registerPacket(BPSVariable.PACKET_SYNC_FULLY_END, new PacketPlayerSyncFullyEnd(new ArrayList<>(), null));
        BSLCoreAPI.registerPacket(BPSVariable.PACKET_SYNC_START, new PacketPlayerSyncStart(null));
        BSLCoreAPI.writer("BungeePlayerSync").printText("Initializing");

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_START).register(buf -> new PacketPlayerSyncStart(ByteBufUtility.readUUID(buf)));
        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_START).register((packet, buf) -> {
            PacketPlayerSyncStart start = (PacketPlayerSyncStart) packet;
            ByteBufUtility.writeUUID(buf, start.getTarget());
        });

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_END).register(buf -> {
            PacketPlayerSyncEnd end = new PacketPlayerSyncEnd(ByteBufUtility.readString(buf), ByteBufUtility.readUUID(buf));
            return end;
        });

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_FULLY_END).register((buf) -> {
            UUID uid = ByteBufUtility.readUUID(buf);
            List<String> xt = new ArrayList<>();
            int size = buf.readInt();
            for (int i = 0; i < size; i++)
                xt.add(ByteBufUtility.readString(buf));
            PacketPlayerSyncFullyEnd full = new PacketPlayerSyncFullyEnd(xt, uid);
            return full;
        });
        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_END).register((packet, buf) -> {
            PacketPlayerSyncEnd end = (PacketPlayerSyncEnd) packet;
            ByteBufUtility.writeString(buf, end.getSection());
            ByteBufUtility.writeUUID(buf, end.getTarget());
        });

        BSLCoreAPI.getPacket(BPSVariable.PACKET_SYNC_FULLY_END).register((packet, buf) -> {
            PacketPlayerSyncFullyEnd full = (PacketPlayerSyncFullyEnd) packet;
            ByteBufUtility.writeUUID(buf, full.getTarget());
            buf.writeInt(full.getSyncTarget().size());
            for (String x : full.getSyncTarget())
                ByteBufUtility.writeString(buf, x);
        });


    }

    public static AbstractConsoleWriter writer() {
        return console;
    }

    public static void syncComplete(UUID target, Class section) {
        BSLCoreAPI.bungee().send(new PacketPlayerSyncEnd(section.getName(), target));
    }

    public static void syncEnd(UUID player) {
        BSLCoreAPI.bungee().send(new PacketPlayerSyncFullyEnd(getSyncTargets(), player));
    }
}
