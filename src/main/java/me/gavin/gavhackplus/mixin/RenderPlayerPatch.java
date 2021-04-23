package me.gavin.gavhackplus.mixin;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.features.Nametags;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class RenderPlayerPatch {

    @Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)
    public void renderEntityNamePatch(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo ci) {
        if (Gavhack.featureManager.isFeatureEnabled(Nametags.class))
            ci.cancel();
    }

}
