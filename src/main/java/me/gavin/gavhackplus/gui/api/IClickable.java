package me.gavin.gavhackplus.gui.api;

@Deprecated
public interface IClickable {

    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    default boolean isMouseWithin(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
