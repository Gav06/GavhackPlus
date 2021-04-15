package me.gavin.gavhackplus.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.util.EnumHandSide;

public class RenderHandEvent extends EventCancellable {

    private final EnumHandSide handSide;

    public RenderHandEvent(EnumHandSide side) {
        this.handSide = side;
    }

    public EnumHandSide getHandSide() {
        return this.handSide;
    }
}
