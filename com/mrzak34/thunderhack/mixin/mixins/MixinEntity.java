//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.crash.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public float rotationYaw;
    @Shadow
    public float rotationPitch;
    @Shadow
    public boolean onGround;
    @Shadow
    public boolean noClip;
    @Shadow
    public float prevDistanceWalkedModified;
    @Shadow
    public World world;
    @Shadow
    public float stepHeight;
    @Shadow
    public boolean collidedHorizontally;
    @Shadow
    public boolean collidedVertically;
    @Shadow
    public boolean collided;
    @Shadow
    public float distanceWalkedModified;
    @Shadow
    public float distanceWalkedOnStepModified;
    @Shadow
    public boolean isInWeb;
    @Shadow
    public Random rand;
    @Shadow
    @Final
    public double[] pistonDeltas;
    @Shadow
    public long pistonDeltasGameTime;
    @Shadow
    public int fire;
    @Shadow
    public int nextStepDistance;
    @Shadow
    public float nextFlap;
    
    @Shadow
    public abstract boolean isSprinting();
    
    @Shadow
    public abstract boolean isRiding();
    
    @Shadow
    public abstract boolean isSneaking();
    
    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();
    
    @Shadow
    public abstract void setEntityBoundingBox(final AxisAlignedBB p0);
    
    @Shadow
    public abstract void resetPositionToBB();
    
    @Shadow
    protected abstract void updateFallState(final double p0, final boolean p1, final IBlockState p2, final BlockPos p3);
    
    @Shadow
    protected abstract boolean canTriggerWalking();
    
    @Shadow
    public abstract boolean isInWater();
    
    @Shadow
    public abstract boolean isBeingRidden();
    
    @Shadow
    public abstract Entity getControllingPassenger();
    
    @Shadow
    public abstract void playSound(final SoundEvent p0, final float p1, final float p2);
    
    @Shadow
    protected abstract void doBlockCollisions();
    
    @Shadow
    public abstract boolean isWet();
    
    @Shadow
    protected abstract void playStepSound(final BlockPos p0, final Block p1);
    
    @Shadow
    protected abstract SoundEvent getSwimSound();
    
    @Shadow
    protected abstract float playFlySound(final float p0);
    
    @Shadow
    protected abstract boolean makeFlySound();
    
    @Shadow
    public abstract void addEntityCrashInfo(final CrashReportCategory p0);
    
    @Shadow
    protected abstract void dealFireDamage(final int p0);
    
    @Shadow
    public abstract void setFire(final int p0);
    
    @Shadow
    protected abstract int getFireImmuneTicks();
    
    @Shadow
    public abstract boolean isBurning();
    
    @Shadow
    public abstract int getMaxInPortalTime();
    
    @Inject(method = { "turn" }, at = { @At("HEAD") }, cancellable = true)
    public void onTurn(final float yaw, final float pitch, final CallbackInfo ci) {
        final TurnEvent event = new TurnEvent(yaw, pitch);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "applyEntityCollision" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void addVelocityHook(final Entity entity, final double x, final double y, final double z) {
        final PushEvent event = new PushEvent(entity, x, y, z, true);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            entity.motionX += event.x;
            entity.motionY += event.y;
            entity.motionZ += event.z;
            entity.isAirBorne = event.airbone;
        }
    }
}
