package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

public class Fullbright extends Feature {

    public Fullbright() {
        super("Fullbright", "Makes it bright", Category.Render);
    }

    float prevBrightness;

    @Override
    public void onEnable() {
        prevBrightness = mc.gameSettings.gammaSetting;
    }

    @EventTarget
    public void onTick(TickEvent event) {
    	mc.gameSettings.gammaSetting = 100f;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = prevBrightness;
    }
}
