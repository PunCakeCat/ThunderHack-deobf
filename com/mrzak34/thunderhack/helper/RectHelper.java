//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class RectHelper
{
    public static long delta;
    
    public static void drawRoundedRect(final double x, final double y, final double width, final double height, final float radius, final Color color) {
        final float x2 = (float)(x + (radius / 2.0f + 0.5f));
        final float y2 = (float)(y + (radius / 2.0f + 0.5f));
        final float width2 = (float)(width - (radius / 2.0f + 0.5f));
        final float height2 = (float)(height - (radius / 2.0f + 0.5f));
        drawRect(x2, y2, x2 + width2, y2 + height2, color.getRGB());
        polygon(x, y, radius * 2.0f, 360.0, true, color);
        polygon(x + width2 - radius + 1.2, y, radius * 2.0f, 360.0, true, color);
        polygon(x + width2 - radius + 1.2, y + height2 - radius + 1.0, radius * 2.0f, 360.0, true, color);
        polygon(x, y + height2 - radius + 1.0, radius * 2.0f, 360.0, true, color);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        drawRect(x2 - radius / 2.0f - 0.5f, y2 + radius / 2.0f, x2 + width2, y2 + height2 - radius / 2.0f, color.getRGB());
        drawRect(x2, y2 + radius / 2.0f, x2 + width2 + radius / 2.0f + 0.5f, y2 + height2 - radius / 2.0f, color.getRGB());
        drawRect(x2 + radius / 2.0f, y2 - radius / 2.0f - 0.5f, x2 + width2 - radius / 2.0f, y + height2 - radius / 2.0f, color.getRGB());
        drawRect(x2 + radius / 2.0f, y2, x2 + width2 - radius / 2.0f, y2 + height2 + radius / 2.0f + 0.5f, color.getRGB());
    }
    
    public static void polygon(final double x, final double y, double sideLength, final double amountOfSides, final boolean filled, final Color color) {
        sideLength /= 2.0;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        GlStateManager.disableAlpha();
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        if (!filled) {
            GL11.glLineWidth(1.0f);
        }
        GL11.glEnable(2848);
        GL11.glBegin(filled ? 6 : 3);
        for (double i = 0.0; i <= amountOfSides; ++i) {
            final double angle = i * 6.283185307179586 / amountOfSides;
            GL11.glVertex2d(x + sideLength * Math.cos(angle) + sideLength, y + sideLength * Math.sin(angle) + sideLength);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableAlpha();
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawRectBetter(final double x, final double y, final double width, final double height, final int color) {
        drawRect(x, y, x + width, y + height, color);
    }
    
    public static void drawSmoothRectBetter(final float x, final float y, final float width, final float height, final int color) {
        drawSmoothRect(x, y, x + width, y + height, color);
    }
    
    public static void drawRect(double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f4, f5, f6, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).endVertex();
        bufferbuilder.pos(right, top, 0.0).endVertex();
        bufferbuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawSmoothRect(final float left, final float top, final float right, final float bottom, final int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawRect(left * 2.0f - 1.0f, top * 2.0f, left * 2.0f, bottom * 2.0f - 1.0f, color);
        drawRect(left * 2.0f, top * 2.0f - 1.0f, right * 2.0f, top * 2.0f, color);
        drawRect(right * 2.0f, top * 2.0f, right * 2.0f + 1.0f, bottom * 2.0f - 1.0f, color);
        drawRect(left * 2.0f, bottom * 2.0f - 1.0f, right * 2.0f, bottom * 2.0f, color);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawSkeetRectWithoutBorder(final float x, final float y, final float right, final float bottom) {
        drawSmoothRect(x - 41.0f, y - 61.0f, right + 41.0f, bottom + 61.0f, new Color(48, 48, 48, 255).getRGB());
        drawSmoothRect(x - 40.0f, y - 60.0f, right + 40.0f, bottom + 60.0f, new Color(17, 17, 17, 255).getRGB());
    }
    
    public static void drawSkeetRect(final float x, final float y, final float right, final float bottom) {
        drawRect(x - 46.5f, y - 66.5f, right + 46.5f, bottom + 66.5f, new Color(0, 0, 0, 255).getRGB());
        drawRect(x - 46.0f, y - 66.0f, right + 46.0f, bottom + 66.0f, new Color(48, 48, 48, 255).getRGB());
        drawRect(x - 44.5f, y - 64.5f, right + 44.5f, bottom + 64.5f, new Color(33, 33, 33, 255).getRGB());
        drawRect(x - 43.5f, y - 63.5f, right + 43.5f, bottom + 63.5f, new Color(0, 0, 0, 255).getRGB());
        drawRect(x - 43.0f, y - 63.0f, right + 43.0f, bottom + 63.0f, new Color(9, 9, 9, 255).getRGB());
        drawRect(x - 40.5f, y - 60.5f, right + 40.5f, bottom + 60.5f, new Color(48, 48, 48, 255).getRGB());
        drawRect(x - 40.0f, y - 60.0f, right + 40.0f, bottom + 60.0f, new Color(17, 17, 17, 255).getRGB());
    }
    
    public static void drawBorderedRect(final float left, final float top, final float right, final float bottom, final float borderWidth, final int insideColor, final int borderColor, final boolean borderIncludedInBounds) {
        drawRect(left - (borderIncludedInBounds ? 0.0f : borderWidth), top - (borderIncludedInBounds ? 0.0f : borderWidth), right + (borderIncludedInBounds ? 0.0f : borderWidth), bottom + (borderIncludedInBounds ? 0.0f : borderWidth), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0f), top + (borderIncludedInBounds ? borderWidth : 0.0f), right - (borderIncludedInBounds ? borderWidth : 0.0f), bottom - (borderIncludedInBounds ? borderWidth : 0.0f), insideColor);
    }
    
    public static void drawBorder(final float left, final float top, final float right, final float bottom, final float borderWidth, final int insideColor, final int borderColor, final boolean borderIncludedInBounds) {
        drawRect(left - (borderIncludedInBounds ? 0.0f : borderWidth), top - (borderIncludedInBounds ? 0.0f : borderWidth), right + (borderIncludedInBounds ? 0.0f : borderWidth), bottom + (borderIncludedInBounds ? 0.0f : borderWidth), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0f), top + (borderIncludedInBounds ? borderWidth : 0.0f), right - (borderIncludedInBounds ? borderWidth : 0.0f), bottom - (borderIncludedInBounds ? borderWidth : 0.0f), insideColor);
    }
    
    public static void drawOutlineRect(final float x, final float y, final float width, final float height, final Color color, final Color colorTwo) {
        drawRect(x, y, x + width, y + height, color.getRGB());
        final int colorRgb = colorTwo.getRGB();
        drawRect(x - 1.0f, y, x, y + height, colorRgb);
        drawRect(x + width, y, x + width + 1.0f, y + height, colorRgb);
        drawRect(x - 1.0f, y - 1.0f, x + width + 1.0f, y, colorRgb);
        drawRect(x - 1.0f, y + height, x + width + 1.0f, y + height + 1.0f, colorRgb);
    }
    
    static {
        RectHelper.delta = 0L;
    }
}
