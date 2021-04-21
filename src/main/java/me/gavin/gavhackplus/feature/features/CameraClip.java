package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.NumberSetting;

public class CameraClip extends Feature {

    public static NumberSetting distance;

    public CameraClip() {
        super("CameraClip", "Lets 3rd person cam clip through blocks", Category.Render);
        distance = new NumberSetting("Distance", this, 4.0f, 1.0f, 10.0f, 0.25f);
        addSettings(distance);
    }

    // see me/gavin/gavhackplus/EntityRendererPatch.java
}
