package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Tracers extends Feature {

    private NumberSetting lineWidth = new NumberSetting("LineWidth", this,1.5f, 1.0f, 20.0f, 0.25f);
    private ModeSetting color = new ModeSetting("Color", this, "Distance", "Distance", "Default");

    public Tracers() {
        super("Tracers", "Draw lines to other players",Category.Render);
        addSettings(lineWidth, color);
    }

    @EventTarget
    public void onRender3d(RenderEvent.World event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.equals(mc.player))
                continue;

            double interpX = MathHelper.clampedLerp(player.lastTickPosX, player.posX, event.getPartialTicks());
            double interpY = MathHelper.clampedLerp(player.lastTickPosY, player.posY, event.getPartialTicks());
            double interpZ = MathHelper.clampedLerp(player.lastTickPosZ, player.posZ, event.getPartialTicks());

            Vec3d eyePos = ActiveRenderInfo.getCameraPosition();

            double[] rpos = Util.getRenderPos();
            RenderUtil.prepareGL(1.0f);
            if (color.getMode().equals("Default")) {
                Util.glColor(ColorMod.globalColor);
            } else {
                Util.glColor(Util.getDistanceColor((int)mc.player.getDistance(player)));
            }
            RenderUtil.drawLine3d(eyePos.x, eyePos.y, eyePos.z, interpX - rpos[0], interpY - rpos[1], interpZ - rpos[2], lineWidth.getValue());
            RenderUtil.releaseGL();
        }
    }
}
