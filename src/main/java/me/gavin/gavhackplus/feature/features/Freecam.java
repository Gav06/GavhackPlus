package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.MoveEvent;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.mixin.accessor.IActiveRenderInfo;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

public class Freecam extends Feature {
    public Freecam() {
        super("Freecam", "look around freely", Category.World);
    }

    Vec3d originalPos;

    @Override
    public void onEnable() {
        originalPos = ActiveRenderInfo.getCameraPosition();
    }

    @Override
    public void onDisable() {
        try {

        } catch (Exception e) { e.printStackTrace(); }
    }

    @EventTarget
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketInput || event.getPacket() instanceof CPacketPlayer) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        event.setCancelled(true);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {

        }
    }

}
