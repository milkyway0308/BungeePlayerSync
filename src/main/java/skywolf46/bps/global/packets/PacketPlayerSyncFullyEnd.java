package skywolf46.bps.global.packets;

import skywolf46.bps.global.BPSVariable;
import skywolf46.bsl.global.abstraction.packets.AbstractPacket;

import java.util.List;
import java.util.UUID;

public class PacketPlayerSyncFullyEnd extends AbstractPacket {
    private UUID target;
    private List<String> syncTarget;
    public PacketPlayerSyncFullyEnd(List<String> targets, UUID target) {
        this.target = target;
        this.syncTarget = targets;
    }

    public UUID getTarget() {
        return target;
    }

    public List<String> getSyncTarget() {
        return syncTarget;
    }

    @Override
    public int getID() {
        return BPSVariable.PACKET_SYNC_FULLY_END;
    }
}
