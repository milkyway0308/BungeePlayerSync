package skywolf46.bps.global.packets;

import skywolf46.bps.global.BPSVariable;
import skywolf46.bsl.global.abstraction.packets.AbstractPacket;

import java.util.UUID;

public class PacketPlayerSyncStart extends AbstractPacket {
    private UUID target;

    public PacketPlayerSyncStart(UUID target) {
        this.target = target;
    }

    public UUID getTarget() {
        return target;
    }


    @Override
    public int getID() {
        return BPSVariable.PACKET_SYNC_START;
    }
}
