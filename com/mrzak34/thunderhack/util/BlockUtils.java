//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class BlockUtils
{
    private static final Minecraft mc;
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
        }
        else {
            BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, pos, direction, vec, hand);
            BlockUtils.mc.player.swingArm(hand);
        }
    }
    
    public static Vec3d blockPosToVec(final BlockPos pos) {
        return new Vec3d((Vec3i)pos);
    }
    
    public static BlockPos vecToBlockPos(final Vec3d vec) {
        return new BlockPos(vec);
    }
    
    public static List<BlockPos> getBlocksInSphere(final BlockPos center, final int radius) {
        final List<BlockPos> blocksInSphere = new ArrayList<BlockPos>();
        final int centerX = center.getX();
        final int centerY = center.getY();
        final int centerZ = center.getZ();
        for (int x = centerX - radius; x <= centerX + radius; ++x) {
            for (int z = centerZ - radius; z <= centerZ + radius; ++z) {
                for (int y = centerY - radius; y < centerY + radius; ++y) {
                    final double dist = (centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) + (centerY - y) * (centerY - y);
                    if (dist < radius * radius) {
                        blocksInSphere.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return blocksInSphere;
    }
    
    public static boolean hasNeighbours(final BlockPos blockPos) {
        for (final EnumFacing facing : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(facing);
            if (!BlockUtils.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }
    
    public static Optional<ClickLocation> generateClickLocation(final BlockPos pos) {
        return generateClickLocation(pos, false, false);
    }
    
    public static Optional<ClickLocation> generateClickLocation(final BlockPos pos, final boolean ignoreEntities) {
        return generateClickLocation(pos, ignoreEntities, false);
    }
    
    public static Optional<ClickLocation> generateClickLocation(final BlockPos pos, final boolean ignoreEntities, final boolean noPistons) {
        return generateClickLocation(pos, ignoreEntities, false, false);
    }
    
    public static Optional<ClickLocation> generateClickLocation(final BlockPos pos, final boolean ignoreEntities, final boolean noPistons, final boolean onlyCrystals) {
        final Block block = BlockUtils.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return Optional.empty();
        }
        if (!ignoreEntities) {
            for (final Entity entity : BlockUtils.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
                if (onlyCrystals && entity instanceof EntityEnderCrystal) {
                    continue;
                }
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityArrow)) {
                    return Optional.empty();
                }
            }
        }
        EnumFacing side = null;
        for (final EnumFacing blockSide : EnumFacing.values()) {
            final BlockPos sidePos = pos.offset(blockSide);
            if (!noPistons || BlockUtils.mc.world.getBlockState(sidePos).getBlock() != Blocks.PISTON) {
                if (BlockUtils.mc.world.getBlockState(sidePos).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(sidePos), false)) {
                    final IBlockState blockState = BlockUtils.mc.world.getBlockState(sidePos);
                    if (!blockState.getMaterial().isReplaceable()) {
                        side = blockSide;
                        break;
                    }
                }
            }
        }
        if (side == null) {
            return Optional.empty();
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtils.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(neighbour), false)) {
            return Optional.empty();
        }
        return Optional.of(new ClickLocation(neighbour, opposite));
    }
    
    public static double[] calculateLookAt(final BlockPos pos, final EnumFacing facing, final EntityPlayer me) {
        return PlayerUtils.calculateLookAt(pos.getX() + 0.5 + facing.getDirectionVec().getX() * 0.5, pos.getY() + 0.5 + facing.getDirectionVec().getY() * 0.5, pos.getZ() + 0.5 + facing.getDirectionVec().getZ() * 0.5, me);
    }
    
    public static double[] calculateLookAt(final double x, final double y, final double z, final EnumFacing facing, final EntityPlayer me) {
        return PlayerUtils.calculateLookAt(x + 0.5 + facing.getDirectionVec().getX() * 0.5, y + 0.5 + facing.getDirectionVec().getY() * 0.5, z + 0.5 + facing.getDirectionVec().getZ() * 0.5, me);
    }
    
    public static boolean shouldSneakWhileRightClicking(final BlockPos blockPos) {
        final Block block = BlockUtils.mc.world.getBlockState(blockPos).getBlock();
        TileEntity tileEntity = null;
        for (final TileEntity tE : BlockUtils.mc.world.loadedTileEntityList) {
            if (!tE.getPos().equals((Object)blockPos)) {
                continue;
            }
            tileEntity = tE;
            break;
        }
        return tileEntity != null || block instanceof BlockBed || block instanceof BlockContainer || block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate || block instanceof BlockButton || block instanceof BlockAnvil || block instanceof BlockWorkbench || block instanceof BlockCake || block instanceof BlockRedstoneDiode;
    }
    
    public static boolean validObi(final BlockPos pos) {
        return !validBedrock(pos) && (BlockUtils.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
    }
    
    public static boolean validBedrock(final BlockPos pos) {
        return BlockUtils.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
    }
    
    public static BlockPos validTwoBlockObiXZ(final BlockPos pos) {
        if ((BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && (BlockUtils.mc.world.getBlockState(pos.east().down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east().down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east(2)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east(2)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east().south()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east().south()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east().north()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east().north()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos.east()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up(2)).getMaterial() == Material.AIR) {
            return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(1, 0, 0) : null;
        }
        if ((BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && (BlockUtils.mc.world.getBlockState(pos.south().down()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south().down()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south(2)).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south(2)).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south().east()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south().east()).getBlock() == Blocks.BEDROCK) && (BlockUtils.mc.world.getBlockState(pos.south().west()).getBlock() == Blocks.OBSIDIAN || BlockUtils.mc.world.getBlockState(pos.south().west()).getBlock() == Blocks.BEDROCK) && BlockUtils.mc.world.getBlockState(pos.south()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up(2)).getMaterial() == Material.AIR) {
            return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(0, 0, 1) : null;
        }
        return null;
    }
    
    public static BlockPos validTwoBlockBedrockXZ(final BlockPos pos) {
        if (BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east(2)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east().south()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east().north()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.east().up(2)).getMaterial() == Material.AIR) {
            return new BlockPos(1, 0, 0);
        }
        if (BlockUtils.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.up(2)).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().down()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south(2)).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south().east()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south().west()).getBlock() == Blocks.BEDROCK && BlockUtils.mc.world.getBlockState(pos.south()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up()).getMaterial() == Material.AIR && BlockUtils.mc.world.getBlockState(pos.south().up(2)).getMaterial() == Material.AIR) {
            return new BlockPos(0, 0, 1);
        }
        return null;
    }
    
    public static boolean isHole(final BlockPos pos) {
        return validObi(pos) || validBedrock(pos);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public static class ClickLocation
    {
        public final BlockPos neighbour;
        public final EnumFacing opposite;
        
        public ClickLocation(final BlockPos neighbour, final EnumFacing opposite) {
            this.neighbour = neighbour;
            this.opposite = opposite;
        }
    }
}
