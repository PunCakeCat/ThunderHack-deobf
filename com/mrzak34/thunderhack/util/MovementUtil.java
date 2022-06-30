//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.potion.*;

public class MovementUtil implements Util
{
    public static Vec3d calculateLine(final Vec3d x1, final Vec3d x2, final double distance) {
        final double length = Math.sqrt(multiply(x2.x - x1.x) + multiply(x2.y - x1.y) + multiply(x2.z - x1.z));
        final double unitSlopeX = (x2.x - x1.x) / length;
        final double unitSlopeY = (x2.y - x1.y) / length;
        final double unitSlopeZ = (x2.z - x1.z) / length;
        final double x3 = x1.x + unitSlopeX * distance;
        final double y = x1.y + unitSlopeY * distance;
        final double z = x1.z + unitSlopeZ * distance;
        return new Vec3d(x3, y, z);
    }
    
    public static Vec2f calculateLineNoY(final Vec2f x1, final Vec2f x2, final double distance) {
        final double length = Math.sqrt(multiply(x2.x - x1.x) + multiply(x2.y - x1.y));
        final double unitSlopeX = (x2.x - x1.x) / length;
        final double unitSlopeZ = (x2.y - x1.y) / length;
        final float x3 = (float)(x1.x + unitSlopeX * distance);
        final float z = (float)(x1.y + unitSlopeZ * distance);
        return new Vec2f(x3, z);
    }
    
    public static boolean isMoving() {
        return MovementUtil.mc.player.moveForward != 0.0 || MovementUtil.mc.player.moveStrafing != 0.0;
    }
    
    public static void strafe(final EventMove event, final double speed) {
        if (isMoving()) {
            final double[] strafe = strafe(speed);
            event.set_x(strafe[0]);
            event.set_z(strafe[1]);
        }
        else {
            event.set_x(0.0);
            event.set_z(0.0);
        }
    }
    
    public static double[] strafe(final double speed) {
        return strafe((Entity)MovementUtil.mc.player, speed);
    }
    
    public static double[] strafe(final Entity entity, final double speed) {
        return strafe(entity, MovementUtil.mc.player.movementInput, speed);
    }
    
    public static double[] strafe(final Entity entity, final MovementInput movementInput, final double speed) {
        float moveForward = movementInput.moveForward;
        float moveStrafe = movementInput.moveStrafe;
        float rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * MovementUtil.mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
            }
            else if (moveStrafe < 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double posX = moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        final double posZ = moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[] { posX, posZ };
    }
    
    public static double multiply(final double one) {
        return one * one;
    }
    
    public static Vec3d extrapolatePlayerPositionWithGravity(final EntityPlayer player, final int ticks) {
        double totalDistance = 0.0;
        double extrapolatedMotionY = player.motionY;
        for (int i = 0; i < ticks; ++i) {
            totalDistance += multiply(player.motionX) + multiply(extrapolatedMotionY) + multiply(player.motionZ);
            extrapolatedMotionY -= 0.1;
        }
        final double horizontalDistance = multiply(player.motionX) + multiply(player.motionZ) * ticks;
        final Vec2f horizontalVec = calculateLineNoY(new Vec2f((float)player.lastTickPosX, (float)player.lastTickPosZ), new Vec2f((float)player.posX, (float)player.posZ), horizontalDistance);
        double addedY = player.motionY;
        double finalY = player.posY;
        final Vec3d tempPos = new Vec3d((double)horizontalVec.x, player.posY, (double)horizontalVec.y);
        for (int j = 0; j < ticks; ++j) {
            finalY += addedY;
            addedY -= 0.1;
        }
        final RayTraceResult result = MovementUtil.mc.world.rayTraceBlocks(player.getPositionVector(), new Vec3d(tempPos.x, finalY, tempPos.z));
        if (result == null || result.typeOfHit == RayTraceResult.Type.ENTITY) {
            return new Vec3d(tempPos.x, finalY, tempPos.z);
        }
        return result.hitVec;
    }
    
    public static double[] forward(final double d) {
        float f = Minecraft.getMinecraft().player.movementInput.moveForward;
        float f2 = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float f3 = Minecraft.getMinecraft().player.prevRotationYaw + (Minecraft.getMinecraft().player.rotationYaw - Minecraft.getMinecraft().player.prevRotationYaw) * Minecraft.getMinecraft().getRenderPartialTicks();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += ((f > 0.0f) ? -45 : 45);
            }
            else if (f2 < 0.0f) {
                f3 += ((f > 0.0f) ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            }
            else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        final double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        final double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        final double d4 = f * d * d3 + f2 * d * d2;
        final double d5 = f * d * d2 - f2 * d * d3;
        return new double[] { d4, d5 };
    }
    
    public static boolean isMoving(final EntityLivingBase entityLivingBase) {
        return entityLivingBase.moveForward != 0.0f || entityLivingBase.moveStrafing != 0.0f;
    }
    
    public static void setSpeed(final EntityLivingBase entityLivingBase, final double d) {
        final double[] dArray = forward(d);
        entityLivingBase.motionX = dArray[0];
        entityLivingBase.motionZ = dArray[1];
    }
    
    public static double getBaseMoveSpeed() {
        double d = 0.2873;
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionById(1)))) {
            final int n = Objects.requireNonNull(Minecraft.getMinecraft().player.getActivePotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(1)))).getAmplifier();
            d *= 1.0 + 0.2 * (n + 1);
        }
        return d;
    }
    
    public static Vec3d extrapolatePlayerPosition(final EntityPlayer player, final int ticks) {
        final double totalDistance = 0.0;
        final double extrapolatedMotionY = player.motionY;
        for (int i = 0; i < ticks; ++i) {}
        final Vec3d lastPos = new Vec3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
        final Vec3d currentPos = new Vec3d(player.posX, player.posY, player.posZ);
        final double distance = multiply(player.motionX) + multiply(player.motionY) + multiply(player.motionZ);
        double extrapolatedPosY = player.posY;
        if (!player.hasNoGravity()) {
            extrapolatedPosY -= 0.1;
        }
        final Vec3d tempVec = calculateLine(lastPos, currentPos, distance * ticks);
        final Vec3d finalVec = new Vec3d(tempVec.x, extrapolatedPosY, tempVec.z);
        MovementUtil.mc.world.rayTraceBlocks(player.getPositionVector(), finalVec);
        return new Vec3d(tempVec.x, player.posY, tempVec.z);
    }
}
