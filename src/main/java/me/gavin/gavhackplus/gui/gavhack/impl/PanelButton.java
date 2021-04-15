package me.gavin.gavhackplus.gui.gavhack.impl;

import me.gavin.gavhackplus.gui.gavhack.api.button.AbstractButton;
import me.gavin.gavhackplus.util.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public abstract class PanelButton extends AbstractButton {

	private FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
	
    public String title;

    private int pLength;
    private GuiPanel parent;
    
    public PanelButton(int x, int y, int width, int height, String title, int length, GuiPanel parent) {
        super(x, y, width, height);
        this.title = title;
        this.parent = parent;
        this.pLength = length;
        str = title + " (" + pLength + ")";
    }
    
    String str = title + " (" + pLength + ")";

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(x, y, x + width, y + height, ClickGuiScreen.guiColor.getRGB());
        FontUtil.drawStringWithShadow(str, x + ((width / 2f) - (FontUtil.getStringWidth(str) / 2f)), y + (FontUtil.getHeight() / 2f), -1);
    }

    @Override
    public abstract void handleClick(int mouseButton);
}
