//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class NewSpeed extends Module
{
    private Setting<mode> Mode;
    public static boolean isSpeeding;
    private float lastYaw;
    private final Timer timings;
    private boolean settedAir;
    private int speedingTime;
    private double speedingDouble;
    private double moveSpeed;
    
    public NewSpeed() {
        super("NewSpeed", "NewSpeed", Module.Category.FUNNYGAME, true, false, false);
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.AAC));
        this.timings = new Timer();
    }
    
    public void onDisable() {
        Util.mc.player.motionX = 0.0;
        Util.mc.player.motionZ = 0.0;
        Thunderhack.TICK_TIMER = 1.0f;
        Util.mc.player.speedInAir = 0.02f;
        NewSpeed.isSpeeding = false;
        this.settedAir = false;
        this.speedingTime = 0;
        this.speedingDouble = 0.0;
        super.onDisable();
    }
    
    public void onUpdate() {
        if ((Util.mc.player.moveForward == 0.0f && Util.mc.player.moveStrafing == 0.0f) || NewSpeed.mc.gameSettings.keyBindJump.pressed || Util.mc.player.isInWater() || NewSpeed.mc.world.getBlockState(Util.mc.player.getPosition().add(0.0, -0.5, 0.0)).getBlock() instanceof BlockIce || NewSpeed.mc.world.getBlockState(Util.mc.player.getPosition().add(0.0, -0.5, 0.0)).getBlock() instanceof BlockPackedIce) {
            NewSpeed.isSpeeding = false;
            Thunderhack.TICK_TIMER = 1.0f;
            this.settedAir = false;
            this.speedingTime = 0;
            this.speedingDouble = 0.0;
            if (!NewSpeed.mc.gameSettings.keyBindJump.pressed) {
                Util.mc.player.motionX = 0.0;
                Util.mc.player.motionZ = 0.0;
            }
            return;
        }
        NewSpeed.isSpeeding = !Util.mc.player.onGround;
        if (this.Mode.getValue() == mode.AAC) {
            if (Util.mc.player.onGround) {
                Util.mc.player.jump();
                final EntityPlayerSP player = Util.mc.player;
                player.motionX *= 1.013;
                final EntityPlayerSP player2 = Util.mc.player;
                player2.motionZ *= 1.013;
                Thunderhack.TICK_TIMER = 1.15f;
            }
            else {
                Thunderhack.TICK_TIMER = 1.0f;
                if (Util.mc.player.fallDistance < 0.1f) {
                    Util.mc.player.speedInAir = 0.0215f;
                }
                else if (Util.mc.player.fallDistance >= 0.15f) {
                    Util.mc.player.speedInAir = 0.0216f;
                    final EntityPlayerSP player3 = Util.mc.player;
                    player3.motionY += 0.014;
                }
                else if (Util.mc.player.fallDistance > 0.025f) {
                    Thunderhack.TICK_TIMER = 1.0f;
                    Util.mc.player.speedInAir = 0.024f;
                }
            }
        }
        else if (this.Mode.getValue() == mode.AACBhop320) {
            if (Util.mc.player.onGround) {
                Thunderhack.TICK_TIMER = 1.0f;
                Util.mc.player.jump();
            }
            else if (Util.mc.player.fallDistance <= 0.15f) {
                Util.mc.player.speedInAir = 0.02f;
            }
            else if (Util.mc.player.fallDistance >= 0.15f && Util.mc.player.fallDistance <= 0.025f) {
                Thunderhack.TICK_TIMER = 1.0f;
                Util.mc.player.speedInAir = 0.02f;
            }
            else {
                Thunderhack.TICK_TIMER = 1.05f;
            }
        }
        else if (this.Mode.getValue() == mode.AACLowhop320) {
            this.onAAC320();
        }
        else if (this.Mode.getValue() == mode.AACStrafeBhop320) {
            if (KillauraOld.target != null) {
                if (Util.mc.player.onGround) {
                    Util.mc.player.motionY = 0.41;
                }
                return;
            }
            if (Util.mc.player.onGround) {
                Util.mc.player.jump();
                this.speedingDouble = 0.0;
                ++this.speedingTime;
                this.moveSpeed = 0.356;
            }
            else {
                this.moveSpeed = this.getBaseMoveSpeed() / 1.04;
                if (this.speedingTime > 1) {
                    if (Util.mc.player.fallDistance >= 0.1f) {
                        this.moveSpeed = 0.25;
                    }
                    if (Util.mc.player.fallDistance >= 0.125f) {
                        this.moveSpeed = 0.274;
                    }
                }
                else {
                    this.moveSpeed = 0.1;
                }
            }
            this.shouldSpeed();
        }
        else if (this.Mode.getValue() == mode.MotionJump) {
            if (KillauraOld.target != null || Util.mc.player.collidedHorizontally) {
                if (Util.mc.player.onGround) {
                    Util.mc.player.motionY = 0.4;
                }
                return;
            }
            if (Util.mc.player.onGround) {
                Util.mc.player.motionY = 0.35;
            }
            else {
                this.moveSpeed = this.getBaseMoveSpeed() / 1.05;
                if (Util.mc.player.fallDistance > 0.1f) {
                    final EntityPlayerSP player4 = Util.mc.player;
                    player4.motionY -= 0.02;
                }
                if (Util.mc.player.fallDistance >= 0.2f && Util.mc.player.fallDistance < 0.5f) {
                    final EntityPlayerSP player5 = Util.mc.player;
                    player5.motionY -= 0.01;
                }
                this.shouldSpeed();
            }
        }
        else if (this.Mode.getValue() == mode.FastJumpDown) {
            if (Util.mc.player.motionY < -0.1) {
                return;
            }
            if (Util.mc.player.onGround) {
                Util.mc.player.motionY = 0.1;
                this.moveSpeed = 0.4;
            }
            else {
                this.moveSpeed = this.getBaseMoveSpeed();
            }
            this.shouldSpeed();
        }
        else if (this.Mode.getValue() == mode.HopFast) {
            if (Util.mc.player.motionY < -0.1) {
                return;
            }
            if (Util.mc.player.onGround) {
                Util.mc.player.motionY = 0.15;
                this.moveSpeed = 0.5;
            }
            else {
                this.moveSpeed = this.getBaseMoveSpeed() + Util.mc.player.fallDistance + 0.30000001192092896;
            }
            this.shouldSpeed();
        }
        else if (this.Mode.getValue() == mode.AACStrafeBhop321) {
            if (Util.mc.player.onGround) {
                Thunderhack.TICK_TIMER = 1.3f;
                if (this.speedingDouble < 0.7) {
                    this.speedingDouble += 0.15;
                }
                else {
                    Util.mc.player.fallDistance = 0.018f;
                }
                Util.mc.player.jump();
                this.moveSpeed = this.speedingDouble;
            }
            else {
                Thunderhack.TICK_TIMER = 1.05f;
                Util.mc.player.speedInAir = 0.034f;
                this.moveSpeed = this.getBaseMoveSpeed() + Math.abs(this.speedingDouble / 1.1) + (Math.random() - 0.2);
            }
            this.shouldSpeed();
        }
        else if (this.Mode.getValue() == mode.MineSuchtStrafeBhop) {
            if (Util.mc.player.onGround) {
                Thunderhack.TICK_TIMER = 1.0f;
                Util.mc.player.jump();
                this.moveSpeed = 0.4;
            }
            else {
                this.moveSpeed = 0.26;
                if (Util.mc.player.fallDistance > 0.01f) {
                    Thunderhack.TICK_TIMER = 2.0f;
                }
                if (Util.mc.player.fallDistance >= 0.23f) {
                    Thunderhack.TICK_TIMER = 1.0f;
                }
            }
            this.shouldSpeed();
        }
        else if (this.Mode.getValue() == mode.AACBhop321) {
            if (Util.mc.player.onGround) {
                Util.mc.player.motionY = 0.39;
                Thunderhack.TICK_TIMER = 1.0f;
                Util.mc.player.speedInAir = 0.02f;
                if (this.speedingDouble < 0.4) {
                    this.speedingDouble += 0.1;
                }
                this.moveSpeed = Util.mc.player.getAIMoveSpeed() + this.speedingDouble / 1.2 + 0.011;
                this.shouldSpeed();
            }
            else {
                this.moveSpeed = Util.mc.player.getAIMoveSpeed() + this.speedingDouble / 3.1;
                if (Util.mc.player.fallDistance >= 0.03f) {
                    final EntityPlayerSP player6 = Util.mc.player;
                    player6.motionY += 0.015;
                }
                Thunderhack.TICK_TIMER = 1.0f;
                this.shouldSpeed();
                Util.mc.player.jumpMovementFactor = 0.03f;
                if (Util.mc.player.fallDistance >= 0.25f) {
                    final EntityPlayerSP player7 = Util.mc.player;
                    player7.motionX *= 1.036;
                    final EntityPlayerSP player8 = Util.mc.player;
                    player8.motionZ *= 1.036;
                    Thunderhack.TICK_TIMER = 1.18f;
                }
                if (Util.mc.player.fallDistance >= 0.3f) {
                    Util.mc.player.speedInAir = 0.0232f;
                    Thunderhack.TICK_TIMER = 1.0f;
                }
            }
        }
        else if (this.Mode.getValue() == mode.HiveBhop) {
            if (Util.mc.player.onGround) {
                this.moveSpeed = this.getBaseMoveSpeed();
                Thunderhack.TICK_TIMER = 1.0f;
                Util.mc.player.motionY = 0.399;
                this.settedAir = false;
                ++this.speedingTime;
                if (this.speedingTime > 1) {
                    this.moveSpeed = 0.2726;
                }
                else {
                    this.moveSpeed = 0.1;
                }
            }
            else {
                if (this.speedingTime < 2) {
                    return;
                }
                if (Util.mc.player.hurtTime > 0) {
                    return;
                }
                if (!this.settedAir) {
                    this.moveSpeed = 0.3721;
                    this.settedAir = true;
                }
                this.moveSpeed -= 0.00247;
                if (this.distLastYaw() > 10.0) {
                    this.moveSpeed -= 0.0019;
                }
            }
            this.shouldSpeed();
        }
        else if (this.Mode.getValue() == mode.NCPLowhop) {
            if (Util.mc.player.onGround) {
                if (!this.settedAir) {
                    Util.mc.player.motionY = 0.05;
                }
                else {
                    Util.mc.player.motionY = 0.035;
                }
                this.settedAir = !this.settedAir;
                this.moveSpeed = 0.23;
            }
            else if (this.settedAir) {
                this.moveSpeed = 0.25;
            }
            else {
                this.moveSpeed = 0.15;
            }
            this.shouldSpeed();
        }
        else if (this.Mode.getValue() == mode.Boost) {
            if (Util.mc.player.onGround) {
                ++this.speedingTime;
                if (this.speedingTime >= 15) {
                    if (this.speedingTime >= 20) {
                        this.speedingTime = 0;
                    }
                    return;
                }
                Thunderhack.TICK_TIMER = (float)(Math.random() + 0.4000000059604645);
                if (Thunderhack.TICK_TIMER < 1.0f) {
                    Thunderhack.TICK_TIMER = 1.0f;
                }
                if (this.timings.passedMs(250L)) {
                    this.settedAir = false;
                    Util.mc.player.motionY = 0.03;
                    move(Util.mc.player.rotationYaw, 0.215f);
                    this.timings.reset();
                }
                else {
                    move(Util.mc.player.rotationYaw, 0.15f);
                }
            }
            else {
                move(Util.mc.player.rotationYaw, 0.25f);
                if (!this.settedAir) {
                    Util.mc.player.jumpMovementFactor = 0.03f;
                    this.settedAir = true;
                }
            }
        }
        else if (this.Mode.getValue() == mode.RedeskyTest) {
            if (Util.mc.player.onGround) {
                this.moveSpeed = this.getBaseMoveSpeed() - 0.03;
                Util.mc.player.motionY = 0.4;
                Thunderhack.TICK_TIMER = 1.0f;
            }
            else {
                final double fallDistance = Util.mc.player.fallDistance;
                if (fallDistance >= 0.699999988079071 && fallDistance < 0.8999999761581421) {
                    Thunderhack.TICK_TIMER = 1.1f;
                    this.moveSpeed = this.getBaseMoveSpeed() + 0.1;
                    Util.mc.player.speedInAir = 0.2f;
                }
                if (fallDistance < 0.699999988079071) {
                    Thunderhack.TICK_TIMER = 0.834534f;
                    Util.mc.player.speedInAir = 0.1f;
                    this.moveSpeed = this.getBaseMoveSpeed();
                }
            }
            this.shouldSpeed();
        }
        this.lastYaw = Util.mc.player.rotationYaw;
    }
    
    private double distLastYaw() {
        return Math.abs(Util.mc.player.rotationYaw - this.lastYaw);
    }
    
    private void onAAC320() {
        final BlockPos pos = new BlockPos(Util.mc.player.posX, Util.mc.player.posY - 1.0, Util.mc.player.posZ);
        if (!NewSpeed.mc.gameSettings.keyBindForward.isKeyDown() || NewSpeed.mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
            Thunderhack.TICK_TIMER = 1.0f;
            return;
        }
        Thunderhack.TICK_TIMER = 1.03f;
        if (Util.mc.player.onGround) {
            Util.mc.player.jump();
            Util.mc.player.motionY = 0.38510000705718994;
            final EntityPlayerSP player = Util.mc.player;
            player.motionX *= 1.0134999752044678;
            final EntityPlayerSP player2 = Util.mc.player;
            player2.motionZ *= 1.0134999752044678;
        }
        else if (Util.mc.player.motionY > 0.0) {
            final EntityPlayerSP player3 = Util.mc.player;
            player3.motionY -= 0.014999999664723873;
            final EntityPlayerSP player4 = Util.mc.player;
            player4.motionX *= 1.0110000371932983;
            final EntityPlayerSP player5 = Util.mc.player;
            player5.motionZ *= 1.0110000371932983;
        }
        else {
            final EntityPlayerSP player6 = Util.mc.player;
            player6.motionY -= 0.014999990351498127;
            if (Util.mc.player.motionY <= -0.282) {
                final EntityPlayerSP player7 = Util.mc.player;
                player7.motionY -= 0.5;
                final EntityPlayerSP player8 = Util.mc.player;
                player8.motionX *= 1.0049999952316284;
                final EntityPlayerSP player9 = Util.mc.player;
                player9.motionZ *= 1.0049999952316284;
            }
        }
    }
    
    public void shouldSpeed() {
        final MovementInput movementInput = Util.mc.player.movementInput;
        float forward = movementInput.moveForward;
        float strafe = movementInput.moveStrafe;
        float yaw = Util.mc.player.rotationYaw;
        if (forward == 0.0f && strafe == 0.0f) {
            Util.mc.player.motionX = 0.0;
            Util.mc.player.motionZ = 0.0;
        }
        else if (forward != 0.0f) {
            if (strafe > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
                strafe = 0.0f;
            }
            else if (strafe < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        Util.mc.player.motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
        Util.mc.player.motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
        Util.mc.player.stepHeight = 0.6f;
        if (forward == 0.0f && strafe == 0.0f) {
            Util.mc.player.motionX = 0.0;
            Util.mc.player.motionZ = 0.0;
        }
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Util.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Util.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static void move(final float yaw, final float multiplyer) {
        final double moveX = -Math.sin(Math.toRadians(yaw)) * multiplyer;
        final double moveZ = Math.cos(Math.toRadians(yaw)) * multiplyer;
        NewSpeed.mc.player.motionX = moveX;
        NewSpeed.mc.player.motionZ = moveZ;
    }
    
    public enum mode
    {
        AAC, 
        AACBhop320, 
        AACStrafeBhop320, 
        AACLowhop320, 
        MotionJump, 
        FastJumpDown, 
        HopFast, 
        AACStrafeBhop321, 
        MineSuchtStrafeBhop, 
        AACBhop321, 
        HiveBhop, 
        NCPLowhop, 
        Boost, 
        RedeskyTest;
    }
}
