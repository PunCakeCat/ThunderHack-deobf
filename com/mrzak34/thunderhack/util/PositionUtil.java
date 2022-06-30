//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class PositionUtil
{
    public static BlockPos getPosition() {
        return getPosition((Entity)getRotationPlayer());
    }
    
    public static EntityPlayer getRotationPlayer() {
        final EntityPlayer rotationEntity = (EntityPlayer)Util.mc.player;
        return (EntityPlayer)((rotationEntity == null) ? Util.mc.player : rotationEntity);
    }
    
    public static BlockPos getPosition(final Entity entity) {
        return getPosition(entity, 0.0);
    }
    
    public static BlockPos getPosition(final Entity entity, final double yOffset) {
        double y = entity.posY + yOffset;
        if (entity.posY - Math.floor(entity.posY) > 0.5) {
            y = Math.ceil(entity.posY);
        }
        return new BlockPos(entity.posX, y, entity.posZ);
    }
    
    public static Vec3d getEyePos() {
        return getEyePos((Entity)Util.mc.player);
    }
    
    public static Vec3d getEyePos(final Entity entity) {
        return new Vec3d(entity.posX, getEyeHeight(entity), entity.posZ);
    }
    
    public static double getEyeHeight() {
        return getEyeHeight((Entity)Util.mc.player);
    }
    
    public static double getEyeHeight(final Entity entity) {
        return entity.posY + entity.getEyeHeight();
    }
    
    public static Set<BlockPos> getBlockedPositions(final Entity entity) {
        return getBlockedPositions(entity.getEntityBoundingBox());
    }
    
    public static Set<BlockPos> getBlockedPositions(final AxisAlignedBB bb) {
        return getBlockedPositions(bb, 0.5);
    }
    
    public static Set<BlockPos> getBlockedPositions(final AxisAlignedBB bb, final double offset) {
        final Set<BlockPos> positions = new HashSet<BlockPos>();
        double y = bb.minY;
        if (bb.minY - Math.floor(bb.minY) > offset) {
            y = Math.ceil(bb.minY);
        }
        positions.add(new BlockPos(bb.maxX, y, bb.maxZ));
        positions.add(new BlockPos(bb.minX, y, bb.minZ));
        positions.add(new BlockPos(bb.maxX, y, bb.minZ));
        positions.add(new BlockPos(bb.minX, y, bb.maxZ));
        return positions;
    }
    
    public static boolean isBoxColliding() {
        return Util.mc.world.getCollisionBoxes((Entity)Util.mc.player, Util.mc.player.getEntityBoundingBox().offset(0.0, 0.21, 0.0)).size() > 0;
    }
    
    public static boolean inLiquid() {
        return inLiquid(MathHelper.floor(Util.mc.player.getEntityBoundingBox().minY + 0.01));
    }
    
    public static boolean inLiquid(final boolean feet) {
        return inLiquid(MathHelper.floor(Util.mc.player.getEntityBoundingBox().minY - (feet ? 0.03 : 0.2)));
    }
    
    private static boolean inLiquid(final int y) {
        return findState((Class<? extends Block>)BlockLiquid.class, y) != null;
    }
    
    private static IBlockState findState(final Class<? extends Block> block, final int y) {
        final int startX = MathHelper.floor(Util.mc.player.getEntityBoundingBox().minX);
        final int startZ = MathHelper.floor(Util.mc.player.getEntityBoundingBox().minZ);
        final int endX = MathHelper.ceil(Util.mc.player.getEntityBoundingBox().maxX);
        final int endZ = MathHelper.ceil(Util.mc.player.getEntityBoundingBox().maxZ);
        for (int x = startX; x < endX; ++x) {
            for (int z = startZ; z < endZ; ++z) {
                final IBlockState s = Util.mc.world.getBlockState(new BlockPos(x, y, z));
                if (block.isInstance(s.getBlock())) {
                    return s;
                }
            }
        }
        return null;
    }
    
    public static boolean isMovementBlocked() {
        final IBlockState state = findState(Block.class, MathHelper.floor(Util.mc.player.getEntityBoundingBox().minY - 0.01));
        return state != null && state.getMaterial().blocksMovement();
    }
    
    public static boolean isAbove(final BlockPos pos) {
        return Util.mc.player.getEntityBoundingBox().minY >= pos.getY();
    }
    
    public static BlockPos fromBB(final AxisAlignedBB bb) {
        return new BlockPos((bb.minX + bb.maxX) / 2.0, (bb.minY + bb.maxY) / 2.0, (bb.minZ + bb.maxZ) / 2.0);
    }
    
    public static AxisAlignedBB serverBB(final Entity entity) {
        return newBB(entity.serverPosX / 4096.0, entity.serverPosY / 4096.0, entity.serverPosZ / 4096.0, entity.width / 2.0f, entity.height);
    }
    
    public static AxisAlignedBB newBB(final double x, final double y, final double z, final double w, final double h) {
        return new AxisAlignedBB(x - w, y, z - w, x + w, y + h, z + w);
    }
    
    public static boolean intersects(final AxisAlignedBB bb, final BlockPos pos) {
        return bb.intersects((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1));
    }
}
