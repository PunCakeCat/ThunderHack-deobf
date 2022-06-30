//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.util.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderItem.class })
public class MixinRenderItem
{
    private static final ResourceLocation RESOURCE;
    private static final ResourceLocation RESOURCE2;
    private static final ResourceLocation RESOURCE3;
    
    @Shadow
    private void renderModel(final IBakedModel model, final int color, final ItemStack stack) {
    }
    
    @Redirect(method = { "renderEffect" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
    public void bindHook(final TextureManager textureManager, final ResourceLocation resource) {
        if (CustomEnchants.getInstance().isEnabled()) {
            textureManager.bindTexture(MixinRenderItem.RESOURCE);
        }
        else {
            textureManager.bindTexture(resource);
        }
    }
    
    @Inject(method = { "renderItemModel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V", shift = At.Shift.BEFORE) })
    private void renderItemModel(final ItemStack stack, final IBakedModel bakedModel, final ItemCameraTransforms.TransformType transform, final boolean leftHanded, final CallbackInfo ci) {
        final RenderItemEvent event = new RenderItemEvent(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (ViewModel.getInstance().isEnabled()) {
            if (!leftHanded) {
                GlStateManager.scale(event.getMainHandScaleX(), event.getMainHandScaleY(), event.getMainHandScaleZ());
            }
            else {
                GlStateManager.scale(event.getOffHandScaleX(), event.getOffHandScaleY(), event.getOffHandScaleZ());
            }
        }
    }
    
    static {
        RESOURCE = new ResourceLocation("textures/rainbow.png");
        RESOURCE2 = new ResourceLocation("textures/rus.png");
        RESOURCE3 = new ResourceLocation("textures/jew.png");
    }
}
