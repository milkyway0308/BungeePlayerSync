package skywolf46.bps.client.event.impl;

import org.bukkit.event.HandlerList;
import skywolf46.bps.client.event.BPSBasedEvent;

import java.util.UUID;

public class PlayerSyncIncompleteEvent extends BPSBasedEvent {
    private static HandlerList handlerList = new HandlerList();
    private String section;
    public PlayerSyncIncompleteEvent(UUID target, String section) {
        super(target);
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public boolean isSyncOf(Class target) {
        return section.equals(target.getName());
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
