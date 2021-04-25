package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class PopCounter extends Feature {

    public PopCounter() {
        super("PopCounter", "Display totem pops per player", Category.Chat);
    }

    HashMap<String, Integer> popMap = new HashMap<>();

    @EventTarget
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) {
                Entity e = packet.getEntity(mc.world);
                if (e instanceof EntityPlayer) {

                    if (popMap.containsKey(e.getName())) {
                        int pops = popMap.get(e.getName());
                        popMap.remove(e.getName());
                        popMap.put(e.getName(), pops + 1);
                    } else {
                        popMap.put(e.getName(), 1);
                    }

                    int count = popMap.get(e.getName());
                    Util.sendMsg(e.getName() + " popped " + count + " totem" + (count > 2 ? "s" : ""));
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(TickEvent event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (popMap.containsKey(player.getName())) {
                if (player.getHealth() <= 0 || player.isDead || !player.isEntityAlive())
                    popMap.remove(player.getName());
            }
        }
    }
}
