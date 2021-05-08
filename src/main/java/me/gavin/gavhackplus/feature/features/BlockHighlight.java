package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;

public class BlockHighlight extends Feature {

    public BlockHighlight() {
        super("BlockHighlight", "Highlights the block you are looking", Category.Render);
        addSettings(
                red,
                green,
                blue
        );
    }

    public NumberSetting red = new NumberSetting("Red", this,255, 0, 255, 1);
    public NumberSetting green = new NumberSetting("Green", this,255, 0, 255, 1);
    public NumberSetting blue = new NumberSetting("Blue", this,255, 0, 255, 1);

    @EventTarget
    public void onRender3d(RenderEvent.World event) {
        RayTraceResult rayTrace = mc.objectMouseOver;
        if (rayTrace != null) {

            if (rayTrace.typeOfHit != RayTraceResult.Type.BLOCK)
                return;

            AxisAlignedBB box = mc.world.getBlockState(rayTrace.getBlockPos()).getSelectedBoundingBox(mc.world, rayTrace.getBlockPos());

            RenderUtil.prepareGL(1.0f);
            double[] renderPos = Util.getRenderPos();
            RenderGlobal.renderFilledBox(box.offset(-renderPos[0], -renderPos[1], -renderPos[2]), red.getValue() / 255f, green.getValue() / 255f, blue.getValue() / 255f, 0.5f);
            RenderUtil.releaseGL();
        }
    }
}
