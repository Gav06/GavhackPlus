package me.gavin.gavhackplus.feature;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.gavin.gavhackplus.events.KeyEvent;
import me.gavin.gavhackplus.feature.features.*;
import me.gavin.gavhackplus.setting.RegisterSetting;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.KeybindSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.FontUtil;


public class FeatureManager {

	public FeatureManager() {
		init();
		EventManager.register(this);
	}

	private ArrayList<Feature> features = new ArrayList<>();
	private ArrayList<Feature> sortedFeatures = new ArrayList<>();

	private void init() {
		// combat
		add(new AntiBot());
		add(new KillAura());
		add(new FastEXP());
		add(new AutoCrystal());
		add(new AutoTotem());
		add(new BowSpam());

		// movement
		add(new Sprint());
		add(new Jesus());
		add(new Velocity());

		// render
		add(new Fullbright());
		add(new ESP());
		add(new BlockHighlight());
		add(new Chams());
		add(new AntiFog());
		add(new ItemViewmodel());
		add(new Tracers());
		add(new Nametags());
		add(new HoleESP());
		add(new CameraClip());
		
		// world
		add(new NoRain());
		add(new Nuker());
		add(new Freecam());
		//add(new Scaffold());

		// misc
		add(new AutoPorn());
		add(new HUD());
		add(new ColorMod());
		add(new ClickGUI());
		add(new OffhandCrash());
		add(new Fakeplayer());
		add(new RPC());
		add(new AutoPrank());

		// chat
		add(new AutoSuicide());
		add(new ChatSuffix());
		add(new ChatTimestamps());
		add(new PopCounter());
		add(new PotionAlert());
		add(new ToggleNotify());
		add(new VisualRange());
		
		
		// sorting
		features.sort(this::sortABC);
		
		sortedFeatures.addAll(features);
		sortedFeatures.sort(this::sortLength);
	}

	private void add(Feature feature) {
		features.add(feature);
	}

	public ArrayList<Feature> getFeatures() {
		return features;
	}
	
	public ArrayList<Feature> getSortedFeatures() {
		return sortedFeatures;
	}

	public Feature getFeature(Class<?> clazz) {
		for (Feature f : features) {
			if (f.getClass() == clazz)
				return f;
		}

		return null;
	}

	@Deprecated
	public Feature getFeature(String name) {
		for (Feature f : features) {
			if (f.getName().equalsIgnoreCase(name))
				return f;
		}

		return null;
	}

	public boolean isFeatureEnabled(Class<?> clazz) {
		return getFeature(clazz).isEnabled();
	}

	public List<Feature> getFeaturesFromCategory(Category c) {
		List<Feature> flist = new ArrayList<>();
		
		for (Feature f : features) {
			if (f.getCategory() == c)
				flist.add(f);
		}
		
		return flist;
	}

	@EventTarget
	public void onKeyPress(KeyEvent event) {
		for (Feature f : features) {
			if (f.getKey() == event.getKey())
				f.toggle();
		}
	}
	
	private int sortABC(Feature f1, Feature f2) {
		return f1.getName().compareTo(f2.getName());
	}
	
	private int sortLength(Feature f1, Feature f2) {
		return Integer.compare(FontUtil.getStringWidth(f2.getName()), FontUtil.getStringWidth(f1.getName()));
	}
	
	public void sortList() {
		sortedFeatures.sort(this::sortLength);
	}
}
