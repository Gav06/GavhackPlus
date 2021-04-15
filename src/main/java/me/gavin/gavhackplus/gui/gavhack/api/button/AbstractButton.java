package me.gavin.gavhackplus.gui.gavhack.api.button;

import me.gavin.gavhackplus.gui.gavhack.api.IButton;

public abstract class AbstractButton implements IButton {

    public int x, y, width, height;

    public AbstractButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public abstract void draw(int mouseX, int mouseY);

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseWithin(mouseX, mouseY))
            handleClick(mouseButton);
    }

    @Override
    public abstract void handleClick(int mouseButton);

    @Override
    public boolean isMouseWithin(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
