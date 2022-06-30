//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.*;

public class LegitStrafe extends Module
{
    private final Setting<Float> time1;
    public Setting<Boolean> sp;
    Setting<Double> jumpHeight;
    
    public LegitStrafe() {
        super("LegitStrafe", "\u043c\u043e\u0436\u043d\u043e \u0438 \u043b\u0435\u0433\u0438\u0442\u043d\u0435\u0435", Category.FUNNYGAME, true, false, false);
        this.time1 = (Setting<Float>)this.register(new Setting("speed", (T)1.0f, (T)0.1f, (T)5.0f));
        this.sp = (Setting<Boolean>)this.register(new Setting("NoJumpBoost", (T)true));
        this.jumpHeight = (Setting<Double>)this.register(new Setting("Jump Height", (T)0.41, (T)0.0, (T)1.0, v -> this.sp.getValue()));
    }
    
    @Override
    public void onUpdate() {
        if (LegitStrafe.mc.gameSettings.keyBindForward.isKeyDown() || LegitStrafe.mc.gameSettings.keyBindBack.isKeyDown() || LegitStrafe.mc.gameSettings.keyBindLeft.isKeyDown() || LegitStrafe.mc.gameSettings.keyBindRight.isKeyDown()) {
            LegitStrafe.mc.player.setSprinting(true);
            double speedY = this.jumpHeight.getValue();
            if (this.sp.getValue() && LegitStrafe.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                speedY += (LegitStrafe.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                LegitStrafe.mc.player.motionY = speedY;
            }
            if (LegitStrafe.mc.player.onGround) {
                LegitStrafe.mc.player.jump();
                Thunderhack.TICK_TIMER = this.time1.getValue();
            }
            else {
                Thunderhack.TICK_TIMER = 1.088f;
            }
        }
    }
    
    @Override
    public void onDisable() {
        Thunderhack.TICK_TIMER = 1.0f;
        LegitStrafe.mc.player.setSprinting(false);
    }
}
