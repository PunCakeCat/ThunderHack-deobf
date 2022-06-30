//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;

public class InteractionUtil
{
    private static Minecraft mc;
    
    public static boolean canPlaceNormally() {
        return true;
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate) {
        return preparePlacement(pos, rotate, false);
    }
    
    public static boolean canPlaceNormally(final boolean rotate) {
        return rotate || true;
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant) {
        return preparePlacement(pos, rotate, instant, false);
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant, final boolean strictDirection) {
        return preparePlacement(pos, rotate, instant, strictDirection, false);
    }
    
    public static Placement preparePlacement(final BlockPos pos, final boolean rotate, final boolean instant, final boolean strictDirection, final boolean rayTrace) {
        EnumFacing side = null;
        Vec3d hitVec = null;
        final double dist = 69420.0;
        for (final EnumFacing facing : getPlacableFacings(pos, strictDirection, rayTrace)) {
            final BlockPos tempNeighbour = pos.offset(facing);
            final Vec3d tempVec = new Vec3d((Vec3i)tempNeighbour).add(0.5, 0.5, 0.5).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
            if (InteractionUtil.mc.player.getPositionVector().add(0.0, (double)InteractionUtil.mc.player.getEyeHeight(), 0.0).distanceTo(tempVec) < dist) {
                side = facing;
                hitVec = tempVec;
            }
        }
        if (side == null) {
            return null;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final float[] angle = RotationManager.calculateAngle(InteractionUtil.mc.player.getPositionEyes(InteractionUtil.mc.getRenderPartialTicks()), hitVec);
        if (rotate) {
            if (instant) {
                InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], InteractionUtil.mc.player.onGround));
                ((IEntityPlayerSP)InteractionUtil.mc.player).setLastReportedYaw(angle[0]);
                ((IEntityPlayerSP)InteractionUtil.mc.player).setLastReportedPitch(angle[1]);
            }
            else {
                Thunderhack.rotationManager.setPlayerRotations(angle[0], angle[1]);
            }
        }
        return new Placement(neighbour, opposite, hitVec, angle[0], angle[1]);
    }
    
    public static void placeBlockSafely(final Placement placement, final EnumHand hand, final boolean packet) {
        final boolean isSprinting = InteractionUtil.mc.player.isSprinting();
        final boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(placement.getNeighbour());
        if (isSprinting) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        if (shouldSneak) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        placeBlock(placement, hand, packet);
        if (shouldSneak) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (isSprinting) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)InteractionUtil.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
    
    public static void placeBlock(final Placement placement, final EnumHand hand, final boolean packet) {
        rightClickBlock(placement.getNeighbour(), placement.getHitVec(), hand, placement.getOpposite(), packet, true);
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet, final boolean swing) {
        if (packet) {
            final float dX = (float)(vec.x - pos.getX());
            final float dY = (float)(vec.y - pos.getY());
            final float dZ = (float)(vec.z - pos.getZ());
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, dX, dY, dZ));
        }
        else {
            InteractionUtil.mc.playerController.processRightClickBlock(InteractionUtil.mc.player, InteractionUtil.mc.world, pos, direction, vec, hand);
        }
        if (swing) {
            InteractionUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
        }
    }
    
    public static boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection) {
        return canPlaceBlock(pos, strictDirection, true);
    }
    
    public static boolean canRayTrace(final BlockPos pos) {
        return InteractionUtil.mc.world.rayTraceBlocks(new Vec3d(InteractionUtil.mc.player.posX, InteractionUtil.mc.player.posY + InteractionUtil.mc.player.getEyeHeight(), InteractionUtil.mc.player.posZ), new Vec3d((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection, final boolean checkEntities) {
        return canPlaceBlock(pos, strictDirection, false, checkEntities);
    }
    
    public static boolean canPlaceBlock(final BlockPos pos, final boolean strictDirection, final boolean rayTrace, final boolean checkEntities) {
        final Block block = InteractionUtil.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return false;
        }
        if (checkEntities) {
            for (final Entity entity : InteractionUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                if (!(entity instanceof EntityItem)) {
                    if (entity instanceof EntityXPOrb) {
                        continue;
                    }
                    return false;
                }
            }
        }
        for (final EnumFacing side : getPlacableFacings(pos, strictDirection, rayTrace)) {
            if (!canClick(pos.offset(side))) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public static boolean canClick(final BlockPos pos) {
        return InteractionUtil.mc.world.getBlockState(pos).getBlock().canCollideCheck(InteractionUtil.mc.world.getBlockState(pos), false);
    }
    
    public static List<EnumFacing> getPlacableFacings(final BlockPos pos, final boolean strictDirection, final boolean rayTrace) {
        final ArrayList<EnumFacing> validFacings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            Label_0420: {
                if (rayTrace) {
                    final Vec3d testVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5).add(new Vec3d(side.getDirectionVec()).scale(0.5));
                    final RayTraceResult result = InteractionUtil.mc.world.rayTraceBlocks(InteractionUtil.mc.player.getPositionEyes(1.0f), testVec);
                    if (result != null && result.typeOfHit != RayTraceResult.Type.MISS) {
                        System.out.println("weary");
                        break Label_0420;
                    }
                }
                final BlockPos neighbour = pos.offset(side);
                if (strictDirection) {
                    final Vec3d eyePos = InteractionUtil.mc.player.getPositionEyes(1.0f);
                    final Vec3d blockCenter = new Vec3d(neighbour.getX() + 0.5, neighbour.getY() + 0.5, neighbour.getZ() + 0.5);
                    final IBlockState blockState = InteractionUtil.mc.world.getBlockState(neighbour);
                    final boolean isFullBox = blockState.getBlock() == Blocks.AIR || blockState.isFullBlock();
                    final ArrayList<EnumFacing> validAxis = new ArrayList<EnumFacing>();
                    validAxis.addAll(checkAxis(eyePos.x - blockCenter.x, EnumFacing.WEST, EnumFacing.EAST, !isFullBox));
                    validAxis.addAll(checkAxis(eyePos.y - blockCenter.y, EnumFacing.DOWN, EnumFacing.UP, true));
                    validAxis.addAll(checkAxis(eyePos.z - blockCenter.z, EnumFacing.NORTH, EnumFacing.SOUTH, !isFullBox));
                    if (!validAxis.contains(side.getOpposite())) {
                        break Label_0420;
                    }
                }
                final IBlockState blockState2 = InteractionUtil.mc.world.getBlockState(neighbour);
                if (blockState2 != null && blockState2.getBlock().canCollideCheck(blockState2, false)) {
                    if (!blockState2.getMaterial().isReplaceable()) {
                        validFacings.add(side);
                    }
                }
            }
        }
        return validFacings;
    }
    
    public static ArrayList<EnumFacing> checkAxis(final double diff, final EnumFacing negativeSide, final EnumFacing positiveSide, final boolean bothIfInRange) {
        final ArrayList<EnumFacing> valid = new ArrayList<EnumFacing>();
        if (diff < -0.5) {
            valid.add(negativeSide);
        }
        if (diff > 0.5) {
            valid.add(positiveSide);
        }
        if (bothIfInRange) {
            if (!valid.contains(negativeSide)) {
                valid.add(negativeSide);
            }
            if (!valid.contains(positiveSide)) {
                valid.add(positiveSide);
            }
        }
        return valid;
    }
    
    static {
        InteractionUtil.mc = Minecraft.getMinecraft();
    }
    
    public static class Placement
    {
        private final BlockPos neighbour;
        private final EnumFacing opposite;
        private final Vec3d hitVec;
        private final float yaw;
        private final float pitch;
        
        public Placement(final BlockPos neighbour, final EnumFacing opposite, final Vec3d hitVec, final float yaw, final float pitch) {
            this.neighbour = neighbour;
            this.opposite = opposite;
            this.hitVec = hitVec;
            this.yaw = yaw;
            this.pitch = pitch;
        }
        
        public BlockPos getNeighbour() {
            return this.neighbour;
        }
        
        public EnumFacing getOpposite() {
            return this.opposite;
        }
        
        public Vec3d getHitVec() {
            return this.hitVec;
        }
        
        public float getYaw() {
            return this.yaw;
        }
        
        public float getPitch() {
            return this.pitch;
        }
    }
}
