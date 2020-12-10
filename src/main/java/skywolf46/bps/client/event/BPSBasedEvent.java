package skywolf46.bps.client.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public abstract class BPSBasedEvent extends Event {
    private UUID uid;

    public BPSBasedEvent(UUID target) {
        this.uid = target;
    }

    public UUID getUid() {
        return uid;
    }

}
