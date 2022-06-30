//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;

public class Speedmine2 extends Module
{
    public Setting<Float> limit;
    public Setting<Float> range;
    public Setting<SubBind> breakBind;
    private Setting<MineMode> mode;
    public Setting<Integer> confirm;
    public Setting<Integer> realDelay;
    public Setting<Integer> outlineA;
    public Setting<Integer> alpha;
    public Setting<Integer> delay;
    public Setting<Boolean> limitRotations;
    public Setting<Boolean> swingStop;
    public Setting<Boolean> checkPacket;
    public Setting<Boolean> resetAfterPacket;
    public Setting<Boolean> normal;
    public Setting<Boolean> placeCrystal;
    public Setting<Boolean> requireBreakSlot;
    public Setting<Boolean> swap;
    public Setting<Boolean> toAir;
    public Setting<Boolean> onGround;
    public Setting<Boolean> display;
    public Setting<Boolean> event;
    public Setting<Boolean> rotate;
    public Setting<Boolean> multiTask;
    public Setting<Boolean> noReset;
    public final float[] damages;
    protected final Timer timer;
    protected final Timer resetTimer;
    protected BlockPos pos;
    protected EnumFacing facing;
    protected AxisAlignedBB bb;
    protected float[] rotations;
    public float maxDamage;
    protected boolean sentPacket;
    protected boolean shouldAbort;
    protected boolean pausing;
    protected final Timer delayTimer;
    protected Packet<?> limitRotationPacket;
    protected int limitRotationSlot;
    
