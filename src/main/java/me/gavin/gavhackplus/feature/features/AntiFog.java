package me.gavin.gavhackplus.feature.features;

import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiFog extends Feature {

    public AntiFog() {
        super("AntiFog", "[BROKEN] Prevents fog from rendering", Category.Render);
    }

    // see me/gavin/gavhackplus/mixins/EntityRendererPatch.java

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onRenderFog(EntityViewRenderEvent.FogDensity event) {
        event.setCanceled(true);
        event.setDensity(0.0f);
    }
}
