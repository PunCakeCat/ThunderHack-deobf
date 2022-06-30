//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public final class GuiRenderHelper
{
    public static void drawBorderedRect(final float x, final float y, final float w, final float h, final int outlineColor, final int color) {
        drawRect(x, y, w, h, color);
        drawOutlineRect(x, y, w, h, 1.0f, outlineColor);
    }
    
    public static void drawRect(final float x, final float y, final float w, final float h, final int color) {
        final float right = x + w;
        final float bottom = y + h;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)x, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawOutlineRect(final float x, final float y, final float w, final float h, final float lineWidth, final int color) {
        final float right = x + w;
        final float bottom = y + h;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);
        GL11.glEnable(2848);
        GlStateManager.glLineWidth(lineWidth);
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)x, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).endVertex();
        bufferBuilder.pos((double)x, (double)bottom, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void prepareScissorBox(final ScaledResolution sr, final float x, final float y, final float width, final float height) {
        final float right = x + width;
        final float bottom = y + height;
        final int srScaleFactor = sr.getScaleFactor();
        GL11.glScissor((int)(x * srScaleFactor), (int)((sr.getScaledHeight() - bottom) * srScaleFactor), (int)((right - x) * srScaleFactor), (int)((bottom - y) * srScaleFactor));
    }
}
