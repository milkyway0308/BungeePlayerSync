package skywolf46.bps.global.packets;

import skywolf46.bps.global.BPSVariable;
import skywolf46.bsl.global.abstraction.packets.AbstractPacket;

import java.util.UUID;

public class PacketPlayerSyncEnd extends AbstractPacket {
    private UUID target;
    private String section;

    public PacketPlayerSyncEnd(String section, UUID target) {
        this.target = target;
        this.section = section;
    }

    public UUID getTarget() {
        return target;
    }

    public String getSection() {
        return section;
    }

    @Override
    public int getID() {
        return BPSVariable.PACKET_SYNC_END;
    }
}
