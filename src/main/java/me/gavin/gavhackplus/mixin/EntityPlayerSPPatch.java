package me.gavin.gavhackplus.mixin;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhackplus.events.MoveEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPPatch {

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    public void onMove(MoverType type, double x, double y, double z, CallbackInfo ci) {
        MoveEvent event = new MoveEvent(x, y, z);
        EventManager.call(event);
        if (event.isCancelled())
            ci.cancel();

        x = event.getX();
        y = event.getY();
        z = event.getZ();
    }
}
