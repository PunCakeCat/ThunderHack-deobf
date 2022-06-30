//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;

@Mixin({ ItemRenderer.class })
public abstract class MixinItemRenderer
{
    public Minecraft mc;
    private boolean injection;
    private static final ResourceLocation RESOURCE;
    
    public MixinItemRenderer() {
        this.injection = true;
    }
    
    @Shadow
    public abstract void renderItemInFirstPerson(final AbstractClientPlayer p0, final float p1, final float p2, final EnumHand p3, final float p4, final ItemStack p5, final float p6);
    
    @Shadow
    protected abstract void renderArmFirstPerson(final float p0, final float p1, final EnumHandSide p2);
    
    @Inject(method = { "transformSideFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void transformSideFirstPerson(final EnumHandSide hand, final float p_187459_2_, final CallbackInfo cancel) {
        final RenderItemEvent event = new RenderItemEvent(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (ViewModel.getInstance().isEnabled()) {
            final boolean bob = ViewModel.getInstance().isDisabled() || (boolean)ViewModel.getInstance().doBob.getValue();
            final int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
            GlStateManager.translate(i * 0.56f, -0.52f + (bob ? p_187459_2_ : 0.0f) * -0.6f, -0.72f);
            if (hand == EnumHandSide.RIGHT) {
                GlStateManager.translate(event.getMainX(), event.getMainY(), event.getMainZ());
                RenderUtil.rotationHelper((float)event.getMainRotX(), (float)event.getMainRotY(), (float)event.getMainRotZ());
            }
            else {
                GlStateManager.translate(event.getOffX(), event.getOffY(), event.getOffZ());
                RenderUtil.rotationHelper((float)event.getOffRotX(), (float)event.getOffRotY(), (float)event.getOffRotZ());
            }
            cancel.cancel();
        }
    }
    
    @Inject(method = { "renderFireInFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFireInFirstPersonHook(final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().fire.getValue()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "transformEatFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    private void transformEatFirstPerson(final float p_187454_1_, final EnumHandSide hand, final ItemStack stack, final CallbackInfo cancel) {
        if (ViewModel.getInstance().isEnabled()) {
            if (!(boolean)ViewModel.getInstance().noEatAnimation.getValue()) {
                final float f = Minecraft.getMinecraft().player.getItemInUseCount() - p_187454_1_ + 1.0f;
                final float f2 = f / stack.getMaxItemUseDuration();
                if (f2 < 0.8f) {
                    final float f3 = MathHelper.abs(MathHelper.cos(f / 4.0f * 3.1415927f) * 0.1f);
                    GlStateManager.translate(0.0f, f3, 0.0f);
                }
                final float f3 = 1.0f - (float)Math.pow(f2, 27.0);
                final int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
                GlStateManager.translate(f3 * 0.6f * i * (double)ViewModel.getInstance().eatX.getValue(), f3 * 0.5f * -(double)ViewModel.getInstance().eatY.getValue(), 0.0);
                GlStateManager.rotate(i * f3 * 90.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(f3 * 10.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(i * f3 * 30.0f, 0.0f, 0.0f, 1.0f);
            }
            cancel.cancel();
        }
    }
    
    @Inject(method = { "renderSuffocationOverlay" }, at = { @At("HEAD") }, cancellable = true)
    public void renderSuffocationOverlay(final CallbackInfo ci) {
        if (NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().blocks.getValue()) {
            ci.cancel();
        }
    }
    
    void rotationHelper(final float xAngle, final float yAngle, final float zAngle) {
        GlStateManager.rotate(yAngle, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(zAngle, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(xAngle, 1.0f, 0.0f, 0.0f);
    }
    
    private void renderEffect(final float p_187456_1_, final float p_187456_2_, final EnumHandSide p_187456_3_) {
        final float f = Minecraft.getMinecraft().player.ticksExisted + Minecraft.getMinecraft().getRenderPartialTicks();
        Minecraft.getMinecraft().getTextureManager().bindTexture(MixinItemRenderer.RESOURCE);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        final float f2 = 0.5f;
        GlStateManager.color(0.5f, 0.5f, 0.5f, 0.5f);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            final float f3 = 0.76f;
            GlStateManager.color(0.38f, 0.19f, 0.608f, 0.5f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            final float f4 = 0.33333334f;
            GlStateManager.scale(0.33333334f, 0.33333334f, 0.33333334f);
            GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.0f, f * (0.001f + i * 0.003f) * 20.0f, 0.0f);
            GlStateManager.matrixMode(5888);
            final RenderPlayer renderPlayer = (RenderPlayer)Minecraft.getMinecraft().getRenderManager().getEntityRenderObject((Entity)Minecraft.getMinecraft().player);
            if (renderPlayer != null) {
                if (p_187456_3_ == EnumHandSide.RIGHT) {
                    renderPlayer.renderRightArm((AbstractClientPlayer)Minecraft.getMinecraft().player);
                }
                else {
                    renderPlayer.renderLeftArm((AbstractClientPlayer)Minecraft.getMinecraft().player);
                }
            }
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
    
    static {
        RESOURCE = new ResourceLocation("textures/rainbow.png");
    }
}
