//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.features.modules.render.*;

@Mixin({ EntityOtherPlayerMP.class })
public class MixinEntityOtherPlayerMP extends AbstractClientPlayer
{
    @Shadow
    private int otherPlayerMPPosRotationIncrements;
    @Shadow
    private double otherPlayerMPX;
    @Shadow
    private double otherPlayerMPY;
    @Shadow
    private double otherPlayerMPZ;
    @Shadow
    private double otherPlayerMPYaw;
    @Shadow
    private double otherPlayerMPPitch;
    
    public MixinEntityOtherPlayerMP(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }
    
    @Overwrite
    public void onLivingUpdate() {
        if (this.otherPlayerMPPosRotationIncrements > 0) {
            double d0;
            double d2;
            double d3;
            if (NoInterp.getInstance().isEnabled()) {
                d0 = this.serverPosX / 4096.0;
                d2 = this.serverPosY / 4096.0;
                d3 = this.serverPosZ / 4096.0;
            }
            else {
                d0 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
                d2 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
                d3 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
            }
            double d4;
            for (d4 = this.otherPlayerMPYaw - this.rotationYaw; d4 < -180.0; d4 += 360.0) {}
            while (d4 >= 180.0) {
                d4 -= 360.0;
            }
            this.rotationYaw += (float)(d4 / this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch += (float)((this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
            --this.otherPlayerMPPosRotationIncrements;
            this.setPosition(d0, d2, d3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        this.prevCameraYaw = this.cameraYaw;
        this.updateArmSwingProgress();
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f2 = (float)Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (f1 > 0.1f) {
            f1 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            f1 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            f2 = 0.0f;
        }
        this.cameraYaw += (f1 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (f2 - this.cameraPitch) * 0.8f;
        this.world.profiler.startSection("push");
        this.collideWithNearbyEntities();
        this.world.profiler.endSection();
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") }, cancellable = true)
    public void prikol(final CallbackInfo ci) {
        if ((boolean)NoInterp.getInstance().lowIQ.getValue() && NoInterp.getInstance().isEnabled()) {
            this.renderOffsetY = 0.0f;
            super.onUpdate();
            this.limbSwing = 0.0f;
            this.limbSwingAmount = 0.0f;
            this.prevLimbSwingAmount = 0.0f;
            ci.cancel();
        }
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") }, cancellable = true)
    public void prikol2(final CallbackInfo ci) {
        if (ShiftInterp.getInstance().isOn()) {
            this.renderOffsetY = 0.0f;
            super.onUpdate();
            this.limbSwing = 0.0f;
            this.limbSwingAmount = 0.0f;
            this.prevLimbSwingAmount = 0.0f;
            if (ShiftInterp.getInstance().sleep.getValue()) {
                this.sleeping = true;
            }
            else {
                this.sleeping = false;
                this.setSneaking(true);
            }
            ci.cancel();
        }
    }
}
