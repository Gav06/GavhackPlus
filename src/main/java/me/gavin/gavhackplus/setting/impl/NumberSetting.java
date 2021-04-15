package me.gavin.gavhackplus.setting.impl;

import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.Setting;

public class NumberSetting extends Setting {

	private float value;
	private final float min, max, increment;

	public NumberSetting(String name, Feature parent, float value, float min, float max, float increment) {
		super(name, parent);
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
	}

	public void setValueClamped(double value) {
		float precision = 1.0F / this.increment;
		this.value = Math.round(Math.max(this.min, Math.min(this.max, value)) * precision) / precision;
	}
	
	public void increment(boolean positive) {
        value = value + (positive ? 1 : -1) * increment;
    }

    public float getValue() {
		return value;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	public float getIncrement() {
		return increment;
	}
}
