package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.ProjectionUtils;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Nametags extends Feature {

    public Nametags() {
        super("Nametags", "Makes nametags better", Category.Render);
    }

    @EventTarget
    public void onRender2d(RenderEvent.Screen event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.equals(mc.player))
                continue;

            GlStateManager.pushMatrix();

            double deltaX = MathHelper.clampedLerp(player.lastTickPosX, player.posX, mc.getRenderPartialTicks());
            double deltaY = MathHelper.clampedLerp(player.lastTickPosY, player.posY, mc.getRenderPartialTicks());
            double deltaZ = MathHelper.clampedLerp(player.lastTickPosZ, player.posZ, mc.getRenderPartialTicks());

            Vec3d deltaVector = new Vec3d(deltaX, deltaY, deltaZ);
            Vec3d prevPosVector = new Vec3d(player.prevPosX, player.prevPosY, player.prevPosZ);
            double[] rpos = Util.getRenderPos();
            Vec3d renderPosVector = new Vec3d(rpos[0], rpos[1], rpos[2]);
            Vec3d screenPos = ProjectionUtils.toScaledScreenPos((deltaVector.subtract(prevPosVector)).add(renderPosVector));
            GlStateManager.translate(screenPos.x, screenPos.y, 0);
            GlStateManager.scale(3, 3, 0);

            System.out.println(screenPos.x + " " + screenPos.y);
            // le render
            mc.fontRenderer.drawStringWithShadow("Test", 0, 0, -1);
            GlStateManager.popMatrix();
        }
    }
}
