package me.gavin.gavhackplus.feature.features;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.util.FontUtil;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class HUD extends Feature {

	private BooleanSetting watermark = new BooleanSetting("Watermark", this, true);
	private BooleanSetting modList = new BooleanSetting("ModList", this, true);
	private BooleanSetting welcomer = new BooleanSetting("Welcomer", this, false);
	private BooleanSetting fancyModList = new BooleanSetting("FancyModList", this, true);
	private ModeSetting modSort = new ModeSetting("Sort", this, "Length", "ABC", "Length");
	private BooleanSetting rainbow = new BooleanSetting("Rainbow", this, true);

	public HUD() {
		super("HUD", "Display information on your screen", Category.Misc);
		addSettings(watermark, welcomer, modList, fancyModList, modSort, rainbow);
	}

	@EventTarget
	public void onRender(RenderEvent.Screen event) {
		ScaledResolution sr = new ScaledResolution(mc);

		if (watermark.getValue()) {
			if (rainbow.getValue()) {
				Util.drawRGBString(Gavhack.nameVersion, 2, 2, 4.0f, 1.0f, 0.75f, 70L);
			} else {
				FontUtil.drawStringWithShadow(Gavhack.nameVersion, 2, 2, ColorMod.globalColor.getRGB());
			}
		}

		if (welcomer.getValue()) {
			String name = mc.player.getName();

			if (name.equals("Gav06") || name.equals("Mastercooker") || name.equals("GL_TEXTURE_2D"))
				name = "Gavin";

			String str = "Hello " + name + ", welcome to Gavhack+";

			int x = (sr.getScaledWidth() / 2) - (mc.fontRenderer.getStringWidth(str) / 2);
			int y = 30;

			if (rainbow.getValue()) {
				Util.drawRGBString(str, x, y, 4.0f, 1.0f, 0.75f, 70L);
			} else {
				mc.fontRenderer.drawStringWithShadow(str, x, y, ColorMod.globalColor.getRGB());
			}
		}

		if (modList.getValue()) {
			float yOffset = 0;

			List<Feature> mods;

			if (modSort.getMode().equals("ABC"))
				mods = Gavhack.featureManager.getFeatures().stream().filter(f -> f.isEnabled())
						.filter(f -> f.getCategory() != Category.Misc).collect(Collectors.toList());
			else
				mods = Gavhack.featureManager.getSortedFeatures().stream().filter(f -> f.isEnabled())
						.filter(f -> f.getCategory() != Category.Misc).collect(Collectors.toList());

			for (int i = 0; i < mods.size(); i++) {
				Feature f = mods.get(i);

				String str = f.getName();
				float x = sr.getScaledWidth() - FontUtil.getStringWidth(str) - 1;
				float y = 1 + yOffset;

				int color = ColorMod.globalColor.getRGB();

				if (rainbow.getValue())
					color = Util.getRGBWave(8.0f, 1.0f, 0.75f, (long) yOffset * 15L);

				if (fancyModList.getValue()) {
					// background rect
					Gui.drawRect((int) x - 2, (int) y - 1, sr.getScaledWidth(), (int) (y + FontUtil.getHeight()),
							0x80000000);

					// side rect
					Gui.drawRect((int)x - 3, (int)y - 1, (int)x - 2, (int)y + FontUtil.getHeight(), color);

					int rectLength = 0;

					try {
						rectLength = FontUtil.getStringWidth(mods.get(i + 1).getName()) + 3;
					} catch (Exception ignored) {}

					Gui.drawRect((int) x - 3, (int) y + FontUtil.getHeight(), sr.getScaledWidth() - rectLength, (int)y + FontUtil.getHeight() + 1, color);
				}
				
				FontUtil.drawStringWithShadow(f.getName(), x, y,color);
				float i_ = FontUtil.customFont ? 1.5f : 0;
				yOffset += FontUtil.getHeight() + i_ + 1;
			}
		}
	}

	private ChatFormatting g = ChatFormatting.GRAY;
	private ChatFormatting r = ChatFormatting.RESET;

	@Deprecated
	private void doLine(float x1, float y1, float x2, float y2, Color c) {
		RenderUtil.prepareGL2D();
		Util.glColor(c);
		RenderUtil.glLine2D(x1, y1, x2, y2, 3.0f);
		RenderUtil.releaseGL2D();
	}
}
