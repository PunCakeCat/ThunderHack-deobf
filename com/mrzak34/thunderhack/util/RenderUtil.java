//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import java.nio.*;
import net.minecraft.client.renderer.culling.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.features.gui.font.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import java.text.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.world.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import java.util.*;
import net.minecraft.util.*;

public class RenderUtil implements Util
{
    private static final Frustum frustrum;
    private static final FloatBuffer screenCoords;
    private static final IntBuffer viewport;
    private static final FloatBuffer modelView;
    private static final FloatBuffer projection;
    public static RenderItem itemRender;
    public static ICamera camera;
    private static boolean depth;
    private static boolean texture;
    private static boolean clean;
    private static boolean bind;
    private static boolean override;
    public static Tessellator tessellator;
    public static BufferBuilder bufferbuilder;
    
    public static float lerp(final float a, final float b, final float f) {
        return a + f * (b - a);
    }
    
    public static Color TwoColoreffect(final Color cl1, final Color cl2, final double speed) {
        final double thing = speed / 4.0 % 1.0;
        final float val = MathHelper.clamp((float)Math.sin(18.84955592153876 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(lerp(cl1.getRed() / 255.0f, cl2.getRed() / 255.0f, val), lerp(cl1.getGreen() / 255.0f, cl2.getGreen() / 255.0f, val), lerp(cl1.getBlue() / 255.0f, cl2.getBlue() / 255.0f, val));
    }
    
    public static void glColor(final int red, final int green, final int blue, final int alpha) {
        GlStateManager.color(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
    }
    
    public static int rainbowNew(final int delay, final float saturation, final float brightness) {
        double rainbow = Math.ceil((double)((System.currentTimeMillis() + delay) / 16L));
        rainbow %= 360.0;
        return Color.getHSBColor((float)(rainbow / 360.0), saturation, brightness).getRGB();
    }
    
    public static void drawCircleWithTexture(final float cX, final float cY, final int start, final int end, final float radius, final ResourceLocation res, final int color) {
        GL11.glPushMatrix();
        RenderUtil.mc.getTextureManager().bindTexture(res);
        GL11.glEnable(2881);
        GL11.glBlendFunc(770, 771);
        colorflux(color);
        GL11.glBegin(9);
        for (int i = start; i < end; ++i) {
            final double radian = i * 0.017453292519943295;
            final double xsin = Math.sin(radian);
            final double ycos = Math.cos(radian);
            final double x = xsin * radius;
            final double y = ycos * radius;
            final double tx = xsin * 0.5 + 0.5;
            final double ty = ycos * 0.5 + 0.5;
            GL11.glTexCoord2d(cX + tx, cY + ty);
            GL11.glVertex2d(cX + x, cY + y);
        }
        GL11.glEnd();
        GL11.glDisable(2881);
        GL11.glPopMatrix();
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void glColor(final Color color) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static void glColor(final Color color, final int alpha) {
        glColor(color, alpha / 255.0f);
    }
    
    public static void glColor(final Color color, final float alpha) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static void glColor(final int hex, final int alpha) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha / 255.0f);
    }
    
    public static void glColor(final int hex, final float alpha) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static Color setAlpha(final Color color, int alpha) {
        alpha = MathHelper.clamp(alpha, 0, 255);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    public static Color astolfoColors45(final float yDist, final float yTotal, final float saturation, final float speedt) {
        float speed;
        float hue;
        for (speed = 1800.0f, hue = System.currentTimeMillis() % (int)speed + (yTotal - yDist) * speedt; hue > speed; hue -= speed) {}
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        hue += 0.5f;
        return Color.getHSBColor(hue, saturation, 1.0f);
    }
    
    public static int getRainbow(final int speed, final int offset, final float s, final float b) {
        final float hue = (float)((System.currentTimeMillis() + offset) % speed);
        return Color.getHSBColor(hue / speed, s, b).getRGB();
    }
    
    public static void draw2DGradientRect(final float left, final float top, final float right, final float bottom, final int leftBottomColor, final int leftTopColor, final int rightBottomColor, final int rightTopColor) {
        final float lba = (leftBottomColor >> 24 & 0xFF) / 255.0f;
        final float lbr = (leftBottomColor >> 16 & 0xFF) / 255.0f;
        final float lbg = (leftBottomColor >> 8 & 0xFF) / 255.0f;
        final float lbb = (leftBottomColor & 0xFF) / 255.0f;
        final float rba = (rightBottomColor >> 24 & 0xFF) / 255.0f;
        final float rbr = (rightBottomColor >> 16 & 0xFF) / 255.0f;
        final float rbg = (rightBottomColor >> 8 & 0xFF) / 255.0f;
        final float rbb = (rightBottomColor & 0xFF) / 255.0f;
        final float lta = (leftTopColor >> 24 & 0xFF) / 255.0f;
        final float ltr = (leftTopColor >> 16 & 0xFF) / 255.0f;
        final float ltg = (leftTopColor >> 8 & 0xFF) / 255.0f;
        final float ltb = (leftTopColor & 0xFF) / 255.0f;
        final float rta = (rightTopColor >> 24 & 0xFF) / 255.0f;
        final float rtr = (rightTopColor >> 16 & 0xFF) / 255.0f;
        final float rtg = (rightTopColor >> 8 & 0xFF) / 255.0f;
        final float rtb = (rightTopColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 0.0).color(rtr, rtg, rtb, rta).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0).color(ltr, ltg, ltb, lta).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 0.0).color(lbr, lbg, lbb, lba).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0).color(rbr, rbg, rbb, rba).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void draw1DGradientRect(final float left, final float top, final float right, final float bottom, final int leftColor, final int rightColor) {
        final float la = (leftColor >> 24 & 0xFF) / 255.0f;
        final float lr = (leftColor >> 16 & 0xFF) / 255.0f;
        final float lg = (leftColor >> 8 & 0xFF) / 255.0f;
        final float lb = (leftColor & 0xFF) / 255.0f;
        final float ra = (rightColor >> 24 & 0xFF) / 255.0f;
        final float rr = (rightColor >> 16 & 0xFF) / 255.0f;
        final float rg = (rightColor >> 8 & 0xFF) / 255.0f;
        final float rb = (rightColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 0.0).color(rr, rg, rb, ra).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0).color(lr, lg, lb, la).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 0.0).color(lr, lg, lb, la).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0).color(rr, rg, rb, ra).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void drawShadowedOutlineRectRB(final float x, final float y, final float width, final float height, final int color1, final float lineWidth) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder builder = tessellator.getBuffer();
        final float mx = x + width;
        final float my = y + height;
        final int clr = 0;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.shadeModel(7425);
        builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        drawSeparateGradientRect(builder, mx, y, lineWidth, lineWidth, 0, 0, 0, color1);
        drawSeparateGradientRect(builder, mx, y + lineWidth, lineWidth, height - lineWidth, color1, 0, 0, color1);
        drawSeparateGradientRect(builder, mx, my, lineWidth, lineWidth, color1, 0, 0, 0);
        drawSeparateGradientRect(builder, x + lineWidth, my, width - lineWidth, lineWidth, color1, color1, 0, 0);
        drawSeparateGradientRect(builder, x, my, lineWidth, lineWidth, 0, color1, 0, 0);
        tessellator.draw();
        GlStateManager.disableTexture2D();
    }
    
    public static void scissor(final float x, final float y, final float x2, final float y2) {
        GL11.glEnable(3089);
        scissor((int)Math.floor(x), (int)Math.floor(y), (int)Math.ceil(x2), (int)Math.ceil(y2));
    }
    
    public static void scissor(final int x, final int y, final int x2, final int y2) {
        final ScaledResolution sc = new ScaledResolution(RenderUtil.mc);
        glScissor((float)(x * sc.getScaleFactor()), (float)((sc.getScaledHeight() - y2) * sc.getScaleFactor()), (float)((x2 - x) * sc.getScaleFactor()), (float)((y2 - y) * sc.getScaleFactor()), sc);
    }
    
    public static void endScissor() {
        GL11.glDisable(3089);
    }
    
    public static void drawSeparateGradientRect(final BufferBuilder bbIn, final float x, final float y, final float width, final float height, final int cxy, final int cx1y, final int cx1y1, final int cxy1) {
        final int cxyr = cxy >> 16 & 0xFF;
        final int cxyg = cxy >> 8 & 0xFF;
        final int cxyb = cxy & 0xFF;
        final int cxya = cxy >> 24 & 0xFF;
        final int cxy1r = cxy1 >> 16 & 0xFF;
        final int cxy1g = cxy1 >> 8 & 0xFF;
        final int cxy1b = cxy1 & 0xFF;
        final int cxy1a = cxy1 >> 24 & 0xFF;
        final int cx1y1r = cx1y1 >> 16 & 0xFF;
        final int cx1y1g = cx1y1 >> 8 & 0xFF;
        final int cx1y1b = cx1y1 & 0xFF;
        final int cx1y1a = cx1y1 >> 24 & 0xFF;
        final int cx1yr = cx1y >> 16 & 0xFF;
        final int cx1yg = cx1y >> 8 & 0xFF;
        final int cx1yb = cx1y & 0xFF;
        final int cx1ya = cx1y >> 24 & 0xFF;
        bbIn.pos((double)x, (double)y, 0.0).color(cxyr, cxyg, cxyb, cxya).endVertex();
        bbIn.pos((double)x, (double)(y + height), 0.0).color(cxy1r, cxy1g, cxy1b, cxy1a).endVertex();
        bbIn.pos((double)(x + width), (double)(y + height), 0.0).color(cx1y1r, cx1y1g, cx1y1b, cx1y1a).endVertex();
        bbIn.pos((double)(x + width), (double)y, 0.0).color(cx1yr, cx1yg, cx1yb, cx1ya).endVertex();
    }
    
    public static void drawPolygon(final double x, final double y, final float radius, final int sides, final Color color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        RenderUtil.bufferbuilder.begin(6, DefaultVertexFormats.POSITION);
        RenderUtil.bufferbuilder.pos(x, y, 0.0).endVertex();
        final double TWICE_PI = 6.283185307179586;
        for (int i = 0; i <= sides; ++i) {
            final double angle = TWICE_PI * i / sides + Math.toRadians(180.0);
            RenderUtil.bufferbuilder.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0.0).endVertex();
        }
        RenderUtil.tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawLine2d(final float x1, final float y1, final float x2, final float y2, final float width, final Color color) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GlStateManager.shadeModel(7425);
        GlStateManager.glLineWidth(width);
        buffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos((double)x1, (double)y1, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos((double)x2, (double)y2, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.glLineWidth(1.0f);
    }
    
    public static void beginRender() {
        GL11.glBlendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
    }
    
    public static void endRender() {
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }
    
    public static void colorflux(final int color) {
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
    }
    
    public static void renderCrosses(final BlockPos pos, final Color color, final float lineWidth) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - Util.mc.getRenderManager().viewerPosX, pos.getY() - Util.mc.getRenderManager().viewerPosY, pos.getZ() - Util.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Util.mc.getRenderManager().viewerPosX, pos.getY() + 1 - Util.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - Util.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(Util.mc.getRenderViewEntity()).posX, Util.mc.getRenderViewEntity().posY, Util.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            renderCrosses(bb, color);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void renderCrosses(final AxisAlignedBB bb, final Color color) {
        final int hex = color.getRGB();
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 1.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, 1.0f).endVertex();
        tessellator.draw();
    }
    
    public static void blockEspFrame(final BlockPos blockPos, final double red, final double green, final double blue) {
        final double d = blockPos.getX();
        Minecraft.getMinecraft().getRenderManager();
        final double x = d - RenderUtil.mc.getRenderManager().renderPosX;
        final double d2 = blockPos.getY();
        Minecraft.getMinecraft().getRenderManager();
        final double y = d2 - RenderUtil.mc.getRenderManager().renderPosY;
        final double d3 = blockPos.getZ();
        Minecraft.getMinecraft().getRenderManager();
        final double z = d3 - RenderUtil.mc.getRenderManager().renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(red, green, blue, 0.5);
        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void drawNametag(final Entity entity, final String[] text, final Color color, final int type) {
        final Vec3d pos = EntityUtil.getInterpolatedPos(entity, RenderUtil.mc.getRenderPartialTicks());
        drawNametag(pos.x, pos.y + entity.height, pos.z, text, color, type);
    }
    
    public static void drawNametag(final double x, final double y, final double z, final String[] text, final Color color, final int type) {
        final double dist = RenderUtil.mc.player.getDistance(x, y, z);
        double scale = 1.0;
        double offset = 0.0;
        int start = 0;
        switch (type) {
            case 0: {
                scale = dist / 20.0 * Math.pow(1.2589254, 0.1 / ((dist < 25.0) ? 0.5 : 2.0));
                scale = Math.min(Math.max(scale, 0.5), 5.0);
                offset = ((scale > 2.0) ? (scale / 2.0) : scale);
                scale /= 40.0;
                start = 10;
                break;
            }
            case 1: {
                scale = -(int)dist / 6.0;
                if (scale < 1.0) {
                    scale = 1.0;
                }
                scale *= 0.02666666666666667;
                break;
            }
            case 2: {
                scale = 0.0018 + 0.003 * dist;
                if (dist <= 8.0) {
                    scale = 0.0245;
                }
                start = -8;
                break;
            }
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(x - RenderUtil.mc.getRenderManager().viewerPosX, y + offset - RenderUtil.mc.getRenderManager().viewerPosY, z - RenderUtil.mc.getRenderManager().viewerPosZ);
        GlStateManager.rotate(-RenderUtil.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(RenderUtil.mc.getRenderManager().playerViewX, (RenderUtil.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        if (type == 2) {
            double width = 0.0;
            final Color bcolor = new Color(0, 175, 255, 100);
            for (int i = 0; i < text.length; ++i) {
                final double w = FontRendererWrapper.getStringWidth(text[i]) / 2.0f;
                if (w > width) {
                    width = w;
                }
            }
            rectangleBordered(-width - 1.0, -RenderUtil.mc.fontRenderer.FONT_HEIGHT, width + 2.0, 1.0, 1.7999999523162842, new Color(0, 4, 0, 85).getRGB(), bcolor.getRGB());
        }
        GlStateManager.enableTexture2D();
        for (int j = 0; j < text.length; ++j) {
            FontRendererWrapper.drawStringWithShadow(text[j], -FontRendererWrapper.getStringWidth(text[j]) / 2.0f, (float)(j * (RenderUtil.mc.fontRenderer.FONT_HEIGHT + 1) + start), color.getRGB());
        }
        GlStateManager.disableTexture2D();
        if (type != 2) {
            GlStateManager.popMatrix();
        }
    }
    
    public static void rectangleBordered(final double x, final double y, final double x1, final double y1, final double width, final int internalColor, final int borderColor) {
        drawRect((float)(x + width), (float)(y + width), (float)(x1 - width), (float)(y1 - width), internalColor);
        drawRect((float)(x + width), (float)y, (float)(x1 - width), (float)(y + width), borderColor);
        drawRect((float)x, (float)y, (float)(x + width), (float)y1, borderColor);
        drawRect((float)(x1 - width), (float)y, (float)x1, (float)y1, borderColor);
        drawRect((float)(x + width), (float)(y1 - width), (float)(x1 - width), (float)y1, borderColor);
    }
    
    public static void rotationHelper(final float xAngle, final float yAngle, final float zAngle) {
        GlStateManager.rotate(yAngle, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(zAngle, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(xAngle, 1.0f, 0.0f, 0.0f);
    }
    
    public static void drawRectangleCorrectly(final int x, final int y, final int w, final int h, final int color) {
        GL11.glLineWidth(1.0f);
        Gui.drawRect(x, y, x + w, y + h, color);
    }
    
    public static AxisAlignedBB interpolateAxis(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX - RenderUtil.mc.getRenderManager().viewerPosX, bb.minY - RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ - RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX - RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY - RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ - RenderUtil.mc.getRenderManager().viewerPosZ);
    }
    
    public static boolean isInViewFrustrum(final Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    
    public static boolean isInViewFrustrum(final AxisAlignedBB bb) {
        final Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        RenderUtil.frustrum.setPosition(current.posX, current.posY, current.posZ);
        return RenderUtil.frustrum.isBoundingBoxInFrustum(bb);
    }
    
    public static Vec3d to2D(final double x, final double y, final double z) {
        GL11.glGetFloat(2982, RenderUtil.modelView);
        GL11.glGetFloat(2983, RenderUtil.projection);
        GL11.glGetInteger(2978, RenderUtil.viewport);
        final boolean result = GLU.gluProject((float)x, (float)y, (float)z, RenderUtil.modelView, RenderUtil.projection, RenderUtil.viewport, RenderUtil.screenCoords);
        if (result) {
            return new Vec3d((double)RenderUtil.screenCoords.get(0), (double)(Display.getHeight() - RenderUtil.screenCoords.get(1)), (double)RenderUtil.screenCoords.get(2));
        }
        return null;
    }
    
    public static void drawTracerPointer(final float x, final float y, final float size, final float widthDiv, final float heightDiv, final boolean outline, final float outlineWidth, final int color) {
        final boolean blend = GL11.glIsEnabled(3042);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        hexColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x - size / widthDiv), (double)(y + size));
        GL11.glVertex2d((double)x, (double)(y + size / heightDiv));
        GL11.glVertex2d((double)(x + size / widthDiv), (double)(y + size));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
        if (outline) {
            GL11.glLineWidth(outlineWidth);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, alpha);
            GL11.glBegin(2);
            GL11.glVertex2d((double)x, (double)y);
            GL11.glVertex2d((double)(x - size / widthDiv), (double)(y + size));
            GL11.glVertex2d((double)x, (double)(y + size / heightDiv));
            GL11.glVertex2d((double)(x + size / widthDiv), (double)(y + size));
            GL11.glVertex2d((double)x, (double)y);
            GL11.glEnd();
        }
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glDisable(2848);
    }
    
    public static void hexColor(final int hexColor) {
        final float red = (hexColor >> 16 & 0xFF) / 255.0f;
        final float green = (hexColor >> 8 & 0xFF) / 255.0f;
        final float blue = (hexColor & 0xFF) / 255.0f;
        final float alpha = (hexColor >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void drawTexturedRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height, final int zLevel) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder BufferBuilder2 = tessellator.getBuffer();
        BufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder2.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex((double)((textureX + 0) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + 0) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex((double)((textureX + 0) * 0.00390625f), (double)((textureY + 0) * 0.00390625f)).endVertex();
        tessellator.draw();
    }
    
    public static void blockESP(final BlockPos b, final Color c, final double length, final double length2) {
        blockEsp(b, c, length, length2);
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final boolean secondC, final Color secondColor, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final boolean air) {
        if (box) {
            drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha));
        }
        if (outline) {
            drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air);
        }
    }
    
    public static void glScissor(final float x, final float y, final float x1, final float y1, final ScaledResolution sr) {
        GL11.glScissor((int)(x * sr.getScaleFactor()), (int)(RenderUtil.mc.displayHeight - y1 * sr.getScaleFactor()), (int)((x1 - x) * sr.getScaleFactor()), (int)((y1 - y) * sr.getScaleFactor()));
    }
    
    public static void drawLine(final float x, final float y, final float x1, final float y1, final float thickness, final int hex) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glLineWidth(thickness);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static Color getHealthColor(final EntityLivingBase entityLivingBase) {
        final float health = entityLivingBase.getHealth();
        final float[] fractions = { 0.0f, 0.15f, 0.55f, 0.7f, 0.9f };
        final Color[] colors = { new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN };
        final float progress = health / entityLivingBase.getMaxHealth();
        return (health >= 0.0f) ? blendColors(fractions, colors, progress).brighter() : colors[0];
    }
    
    public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colors == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
        final int[] indicies = getFractionIndicies(fractions, progress);
        final float[] range = { fractions[indicies[0]], fractions[indicies[1]] };
        final Color[] colorRange = { colors[indicies[0]], colors[indicies[1]] };
        final float max = range[1] - range[0];
        final float value = progress - range[0];
        final float weight = value / max;
        return blend(colorRange[0], colorRange[1], 1.0f - weight);
    }
    
    public static int[] getFractionIndicies(final float[] fractions, final float progress) {
        final int[] range = new int[2];
        int startPoint;
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {}
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }
    
    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float)ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        }
        else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        }
        else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        }
        else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color3 = null;
        try {
            color3 = new Color(red, green, blue);
        }
        catch (IllegalArgumentException exp) {
            NumberFormat.getNumberInstance();
        }
        return color3;
    }
    
    public static final void drawSmoothRect(final float left, final float top, final float right, final float bottom, final int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawRect(left * 2.0f - 1.0f, top * 2.0f, left * 2.0f, bottom * 2.0f - 1.0f, color);
        drawRect(left * 2.0f, top * 2.0f - 1.0f, right * 2.0f, top * 2.0f, color);
        drawRect(right * 2.0f, top * 2.0f, right * 2.0f + 1.0f, bottom * 2.0f - 1.0f, color);
        drawRect(left * 2.0f, bottom * 2.0f - 1.0f, right * 2.0f, bottom * 2.0f, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static final void drawSmoothRectdoub(final double left, final double top, final double right, final double bottom, final int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        drawRectdoub(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawRectdoub(left * 2.0 - 1.0, top * 2.0, left * 2.0, bottom * 2.0 - 1.0, color);
        drawRectdoub(left * 2.0, top * 2.0 - 1.0, right * 2.0, top * 2.0, color);
        drawRectdoub(right * 2.0, top * 2.0, right * 2.0 + 1.0, bottom * 2.0 - 1.0, color);
        drawRectdoub(left * 2.0, bottom * 2.0 - 1.0, right * 2.0, bottom * 2.0, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawBox(final BlockPos pos, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtil.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - RenderUtil.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawBlockOutline(final BlockPos pos, final Color color, final float linewidth, final boolean air) {
        final IBlockState iblockstate = RenderUtil.mc.world.getBlockState(pos);
        if ((air || iblockstate.getMaterial() != Material.AIR) && RenderUtil.mc.world.getWorldBorder().contains(pos)) {
            assert RenderUtil.mc.renderViewEntity != null;
            final Vec3d interp = EntityUtil.interpolateEntity(RenderUtil.mc.renderViewEntity, RenderUtil.mc.getRenderPartialTicks());
            drawBlockOutline(iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z), color, linewidth);
        }
    }
    
    public static void drawBlockOutline(final AxisAlignedBB bb, final Color color, final float linewidth) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtil.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - RenderUtil.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            final double dist = RenderUtil.mc.player.getDistance((double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f)) * 0.75;
            if (box) {
                RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, boxAlpha / 255.0f);
            }
            if (outline) {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            }
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawText(final BlockPos pos, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, (EntityPlayer)RenderUtil.mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(Thunderhack.textManager.getStringWidth(text) / 2.0), 0.0, 0.0);
        Thunderhack.textManager.drawStringWithShadow(text, 0.0f, 0.0f, -5592406);
        GlStateManager.popMatrix();
    }
    
    public static void drawOutlinedBlockESP(final BlockPos pos, final Color color, final float linewidth) {
        final IBlockState iblockstate = RenderUtil.mc.world.getBlockState(pos);
        final Vec3d interp = EntityUtil.interpolateEntity((Entity)RenderUtil.mc.player, RenderUtil.mc.getRenderPartialTicks());
        drawBoundingBox(iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z), linewidth, ColorUtil.toRGBA(color));
    }
    
    public static void blockEsp(final BlockPos blockPos, final Color c, final double length, final double length2) {
        final double x = blockPos.getX() - RenderUtil.mc.renderManager.renderPosX;
        final double y = blockPos.getY() - RenderUtil.mc.renderManager.renderPosY;
        final double z = blockPos.getZ() - RenderUtil.mc.renderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(c.getRed() / 255.0f), (double)(c.getGreen() / 255.0f), (double)(c.getBlue() / 255.0f), 0.25);
        drawColorBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length));
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void EspXYZ(final double x, final double y, final double z, final Color c, final double length, final double length2) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(c.getRed() / 255.0f), (double)(c.getGreen() / 255.0f), (double)(c.getBlue() / 255.0f), 0.25);
        drawColorBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + length2, y + 1.0, z + length));
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawRect2(double left, double top, double right, double bottom, final int color) {
        GlStateManager.pushMatrix();
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
        GlStateManager.popMatrix();
    }
    
    public static void drawRectdoub(final double x, final double y, final double w, final double h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(w, h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(w, y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawColorBox(final AxisAlignedBB axisalignedbb, final float red, final float green, final float blue, final float alpha) {
        final Tessellator ts = Tessellator.getInstance();
        final BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB boundingBox) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }
    
    public static void glrendermethod() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2929);
        final double viewerPosX = RenderUtil.mc.getRenderManager().viewerPosX;
        final double viewerPosY = RenderUtil.mc.getRenderManager().viewerPosY;
        final double viewerPosZ = RenderUtil.mc.getRenderManager().viewerPosZ;
        GL11.glPushMatrix();
        GL11.glTranslated(-viewerPosX, -viewerPosY, -viewerPosZ);
    }
    
    public static void glStart(final float n, final float n2, final float n3, final float n4) {
        glrendermethod();
        GL11.glColor4f(n, n2, n3, n4);
    }
    
    public static void glEnd() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static AxisAlignedBB getBoundingBox(final BlockPos blockPos) {
        return RenderUtil.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)RenderUtil.mc.world, blockPos).offset(blockPos);
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB axisAlignedBB) {
        GL11.glBegin(1);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        GL11.glVertex3d(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        GL11.glEnd();
    }
    
    public static void drawFilledBoxESPN(final BlockPos pos, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtil.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - RenderUtil.mc.getRenderManager().viewerPosZ);
        final int rgba = ColorUtil.toRGBA(color);
        drawFilledBox(bb, rgba);
    }
    
    public static void drawFilledBox(final AxisAlignedBB bb, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - RenderUtil.mc.getRenderManager().renderPosX, y - RenderUtil.mc.getRenderManager().renderPosY, z - RenderUtil.mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-RenderUtil.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(RenderUtil.mc.player.rotationPitch, (RenderUtil.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public static void drawColoredBoundingBox(final AxisAlignedBB bb, final float width, final float red, final float green, final float blue, final float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawSphere(final double x, final double y, final double z, final float size, final int slices, final int stacks) {
        final Sphere s = new Sphere();
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.2f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        s.setDrawStyle(100013);
        GL11.glTranslated(x - RenderUtil.mc.renderManager.renderPosX, y - RenderUtil.mc.renderManager.renderPosY, z - RenderUtil.mc.renderManager.renderPosZ);
        s.draw(size, slices, stacks);
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void GLPre(final float lineWidth) {
        RenderUtil.depth = GL11.glIsEnabled(2896);
        RenderUtil.texture = GL11.glIsEnabled(3042);
        RenderUtil.clean = GL11.glIsEnabled(3553);
        RenderUtil.bind = GL11.glIsEnabled(2929);
        RenderUtil.override = GL11.glIsEnabled(2848);
        GLPre(RenderUtil.depth, RenderUtil.texture, RenderUtil.clean, RenderUtil.bind, RenderUtil.override, lineWidth);
    }
    
    public static void GlPost() {
        GLPost(RenderUtil.depth, RenderUtil.texture, RenderUtil.clean, RenderUtil.bind, RenderUtil.override);
    }
    
    private static void GLPre(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override, final float lineWidth) {
        if (depth) {
            GL11.glDisable(2896);
        }
        if (!texture) {
            GL11.glEnable(3042);
        }
        GL11.glLineWidth(lineWidth);
        if (clean) {
            GL11.glDisable(3553);
        }
        if (bind) {
            GL11.glDisable(2929);
        }
        if (!override) {
            GL11.glEnable(2848);
        }
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
    }
    
    public static float[][] getBipedRotations(final ModelBiped biped) {
        final float[][] rotations = new float[5][];
        final float[] headRotation = { biped.bipedHead.rotateAngleX, biped.bipedHead.rotateAngleY, biped.bipedHead.rotateAngleZ };
        rotations[0] = headRotation;
        final float[] rightArmRotation = { biped.bipedRightArm.rotateAngleX, biped.bipedRightArm.rotateAngleY, biped.bipedRightArm.rotateAngleZ };
        rotations[1] = rightArmRotation;
        final float[] leftArmRotation = { biped.bipedLeftArm.rotateAngleX, biped.bipedLeftArm.rotateAngleY, biped.bipedLeftArm.rotateAngleZ };
        rotations[2] = leftArmRotation;
        final float[] rightLegRotation = { biped.bipedRightLeg.rotateAngleX, biped.bipedRightLeg.rotateAngleY, biped.bipedRightLeg.rotateAngleZ };
        rotations[3] = rightLegRotation;
        final float[] leftLegRotation = { biped.bipedLeftLeg.rotateAngleX, biped.bipedLeftLeg.rotateAngleY, biped.bipedLeftLeg.rotateAngleZ };
        rotations[4] = leftLegRotation;
        return rotations;
    }
    
    private static void GLPost(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override) {
        GlStateManager.depthMask(true);
        if (!override) {
            GL11.glDisable(2848);
        }
        if (bind) {
            GL11.glEnable(2929);
        }
        if (clean) {
            GL11.glEnable(3553);
        }
        if (!texture) {
            GL11.glDisable(3042);
        }
        if (depth) {
            GL11.glEnable(2896);
        }
    }
    
    public static void drawArc(final float cx, final float cy, final float r, final float start_angle, final float end_angle, final int num_segments) {
        GL11.glBegin(4);
        for (int i = (int)(num_segments / (360.0f / start_angle)) + 1; i <= num_segments / (360.0f / end_angle); ++i) {
            final double previousangle = 6.283185307179586 * (i - 1) / num_segments;
            final double angle = 6.283185307179586 * i / num_segments;
            GL11.glVertex2d((double)cx, (double)cy);
            GL11.glVertex2d(cx + Math.cos(angle) * r, cy + Math.sin(angle) * r);
            GL11.glVertex2d(cx + Math.cos(previousangle) * r, cy + Math.sin(previousangle) * r);
        }
        glEnd();
    }
    
    public static void drawArcOutline(final float cx, final float cy, final float r, final float start_angle, final float end_angle, final int num_segments, final Color colour) {
        GL11.glBegin(2);
        GL11.glColor4f(colour.getRed() / 255.0f, colour.getGreen() / 255.0f, colour.getBlue() / 255.0f, colour.getAlpha() / 255.0f);
        for (int i = (int)(num_segments / (360.0f / start_angle)) + 1; i <= num_segments / (360.0f / end_angle); ++i) {
            final double angle = 6.283185307179586 * i / num_segments;
            GL11.glVertex2d(cx + Math.cos(angle) * r, cy + Math.sin(angle) * r);
        }
        glEnd();
    }
    
    public static void drawCircleOutline(final float x, final float y, final float radius, final int start, final int end, final int segments, final Color colour) {
        drawArcOutline(x, y, radius, (float)start, (float)end, segments, colour);
    }
    
    public static void drawCircle(final float x, final float y, final float radius) {
        drawCircle(x, y, radius, 0, 360, 64);
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final int start, final int end, final int segments) {
        drawArc(x, y, radius, (float)start, (float)end, segments);
    }
    
    public static void drawOutlinedRoundedRectangle(final int x, final int y, final int width, final int height, final float radius, final float dR, final float dG, final float dB, final float dA, final float outlineWidth) {
        drawRoundedRectangle((float)x, (float)y, (float)width, (float)height, radius);
        GL11.glColor4f(dR, dG, dB, dA);
        drawRoundedRectangle(x + outlineWidth, y + outlineWidth, width - outlineWidth * 2.0f, height - outlineWidth * 2.0f, radius);
    }
    
    public static void drawRectangle(final float x, final float y, final float width, final float height) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(2);
        GL11.glVertex2d((double)width, 0.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glVertex2d(0.0, (double)height);
        GL11.glVertex2d((double)width, (double)height);
        glEnd();
    }
    
    public static void drawRectangleXY(final float x, final float y, final float width, final float height) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(2);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        glEnd();
    }
    
    public static void drawFilledRectangle(final float x, final float y, final float width, final float height) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        glEnd();
    }
    
    public static void drawRoundedRectangle(final float x, final float y, final float width, final float height, final float radius) {
        GL11.glEnable(3042);
        drawArc(x + width - radius, y + height - radius, radius, 0.0f, 90.0f, 16);
        drawArc(x + radius, y + height - radius, radius, 90.0f, 180.0f, 16);
        drawArc(x + radius, y + radius, radius, 180.0f, 270.0f, 16);
        drawArc(x + width - radius, y + radius, radius, 270.0f, 360.0f, 16);
        GL11.glBegin(4);
        GL11.glVertex2d((double)(x + width - radius), (double)y);
        GL11.glVertex2d((double)(x + radius), (double)y);
        GL11.glVertex2d((double)(x + width - radius), (double)(y + radius));
        GL11.glVertex2d((double)(x + width - radius), (double)(y + radius));
        GL11.glVertex2d((double)(x + radius), (double)y);
        GL11.glVertex2d((double)(x + radius), (double)(y + radius));
        GL11.glVertex2d((double)(x + width), (double)(y + radius));
        GL11.glVertex2d((double)x, (double)(y + radius));
        GL11.glVertex2d((double)x, (double)(y + height - radius));
        GL11.glVertex2d((double)(x + width), (double)(y + radius));
        GL11.glVertex2d((double)x, (double)(y + height - radius));
        GL11.glVertex2d((double)(x + width), (double)(y + height - radius));
        GL11.glVertex2d((double)(x + width - radius), (double)(y + height - radius));
        GL11.glVertex2d((double)(x + radius), (double)(y + height - radius));
        GL11.glVertex2d((double)(x + width - radius), (double)(y + height));
        GL11.glVertex2d((double)(x + width - radius), (double)(y + height));
        GL11.glVertex2d((double)(x + radius), (double)(y + height - radius));
        GL11.glVertex2d((double)(x + radius), (double)(y + height));
        glEnd();
    }
    
    public static void renderOne(final float lineWidth) {
        checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void renderFour(final Color color) {
        setColor(color);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    public static void setColor(final Color color) {
        GL11.glColor4d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
    }
    
    public static void setColorNovoline(final Color var0) {
        final float var = (var0.getRGB() >> 24 & 0xFF) / 255.0f;
        final float var2 = (var0.getRGB() >> 16 & 0xFF) / 255.0f;
        final float var3 = (var0.getRGB() >> 8 & 0xFF) / 255.0f;
        final float var4 = (var0.getRGB() & 0xFF) / 255.0f;
        GL11.glColor4f(var2, var3, var4, var);
    }
    
    public static void checkSetupFBO() {
        final Framebuffer fbo = RenderUtil.mc.framebuffer;
        if (fbo != null && fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }
    
    private static void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, RenderUtil.mc.displayWidth, RenderUtil.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
    }
    
    public static double interpolate(final double prevPosX, final double posX, final float partialTicks) {
        return prevPosX + (posX - prevPosX) * partialTicks;
    }
    
    public static void drawEntityBoundingBox(final Entity entity, final int c, final float partialTicks) {
        final Minecraft mc = Minecraft.getMinecraft();
        final float red = (c >> 16 & 0xFF) / 255.0f;
        final float green = (c >> 8 & 0xFF) / 255.0f;
        final float blue = (c & 0xFF) / 255.0f;
        final float alpha = (c >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        beginRender();
        GlStateManager.glLineWidth(2.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        if (entity != mc.player) {
            final Vec3d pos = NordTessellator.getInterpolatedPos(entity, 1.0f);
            final AxisAlignedBB entityBox = entity.getEntityBoundingBox();
            final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.minX - entity.posX + pos.x, entityBox.minY - entity.posY + pos.y, entityBox.minZ - entity.posZ + pos.z, entityBox.maxX - entity.posX + pos.x, entityBox.maxY - entity.posY + pos.y, entityBox.maxZ - entity.posZ + pos.z);
            drawBoundingBox(axisAlignedBB.grow(0.0020000000949949026), red, green, blue, alpha);
        }
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        endRender();
        GlStateManager.popMatrix();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB box, final float red, final float green, final float blue, final float alpha) {
        final BufferBuilder buffer = RenderUtil.tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        RenderUtil.tessellator.draw();
    }
    
    static {
        frustrum = new Frustum();
        screenCoords = BufferUtils.createFloatBuffer(3);
        viewport = BufferUtils.createIntBuffer(16);
        modelView = BufferUtils.createFloatBuffer(16);
        projection = BufferUtils.createFloatBuffer(16);
        RenderUtil.itemRender = RenderUtil.mc.getItemRenderer().itemRenderer;
        RenderUtil.camera = (ICamera)new Frustum();
        RenderUtil.depth = GL11.glIsEnabled(2896);
        RenderUtil.texture = GL11.glIsEnabled(3042);
        RenderUtil.clean = GL11.glIsEnabled(3553);
        RenderUtil.bind = GL11.glIsEnabled(2929);
        RenderUtil.override = GL11.glIsEnabled(2848);
        RenderUtil.tessellator = Tessellator.getInstance();
        RenderUtil.bufferbuilder = RenderUtil.tessellator.getBuffer();
        RenderUtil.itemRender = RenderUtil.mc.getRenderItem();
        RenderUtil.camera = (ICamera)new Frustum();
        RenderUtil.depth = GL11.glIsEnabled(2896);
        RenderUtil.texture = GL11.glIsEnabled(3042);
        RenderUtil.clean = GL11.glIsEnabled(3553);
        RenderUtil.bind = GL11.glIsEnabled(2929);
        RenderUtil.override = GL11.glIsEnabled(2848);
    }
    
    public static final class GeometryMasks
    {
        public static final HashMap FACEMAP;
        
        static {
            (FACEMAP = new HashMap()).put(EnumFacing.DOWN, 1);
            GeometryMasks.FACEMAP.put(EnumFacing.WEST, 16);
            GeometryMasks.FACEMAP.put(EnumFacing.NORTH, 4);
            GeometryMasks.FACEMAP.put(EnumFacing.SOUTH, 8);
            GeometryMasks.FACEMAP.put(EnumFacing.EAST, 32);
            GeometryMasks.FACEMAP.put(EnumFacing.UP, 2);
        }
        
        public static final class Quad
        {
            public static final int DOWN = 1;
            public static final int UP = 2;
            public static final int NORTH = 4;
            public static final int SOUTH = 8;
            public static final int WEST = 16;
            public static final int EAST = 32;
            public static final int ALL = 63;
        }
        
        public static final class Line
        {
            public static final int DOWN_WEST = 17;
            public static final int UP_WEST = 18;
            public static final int DOWN_EAST = 33;
            public static final int UP_EAST = 34;
            public static final int DOWN_NORTH = 5;
            public static final int UP_NORTH = 6;
            public static final int DOWN_SOUTH = 9;
            public static final int UP_SOUTH = 10;
            public static final int NORTH_WEST = 20;
            public static final int NORTH_EAST = 36;
            public static final int SOUTH_WEST = 24;
            public static final int SOUTH_EAST = 40;
            public static final int ALL = 63;
        }
    }
}
