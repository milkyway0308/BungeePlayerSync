package skywolf46.bps.client.event.impl;

import org.bukkit.event.HandlerList;
import skywolf46.bps.client.event.BPSBasedEvent;

import java.util.List;
import java.util.UUID;

public class PlayerSyncEndEvent extends BPSBasedEvent {
    private static HandlerList handlerList = new HandlerList();
    private List<String> syncTargets;

    public PlayerSyncEndEvent(UUID target, List<String> syncTarget) {
        super(target);
        this.syncTargets = syncTarget;
    }

    public List<String> getSyncTargets() {
        return syncTargets;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
