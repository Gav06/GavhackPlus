package me.gavin.gavhackplus.gui.gavhack.api;

public interface IButton {

    void draw(int mouseX, int mouseY);

    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    void handleClick(int mouseButton);

    boolean isMouseWithin(int mouseX, int mouseY);
}
