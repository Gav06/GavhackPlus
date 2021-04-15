package me.gavin.gavhackplus.mixin;

import com.darkmagician6.eventapi.EventManager;
import me.gavin.gavhackplus.events.AddBoxToListEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Used for Jesus (method from KAMI)
 */
@Mixin(BlockStateContainer.StateImplementation.class)
public class StateImplementationPatch {

    @Shadow @Final private Block block;

    @Redirect(method = "addCollisionBoxToList", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V"))
    public void addCollisionBoxToList(Block block, IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        AddBoxToListEvent event = new AddBoxToListEvent(block, state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        EventManager.call(event);
        if (!event.isCancelled()) {
            block.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
        }
    }
}
