package me.gavin.gavhackplus.gui.gavhack.impl.setting;

import me.gavin.gavhackplus.gui.gavhack.impl.BaseComponent;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.util.FontUtil;

public class ModeComponent extends BaseComponent {

    public ModeSetting parent;

    public ModeComponent(ModeSetting parent, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.parent = parent;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        FontUtil.drawStringWithShadow(parent.getName() + ": " + parent.getMode(),x + 3, y + 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseWithin(mouseX, mouseY) && mouseButton == 0)
            parent.cycle();
    }

    @Deprecated
    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }
}