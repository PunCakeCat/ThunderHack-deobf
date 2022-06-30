//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ElytraFlight extends Module
{
    public static ElytraFlight INSTANCE;
    private Setting<Mode> mode;
    private Setting<Boolean> groundSafety;
    private Setting<Float> packetDelay;
    private Setting<Float> staticDelay;
    private Setting<Float> timeout;
    public Setting<Boolean> stopMotion;
    public Setting<Boolean> freeze;
    public Setting<Boolean> cruiseControl;
    public Setting<Float> minUpSpeed;
    private Setting<Boolean> autoSwitch;
    public Setting<Float> factor;
    private Setting<Integer> minSpeed;
    public Setting<Float> upFactor;
    public Setting<Float> downFactor;
    public Setting<Boolean> forceHeight;
    private Setting<Integer> manualHeight;
    private Setting<Float> triggerHeight;
    public Setting<Float> speed;
    private Setting<Float> sneakDownSpeed;
    private Setting<Boolean> instantFly;
    private Setting<Boolean> boostTimer;
    public Setting<Boolean> speedLimit;
    public Setting<Float> maxSpeed;
    public Setting<Boolean> noDrag;
    private static boolean hasElytra;
    private boolean rSpeed;
    private double curSpeed;
    public double tempSpeed;
    private double height;
    private final Random random;
    private Timer instantFlyTimer;
    private Timer staticTimer;
    private Timer rocketTimer;
    private Timer strictTimer;
    private boolean isJumping;
    private boolean hasTouchedGround;
    
    public ElytraFlight() {
        super("ElytraFlight", "\u0431\u0443\u0441\u0442\u044b \u0434\u043b\u044f 2\u0431", Module.Category.MOVEMENT, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.BOOST));
        this.groundSafety = (Setting<Boolean>)this.register(new Setting("GroundSafety", (T)false, v -> this.mode.getValue() == Mode.FIREWORK));
        this.packetDelay = (Setting<Float>)this.register(new Setting("Limit", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.mode.getValue() == Mode.BOOST));
        this.staticDelay = (Setting<Float>)this.register(new Setting("Delay", (T)5.0f, (T)0.1f, (T)20.0f, v -> this.mode.getValue() == Mode.BOOST));
        this.timeout = (Setting<Float>)this.register(new Setting("Timeout", (T)0.5f, (T)0.1f, (T)1.0f, v -> this.mode.getValue() == Mode.BOOST));
        this.stopMotion = (Setting<Boolean>)this.register(new Setting("StopMotion", (T)true, v -> this.mode.getValue() == Mode.BOOST));
        this.freeze = (Setting<Boolean>)this.register(new Setting("Freeze", (T)false, v -> this.mode.getValue() == Mode.BOOST));
        this.cruiseControl = (Setting<Boolean>)this.register(new Setting("CruiseControl", (T)false));
        this.minUpSpeed = (Setting<Float>)this.register(new Setting("MinUpSpeed", (T)0.5f, (T)0.1f, (T)5.0f, v -> this.mode.getValue() == Mode.BOOST && this.cruiseControl.getValue()));
        this.autoSwitch = (Setting<Boolean>)this.register(new Setting("AutoSwitch", (T)false, v -> this.mode.getValue() == Mode.FIREWORK));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (T)1.5f, (T)0.1f, (T)50.0f));
        this.minSpeed = (Setting<Integer>)this.register(new Setting("MinSpeed", (T)20, (T)1, (T)50, v -> this.mode.getValue() == Mode.FIREWORK));
        this.upFactor = (Setting<Float>)this.register(new Setting("UpFactor", (T)1.0f, (T)0.0f, (T)10.0f));
        this.downFactor = (Setting<Float>)this.register(new Setting("DownFactor", (T)1.0f, (T)0.0f, (T)10.0f));
        this.forceHeight = (Setting<Boolean>)this.register(new Setting("ForceHeight", (T)false, v -> this.mode.getValue() == Mode.FIREWORK || (this.mode.getValue() == Mode.BOOST && this.cruiseControl.getValue())));
        this.manualHeight = (Setting<Integer>)this.register(new Setting("Height", (T)121, (T)1, (T)256, v -> (this.mode.getValue() == Mode.FIREWORK || (this.mode.getValue() == Mode.BOOST && this.cruiseControl.getValue())) && this.forceHeight.getValue()));
        this.triggerHeight = (Setting<Float>)this.register(new Setting("TriggerHeight", (T)0.3f, (T)0.05f, (T)1.0f, v -> this.mode.getValue() == Mode.FIREWORK && this.groundSafety.getValue()));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)1.0f, (T)0.1f, (T)10.0f, v -> this.mode.getValue() == Mode.CONTROL));
        this.sneakDownSpeed = (Setting<Float>)this.register(new Setting("DownSpeed", (T)1.0f, (T)0.1f, (T)10.0f, v -> this.mode.getValue() == Mode.CONTROL));
        this.instantFly = (Setting<Boolean>)this.register(new Setting("InstantFly", (T)true));
        this.boostTimer = (Setting<Boolean>)this.register(new Setting("Timer", (T)true, v -> this.mode.getValue() == Mode.BOOST));
        this.speedLimit = (Setting<Boolean>)this.register(new Setting("SpeedLimit", (T)true, v -> this.mode.getValue() != Mode.FIREWORK));
        this.maxSpeed = (Setting<Float>)this.register(new Setting("MaxSpeed", (T)2.5f, (T)0.1f, (T)10.0f, v -> this.speedLimit.getValue() && this.mode.getValue() != Mode.FIREWORK));
        this.noDrag = new Setting<Boolean>("NoDrag", false, v -> this.mode.getValue() != Mode.FIREWORK);
        this.random = new Random();
        this.instantFlyTimer = new Timer();
        this.staticTimer = new Timer();
        this.rocketTimer = new Timer();
        this.strictTimer = new Timer();
        this.isJumping = false;
        this.hasTouchedGround = false;
    }
    
    public void onEnable() {
        this.rSpeed = false;
        this.curSpeed = 0.0;
        if (ElytraFlight.mc.player != null) {
            this.height = ElytraFlight.mc.player.posY;
            if (!ElytraFlight.mc.player.isCreative()) {
                ElytraFlight.mc.player.capabilities.allowFlying = false;
            }
            ElytraFlight.mc.player.capabilities.isFlying = false;
        }
        this.isJumping = false;
        ElytraFlight.hasElytra = false;
    }
    
    public void onDisable() {
        if (ElytraFlight.mc.player != null) {
            if (!ElytraFlight.mc.player.isCreative()) {
                ElytraFlight.mc.player.capabilities.allowFlying = false;
            }
            ElytraFlight.mc.player.capabilities.isFlying = false;
        }
        Thunderhack.TICK_TIMER = 1.0f;
        ElytraFlight.hasElytra = false;
    }
    
    public void onUpdate() {
        if (ElytraFlight.mc.world == null || ElytraFlight.mc.player == null) {
            return;
        }
        if (ElytraFlight.mc.player.onGround) {
            this.hasTouchedGround = true;
        }
        if (!this.cruiseControl.getValue()) {
            this.height = ElytraFlight.mc.player.posY;
        }
        for (final ItemStack is : ElytraFlight.mc.player.getArmorInventoryList()) {
            if (is.getItem() instanceof ItemElytra) {
                ElytraFlight.hasElytra = true;
                break;
            }
            ElytraFlight.hasElytra = false;
        }
        if (this.strictTimer.passedMs(1500L) && !this.strictTimer.passedMs(2000L)) {
            Thunderhack.TICK_TIMER = 1.0f;
        }
        if (!ElytraFlight.mc.player.isElytraFlying()) {
            if (this.hasTouchedGround && this.boostTimer.getValue() && !ElytraFlight.mc.player.onGround) {
                Thunderhack.TICK_TIMER = 0.3f;
            }
            if (!ElytraFlight.mc.player.onGround && this.instantFly.getValue() && ElytraFlight.mc.player.motionY < 0.0) {
                if (!this.instantFlyTimer.passedMs((long)(1000.0f * this.timeout.getValue()))) {
                    return;
                }
                this.instantFlyTimer.reset();
                ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                this.hasTouchedGround = false;
                this.strictTimer.reset();
            }
            return;
        }
        if (ElytraFlight.mc.player == null) {
            return;
        }
        if (this.mode.getValue() != Mode.FIREWORK) {
            return;
        }
        if (ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
            this.height += this.upFactor.getValue() * 0.5;
        }
        else if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
            this.height -= this.downFactor.getValue() * 0.5;
        }
        if (this.forceHeight.getValue()) {
            this.height = this.manualHeight.getValue();
        }
        final Vec3d motionVector = new Vec3d(ElytraFlight.mc.player.motionX, ElytraFlight.mc.player.motionY, ElytraFlight.mc.player.motionZ);
        final double bps = motionVector.length() * 20.0;
        final double horizSpeed = Math.sqrt(ElytraFlight.mc.player.motionX * ElytraFlight.mc.player.motionX + ElytraFlight.mc.player.motionZ * ElytraFlight.mc.player.motionZ);
        final double horizPct = MathHelper.clamp(horizSpeed / 1.7, 0.0, 1.0);
        final double heightPct = 1.0 - Math.sqrt(horizPct);
        final double minAngle = 0.6;
        if (horizPct >= 0.5 || ElytraFlight.mc.player.posY > this.height + 1.0) {
            final double pitch = -((45.0 - minAngle) * heightPct + minAngle);
            final double diff = (this.height + 1.0 - ElytraFlight.mc.player.posY) * 2.0;
            final double heightDiffPct = MathHelper.clamp(Math.abs(diff), 0.0, 1.0);
            final double pDist = -Math.toDegrees(Math.atan2(Math.abs(diff), horizSpeed * 30.0)) * Math.signum(diff);
            final double adjustment = (pDist - pitch) * heightDiffPct;
            ElytraFlight.mc.player.rotationPitch = (float)pitch;
            final EntityPlayerSP player = ElytraFlight.mc.player;
            player.rotationPitch += (float)adjustment;
            ElytraFlight.mc.player.prevRotationPitch = ElytraFlight.mc.player.rotationPitch;
        }
        if (this.rocketTimer.passedMs((long)(1000.0f * this.factor.getValue()))) {
            final double heightDiff = this.height - ElytraFlight.mc.player.posY;
            boolean shouldBoost = (heightDiff > 0.25 && heightDiff < 1.0) || bps < this.minSpeed.getValue();
            if (this.groundSafety.getValue()) {
                final Block bottomBlock = ElytraFlight.mc.world.getBlockState(new BlockPos((Entity)ElytraFlight.mc.player).down()).getBlock();
                if (bottomBlock != Blocks.AIR && !(bottomBlock instanceof BlockLiquid) && ElytraFlight.mc.player.getEntityBoundingBox().minY - Math.floor(ElytraFlight.mc.player.getEntityBoundingBox().minY) > this.triggerHeight.getValue()) {
                    shouldBoost = true;
                }
            }
            if (this.autoSwitch.getValue() && shouldBoost && ElytraFlight.mc.player.getHeldItemMainhand().getItem() != Items.FIREWORKS) {
                for (int l = 0; l < 9; ++l) {
                    if (ElytraFlight.mc.player.inventory.getStackInSlot(l).getItem() == Items.FIREWORKS) {
                        ElytraFlight.mc.player.inventory.currentItem = l;
                        ElytraFlight.mc.playerController.updateController();
                        break;
                    }
                }
            }
            if (ElytraFlight.mc.player.getHeldItemMainhand().getItem() == Items.FIREWORKS && shouldBoost) {
                ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.rocketTimer.reset();
            }
        }
    }
    
    public static boolean isHasElytra() {
        return ElytraFlight.hasElytra;
    }
    
    private boolean checkIfBlockInBB(final int minY) {
        for (int iX = MathHelper.floor(ElytraFlight.mc.player.getEntityBoundingBox().minX); iX < MathHelper.ceil(ElytraFlight.mc.player.getEntityBoundingBox().maxX); ++iX) {
            for (int iZ = MathHelper.floor(ElytraFlight.mc.player.getEntityBoundingBox().minZ); iZ < MathHelper.ceil(ElytraFlight.mc.player.getEntityBoundingBox().maxZ); ++iZ) {
                final IBlockState state = ElytraFlight.mc.world.getBlockState(new BlockPos(iX, minY, iZ));
                if (state.getBlock() != Blocks.AIR) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @SubscribeEvent
    public void onElytra(final ElytraEvent event) {
        if (ElytraFlight.mc.world == null || ElytraFlight.mc.player == null || !ElytraFlight.hasElytra || !ElytraFlight.mc.player.isElytraFlying()) {
            return;
        }
        if (this.mode.getValue() == Mode.FIREWORK) {
            return;
        }
        if ((event.getEntity() == ElytraFlight.mc.player && ElytraFlight.mc.player.isServerWorld()) || (ElytraFlight.mc.player.canPassengerSteer() && !ElytraFlight.mc.player.isInWater()) || (ElytraFlight.mc.player != null && ElytraFlight.mc.player.capabilities.isFlying && !ElytraFlight.mc.player.isInLava()) || (ElytraFlight.mc.player.capabilities.isFlying && ElytraFlight.mc.player.isElytraFlying())) {
            event.setCanceled(true);
            if (this.mode.getValue() != Mode.BOOST) {
                final Vec3d lookVec = ElytraFlight.mc.player.getLookVec();
                final float pitch = ElytraFlight.mc.player.rotationPitch * 0.017453292f;
                final double lookDist = Math.sqrt(lookVec.x * lookVec.x + lookVec.z * lookVec.z);
                final double motionDist = Math.sqrt(ElytraFlight.mc.player.motionX * ElytraFlight.mc.player.motionX + ElytraFlight.mc.player.motionZ * ElytraFlight.mc.player.motionZ);
                final double lookVecDist = lookVec.length();
                float cosPitch = MathHelper.cos(pitch);
                cosPitch = (float)(cosPitch * (double)cosPitch * Math.min(1.0, lookVecDist / 0.4));
                if (this.mode.getValue() != Mode.CONTROL) {
                    final EntityPlayerSP player = ElytraFlight.mc.player;
                    player.motionY += -0.08 + cosPitch * (0.06 / this.downFactor.getValue());
                }
                if (this.mode.getValue() == Mode.CONTROL) {
                    if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        ElytraFlight.mc.player.motionY = -this.sneakDownSpeed.getValue();
                    }
                    else if (!ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                        ElytraFlight.mc.player.motionY = -3.0E-14 * this.downFactor.getValue();
                    }
                }
                else if (this.mode.getValue() != Mode.CONTROL && ElytraFlight.mc.player.motionY < 0.0 && lookDist > 0.0) {
                    final double downSpeed = ElytraFlight.mc.player.motionY * -0.1 * cosPitch;
                    final EntityPlayerSP player2 = ElytraFlight.mc.player;
                    player2.motionY += downSpeed;
                    final EntityPlayerSP player3 = ElytraFlight.mc.player;
                    player3.motionX += lookVec.x * downSpeed / lookDist * this.factor.getValue();
                    final EntityPlayerSP player4 = ElytraFlight.mc.player;
                    player4.motionZ += lookVec.z * downSpeed / lookDist * this.factor.getValue();
                }
                if (pitch < 0.0f && this.mode.getValue() != Mode.CONTROL) {
                    final double rawUpSpeed = motionDist * -MathHelper.sin(pitch) * 0.04;
                    final EntityPlayerSP player5 = ElytraFlight.mc.player;
                    player5.motionY += rawUpSpeed * 3.2 * this.upFactor.getValue();
                    final EntityPlayerSP player6 = ElytraFlight.mc.player;
                    player6.motionX -= lookVec.x * rawUpSpeed / lookDist;
                    final EntityPlayerSP player7 = ElytraFlight.mc.player;
                    player7.motionZ -= lookVec.z * rawUpSpeed / lookDist;
                }
                else if (this.mode.getValue() == Mode.CONTROL && ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (motionDist > this.upFactor.getValue() / this.upFactor.getMax()) {
                        final double rawUpSpeed = motionDist * 0.01325;
                        final EntityPlayerSP player8 = ElytraFlight.mc.player;
                        player8.motionY += rawUpSpeed * 3.2;
                        final EntityPlayerSP player9 = ElytraFlight.mc.player;
                        player9.motionX -= lookVec.x * rawUpSpeed / lookDist;
                        final EntityPlayerSP player10 = ElytraFlight.mc.player;
                        player10.motionZ -= lookVec.z * rawUpSpeed / lookDist;
                    }
                    else {
                        final double[] dir = directionSpeed(this.speed.getValue());
                        ElytraFlight.mc.player.motionX = dir[0];
                        ElytraFlight.mc.player.motionZ = dir[1];
                    }
                }
                if (lookDist > 0.0) {
                    final EntityPlayerSP player11 = ElytraFlight.mc.player;
                    player11.motionX += (lookVec.x / lookDist * motionDist - ElytraFlight.mc.player.motionX) * 0.1;
                    final EntityPlayerSP player12 = ElytraFlight.mc.player;
                    player12.motionZ += (lookVec.z / lookDist * motionDist - ElytraFlight.mc.player.motionZ) * 0.1;
                }
                if (this.mode.getValue() == Mode.CONTROL && !ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                    final double[] dir = directionSpeed(this.speed.getValue());
                    ElytraFlight.mc.player.motionX = dir[0];
                    ElytraFlight.mc.player.motionZ = dir[1];
                }
                if (!this.noDrag.getValue()) {
                    final EntityPlayerSP player13 = ElytraFlight.mc.player;
                    player13.motionX *= 0.9900000095367432;
                    final EntityPlayerSP player14 = ElytraFlight.mc.player;
                    player14.motionY *= 0.9800000190734863;
                    final EntityPlayerSP player15 = ElytraFlight.mc.player;
                    player15.motionZ *= 0.9900000095367432;
                }
                final double finalDist = Math.sqrt(ElytraFlight.mc.player.motionX * ElytraFlight.mc.player.motionX + ElytraFlight.mc.player.motionZ * ElytraFlight.mc.player.motionZ);
                if (this.speedLimit.getValue() && finalDist > this.maxSpeed.getValue()) {
                    final EntityPlayerSP player16 = ElytraFlight.mc.player;
                    player16.motionX *= this.maxSpeed.getValue() / finalDist;
                    final EntityPlayerSP player17 = ElytraFlight.mc.player;
                    player17.motionZ *= this.maxSpeed.getValue() / finalDist;
                }
                ElytraFlight.mc.player.move(MoverType.SELF, ElytraFlight.mc.player.motionX, ElytraFlight.mc.player.motionY, ElytraFlight.mc.player.motionZ);
            }
            else {
                final boolean shouldBoost = false;
                float moveForward = ElytraFlight.mc.player.movementInput.moveForward;
                if (this.cruiseControl.getValue()) {
                    if (ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                        this.height += this.upFactor.getValue() * 0.5;
                    }
                    else if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        this.height -= this.downFactor.getValue() * 0.5;
                    }
                    if (this.forceHeight.getValue()) {
                        this.height = this.manualHeight.getValue();
                    }
                    final double horizSpeed = Math.sqrt(ElytraFlight.mc.player.motionX * ElytraFlight.mc.player.motionX + ElytraFlight.mc.player.motionZ * ElytraFlight.mc.player.motionZ);
                    final double horizPct = MathHelper.clamp(horizSpeed / 1.7, 0.0, 1.0);
                    final double heightPct = 1.0 - Math.sqrt(horizPct);
                    final double minAngle = 0.6;
                    if (horizSpeed >= this.minUpSpeed.getValue() && this.instantFlyTimer.passedMs((long)(2000.0f * this.packetDelay.getValue()))) {
                        final double pitch2 = -((45.0 - minAngle) * heightPct + minAngle);
                        final double diff = (this.height + 1.0 - ElytraFlight.mc.player.posY) * 2.0;
                        final double heightDiffPct = MathHelper.clamp(Math.abs(diff), 0.0, 1.0);
                        final double pDist = -Math.toDegrees(Math.atan2(Math.abs(diff), horizSpeed * 30.0)) * Math.signum(diff);
                        final double adjustment = (pDist - pitch2) * heightDiffPct;
                        ElytraFlight.mc.player.rotationPitch = (float)pitch2;
                        final EntityPlayerSP player18 = ElytraFlight.mc.player;
                        player18.rotationPitch += (float)adjustment;
                        ElytraFlight.mc.player.prevRotationPitch = ElytraFlight.mc.player.rotationPitch;
                    }
                    else {
                        ElytraFlight.mc.player.rotationPitch = 0.25f;
                        ElytraFlight.mc.player.prevRotationPitch = 0.25f;
                        moveForward = 1.0f;
                    }
                }
                final Vec3d vec3d = ElytraFlight.mc.player.getLookVec();
                final float f = ElytraFlight.mc.player.rotationPitch * 0.017453292f;
                final double d6 = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
                final double d7 = Math.sqrt(ElytraFlight.mc.player.motionX * ElytraFlight.mc.player.motionX + ElytraFlight.mc.player.motionZ * ElytraFlight.mc.player.motionZ);
                final double d8 = vec3d.length();
                float f2 = MathHelper.cos(f);
                f2 = (float)(f2 * (double)f2 * Math.min(1.0, d8 / 0.4));
                final EntityPlayerSP player19 = ElytraFlight.mc.player;
                player19.motionY += -0.08 + f2 * 0.06;
                if (ElytraFlight.mc.player.motionY < 0.0 && d6 > 0.0) {
                    final double d9 = ElytraFlight.mc.player.motionY * -0.1 * f2;
                    final EntityPlayerSP player20 = ElytraFlight.mc.player;
                    player20.motionY += d9;
                    final EntityPlayerSP player21 = ElytraFlight.mc.player;
                    player21.motionX += vec3d.x * d9 / d6;
                    final EntityPlayerSP player22 = ElytraFlight.mc.player;
                    player22.motionZ += vec3d.z * d9 / d6;
                }
                if (f < 0.0f) {
                    final double d10 = d7 * -MathHelper.sin(f) * 0.04;
                    final EntityPlayerSP player23 = ElytraFlight.mc.player;
                    player23.motionY += d10 * 3.2;
                    final EntityPlayerSP player24 = ElytraFlight.mc.player;
                    player24.motionX -= vec3d.x * d10 / d6;
                    final EntityPlayerSP player25 = ElytraFlight.mc.player;
                    player25.motionZ -= vec3d.z * d10 / d6;
                }
                if (d6 > 0.0) {
                    final EntityPlayerSP player26 = ElytraFlight.mc.player;
                    player26.motionX += (vec3d.x / d6 * d7 - ElytraFlight.mc.player.motionX) * 0.1;
                    final EntityPlayerSP player27 = ElytraFlight.mc.player;
                    player27.motionZ += (vec3d.z / d6 * d7 - ElytraFlight.mc.player.motionZ) * 0.1;
                }
                if (!this.noDrag.getValue()) {
                    final EntityPlayerSP player28 = ElytraFlight.mc.player;
                    player28.motionX *= 0.9900000095367432;
                    final EntityPlayerSP player29 = ElytraFlight.mc.player;
                    player29.motionY *= 0.9800000190734863;
                    final EntityPlayerSP player30 = ElytraFlight.mc.player;
                    player30.motionZ *= 0.9900000095367432;
                }
                final float yaw = ElytraFlight.mc.player.rotationYaw * 0.017453292f;
                if (f > 0.0f && (ElytraFlight.mc.player.motionY < 0.0 || shouldBoost)) {
                    if (moveForward != 0.0f && this.instantFlyTimer.passedMs((long)(2000.0f * this.packetDelay.getValue())) && this.staticTimer.passedMs((long)(1000.0f * this.staticDelay.getValue()))) {
                        if (this.stopMotion.getValue()) {
                            ElytraFlight.mc.player.motionX = 0.0;
                            ElytraFlight.mc.player.motionZ = 0.0;
                        }
                        this.instantFlyTimer.reset();
                        ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    }
                    else if (!this.instantFlyTimer.passedMs((long)(2000.0f * this.packetDelay.getValue()))) {
                        final EntityPlayerSP player31 = ElytraFlight.mc.player;
                        player31.motionX -= moveForward * Math.sin(yaw) * this.factor.getValue() / 20.0;
                        final EntityPlayerSP player32 = ElytraFlight.mc.player;
                        player32.motionZ += moveForward * Math.cos(yaw) * this.factor.getValue() / 20.0;
                        this.staticTimer.reset();
                    }
                }
                final double finalDist2 = Math.sqrt(ElytraFlight.mc.player.motionX * ElytraFlight.mc.player.motionX + ElytraFlight.mc.player.motionZ * ElytraFlight.mc.player.motionZ);
                if (this.speedLimit.getValue() && finalDist2 > this.maxSpeed.getValue()) {
                    final EntityPlayerSP player33 = ElytraFlight.mc.player;
                    player33.motionX *= this.maxSpeed.getValue() / finalDist2;
                    final EntityPlayerSP player34 = ElytraFlight.mc.player;
                    player34.motionZ *= this.maxSpeed.getValue() / finalDist2;
                }
                if (this.freeze.getValue() && Keyboard.isKeyDown(56)) {
                    ElytraFlight.mc.player.setVelocity(0.0, 0.0, 0.0);
                }
                ElytraFlight.mc.player.move(MoverType.SELF, ElytraFlight.mc.player.motionX, ElytraFlight.mc.player.motionY, ElytraFlight.mc.player.motionZ);
            }
        }
    }
    
    public static double[] directionSpeed(final double speed) {
        float forward = ElytraFlight.mc.player.movementInput.moveForward;
        float side = ElytraFlight.mc.player.movementInput.moveStrafe;
        float yaw = ElytraFlight.mc.player.prevRotationYaw + (ElytraFlight.mc.player.rotationYaw - ElytraFlight.mc.player.prevRotationYaw) * ElytraFlight.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    static {
        ElytraFlight.INSTANCE = new ElytraFlight();
        ElytraFlight.hasElytra = false;
    }
    
    private enum Mode
    {
        BOOST, 
        CONTROL, 
        FIREWORK;
    }
}
