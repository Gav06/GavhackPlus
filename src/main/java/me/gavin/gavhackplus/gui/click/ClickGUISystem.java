package me.gavin.gavhackplus.gui.click;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

// wrapper for the clickgui

// deprecated becaause im just gonna port in gavhack gui
@Deprecated
public class ClickGUISystem extends Gui {

	public ClickGUIScreen screen;
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	public ClickGUISystem() {
		screen = new ClickGUIScreen(this);
	}
	
	public void mouseClick(int mouseX, int mouseY, int mouseButton) {
		
	}
	
	public void mouseRelease(int mouseX, int mouseY, int mouseButton) {
		
	}
	
	ScaledResolution sr;
	
	public void render(int mouseX, int mouseY, float partialTicks) {
		sr = new ScaledResolution(mc);
		
	}
	
	public void keyType(char key, int keyCode) {
		
	}
	
	public void onOpen() {
		
	}
	
	public void onClose() {
		
	}
}
