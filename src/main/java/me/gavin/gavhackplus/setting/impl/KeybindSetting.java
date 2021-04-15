package me.gavin.gavhackplus.setting.impl;

import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.Setting;

public class KeybindSetting extends Setting {

	private int key;
	
	public KeybindSetting(String name, Feature parent, int key) {
		super(name, parent);
		this.key = key;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
