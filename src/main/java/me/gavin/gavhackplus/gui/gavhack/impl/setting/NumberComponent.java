package me.gavin.gavhackplus.gui.gavhack.impl.setting;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.gavin.gavhackplus.gui.gavhack.impl.BaseComponent;
import me.gavin.gavhackplus.gui.gavhack.impl.ClickGuiScreen;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.FontUtil;
import net.minecraft.client.gui.Gui;

public class NumberComponent extends BaseComponent {

    public NumberSetting parent;
    double sliderWidth;
    boolean draggingSlider;

    public NumberComponent(NumberSetting parent, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.parent = parent;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        update(mouseX, mouseY);
        // rect or slider
        Gui.drawRect(x, y, (int) (x + sliderWidth), y + height, ClickGuiScreen.guiColor.getRGB());

        // text
        FontUtil.drawStringWithShadow(parent.getName() + " " + parent.getValue(), x + 3, y + 2, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseWithin(mouseX, mouseY) && mouseButton == 0) {
            this.draggingSlider = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.draggingSlider = false;
    }

    private void update(int mouseX, int mouseY) {
        double diff = Math.min(width, Math.max(0, mouseX - this.x));
        double min = this.parent.getMin();
        double max = this.parent.getMax();
        this.sliderWidth = width * (this.parent.getValue() - min) / (max - min);
        if (this.draggingSlider) {
            if (diff == 0) {
                this.parent.setValueClamped(this.parent.getMin());
            } else {
                double newValue = roundToPlace(diff / width * (max - min) + min, 2);
                this.parent.setValueClamped(newValue);
            }
        }
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
