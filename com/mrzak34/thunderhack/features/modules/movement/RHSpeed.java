//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.init.*;
import java.math.*;

public class RHSpeed extends Module
{
    private Setting<mode> Mode;
    public Setting<Float> speed;
    public Setting<Boolean> onGroundStrict;
    public Setting<Boolean> water;
    public Setting<Boolean> autoSprint;
    public Setting<Boolean> timer;
    public Setting<Boolean> nigger2;
    public Setting<Boolean> nigger;
    public Setting<Float> speed1;
    public Setting<Float> speed2;
    private boolean flip;
    private int rhh;
    private int stage;
    private double moveSpeed;
    private double distance;
    
    public RHSpeed() {
        super("Speed", "\u043d\u0435\u0434\u043e \u0441\u043f\u0438\u0434\u044b \u0438\u0437 \u0440\u0445", Module.Category.MOVEMENT, true, false, false);
        this.Mode = (Setting<mode>)this.register(new Setting("Rotation Mode", (T)mode.STRAFE));
        this.speed = (Setting<Float>)this.register(new Setting("Vanlla Speed", (T)4.0f, (T)1.0f, (T)10.0f, v -> this.Mode.getValue() == mode.VANILLA));
        this.onGroundStrict = (Setting<Boolean>)this.register(new Setting("OnGround Strict", (T)true));
        this.water = (Setting<Boolean>)this.register(new Setting("Water", (T)true));
        this.autoSprint = (Setting<Boolean>)this.register(new Setting("AutoSprint", (T)true));
        this.timer = (Setting<Boolean>)this.register(new Setting("Timer", (T)true));
        this.nigger2 = (Setting<Boolean>)this.register(new Setting("Timer2", (T)true));
        this.nigger = (Setting<Boolean>)this.register(new Setting("Timer3", (T)true));
        this.speed1 = (Setting<Float>)this.register(new Setting("Vanlla Spe-09ed", (T)4.0f, (T)0.0f, (T)2.0f));
        this.speed2 = (Setting<Float>)this.register(new Setting("Vanlla Spe90-ed", (T)4.0f, (T)0.0f, (T)2.0f));
        this.flip = false;
        this.rhh = 0;
        this.stage = 0;
        this.moveSpeed = 0.0;
        this.distance = 0.0;
    }
    
