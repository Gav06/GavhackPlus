package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiFog extends Feature {

    public AntiFog() {
        super("AntiFog", "Prevents fog from rendering", Category.Render);
    }

    // see me/gavin/gavhackplus/mixins/EntityRendererPatch.java
}
