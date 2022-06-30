//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.util.*;
import net.minecraft.client.renderer.color.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import java.lang.reflect.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;

public class CustomRenderItem extends RenderItem
{
    private static final ResourceLocation RES_ITEM_GLINT;
    private Method renderModel;
    private final TextureManager textureManagerSave;
    
    public CustomRenderItem(final TextureManager textureManager, final ModelManager modelManager, final ItemColors itemColors) {
        super(textureManager, modelManager, itemColors);
        this.textureManagerSave = textureManager;
        final Class<RenderItem> renderItem = RenderItem.class;
        try {
            this.renderModel = renderItem.getDeclaredMethod("renderModel", IBakedModel.class, Integer.TYPE, ItemStack.class);
        }
        catch (NoSuchMethodException e) {
            try {
                this.renderModel = renderItem.getDeclaredMethod("renderModel", IBakedModel.class, Integer.TYPE, ItemStack.class);
            }
            catch (NoSuchMethodException e2) {
                throw new RuntimeException("th Error: no method renderModel in class RenderItem");
            }
        }
        this.renderModel.setAccessible(true);
    }
    
    public void renderItem(final ItemStack stack, final IBakedModel model) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            if (model.isBuiltInRenderer()) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableRescaleNormal();
                stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
            }
            else {
                try {
                    this.renderModel.invoke(this, model, new Integer(-1), stack);
                }
                catch (IllegalAccessException ex) {}
                catch (InvocationTargetException ex2) {}
                final NBTTagCompound tag = stack.getTagCompound();
                if ((stack.isItemStackDamageable() && stack.getItemDamage() > stack.getMaxDamage()) || (tag != null && (tag instanceof SpecialTagCompound || tag.getBoolean("Unbreakable")))) {
                    this.renderEffect(model, -260859);
                }
                else if (stack.hasEffect()) {
                    this.renderEffect(model, -8372020);
                }
            }
            GlStateManager.popMatrix();
        }
    }
    
    private void renderEffect(final IBakedModel model, final int color) {
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        this.textureManagerSave.bindTexture(CustomRenderItem.RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        final float f = Minecraft.getSystemTime() % 3000L / 3000.0f / 8.0f;
        GlStateManager.translate(f, 0.0f, 0.0f);
        GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f);
        try {
            this.renderModel.invoke(this, model, new Integer(color), ItemStack.EMPTY);
        }
        catch (IllegalAccessException ex) {}
        catch (InvocationTargetException ex2) {}
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        final float f2 = Minecraft.getSystemTime() % 4873L / 4873.0f / 8.0f;
        GlStateManager.translate(-f2, 0.0f, 0.0f);
        GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f);
        try {
            this.renderModel.invoke(this, model, new Integer(color), ItemStack.EMPTY);
        }
        catch (IllegalAccessException ex3) {}
        catch (InvocationTargetException ex4) {}
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        this.textureManagerSave.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }
    
    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/glint.png");
    }
}
