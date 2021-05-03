package me.gavin.gavhackplus.gui.particle;

import com.google.common.collect.Lists;

import me.gavin.gavhackplus.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleEngine {

	public Minecraft mc = Minecraft.getMinecraft();
	
    public CopyOnWriteArrayList<Particle> particles = Lists.newCopyOnWriteArrayList();
    public float lastMouseX;
    public float lastMouseY;

    public void render(float mouseX, float mouseY, float partialTicks) {
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1, 1, 1, 1);
        ScaledResolution sr = new ScaledResolution(mc);
        float xOffset = sr.getScaledWidth() / 2 - mouseX;
        float yOffset = sr.getScaledHeight() / 2 - mouseY;
        for (particles.size(); particles.size() < (int) (sr.getScaledWidth() / 19.2f); particles.add(new Particle(sr, new Random().nextFloat() * 2 + 2, new Random().nextFloat() * 5 + 5)))
            ;
        List<Particle> toremove = Lists.newArrayList();
        for (Particle p : particles) {
            if (p.opacity < 32) {
                p.opacity += 2;
            }
            if (p.opacity > 32) {
                p.opacity = 32;
            }
            p.ticks *= partialTicks;
            Color c = new Color(255, 255, 255, (int) p.opacity);
            RenderUtil.drawBorderedCircle(p.x + Math.sin(p.ticks / 2) * 50 + -xOffset / 5, (p.ticks * p.speed) * p.ticks / 10 + -yOffset / 5, p.radius * (p.opacity / 32), c.getRGB(), c.getRGB());
            p.ticks += 0.05;
            if (((p.ticks * p.speed) * p.ticks / 10 + -yOffset / 5) > sr.getScaledHeight()
                    || ((p.ticks * p.speed) * p.ticks / 10 + -yOffset / 5) < 0
                    || (p.x + Math.sin(p.ticks / 2) * 50 + -xOffset / 5) > sr.getScaledWidth()
                    || (p.x + Math.sin(p.ticks / 2) * 50 + -xOffset / 5) < 0) {
                toremove.add(p);
            }
        }

        particles.removeAll(toremove);
        GlStateManager.color(1, 1, 1, 1);
        GL11.glColor4f(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        lastMouseX = getMouseX();
        lastMouseY = getMouseY();
    }

    public static int getMouseX() {
        return Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth;
    }

    public static int getMouseY() {
        return getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayWidth - 1;
    }

    public static int getScreenWidth() {
        return Minecraft.getMinecraft().displayWidth / getScaleFactor();
    }

    public static int getScreenHeight() {
        return Minecraft.getMinecraft().displayHeight / getScaleFactor();
    }

    public static int getScaleFactor() {
        int scaleFactor = 1;
        final boolean isUnicode = Minecraft.getMinecraft().isUnicode();
        int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;
        if (guiScale == 0) {
            guiScale = 1000;
        }
        while (scaleFactor < guiScale && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
            --scaleFactor;
        }
        return scaleFactor;
    }

}
