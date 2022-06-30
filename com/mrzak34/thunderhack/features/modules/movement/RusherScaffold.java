//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class RusherScaffold extends Module
{
    public Color color;
    public Setting<Boolean> rotate;
    public Setting<Boolean> autoswap;
    public Setting<Boolean> tower;
    public Setting<Boolean> safewalk;
    public Setting<Boolean> echestholding;
    public Setting<Boolean> render;
    private final Setting<Float> lineWidth;
    public final Setting<ColorSetting> Color2;
    private Timer timer;
    private BlockPosWithFacing currentblock;
    
    public RusherScaffold() {
        super("Scaffold", "\u043b\u0443\u0447\u0448\u0438\u0439 \u0441\u043a\u0430\u0444\u0444", Module.Category.PLAYER, true, false, false);
        this.color = new Color(Color.CYAN.getRed(), Color.CYAN.getGreen(), Color.CYAN.getBlue(), 50);
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.autoswap = (Setting<Boolean>)this.register(new Setting("AutoSwap", (T)true));
        this.tower = (Setting<Boolean>)this.register(new Setting("Tower", (T)true));
        this.safewalk = (Setting<Boolean>)this.register(new Setting("SafeWalk", (T)true));
        this.echestholding = (Setting<Boolean>)this.register(new Setting("EchestHolding", (T)false));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.Color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.timer = new Timer();
    }
    
    private boolean isBlockValid(final Block block) {
        return block.getDefaultState().getMaterial().isSolid();
    }
    
    private BlockPosWithFacing checkNearBlocks(final BlockPos blockPos) {
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, -1, 0), EnumFacing.UP);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (this.isBlockValid(RusherScaffold.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock())) {
            return new BlockPosWithFacing(blockPos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    private int findBlockToPlace() {
        if (RusherScaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && this.isBlockValid(((ItemBlock)RusherScaffold.mc.player.getHeldItemMainhand().getItem()).getBlock())) {
            return RusherScaffold.mc.player.inventory.currentItem;
        }
        for (int n = 0, n2 = 0; n2 < 9; n2 = ++n) {
            if (RusherScaffold.mc.player.inventory.getStackInSlot(n).getCount() != 0 && RusherScaffold.mc.player.inventory.getStackInSlot(n).getItem() instanceof ItemBlock && (!this.echestholding.getValue() || (this.echestholding.getValue() && !RusherScaffold.mc.player.inventory.getStackInSlot(n).getItem().equals(Item.getItemFromBlock(Blocks.ENDER_CHEST)))) && this.isBlockValid(((ItemBlock)RusherScaffold.mc.player.inventory.getStackInSlot(n).getItem()).getBlock())) {
                return n;
            }
        }
        return -1;
    }
    
    private boolean someblockcheck(final int itemnum) {
        final Item item = RusherScaffold.mc.player.inventory.getStackInSlot(itemnum).getItem();
        if (item instanceof ItemBlock) {
            final Vec3d vec3d = RusherScaffold.mc.player.getPositionVector();
            final Block block = ((ItemBlock)item).getBlock();
            return RusherScaffold.mc.world.rayTraceBlocks(vec3d, vec3d.add(0.0, -block.getDefaultState().getSelectedBoundingBox((World)RusherScaffold.mc.world, BlockPos.ORIGIN).maxY, 0.0), false, true, false) == null;
        }
        return false;
    }
    
    private BlockPosWithFacing checkNearBlocksExtended(final BlockPos blockPos) {
        BlockPosWithFacing ret = null;
        ret = this.checkNearBlocks(blockPos);
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(-1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, 1));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, -1));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(-2, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(2, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, 2));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, 0, -2));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos.add(0, -1, 0));
        final BlockPos blockPos2 = blockPos.add(0, -1, 0);
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(-1, 0, 0));
        if (ret != null) {
            return ret;
        }
        ret = this.checkNearBlocks(blockPos2.add(0, 0, 1));
        if (ret != null) {
            return ret;
        }
        return this.checkNearBlocks(blockPos2.add(0, 0, -1));
    }
    
    private int countValidBlocks() {
        int n = 36;
        int n2 = 0;
        while (n < 45) {
            if (RusherScaffold.mc.player.inventoryContainer.getSlot(n).getHasStack()) {
                final ItemStack itemStack = RusherScaffold.mc.player.inventoryContainer.getSlot(n).getStack();
                if (itemStack.getItem() instanceof ItemBlock && this.isBlockValid(((ItemBlock)itemStack.getItem()).getBlock())) {
                    n2 += itemStack.getCount();
                }
            }
            ++n;
        }
        return n2;
    }
    
    private Vec3d getEyePosition() {
        return new Vec3d(RusherScaffold.mc.player.posX, RusherScaffold.mc.player.posY + RusherScaffold.mc.player.getEyeHeight(), RusherScaffold.mc.player.posZ);
    }
    
    private float[] getRotations(final BlockPos blockPos, final EnumFacing enumFacing) {
        Vec3d vec3d = new Vec3d(blockPos.getX() + 0.5, RusherScaffold.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)RusherScaffold.mc.world, blockPos).maxY - 0.01, blockPos.getZ() + 0.5);
        vec3d = vec3d.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
        final Vec3d vec3d2 = this.getEyePosition();
        final double d = vec3d.x - vec3d2.x;
        final double d2 = vec3d.y - vec3d2.y;
        final double d3 = vec3d.z - vec3d2.z;
        final double d4 = d;
        final double d5 = d3;
        final double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        final float f = (float)(Math.toDegrees(Math.atan2(d3, d)) - 90.0);
        final float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d6)));
        final float[] ret = { RusherScaffold.mc.player.rotationYaw + MathHelper.wrapDegrees(f - RusherScaffold.mc.player.rotationYaw), RusherScaffold.mc.player.rotationPitch + MathHelper.wrapDegrees(f2 - RusherScaffold.mc.player.rotationPitch) };
        return ret;
    }
    
    private void doSafeWalk(final RhMoveEvent event) {
        double x = event.motionX;
        final double y = event.motionY;
        double z = event.motionZ;
        if (RusherScaffold.mc.player.onGround && !RusherScaffold.mc.player.noClip) {
            final double increment = 0.05;
            while (x != 0.0 && this.isOffsetBBEmpty(x, -2.0, 0.0)) {
                if (x < increment && x >= -increment) {
                    x = 0.0;
                }
                else if (x > 0.0) {
                    x -= increment;
                }
                else {
                    x += increment;
                }
            }
            while (z != 0.0 && this.isOffsetBBEmpty(0.0, -2.0, z)) {
                if (z < increment && z >= -increment) {
                    z = 0.0;
                }
                else if (z > 0.0) {
                    z -= increment;
                }
                else {
                    z += increment;
                }
            }
            while (x != 0.0 && z != 0.0 && this.isOffsetBBEmpty(x, -2.0, z)) {
                if (x < increment && x >= -increment) {
                    x = 0.0;
                }
                else if (x > 0.0) {
                    x -= increment;
                }
                else {
                    x += increment;
                }
                if (z < increment && z >= -increment) {
                    z = 0.0;
                }
                else if (z > 0.0) {
                    z -= increment;
                }
                else {
                    z += increment;
                }
            }
        }
        event.motionX = x;
        event.motionY = y;
        event.motionZ = z;
    }
    
    @SubscribeEvent
    public void onMove(final RhMoveEvent event) {
        if (nullCheck()) {
            return;
        }
        if (this.safewalk.getValue()) {
            this.doSafeWalk(event);
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (this.render.getValue() && this.currentblock != null) {
            GlStateManager.pushMatrix();
            RenderUtil.drawBlockOutline(this.currentblock.blockPos, this.Color2.getValue().getColorObject(), this.lineWidth.getValue(), false);
            GlStateManager.popMatrix();
        }
    }
    
    private boolean isOffsetBBEmpty(final double x, final double y, final double z) {
        return RusherScaffold.mc.world.getCollisionBoxes((Entity)RusherScaffold.mc.player, RusherScaffold.mc.player.getEntityBoundingBox().offset(x, y, z)).isEmpty();
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPost(final UpdateWalkingPlayerEvent event) {
        if (nullCheck()) {
            return;
        }
        if (this.countValidBlocks() > 0) {
            if (Double.compare(RusherScaffold.mc.player.posY, 257.0) <= 0) {
                if (this.countValidBlocks() > 0) {
                    if (!this.autoswap.getValue()) {
                        if (!(RusherScaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
                            return;
                        }
                    }
                    if (event.getStage() != 0) {
                        if (this.currentblock != null) {
                            final int n = RusherScaffold.mc.player.inventory.currentItem;
                            Label_0521: {
                                if (RusherScaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
                                    if (this.isBlockValid(((ItemBlock)RusherScaffold.mc.player.getHeldItemMainhand().getItem()).getBlock())) {
                                        break Label_0521;
                                    }
                                }
                                if (this.autoswap.getValue()) {
                                    final int n2 = this.findBlockToPlace();
                                    if (n2 != -1) {
                                        RusherScaffold.mc.player.inventory.currentItem = n2;
                                        RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(RusherScaffold.mc.player.inventory.currentItem));
                                    }
                                }
                            }
                            RusherScaffold scaffold4 = null;
                            Label_0665: {
                                Label_0663: {
                                    if (RusherScaffold.mc.player.movementInput.jump) {
                                        if (RusherScaffold.mc.player.moveForward == 0.0f) {
                                            if (RusherScaffold.mc.player.moveStrafing == 0.0f) {
                                                if (this.tower.getValue()) {
                                                    RusherScaffold.mc.player.setVelocity(0.0, 0.42, 0.0);
                                                    final Object[] objectArray = { null };
                                                    if (!this.timer.passed(1500L)) {
                                                        break Label_0663;
                                                    }
                                                    RusherScaffold.mc.player.motionY = -0.28;
                                                    final RusherScaffold scaffold3 = scaffold4 = this;
                                                    this.timer.reset();
                                                    break Label_0665;
                                                }
                                            }
                                        }
                                    }
                                    this.timer.reset();
                                }
                                scaffold4 = this;
                            }
                            final BlockPos blockPos2;
                            final BlockPos blockPos = blockPos2 = scaffold4.currentblock.blockPos;
                            final boolean bl = RusherScaffold.mc.world.getBlockState(blockPos).getBlock().onBlockActivated((World)RusherScaffold.mc.world, blockPos2, RusherScaffold.mc.world.getBlockState(blockPos2), (EntityPlayer)RusherScaffold.mc.player, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
                            if (bl) {
                                RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RusherScaffold.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                            }
                            RusherScaffold.mc.playerController.processRightClickBlock(RusherScaffold.mc.player, RusherScaffold.mc.world, blockPos, this.currentblock.enumFacing, new Vec3d(blockPos.getX() + Math.random(), RusherScaffold.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)RusherScaffold.mc.world, blockPos).maxY - 0.01, blockPos.getZ() + Math.random()), EnumHand.MAIN_HAND);
                            RusherScaffold.mc.player.swingArm(EnumHand.MAIN_HAND);
                            if (bl) {
                                RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RusherScaffold.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                            }
                            RusherScaffold.mc.player.inventory.currentItem = n;
                            RusherScaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(RusherScaffold.mc.player.inventory.currentItem));
                        }
                    }
                    else {
                        this.currentblock = null;
                        if (!RusherScaffold.mc.player.isSneaking()) {
                            final int n3 = this.findBlockToPlace();
                            if (n3 != -1) {
                                final Item item = RusherScaffold.mc.player.inventory.getStackInSlot(n3).getItem();
                                if (item instanceof ItemBlock) {
                                    final Block block = ((ItemBlock)item).getBlock();
                                    final boolean bl2 = block.getDefaultState().isFullBlock();
                                    final double d = bl2 ? 1.0 : 0.01;
                                    final BlockPos blockPos3 = new BlockPos(RusherScaffold.mc.player.posX, RusherScaffold.mc.player.posY - d, RusherScaffold.mc.player.posZ);
                                    if (RusherScaffold.mc.world.getBlockState(blockPos3).getMaterial().isReplaceable()) {
                                        if (!bl2) {
                                            if (!this.someblockcheck(n3)) {
                                                return;
                                            }
                                        }
                                        final RusherScaffold scaffold5 = this;
                                        scaffold5.currentblock = this.checkNearBlocksExtended(blockPos3);
                                        if (scaffold5.currentblock != null && this.rotate.getValue()) {
                                            final float[] rotations = this.getRotations(this.currentblock.blockPos, this.currentblock.enumFacing);
                                            RusherScaffold.mc.player.rotationYaw = rotations[0];
                                            RusherScaffold.mc.player.rotationPitch = rotations[1];
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            }
        }
        this.currentblock = null;
    }
    
    public class BlockPosWithFacing
    {
        public BlockPos blockPos;
        public EnumFacing enumFacing;
        
        public BlockPosWithFacing(final BlockPos blockPos, final EnumFacing enumFacing) {
            this.blockPos = blockPos;
            this.enumFacing = enumFacing;
        }
    }
}
