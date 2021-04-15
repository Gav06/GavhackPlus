package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;

public class AntiFog extends Feature {

    public AntiFog() {
        super("AntiFog", "Prevents fog from rendering", Category.Render);
    }

    // see me/gavin/gavhackplus/mixins/EntityRendererPatch.java
}
