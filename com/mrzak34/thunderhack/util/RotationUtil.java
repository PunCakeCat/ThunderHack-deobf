//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

public class RotationUtil implements Util
{
    public static Vec3d getEyesPos() {
        return new Vec3d(RotationUtil.mc.player.posX, RotationUtil.mc.player.posY + RotationUtil.mc.player.getEyeHeight(), RotationUtil.mc.player.posZ);
    }
    
    public static double[] directionSpeed(final double speed) {
        final Minecraft mc = Minecraft.getMinecraft();
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
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
    
    public static void faceVectorPacketInstant(final Vec3d faceVec) {
        final float[] var = getLegitRotations(faceVec);
        RotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(var[0], var[1], RotationUtil.mc.player.onGround));
    }
    
    public static EntityPlayer getRotationPlayer() {
        final EntityPlayer rotationEntity = (EntityPlayer)RotationUtil.mc.player;
        return (EntityPlayer)((rotationEntity == null) ? RotationUtil.mc.player : rotationEntity);
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing) {
        return getRotations(pos, facing, (Entity)getRotationPlayer());
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from) {
        return getRotations(pos, facing, from, (IBlockAccess)RotationUtil.mc.world, RotationUtil.mc.world.getBlockState(pos));
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from, final IBlockAccess world, final IBlockState state) {
        final AxisAlignedBB bb = state.getBoundingBox(world, pos);
        double x = pos.getX() + (bb.minX + bb.maxX) / 2.0;
        double y = pos.getY() + (bb.minY + bb.maxY) / 2.0;
        double z = pos.getZ() + (bb.minZ + bb.maxZ) / 2.0;
        if (facing != null) {
            x += facing.getDirectionVec().getX() * ((bb.minX + bb.maxX) / 2.0);
            y += facing.getDirectionVec().getY() * ((bb.minY + bb.maxY) / 2.0);
            z += facing.getDirectionVec().getZ() * ((bb.minZ + bb.maxZ) / 2.0);
        }
        return getRotations(x, y, z, from);
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final Entity f) {
        return getRotations(x, y, z, f.posX, f.posY, f.posZ, f.getEyeHeight());
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final double fromX, final double fromY, final double fromZ, final float fromHeight) {
        final double xDiff = x - fromX;
        final double yDiff = y - (fromY + fromHeight);
        final double zDiff = z - fromZ;
        final double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        final float prevYaw = RotationUtil.mc.player.rotationYaw;
        float diff = yaw - prevYaw;
        if (diff < -180.0f || diff > 180.0f) {
            final float round = (float)Math.round(Math.abs(diff / 360.0f));
            diff = ((diff < 0.0f) ? (diff + 360.0f * round) : (diff - 360.0f * round));
        }
        return new float[] { prevYaw + diff, pitch };
    }
    
    public static float[] simpleFacing(final EnumFacing facing) {
        switch (facing) {
            case DOWN: {
                return new float[] { RotationUtil.mc.player.rotationYaw, 90.0f };
            }
            case UP: {
                return new float[] { RotationUtil.mc.player.rotationYaw, -90.0f };
            }
            case NORTH: {
                return new float[] { 180.0f, 0.0f };
            }
            case SOUTH: {
                return new float[] { 0.0f, 0.0f };
            }
            case WEST: {
                return new float[] { 90.0f, 0.0f };
            }
            default: {
                return new float[] { 270.0f, 0.0f };
            }
        }
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        double pitch = Math.asin(diry /= len);
        dirz /= len;
        double yaw = Math.atan2(dirz, dirx /= len);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        final double[] array = new double[2];
        yaw = (array[0] = yaw + 90.0);
        array[1] = pitch;
        return array;
    }
    
