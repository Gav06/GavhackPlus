package me.gavin.gavhackplus.gui.gavhack.api.component;

import me.gavin.gavhackplus.gui.gavhack.api.IComponent;

public abstract class AbstractComponent implements IComponent {

    public int x, y, width, height;

    public AbstractComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public abstract void draw(int mouseX, int mouseY);

    @Override
    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    @Override
    public abstract void mouseReleased(int mouseX, int mouseY, int mouseButton);

    @Override
    public boolean isMouseWithin(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
