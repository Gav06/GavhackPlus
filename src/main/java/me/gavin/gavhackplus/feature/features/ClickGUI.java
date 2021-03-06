package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Feature {

	public static BooleanSetting particles;
	public static BooleanSetting showBinds;
	public static BooleanSetting clampPanels;
	public static ModeSetting background;
	
	public ClickGUI() {
		super("ClickGUI", "The gui for managing features", Category.Client, Keyboard.KEY_RSHIFT);
		showBinds = new BooleanSetting("Binds", this, false);
		clampPanels = new BooleanSetting("ClampSides", this, true);
		background = new ModeSetting("Backdrop", this, "Gradient", "Gradient", "Default", "Blur", "None");
		particles = new BooleanSetting("Particles", this, true);
		addSettings(particles, showBinds, clampPanels, background);
	}
	
	@Override
	public void onEnable() {
		mc.displayGuiScreen(Gavhack.clickGui);
	}

}