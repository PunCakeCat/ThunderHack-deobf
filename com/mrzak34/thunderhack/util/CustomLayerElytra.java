//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class CustomLayerElytra implements LayerRenderer<EntityLivingBase>
{
    private static final ResourceLocation TEXTURE_ELYTRA;
    protected RenderLivingBase<?> renderPlayer;
    private final ModelElytra modelElytra;
    
    public CustomLayerElytra(final RenderLivingBase<?> render) {
        this.modelElytra = new ModelElytra();
        this.renderPlayer = render;
    }
    
    public void doRenderLayer(final EntityLivingBase entity, final float f1, final float f2, final float f3, final float f4, final float f5, final float f6, final float f7) {
        final ItemStack stack = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (stack.getItem() != Items.ELYTRA) {
            return;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        if (entity instanceof AbstractClientPlayer) {
            final AbstractClientPlayer player = (AbstractClientPlayer)entity;
            if (player.isPlayerInfoSet() && player.getLocationElytra() != null) {
                this.renderPlayer.bindTexture(player.getLocationElytra());
            }
            else if (player.hasPlayerInfo() && player.getLocationCape() != null && player.isWearing(EnumPlayerModelParts.CAPE)) {
                this.renderPlayer.bindTexture(player.getLocationCape());
            }
            else {
                this.renderPlayer.bindTexture(CustomLayerElytra.TEXTURE_ELYTRA);
            }
        }
        else {
            this.renderPlayer.bindTexture(CustomLayerElytra.TEXTURE_ELYTRA);
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 0.0f, 0.125f);
        this.modelElytra.setRotationAngles(f1, f2, f4, f5, f6, f7, (Entity)entity);
        this.modelElytra.render((Entity)entity, f1, f2, f4, f5, f6, f7);
        final NBTTagCompound tag = stack.getTagCompound();
        if (stack.getItemDamage() > stack.getMaxDamage() || (tag != null && tag instanceof SpecialTagCompound)) {
            CustomLayerBipedArmor.renderEnchantedGlint((RenderLivingBase)this.renderPlayer, entity, (ModelBase)this.modelElytra, f1, f2, f3, f4, f5, f6, f7);
        }
        else if (stack.isItemEnchanted()) {
            LayerArmorBase.renderEnchantedGlint((RenderLivingBase)this.renderPlayer, entity, (ModelBase)this.modelElytra, f1, f2, f3, f4, f5, f6, f7);
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    }
}
