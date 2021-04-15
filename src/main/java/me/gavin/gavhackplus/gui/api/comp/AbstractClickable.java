package me.gavin.gavhackplus.gui.api.comp;

import me.gavin.gavhackplus.gui.api.IClickable;
import me.gavin.gavhackplus.gui.api.IDrawable;

@Deprecated
public abstract class AbstractClickable implements IClickable, IDrawable {

    public int x, y, width, height;

    public AbstractClickable(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseWithin(mouseX, mouseY, x, y, width, height)) {
            processClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        render(mouseX, mouseY);
    }

    public abstract void processClick(int mouseX, int mouseY, int mouseButton);
    public abstract void render(int mouseX, int mouseY);
}
