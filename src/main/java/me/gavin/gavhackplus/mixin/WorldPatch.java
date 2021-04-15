package me.gavin.gavhackplus.mixin;

import me.gavin.gavhackplus.Gavhack;
import me.gavin.gavhackplus.feature.features.NoRain;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class WorldPatch {

    @Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    public void getRainStrengthPatch(CallbackInfoReturnable<Float> cir) {
        if (Gavhack.featureManager.isFeatureEnabled(NoRain.class))
            cir.cancel();
    }
}
