//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;

public class Speedmine extends Module
{
    private Setting<Mode> mode;
    private Setting<Mode2> renderMode;
    private final Setting<Float> startDamage;
    private final Setting<Float> endDamage;
    public Setting<Boolean> display;
    public Setting<Boolean> forceRotation;
    public Setting<Boolean> startPick;
    public Setting<Boolean> onlyOnPick;
    public Setting<Boolean> ignoreChecks;
    public Setting<Boolean> spammer;
    public Setting<Boolean> silentSwitch;
    public Setting<Boolean> placeCrystal;
    public Setting<Boolean> stopEating;
    public Setting<Boolean> switchBack;
    public Setting<Boolean> switchPick;
    public Setting<Boolean> haste;
    public Setting<Boolean> continueBreaking;
    public Setting<Boolean> showProgress;
    public Setting<Boolean> continueBreakingAlways;
    public Setting<Boolean> disableContinueShift;
    private final Setting<Integer> rangeDisableBreaker;
    private final Setting<Integer> breakerTickDelay;
    private final Setting<Integer> spammerTickDelay;
    private final Setting<Integer> pickStill;
    private final Setting<Integer> pickTickSwitch;
    private final Setting<Integer> resetTickDestroy;
    private int tick;
    private int tickSpammer;
    private int oldslot;
    private int breakTick;
    private int wait;
    private BlockPos lastBlock;
    private BlockPos continueBlock;
    private boolean pickStillBol;
    private boolean ready;
    private EnumFacing direction;
    private boolean minedBefore;
    private int reseTick;
    private Vec3d lastHitVec;
    boolean broke;
    boolean needplacecrys;
    
