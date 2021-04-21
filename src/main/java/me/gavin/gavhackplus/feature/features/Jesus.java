package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.AddBoxToListEvent;
import me.gavin.gavhackplus.events.TickEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

/**
 * Method of doing jesus is from KAMI by 086 (i have never made jesus before so idk)
 * https://gitub.com/zeroeightysix/KAMI/tree/master
 * <p>
 * Note: this was NOT pasted, I read the code and tried to understand what it is doing and typed it in myself
 */
public class Jesus extends Feature {

    private final AxisAlignedBB WATER_AABB = new AxisAlignedBB(0.D, 0.D, 0.D, 1.D, 0.99D, 1.D);

    public Jesus() {
        super("Jesus", "Walk like Jesus Christ", Category.Movement);
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if ((mc.player.isInWater() || mc.player.isInLava()) && !mc.player.isSneaking()) {

            float add = mc.player.isInLava() ? 0.3f : 0.0f;
            mc.player.motionY = 0.20 + add;
            // entity jesus :O
            if (mc.player.getRidingEntity() != null && !(mc.player.getRidingEntity() instanceof EntityBoat)) {
                mc.player.getRidingEntity().motionY = 0.3;
            }
        }
    }

    @EventTarget
    public void collisionBoxAdd(AddBoxToListEvent event) {
        // deciding when to make the water block solid or not
        if (mc.player != null
                && (event.getBlock() instanceof BlockLiquid)
                && (isBeingRiddenByPlayer(event.getEntity()) || event.getEntity()==mc.player)
                && !(event.getEntity() instanceof EntityBoat)
                && !mc.player.isSneaking()
                && mc.player.fallDistance < 3
                && !isInWater(mc.player)
                && (isAboveWater(mc.player) || isAboveWater(mc.player.getRidingEntity()))
                && isAboveBlock(mc.player, event.getPos())) {
            AxisAlignedBB waterAABB = WATER_AABB.offset(event.getPos());
            if (event.getEntityBox().intersects(waterAABB))
                event.getCollidingBoxes().add(waterAABB);
            event.setCancelled(true);
        }
    }

    private boolean isEntityRiddenByPlayer(Entity e) {
        return e.isBeingRidden() && e.getRidingEntity().equals(mc.player);
    }

    private boolean isAboveWater(Entity entity) {
        if (entity == null) return false;

        double y = entity.posY - ((entity.equals(mc.player) ? 0.2 : 0.5)); // increasing this seems to flag more in NCP but needs to be increased so the player lands on solid water

        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
                BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

                if (mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) return true;
            }

        return false;
    }

    private boolean isInWater(Entity entity) {
        if (entity == null) return false;

        double y = entity.posY + 0.01;

        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
                BlockPos pos = new BlockPos(x, (int) y, z);

                if (mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid)
                    return true;
            }

        }

        return false;
    }

    private boolean isBeingRiddenByPlayer(Entity entityIn) {
        return mc.player != null && entityIn != null && entityIn.equals(mc.player.getRidingEntity());
    }

    private boolean isAboveBlock(Entity entity, BlockPos blockPos) {
        return entity.posY >= blockPos.getY();
    }
}
