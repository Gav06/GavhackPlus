package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;

import java.awt.*;
import java.net.URI;

public class AutoPrank extends Feature {

    public AutoPrank() {
        super("AutoPrank", "we do a little bit of trolling", Category.Misc);
    }

    @Override
    public void onEnable() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        } catch (Exception e) { e.printStackTrace(); }
        disable();
    }
}
