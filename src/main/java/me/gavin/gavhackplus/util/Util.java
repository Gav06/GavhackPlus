package me.gavin.gavhackplus.util;

import java.awt.Color;

import me.gavin.gavhackplus.mixin.accessor.IRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class Util {

	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static int getRGBWave(float seconds, float brightness, float saturation, long width) {
		float hue = ((System.currentTimeMillis() + width) % (int) (seconds * 1000)) / (seconds * 1000);
		return Color.HSBtoRGB(hue, saturation, brightness);
	}

	public static int getRGB(float seconds, float brightness, float saturation) {
		float hue = (System.currentTimeMillis() % (int) (seconds * 1000)) / (seconds * 1000);
		return Color.HSBtoRGB(hue, saturation, brightness);
	}

	public static void drawRGBString(String text, int x, int y, float seconds, float brightness, float saturation,
			long width) {
		char[] chars = text.toCharArray();

		int xOffset = 0;
		long rgbOffset = 0;
		for (char c : chars) {
			FontUtil.drawStringWithShadow(String.valueOf(c), x + xOffset, y,
					Util.getRGBWave(seconds, brightness, saturation, rgbOffset));
			rgbOffset += width;
			xOffset += FontUtil.getStringWidth(String.valueOf(c));
		}
	}
	
	public static double[] getRenderPos() {
		IRenderManager manager = (IRenderManager) mc.getRenderManager();
		return new double[] {manager.getRenderPosX(), manager.getRenderPosY(), manager.getRenderPosZ()};
	}
	
	public static double[] calculateLookAt(double x, double y, double z, EntityPlayer me) {
		double dirx = me.posX - x;
		double diry = me.posY + me.getEyeHeight() - y;
		double dirz = me.posZ - z;
		
		double distance = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
		
		dirx /= distance;
		diry /= distance;
		dirz /= distance;
		
		double pitch = Math.asin(diry);
		double yaw = Math.atan2(dirz, dirx);
		
		pitch = pitch * 180.0D / Math.PI;
		yaw = yaw * 180.0D / Math.PI;
		
		yaw += 90.0D;
		
		return new double[] {yaw, pitch};
	}
	
	public static void glColor(Color c) {
		GlStateManager.color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}
}
