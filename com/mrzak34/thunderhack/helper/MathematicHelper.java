//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

import java.math.*;
import net.minecraft.util.math.*;

public class MathematicHelper
{
    public static BigDecimal round(final float f, final int times) {
        BigDecimal bd = new BigDecimal(Float.toString(f));
        bd = bd.setScale(times, RoundingMode.HALF_UP);
        return bd;
    }
    
    public static int getMiddle(final int old, final int newValue) {
        return (old + newValue) / 2;
    }
    
    public static double round(final double num, final double increment) {
        final double v = Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static float checkAngle(final float one, final float two, final float three) {
        float f = MathHelper.wrapDegrees(one - two);
        if (f < -three) {
            f = -three;
        }
        if (f >= three) {
            f = three;
        }
        return one - f;
    }
    
    public static float clamp(float val, final float min, final float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }
    
    public static float randomizeFloat(final float min, final float max) {
        return (float)(min + (max - min) * Math.random());
    }
}
