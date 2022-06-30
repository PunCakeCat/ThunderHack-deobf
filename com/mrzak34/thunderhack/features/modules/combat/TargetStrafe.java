//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TargetStrafe extends Module
{
    private float wrap;
    private boolean switchDir;
    public final Setting<Float> reversedDistance;
    public final Setting<Float> speedIfUsing;
    public final Setting<Float> range;
    public final Setting<Float> boostValue;
    public Setting<Integer> boostTicks;
    public final Setting<Float> spd;
    public Setting<Boolean> boost;
    public Setting<Boolean> reversed;
    public Setting<Boolean> autoJump;
    public Setting<Boolean> smartStrafe;
    public Setting<Boolean> usingItemCheck;
    public Setting<Boolean> speedpot;
    public final Setting<Float> spdd;
    float speedy;
    
    public TargetStrafe() {
        super("TargetStrafe", "\u0412\u0440\u0430\u0449\u0430\u0442\u044c\u0441\u044f \u0432\u043e\u043a\u0440\u0443\u0433 \u0446\u0435\u043b\u0438", Category.COMBAT, true, false, false);
        this.wrap = 0.0f;
        this.switchDir = true;
        this.reversedDistance = (Setting<Float>)this.register(new Setting("Reversed Distance", (T)3.0f, (T)1.0f, (T)6.0f));
        this.speedIfUsing = (Setting<Float>)this.register(new Setting("Speed if using", (T)0.1f, (T)0.1f, (T)2.0f));
        this.range = (Setting<Float>)this.register(new Setting("Strafe Distance", (T)2.4f, (T)0.1f, (T)6.0f));
        this.boostValue = (Setting<Float>)this.register(new Setting("Boost Value", (T)0.5f, (T)0.1f, (T)4.0f));
        this.boostTicks = (Setting<Integer>)this.register(new Setting("Boost Ticks", (T)8, (T)0, (T)9));
        this.spd = (Setting<Float>)this.register(new Setting("Strafe Speed", (T)0.23f, (T)0.1f, (T)2.0f));
        this.boost = (Setting<Boolean>)this.register(new Setting("DamageBoost", (T)true));
        this.reversed = (Setting<Boolean>)this.register(new Setting("Reversed", (T)false));
        this.autoJump = (Setting<Boolean>)this.register(new Setting("AutoJump", (T)true));
        this.smartStrafe = (Setting<Boolean>)this.register(new Setting("Smart Strafe", (T)true));
        this.usingItemCheck = (Setting<Boolean>)this.register(new Setting("Speed if using", (T)false));
        this.speedpot = (Setting<Boolean>)this.register(new Setting("Speed if Potion ", (T)true));
        this.spdd = (Setting<Float>)this.register(new Setting("Strafe Speed", (T)0.23f, (T)0.1f, (T)2.0f, v -> this.speedpot.getValue()));
        this.speedy = 1.0f;
    }
    
    @Override
    public void onEnable() {
        this.wrap = 0.0f;
        this.switchDir = true;
        super.onEnable();
    }
    
    public boolean needToSwitch(final double x, final double z) {
        if (TargetStrafe.mc.gameSettings.keyBindLeft.isPressed() || TargetStrafe.mc.gameSettings.keyBindRight.isPressed()) {
            return true;
        }
        int i = (int)(TargetStrafe.mc.player.posY + 4.0);
        while (i >= 0) {
            final BlockPos playerPos = new BlockPos(x, (double)i, z);
            if (!TargetStrafe.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.LAVA)) {
                if (!TargetStrafe.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.FIRE)) {
                    if (TargetStrafe.mc.world.isAirBlock(playerPos)) {
                        --i;
                        continue;
                    }
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    
    private float toDegree(final double x, final double z) {
        return (float)(Math.atan2(z - TargetStrafe.mc.player.posZ, x - TargetStrafe.mc.player.posX) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    @Override
    public void onUpdate() {
        if (KillauraOld.target == null) {
            return;
        }
    }
    
    @Override
    public void onToggle() {
        try {
            Thunderhack.TICK_TIMER = 1.0f;
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final UpdateWalkingPlayerEvent event) {
        if (KillauraOld.target == null) {
            return;
        }
        if (TargetStrafe.mc.player.getDistance((Entity)KillauraOld.target) <= 4.0f) {
            if (EntityUtil.getHealth((Entity)KillauraOld.target) > 0.0f && this.autoJump.getValue() && KillauraOld.getInstance().isEnabled() && TargetStrafe.mc.player.onGround) {
                TargetStrafe.mc.player.jump();
            }
            if (EntityUtil.getHealth((Entity)KillauraOld.target) > 0.0f) {
                final EntityLivingBase target = (EntityLivingBase)KillauraOld.target;
                if (target == null || TargetStrafe.mc.player.ticksExisted < 20) {
                    return;
                }
                if (this.speedpot.getValue()) {
                    if (TargetStrafe.mc.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionFromResourceLocation("speed")))) {
                        this.speedy = this.spdd.getValue();
                    }
                    else {
                        this.speedy = this.spd.getValue();
                    }
                }
                else {
                    this.speedy = this.spd.getValue();
                }
                final float speed = (TargetStrafe.mc.player.hurtTime > this.boostTicks.getValue() && this.boost.getValue() && !TargetStrafe.mc.player.onGround) ? this.boostValue.getValue() : ((TargetStrafe.mc.gameSettings.keyBindUseItem.isKeyDown() && this.usingItemCheck.getValue()) ? this.speedIfUsing.getValue() : this.speedy);
                this.wrap = (float)Math.atan2(TargetStrafe.mc.player.posZ - target.posZ, TargetStrafe.mc.player.posX - target.posX);
                this.wrap += (this.switchDir ? (speed / TargetStrafe.mc.player.getDistance((Entity)target)) : (-(speed / TargetStrafe.mc.player.getDistance((Entity)target))));
                double x = target.posX + this.range.getValue() * Math.cos(this.wrap);
                double z = target.posZ + this.range.getValue() * Math.sin(this.wrap);
                if (this.smartStrafe.getValue() && this.needToSwitch(x, z)) {
                    this.switchDir = !this.switchDir;
                    this.wrap += 2.0f * (this.switchDir ? (speed / TargetStrafe.mc.player.getDistance((Entity)target)) : (-(speed / TargetStrafe.mc.player.getDistance((Entity)target))));
                    x = target.posX + this.range.getValue() * Math.cos(this.wrap);
                    z = target.posZ + this.range.getValue() * Math.sin(this.wrap);
                }
                if (TargetStrafe.mc.player.hurtTime > this.boostTicks.getValue() && this.boost.getValue() && !TargetStrafe.mc.player.onGround) {
                    final EntityPlayerSP player = TargetStrafe.mc.player;
                    player.jumpMovementFactor *= 60.0f;
                }
                final float searchValue = (this.reversed.getValue() && TargetStrafe.mc.player.getDistance((Entity)KillauraOld.target) < this.reversedDistance.getValue()) ? -90.0f : 0.0f;
                final float reversedValue = (!TargetStrafe.mc.gameSettings.keyBindLeft.isKeyDown() && !TargetStrafe.mc.gameSettings.keyBindRight.isKeyDown()) ? searchValue : 0.0f;
                TargetStrafe.mc.player.motionX = speed * -Math.sin((float)Math.toRadians(this.toDegree(x + reversedValue, z + reversedValue)));
                TargetStrafe.mc.player.motionZ = speed * Math.cos((float)Math.toRadians(this.toDegree(x + reversedValue, z + reversedValue)));
            }
        }
    }
}
