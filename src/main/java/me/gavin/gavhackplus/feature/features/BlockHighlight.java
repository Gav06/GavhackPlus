package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;

public class BlockHighlight extends Feature {

    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block you are looking", Category.Render);
    }

    @EventTarget
    public void onRender3d(RenderEvent.World event) {
        RayTraceResult rayTrace = mc.objectMouseOver;
        if (rayTrace != null) {

            if (rayTrace.typeOfHit != RayTraceResult.Type.BLOCK)
                return;

            AxisAlignedBB box = mc.world.getBlockState(rayTrace.getBlockPos()).getSelectedBoundingBox(mc.world, rayTrace.getBlockPos());

            RenderUtil.prepareGL(1.0f);
            double[] renderPos = Util.getRenderPos();
            RenderGlobal.renderFilledBox(box.offset(-renderPos[0], -renderPos[1], -renderPos[2]), 1f, 1f, 1f, 0.5f);
            RenderUtil.releaseGL();
        }
    }
}
