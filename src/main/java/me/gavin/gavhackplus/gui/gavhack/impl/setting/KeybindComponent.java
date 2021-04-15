package me.gavin.gavhackplus.gui.gavhack.impl.setting;

import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.gui.gavhack.impl.BaseComponent;
import me.gavin.gavhackplus.util.FontUtil;
import org.lwjgl.input.Keyboard;

public class KeybindComponent extends BaseComponent {

    boolean listening = false;

    private Feature parent;
    public KeybindComponent(Feature parent, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.parent = parent;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (!listening) {
            FontUtil.drawStringWithShadow("Bind: " + Keyboard.getKeyName(parent.getKey()), x + 3, y + 2, -1);
        } else {
            FontUtil.drawStringWithShadow("Listening...", x + 3, y + 2, -1);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseWithin(mouseX, mouseY) && mouseButton == 0)
            listening = !listening;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) { }

    @Override
    public void keyTyped(char keyChar, int keyCode) {
        if (listening) {
            listening = false;
            if (keyCode == Keyboard.KEY_DELETE) {
                this.parent.setKey(Keyboard.KEY_NONE);
                return;
            }

            this.parent.setKey(keyCode);
        }
    }
}