    public Speedmine() {
        super("Speedmine", "\u043f\u0430\u043a\u0435\u0442\u043c\u0430\u0439\u043d", Module.Category.PLAYER, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Packet));
        this.renderMode = (Setting<Mode2>)this.register(new Setting("Render Mode", (T)Mode2.Both));
        this.startDamage = (Setting<Float>)this.register(new Setting("Start Damage", (T)0.1f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.Damage));
        this.endDamage = (Setting<Float>)this.register(new Setting("End Damage", (T)0.9f, (T)0.0f, (T)1.0f, v -> this.mode.getValue() == Mode.Damage));
        this.display = (Setting<Boolean>)this.register(new Setting("Display", (T)false));
        this.forceRotation = (Setting<Boolean>)this.register(new Setting("Force Rotation", (T)false, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.startPick = (Setting<Boolean>)this.register(new Setting("Start Pick", (T)false, v -> this.mode.getValue() == Mode.Breaker));
        this.onlyOnPick = (Setting<Boolean>)this.register(new Setting("Only On Pick", (T)false, v -> this.mode.getValue() == Mode.Breaker));
        this.ignoreChecks = (Setting<Boolean>)this.register(new Setting("Spammer", (T)false, v -> this.mode.getValue() == Mode.Breaker));
        this.spammer = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true, v -> this.mode.getValue() == Mode.Breaker));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("Silent Switch", (T)false, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.placeCrystal = (Setting<Boolean>)this.register(new Setting("PlaceCrystal", (T)false));
        this.stopEating = (Setting<Boolean>)this.register(new Setting("Stop Eating", (T)false, v -> (this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet) && this.silentSwitch.getValue()));
        this.switchBack = (Setting<Boolean>)this.register(new Setting("Switch Back", (T)false, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.switchPick = (Setting<Boolean>)this.register(new Setting("Switch Pick", (T)false, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.haste = (Setting<Boolean>)this.register(new Setting("Haste", (T)false, v -> this.mode.getValue() == Mode.Damage));
        this.continueBreaking = (Setting<Boolean>)this.register(new Setting("Continue Breaking", (T)true));
        this.showProgress = (Setting<Boolean>)this.register(new Setting("Show Progress", (T)true, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.continueBreakingAlways = (Setting<Boolean>)this.register(new Setting("Always Continue", (T)false, v -> this.continueBreaking.getValue()));
        this.disableContinueShift = (Setting<Boolean>)this.register(new Setting("Disable Continue Shift", (T)true, v -> this.continueBreaking.getValue()));
        this.rangeDisableBreaker = (Setting<Integer>)this.register(new Setting("Range Disable Breaker", (T)15, (T)6, (T)50, v -> this.mode.getValue() == Mode.Breaker));
        this.breakerTickDelay = (Setting<Integer>)this.register(new Setting("Breaker Delay", (T)0, (T)0, (T)75, v -> this.mode.getValue() == Mode.Breaker));
        this.spammerTickDelay = (Setting<Integer>)this.register(new Setting("Spammer Delay", (T)0, (T)0, (T)75, v -> this.mode.getValue() == Mode.Breaker));
        this.pickStill = (Setting<Integer>)this.register(new Setting("Pick Switch Still", (T)20, (T)0, (T)30, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.pickTickSwitch = (Setting<Integer>)this.register(new Setting("Pick Switch Destroy", (T)75, (T)0, (T)200, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.resetTickDestroy = (Setting<Integer>)this.register(new Setting("Tick Reset Destroy", (T)0, (T)0, (T)50, v -> this.mode.getValue() == Mode.Breaker || this.mode.getValue() == Mode.Packet));
        this.tick = 99;
        this.tickSpammer = 0;
        this.breakTick = 0;
        this.wait = 100;
        this.lastBlock = null;
        this.continueBlock = null;
        this.pickStillBol = false;
        this.ready = false;
        this.minedBefore = false;
        this.lastHitVec = null;
        this.broke = false;
        this.needplacecrys = true;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (this.lastHitVec == null || !this.forceRotation.getValue() || this.lastBlock == null) {
            return;
        }
        final Vec2f rotation = RotationUtil.getRotationTo(this.lastHitVec);
        Util.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotation.x, rotation.y, Speedmine.mc.player.onGround));
    }
    
    public void onUpdate() {
        if (this.lastBlock == null) {
            this.needplacecrys = true;
        }
        if (this.lastBlock != null && this.placeCrystal.getValue() && this.needplacecrys && Speedmine.mc.world.getBlockState(this.lastBlock).getBlock() == Blocks.AIR) {
            final Vec3d vec = new Vec3d((Vec3i)this.lastBlock).add(0.0, -1.0, 0.0);
            SilentRotaionUtil.lookAtVector(vec);
            final int crystalSlot = CrystalUtils.getCrystalSlot();
            if (crystalSlot == -1) {
                Command.sendMessage("No crystals found!");
                this.toggle();
                return;
            }
            if (Speedmine.mc.player.inventory.currentItem != crystalSlot) {
                Speedmine.mc.player.inventory.currentItem = crystalSlot;
                Speedmine.mc.playerController.updateController();
            }
            BlockUtils.rightClickBlock(this.lastBlock.add(0.0, -1.0, 0.0), Speedmine.mc.player.getPositionVector().add(0.0, (double)Speedmine.mc.player.getEyeHeight(), 0.0), EnumHand.MAIN_HAND, EnumFacing.UP, true);
            this.needplacecrys = false;
        }
        if (this.continueBreaking.getValue() && this.continueBlock != null) {
            if (this.disableContinueShift.getValue() && Speedmine.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.continueBlock = null;
            }
            else {
                if (BlockUtil.getBlockgs(this.continueBlock) instanceof BlockAir) {
                    this.broke = true;
                }
                if (!(BlockUtil.getBlockgs(this.continueBlock) instanceof BlockAir) && (this.broke || this.continueBreakingAlways.getValue())) {
                    Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
                    Speedmine.mc.playerController.onPlayerDamageBlock(this.continueBlock, EnumFacing.UP);
                    this.broke = false;
                }
            }
        }
        if (this.tick != 99 && this.tick++ >= this.wait) {
            final int prev = Speedmine.mc.player.inventory.currentItem;
            this.ready = true;
            if (this.switchPick.getValue() && this.oldslot != -1) {
                if (this.silentSwitch.getValue()) {
                    Speedmine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
                    Speedmine.mc.playerController.updateController();
                    if (this.lastBlock != null && this.direction != null) {
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.lastBlock, this.direction));
                    }
                    if (this.stopEating.getValue() && Speedmine.mc.player.isHandActive()) {
                        Speedmine.mc.player.stopActiveHand();
                    }
                }
                else {
                    Speedmine.mc.player.inventory.currentItem = this.oldslot;
                }
                this.oldslot = -1;
            }
            if (!this.pickStillBol) {
                if (this.pickTickSwitch.getValue() != 0 && this.switchPick.getValue()) {
                    this.wait = this.pickStill.getValue();
                    this.tick = 0;
                    this.oldslot = prev;
                    this.pickStillBol = true;
                }
                else {
                    this.tick = 99;
                    if (this.silentSwitch.getValue()) {
                        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(prev));
                        Speedmine.mc.playerController.updateController();
                    }
                    else {
                        Speedmine.mc.player.inventory.currentItem = prev;
                    }
                }
            }
            else {
                this.tick = 99;
            }
        }
        Speedmine.mc.playerController.blockHitDelay = 0;
        if (this.haste.getValue()) {
            final PotionEffect effect = new PotionEffect(MobEffects.HASTE, 80950, 1, false, false);
            Speedmine.mc.player.addPotionEffect(new PotionEffect(effect));
        }
        if (!this.haste.getValue() && Speedmine.mc.player.isPotionActive(MobEffects.HASTE)) {
            Speedmine.mc.player.removePotionEffect(MobEffects.HASTE);
        }
        if ((!this.onlyOnPick.getValue() || Speedmine.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) && this.mode.getValue() == Mode.Breaker && this.lastBlock != null && this.spammer.getValue() && this.tickSpammer++ >= this.spammerTickDelay.getValue()) {
            this.tickSpammer = 0;
            if (BlockUtil.getBlockgs(this.lastBlock) instanceof BlockAir) {
                this.minedBefore = true;
                this.reseTick = 0;
                this.lastHitVec = null;
            }
            if (this.minedBefore) {
                System.out.println("Not this shit");
                if (this.resetTickDestroy.getValue() != 0 && this.reseTick++ >= this.resetTickDestroy.getValue() && !(BlockUtil.getBlockgs(this.lastBlock) instanceof BlockAir)) {
                    Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.lastBlock, this.direction));
                    this.breakerBreak();
                    this.reseTick = 0;
                    this.minedBefore = false;
                    return;
                }
                if (this.ignoreChecks.getValue() || !(BlockUtil.getBlockgs(this.lastBlock) instanceof BlockAir)) {
                    if (this.forceRotation.getValue()) {
                        this.setVec3d(this.lastBlock, this.direction);
                    }
                    if (Speedmine.mc.player.getDistanceSq(this.lastBlock) >= this.rangeDisableBreaker.getValue()) {
                        this.lastBlock = null;
                    }
                    else {
                        this.breakerBreak();
                    }
                }
            }
        }
    }
    
    private void breakerBreak() {
        final Item item = Speedmine.mc.player.inventory.getCurrentItem().getItem();
        int oldSlot = -1;
        if (!(item instanceof ItemPickaxe) && this.minedBefore && (this.switchBack.getValue() || this.switchPick.getValue())) {
            oldSlot = Speedmine.mc.player.inventory.currentItem;
            final int slot = InventoryUtil.findFirstItemSlot((Class<? extends Item>)ItemPickaxe.class, 0, 9);
            if (slot != -1) {
                Speedmine.mc.player.inventory.currentItem = slot;
            }
        }
        Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.lastBlock, this.direction));
        if (oldSlot != -1 && this.switchBack.getValue()) {
            this.tick = 0;
            this.oldslot = oldSlot;
            if (!this.minedBefore || this.mode.getValue() == Mode.Packet) {
                this.wait = this.pickTickSwitch.getValue();
                this.pickStillBol = !this.switchBack.getValue();
            }
            else {
                this.wait = this.pickStill.getValue();
            }
        }
    }
    
    @SubscribeEvent
    public void onBreakBlock(final DamageBlockEvent event) {
        if (Speedmine.mc.world == null || Speedmine.mc.player == null) {
            return;
        }
        if (!this.canBreak(event.getBlockPos()) || event.getBlockPos() == null) {
            return;
        }
        if (this.forceRotation.getValue()) {
            this.setVec3d(event.getBlockPos(), event.getEnumFacing());
        }
        if (this.continueBreaking.getValue()) {
            this.continueBlock = event.getBlockPos();
        }
        switch (this.mode.getValue()) {
            case Packet: {
                Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                event.setCanceled(true);
                this.lastBlock = event.getBlockPos();
                this.direction = event.getEnumFacing();
                this.oldslot = InventoryUtil.findFirstItemSlot((Class<? extends Item>)ItemPickaxe.class, 0, 9);
                this.tick = 0;
                this.wait = this.pickTickSwitch.getValue();
                this.ready = false;
                this.pickStillBol = false;
                break;
            }
            case Damage: {
                if (Speedmine.mc.playerController.curBlockDamageMP < this.startDamage.getValue()) {
                    Speedmine.mc.playerController.curBlockDamageMP = this.startDamage.getValue();
                }
                if (Speedmine.mc.playerController.curBlockDamageMP >= this.endDamage.getValue()) {
                    Speedmine.mc.playerController.curBlockDamageMP = 1.0f;
                    break;
                }
                break;
            }
            case Instant: {
                Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
                Speedmine.mc.playerController.onPlayerDestroyBlock(event.getBlockPos());
                Speedmine.mc.world.setBlockToAir(event.getBlockPos());
                break;
            }
            case Breaker: {
                this.breakerAlgo(event);
                break;
            }
        }
    }
    
    private void setVec3d(final BlockPos pos, final EnumFacing side) {
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        this.lastHitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
    }
    
    private void breakerAlgo(final DamageBlockEvent event) {
        if (this.lastBlock == null || event.getBlockPos().x != this.lastBlock.x || event.getBlockPos().y != this.lastBlock.y || event.getBlockPos().z != this.lastBlock.z) {
            if (this.startPick.getValue()) {
                final int pick = InventoryUtil.findFirstItemSlot((Class<? extends Item>)ItemPickaxe.class, 0, 9);
                if (pick != -1) {
                    Speedmine.mc.player.inventory.currentItem = pick;
                }
            }
            this.minedBefore = false;
            Speedmine.mc.player.swingArm(EnumHand.MAIN_HAND);
            Speedmine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
            this.lastBlock = event.getBlockPos();
            this.direction = event.getEnumFacing();
        }
        if (this.breakerTickDelay.getValue() <= this.breakTick++) {
            this.breakerBreak();
            event.setCanceled(true);
            this.breakTick = 0;
        }
        this.wait = this.pickTickSwitch.getValue();
        this.ready = false;
        this.tick = 0;
        if (this.switchPick.getValue()) {
            this.oldslot = InventoryUtil.findFirstItemSlot((Class<? extends Item>)ItemPickaxe.class, 0, 9);
            this.pickStillBol = !this.switchBack.getValue();
        }
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = Speedmine.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)Speedmine.mc.world, pos) != -1.0f;
    }
    
    public void onDisable() {
        if (this.haste.getValue()) {
            Speedmine.mc.player.removePotionEffect(MobEffects.HASTE);
        }
        this.breakTick = 0;
        this.continueBlock = null;
    }
    
    public void onRender3D(final Render3DEvent event) {
        if (this.lastBlock != null) {
            if (Speedmine.mc.player.getDistanceSq(this.lastBlock) >= this.rangeDisableBreaker.getValue()) {
                this.lastBlock = null;
            }
            else if (this.display.getValue()) {
                if (this.mode.getValue() == Mode.Breaker || (this.mode.getValue() == Mode.Packet && !(BlockUtil.getBlockgs(this.lastBlock) instanceof BlockAir)) || this.mode.getValue() == Mode.Packet) {
                    RenderUtil.drawBlockOutline(this.lastBlock, new Color(175, 175, 255), 2.0f, false);
                }
                if (this.showProgress.getValue()) {
                    final int prognum = (int)(this.tick / (float)this.pickTickSwitch.getValue() * 100.0f / Blocks.OBSIDIAN.blockHardness * Speedmine.mc.world.getBlockState(this.lastBlock).getBlock().blockHardness);
                    GlStateManager.pushMatrix();
                    try {
                        NewAC.glBillboardDistanceScaled(this.lastBlock.getX() + 0.5f, this.lastBlock.getY() + 0.5f, this.lastBlock.getZ() + 0.5f, (EntityPlayer)Speedmine.mc.player, 1.0f);
                    }
                    catch (Exception ex) {}
                    GlStateManager.disableDepth();
                    GlStateManager.disableLighting();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    Speedmine.mc.fontRenderer.drawStringWithShadow(String.valueOf(prognum), (float)(int)(-(Speedmine.mc.fontRenderer.getStringWidth(String.valueOf(prognum)) / 2.0)), -4.0f, -1);
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    GlStateManager.popMatrix();
                }
                else {
                    this.lastBlock = null;
                }
            }
        }
    }
    
    public enum Mode
    {
        Packet, 
        Damage, 
        Instant, 
        Breaker;
    }
    
    public enum Mode2
    {
        Outline, 
        Fill, 
        Both;
    }
}
