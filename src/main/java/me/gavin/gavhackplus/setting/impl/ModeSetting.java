package me.gavin.gavhackplus.setting.impl;

import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {

	private int settingIndex;
	private final List<String> modes;
	
	public ModeSetting(String name, Feature parent, String defaultMode, String...modes) {
		super(name, parent);
		this.modes = Arrays.asList(modes);
		this.settingIndex = this.modes.indexOf(defaultMode);
	}

	public void setMode(String mode) {
		settingIndex = modes.indexOf(mode);
	}
	
	public String getMode() {
		return modes.get(settingIndex);
	}
	
	public void cycle() {
		if (settingIndex == modes.size() - 1) {
			settingIndex = 0;
		} else {
			settingIndex++;
		}
	}
}
