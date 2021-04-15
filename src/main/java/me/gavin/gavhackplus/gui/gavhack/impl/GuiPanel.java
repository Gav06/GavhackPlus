package me.gavin.gavhackplus.gui.gavhack.impl;

import java.util.ArrayList;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.feature.features.ClickGUI;
import me.gavin.gavhackplus.gui.gavhack.api.component.AbstractDragComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiPanel extends AbstractDragComponent {

    public ArrayList<ModuleButton> modButtons;
    public PanelButton button;
    public Feature hoveredFeature;
    public boolean open;

    public GuiPanel(int x, int y, Category category) {
        super(x, y, 100, 16);
        modButtons = new ArrayList<>();
        int yOffset = 20;
        for (Feature m : Gavhack.featureManager.getFeaturesFromCategory(category)) {
            modButtons.add(new ModuleButton(x, y + yOffset, width - 1, 15, m));
            yOffset += 16;
        }
        // button height will be 18, and the top of the panel will be 20 tall
        height = (modButtons.size() * 16) + 16;

        button = new PanelButton(x, y, width, 16, category.toString(), modButtons.size(), this) {
            @Override
            public void handleClick(int mouseButton) {
                if (mouseButton == 1)
                    open = !open;
            }
        };
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (button.isMouseWithin(mouseX, mouseY)) {
            if (mouseButton == 0) {
                isDragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            }
        }
        if (isMouseWithin(mouseX, mouseY)) {
            handleClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void renderComponent(int mouseX, int mouseY) {
        if (open) {
            Gui.drawRect(x, y + button.height, x + width, y + height, 0xCF000000);
            int yOffset = 16;
            for (ModuleButton mb : modButtons) {
                mb.setPos(x, y + yOffset);
                mb.draw(mouseX, mouseY);
                yOffset += mb.getSettingsHeight() + mb.height;
            }

            height = yOffset + 1;
        }
        button.setPos(x, y);
        button.draw(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0)
            isDragging = false;

        for (ModuleButton mb : modButtons) {
            if (open && mb.open) {
                mb.components.forEach(comp -> comp.mouseReleased(mouseX, mouseY, mouseButton));
            }
        }
    }

    @Override
    public void handleClick(int mouseX, int mouseY, int mouseButton) {
        if (button.isMouseWithin(mouseX, mouseY))
            button.handleClick(mouseButton);

        if (!open)
            return;

        for (ModuleButton mb : modButtons) {
            mb.mouseClicked(mouseX, mouseY, mouseButton);
            if (mb.open) {
                for (BaseComponent c : mb.components)
                    c.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    ScaledResolution sr;
    
    @Override
    public void updateDragPos(int mouseX, int mouseY) {
    	sr = new ScaledResolution(Minecraft.getMinecraft());
    	
        if (isDragging) {
            x = (mouseX - dragX);
            y = (mouseY - dragY);
        }
        
        if (ClickGUI.clampPanels.getValue()) {
        	
        	// right side
        	if ((x + width) > sr.getScaledWidth()) {
        		x = sr.getScaledWidth() - width;
        	}
        	
        	// left side
        	if (x < 0) {
        		x = 0;
        	}
        }
    }

    public void keyTyped(char keyChar, int keyCode) {
        for (ModuleButton mb : modButtons) {
            if (mb.open) {
                for (BaseComponent c : mb.components)
                    c.keyTyped(keyChar, keyCode);
            }
        }
    }
    
    public void findHoveredButton(int mouseX, int mouseY) {
    	for (ModuleButton mb : modButtons) {
    		if (mb.isMouseWithin(mouseX, mouseY)) {
    			hoveredFeature = mb.module;
    			return;
    		}
    	}
    	
    	hoveredFeature = null;
    }
}
