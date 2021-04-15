package me.gavin.gavhackplus.setting;

import me.gavin.gavhackplus.feature.Feature;

public abstract class Setting {

	private final String name;
	private final Feature parent;
	
	public Setting(String name, Feature parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
}
