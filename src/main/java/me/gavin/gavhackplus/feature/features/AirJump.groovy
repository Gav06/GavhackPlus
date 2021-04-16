package me.gavin.gavhackplus.feature.features

import com.darkmagician6.eventapi.EventTarget
import me.gavin.gavhackplus.events.TickEvent
import me.gavin.gavhackplus.feature.Category
import me.gavin.gavhackplus.feature.Feature

class AirJump extends Feature {
    AirJump() {
        super("AirJump", "jump mid-air", Category.Movement)
    }

    @EventTarget
    void onTick(TickEvent e) {
        if (mc.gameSettings.keyBindJump.isPressed()) {
           mc.player.jump()
        }
    }
}