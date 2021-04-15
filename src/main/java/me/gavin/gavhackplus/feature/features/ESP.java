package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;

import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

public class ESP extends Feature {
	
	public ESP() {
		super("ESP", "Highlights entities", Category.Render);
	}
	
	@EventTarget
	public void onRenderWorld(RenderEvent.World event) {
        
        
        for (Entity e : mc.world.loadedEntityList) {
        	
        	if (e.equals(mc.player))
        		continue;
        	
        	double[] rpos = Util.getRenderPos();
        	AxisAlignedBB box = e.getEntityBoundingBox().offset(-rpos[0], -rpos[1], -rpos[2]);
        	
        	double x = (e.posX - e.lastTickPosX) * event.getPartialTicks();
        	double y = (e.posY - e.lastTickPosY) * event.getPartialTicks();
        	double z = (e.posZ - e.lastTickPosZ) * event.getPartialTicks();
        	
        	RenderUtil.prepareGL(1.5f);
        	RenderGlobal.renderFilledBox(x + box.minX, y + box.minY, z + box.minZ, x + box.maxX, y + box.maxY, z + box.maxZ, 1.0f, 1.0f, 1.0f, 0.2f);
        	RenderGlobal.drawBoundingBox(x + box.minX, y + box.minY, z + box.minZ, x + box.maxX, y + box.maxY, z + box.maxZ, 1.0f, 1.0f, 1.0f, 0.2f);
        	RenderUtil.releaseGL();
        }
	}

}
