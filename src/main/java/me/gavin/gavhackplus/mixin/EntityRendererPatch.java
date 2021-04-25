package me.gavin.gavhackplus.mixin;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.events.Render2dPre;
import me.gavin.gavhackplus.feature.features.AntiFog;
import me.gavin.gavhackplus.feature.features.CameraClip;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererPatch {

    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    public void setupFogPatch(int startCoords, float partialTicks, CallbackInfo ci) {
        if (Gavhack.featureManager.isFeatureEnabled(AntiFog.class)) {
            ci.cancel();
        }
    }

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(WorldClient worldClient, Vec3d start, Vec3d end) {
        if (Gavhack.featureManager.isFeatureEnabled(CameraClip.class))
            return null;
        else
            return worldClient.rayTraceBlocks(start, end);
    }
}
