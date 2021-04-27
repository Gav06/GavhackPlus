package me.gavin.gavhackplus.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickTimer {

    public TickTimer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private int ticksPassed = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        ticksPassed++;
    }

    private int lastTickAmount = ticksPassed;

    public void reset() {
        lastTickAmount = ticksPassed;
    }

    public boolean hasTicksPassed(int amount, boolean reset) {
        if (ticksPassed - lastTickAmount > amount) {
            if (reset)
                reset();

            return true;
        }

        return false;
    }
}
