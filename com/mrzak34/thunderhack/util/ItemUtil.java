//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import java.util.stream.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;

public class ItemUtil implements Util
{
    public static final Minecraft mc;
    
    public static Map<ItemStack, Integer> getHotbarItems() {
        final Map<ItemStack, Integer> items = new HashMap<ItemStack, Integer>();
        for (int j = 0; j < 9; ++j) {
            items.put(ItemUtil.mc.player.inventory.getStackInSlot(j), j);
        }
        return items;
    }
    
    public static int getGappleSlot(final boolean crapple) {
        if (Items.GOLDEN_APPLE == ItemUtil.mc.player.getHeldItemOffhand().getItem() && crapple == ItemUtil.mc.player.getHeldItemOffhand().getRarity().equals((Object)EnumRarity.RARE)) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final ItemStack item = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (crapple == item.getRarity().equals((Object)EnumRarity.RARE) && item.getItem() == Items.GOLDEN_APPLE) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    public static int getItemSlot(final Item input) {
        if (input == ItemUtil.mc.player.getHeldItemOffhand().getItem()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final Item item = ItemUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (int i = 0; i < 4; ++i) {
            if (getDamageInPercent((ItemStack)player.inventory.armorInventory.get(i)) < durability) {
                return true;
            }
        }
        return false;
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
        final float red = 1.0f - green;
        return (float)(100 - (int)(red * 100.0f));
    }
    
    public static void swapToOffhandSlot(final int slot) {
        if (slot == -1) {
            return;
        }
        ItemUtil.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.updateController();
    }
    
    public static int swapToHotbarSlot(final int slot, final boolean silent) {
        if (ItemUtil.mc.player.inventory.currentItem == slot || slot < 0 || slot > 8) {
            return slot;
        }
        InventoryManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        if (!silent) {
            ItemUtil.mc.player.inventory.currentItem = slot;
        }
        ItemUtil.mc.playerController.updateController();
        return slot;
    }
    
    public static void swapToOffhandSlot2(final int slot) {
        if (slot == -1) {
            return;
        }
        ItemUtil.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ItemUtil.mc.player);
        ItemUtil.mc.playerController.updateController();
    }
    
    public static int findItem(final Block... blockIn) {
        final List<Block> list = Arrays.stream(blockIn).collect((Collector<? super Block, ?, List<Block>>)Collectors.toList());
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock && list.contains(((ItemBlock)stack.getItem()).getBlock())) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findItem(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    if (clazz.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
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
        final Block neighbourBlock = ItemUtil.mc.world.getBlockState(neighbour).getBlock();
        if (!ItemUtil.mc.player.isSneaking()) {
            ItemUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ItemUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            ItemUtil.mc.player.setSneaking(true);
            sneaking = true;
        }
        if (rotate) {
            faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        ItemUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        ItemUtil.mc.rightClickDelayTimer = 4;
        return sneaking || isSneaking;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (ItemUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(ItemUtil.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = ItemUtil.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(ItemUtil.mc.player.posX, ItemUtil.mc.player.posY + ItemUtil.mc.player.getEyeHeight(), ItemUtil.mc.player.posZ);
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { ItemUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - ItemUtil.mc.player.rotationYaw), ItemUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - ItemUtil.mc.player.rotationPitch) };
    }
    
    public static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final float[] rotations = getLegitRotations(vec);
        ItemUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], ItemUtil.mc.player.onGround));
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            ItemUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            ItemUtil.mc.playerController.processRightClickBlock(ItemUtil.mc.player, ItemUtil.mc.world, pos, direction, vec, hand);
        }
        ItemUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        ItemUtil.mc.rightClickDelayTimer = 4;
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ItemUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (clazz.isInstance(block)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final int slot) {
        ItemUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        ItemUtil.mc.player.inventory.currentItem = slot;
        ItemUtil.mc.playerController.updateController();
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
