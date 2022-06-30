//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoSlow extends Module
{
    private final Setting<Float> speedmult;
    public Setting<Integer> speeeed;
    public Setting<Boolean> auto_sprint;
    public Setting<Boolean> on_water;
    public Setting<Boolean> backward;
    public Setting<Boolean> auto_jump;
    public Setting<Boolean> bypass;
    
    public NoSlow() {
        super("FunnGamSpeed", "\u043b\u0443\u0447\u0448\u0438\u0439 \u043d\u0430 \u0444\u0430\u043d\u0438\u0433\u0435\u0439\u043c\u0435-\u043f\u043e\u043a\u0430 \u0447\u0442\u043e", Category.FUNNYGAME, true, false, false);
        this.speedmult = (Setting<Float>)this.register(new Setting("PotionSpAmpl", (T)1.0f, (T)0.1f, (T)2.0f));
        this.speeeed = (Setting<Integer>)this.register(new Setting("sdad", (T)2873, (T)0, (T)10000));
        this.auto_sprint = (Setting<Boolean>)this.register(new Setting("Auto Sprint", (T)true));
        this.on_water = (Setting<Boolean>)this.register(new Setting("On Water", (T)true));
        this.backward = (Setting<Boolean>)this.register(new Setting("Backwards", (T)true));
        this.auto_jump = (Setting<Boolean>)this.register(new Setting("Auto Jump", (T)true));
        this.bypass = (Setting<Boolean>)this.register(new Setting("NoSlow", (T)true));
    }
    
    @Override
    public void onUpdate() {
        if (NoSlow.mc.player.isRiding()) {
            return;
        }
        if ((NoSlow.mc.player.isInWater() || NoSlow.mc.player.isInLava()) && !this.on_water.getValue()) {
            return;
        }
        if (NoSlow.mc.player.moveForward != 0.0f || NoSlow.mc.player.moveStrafing != 0.0f) {
            if (NoSlow.mc.player.moveForward < 0.0f && !this.backward.getValue()) {
                return;
            }
            if (this.auto_sprint.getValue()) {
                NoSlow.mc.player.setSprinting(true);
            }
            if (NoSlow.mc.player.onGround) {
                if (this.auto_jump.getValue()) {
                    NoSlow.mc.player.motionY = 0.4050000011920929;
                }
                final float yaw = this.get_rotation_yaw() * 0.017453292f;
                final EntityPlayerSP player = NoSlow.mc.player;
                player.motionX -= MathHelper.sin(yaw) * 0.2f;
                final EntityPlayerSP player2 = NoSlow.mc.player;
                player2.motionZ += MathHelper.cos(yaw) * 0.2f;
            }
        }
        if (NoSlow.mc.gameSettings.keyBindJump.isKeyDown() && NoSlow.mc.player.onGround) {
            NoSlow.mc.player.motionY = 0.4050000011920929;
        }
    }
    
    @SubscribeEvent
    public void onMove(final EventMove event) {
        if ((NoSlow.mc.player.isInWater() || NoSlow.mc.player.isInLava()) && !this.on_water.getValue()) {
            return;
        }
        if (NoSlow.mc.player.isSneaking() || NoSlow.mc.player.isOnLadder() || NoSlow.mc.player.isInWeb || NoSlow.mc.player.isInLava() || NoSlow.mc.player.isInWater() || NoSlow.mc.player.capabilities.isFlying) {
            return;
        }
        float player_speed = this.speeeed.getValue() / 10000.0f;
        float move_forward = NoSlow.mc.player.movementInput.moveForward;
        float move_strafe = NoSlow.mc.player.movementInput.moveStrafe;
        float rotation_yaw = NoSlow.mc.player.rotationYaw;
        if (NoSlow.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amp = NoSlow.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            player_speed *= this.speedmult.getValue() * (amp + 1);
        }
        if (!this.bypass.getValue()) {
            player_speed *= 1.0064f;
        }
        if (move_forward == 0.0f && move_strafe == 0.0f) {
            event.set_x(0.0);
            event.set_z(0.0);
        }
        else {
            if (move_forward != 0.0f) {
                if (move_strafe > 0.0f) {
                    rotation_yaw += ((move_forward > 0.0f) ? -45 : 45);
                }
                else if (move_strafe < 0.0f) {
                    rotation_yaw += ((move_forward > 0.0f) ? 45 : -45);
                }
                move_strafe = 0.0f;
                if (move_forward > 0.0f) {
                    move_forward = 1.0f;
                }
                else if (move_forward < 0.0f) {
                    move_forward = -1.0f;
                }
            }
            event.set_x(move_forward * player_speed * Math.cos(Math.toRadians(rotation_yaw + 90.0f)) + move_strafe * player_speed * Math.sin(Math.toRadians(rotation_yaw + 90.0f)));
            event.set_z(move_forward * player_speed * Math.sin(Math.toRadians(rotation_yaw + 90.0f)) - move_strafe * player_speed * Math.cos(Math.toRadians(rotation_yaw + 90.0f)));
        }
        event.setCanceled(true);
    }
    
    private float get_rotation_yaw() {
        float rotation_yaw = NoSlow.mc.player.rotationYaw;
        if (NoSlow.mc.player.moveForward < 0.0f) {
            rotation_yaw += 180.0f;
        }
        float n = 1.0f;
        if (NoSlow.mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (NoSlow.mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (NoSlow.mc.player.moveStrafing > 0.0f) {
            rotation_yaw -= 90.0f * n;
        }
        if (NoSlow.mc.player.moveStrafing < 0.0f) {
            rotation_yaw += 90.0f * n;
        }
        return rotation_yaw * 0.017453292f;
    }
}
