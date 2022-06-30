//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import javax.vecmath.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class ImageESP extends Module
{
    public Setting<Float> scalefactor;
    private final int black;
    private Setting<mode2> Mode2;
    public Setting<Boolean> wtf;
    public Setting<Float> scalefactor1;
    public Setting<Float> scalefactor2;
    private final Setting<ColorSetting> cc;
    private Setting<mode3> Mode3;
    
    public ImageESP() {
        super("ImageESP", "ImageESP ImageESP.", Module.Category.RENDER, true, false, false);
        this.scalefactor = (Setting<Float>)this.register(new Setting("Raytrace", (T)2.0f, (T)0.1f, (T)4.0f, "Wall Range."));
        this.black = Color.BLACK.getRGB();
        this.Mode2 = (Setting<mode2>)this.register(new Setting("Color Mode", (T)mode2.Rainbow));
        this.wtf = (Setting<Boolean>)this.register(new Setting("Not done", (T)false));
        this.scalefactor1 = (Setting<Float>)this.register(new Setting("X", (T)0.0f, (T)(-6.0f), (T)6.0f, "Wall Range."));
        this.scalefactor2 = (Setting<Float>)this.register(new Setting("Y", (T)0.0f, (T)(-6.0f), (T)6.0f, "Wall Range."));
        this.cc = (Setting<ColorSetting>)this.register(new Setting("CustomColor", (T)new ColorSetting(-2013200640)));
        this.Mode3 = (Setting<mode3>)this.register(new Setting("Color Mode", (T)mode3.CAT));
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        final float partialTicks = ImageESP.mc.timer.renderPartialTicks;
        final Float scaleFactor = this.scalefactor.getValue();
        final double scaling = scaleFactor / Math.pow(scaleFactor, 2.0);
        GlStateManager.scale(scaling, scaling, scaling);
        final Color c = this.cc.getValue().getColorObject();
        int color = 0;
        if (this.Mode2.getValue() == mode2.Custom) {
            color = c.getRGB();
        }
        if (this.Mode2.getValue() == mode2.Astolfo) {
            color = PaletteHelper.astolfo(false, 1).getRGB();
        }
        if (this.Mode2.getValue() == mode2.Rainbow) {
            color = PaletteHelper.rainbow(300, 1.0f, 1.0f).getRGB();
        }
        final float scale = 1.0f;
        for (final Entity entity : ImageESP.mc.world.loadedEntityList) {
            if (this.isValid(entity) && RenderHelper.isInViewFrustum(entity)) {
                final EntityPlayer entityPlayer = (EntityPlayer)entity;
                if (entityPlayer == ImageESP.mc.player) {
                    continue;
                }
                final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ImageESP.mc.getRenderPartialTicks();
                final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ImageESP.mc.getRenderPartialTicks();
                final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ImageESP.mc.getRenderPartialTicks();
                final AxisAlignedBB axisAlignedBB2 = entity.getEntityBoundingBox();
                final AxisAlignedBB axisAlignedBB3 = new AxisAlignedBB(axisAlignedBB2.minX - entity.posX + x - 0.05, axisAlignedBB2.minY - entity.posY + y, axisAlignedBB2.minZ - entity.posZ + z - 0.05, axisAlignedBB2.maxX - entity.posX + x + 0.05, axisAlignedBB2.maxY - entity.posY + y + 0.15, axisAlignedBB2.maxZ - entity.posZ + z + 0.05);
                final Vector3d[] vectors = { new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ) };
                ImageESP.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;
                for (Vector3d vector : vectors) {
                    vector = this.project2D(scaleFactor, vector.x - ImageESP.mc.getRenderManager().renderPosX, vector.y - ImageESP.mc.getRenderManager().renderPosY, vector.z - ImageESP.mc.getRenderManager().renderPosZ);
                    if (vector != null && vector.z > 0.0 && vector.z < 1.0) {
                        if (position == null) {
                            position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                        }
                        position.x = Math.min(vector.x, position.x);
                        position.y = Math.min(vector.y, position.y);
                        position.z = Math.max(vector.x, position.z);
                        position.w = Math.max(vector.y, position.w);
                    }
                }
                if (position == null) {
                    continue;
                }
                ImageESP.mc.entityRenderer.setupOverlayRendering();
                final double posX = position.x;
                final double posY = position.y;
                final double endPosX = position.z;
                final double endPosY = position.w;
                if (this.wtf.getValue()) {
                    RectHelper.drawRect(posX - 1.0, posY, posX + 0.5, endPosY + 0.5, this.black);
                    RectHelper.drawRect(posX - 1.0, posY - 0.5, endPosX + 0.5, posY + 0.5 + 0.5, this.black);
                    RectHelper.drawRect(endPosX - 0.5 - 0.5, posY, endPosX + 0.5, endPosY + 0.5, this.black);
                    RectHelper.drawRect(posX - 1.0, endPosY - 0.5 - 0.5, endPosX + 0.5, endPosY + 0.5, this.black);
                    RectHelper.drawRect(posX - 0.5, posY, posX + 0.5 - 0.5, endPosY, color);
                    RectHelper.drawRect(posX, endPosY - 0.5, endPosX, endPosY, color);
                    RectHelper.drawRect(posX - 0.5, posY, endPosX, posY + 0.5, color);
                    RectHelper.drawRect(endPosX - 0.5, posY, endPosX, endPosY, color);
                }
                RectHelper.drawRect(posX, posY, posX, posY, color);
                if (this.Mode3.getValue() == mode3.CAT) {
                    final Minecraft mc = Util.mc;
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/image9.png"));
                }
                if (this.Mode3.getValue() == mode3.MrZak) {
                    final Minecraft mc2 = Util.mc;
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/image10.png"));
                }
                ModuleButton.drawCompleteImage((float)posX + this.scalefactor1.getValue(), (float)posY + this.scalefactor2.getValue(), (int)((int)endPosX - posX), (int)((int)endPosY - posY));
            }
        }
        GL11.glEnable(2929);
        ImageESP.mc.entityRenderer.setupOverlayRendering();
    }
    
    private Vector3d project2D(final Float scaleFactor, final double x, final double y, final double z) {
        final float xPos = (float)x;
        final float yPos = (float)y;
        final float zPos = (float)z;
        final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector)) {
            return new Vector3d((double)(vector.get(0) / scaleFactor), (double)((Display.getHeight() - vector.get(1)) / scaleFactor), (double)vector.get(2));
        }
        return null;
    }
    
    private boolean isValid(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public enum mode2
    {
        Custom, 
        Rainbow, 
        Astolfo;
    }
    
    public enum mode3
    {
        CAT, 
        MrZak;
    }
}
