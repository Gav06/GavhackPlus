package me.gavin.gavhackplus.mixin;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.features.Velocity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityPatch {

    @Shadow
    private int entityId;

    @Inject(method = "isPushedByWater", at = @At("HEAD"), cancellable = true)
    public void isPushedByWaterPatch(CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getMinecraft().player != null) {
            if (entityId == Minecraft.getMinecraft().player.getEntityId()) {
                System.out.println("checking if player is pushed by waterrrr");
                if (Gavhack.featureManager.isFeatureEnabled(Velocity.class) && Velocity.waterPush.getValue()) {
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "applyEntityCollision", at = @At("HEAD"), cancellable = true)
    public void applyEntityCollisionPatch(Entity entityIn, CallbackInfo ci) {
        if (Minecraft.getMinecraft().player != null) {
            if (entityId == Minecraft.getMinecraft().player.getEntityId()) {
                if (Gavhack.featureManager.isFeatureEnabled(Velocity.class) && Velocity.entityPush.getValue())
                    ci.cancel();
            }
        }
    }
}
