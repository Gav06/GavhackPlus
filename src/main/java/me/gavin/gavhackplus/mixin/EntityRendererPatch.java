package me.gavin.gavhackplus.mixin;

import me.gavin.gavhackplus.client.Gavhack;
import me.gavin.gavhackplus.feature.features.AntiFog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
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

    @Redirect(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;getBlockStateAtEntityViewpoint(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;F)Lnet/minecraft/block/state/IBlockState;"))
    public IBlockState getBlockStateAtEntityViewpoint(World worldIn, Entity entityIn, float p_186703_2_) {
        if (Gavhack.featureManager.isFeatureEnabled(AntiFog.class)) return Blocks.AIR.getDefaultState();
        return ActiveRenderInfo.getBlockStateAtEntityViewpoint(worldIn, entityIn, p_186703_2_);
    }
}
