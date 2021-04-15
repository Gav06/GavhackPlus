package me.gavin.gavhackplus.util;

import net.minecraft.client.renderer.GlStateManager;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL32;

public class RenderUtil {

	public static void prepareGL(float lineWidth) {
		GlStateManager.pushMatrix();
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ZERO, GL_ONE);
		GlStateManager.shadeModel(GL_SMOOTH);
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		glEnable(GL_LINE_SMOOTH);
		glEnable(GL32.GL_DEPTH_CLAMP);
		glLineWidth(lineWidth);
	}

	public static void releaseGL() {
		glDisable(GL32.GL_DEPTH_CLAMP);
		glDisable(GL_LINE_SMOOTH);
		GlStateManager.enableAlpha();
		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.glLineWidth(1.0f);
		GlStateManager.shadeModel(GL_FLAT);
		glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
		GlStateManager.popMatrix();
	}
	
	public static void prepareGL2D() {
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
	}
	
	public static void releaseGL2D() {
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
	}
	
	public static void glLine2D(float x1, float y1, float x2, float y2, float width) {
		glLineWidth(width);
		glBegin(GL_LINES);
		{
			glVertex2f(x1, y1);
			glVertex2f(x2, y2);
		}
		glEnd();
	}
	
	public static void lineBox2D(float x1, float y1, float x2, float y2, float width) {
		glLineWidth(width);
		glEnable(GL_LINE_SMOOTH);
		glBegin(GL_LINE_LOOP);
		{
			glVertex2f(x1, y1);
			glVertex2f(x2, y1);
			glVertex2f(x2, y2);
			glVertex2f(x1, y2);
		}
		glEnd();
	}
	
	public static void drawCircle(double x, double y, float radius, int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0f;
        float red = (float) (color >> 16 & 255) / 255.0f;
        float green = (float) (color >> 8 & 255) / 255.0f;
        float blue = (float) (color & 255) / 255.0f;
        glColor4f(red, green, blue, alpha);
        glBegin(9);
        int i = 0;
        while (i <= 360) {
            glVertex2d(x + Math.sin((double) i * 3.141526 / 180.0) * (double) radius, y + Math.cos((double) i * 3.141526 / 180.0) * (double) radius);
            ++i;
        }
        glEnd();
    }
	
	public static void drawBorderedCircle(double x, double y, float radius, int outsideC, int insideC) {
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glPushMatrix();
        glScalef(0.1f, 0.1f, 0.1f);
        drawCircle(x *= 10, y *= 10, radius *= 10.0f, insideC);
        glScalef(10.0f, 10.0f, 10.0f);
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_LINE_SMOOTH);
    }
}
