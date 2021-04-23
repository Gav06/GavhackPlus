package me.gavin.gavhackplus.mixin;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.events.Render2dPre;
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

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V"))
    public void updateCameraAndRenderPatch(float partialTicks, long nanoTime, CallbackInfo ci) {
        EventManager.call(new Render2dPre(partialTicks));
    }
}
