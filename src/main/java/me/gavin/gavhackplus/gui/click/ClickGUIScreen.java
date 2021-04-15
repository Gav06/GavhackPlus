package me.gavin.gavhackplus.gui.click;

import me.gavin.gavhackplus.Gavhack;
import me.gavin.gavhackplus.feature.features.ClickGUI;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

@Deprecated
public class ClickGUIScreen extends GuiScreen {

	private ClickGUISystem sys;

	public ClickGUIScreen(ClickGUISystem sys) {
		this.sys = sys;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		sys.mouseClick(mouseX, mouseY, mouseButton);
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		sys.mouseRelease(mouseX, mouseY, mouseButton);
	}

	@Override
	public void keyTyped(char key, int keyCode) {
		if (keyCode != Keyboard.KEY_ESCAPE) {
			sys.keyType(key, keyCode);
		} else {
			Minecraft.getMinecraft().displayGuiScreen(null);
			Gavhack.featureManager.getFeature(ClickGUI.class).disable();
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		sys.render(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		sys.onOpen();
	}

	@Override
	public void onGuiClosed() {
		sys.onClose();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
