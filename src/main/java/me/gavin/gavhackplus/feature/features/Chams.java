package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.RenderEntityEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.ModeSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Chams extends Feature {

    private BooleanSetting players = new BooleanSetting("Players", this, true);
    private BooleanSetting crystals = new BooleanSetting("Crystals", this, true);
    private ModeSetting renderMode = new ModeSetting("RenderMode", this, "Default", "Default", "Color");

    private NumberSetting r = new NumberSetting("R", this, 0f, 0f, 255f, 1f);
    private NumberSetting g = new NumberSetting("G", this, 0f, 0f, 255f, 1f);
    private NumberSetting b = new NumberSetting("B", this, 0f, 0f, 255f, 1f);
    private NumberSetting a = new NumberSetting("A", this, 0f, 0f, 255f, 1f);

    public Chams() {
        super("Chams", "See entities through walls", Category.Render);
        addSettings(players, crystals, renderMode,
                r, g, b, a);
    }

    @EventTarget
    public void onRenderPre(RenderEntityEvent.Pre event) {
        if (event.getEntity() instanceof EntityPlayer && players.getValue()) {
            doChamPre();
        }

        if (event.getEntity() instanceof EntityEnderCrystal && crystals.getValue()) {
            doChamPre();
        }
    }

    @EventTarget
    public void onRenderPost(RenderEntityEvent.Post event) {
        if (event.getEntity() instanceof EntityPlayer && players.getValue()) {
            doChamPost();
        }

        if (event.getEntity() instanceof EntityEnderCrystal && crystals.getValue()) {
            doChamPost();
        }
    }

    private void doChamPre() {
        GlStateManager.pushMatrix();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

        // disable shadows and outline
        mc.getRenderManager().setRenderShadow(false);
        mc.getRenderManager().setRenderOutlines(false);

        // making entity visible behind blocks
        GL11.glDepthRange(0.0, 0.1);

        if (renderMode.getMode().equals("Color")) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            GlStateManager.color(r.getValue() / 255f, g.getValue() / 255f, b.getValue() / 255f, a.getValue() / 255f);
        }
        GlStateManager.popMatrix();
    }

    private void doChamPost() {
        GlStateManager.pushMatrix();

        // re enabling stuff
        mc.getRenderManager().setRenderShadow(mc.getRenderManager().isRenderShadow());

        // make un visible
        GL11.glDepthRange(0.0, 1.0);

        // disable color model (if mode is color)
        if (renderMode.getMode().equals("Color")) {
            GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            GlStateManager.color(1f, 1f, 1f, 1f);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        // pop matrix
        GlStateManager.popMatrix();
    }

}
