package me.gavin.gavhackplus.feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.gavin.gavhackplus.setting.Setting;
import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;

import net.minecraft.client.Minecraft;

public abstract class Feature {

	protected Minecraft mc = Minecraft.getMinecraft();

	private final String name, description;
	private final Category category;

	private int key;
	public List<Setting> settings = new ArrayList<>();
	private boolean enabled;

	public Feature(String name, String description, Category category) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.key = Keyboard.KEY_NONE;
	}
	
	public Feature(String name, String description, Category category, int key) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.key = key;
	}

	protected void addSettings(Setting... settings1) {
		settings.addAll(Arrays.asList(settings1));
	}

	public void toggle() {
		if (enabled) {
			disable();
		} else {
			enable();
		}
	}

	public void enable() {
		enabled = true;
		onEnable();
		EventManager.register(this);
	}

	public void disable() {
		EventManager.unregister(this);
		onDisable();
		enabled = false;
	}

	protected void onEnable() {
	}

	protected void onDisable() {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}