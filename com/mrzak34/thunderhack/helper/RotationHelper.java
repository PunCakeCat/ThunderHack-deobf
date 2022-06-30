//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class RotationHelper
{
    public static Vec3d getEyesPos() {
        return new Vec3d(Util.mc.player.posX, Util.mc.player.getEntityBoundingBox().minY + Util.mc.player.getEyeHeight(), Util.mc.player.posZ);
    }
    
    public static float[] getRotations(final Entity entityIn, final boolean random, final float maxSpeed, final float minSpeed, final float yawRandom, final float pitchRandom) {
        final double diffX = entityIn.posX + (entityIn.posX - entityIn.prevPosX) * 0.05000000074505806 - Util.mc.player.posX - Util.mc.player.motionX * 0.05000000074505806;
        final double diffZ = entityIn.posZ + (entityIn.posZ - entityIn.prevPosZ) * 0.05000000074505806 - Util.mc.player.posZ - Util.mc.player.motionZ * 0.05000000074505806;
        final double diffY = entityIn.posY + entityIn.getEyeHeight() - (Util.mc.player.posY + Util.mc.player.getEyeHeight()) - 0.1599999964237213 - (((EntityLivingBase)entityIn).canEntityBeSeen((Entity)Util.mc.player) ? 0.0 : -0.38);
        float randomYaw = 0.0f;
        if (random) {
            randomYaw = MathematicHelper.randomizeFloat(yawRandom, -yawRandom);
        }
        float randomPitch = 0.0f;
        if (random) {
            randomPitch = MathematicHelper.randomizeFloat(pitchRandom, -pitchRandom);
        }
        final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793 - 90.0) + randomYaw;
        float pitch = (float)(-(Math.atan2(diffY, diffXZ) * 180.0 / 3.141592653589793)) + randomPitch;
        yaw = Util.mc.player.rotationYaw + GCDCalcHelper.getFixedRotation(MathHelper.wrapDegrees(yaw - Util.mc.player.rotationYaw));
        pitch = Util.mc.player.rotationPitch + GCDCalcHelper.getFixedRotation(MathHelper.wrapDegrees(pitch - Util.mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
        yaw = updateRotation(Util.mc.player.rotationYaw, yaw, MathematicHelper.randomizeFloat(minSpeed, maxSpeed));
        pitch = updateRotation(Util.mc.player.rotationPitch, pitch, MathematicHelper.randomizeFloat(minSpeed, maxSpeed));
        return new float[] { yaw, pitch };
    }
    
    public static float getAngleEntity(final Entity entity) {
        final float yaw = Util.mc.player.rotationYaw;
        final double xDist = entity.posX - Util.mc.player.posX;
        final double zDist = entity.posZ - Util.mc.player.posZ;
        final float yaw2 = (float)(Math.atan2(zDist, xDist) * 180.0 / 3.141592653589793 - 90.0);
        return yaw + MathHelper.wrapDegrees(yaw2 - yaw);
    }
    
    public static float updateRotation(final float current, final float newValue, final float speed) {
        float f = MathHelper.wrapDegrees(newValue - current);
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }
    
    public static class Rotation
    {
        public static boolean isReady;
        public static float packetPitch;
        public static float packetYaw;
        public static float lastPacketPitch;
        public static float lastPacketYaw;
        public static float renderPacketYaw;
        public static float lastRenderPacketYaw;
        public static float bodyYaw;
        public static float lastBodyYaw;
        public static int rotationCounter;
        public static boolean isAiming;
        
        public static boolean isAiming() {
            return !Rotation.isAiming;
        }
        
        public static double calcMove() {
            final double x = Util.mc.player.posX - Util.mc.player.prevPosX;
            final double z = Util.mc.player.posZ - Util.mc.player.prevPosZ;
            return Math.hypot(x, z);
        }
        
        static {
            Rotation.isReady = false;
        }
    }
}
