//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;

public class PlacementUtil
{
    private static final Minecraft mc;
    private static int placementConnections;
    private static boolean isSneaking;
    
    public static void onEnable() {
        ++PlacementUtil.placementConnections;
    }
    
    public static void stopSneaking() {
        if (PlacementUtil.isSneaking) {
            PlacementUtil.isSneaking = false;
            PlacementUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlacementUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    public static void onDisable() {
        --PlacementUtil.placementConnections;
        if (PlacementUtil.placementConnections == 0 && PlacementUtil.isSneaking) {
            PlacementUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlacementUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            PlacementUtil.isSneaking = false;
        }
    }
    
    public static boolean placeBlock(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final Class<? extends Block> blockToPlace) {
        final int oldSlot = PlacementUtil.mc.player.inventory.currentItem;
        final int newSlot = InventoryUtil.findFirstBlockSlot((Class)blockToPlace, 0, 8);
        if (newSlot == -1) {
            return false;
        }
        PlacementUtil.mc.player.inventory.currentItem = newSlot;
        final boolean output = place(blockPos, hand, rotate);
        PlacementUtil.mc.player.inventory.currentItem = oldSlot;
        return output;
    }
    
    public static boolean placeBlockSilent(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final Class<? extends Block> blockToPlace) {
        final int oldSlot = PlacementUtil.mc.player.inventory.currentItem;
        final int newSlot = InventoryUtil.findFirstBlockSlot((Class)blockToPlace, 0, 8);
        if (newSlot == -1) {
            return false;
        }
        PlacementUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(newSlot));
        final boolean output = place(blockPos, hand, rotate);
        PlacementUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldSlot));
        return output;
    }
    
    public static boolean placeItem(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final Class<? extends Item> itemToPlace) {
        final int oldSlot = PlacementUtil.mc.player.inventory.currentItem;
        final int newSlot = InventoryUtil.findFirstItemSlot((Class)itemToPlace, 0, 8);
        if (newSlot == -1) {
            return false;
        }
        PlacementUtil.mc.player.inventory.currentItem = newSlot;
        final boolean output = place(blockPos, hand, rotate);
        PlacementUtil.mc.player.inventory.currentItem = oldSlot;
        return output;
    }
    
    public static boolean place(final BlockPos blockPos, final EnumHand hand, final boolean rotate) {
        return placeBlock(blockPos, hand, rotate, true, null, true);
    }
    
    public static boolean place(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final ArrayList<EnumFacing> forceSide) {
        return placeBlock(blockPos, hand, rotate, true, forceSide, true);
    }
    
    public static boolean place(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final boolean checkAction) {
        return placeBlock(blockPos, hand, rotate, checkAction, null, true);
    }
    
    public static boolean place(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final boolean checkAction, final boolean swingArm) {
        return placeBlock(blockPos, hand, rotate, checkAction, null, swingArm);
    }
    
    public static EnumFacing getPlaceableSideExlude(final BlockPos pos, final ArrayList<EnumFacing> excluding) {
        for (final EnumFacing side : EnumFacing.values()) {
            if (!excluding.contains(side)) {
                final BlockPos neighbour = pos.offset(side);
                if (PlacementUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(PlacementUtil.mc.world.getBlockState(neighbour), false)) {
                    final IBlockState blockState = PlacementUtil.mc.world.getBlockState(neighbour);
                    if (!blockState.getMaterial().isReplaceable()) {
                        return side;
                    }
                }
            }
        }
        return null;
    }
    
    public static boolean placeBlock(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final boolean checkAction, final ArrayList<EnumFacing> forceSide, final boolean swingArm) {
        final EntityPlayerSP player = PlacementUtil.mc.player;
        final WorldClient world = PlacementUtil.mc.world;
        final PlayerControllerMP playerController = PlacementUtil.mc.playerController;
        if (player == null || world == null || playerController == null) {
            return false;
        }
        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }
        final EnumFacing side = (forceSide != null) ? getPlaceableSideExlude(blockPos, forceSide) : BlockUtil.getPlaceableSide(blockPos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = blockPos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = world.getBlockState(neighbour).getBlock();
        if ((!PlacementUtil.isSneaking && BlockUtil.blackList.contains(neighbourBlock)) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.START_SNEAKING));
            PlacementUtil.isSneaking = true;
        }
        final boolean stoppedAC = false;
        if (rotate) {
            SilentRotaionUtil.lookAtVector(hitVec);
        }
        final EnumActionResult action = playerController.processRightClickBlock(player, world, neighbour, opposite, hitVec, hand);
        if (!checkAction || action == EnumActionResult.SUCCESS) {
            if (swingArm) {
                player.swingArm(hand);
                PlacementUtil.mc.rightClickDelayTimer = 4;
            }
            else {
                player.connection.sendPacket((Packet)new CPacketAnimation(hand));
            }
        }
        return action == EnumActionResult.SUCCESS;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(PlacementUtil.mc.player.posX, PlacementUtil.mc.player.posY + PlacementUtil.mc.player.getEyeHeight(), PlacementUtil.mc.player.posZ);
    }
    
    public static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { PlacementUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - PlacementUtil.mc.player.rotationYaw), PlacementUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - PlacementUtil.mc.player.rotationPitch) };
    }
    
    public static CPacketPlayer.Rotation faceVectorPacketInstant(final Vec3d vec, final Boolean roundAngles) {
        final float[] rotations = getNeededRotations2(vec);
        final CPacketPlayer.Rotation e = new CPacketPlayer.Rotation(rotations[0], ((boolean)roundAngles) ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], PlacementUtil.mc.player.onGround);
        PlacementUtil.mc.player.connection.sendPacket((Packet)e);
        return e;
    }
    
    public static CPacketPlayer.Rotation placeBlockGetRotate(final BlockPos blockPos, final EnumHand hand, final boolean checkAction, final ArrayList<EnumFacing> forceSide, final boolean swingArm) {
        final EntityPlayerSP player = PlacementUtil.mc.player;
        final WorldClient world = PlacementUtil.mc.world;
        final PlayerControllerMP playerController = PlacementUtil.mc.playerController;
        if (player == null || world == null || playerController == null) {
            return null;
        }
        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return null;
        }
        final EnumFacing side = (forceSide != null) ? getPlaceableSideExlude(blockPos, forceSide) : BlockUtil.getPlaceableSide(blockPos);
        if (side == null) {
            return null;
        }
        final BlockPos neighbour = blockPos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return null;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = world.getBlockState(neighbour).getBlock();
        if ((!PlacementUtil.isSneaking && BlockUtil.blackList.contains(neighbourBlock)) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.START_SNEAKING));
            PlacementUtil.isSneaking = true;
        }
        final boolean stoppedAC = false;
        final EnumActionResult action = playerController.processRightClickBlock(player, world, neighbour, opposite, hitVec, hand);
        if (!checkAction || action == EnumActionResult.SUCCESS) {
            if (swingArm) {
                player.swingArm(hand);
                PlacementUtil.mc.rightClickDelayTimer = 4;
            }
            else {
                player.connection.sendPacket((Packet)new CPacketAnimation(hand));
            }
        }
        return getFaceVectorPacket(hitVec, true);
    }
    
    public static CPacketPlayer.Rotation getFaceVectorPacket(final Vec3d vec, final Boolean roundAngles) {
        final float[] rotations = getNeededRotations2(vec);
        final CPacketPlayer.Rotation e = new CPacketPlayer.Rotation(rotations[0], ((boolean)roundAngles) ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], PlacementUtil.mc.player.onGround);
        PlacementUtil.mc.player.connection.sendPacket((Packet)e);
        return e;
    }
    
    public static boolean placePrecise(final BlockPos blockPos, final EnumHand hand, final boolean rotate, final Vec3d precise, final EnumFacing forceSide, final boolean onlyRotation, final boolean support) {
        final EntityPlayerSP player = PlacementUtil.mc.player;
        final WorldClient world = PlacementUtil.mc.world;
        final PlayerControllerMP playerController = PlacementUtil.mc.playerController;
        if (player == null || world == null || playerController == null) {
            return false;
        }
        if (!world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            return false;
        }
        final EnumFacing side = (forceSide == null) ? BlockUtil.getPlaceableSide(blockPos) : forceSide;
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = blockPos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = world.getBlockState(neighbour).getBlock();
        if ((!PlacementUtil.isSneaking && BlockUtil.blackList.contains(neighbourBlock)) || BlockUtil.shulkerList.contains(neighbourBlock)) {
            player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)player, CPacketEntityAction.Action.START_SNEAKING));
            PlacementUtil.isSneaking = true;
        }
        final boolean stoppedAC = false;
        if (rotate && !support) {
            faceVectorPacketInstant((precise == null) ? hitVec : precise, true);
        }
        if (!onlyRotation) {
            final EnumActionResult action = playerController.processRightClickBlock(player, world, neighbour, opposite, (precise == null) ? hitVec : precise, hand);
            if (action == EnumActionResult.SUCCESS) {
                player.swingArm(hand);
                PlacementUtil.mc.rightClickDelayTimer = 4;
            }
            return action == EnumActionResult.SUCCESS;
        }
        return true;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        PlacementUtil.placementConnections = 0;
        PlacementUtil.isSneaking = false;
    }
}
