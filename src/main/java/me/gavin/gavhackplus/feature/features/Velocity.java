package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Feature {

    public static BooleanSetting waterPush;
    public static BooleanSetting entityPush;

    private BooleanSetting explosions = new BooleanSetting("Explosions", this, true);

    public Velocity() {
        super("Velocity", "stop various things from moving you", Category.Movement);
        waterPush = new BooleanSetting("PushedByWater", this, true);
        entityPush = new BooleanSetting("EntityPush", this, true);
        addSettings(explosions, waterPush, entityPush);
    }

    @EventTarget
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketExplosion && explosions.getValue()) {
            event.setCancelled(true);
        }

        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if (((SPacketEntityVelocity)event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                event.setCancelled(true);
            }
        }
    }
}