    public Speedmine2() {
        super("testspedmoin", "\u043f\u0430\u043a\u0435\u0442\u043c\u0430\u0439\u043d \u0444\u043e\u0431\u0443\u0441", Module.Category.PLAYER, true, false, false);
        this.limit = (Setting<Float>)this.register(new Setting("Damage", (T)1.0f, (T)0.0f, (T)2.0f));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)7.0f, (T)0.1f, (T)100.0f));
        this.breakBind = (Setting<SubBind>)this.register(new Setting("BreakBind", (T)new SubBind(24)));
        this.mode = (Setting<MineMode>)this.register(new Setting("Rotation Mode", (T)MineMode.Smart));
        this.confirm = (Setting<Integer>)this.register(new Setting("Confirm", (T)500, (T)0, (T)1000));
        this.realDelay = (Setting<Integer>)this.register(new Setting("Delay", (T)5, (T)0, (T)15));
        this.outlineA = (Setting<Integer>)this.register(new Setting("OutlineAlpha", (T)100, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("BlockAlpha", (T)100, (T)0, (T)255));
        this.delay = (Setting<Integer>)this.register(new Setting("ClickDelay", (T)100, (T)0, (T)500));
        this.limitRotations = (Setting<Boolean>)this.register(new Setting("Limit-Rotations", (T)true));
        this.swingStop = (Setting<Boolean>)this.register(new Setting("Swing-Stop", (T)true));
        this.checkPacket = (Setting<Boolean>)this.register(new Setting("CheckPacket", (T)true));
        this.resetAfterPacket = (Setting<Boolean>)this.register(new Setting("ResetAfterPacket", (T)false));
        this.normal = (Setting<Boolean>)this.register(new Setting("Normal", (T)false));
        this.placeCrystal = (Setting<Boolean>)this.register(new Setting("PlaceCrystal", (T)false));
        this.requireBreakSlot = (Setting<Boolean>)this.register(new Setting("RequireBreakSlot", (T)false));
        this.swap = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)false));
        this.toAir = (Setting<Boolean>)this.register(new Setting("ToAir", (T)false));
        this.onGround = (Setting<Boolean>)this.register(new Setting("OnGround", (T)false));
        this.display = (Setting<Boolean>)this.register(new Setting("DisplayDamage", (T)false));
        this.event = (Setting<Boolean>)this.register(new Setting("Event", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.multiTask = (Setting<Boolean>)this.register(new Setting("MultiTask", (T)false));
        this.noReset = (Setting<Boolean>)this.register(new Setting("Reset", (T)true));
        this.damages = new float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
        this.timer = new Timer();
        this.resetTimer = new Timer();
        this.delayTimer = new Timer();
        this.limitRotationSlot = -1;
    }
    
    public void onEnable() {
        this.reset();
    }
    
    public void abortCurrentPos() {
        Speedmine2.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.pos, this.facing));
        Speedmine2.mc.playerController.curBlockDamageMP = 0.0f;
        Speedmine2.mc.world.sendBlockBreakProgress(Speedmine2.mc.player.getEntityId(), this.pos, -1);
        Speedmine2.mc.player.resetCooldown();
        this.reset();
    }
    
    public void reset() {
        this.pos = null;
        this.facing = null;
        this.bb = null;
        this.maxDamage = 0.0f;
        this.sentPacket = false;
        this.limitRotationSlot = -1;
        this.limitRotationPacket = null;
        for (int i = 0; i < 9; ++i) {
            this.damages[i] = 0.0f;
        }
    }
    
    public MineMode getMode() {
        return this.mode.getValue();
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public Timer getTimer() {
        return this.timer;
    }
    
    public float getRange() {
        return this.range.getValue();
    }
    
    public int getBlockAlpha() {
        return this.alpha.getValue();
    }
    
    public int getOutlineAlpha() {
        return this.outlineA.getValue();
    }
    
    public boolean isPausing() {
        return this.pausing;
    }
    
    public void setPausing(final boolean pausing) {
        this.pausing = pausing;
    }
    
    protected boolean sendStopDestroy(final BlockPos pos, final EnumFacing facing, final boolean toAir) {
        final CPacketPlayerDigging stop = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, facing);
        if (toAir) {
            ((ICPacketPlayerDigging)stop).setClientSideBreaking(true);
        }
        if (this.rotate.getValue() && this.limitRotations.getValue()) {
            this.limitRotationPacket = (Packet<?>)stop;
            this.limitRotationSlot = Speedmine2.mc.player.inventory.currentItem;
            return false;
        }
        if (this.event.getValue()) {
            Speedmine2.mc.player.connection.sendPacket((Packet)stop);
        }
        this.onSendPacket();
        return true;
    }
    
    public void swing(final EnumHand hand) {
        swingPacket(hand);
    }
    
    public static void swingPacket(final EnumHand hand) {
        Objects.requireNonNull(Speedmine2.mc.getConnection()).sendPacket((Packet)new CPacketAnimation(hand));
    }
    
    protected void postSend(final boolean toAir) {
        if (this.swingStop.getValue()) {
            this.swing(EnumHand.MAIN_HAND);
        }
        if (toAir) {
            Speedmine2.mc.playerController.onPlayerDestroyBlock(this.pos);
        }
        if (this.resetAfterPacket.getValue()) {
            this.reset();
        }
    }
    
    public void forceSend() {
        if (this.pos != null) {
            if (this.mode.getValue() == MineMode.Instant) {
                Speedmine2.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, this.facing));
                this.sendStopDestroy(this.pos, this.facing, false);
                if (this.mode.getValue() == MineMode.Instant) {
                    Speedmine2.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.pos, this.facing));
                }
            }
            else if (this.mode.getValue() == MineMode.Civ) {
                this.sendStopDestroy(this.pos, this.facing, false);
            }
        }
    }
    
    public void tryBreak() {
        final int breakSlot;
        if (!this.pausing && ((breakSlot = this.findBreakSlot()) != -1 || this.requireBreakSlot.getValue())) {
            final boolean toAir = this.toAir.getValue();
            final int lastSlot = Speedmine2.mc.player.inventory.currentItem;
            if (breakSlot != -1) {
                InventoryUtil.switchToHotbarSlot(breakSlot, false);
            }
            final CPacketPlayerDigging packet = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, this.facing);
            if (toAir) {
                ((ICPacketPlayerDigging)packet).setClientSideBreaking(true);
            }
            Speedmine2.mc.player.connection.sendPacket((Packet)packet);
            if (breakSlot != -1) {
                InventoryUtil.switchToHotbarSlot(lastSlot, false);
            }
            if (toAir) {
                Speedmine2.mc.playerController.onPlayerDestroyBlock(this.pos);
            }
            this.onSendPacket();
        }
    }
    
    private int findBreakSlot() {
        int slot = -1;
        for (int i = 0; i < this.damages.length; ++i) {
            if (this.damages[i] >= this.limit.getValue() && (slot = i) >= Speedmine2.mc.player.inventory.currentItem) {
                return slot;
            }
        }
        return slot;
    }
    
    public void checkReset() {
        if (this.sentPacket && this.resetTimer.passed(this.confirm.getValue()) && (this.mode.getValue() == MineMode.Packet || this.mode.getValue() == MineMode.Smart)) {
            this.reset();
        }
    }
    
    public void onSendPacket() {
        this.sentPacket = true;
        this.resetTimer.reset();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketMultiBlockChange) {
            final SPacketMultiBlockChange packet2 = (SPacketMultiBlockChange)event.getPacket();
            if ((this.mode.getValue() != MineMode.Smart || this.sentPacket) && this.mode.getValue() != MineMode.Instant && this.mode.getValue() != MineMode.Civ) {
                for (final SPacketMultiBlockChange.BlockUpdateData data : packet2.getChangedBlocks()) {
                    if (data.getPos().equals((Object)this.pos) && data.getBlockState().getBlock() == Blocks.AIR) {
                        this.reset();
                    }
                }
            }
        }
        if (event.getPacket() instanceof SPacketMultiBlockChange) {
            final SPacketBlockChange packet3 = (SPacketBlockChange)event.getPacket();
            if (packet3.getBlockPosition().equals((Object)this.pos) && packet3.getBlockState().getBlock() == Blocks.AIR && (this.mode.getValue() != MineMode.Smart || this.sentPacket) && this.mode.getValue() != MineMode.Instant && this.mode.getValue() != MineMode.Civ) {
                this.reset();
            }
            else if (packet3.getBlockPosition().equals((Object)this.pos) && packet3.getBlockState() == Speedmine2.mc.world.getBlockState(this.pos) && this.shouldAbort && this.mode.getValue() == MineMode.Instant) {
                Speedmine2.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.pos, EnumFacing.DOWN));
                this.shouldAbort = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onClickBlock(final ClickBlockEvent event) {
        if ((!this.noReset.getValue() && this.mode.getValue() != MineMode.Reset) || Speedmine2.mc.playerController.curBlockDamageMP > 0.1f) {}
    }
    
    @SubscribeEvent
    public void onDamageBlock(final DamageBlockEvent even2) {
        this.checkReset();
        switch (this.mode.getValue()) {
            case Reset: {
                this.setPos(even2);
                break;
            }
            case Packet:
            case Civ: {
                this.setPos(even2);
                Speedmine2.mc.player.swingArm(EnumHand.MAIN_HAND);
                final CPacketPlayerDigging start = new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, even2.getBlockPos(), even2.getEnumFacing());
                final CPacketPlayerDigging stop = new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, even2.getBlockPos(), even2.getEnumFacing());
                if (this.event.getValue()) {
                    Speedmine2.mc.player.connection.sendPacket((Packet)start);
                    Speedmine2.mc.player.connection.sendPacket((Packet)stop);
                }
                else {
                    Speedmine2.mc.player.connection.sendPacket((Packet)start);
                    Speedmine2.mc.player.connection.sendPacket((Packet)stop);
                }
                if (this.swingStop.getValue()) {
                    this.swing(EnumHand.MAIN_HAND);
                }
                even2.setCanceled(true);
                break;
            }
            case Damage: {
                this.setPos(even2);
                if (Speedmine2.mc.playerController.curBlockDamageMP >= this.limit.getValue()) {
                    Speedmine2.mc.playerController.curBlockDamageMP = 1.0f;
                    break;
                }
                break;
            }
            case Smart: {
                boolean aborted = false;
                if (this.pos != null && !this.pos.equals((Object)even2.getBlockPos())) {
                    this.abortCurrentPos();
                    aborted = true;
                }
                if (!aborted && !this.timer.passed(this.delay.getValue())) {
                    break;
                }
                if (!aborted && this.pos != null && this.pos.equals((Object)even2.getBlockPos())) {
                    this.abortCurrentPos();
                    this.timer.reset();
                    return;
                }
                this.setPos(even2);
                Speedmine2.mc.player.swingArm(EnumHand.MAIN_HAND);
                final CPacketPlayerDigging packet = new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, even2.getBlockPos(), even2.getEnumFacing());
                if (this.event.getValue()) {
                    Speedmine2.mc.player.connection.sendPacket((Packet)packet);
                }
                else {
                    Speedmine2.mc.player.connection.sendPacket((Packet)packet);
                }
                even2.setCanceled(true);
                this.timer.reset();
                break;
            }
            case Instant: {
                boolean abortedd = false;
                if (this.pos != null && !this.pos.equals((Object)even2.getBlockPos())) {
                    this.abortCurrentPos();
                    abortedd = true;
                }
                if (!abortedd && !this.timer.passed(this.delay.getValue())) {
                    break;
                }
                if (!abortedd && this.pos != null && this.pos.equals((Object)even2.getBlockPos())) {
                    this.abortCurrentPos();
                    this.timer.reset();
                    return;
                }
                this.setPos(even2);
                Speedmine2.mc.player.swingArm(EnumHand.MAIN_HAND);
                final CPacketPlayerDigging packet2 = new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, even2.getBlockPos(), even2.getEnumFacing());
                if (this.event.getValue()) {
                    Speedmine2.mc.player.connection.sendPacket((Packet)packet2);
                }
                else {
                    Speedmine2.mc.player.connection.sendPacket((Packet)packet2);
                }
                even2.setCanceled(this.shouldAbort = true);
                this.timer.reset();
                break;
            }
        }
    }
    
    private void setPos(final DamageBlockEvent event) {
        this.reset();
        this.pos = event.getBlockPos();
        this.facing = event.getEnumFacing();
        this.bb = Speedmine2.mc.world.getBlockState(this.pos).getSelectedBoundingBox((World)Speedmine2.mc.world, this.pos).grow(0.0020000000949949026);
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final DeathEvent event) {
        if (event.player == Speedmine2.mc.player) {
            this.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerDigging && (this.mode.getValue() == MineMode.Packet || this.mode.getValue() == MineMode.Smart || this.mode.getValue() == MineMode.Instant)) {
            final CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK || packet.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                final BlockPos pos = packet.getPosition();
                if (!canBreak(pos)) {
                    event.setCanceled(true);
                }
            }
        }
    }
    
    public static boolean canBreak(final BlockPos pos) {
        return canBreak(Speedmine2.mc.world.getBlockState(pos), pos);
    }
    
    public static boolean canBreak(final IBlockState state, final BlockPos pos) {
        return state.getBlockHardness((World)Speedmine2.mc.world, pos) != -1.0f || state.getMaterial().isLiquid();
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event2) {
        if (!this.rotate.getValue()) {
            return;
        }
        final Packet<?> packet = this.limitRotationPacket;
        if ((event2.getStage() == 0 && this.pos != null) || (Speedmine2.mc.gameSettings.keyBindUseItem.isKeyDown() && (!this.limitRotations.getValue() || packet != null))) {
            if (this.facing != null) {
                this.rotations = RotationUtil.getRotations(this.pos, this.facing);
                SilentRotaionUtil.lookAtAngles(this.rotations[0], this.rotations[1]);
            }
        }
        else if (event2.getStage() == 1 && this.limitRotations.getValue() && packet != null) {
            final boolean toAir1 = this.toAir.getValue();
            final int last = Speedmine2.mc.player.inventory.currentItem;
            InventoryUtil.switchToHotbarSlot(this.limitRotationSlot, true);
            if (this.event.getValue()) {
                Speedmine2.mc.player.connection.sendPacket((Packet)packet);
            }
            else {
                Speedmine2.mc.player.connection.sendPacket((Packet)packet);
            }
            InventoryUtil.switchToHotbarSlot(last, true);
            this.onSendPacket();
            this.limitRotationPacket = null;
            this.limitRotationSlot = -1;
            this.postSend(toAir1);
        }
    }
    
    @SubscribeEvent
    public void onBlockReset(final ResetBlockEvent event) {
        if (this.noReset.getValue() || this.mode.getValue() == MineMode.Reset) {
            event.setCanceled(true);
        }
    }
    
    private EntityPlayer getPlacePlayer(final BlockPos pos) {
        for (final EntityPlayer player : Speedmine2.mc.world.playerEntities) {
            if (!Thunderhack.friendManager.isFriend(player.getName())) {
                if (player == Speedmine2.mc.player) {
                    continue;
                }
                final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
                for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                    if (playerPos.offset(facing).equals((Object)pos)) {
                        return player;
                    }
                }
                if (playerPos.offset(EnumFacing.UP).offset(EnumFacing.UP).equals((Object)pos)) {
                    return player;
                }
                continue;
            }
        }
        return null;
    }
    
    public void onUpdate() {
        if (PlayerUtils.isKeyDown(this.breakBind.getValue().getKey())) {
            this.tryBreak();
        }
        this.checkReset();
        Speedmine2.mc.playerController.blockHitDelay = 0;
        if (this.multiTask.getValue() || (!this.noReset.getValue() && this.mode.getValue() != MineMode.Reset) || Speedmine2.mc.gameSettings.keyBindUseItem.isKeyDown()) {}
        if (this.pos != null) {
            if ((this.mode.getValue() == MineMode.Smart || this.mode.getValue() == MineMode.Instant || this.mode.getValue() == MineMode.Civ) && Speedmine2.mc.player.getDistanceSq(this.pos) > MathUtil.square(this.range.getValue())) {
                this.abortCurrentPos();
                return;
            }
            if (this.mode.getValue() == MineMode.Civ && this.facing != null && !BlockUtil.isAir(this.pos) && !this.isPausing() && this.delayTimer.passed(this.realDelay.getValue())) {
                this.swing(EnumHand.MAIN_HAND);
                this.sendStopDestroy(this.pos, this.facing, false);
            }
            this.maxDamage = 0.0f;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = Speedmine2.mc.player.inventory.getStackInSlot(i);
                this.damages[i] = MathUtil.clamp(this.damages[i] + getDamage(stack, this.pos, this.onGround.getValue()), 0.0f, Float.MAX_VALUE);
                if (this.damages[i] > this.maxDamage) {
                    this.maxDamage = this.damages[i];
                }
            }
            if (this.normal.getValue()) {
                int fastSlot = -1;
                for (int j = 0; j < this.damages.length && (this.damages[j] < this.limit.getValue() || (fastSlot = j) != Speedmine2.mc.player.inventory.currentItem); ++j) {}
                if ((this.damages[Speedmine2.mc.player.inventory.currentItem] >= this.limit.getValue() || (this.swap.getValue() && fastSlot != -1)) && (!this.checkPacket.getValue() || !this.sentPacket)) {
                    int lastSlot = -1;
                    if (this.swap.getValue()) {
                        lastSlot = Speedmine2.mc.player.inventory.currentItem;
                        InventoryUtil.switchToHotbarSlot(fastSlot, true);
                    }
                    final boolean toAir2 = this.toAir.getValue();
                    if (this.sendStopDestroy(this.pos, this.facing, toAir2)) {
                        this.postSend(toAir2);
                    }
                    if (lastSlot != -1) {
                        InventoryUtil.switchToHotbarSlot(lastSlot, true);
                    }
                }
                return;
            }
            final int pickSlot = InventoryUtil.getPicatHotbar();
            if (this.damages[Speedmine2.mc.player.inventory.currentItem] >= this.limit.getValue() || (pickSlot >= 0 && this.damages[pickSlot] >= this.limit.getValue() && !this.pausing && this.breakBind.getValue().getKey() == -1)) {
                final int lastSlot = Speedmine2.mc.player.inventory.currentItem;
                final EntityPlayer placeTarg = this.getPlacePlayer(this.pos);
                if (placeTarg != null) {
                    final BlockPos p = getBestPlace(this.pos, placeTarg);
                    if (this.placeCrystal.getValue() && NewAC.getInstance().isEnabled() && p != null && BlockUtil.canPlaceCrystal(p)) {
                        final RayTraceResult result = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, p);
                        if (Speedmine2.mc.player.getHeldItemOffhand() != ItemStack.EMPTY && Speedmine2.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                            final CPacketPlayerTryUseItemOnBlock place = new CPacketPlayerTryUseItemOnBlock(p, result.sideHit, EnumHand.OFF_HAND, (float)result.hitVec.x, (float)result.hitVec.y, (float)result.hitVec.z);
                            final CPacketAnimation animation = new CPacketAnimation(EnumHand.OFF_HAND);
                            Speedmine2.mc.player.connection.sendPacket((Packet)place);
                            Speedmine2.mc.player.connection.sendPacket((Packet)animation);
                        }
                        else {
                            final int crystalSlot = InventoryUtil.getCrysathotbar();
                            if (crystalSlot != -1) {
                                InventoryUtil.switchToHotbarSlot(crystalSlot, false);
                                final CPacketPlayerTryUseItemOnBlock place2 = new CPacketPlayerTryUseItemOnBlock(p, result.sideHit, EnumHand.MAIN_HAND, (float)result.hitVec.x, (float)result.hitVec.y, (float)result.hitVec.z);
                                final CPacketAnimation animation2 = new CPacketAnimation(EnumHand.MAIN_HAND);
                                Speedmine2.mc.player.connection.sendPacket((Packet)place2);
                                Speedmine2.mc.player.connection.sendPacket((Packet)animation2);
                                InventoryUtil.switchToHotbarSlot(lastSlot, false);
                            }
                        }
                    }
                }
                if (this.swap.getValue()) {
                    InventoryUtil.switchToHotbarSlot(pickSlot, false);
                }
                Speedmine2.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, this.facing));
                if (this.swap.getValue()) {
                    InventoryUtil.switchToHotbarSlot(lastSlot, false);
                }
                if (this.toAir.getValue()) {
                    Speedmine2.mc.playerController.onPlayerDestroyBlock(this.pos);
                }
                this.onSendPacket();
            }
        }
    }
    
    public static EnumFacing getSide(final EntityPlayer player, final BlockPos blockPos) {
        final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (playerPos.offset(facing).equals((Object)blockPos)) {
                return facing;
            }
        }
        if (playerPos.offset(EnumFacing.UP).offset(EnumFacing.UP).equals((Object)blockPos)) {
            return EnumFacing.UP;
        }
        return EnumFacing.DOWN;
    }
    
    public static BlockPos getBestPlace(final BlockPos pos, final EntityPlayer player) {
        final EnumFacing facing = getSide(player, pos);
        if (facing == EnumFacing.UP) {
            final Block block = Speedmine2.mc.world.getBlockState(pos).getBlock();
            final Block block2 = Speedmine2.mc.world.getBlockState(pos.offset(EnumFacing.UP)).getBlock();
            if (block2 instanceof BlockAir && (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK)) {
                return pos;
            }
        }
        else {
            final BlockPos blockPos = pos.offset(facing);
            final Block block3 = Speedmine2.mc.world.getBlockState(blockPos).getBlock();
            final BlockPos blockPos2 = blockPos.down();
            final Block block4 = Speedmine2.mc.world.getBlockState(blockPos2).getBlock();
            if (block3 instanceof BlockAir && (block4 == Blocks.OBSIDIAN || block4 == Blocks.BEDROCK)) {
                return blockPos2;
            }
        }
        return null;
    }
    
    public static float getDamage(final ItemStack stack, final BlockPos pos, final boolean onGround) {
        final IBlockState state = Speedmine2.mc.world.getBlockState(pos);
        return getDamage(state, stack, pos, onGround);
    }
    
    public static float getDamage(final IBlockState state, final ItemStack stack, final BlockPos pos, final boolean onGround) {
        return getDigSpeed(stack, state, onGround) / (state.getBlockHardness((World)Speedmine2.mc.world, pos) * (canHarvestBlock(pos, stack) ? 30 : 100));
    }
    
    public static boolean canHarvestBlock(final BlockPos pos, final ItemStack stack) {
        IBlockState state = Speedmine2.mc.world.getBlockState(pos);
        state = state.getActualState((IBlockAccess)Speedmine2.mc.world, pos);
        final Block block = state.getBlock();
        if (state.getMaterial().isToolNotRequired()) {
            return true;
        }
        if (stack.isEmpty()) {
            return stack.canHarvestBlock(state);
        }
        final String tool = block.getHarvestTool(state);
        if (tool == null) {
            return stack.canHarvestBlock(state);
        }
        int toolLevel = -1;
        if (stack.getItem() instanceof IItemTool) {
            String toolClass = null;
            if (stack.getItem() instanceof ItemPickaxe) {
                toolClass = "pickaxe";
            }
            else if (stack.getItem() instanceof ItemAxe) {
                toolClass = "axe";
            }
            else if (stack.getItem() instanceof ItemSpade) {
                toolClass = "shovel";
            }
            if (tool.equals(toolClass)) {
                toolLevel = ((IItemTool)stack.getItem()).getToolMaterial().getHarvestLevel();
            }
        }
        if (toolLevel < 0) {
            return stack.canHarvestBlock(state);
        }
        return toolLevel >= block.getHarvestLevel(state);
    }
    
    private static float getDigSpeed(final ItemStack stack, final IBlockState state, final boolean onGround) {
        float digSpeed = 1.0f;
        if (!stack.isEmpty()) {
            digSpeed *= stack.getDestroySpeed(state);
        }
        if (digSpeed > 1.0f) {
            final int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            if (i > 0 && !stack.isEmpty()) {
                digSpeed += i * i + 1;
            }
        }
        if (Speedmine2.mc.player.isPotionActive(MobEffects.HASTE)) {
            digSpeed *= 1.0f + (Speedmine2.mc.player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2f;
        }
        if (Speedmine2.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            float miningFatigue = 0.0f;
            switch (Speedmine2.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) {
                case 0: {
                    miningFatigue = 0.3f;
                    break;
                }
                case 1: {
                    miningFatigue = 0.09f;
                    break;
                }
                case 2: {
                    miningFatigue = 0.0027f;
                    break;
                }
                default: {
                    miningFatigue = 8.1E-4f;
                    break;
                }
            }
            digSpeed *= miningFatigue;
        }
        if (Speedmine2.mc.player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)Speedmine2.mc.player)) {
            digSpeed /= 5.0f;
        }
        if (onGround && !Speedmine2.mc.player.onGround) {
            digSpeed /= 5.0f;
        }
        return (digSpeed < 0.0f) ? 0.0f : digSpeed;
    }
    
    public enum MineMode
    {
        Reset, 
        Packet, 
        Smart, 
        Instant, 
        Civ, 
        Damage;
    }
}
