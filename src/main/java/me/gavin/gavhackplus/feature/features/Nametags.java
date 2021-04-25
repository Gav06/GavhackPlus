package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.gavhackplus.events.RenderEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import me.gavin.gavhackplus.setting.impl.NumberSetting;
import me.gavin.gavhackplus.util.RenderUtil;
import me.gavin.gavhackplus.util.Util;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Nametags extends Feature {

    private NumberSetting scale = new NumberSetting("Scale", this, 2.5f, 1.0f, 5.0f, 0.1f);
    private BooleanSetting health = new BooleanSetting("Health", this, true);
    private BooleanSetting ping = new BooleanSetting("Ping", this, true);
    private BooleanSetting armor = new BooleanSetting("Armor", this, true);
    private BooleanSetting items = new BooleanSetting("Items", this, true);

    public Nametags() {
        super("Nametags", "Makes nametags better", Category.Render);
        addSettings(scale, health, ping, armor, items);
    }

    @EventTarget
    public void onRender3d(RenderEvent.World event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.equals(mc.player))
                continue;

            double yAdd = player.isSneaking() ? 1.75 : 2.25;

            double deltaX = MathHelper.clampedLerp(player.lastTickPosX, player.posX, mc.getRenderPartialTicks());
            double deltaY = MathHelper.clampedLerp(player.lastTickPosY + yAdd, player.posY + yAdd, mc.getRenderPartialTicks());
            double deltaZ = MathHelper.clampedLerp(player.lastTickPosZ, player.posZ, mc.getRenderPartialTicks());

            double lastDistance = getDistance(
                    new Vec3d(mc.player.lastTickPosX, mc.player.lastTickPosY, mc.player.lastTickPosZ),
                    new Vec3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ));
            double nowDistance = getDistance(mc.player.getPositionVector(), player.getPositionVector());
            double lerp = MathHelper.clampedLerp(lastDistance, nowDistance, event.getPartialTicks());
            if (lerp <= 3)
                lerp = 3;
            double deltaScale = lerp;

            deltaScale /= (200.0D / scale.getValue());

            double[] rpos = Util.getRenderPos();
            RenderUtil.prepareGL(0.1f);

            // moving everything to the correct position
            GlStateManager.translate(deltaX - rpos[0], deltaY - rpos[1], deltaZ - rpos[2]);
            // rotating so it is always facing the player
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);

            // scaling properly
            GlStateManager.scale(-deltaScale, -deltaScale, -deltaScale);

            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

            int ping = -1;

            if (mc.getConnection() != null) {
                ping = mc.getConnection().getPlayerInfo(player.getUniqueID()).getResponseTime();
            }

            double health = player.getHealth() + player.getAbsorptionAmount();

            // le render
            String str = "";
            if (this.ping.getValue()) {
                str += ChatFormatting.GRAY + "" + ping + "ms " + ChatFormatting.RESET;
            }
            str += player.getName();
            if (this.health.getValue()) {
                str += " " + getHealthColor(health) + String.format("%.1f", health);
            }
            RenderUtil.drawBorderedRect(
                    -((mc.fontRenderer.getStringWidth(str) + 2) / 2f),
                    -(mc.fontRenderer.FONT_HEIGHT + 2 ),
                    (mc.fontRenderer.getStringWidth(str) + 2) / 2f,
                    1,
                    0xCF111111,
                    0xFFFFFFFF,
                    1.0f);

            GlStateManager.enableTexture2D();
            mc.fontRenderer.drawStringWithShadow(str, -(mc.fontRenderer.getStringWidth(str) / 2f), -(mc.fontRenderer.FONT_HEIGHT), -1);

            int y = -(mc.fontRenderer.FONT_HEIGHT * 3);
            if (this.armor.getValue()) {
                int x = -30;

                for (ItemStack armorPiece : Lists.reverse(player.inventory.armorInventory)) {
                    if (armorPiece.isEmpty()) {
                        x += 15;
                        continue;
                    }

                    renderItem(armorPiece, x, y);

                    x += 15;
                }
            }

            if (this.items.getValue()) {
                if (!player.inventory.getStackInSlot(player.inventory.currentItem).isEmpty()) {
                    renderItem(player.inventory.getStackInSlot(player.inventory.currentItem), -48, y);
                }

                // offhand
                if (!player.getHeldItemOffhand().isEmpty()) {
                    renderItem(player.getHeldItemOffhand(), 35, y);
                }
            }

            RenderUtil.releaseGL();
        }

    }

    private double getDistance(Vec3d pos1, Vec3d pos2) {
        return Math.sqrt(Math.pow(pos2.x - pos1.x, 2) + Math.pow(pos2.y - pos1.y, 2) + Math.pow(pos2.z - pos1.z, 2));
    }

    private ChatFormatting getHealthColor(double health) {
        if (health >= 15.0) {
            return ChatFormatting.GREEN;
        } else if (health >= 10.0) {
            return ChatFormatting.YELLOW;
        } else if (health >= 5.0) {
            return ChatFormatting.RED;
        } else {
            return ChatFormatting.DARK_RED;
        }
    }

    // method is from Gamesense nametags and tweaked a bit
    //
    // i was having issues getting the items to render with the effects properly,
    // so i looked how someone else did it
    private void renderItem(ItemStack itemStack, int posX, int posY) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        mc.getRenderItem().zLevel = -150.0f;
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, posX, posY);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, itemStack, posX, posY);
        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0f;
    }
}
