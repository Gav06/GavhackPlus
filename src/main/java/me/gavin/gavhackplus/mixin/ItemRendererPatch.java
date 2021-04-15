package me.gavin.gavhackplus.mixin;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.events.RenderHandEvent;
import me.gavin.gavhackplus.feature.features.ItemViewmodel;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererPatch {

    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    public void transformSideFirstPersonPatch(EnumHandSide hand, float swingProgress, CallbackInfo ci) {
        EventManager.call(new RenderHandEvent(hand));
    }

    @Inject(method = "transformEatFirstPerson", at = @At("HEAD"), cancellable = true)
    public void transformEatFirstPersonPath(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo ci) {
        if (Gavhack.featureManager.isFeatureEnabled(ItemViewmodel.class) && ItemViewmodel.cancelEating.getValue())
            ci.cancel();
    }
}
