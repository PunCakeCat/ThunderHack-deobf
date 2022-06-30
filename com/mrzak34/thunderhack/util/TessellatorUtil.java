//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class TessellatorUtil
{
    private static final Minecraft mc;
    
    public static void drawBoundingBox(final BlockPos bp, final double height, final float width, final Color color) {
        drawBoundingBox(getBoundingBox(bp, 1.0, height, 1.0), width, color, color.getAlpha());
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final double width, final Color color) {
        drawBoundingBox(bb, width, color, color.getAlpha());
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final double width, final Color color, final int alpha) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.glLineWidth((float)width);
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.minX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.maxZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.maxZ, color, alpha, bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        colorVertex(bb.maxX, bb.minY, bb.minZ, color, color.getAlpha(), bufferbuilder);
        colorVertex(bb.maxX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        colorVertex(bb.minX, bb.maxY, bb.minZ, color, alpha, bufferbuilder);
        tessellator.draw();
    }
    
    public static void drawBoundingBoxWithSides(final BlockPos blockPos, final int width, final Color color, final int sides) {
        drawBoundingBoxWithSides(getBoundingBox(blockPos, 1.0, 1.0, 1.0), width, color, color.getAlpha(), sides);
    }
    
    public static void drawBoundingBoxWithSides(final BlockPos blockPos, final int width, final Color color, final int alpha, final int sides) {
        drawBoundingBoxWithSides(getBoundingBox(blockPos, 1.0, 1.0, 1.0), width, color, alpha, sides);
    }
    
    public static void drawBoundingBoxWithSides(final AxisAlignedBB axisAlignedBB, final int width, final Color color, final int sides) {
        drawBoundingBoxWithSides(axisAlignedBB, width, color, color.getAlpha(), sides);
    }
    
    public static void drawBoundingBoxWithSides(final AxisAlignedBB axisAlignedBB, final int width, final Color color, final int alpha, final int sides) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.glLineWidth((float)width);
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        doVerticies(axisAlignedBB, color, alpha, bufferbuilder, sides, true);
        tessellator.draw();
    }
    
    public static void drawBox(final BlockPos blockPos, final double height, final Color color, final int sides) {
        drawBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0, height, 1.0, color, color.getAlpha(), sides);
    }
    
    public static void drawBox(final AxisAlignedBB bb, final Color color) {
        drawBox(bb, true, 1.0, color, color.getAlpha(), 63);
    }
    
    public static void drawBox(final AxisAlignedBB bb, final double height, final Color color, final int sides) {
        drawBox(bb, false, height, color, color.getAlpha(), sides);
    }
    
    public static void drawBox(final AxisAlignedBB bb, final boolean check, final double height, final Color color, final int sides) {
        drawBox(bb, check, height, color, color.getAlpha(), sides);
    }
    
    public static void drawBox(final AxisAlignedBB bb, final boolean check, final double height, final Color color, final int alpha, final int sides) {
        if (check) {
            drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, bb.maxY - bb.minY, bb.maxZ - bb.minZ, color, alpha, sides);
        }
        else {
            drawBox(bb.minX, bb.minY, bb.minZ, bb.maxX - bb.minX, height, bb.maxZ - bb.minZ, color, alpha, sides);
        }
    }
    
    public static void drawBox(final double x, final double y, final double z, final double w, final double h, final double d, final Color color, final int alpha, final int sides) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        doVerticies(new AxisAlignedBB(x, y, z, x + w, y + h, z + d), color, alpha, bufferbuilder, sides, false);
        tessellator.draw();
    }
    
    public static void drawLine(final double posx, final double posy, final double posz, final double posx2, final double posy2, final double posz2, final Color color) {
        drawLine(posx, posy, posz, posx2, posy2, posz2, color, 1.0f);
    }
    
    public static void drawLine(final double posx, final double posy, final double posz, final double posx2, final double posy2, final double posz2, final Color color, final float width) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.glLineWidth(width);
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION);
        vertex(posx, posy, posz, bufferbuilder);
        vertex(posx2, posy2, posz2, bufferbuilder);
        tessellator.draw();
    }
    
    public static void drawDirection(final Points square, final Color color, final float width) {
        for (int i = 0; i < 4; ++i) {
            drawLine(square.getPoint(i)[0], square.yMin, square.getPoint(i)[1], square.getPoint((i + 1) % 4)[0], square.yMin, square.getPoint((i + 1) % 4)[1], color, width);
        }
        for (int i = 0; i < 4; ++i) {
            drawLine(square.getPoint(i)[0], square.yMax, square.getPoint(i)[1], square.getPoint((i + 1) % 4)[0], square.yMax, square.getPoint((i + 1) % 4)[1], color, width);
        }
        for (int i = 0; i < 4; ++i) {
            drawLine(square.getPoint(i)[0], square.yMin, square.getPoint(i)[1], square.getPoint(i)[0], square.yMax, square.getPoint(i)[1], color, width);
        }
    }
    
    private static void vertex(final double x, final double y, final double z, final BufferBuilder bufferbuilder) {
        bufferbuilder.pos(x - TessellatorUtil.mc.getRenderManager().viewerPosX, y - TessellatorUtil.mc.getRenderManager().viewerPosY, z - TessellatorUtil.mc.getRenderManager().viewerPosZ).endVertex();
    }
    
    private static void colorVertex(final double x, final double y, final double z, final Color color, final int alpha, final BufferBuilder bufferbuilder) {
        bufferbuilder.pos(x - TessellatorUtil.mc.getRenderManager().viewerPosX, y - TessellatorUtil.mc.getRenderManager().viewerPosY, z - TessellatorUtil.mc.getRenderManager().viewerPosZ).color(color.getRed(), color.getGreen(), color.getBlue(), alpha).endVertex();
    }
    
    private static AxisAlignedBB getBoundingBox(final BlockPos bp, final double width, final double height, final double depth) {
        final double x = bp.getX();
        final double y = bp.getY();
        final double z = bp.getZ();
        return new AxisAlignedBB(x, y, z, x + width, y + height, z + depth);
    }
    
    private static void doVerticies(final AxisAlignedBB axisAlignedBB, final Color color, final int alpha, final BufferBuilder bufferbuilder, final int sides, final boolean five) {
        if ((sides & 0x20) != 0x0) {
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
            if (five) {
                colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            }
        }
        if ((sides & 0x10) != 0x0) {
            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
            if (five) {
                colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            }
        }
        if ((sides & 0x4) != 0x0) {
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
            if (five) {
                colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            }
        }
        if ((sides & 0x8) != 0x0) {
            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
            if (five) {
                colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            }
        }
        if ((sides & 0x2) != 0x0) {
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, color, alpha, bufferbuilder);
            if (five) {
                colorVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, color, alpha, bufferbuilder);
            }
        }
        if ((sides & 0x1) != 0x0) {
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, color, color.getAlpha(), bufferbuilder);
            colorVertex(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            if (five) {
                colorVertex(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, color, color.getAlpha(), bufferbuilder);
            }
        }
    }
    
    public static void prepare() {
        GL11.glPushAttrib(1048575);
        GL11.glHint(3154, 4354);
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.shadeModel(7425);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GL11.glEnable(2848);
        GL11.glEnable(34383);
    }
    
    public static void release() {
        GL11.glDisable(34383);
        GL11.glDisable(2848);
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GL11.glHint(3154, 4352);
        GL11.glPopAttrib();
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    private static class Points
    {
        double[][] point;
        private int count;
        private final double xCenter;
        private final double zCenter;
        public final double yMin;
        public final double yMax;
        private final float rotation;
        
        public Points(final double yMin, final double yMax, final double xCenter, final double zCenter, final float rotation) {
            this.point = new double[10][2];
            this.count = 0;
            this.yMin = yMin;
            this.yMax = yMax;
            this.xCenter = xCenter;
            this.zCenter = zCenter;
            this.rotation = rotation;
        }
        
        public void addPoints(double x, double z) {
            x -= this.xCenter;
            z -= this.zCenter;
            double rotateX = x * Math.cos(this.rotation) - z * Math.sin(this.rotation);
            double rotateZ = x * Math.sin(this.rotation) + z * Math.cos(this.rotation);
            rotateX += this.xCenter;
            rotateZ += this.zCenter;
            this.point[this.count++] = new double[] { rotateX, rotateZ };
        }
        
        public double[] getPoint(final int index) {
            return this.point[index];
        }
    }
}
