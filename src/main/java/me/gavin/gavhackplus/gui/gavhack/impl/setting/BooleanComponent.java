package me.gavin.gavhackplus.gui.gavhack.impl.setting;

import me.gavin.gavhackplus.gui.gavhack.impl.BaseComponent;
import me.gavin.gavhackplus.gui.gavhack.impl.ClickGuiScreen;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.util.FontUtil;
import net.minecraft.client.gui.Gui;

public class BooleanComponent extends BaseComponent {

    public BooleanSetting parent;
    public BooleanComponent(BooleanSetting parent, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.parent = parent;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (parent.getValue()) {
            Gui.drawRect(x, y, x + width, y + height, ClickGuiScreen.guiColor.getRGB());
        }

        FontUtil.drawStringWithShadow(parent.getName(), x + 3, y + 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseWithin(mouseX, mouseY) && mouseButton == 0)
            parent.setValue(!parent.getValue());
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}
}
