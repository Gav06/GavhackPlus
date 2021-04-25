package me.gavin.gavhackplus.mixin;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhackplus.events.RenderEntityEvent;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public class RenderManagerPatch {

    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    public void renderEntityPrePatch(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci) {
        RenderEntityEvent.Pre event = new RenderEntityEvent.Pre(entityIn);
        EventManager.call(event);
        if (event.isCancelled())
            ci.cancel();
    }

    @Inject(method = "renderEntity", at = @At("TAIL"))
    public void renderEntityPostPatch(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci) {
        RenderEntityEvent.Post event = new RenderEntityEvent.Post(entityIn);
        EventManager.call(event);
    }
}
