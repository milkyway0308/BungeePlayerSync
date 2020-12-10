package skywolf46.bps.client.event.impl;

import org.bukkit.event.HandlerList;
import skywolf46.bps.client.event.BPSBasedEvent;

import java.util.UUID;

public class PlayerInitialJoinEvent extends BPSBasedEvent {
    private static HandlerList handlerList = new HandlerList();

    public PlayerInitialJoinEvent(UUID target) {
        super(target);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
