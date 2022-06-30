//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.features.modules.player.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.command.commands.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ RenderPlayer.class })
public class MixinRenderPlayer
{
    private float renderPitch;
    private float renderYaw;
    private float renderHeadYaw;
    private float prevRenderHeadYaw;
    private float lastRenderHeadYaw;
    private float prevRenderPitch;
    private float lastRenderPitch;
    
    public MixinRenderPlayer() {
        this.lastRenderHeadYaw = 0.0f;
        this.lastRenderPitch = 0.0f;
    }
    
    @Inject(method = { "renderEntityName" }, at = { @At("HEAD") }, cancellable = true)
    public void renderEntityNameHook(final AbstractClientPlayer entityIn, final double x, final double y, final double z, final String name, final double distanceSq, final CallbackInfo info) {
        if (NameTags.getInstance().isOn()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "doRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z"))
    private boolean isUserRedirect(final AbstractClientPlayer abstractClientPlayer) {
        final Minecraft mc = Minecraft.getMinecraft();
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return abstractClientPlayer.isUser() && abstractClientPlayer == mc.getRenderViewEntity();
        }
        return abstractClientPlayer.isUser();
    }
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    private void rotateBegin(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (((PacketRender)Thunderhack.moduleManager.getModuleByClass((Class)PacketRender.class)).isEnabled() && entity == Minecraft.getMinecraft().player) {
            this.prevRenderHeadYaw = entity.prevRotationYawHead;
            this.prevRenderPitch = entity.prevRotationPitch;
            this.renderPitch = entity.rotationPitch;
            this.renderYaw = entity.rotationYaw;
            this.renderHeadYaw = entity.rotationYawHead;
            entity.rotationPitch = PacketRender.getPitch();
            entity.prevRotationPitch = this.lastRenderPitch;
            entity.rotationYaw = PacketRender.getYaw();
            entity.rotationYawHead = PacketRender.getYaw();
            entity.prevRotationYawHead = this.lastRenderHeadYaw;
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("RETURN") })
    private void rotateEnd(final AbstractClientPlayer entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
        if (((PacketRender)Thunderhack.moduleManager.getModuleByClass((Class)PacketRender.class)).isEnabled() && entity == Minecraft.getMinecraft().player) {
            this.lastRenderHeadYaw = entity.rotationYawHead;
            this.lastRenderPitch = entity.rotationPitch;
            entity.rotationPitch = this.renderPitch;
            entity.rotationYaw = this.renderYaw;
            entity.rotationYawHead = this.renderHeadYaw;
            entity.prevRotationYawHead = this.prevRenderHeadYaw;
            entity.prevRotationPitch = this.prevRenderPitch;
        }
    }
    
    @Inject(method = { "renderLivingAt" }, at = { @At("HEAD") })
    protected void renderLivingAt(final AbstractClientPlayer entityLivingBaseIn, final double x, final double y, final double z, final CallbackInfo callbackInfo) {
        if (((EzingKids)Thunderhack.moduleManager.getModuleByClass((Class)EzingKids.class)).isOn()) {
            if ((boolean)EzingKids.INSTANCE.onlyme.getValue() && !entityLivingBaseIn.equals((Object)Minecraft.getMinecraft().player)) {
                return;
            }
            GlStateManager.scale((float)EzingKids.INSTANCE.scale.getValue(), (float)EzingKids.INSTANCE.scale.getValue(), (float)EzingKids.INSTANCE.scale.getValue());
            GlStateManager.translate(0.0f, (float)EzingKids.INSTANCE.translatey.getValue(), 0.0f);
        }
    }
    
    @Overwrite
    public ResourceLocation getEntityTexture(final AbstractClientPlayer entity) {
        if (ChangeSkinCommand.getInstance().changedplayers.contains(entity.getName())) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            return SkinStorageManipulationer.getTexture3(entity.getName(), "png");
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return entity.getLocationSkin();
    }
}
