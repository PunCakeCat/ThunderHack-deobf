//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;

public class LevitationControl extends Module
{
    private final Setting<Integer> upAmplifier;
    private final Setting<Integer> downAmplifier;
    
    public LevitationControl() {
        super("LevitCtrl", "\u0445\u0437 \u0445\u0435\u0440\u043d\u044f \u043a\u0430\u043a\u0430\u044f\u0442\u0430", Module.Category.MOVEMENT, true, false, false);
        this.upAmplifier = (Setting<Integer>)this.register(new Setting("Alpha", (T)1, (T)1, (T)3));
        this.downAmplifier = (Setting<Integer>)this.register(new Setting("Alpha", (T)1, (T)1, (T)3));
    }
    
    public void onUpdate() {
        if (LevitationControl.mc.player.isPotionActive(MobEffects.LEVITATION)) {
            final int amplifier = Objects.requireNonNull(LevitationControl.mc.player.getActivePotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(25)))).getAmplifier();
            if (LevitationControl.mc.gameSettings.keyBindJump.isKeyDown()) {
                LevitationControl.mc.player.motionY = (0.05 * (amplifier + 1) - LevitationControl.mc.player.motionY) * 0.2 * this.upAmplifier.getValue();
            }
            else if (LevitationControl.mc.gameSettings.keyBindSneak.isKeyDown()) {
                LevitationControl.mc.player.motionY = -((0.05 * (amplifier + 1) - LevitationControl.mc.player.motionY) * 0.2 * this.downAmplifier.getValue());
            }
            else {
                LevitationControl.mc.player.motionY = 0.0;
            }
        }
    }
}