    public void onEnable() {
        try {
            this.stage = 2;
            this.distance = 0.0;
            this.moveSpeed = this.getBaseMoveSpeed();
            Thunderhack.TICK_TIMER = 1.0f;
            if (this.autoSprint.getValue() && RHSpeed.mc.player != null) {
                RHSpeed.mc.player.setSprinting(false);
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerPosLook && (this.Mode.getValue() == mode.STRAFE || this.Mode.getValue() == mode.ONGROUND)) {
            this.rhh = 6;
            if (this.Mode.getValue() == mode.ONGROUND) {
                this.stage = 2;
            }
            else {
                this.stage = 2;
                this.flip = false;
            }
            this.distance = 0.0;
            this.moveSpeed = this.getBaseMoveSpeed();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = (CPacketPlayer)e.getPacket();
            if (this.Mode.getValue() == mode.ONGROUND && this.stage == 3) {
                ((AccessorCPacketPlayer)packet).setY(packet.getY(0.0) + 0.4);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent e) {
        if (e.getStage() == 0) {
            final double d3 = RHSpeed.mc.player.posX - RHSpeed.mc.player.prevPosX;
            final double d4 = RHSpeed.mc.player.posZ - RHSpeed.mc.player.prevPosZ;
            this.distance = Math.sqrt(d3 * d3 + d4 * d4);
        }
    }
    
    @SubscribeEvent
    public void onMoveEvent(final RhMoveEvent event) {
        if (nullCheck()) {
            return;
        }
        if (RHSpeed.mc.player.isElytraFlying() || RHSpeed.mc.player.fallDistance >= 4.0f) {
            return;
        }
        if (!this.water.getValue() && (RHSpeed.mc.player.isInWater() || RHSpeed.mc.player.isInLava())) {
            return;
        }
        if (this.rhh > 0) {
            --this.rhh;
        }
        if (this.autoSprint.getValue()) {
            RHSpeed.mc.player.setSprinting(true);
        }
        if (this.Mode.getValue() == mode.STRAFE) {
            if (this.timer.getValue()) {
                Thunderhack.TICK_TIMER = 1.08f;
            }
            if (this.round(RHSpeed.mc.player.posY - (int)RHSpeed.mc.player.posY, 3) == this.round(0.138, 3)) {
                final EntityPlayerSP player = RHSpeed.mc.player;
                --player.motionY;
                event.motionY -= 0.09316090325960147;
            }
            if (this.stage == 2 && this.isMoving()) {
                if (RHSpeed.mc.player.collidedVertically) {
                    event.motionY = 0.4;
                    RHSpeed.mc.player.motionY = 0.3995;
                    this.flip = !this.flip;
                    Thunderhack.TICK_TIMER = 1.0f;
                    if (this.flip) {
                        this.moveSpeed *= 1.5499999523162842;
                    }
                    else {
                        this.moveSpeed *= 1.3949999809265137;
                    }
                }
            }
            else if (this.stage == 3 && this.isMoving()) {
                final double var = 0.66 * (this.distance - this.getBaseMoveSpeed());
                if (this.nigger.getValue()) {
                    this.moveSpeed = this.speed1.getValue();
                }
                else {
                    this.moveSpeed = this.distance - var;
                }
                if (this.timer.getValue()) {
                    if (this.flip) {
                        Thunderhack.TICK_TIMER = 1.125f;
                    }
                    else {
                        Thunderhack.TICK_TIMER = 1.0088f;
                    }
                }
            }
            else {
                if (RHSpeed.mc.world.getCollisionBoxes((Entity)RHSpeed.mc.player, RHSpeed.mc.player.getEntityBoundingBox().offset(0.0, RHSpeed.mc.player.motionY, 0.0)).size() > 0 || RHSpeed.mc.player.collidedVertically) {
                    this.stage = 1;
                }
                this.moveSpeed = this.distance - this.distance / 159.0;
            }
            float val = 1.0f;
            val *= (float)this.getBaseMoveSpeed();
            this.moveSpeed = Math.max(this.moveSpeed, val);
            final float[] dir = this.getYaw(this.moveSpeed);
            event.motionX = dir[0];
            event.motionZ = dir[1];
            ++this.stage;
        }
        else if (this.Mode.getValue() == mode.ONGROUND) {
            if (RHSpeed.mc.player.collidedHorizontally || !this.checkMove()) {
                Thunderhack.TICK_TIMER = 1.0f;
            }
            else {
                if (!this.onGroundStrict.getValue()) {
                    if (!RHSpeed.mc.player.onGround) {
                        Thunderhack.TICK_TIMER = 1.0f;
                    }
                    else if (this.stage == 2) {
                        Thunderhack.TICK_TIMER = 1.0f;
                        if (this.rhh > 0) {
                            this.moveSpeed = this.getBaseMoveSpeed();
                        }
                        this.moveSpeed *= 2.149;
                        this.stage = 3;
                    }
                    else if (this.stage == 3) {
                        if (this.timer.getValue()) {
                            Thunderhack.TICK_TIMER = Math.max(1.0f + new Random().nextFloat(), 1.2f);
                        }
                        else {
                            Thunderhack.TICK_TIMER = 1.0f;
                        }
                        this.stage = 2;
                        final double var = 0.66 * (this.distance - this.getBaseMoveSpeed());
                        if (this.nigger2.getValue()) {
                            this.moveSpeed = this.speed2.getValue();
                        }
                        else {
                            this.moveSpeed = this.distance - var;
                        }
                    }
                }
                this.setVanilaSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed()));
            }
        }
        else if (this.Mode.getValue() == mode.GROUNDSTRAFE) {
            if (RHSpeed.mc.player.collidedHorizontally || RHSpeed.mc.player.movementInput.sneak) {
                return;
            }
            if (RHSpeed.mc.player.isHandActive() && RHSpeed.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood) {
                return;
            }
            if (!this.checkMove()) {
                return;
            }
            if (RHSpeed.mc.player.onGround) {
                if (RHSpeed.mc.player.ticksExisted % 2 == 0) {
                    Thunderhack.TICK_TIMER = 1.0f;
                    this.stage = 2;
                }
                else {
                    if (this.timer.getValue()) {
                        Thunderhack.TICK_TIMER = 1.2f;
                    }
                    else {
                        Thunderhack.TICK_TIMER = 1.0f;
                    }
                    this.stage = 3;
                }
                this.moveSpeed = this.getBaseMoveSpeed();
            }
            else {
                Thunderhack.TICK_TIMER = 1.0f;
                this.stage = 0;
            }
            this.setVanilaSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed()));
        }
        else if (this.Mode.getValue() == mode.VANILLA) {
            final double speedval = this.speed.getValue() / 5.0;
            this.setVanilaSpeed(event, speedval);
        }
    }
    
    public double getBaseMoveSpeed() {
        double d = 0.2873;
        if (RHSpeed.mc.player != null && RHSpeed.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int n = RHSpeed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * (n + 1);
        }
        return d;
    }
    
    public float[] setYaw(final float yaw, final double niggers) {
        float moveForward = RHSpeed.mc.player.movementInput.moveForward;
        float moveStrafe = RHSpeed.mc.player.movementInput.moveStrafe;
        float rotationYaw = yaw;
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            final float[] ret = { 0.0f, 0.0f };
            return ret;
        }
        if (moveForward != 0.0f) {
            if (moveStrafe >= 1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
                moveStrafe = 0.0f;
            }
            else if (moveStrafe <= -1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
                moveStrafe = 0.0f;
            }
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
        final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
        final double newX = moveForward * niggers * motionX + moveStrafe * niggers * motionZ;
        final double newZ = moveForward * niggers * motionZ - moveStrafe * niggers * motionX;
        final float[] ret2 = { (float)newX, (float)newZ };
        return ret2;
    }
    
    public float[] getYaw(final double niggers) {
        final float yaw = RHSpeed.mc.player.prevRotationYaw + (RHSpeed.mc.player.rotationYaw - RHSpeed.mc.player.prevRotationYaw) * RHSpeed.mc.getRenderPartialTicks();
        return this.setYaw(yaw, niggers);
    }
    
    public boolean isMoving() {
        return RHSpeed.mc.player.movementInput.moveForward != 0.0f || RHSpeed.mc.player.movementInput.moveStrafe != 0.0f;
    }
    
    public double round(final double value, final int places) {
        final BigDecimal b = new BigDecimal(value).setScale(places, RoundingMode.HALF_UP);
        return b.doubleValue();
    }
    
    public boolean checkMove() {
        return RHSpeed.mc.player.moveForward != 0.0f || RHSpeed.mc.player.moveStrafing != 0.0f;
    }
    
    public void setVanilaSpeed(final RhMoveEvent event, final double speed) {
        float moveForward = RHSpeed.mc.player.movementInput.moveForward;
        float moveStrafe = RHSpeed.mc.player.movementInput.moveStrafe;
        float rotationYaw = RHSpeed.mc.player.rotationYaw;
        if (moveForward == 0.0f && moveStrafe == 0.0f) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
            return;
        }
        if (moveForward != 0.0f) {
            if (moveStrafe >= 1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
                moveStrafe = 0.0f;
            }
            else if (moveStrafe <= -1.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
                moveStrafe = 0.0f;
            }
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
        final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
        final double newX = moveForward * speed * motionX + moveStrafe * speed * motionZ;
        final double newZ = moveForward * speed * motionZ - moveStrafe * speed * motionX;
        event.motionX = newX;
        event.motionZ = newZ;
    }
    
    public enum mode
    {
        STRAFE, 
        ONGROUND, 
        GROUNDSTRAFE, 
        VANILLA;
    }
}
