//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public class RayTraceUtil implements Globals
{
    public static float[] hitVecToPlaceVec(final BlockPos pos, final Vec3d hitVec) {
        final float x = (float)(hitVec.x - pos.getX());
        final float y = (float)(hitVec.y - pos.getY());
        final float z = (float)(hitVec.z - pos.getZ());
        return new float[] { x, y, z };
    }
    
    public static boolean canBeSeen(final double x, final double y, final double z, final Entity by) {
        return canBeSeen(new Vec3d(x, y, z), by.posX, by.posY, by.posZ, by.getEyeHeight());
    }
    
    public static boolean canBeSeen(final Vec3d toSee, final Entity by) {
        return canBeSeen(toSee, by.posX, by.posY, by.posZ, by.getEyeHeight());
    }
    
    public static boolean canBeSeen(final Vec3d toSee, final double x, final double y, final double z, final float eyeHeight) {
        final Vec3d start = new Vec3d(x, y + eyeHeight, z);
        return RayTraceUtil.mc.world.rayTraceBlocks(start, toSee, false, true, false) == null;
    }
    
    public static boolean canBeSeen(final Entity toSee, final EntityLivingBase by) {
        return by.canEntityBeSeen(toSee);
    }
    
    public static boolean raytracePlaceCheck(final Entity entity, final BlockPos pos) {
        return getFacing(entity, pos, false) != null;
    }
    
    public static EnumFacing getFacing(final Entity entity, final BlockPos pos, final boolean verticals) {
        for (final EnumFacing facing : EnumFacing.values()) {
            final RayTraceResult result = RayTraceUtil.mc.world.rayTraceBlocks(PositionUtil.getEyePos(entity), new Vec3d(pos.getX() + 0.5 + facing.getDirectionVec().getX() * 1.0 / 2.0, pos.getY() + 0.5 + facing.getDirectionVec().getY() * 1.0 / 2.0, pos.getZ() + 0.5 + facing.getDirectionVec().getZ() * 1.0 / 2.0), false, true, false);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK && result.getBlockPos().equals((Object)pos)) {
                return facing;
            }
        }
        if (!verticals) {
            return null;
        }
        if (pos.getY() > RayTraceUtil.mc.player.posY + RayTraceUtil.mc.player.getEyeHeight()) {
            return EnumFacing.DOWN;
        }
        return EnumFacing.UP;
    }
}
