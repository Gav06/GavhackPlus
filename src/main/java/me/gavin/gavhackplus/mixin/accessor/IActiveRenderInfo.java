package me.gavin.gavhackplus.mixin.accessor;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ActiveRenderInfo.class)
public interface IActiveRenderInfo {

    @Accessor("position")
    void setCamPosition(Vec3d newPos);
}