    public static Vec2f getRotationTo(final AxisAlignedBB box) {
        final EntityPlayerSP player = RotationUtil.mc.player;
        if (player == null) {
            return Vec2f.ZERO;
        }
        final Vec3d eyePos = player.getPositionEyes(1.0f);
        if (player.getEntityBoundingBox().intersects(box)) {
            return getRotationTo(eyePos, box.getCenter());
        }
        final double x = MathHelper.clamp(eyePos.x, box.minX, box.maxX);
        final double y = MathHelper.clamp(eyePos.y, box.minY, box.maxY);
        final double z = MathHelper.clamp(eyePos.z, box.minZ, box.maxZ);
        return getRotationTo(eyePos, new Vec3d(x, y, z));
    }
    
    public static Vec2f getRotationTo(final Vec3d posTo) {
        final EntityPlayerSP player = RotationUtil.mc.player;
        return (player != null) ? getRotationTo(player.getPositionEyes(1.0f), posTo) : Vec2f.ZERO;
    }
    
    public static Vec2f getRotationTo(final Vec3d posTo, final EntityPlayer player) {
        return (player != null) ? getRotationTo(player.getPositionEyes(1.0f), posTo) : Vec2f.ZERO;
    }
    
    public static Vec2f getRotationTo(final Vec3d posFrom, final Vec3d posTo) {
        return getRotationFromVec(posTo.subtract(posFrom));
    }
    
    public static Vec2f getRotationFromVec(final Vec3d vec) {
        final double lengthXZ = Math.hypot(vec.x, vec.z);
        final double yaw = normalizeAngle(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0);
        final double pitch = normalizeAngle(Math.toDegrees(-Math.atan2(vec.y, lengthXZ)));
        return new Vec2f((float)yaw, (float)pitch);
    }
    
    public static double normalizeAngle(double angle) {
        angle %= 360.0;
        if (angle >= 180.0) {
            angle -= 360.0;
        }
        if (angle < -180.0) {
            angle += 360.0;
        }
        return angle;
    }
    
    public static float getFixedRotation(final float rot) {
        return getDeltaMouse(rot) * getGCDValue();
    }
    
    public static float getDeltaMouse(final float delta) {
        return (float)Math.round(delta / getGCDValue());
    }
    
    public static float getGCDValue() {
        return (float)(getGCD() * 0.15);
    }
    
    public static float getGCD() {
        final float f1 = (float)(Util.mc.gameSettings.mouseSensitivity * 0.6 + 0.2);
        return f1 * f1 * f1 * 8.0f;
    }
    
    public static boolean canSeeEntityAtFov(final Entity entityLiving, final float scope) {
        final Minecraft mc = Util.mc;
        Minecraft.getMinecraft();
        final double diffX = entityLiving.posX - Util.mc.player.posX;
        Minecraft.getMinecraft();
        final double diffZ = entityLiving.posZ - Util.mc.player.posZ;
        final float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        final double d = newYaw;
        Minecraft.getMinecraft();
        final double difference = angleDifference(d, Util.mc.player.rotationYaw);
        return difference <= scope;
    }
    
