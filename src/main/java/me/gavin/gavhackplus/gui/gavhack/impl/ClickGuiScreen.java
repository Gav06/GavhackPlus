package me.gavin.gavhackplus.gui.gavhack.impl;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.features.ClickGUI;
import me.gavin.gavhackplus.feature.features.ColorMod;
import me.gavin.gavhackplus.util.FontUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ClickGuiScreen extends GuiScreen {

	ArrayList<GuiPanel> panels;

	public static Color guiColor;

	public ClickGuiScreen() {
		panels = new ArrayList<>();

		int xOffset = 0;
		for (Category c : Category.values()) {
			panels.add(new GuiPanel(20 + xOffset, 20, c));
			xOffset += 110;
		}

		updateColor();
	}

	@Override
	public void updateScreen() {
		updateColor();
	}

	@Override
	public void initGui() {
		if (ClickGUI.background.getMode().equals("Blur"))
			mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
		updateColor();
	}

	@Override
	public void onGuiClosed() {
		if (mc.entityRenderer.getShaderGroup() != null)
			mc.entityRenderer.getShaderGroup().deleteShaderGroup();
	}

	ScaledResolution sr;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		sr = new ScaledResolution(mc);
		if (ClickGUI.background.getMode().equals("Gradient"))
			drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 00000000, guiColor.getRGB());
		else if (ClickGUI.background.getMode().equals("Default"))
			drawDefaultBackground();
		panels.forEach(panel -> {
			if (!isPanelOnTopRenderLayer(panel))
				GlStateManager.color(1, 1, 1, 0.5f);
			
			panel.draw(mouseX, mouseY);
		});

		for (GuiPanel p : panels) {
			if (isPanelOnTopRenderLayer(p) && p.open) {
				p.findHoveredButton(mouseX, mouseY);

				if (p.hoveredFeature != null) {
					String str = p.hoveredFeature.getDescription();
					Gui.drawRect(mouseX + 2, mouseY - 6, mouseX + FontUtil.getStringWidth(str) + 7, mouseY - 2 + FontUtil.getHeight(), 0xC0000000);
					FontUtil.drawStringWithShadow(str, mouseX + 5, mouseY - 4, -1);
				}
			}
		}
		if (ClickGUI.particles.getValue())
			Gavhack.particleEngine.render(mouseX, mouseY);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		for (GuiPanel p : Lists.reverse(panels)) {

			if (p.open) {
				if (p.isMouseWithin(mouseX, mouseY)) {
					p.mouseClicked(mouseX, mouseY, mouseButton);
					movePanel(p);
					return;
				}
			} else {
				if (p.button.isMouseWithin(mouseX, mouseY)) {
					p.mouseClicked(mouseX, mouseY, mouseButton);
					movePanel(p);
					return;
				}
			}
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		panels.forEach(panel -> panel.mouseReleased(mouseX, mouseY, mouseButton));
	}

	@Override
	public void keyTyped(char keyChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Gavhack.featureManager.getFeature(ClickGUI.class).disable();
			mc.displayGuiScreen(null);
			return;
		}

		panels.forEach(panel -> panel.keyTyped(keyChar, keyCode));
	}

	private void movePanel(GuiPanel p) {
		panels.remove(p);
		panels.add(p);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	// scrolling
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		int i = Mouse.getEventDWheel();

		if (i > 0) {
			i = 1;
		}

		if (i < 0) {
			i = -1;
		}

		float scrollAmount = 12.0f;

		for (GuiPanel p : panels) {
			p.y += scrollAmount * -i;
		}
	}

	private void updateColor() {
		guiColor = ColorMod.globalColor;
	}

	public boolean isPanelOnTopRenderLayer(GuiPanel panelIn) {
		return panels.indexOf(panelIn) == panels.size() - 1;
	}
}
