//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.client.*;

public class CustomLayerBipedArmor extends LayerBipedArmor
{
    public static final float enchant_red = 0.95f;
    public static final float enchant_blue = 0.05f;
    public static final float enchant_green = 0.05f;
    private final RenderLivingBase<?> renderer_save;
    
    public CustomLayerBipedArmor(final RenderLivingBase<?> entity_renderer) {
        super((RenderLivingBase)entity_renderer);
        this.renderer_save = entity_renderer;
    }
    
    public void doRenderLayer(final EntityLivingBase entity, final float f1, final float f2, final float f3, final float f4, final float f5, final float f6, final float f7) {
        this.renderArmorLayer(entity, f1, f2, f3, f4, f5, f6, f7, EntityEquipmentSlot.CHEST);
        this.renderArmorLayer(entity, f1, f2, f3, f4, f5, f6, f7, EntityEquipmentSlot.LEGS);
        this.renderArmorLayer(entity, f1, f2, f3, f4, f5, f6, f7, EntityEquipmentSlot.FEET);
        this.renderArmorLayer(entity, f1, f2, f3, f4, f5, f6, f7, EntityEquipmentSlot.HEAD);
    }
    
    private void renderArmorLayer(final EntityLivingBase entity, final float fp1, final float fp2, final float fp3, final float fp4, final float fp5, final float fp6, final float fp7, final EntityEquipmentSlot slot) {
        final ItemStack itemstack = entity.getItemStackFromSlot(slot);
        if (itemstack.getItem() instanceof ItemArmor) {
            final ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
            if (itemarmor.getEquipmentSlot() == slot) {
                ModelBiped model = (ModelBiped)this.getModelFromSlot(slot);
                model = this.getArmorModelHook(entity, itemstack, slot, model);
                model.setModelAttributes(this.renderer_save.getMainModel());
                model.setLivingAnimations(entity, fp1, fp2, fp3);
                this.setModelSlotVisible(model, slot);
                this.renderer_save.bindTexture(this.getArmorResource((Entity)entity, itemstack, slot, (String)null));
                if (itemarmor.hasOverlay(itemstack)) {
                    final int i = itemarmor.getColor(itemstack);
                    final float f = (i >> 16 & 0xFF) / 255.0f;
                    final float f2 = (i >> 8 & 0xFF) / 255.0f;
                    final float f3 = (i & 0xFF) / 255.0f;
                    GlStateManager.color(1.0f * f, 1.0f * f2, 1.0f * f3, 1.0f);
                    model.render((Entity)entity, fp1, fp2, fp4, fp5, fp6, fp7);
                    this.renderer_save.bindTexture(this.getArmorResource((Entity)entity, itemstack, slot, "overlay"));
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                model.render((Entity)entity, fp1, fp2, fp4, fp5, fp6, fp7);
                final NBTTagCompound tag = itemstack.getTagCompound();
                if ((itemstack.isItemStackDamageable() && itemstack.getItemDamage() > itemstack.getMaxDamage()) || (tag != null && (tag instanceof SpecialTagCompound || tag.getBoolean("Unbreakable")))) {
                    renderEnchantedGlint(this.renderer_save, entity, (ModelBase)model, fp1, fp2, fp3, fp4, fp5, fp6, fp7);
                }
                else if (itemstack.hasEffect()) {
                    LayerArmorBase.renderEnchantedGlint((RenderLivingBase)this.renderer_save, entity, (ModelBase)model, fp1, fp2, fp3, fp4, fp5, fp6, fp7);
                }
            }
        }
    }
    
    public static void renderEnchantedGlint(final RenderLivingBase<?> render, final EntityLivingBase entity, final ModelBase model, final float fp1, final float fp2, final float fp3, final float fp4, final float fp5, final float fp6, final float fp7) {
        final float f = entity.ticksExisted + fp3;
        render.bindTexture(LayerArmorBase.ENCHANTED_ITEM_GLINT_RES);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
            GlStateManager.color(0.95f, 0.05f, 0.05f, 1.0f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.scale(0.33333334f, 0.33333334f, 0.33333334f);
            GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.0f, f * (0.001f + i * 0.003f) * 20.0f, 0.0f);
            GlStateManager.matrixMode(5888);
            model.render((Entity)entity, fp1, fp2, fp4, fp5, fp6, fp7);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        }
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
    }
}
