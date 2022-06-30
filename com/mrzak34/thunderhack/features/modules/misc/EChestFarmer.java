//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class EChestFarmer extends Module
{
    private final Setting<Integer> range;
    private final Setting<Integer> bd;
    private Timer timer;
    private Timer breakTimer;
    private InteractionUtil.Placement placement;
    
    public EChestFarmer() {
        super("EChestFarmer", "\u0430\u0444\u043a \u0444\u0430\u0440\u043c \u043e\u0431\u0441\u044b", Category.MISC, true, false, false);
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)2, (T)1, (T)3));
        this.bd = (Setting<Integer>)this.register(new Setting("BreakDelay", (T)4000, (T)0, (T)5000));
        this.timer = new Timer();
        this.breakTimer = new Timer();
        this.placement = null;
    }
    
    @Override
    public void onEnable() {
        this.placement = null;
        this.breakTimer.reset();
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0) {
            this.placement = null;
            if (event.isCanceled() || !InteractionUtil.canPlaceNormally()) {
                return;
            }
            final BlockPos closestEChest = getSphere(new BlockPos((Entity)EChestFarmer.mc.player), this.range.getValue(), this.range.getValue(), false, true, 0).stream().filter(pos -> EChestFarmer.mc.world.getBlockState(pos).getBlock() instanceof BlockEnderChest).min(Comparator.comparing(pos -> EChestFarmer.mc.player.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5))).orElse(null);
            if (closestEChest != null) {
                if (this.breakTimer.passedMs(this.bd.getValue())) {
                    boolean holdingPickaxe = EChestFarmer.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;
                    if (!holdingPickaxe) {
                        for (int i = 0; i < 9; ++i) {
                            final ItemStack stack = EChestFarmer.mc.player.inventory.getStackInSlot(i);
                            if (!stack.isEmpty()) {
                                if (stack.getItem() == Items.DIAMOND_PICKAXE) {
                                    holdingPickaxe = true;
                                    EChestFarmer.mc.player.inventory.currentItem = i;
                                    EChestFarmer.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
                                    break;
                                }
                            }
                        }
                    }
                    if (!holdingPickaxe) {
                        return;
                    }
                    final EnumFacing facing = EChestFarmer.mc.player.getHorizontalFacing().getOpposite();
                    SilentRotaionUtil.lookAtVector(new Vec3d(closestEChest.getX() + 0.5 + facing.getDirectionVec().getX() * 0.5, closestEChest.getY() + 0.5 + facing.getDirectionVec().getY() * 0.5, closestEChest.getZ() + 0.5 + facing.getDirectionVec().getZ() * 0.5));
                    EChestFarmer.mc.player.swingArm(EnumHand.MAIN_HAND);
                    EChestFarmer.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, closestEChest, facing));
                    EChestFarmer.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, closestEChest, facing));
                    this.breakTimer.reset();
                }
            }
            else if (this.timer.passedMs(350L)) {
                this.timer.reset();
                if (EChestFarmer.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
                    final ItemBlock block = (ItemBlock)EChestFarmer.mc.player.getHeldItemMainhand().getItem();
                    if (block.getBlock() != Blocks.ENDER_CHEST && !this.changeToEChest()) {
                        return;
                    }
                }
                else if (!this.changeToEChest()) {
                    return;
                }
                for (final BlockPos pos2 : getSphere(new BlockPos((Entity)EChestFarmer.mc.player), this.range.getValue(), this.range.getValue(), false, true, 0)) {
                    final InteractionUtil.Placement cPlacement = InteractionUtil.preparePlacement(pos2, true);
                    if (cPlacement != null) {
                        this.placement = cPlacement;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPost(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 1 && this.placement != null) {
            InteractionUtil.placeBlockSafely(this.placement, EnumHand.MAIN_HAND, false);
            this.breakTimer.reset();
        }
    }
    
    private boolean changeToEChest() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = EChestFarmer.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemBlock) {
                    final ItemBlock block = (ItemBlock)stack.getItem();
                    if (block.getBlock() == Blocks.ENDER_CHEST) {
                        EChestFarmer.mc.player.inventory.currentItem = i;
                        EChestFarmer.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
