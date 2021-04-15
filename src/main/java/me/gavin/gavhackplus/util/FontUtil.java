package me.gavin.gavhackplus.util;

import me.gavin.gavhackplus.Gavhack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.Color;


public class FontUtil {

	public static boolean customFont;
	
	private static FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
	
	public static void drawString(String text, float x, float y, int color) {
		if (customFont)
			Gavhack.font.drawString(text, x, y, new Color(color));
		else
			fr.drawString(text, x, y, color, false);
	}
	
	public static void drawStringWithShadow(String text, float x, float y, int color) {
		if (customFont)
			Gavhack.font.drawStringWithShadow(text, x, y, new Color(color));
		else
			fr.drawStringWithShadow(text, x, y, color);
	}
	
	public static int getHeight() {
		if (customFont)
			return Gavhack.font.getHeight();
		else
			return fr.FONT_HEIGHT;
	}
	
	public static int getStringWidth(String text) {
		if (customFont)
			return Gavhack.font.getStringWidth(text);
		else
			return fr.getStringWidth(text);
	}
}
