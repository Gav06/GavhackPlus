package me.gavin.gavhackplus.gui.gavhack.impl;

import me.gavin.gavhackplus.gui.gavhack.api.component.AbstractComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public abstract class BaseComponent extends AbstractComponent {
    public BaseComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    protected FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

    @Override
    public abstract void draw(int mouseX, int mouseY);
    @Override
    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    @Override
    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    protected void keyTyped(char keyChar, int keyCode) { }
}
