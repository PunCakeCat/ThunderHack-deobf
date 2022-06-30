//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

import net.minecraft.client.*;

public class AnimationHelper
{
    public static float animation(final float animation, final float target, final float speedTarget) {
        float dif = (target - animation) / Math.max((float)Minecraft.getDebugFPS(), 5.0f) * 15.0f;
        if (dif > 0.0f) {
            dif = Math.max(speedTarget, dif);
            dif = Math.min(target - animation, dif);
        }
        else if (dif < 0.0f) {
            dif = Math.min(-speedTarget, dif);
            dif = Math.max(target - animation, dif);
        }
        return animation + dif;
    }
    
    public static float calculateCompensation(final float target, float current, long delta, final double speed) {
        final float diff = current - target;
        if (delta < 1L) {
            delta = 1L;
        }
        if (delta > 1000L) {
            delta = 16L;
        }
        final double dif = Math.max(speed * delta / 16.66666603088379, 0.5);
        if (diff > speed) {
            current -= (float)dif;
            if (current < target) {
                current = target;
            }
        }
        else if (diff < -speed) {
            current += (float)dif;
            if (current > target) {
                current = target;
            }
        }
        else {
            current = target;
        }
        return current;
    }
}
