package me.gavin.gavhackplus.gui.gavhack.api;

public interface IComponent {

    void draw(int mouseX, int mouseY);

    // mouse stuff for potential dragging
    void mouseClicked(int mouseX, int mouseY, int mouseButton);

    void mouseReleased(int mouseX, int mouseY, int mouseButton);

    boolean isMouseWithin(int mouseX, int mouseY);
}
