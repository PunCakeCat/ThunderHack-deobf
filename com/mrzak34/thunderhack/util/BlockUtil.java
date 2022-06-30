//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.entity.player.*;
import java.util.stream.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.atomic.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import java.util.*;

public class BlockUtil implements Util
{
    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    public static final List<Block> unSafeBlocks;
    public static List<Block> unSolidBlocks;
    
    public static List<BlockPos> getBlockSphere(final float breakRange, final Class clazz) {
        final NonNullList positions = NonNullList.create();
        positions.addAll((Collection)getSphere(EntityUtil.getPlayerPos((EntityPlayer)BlockUtil.mc.player), breakRange, (int)breakRange, false, true, 0).stream().filter(pos -> clazz.isInstance(BlockUtil.mc.world.getBlockState(pos).getBlock())).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public static boolean isAir(final BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos).getBlock() == Blocks.AIR;
    }
    
    public static CPacketPlayer.Rotation faceVectorPacketInstant(final Vec3d vec, final Boolean roundAngles) {
        final float[] rotations = getNeededRotations2(vec);
        final CPacketPlayer.Rotation e = new CPacketPlayer.Rotation(rotations[0], ((boolean)roundAngles) ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], BlockUtil.mc.player.onGround);
        BlockUtil.mc.player.connection.sendPacket((Packet)e);
        return e;
    }
    
