package me.gavin.gavhackplus.util.misc;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhackplus.events.KeyEvent;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.features.ColorMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

// to translate forge events into the events already used by the event system
// since this was ported from MCP
public class EventTranslator {

    public EventTranslator() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        ColorMod.updateColors();

        if (mc.player == null || mc.world == null)
            return;

        EventManager.call(new me.gavin.gavhackplus.events.TickEvent());
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            EventManager.call(new KeyEvent(Keyboard.getEventKey()));
        }
    }

    @SubscribeEvent
    public void onRender2d(RenderGameOverlayEvent.Text event) {
        EventManager.call(new RenderEvent.Screen(event.getPartialTicks()));
    }

    @SubscribeEvent
    public void onRender3d(RenderWorldLastEvent event) {
        EventManager.call(new RenderEvent.World(event.getPartialTicks()));
    }
}
