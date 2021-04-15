package me.gavin.gavhackplus.setting.impl;

import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.Setting;

public class BooleanSetting extends Setting {

	private boolean value;
	
	public BooleanSetting(String name, Feature parent, boolean value) {
		super(name, parent);
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}
}
