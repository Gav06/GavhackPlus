package me.gavin.gavhackplus.gui.gavhack.api.component;

public abstract class AbstractDragComponent extends AbstractComponent {

    public boolean isDragging;
    public int dragX, dragY;

    protected boolean hovering;

    public AbstractDragComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        updateDragPos(mouseX, mouseY);
        renderComponent(mouseX, mouseY);

        hovering = isMouseWithin(mouseX, mouseY);
    }

    public abstract void renderComponent(int mouseX, int mouseY);

    public abstract void handleClick(int mouseX, int mouseY, int mouseButton);

    // this will be called if the mouse is clicked AND the mouse is within
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        isDragging = true;
        dragX = mouseX - x;
        dragY = mouseY - y;

        if (isMouseWithin(mouseX, mouseY))
            handleClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        isDragging = false;
    }

    public void updateDragPos(int mouseX, int mouseY) {
        if (isDragging) {
            x = (mouseX - dragX);
            y = (mouseY - dragY);
        }
    }
}
