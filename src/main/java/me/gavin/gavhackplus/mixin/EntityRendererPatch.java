package me.gavin.gavhackplus.mixin;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.features.AntiFog;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererPatch {

    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    public void setupFogPatch(int startCoords, float partialTicks, CallbackInfo ci) {
        if (Gavhack.featureManager.isFeatureEnabled(AntiFog.class)) {
            ci.cancel();
        }
    }
}
