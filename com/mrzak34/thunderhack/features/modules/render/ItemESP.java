//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.util.math.*;
import javax.vecmath.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class ItemESP extends Module
{
    private final Setting<Boolean> entityName;
    private final Setting<Boolean> fullBox;
    public Setting<Float> scalefactor;
    private final Setting<ColorSetting> cc;
    private final int black;
    private Setting<mode> Mode;
    private Setting<mode2> Mode2;
    
    public ItemESP() {
        super("ItemESP", "ItemESP", Module.Category.RENDER, false, false, false);
        this.entityName = (Setting<Boolean>)this.register(new Setting("Name", (T)true));
        this.fullBox = (Setting<Boolean>)this.register(new Setting("Full Box", (T)true));
        this.scalefactor = (Setting<Float>)this.register(new Setting("Raytrace", (T)2.0f, (T)0.1f, (T)4.0f, "Wall Range."));
        this.cc = (Setting<ColorSetting>)this.register(new Setting("CustomColor", (T)new ColorSetting(-2013200640)));
        this.black = Color.BLACK.getRGB();
        this.Mode = (Setting<mode>)this.register(new Setting("Render Mode", (T)mode.render2D));
        this.Mode2 = (Setting<mode2>)this.register(new Setting("Color Mode", (T)mode2.Rainbow));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        for (final Entity item : ItemESP.mc.world.loadedEntityList) {
            if (item instanceof EntityItem) {
                int color = 0;
                if (this.Mode2.getValue() == mode2.Astolfo) {
                    color = PaletteHelper.astolfo(false, (int)item.height).getRGB();
                }
                if (this.Mode2.getValue() == mode2.Rainbow) {
                    color = PaletteHelper.rainbow(300, 1.0f, 1.0f).getRGB();
                }
                if (this.Mode.getValue() != mode.render3D) {
                    continue;
                }
                GlStateManager.pushMatrix();
                RenderHelper.drawEntityBox(item, new Color(color), this.fullBox.getValue(), ((boolean)this.fullBox.getValue()) ? 0.15f : 0.9f);
                GlStateManager.popMatrix();
            }
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        final float partialTicks = ItemESP.mc.timer.renderPartialTicks;
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
        for (final Entity entity : ItemESP.mc.world.loadedEntityList) {
            if (this.isValid(entity) && RenderHelper.isInViewFrustum(entity)) {
                final EntityItem entityItem = (EntityItem)entity;
                final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ItemESP.mc.getRenderPartialTicks();
                final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ItemESP.mc.getRenderPartialTicks();
                final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ItemESP.mc.getRenderPartialTicks();
                final AxisAlignedBB axisAlignedBB2 = entity.getEntityBoundingBox();
                final AxisAlignedBB axisAlignedBB3 = new AxisAlignedBB(axisAlignedBB2.minX - entity.posX + x - 0.05, axisAlignedBB2.minY - entity.posY + y, axisAlignedBB2.minZ - entity.posZ + z - 0.05, axisAlignedBB2.maxX - entity.posX + x + 0.05, axisAlignedBB2.maxY - entity.posY + y + 0.15, axisAlignedBB2.maxZ - entity.posZ + z + 0.05);
                final Vector3d[] vectors = { new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ) };
                ItemESP.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;
                for (Vector3d vector : vectors) {
                    vector = this.project2D(scaleFactor, vector.x - ItemESP.mc.getRenderManager().renderPosX, vector.y - ItemESP.mc.getRenderManager().renderPosY, vector.z - ItemESP.mc.getRenderManager().renderPosZ);
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
                ItemESP.mc.entityRenderer.setupOverlayRendering();
                final double posX = position.x;
                final double posY = position.y;
                final double endPosX = position.z;
                final double endPosY = position.w;
                if (this.Mode.getValue() == mode.render2D) {
                    RectHelper.drawRect(posX - 1.0, posY, posX + 0.5, endPosY + 0.5, this.black);
                    RectHelper.drawRect(posX - 1.0, posY - 0.5, endPosX + 0.5, posY + 0.5 + 0.5, this.black);
                    RectHelper.drawRect(endPosX - 0.5 - 0.5, posY, endPosX + 0.5, endPosY + 0.5, this.black);
                    RectHelper.drawRect(posX - 1.0, endPosY - 0.5 - 0.5, endPosX + 0.5, endPosY + 0.5, this.black);
                    RectHelper.drawRect(posX - 0.5, posY, posX + 0.5 - 0.5, endPosY, color);
                    RectHelper.drawRect(posX, endPosY - 0.5, endPosX, endPosY, color);
                    RectHelper.drawRect(posX - 0.5, posY, endPosX, posY + 0.5, color);
                    RectHelper.drawRect(endPosX - 0.5, posY, endPosX, endPosY, color);
                }
                final float diff = (float)(endPosX - posX) / 2.0f;
                final float textWidth = Util.fr.getStringWidth(entityItem.getItem().getDisplayName()) * scale;
                final float tagX = (float)((posX + diff - textWidth / 2.0f) * scale);
                if (!this.entityName.getValue()) {
                    continue;
                }
                Util.fr.drawStringWithShadow(entityItem.getItem().getDisplayName(), tagX, (float)posY - 10.0f, -1);
            }
        }
        GL11.glEnable(2929);
        ItemESP.mc.entityRenderer.setupOverlayRendering();
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
        return entity instanceof EntityItem;
    }
    
    public enum mode
    {
        render2D, 
        render3D;
    }
    
    public enum mode2
    {
        Custom, 
        Rainbow, 
        Astolfo;
    }
}
