//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import java.util.function.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;

public class BlockUtil2
{
    public static boolean canPlaceCrystal(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2) {
        return canPlaceCrystal(pos, ignoreCrystals, noBoost2, null);
    }
    
    public static double getDistanceSq(final BlockPos pos) {
        return getDistanceSq((Entity)PositionUtil.getRotationPlayer(), pos);
    }
    
    public static boolean isBlocking(final BlockPos pos, final EntityPlayer player, final BlockingType type) {
        final AxisAlignedBB posBB = new AxisAlignedBB(pos);
        if (type == BlockingType.Strict || type == BlockingType.Crystals) {
            return player.getEntityBoundingBox().intersects(posBB);
        }
        if (type == BlockingType.PacketFly) {
            return player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625).intersects(posBB);
        }
        if (type == BlockingType.Full && player.getEntityBoundingBox().expand(-0.0625, -0.0625, -0.0625).intersects(posBB)) {
            return true;
        }
        AxisAlignedBB bb = player.getEntityBoundingBox();
        if (type == BlockingType.All) {
            bb = bb.expand(-0.0625, -0.0625, -0.0625);
        }
        if (type != BlockingType.NoPacketFly || !bb.intersects(posBB)) {
            return false;
        }
        final BlockPos playerPos = new BlockPos((Entity)player);
        if (playerPos.getX() == pos.getX() && playerPos.getZ() == pos.getZ()) {
            return true;
        }
        if (playerPos.getY() < pos.getY()) {
            return Util.mc.world.getBlockState(pos.down()).getMaterial().isReplaceable();
        }
        return Util.mc.world.getBlockState(pos.up()).getMaterial().isReplaceable();
    }
    
    public static double getDistanceSq(final Entity from, final BlockPos to) {
        return from.getDistanceSqToCenter(to);
    }
    
    public static double getDistanceSqDigging(final BlockPos to) {
        return getDistanceSqDigging((Entity)PositionUtil.getRotationPlayer(), to);
    }
    
    public static boolean sphere(final double radius, final Predicate<BlockPos> predicate) {
        return sphere(PositionUtil.getPosition(), radius, predicate);
    }
    
    public static boolean sphere(final BlockPos pos, final double r, final Predicate<BlockPos> predicate) {
        int tested = 0;
        final double rSquare = r * r;
        for (int x = pos.getX() - (int)r; x <= pos.getX() + r; ++x) {
            for (int z = pos.getZ() - (int)r; z <= pos.getZ() + r; ++z) {
                for (int y = pos.getY() - (int)r; y < pos.getY() + r; ++y) {
                    final double dist = (pos.getX() - x) * (pos.getX() - x) + (pos.getZ() - z) * (pos.getZ() - z) + (pos.getY() - y) * (pos.getY() - y);
                    if (dist < rSquare && tested++ > 0 && predicate.test(new BlockPos(x, y, z))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static double getDistanceSqDigging(final Entity from, final BlockPos to) {
        final double x = from.posX - (to.getX() + 0.5);
        final double y = from.posY - (to.getY() + 0.5) + 1.5;
        final double z = from.posZ - (to.getZ() + 0.5);
        return x * x + y * y + z * z;
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities) {
        return canPlaceCrystal(pos, ignoreCrystals, noBoost2, entities, noBoost2, 0L);
    }
    
    public static boolean canPlaceCrystal(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities, final boolean ignoreBoost2Entities, final long deathTime) {
        final IBlockState state = Util.mc.world.getBlockState(pos);
        return (state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK) && checkBoost(pos, ignoreCrystals, noBoost2, entities, ignoreBoost2Entities, deathTime);
    }
    
    public static boolean canPlaceCrystalReplaceable(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities, final boolean ignoreBoost2Entities, final long deathTime) {
        final IBlockState state = Util.mc.world.getBlockState(pos);
        return (state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK || state.getMaterial().isReplaceable()) && checkBoost(pos, ignoreCrystals, noBoost2, entities, ignoreBoost2Entities, deathTime);
    }
    
    public static boolean checkBoost(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2, final List<Entity> entities, final boolean ignoreBoost2Entities, final long deathTime) {
        final BlockPos boost = pos.up();
        if (Util.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || !checkEntityList(boost, ignoreCrystals, entities, deathTime)) {
            return false;
        }
        if (!noBoost2) {
            final BlockPos boost2 = boost.up();
            return Util.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && (ignoreBoost2Entities || checkEntityList(boost2, ignoreCrystals, entities, deathTime));
        }
        return true;
    }
    
    public static boolean isSemiSafe(final EntityPlayer player, final boolean ignoreCrystals, final boolean noBoost2) {
        final BlockPos origin = PositionUtil.getPosition((Entity)player);
        int i = 0;
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            final BlockPos off = origin.offset(face);
            if (Util.mc.world.getBlockState(off).getBlock() != Blocks.AIR) {
                ++i;
            }
        }
        return i >= 3;
    }
    
    public static boolean canBeFeetPlaced(final EntityPlayer player, final boolean ignoreCrystals, final boolean noBoost2) {
        final BlockPos origin = PositionUtil.getPosition((Entity)player).down();
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            final BlockPos off = origin.offset(face);
            final IBlockState state = Util.mc.world.getBlockState(off);
            if (canPlaceCrystal(off, ignoreCrystals, noBoost2)) {
                return true;
            }
            final BlockPos off2 = off.offset(face);
            if (canPlaceCrystal(off2, ignoreCrystals, noBoost2) && state.getBlock() == Blocks.AIR) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean canPlaceCrystalFuture(final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2) {
        final IBlockState state = Util.mc.world.getBlockState(pos);
        if (state.getBlock() != Blocks.OBSIDIAN && state.getBlock() != Blocks.BEDROCK) {
            return false;
        }
        final BlockPos boost = pos.up();
        if (!checkEntityList(boost, ignoreCrystals, null)) {
            return false;
        }
        if (Util.mc.world.getBlockState(boost).getBlock() == Blocks.BEDROCK) {
            return false;
        }
        if (!noBoost2) {
            final BlockPos boost2 = boost.up();
            return Util.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && checkEntityList(boost2, ignoreCrystals, null);
        }
        return true;
    }
    
    public static boolean isAtFeet(final EntityPlayer player, final BlockPos pos, final boolean ignoreCrystals, final boolean noBoost2) {
        final BlockPos up = pos.up();
        if (!canPlaceCrystal(pos, ignoreCrystals, noBoost2)) {
            return false;
        }
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            final BlockPos off = up.offset(face);
            final IBlockState state = Util.mc.world.getBlockState(off);
            if (Util.mc.world.getEntitiesWithinAABB((Class)EntityPlayer.class, new AxisAlignedBB(off)).contains(player)) {
                return true;
            }
            final BlockPos off2 = off.offset(face);
            final IBlockState offState = Util.mc.world.getBlockState(off2);
            if (Util.mc.world.getEntitiesWithinAABB((Class)EntityPlayer.class, new AxisAlignedBB(off2)).contains(player)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean canPlaceBed(final BlockPos pos, final boolean newerVer) {
        if (!bedBlockCheck(pos, newerVer)) {
            return false;
        }
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos horizontal = pos.offset(facing);
            if (bedBlockCheck(horizontal, newerVer) && getFacing(horizontal) != null) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean checkEntityList(final BlockPos pos, final boolean ignoreCrystals, final List<Entity> entities) {
        return checkEntityList(pos, ignoreCrystals, entities, 0L);
    }
    
    public static boolean checkEntityList(final BlockPos pos, final boolean ignoreCrystals, final List<Entity> entities, final long deathTime) {
        if (entities == null) {
            return checkEntities(pos, ignoreCrystals, deathTime);
        }
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        for (final Entity entity : entities) {
            if (checkEntity(entity, ignoreCrystals, deathTime) && entity.getEntityBoundingBox().intersects(bb)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAir(final BlockPos pos) {
        return Util.mc.world.getBlockState(pos).getBlock() == Blocks.AIR;
    }
    
    public static boolean checkEntities(final BlockPos pos, final boolean ignoreCrystals, final long deathTime) {
        for (final Entity entity : Util.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (checkEntity(entity, ignoreCrystals, deathTime)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean checkEntity(final Entity entity, final boolean ignoreCrystals, final long deathTime) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof EntityEnderCrystal) {
            return !ignoreCrystals && !entity.isDead;
        }
        return !EntityUtil.isDead(entity);
    }
    
    public static EnumFacing getFacing(final BlockPos pos) {
        return getFacing(pos, (IBlockAccess)Util.mc.world);
    }
    
    public static EnumFacing getFacing(final BlockPos pos, final IBlockAccess provider) {
        for (final EnumFacing facing : EnumFacing.values()) {
            if (!provider.getBlockState(pos.offset(facing)).getMaterial().isReplaceable()) {
                return facing;
            }
        }
        return null;
    }
    
    public static boolean isReplaceable(final BlockPos pos) {
        return Util.mc.world.getBlockState(pos).getMaterial().isReplaceable();
    }
    
    private static boolean bedBlockCheck(final BlockPos pos, final boolean newerVer) {
        return Util.mc.world.getBlockState(pos).getMaterial().isReplaceable() && (newerVer || !Util.mc.world.getBlockState(pos.down()).getMaterial().isReplaceable());
    }
    
    public enum BlockingType
    {
        Strict, 
        PacketFly, 
        NoPacketFly, 
        All, 
        Full, 
        Crystals;
    }
}