    public static double angleDifference(final double a, final double b) {
        float yaw360 = (float)(Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }
    
    public static float[] getNeededRotations(final Entity entityLivingBase) {
        final double d = entityLivingBase.posX - Util.mc.player.posX;
        final double d2 = entityLivingBase.posZ - Util.mc.player.posZ;
        final double n = entityLivingBase.posY + entityLivingBase.getEyeHeight();
        final double minY = Util.mc.player.getEntityBoundingBox().minY;
        final double maxY = Util.mc.player.getEntityBoundingBox().maxY;
        final Minecraft mc = Util.mc;
        final double d3 = n - (minY + (maxY - Minecraft.getMinecraft().player.getEntityBoundingBox().minY));
        final double d4 = MathHelper.sqrt(d * d + d2 * d2);
        final float f = (float)(MathHelper.atan2(d2, d) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.atan2(d3, d4) * 180.0 / 3.141592653589793));
        return new float[] { f, f2 };
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { RotationUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - RotationUtil.mc.player.rotationYaw), RotationUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - RotationUtil.mc.player.rotationPitch) };
    }
    
    public static void faceYawAndPitch(final float yaw, final float pitch) {
        RotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, RotationUtil.mc.player.onGround));
    }
    
    public static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final float[] rotations = getLegitRotations(vec);
        RotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], RotationUtil.mc.player.onGround));
    }
    
    public static void faceEntity(final Entity entity) {
        final float[] angle = MathUtil.calcAngle(RotationUtil.mc.player.getPositionEyes(RotationUtil.mc.getRenderPartialTicks()), entity.getPositionEyes(RotationUtil.mc.getRenderPartialTicks()));
        faceYawAndPitch(angle[0], angle[1]);
    }
    
    public static float[] getAngle(final Entity entity) {
        return MathUtil.calcAngle(RotationUtil.mc.player.getPositionEyes(RotationUtil.mc.getRenderPartialTicks()), entity.getPositionEyes(RotationUtil.mc.getRenderPartialTicks()));
    }
    
    public static double getAngle(final Entity entity, final double yOffset) {
        final Vec3d vec3d = fromTo(interpolatedEyePos(), entity.posX, entity.posY + yOffset, entity.posZ);
        return angle(vec3d, interpolatedEyeVec());
    }
    
    public static Vec3d interpolatedEyePos() {
        return RotationUtil.mc.player.getPositionEyes(RotationUtil.mc.getRenderPartialTicks());
    }
    
    public static Vec3d interpolatedEyeVec() {
        return RotationUtil.mc.player.getLook(RotationUtil.mc.getRenderPartialTicks());
    }
    
    public static Vec3d fromTo(final Vec3d from, final double x, final double y, final double z) {
        return fromTo(from.x, from.y, from.z, x, y, z);
    }
    
    public static Vec3d fromTo(final double x, final double y, final double z, final Vec3d to) {
        return fromTo(x, y, z, to.x, to.y, to.z);
    }
    
    public static Vec3d fromTo(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        return new Vec3d(x2 - x, y2 - y, z2 - z);
    }
    
    public static double angle(final Vec3d vec3d, final Vec3d other) {
        final double lengthSq = vec3d.length() * other.length();
        if (lengthSq < 1.0E-4) {
            return 0.0;
        }
        final double dot = vec3d.dotProduct(other);
        final double arg = dot / lengthSq;
        if (arg > 1.0) {
            return 0.0;
        }
        if (arg < -1.0) {
            return 180.0;
        }
        return Math.acos(arg) * 180.0 / 3.141592653589793;
    }
    
    public static double angle(final float[] rotation1, final float[] rotation2) {
        final Vec3d r1Vec = getVec3d(rotation1[0], rotation1[1]);
        final Vec3d r2Vec = getVec3d(rotation2[0], rotation2[1]);
        return angle(r1Vec, r2Vec);
    }
    
    public static Vec3d getVec3d(final float yaw, final float pitch) {
        final float vx = -MathHelper.sin(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vz = MathHelper.cos(MathUtil.rad(yaw)) * MathHelper.cos(MathUtil.rad(pitch));
        final float vy = -MathHelper.sin(MathUtil.rad(pitch));
        return new Vec3d((double)vx, (double)vy, (double)vz);
    }
    
    public static int getDirection4D() {
        return MathHelper.floor(RotationUtil.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
    }
    
    public static String getDirection4D(final boolean northRed) {
        final int dirnumber = getDirection4D();
        if (dirnumber == 0) {
            return "South (+Z)";
        }
        if (dirnumber == 1) {
            return "West (-X)";
        }
        if (dirnumber == 2) {
            return (northRed ? "\u00c2§c" : "") + "North (-Z)";
        }
        if (dirnumber == 3) {
            return "East (+X)";
        }
        return "Loading...";
    }
    
    public static boolean isInFov(final Entity player) {
        return false;
    }
}
