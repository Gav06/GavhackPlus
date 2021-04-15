package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.Util;

import java.awt.Color;


public class ColorMod extends Feature {

	public static NumberSetting red, green, blue;
	private static BooleanSetting rainbow;

	public ColorMod() {
		super("Color", "Global color values for the client", Category.Misc);

		red = new NumberSetting("R", this, 255, 0, 255, 1);
		green = new NumberSetting("G", this, 0, 0, 255, 1);
		blue = new NumberSetting("B", this, 255, 0, 255, 1);
		rainbow = new BooleanSetting("Rainbow", this, false);

		addSettings(red, green, blue, rainbow);
		globalColor = new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue());
	}

	public static Color globalColor = new Color(0, 0, 0);

	public static void updateColors() {
		if (!rainbow.getValue()) {
			globalColor = new Color((int) red.getValue(), (int) green.getValue(), (int) blue.getValue());
		} else {
			globalColor = new Color(Util.getRGB(9.0f, 1.0f, 0.65f));
		}
	}

	@Override
	public void onEnable() {
		disable();
	}
}