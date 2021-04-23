package me.gavin.gavhackplus.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class Render2dPre extends EventCancellable {

    private final float partialTicks;

    public Render2dPre(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
