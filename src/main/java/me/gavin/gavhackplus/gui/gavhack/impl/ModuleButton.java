package me.gavin.gavhackplus.gui.gavhack.impl;

import java.util.ArrayList;

import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.feature.features.ClickGUI;
import me.gavin.gavhackplus.gui.gavhack.api.button.AbstractButton;
import me.gavin.gavhackplus.gui.gavhack.impl.setting.BooleanComponent;
import me.gavin.gavhackplus.gui.gavhack.impl.setting.KeybindComponent;
import me.gavin.gavhackplus.gui.gavhack.impl.setting.ModeComponent;
import me.gavin.gavhackplus.gui.gavhack.impl.setting.NumberComponent;
import me.gavin.gavhackplus.setting.Setting;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.FontUtil;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;

public class ModuleButton extends AbstractButton {
    // this code is kinda trash tbh
    public Feature module;
    public ArrayList<BaseComponent> components;
    public boolean open;
    public ModuleButton(int x, int y, int width, int height, Feature parent) {
        super(x, y, width, height);
        this.module = parent;
        components = new ArrayList<>();
        
        for (Setting s : parent.settings) {
            if (s instanceof ModeSetting) {
                components.add(new ModeComponent((ModeSetting)s, x, y, width - 5, height - 3));
            }

            if (s instanceof BooleanSetting) {
                components.add(new BooleanComponent((BooleanSetting)s, x, y, width - 5, height - 3));
            }

            if (s instanceof NumberSetting) {
                components.add(new NumberComponent((NumberSetting) s, x, y, width - 5, height - 3));
            }
        }

        components.add(new KeybindComponent(module, x, y, width - 5, height - 3));
    }

    int yOff = 0;
    @Override
    public void draw(int mouseX, int mouseY) {
        if (module.isEnabled())
            Gui.drawRect(x + 1, y + 1, x + width, y + height, ClickGuiScreen.guiColor.getRGB());
        
        String str = module.getName();
        
        if (ClickGUI.showBinds.getValue() && module.getKey() != 0)
        	str += " [" + Keyboard.getKeyName(module.getKey()) + "]";
        
        FontUtil.drawStringWithShadow(str, x + 4, y + 4, -1);

        if (open) {
            yOff = height + 1;
            components.forEach(comp -> {
                comp.x = x + 4;
                comp.y = y + yOff;
                Gui.drawRect(x + 1, comp.y, x + 3, comp.y + comp.height + 1, ClickGuiScreen.guiColor.getRGB());
                comp.draw(mouseX, mouseY);
                yOff += comp.height + 1;
            });
            yOff = 0;
        }
        
        
    }

    @Override
    public void handleClick(int mouseButton) {
        if (mouseButton == 0) {
            module.toggle();
        }

        if (mouseButton == 1) {
            open = !open;
        }
    }

    public int getSettingsHeight() {
        if (!open)
            return 0;


        int _height = 0;
        for (BaseComponent comp : components) {
            _height += comp.height + 1;
        }

        return _height;
    }
}
