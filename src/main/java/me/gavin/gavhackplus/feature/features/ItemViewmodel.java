package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderHandEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class ItemViewmodel extends Feature {

	private NumberSetting mainX = new NumberSetting("MainX", this, 0.0f, -3.0f, 3.0f, 0.1f);
	private NumberSetting mainY = new NumberSetting("MainY", this, 0.0f, -3.0f, 3.0f, 0.1f);
	private NumberSetting mainZ = new NumberSetting("MainZ", this, 0.0f, -3.0f, 3.0f, 0.1f);

	private NumberSetting offhandX = new NumberSetting("OffhandX", this, 0.0f, -3.0f, 3.0f, 0.1f);
	private NumberSetting offhandY = new NumberSetting("OffhandY", this, 0.0f, -3.0f, 3.0f, 0.1f);
	private NumberSetting offhandZ = new NumberSetting("OffhandZ", this, 0.0f, -3.0f, 3.0f, 0.1f);

	public static BooleanSetting cancelEating;

	public ItemViewmodel() {
		super("ItemViewmodel", "Customize how items render", Category.Render);
		cancelEating = new BooleanSetting("CancelEating", this, true);
		addSettings(mainX, mainY, mainZ, offhandX, offhandY, offhandZ, cancelEating);
	}

	@EventTarget
	public void onRenderHand(RenderHandEvent event) {
		if (event.getHandSide() == EnumHandSide.RIGHT) {
			GlStateManager.translate(mainX.getValue(), mainY.getValue(), mainZ.getValue());
		} else {
			GlStateManager.translate(offhandX.getValue(), offhandY.getValue(), offhandZ.getValue());
		}
	}
}
