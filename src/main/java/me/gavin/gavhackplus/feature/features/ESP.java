package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import io.netty.util.internal.MathUtil;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.ProjectionUtils;
import me.gavin.gavhackplus.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ESP extends Feature {

	public ESP() {
		super("ESP", "Show entities through walls", Category.Render);
	}

	@EventTarget
	public void onRender(RenderEvent.Screen event) {
		for (EntityPlayer ep : mc.world.playerEntities) {
			if (ep.equals(mc.player))
				continue;

			ScaledResolution sr = new ScaledResolution(mc);
			float[] corners = getCornerPositions(ep, event.getPartialTicks(), sr.getScaledWidth(), sr.getScaledHeight());

			if (corners != null) {
				GlStateManager.pushMatrix();
				GlStateManager.disableTexture2D();
				GlStateManager.color(0f, 0f, 0f, 1f);
				RenderUtil.lineBox2D(corners[0], corners[1], corners[2], corners[3], 4f);
				GlStateManager.color(1f, 0f, 0f, 1f);
				RenderUtil.lineBox2D(corners[0], corners[1], corners[2], corners[3], 1.5f);
				GlStateManager.enableTexture2D();
				GlStateManager.popMatrix();
			}
		}
	}

	private final ICamera camera = new Frustum();

	// method from seppuku to get corners (tweaked a bit)
	private float[] getCornerPositions(Entity e, float partialTicks, int width, int height) {
		float x = -1;
		float y = -1;
		float w = width + 1;
		float h = height + 1;


		final Vec3d pos = new Vec3d(
				MathHelper.clampedLerp(e.lastTickPosX, e.posX, partialTicks),
				MathHelper.clampedLerp(e.lastTickPosY, e.posY, partialTicks),
				MathHelper.clampedLerp(e.lastTickPosZ, e.posZ, partialTicks));

		AxisAlignedBB bb = e.getEntityBoundingBox();

		if (e instanceof EntityEnderCrystal) {
			bb = new AxisAlignedBB(bb.minX + 0.3f, bb.minY + 0.2f, bb.minZ + 0.3f, bb.maxX - 0.3f, bb.maxY, bb.maxZ - 0.3f);
		}

		if (e instanceof EntityItem) {
			bb = new AxisAlignedBB(bb.minX, bb.minY + 0.7f, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
		}

		bb = bb.expand(0.15f, 0.1f, 0.15f);

		camera.setPosition(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

		if (!camera.isBoundingBoxInFrustum(bb)) {
			return null;
		}

		final Vec3d[] corners = {
				new Vec3d(bb.minX - bb.maxX + e.width / 2, 0, bb.minZ - bb.maxZ + e.width / 2),
				new Vec3d(bb.maxX - bb.minX - e.width / 2, 0, bb.minZ - bb.maxZ + e.width / 2),
				new Vec3d(bb.minX - bb.maxX + e.width / 2, 0, bb.maxZ - bb.minZ - e.width / 2),
				new Vec3d(bb.maxX - bb.minX - e.width / 2, 0, bb.maxZ - bb.minZ - e.width / 2),

				new Vec3d(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
				new Vec3d(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2),
				new Vec3d(bb.minX - bb.maxX + e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2),
				new Vec3d(bb.maxX - bb.minX - e.width / 2, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2)
		};

		for (Vec3d vec : corners) {
			final Vec3d projection = ProjectionUtils.toScaledScreenPos(new Vec3d(
					pos.x + vec.x,
					pos.y + vec.y,
					pos.z + vec.z));

			x = Math.max(x, (float) projection.x);
			y = Math.max(y, (float) projection.y);

			w = Math.min(w, (float) projection.x);
			h = Math.min(h, (float) projection.y);
		}

		if (x != -1 && y != -1 && w != width + 1 && h != height + 1) {
			return new float[]{x, y, w, h};
		}

		return null;
	}
}
