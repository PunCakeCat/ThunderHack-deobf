//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.item.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.*;
import javax.vecmath.*;
import java.awt.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.client.multiplayer.*;
import javax.annotation.*;
import com.google.common.base.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.player.*;
import org.lwjgl.input.*;
import java.util.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.event.events.*;
import org.lwjgl.util.glu.*;
import net.minecraft.entity.*;
import net.minecraftforge.client.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer
{
    @Shadow
    private ItemStack itemActivationItem;
    @Shadow
    @Final
    private Minecraft mc;
    private boolean injection;
    @Shadow
    public boolean debugView;
    @Shadow
    public float farPlaneDistance;
    @Final
    @Shadow
    public ItemRenderer itemRenderer;
    @Shadow
    @Final
    private int[] lightmapColors;
    @Shadow
    public float thirdPersonDistancePrev;
    @Shadow
    public boolean cloudFog;
    
    public MixinEntityRenderer() {
        this.injection = true;
    }
    
    @Shadow
    public abstract void getMouseOver(final float p0);
    
    @Inject(method = { "renderItemActivation" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemActivationHook(final CallbackInfo info) {
        if (this.itemActivationItem != null && NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().totemPops.getValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderRainSnow(F)V") })
    public void weatherHook(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo ci) {
        if (((Weather)Thunderhack.moduleManager.getModuleByClass((Class)Weather.class)).isOn()) {
            ((Weather)Thunderhack.moduleManager.getModuleByClass((Class)Weather.class)).render(partialTicks);
        }
    }
    
    @Inject(method = { "updateLightmap" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;updateDynamicTexture()V", shift = At.Shift.BEFORE) })
    private void updateTextureHook(final float partialTicks, final CallbackInfo ci) {
        final Ambience ambience = (Ambience)Thunderhack.moduleManager.getModuleByClass((Class)Ambience.class);
        if (ambience.isEnabled()) {
            for (int i = 0; i < this.lightmapColors.length; ++i) {
                final Color ambientColor = ((ColorSetting)ambience.colorLight.getValue()).getColorObject();
                final int alpha = ambientColor.getAlpha();
                final float modifier = alpha / 255.0f;
                final int color = this.lightmapColors[i];
                final int[] bgr = this.toRGBAArray(color);
                final Vector3f values = new Vector3f(bgr[2] / 255.0f, bgr[1] / 255.0f, bgr[0] / 255.0f);
                final Vector3f newValues = new Vector3f(ambientColor.getRed() / 255.0f, ambientColor.getGreen() / 255.0f, ambientColor.getBlue() / 255.0f);
                final Vector3f finalValues = this.mix(values, newValues, modifier);
                final int red = (int)(finalValues.x * 255.0f);
                final int green = (int)(finalValues.y * 255.0f);
                final int blue = (int)(finalValues.z * 255.0f);
                this.lightmapColors[i] = (0xFF000000 | red << 16 | green << 8 | blue);
            }
        }
    }
    
    private int[] toRGBAArray(final int colorBuffer) {
        return new int[] { colorBuffer >> 16 & 0xFF, colorBuffer >> 8 & 0xFF, colorBuffer & 0xFF };
    }
    
    private Vector3f mix(final Vector3f first, final Vector3f second, final float factor) {
        return new Vector3f(first.x * (1.0f - factor) + second.x * factor, first.y * (1.0f - factor) + second.y * factor, first.z * (1.0f - factor) + first.z * factor);
    }
    
    @Inject(method = { "updateLightmap" }, at = { @At("HEAD") }, cancellable = true)
    private void updateLightmap(final float partialTicks, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && (NoRender.getInstance().skylight.getValue() == NoRender.Skylight.ENTITY || NoRender.getInstance().skylight.getValue() == NoRender.Skylight.ALL)) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "setupCameraTransform" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
    public float prevTimeInPortalHook(final EntityPlayerSP entityPlayerSP) {
        if (NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().nausea.getValue()) {
            return -3.4028235E38f;
        }
        return entityPlayerSP.prevTimeInPortal;
    }
    
    @Inject(method = { "setupFog" }, at = { @At("HEAD") }, cancellable = true)
    public void setupFogHook(final int startCoords, final float partialTicks, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().fog.getValue() == NoRender.Fog.NOFOG) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "setupFog" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;getBlockStateAtEntityViewpoint(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;F)Lnet/minecraft/block/state/IBlockState;"))
    public IBlockState getBlockStateAtEntityViewpointHook(final World worldIn, final Entity entityIn, final float p_186703_2_) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().fog.getValue() == NoRender.Fog.AIR) {
            return Blocks.AIR.defaultBlockState;
        }
        return ActiveRenderInfo.getBlockStateAtEntityViewpoint(worldIn, entityIn, p_186703_2_);
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffectHook(final float ticks, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().hurtcam.getValue()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcludingHook(final WorldClient worldClient, @Nullable final Entity entityIn, final AxisAlignedBB boundingBox, @Nullable final Predicate<? super Entity> predicate) {
        if (NoEntityTrace.getINSTANCE().isOn() && NoEntityTrace.getINSTANCE().noTrace) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, (Predicate)predicate);
    }
    
    @Inject(method = { "renderWorld" }, at = { @At("RETURN") })
    private void renderWorldHook(final CallbackInfo info) {
        final int guiScale = this.mc.gameSettings.guiScale;
        this.mc.gameSettings.guiScale = 1;
        MinecraftForge.EVENT_BUS.post((Event)new WorldRenderEvent());
        this.mc.gameSettings.guiScale = guiScale;
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    private Entity redirectMouseOver(final Minecraft mc) {
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled() && Keyboard.isKeyDown(((SubBind)FreeCam.getInstance().movePlayer.getValue()).getKey())) {
            return (Entity)mc.player;
        }
        return mc.getRenderViewEntity();
    }
    
    @Redirect(method = { "updateCameraAndRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
    private void redirectTurn(final EntityPlayerSP entityPlayerSP, final float yaw, final float pitch) {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            final FreecamEvent event = new FreecamEvent();
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                if (Keyboard.isKeyDown(((SubBind)FreeCam.getInstance().movePlayer.getValue()).getKey())) {
                    mc.player.turn(yaw, pitch);
                }
                else {
                    Objects.requireNonNull(mc.getRenderViewEntity(), "Render Entity").turn(yaw, pitch);
                }
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        entityPlayerSP.turn(yaw, pitch);
    }
    
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isSpectator()Z"))
    public boolean redirectIsSpectator(final EntityPlayerSP entityPlayerSP) {
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.isCanceled() || (entityPlayerSP != null && entityPlayerSP.isSpectator());
    }
    
    @Inject(method = { "renderHand" }, at = { @At("HEAD") }, cancellable = true)
    public void renderHandMain(final float partialTicks, final int pass, final CallbackInfo ci) {
        final ItemShaders module = (ItemShaders)Thunderhack.moduleManager.getModuleByClass((Class)ItemShaders.class);
        if (module.isEnabled()) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (!(boolean)module.cancelItem.getValue()) {
                this.doRenderHand(partialTicks, pass, mc);
            }
            if (module.glowESP.getValue() != ItemShaders.glowESPmode.None && module.fillShader.getValue() != ItemShaders.fillShadermode.None) {
                GlStateManager.pushMatrix();
                final RenderHand.PreBoth hand = new RenderHand.PreBoth(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostBoth hand2 = new RenderHand.PostBoth(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand2);
                GlStateManager.popMatrix();
            }
            if (module.glowESP.getValue() != ItemShaders.glowESPmode.None) {
                GlStateManager.pushMatrix();
                final RenderHand.PreOutline hand3 = new RenderHand.PreOutline(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand3);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostOutline hand4 = new RenderHand.PostOutline(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand4);
                GlStateManager.popMatrix();
            }
            if (module.fillShader.getValue() != ItemShaders.fillShadermode.None) {
                GlStateManager.pushMatrix();
                final RenderHand.PreFill hand5 = new RenderHand.PreFill(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand5);
                this.doRenderHand(partialTicks, pass, mc);
                final RenderHand.PostFill hand6 = new RenderHand.PostFill(partialTicks);
                MinecraftForge.EVENT_BUS.post((Event)hand6);
                GlStateManager.popMatrix();
            }
            ci.cancel();
        }
    }
    
    @Shadow
    public abstract float getFOVModifier(final float p0, final boolean p1);
    
    @Shadow
    public abstract void hurtCameraEffect(final float p0);
    
    @Shadow
    public abstract void applyBobbing(final float p0);
    
    @Shadow
    public abstract void enableLightmap();
    
    @Shadow
    public abstract void disableLightmap();
    
    void doRenderHand(final float partialTicks, final int pass, final Minecraft mc) {
        if (!this.debugView) {
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            final float f = 0.07f;
            if (mc.gameSettings.anaglyph) {
                GlStateManager.translate(-(pass * 2 - 1) * 0.07f, 0.0f, 0.0f);
            }
            Project.gluPerspective(this.getFOVModifier(partialTicks, false), mc.displayWidth / (float)mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            if (mc.gameSettings.anaglyph) {
                GlStateManager.translate((pass * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            GlStateManager.pushMatrix();
            this.hurtCameraEffect(partialTicks);
            if (mc.gameSettings.viewBobbing) {
                this.applyBobbing(partialTicks);
            }
            final boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping();
            if (!ForgeHooksClient.renderFirstPersonHand(mc.renderGlobal, partialTicks, pass) && mc.gameSettings.thirdPersonView == 0 && !flag && !mc.gameSettings.hideGUI && !mc.playerController.isSpectator()) {
                this.enableLightmap();
                this.itemRenderer.renderItemInFirstPerson(partialTicks);
                this.disableLightmap();
            }
            GlStateManager.popMatrix();
            if (mc.gameSettings.thirdPersonView == 0 && !flag) {
                this.itemRenderer.renderOverlays(partialTicks);
                this.hurtCameraEffect(partialTicks);
            }
            if (mc.gameSettings.viewBobbing) {
                this.applyBobbing(partialTicks);
            }
        }
    }
    
    @Shadow
    public abstract void renderHand(final float p0, final int p1);
    
    @Overwrite
    public void orientCamera(final float partialTicks) {
        final Entity entity = this.mc.getRenderViewEntity();
        float f = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        double d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
        double d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
            ++f;
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final BlockPos blockpos = new BlockPos(entity);
                final IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
                ForgeHooksClient.orientBedCamera((IBlockAccess)this.mc.world, blockpos, iblockstate, entity);
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double d4 = ((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).isOn() ? ((double)(int)((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).z.getValue()) : (this.thirdPersonDistancePrev + (4.0f - this.thirdPersonDistancePrev) * partialTicks);
            if (this.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-d4));
            }
            else {
                float f2;
                float f3;
                if (((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).isOff()) {
                    f2 = entity.rotationYaw;
                    f3 = entity.rotationPitch;
                }
                else {
                    f2 = entity.rotationYaw + (int)((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).x.getValue();
                    f3 = entity.rotationPitch + (int)((ThirdPersView)Thunderhack.moduleManager.getModuleByClass((Class)ThirdPersView.class)).y.getValue();
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    f3 += 180.0f;
                }
                final double d5 = -MathHelper.sin(f2 * 0.017453292f) * MathHelper.cos(f3 * 0.017453292f) * d4;
                final double d6 = MathHelper.cos(f2 * 0.017453292f) * MathHelper.cos(f3 * 0.017453292f) * d4;
                final double d7 = -MathHelper.sin(f3 * 0.017453292f) * d4;
                for (int i = 0; i < 8; ++i) {
                    float f4 = (float)((i & 0x1) * 2 - 1);
                    float f5 = (float)((i >> 1 & 0x1) * 2 - 1);
                    float f6 = (float)((i >> 2 & 0x1) * 2 - 1);
                    f4 *= 0.1f;
                    f5 *= 0.1f;
                    f6 *= 0.1f;
                    final RayTraceResult raytraceresult = this.mc.world.rayTraceBlocks(new Vec3d(d0 + f4, d2 + f5, d3 + f6), new Vec3d(d0 - d5 + f4 + f6, d2 - d7 + f5, d3 - d6 + f6));
                    if (raytraceresult != null) {
                        final double d8 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d2, d3));
                        if (d8 < d4) {
                            d4 = d8;
                        }
                    }
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(entity.rotationPitch - f3, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(entity.rotationYaw - f2, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-d4));
                GlStateManager.rotate(f2 - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(f3 - entity.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
        }
        else {
            GlStateManager.translate(0.0f, 0.0f, 0.05f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0f;
            final float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            final float f2 = 0.0f;
            if (entity instanceof EntityAnimal) {
                final EntityAnimal entityanimal = (EntityAnimal)entity;
                yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0f;
            }
            final IBlockState state = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)this.mc.world, entity, partialTicks);
            final EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup(this.mc.entityRenderer, entity, state, (double)partialTicks, yaw, pitch, f2);
            MinecraftForge.EVENT_BUS.post((Event)event);
            GlStateManager.rotate(event.getRoll(), 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(event.getPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(event.getYaw(), 0.0f, 1.0f, 0.0f);
        }
        GlStateManager.translate(0.0f, -f, 0.0f);
        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
        d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d2, d3, partialTicks);
    }
}
