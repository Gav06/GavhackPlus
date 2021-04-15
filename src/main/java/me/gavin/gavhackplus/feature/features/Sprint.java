package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;

import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.util.MotionUtil;

public class Sprint extends Feature {

	private ModeSetting mode = new ModeSetting("Mode", this, "Legit", "Legit", "Rage");

	public Sprint() {
		super("Sprint", "sprints for you", Category.Movement);
		addSettings(mode);
	}

	@EventTarget
	public void onTick(TickEvent event) {
		if (mode.getMode().equals("Legit")) {

			if (!mc.player.collidedHorizontally 
					&& !mc.player.isSneaking() 
					&& mc.player.moveForward > 0) {
				mc.player.setSprinting(true);
			}

		} else {

			if (MotionUtil.isMoving(mc.player)
					&& !mc.player.isSneaking()
					&& !mc.player.collidedHorizontally) {
				mc.player.setSprinting(true);
			}
		}
	}
}
