//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import java.text.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import org.lwjgl.input.*;

public class PlayerUtils
{
    private static final Minecraft mc;
    public static double[] lastLookAt;
    
    public static boolean isPlayerMoving() {
        return PlayerUtils.mc.gameSettings.keyBindForward.isKeyDown() || PlayerUtils.mc.gameSettings.keyBindBack.isKeyDown() || PlayerUtils.mc.gameSettings.keyBindRight.isKeyDown() || PlayerUtils.mc.gameSettings.keyBindLeft.isKeyDown();
    }
    
    public static boolean isPlayerMovingLegit() {
        return PlayerUtils.mc.gameSettings.keyBindForward.isKeyDown() && !PlayerUtils.mc.player.isSneaking();
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        final float[] rotations = { PlayerUtils.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - PlayerUtils.mc.player.rotationYaw), PlayerUtils.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - PlayerUtils.mc.player.rotationPitch) };
        return rotations;
    }
    
    private static Vec3d getEyesPos() {
        return new Vec3d(PlayerUtils.mc.player.posX, PlayerUtils.mc.player.posY + PlayerUtils.mc.player.getEyeHeight(), PlayerUtils.mc.player.posZ);
    }
    
    public static Vec3d roundPos(final Vec3d vec3d) {
        final DecimalFormat f = new DecimalFormat(".##");
        final double x = Double.parseDouble(f.format(vec3d.x));
        final double y = Double.parseDouble(f.format(vec3d.y));
        final double z = Double.parseDouble(f.format(vec3d.z));
        return new Vec3d(x, y, z);
    }
    
    public static double degreeToRadian(final float degree) {
        return degree * 0.017453292519943295;
    }
    
    public static double[] getDirectionFromYaw(final float yaw) {
        final double radian = degreeToRadian(yaw);
        final double x = Math.sin(radian);
        final double z = Math.cos(radian);
        return new double[] { x, z };
    }
    
    public static double[] directionSpeed(final double speed) {
        float forward = PlayerUtils.mc.player.movementInput.moveForward;
        float side = PlayerUtils.mc.player.movementInput.moveStrafe;
        float yaw = PlayerUtils.mc.player.prevRotationYaw + (PlayerUtils.mc.player.rotationYaw - PlayerUtils.mc.player.prevRotationYaw) * PlayerUtils.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static double[] directionSpeed(final double speed, final EntityPlayerSP player) {
        final Minecraft mc = Minecraft.getMinecraft();
        float forward = player.movementInput.moveForward;
        float side = player.movementInput.moveStrafe;
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static boolean isPlayerAboveVoid() {
        boolean aboveVoid = false;
        if (PlayerUtils.mc.player.posY <= 0.0) {
            return true;
        }
        for (int i = 1; i < PlayerUtils.mc.player.posY; ++i) {
            final BlockPos pos = new BlockPos(PlayerUtils.mc.player.posX, (double)i, PlayerUtils.mc.player.posZ);
            if (!(PlayerUtils.mc.world.getBlockState(pos).getBlock() instanceof BlockAir)) {
                aboveVoid = false;
                break;
            }
            aboveVoid = true;
        }
        return aboveVoid;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static void moveToBlockCenter() {
        final double centerX = Math.floor(PlayerUtils.mc.player.posX) + 0.5;
        final double centerZ = Math.floor(PlayerUtils.mc.player.posZ) + 0.5;
        PlayerUtils.mc.player.setPosition(centerX, PlayerUtils.mc.player.posY, centerZ);
        PlayerUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(centerX, PlayerUtils.mc.player.posY, centerZ, PlayerUtils.mc.player.onGround));
    }
    
    public static boolean isKeyDown(final int i) {
        return i != 0 && i < 256 && ((i < 0) ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i));
    }
    
    static {
        mc = Minecraft.getMinecraft();
        PlayerUtils.lastLookAt = null;
    }
}