    public static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { BlockUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BlockUtil.mc.player.rotationYaw), BlockUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BlockUtil.mc.player.rotationPitch) };
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState;
                if (!(blockState = BlockUtil.mc.world.getBlockState(neighbour)).getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ);
    }
    
    public static Block getBlock(final double x, final double y, final double z) {
        return BlockUtil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static ArrayList<BlockPos> getAllInBox(final BlockPos from, final BlockPos to) {
        final ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
        final BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        for (int x = min.getX(); x <= max.getX(); ++x) {
            for (int y = min.getY(); y <= max.getY(); ++y) {
                for (int z = min.getZ(); z <= max.getZ(); ++z) {
                    blocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return blocks;
    }
    
    public static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = BlockUtil.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    public static EnumFacing getFacing(final BlockPos pos) {
        for (final EnumFacing facing : EnumFacing.values()) {
            final RayTraceResult rayTraceResult = BlockUtil.mc.world.rayTraceBlocks(new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ), new Vec3d(pos.getX() + 0.5 + facing.getDirectionVec().getX() * 1.0 / 2.0, pos.getY() + 0.5 + facing.getDirectionVec().getY() * 1.0 / 2.0, pos.getZ() + 0.5 + facing.getDirectionVec().getZ() * 1.0 / 2.0), false, true, false);
            if (rayTraceResult == null || (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals((Object)pos))) {
                return facing;
            }
        }
        if (pos.getY() > BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight()) {
            return EnumFacing.DOWN;
        }
        return EnumFacing.UP;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static EnumFacing getRayTraceFacing(final BlockPos pos) {
        final RayTraceResult result = BlockUtil.mc.world.rayTraceBlocks(new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getX() - 0.5, pos.getX() + 0.5));
        if (result == null || result.sideHit == null) {
            return EnumFacing.UP;
        }
        return result.sideHit;
    }
    
    public static int isPositionPlaceable(final BlockPos pos, final boolean rayTrace) {
        return isPositionPlaceable(pos, rayTrace, true);
    }
    
    public static int isPositionPlaceable(final BlockPos pos, final boolean rayTrace, final boolean entityCheck) {
        final Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return 0;
        }
        if (!rayTracePlaceCheck(pos, rayTrace, 0.0f)) {
            return -1;
        }
        for (final EnumFacing side : getPossibleSides(pos)) {
            if (!canBeClicked(pos.offset(side))) {
                continue;
            }
            return 3;
        }
        return 2;
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            BlockUtil.mc.playerController.processRightClickBlock(BlockUtil.mc.player, BlockUtil.mc.world, pos, direction, vec, hand);
        }
        BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        BlockUtil.mc.rightClickDelayTimer = 4;
    }
    
    public static void rightClickBlockLegit(final BlockPos pos, final float range, final boolean rotate, final EnumHand hand, final AtomicDouble Yaw, final AtomicDouble Pitch, final AtomicBoolean rotating) {
        final Vec3d eyesPos = RotationUtil.getEyesPos();
        final Vec3d posVec = new Vec3d((Vec3i)pos).add(0.5, 0.5, 0.5);
        final double distanceSqPosVec = eyesPos.squareDistanceTo(posVec);
        for (final EnumFacing side : EnumFacing.values()) {
            final Vec3d hitVec = posVec.add(new Vec3d(side.getDirectionVec()).scale(0.5));
            final double distanceSqHitVec = eyesPos.squareDistanceTo(hitVec);
            if (distanceSqHitVec <= MathUtil.square(range) && distanceSqHitVec < distanceSqPosVec && BlockUtil.mc.world.rayTraceBlocks(eyesPos, hitVec, false, true, false) == null) {
                if (rotate) {
                    final float[] rotations = RotationUtil.getLegitRotations(hitVec);
                    Yaw.set((double)rotations[0]);
                    Pitch.set((double)rotations[1]);
                    rotating.set(true);
                }
                BlockUtil.mc.playerController.processRightClickBlock(BlockUtil.mc.player, BlockUtil.mc.world, pos, side, hitVec, hand);
                BlockUtil.mc.player.swingArm(hand);
                BlockUtil.mc.rightClickDelayTimer = 4;
                break;
            }
        }
    }
    
    public static boolean placeBlock(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking) {
        boolean sneaking = false;
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = BlockUtil.mc.world.getBlockState(neighbour).getBlock();
        if (!BlockUtil.mc.player.isSneaking() && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtil.mc.player.setSneaking(true);
            sneaking = true;
        }
        if (rotate) {
            RotationUtil.faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        BlockUtil.mc.rightClickDelayTimer = 4;
        return sneaking || isSneaking;
    }
    
    public static boolean placeBlockSmartRotate(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking) {
        boolean sneaking = false;
        final EnumFacing side = getFirstFacing(pos);
        Command.sendMessage(side.toString());
        if (side == null) {
            return isSneaking;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = BlockUtil.mc.world.getBlockState(neighbour).getBlock();
        if (!BlockUtil.mc.player.isSneaking() && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            sneaking = true;
        }
        if (rotate) {
            Thunderhack.rotationManager.lookAtVec3d(hitVec);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        BlockUtil.mc.rightClickDelayTimer = 4;
        return sneaking || isSneaking;
    }
    
    public static void placeBlockStopSneaking(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking) {
        final boolean sneaking = placeBlockSmartRotate(pos, hand, rotate, packet, isSneaking);
        if (!isSneaking && sneaking) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    public static Vec3d[] getHelpingBlocks(final Vec3d vec3d) {
        return new Vec3d[] { new Vec3d(vec3d.x, vec3d.y - 1.0, vec3d.z), new Vec3d((vec3d.x != 0.0) ? (vec3d.x * 2.0) : vec3d.x, vec3d.y, (vec3d.x != 0.0) ? vec3d.z : (vec3d.z * 2.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x + 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z + 1.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x - 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z - 1.0)), new Vec3d(vec3d.x, vec3d.y + 1.0, vec3d.z) };
    }
    
    public static List<BlockPos> possiblePlacePositions(final float placeRange) {
        final NonNullList positions = NonNullList.create();
        positions.addAll((Collection)getSphere(EntityUtil.getPlayerPos((EntityPlayer)BlockUtil.mc.player), placeRange, (int)placeRange, false, true, 0).stream().filter((Predicate<? super Object>)BlockUtil::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public static List<BlockPos> getSphere(final float radius, final boolean ignoreAir) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(Util.mc.player.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    final double dist = (posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y);
                    final BlockPos position;
                    if (dist < radius * radius && (Util.mc.world.getBlockState(position = new BlockPos(x, y, z)).getBlock() != Blocks.AIR || !ignoreAir)) {
                        sphere.add(position);
                    }
                }
            }
        }
        return sphere;
    }
    
    public static List<BlockPos> getSphere(final BlockPos pos, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = pos.getX();
        final int cy = pos.getY();
        final int cz = pos.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = (float)y;
                    final float f2 = sphere ? (cy + r) : ((float)(cy + h));
                    if (f >= f2) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        try {
            return (BlockUtil.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || BlockUtil.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && BlockUtil.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && BlockUtil.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static List<BlockPos> possiblePlacePositions(final float placeRange, final boolean specialEntityCheck) {
        final NonNullList positions = NonNullList.create();
        positions.addAll((Collection)getSphere(EntityUtil.getPlayerPos((EntityPlayer)BlockUtil.mc.player), placeRange, (int)placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, specialEntityCheck)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean specialEntityCheck) {
        return true;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static Block getBlockgs(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static Block getBlockgs(final double x, final double y, final double z) {
        return BlockUtil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos);
    }
    
    public static boolean isBlockAboveEntitySolid(final Entity entity) {
        if (entity != null) {
            final BlockPos pos = new BlockPos(entity.posX, entity.posY + 2.0, entity.posZ);
            return isBlockSolid(pos);
        }
        return false;
    }
    
    public static void debugPos(final String message, final BlockPos pos) {
        Command.sendMessage(message + pos.getX() + "x, " + pos.getY() + "y, " + pos.getZ() + "z");
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand) {
        final RayTraceResult result = BlockUtil.mc.world.rayTraceBlocks(new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
    }
    
    public static BlockPos[] toBlockPos(final Vec3d[] vec3ds) {
        final BlockPos[] list = new BlockPos[vec3ds.length];
        for (int i = 0; i < vec3ds.length; ++i) {
            list[i] = new BlockPos(vec3ds[i]);
        }
        return list;
    }
    
    public static Vec3d posToVec3d(final BlockPos pos) {
        return new Vec3d((Vec3i)pos);
    }
    
    public static BlockPos vec3dToPos(final Vec3d vec3d) {
        return new BlockPos(vec3d);
    }
    
    public static Boolean isPosInFov(final BlockPos pos) {
        final int dirnumber = RotationUtil.getDirection4D();
        if (dirnumber == 0 && pos.getZ() - BlockUtil.mc.player.getPositionVector().z < 0.0) {
            return false;
        }
        if (dirnumber == 1 && pos.getX() - BlockUtil.mc.player.getPositionVector().x > 0.0) {
            return false;
        }
        if (dirnumber == 2 && pos.getZ() - BlockUtil.mc.player.getPositionVector().z > 0.0) {
            return false;
        }
        return dirnumber != 3 || pos.getX() - BlockUtil.mc.player.getPositionVector().x >= 0.0;
    }
    
    public static boolean isBlockBelowEntitySolid(final Entity entity) {
        if (entity != null) {
            final BlockPos pos = new BlockPos(entity.posX, entity.posY - 1.0, entity.posZ);
            return isBlockSolid(pos);
        }
        return false;
    }
    
    public static boolean isBlockSolid(final BlockPos pos) {
        return !isBlockUnSolid(pos);
    }
    
    public static boolean isBlockUnSolid(final BlockPos pos) {
        return isBlockUnSolid(BlockUtil.mc.world.getBlockState(pos).getBlock());
    }
    
    public static boolean isBlockUnSolid(final Block block) {
        return BlockUtil.unSolidBlocks.contains(block);
    }
    
    public static boolean isBlockUnSafe(final Block block) {
        return BlockUtil.unSafeBlocks.contains(block);
    }
    
    public static Vec3d[] convertVec3ds(final Vec3d vec3d, final Vec3d[] input) {
        final Vec3d[] output = new Vec3d[input.length];
        for (int i = 0; i < input.length; ++i) {
            output[i] = vec3d.add(input[i]);
        }
        return output;
    }
    
    public static boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = BlockUtil.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)BlockUtil.mc.world, pos) != -1.0f;
    }
    
    public static boolean isValidBlock(final BlockPos pos) {
        final Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
        return !(block instanceof BlockLiquid) && block.getMaterial((IBlockState)null) != Material.AIR;
    }
    
    public static boolean isScaffoldPos(final BlockPos pos) {
        return BlockUtil.mc.world.isAirBlock(pos) || BlockUtil.mc.world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER || BlockUtil.mc.world.getBlockState(pos).getBlock() == Blocks.TALLGRASS || BlockUtil.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || BlockUtil.mc.world.rayTraceBlocks(new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ), new Vec3d((double)pos.getX(), (double)(pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos) {
        return rayTracePlaceCheck(pos, true);
    }
    
    static {
        blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        unSafeBlocks = Arrays.asList(Blocks.OBSIDIAN, Blocks.BEDROCK, Blocks.ENDER_CHEST, Blocks.ANVIL);
        BlockUtil.unSolidBlocks = Arrays.asList((Block)Blocks.FLOWING_LAVA, Blocks.FLOWER_POT, Blocks.SNOW, Blocks.CARPET, Blocks.END_ROD, (Block)Blocks.SKULL, Blocks.FLOWER_POT, Blocks.TRIPWIRE, (Block)Blocks.TRIPWIRE_HOOK, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.STONE_BUTTON, Blocks.LADDER, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.POWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, Blocks.UNLIT_REDSTONE_TORCH, Blocks.REDSTONE_TORCH, (Block)Blocks.REDSTONE_WIRE, Blocks.AIR, (Block)Blocks.PORTAL, Blocks.END_PORTAL, (Block)Blocks.WATER, (Block)Blocks.FLOWING_WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_LAVA, Blocks.SAPLING, (Block)Blocks.RED_FLOWER, (Block)Blocks.YELLOW_FLOWER, (Block)Blocks.BROWN_MUSHROOM, (Block)Blocks.RED_MUSHROOM, Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES, Blocks.BEETROOTS, (Block)Blocks.REEDS, Blocks.PUMPKIN_STEM, Blocks.MELON_STEM, Blocks.WATERLILY, Blocks.NETHER_WART, Blocks.COCOA, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, (Block)Blocks.TALLGRASS, (Block)Blocks.DEADBUSH, Blocks.VINE, (Block)Blocks.FIRE, Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.TORCH);
    }
}
