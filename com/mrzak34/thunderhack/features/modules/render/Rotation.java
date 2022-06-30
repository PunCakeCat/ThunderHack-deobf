//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;

public class Rotation
{
    public float yaw;
    public float pitch;
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void meth1(final float var1) {
        this.yaw = var1;
    }
    
    public Rotation c(final float var1, final float var2) {
        return new Rotation(var1, var2);
    }
    
    public float meth4() {
        return this.pitch;
    }
    
    public static Rotation c(final Rotation var0, float var1, float var2, final int var3, final Object var4) {
        if ((var3 & 0x1) != 0x0) {
            var1 = var0.yaw;
        }
        if ((var3 & 0x2) != 0x0) {
            var2 = var0.pitch;
        }
        return var0.c(var1, var2);
    }
    
    public float meth2() {
        return this.yaw;
    }
    
    public Rotation(final float var1, final float var2) {
        this.yaw = var1;
        this.pitch = var2;
    }
    
    public void setPitch(final float var1) {
        this.pitch = var1;
    }
    
    public boolean c(final Rotation var1) {
        return var1.yaw == this.yaw && var1.pitch == this.pitch;
    }
    
    public void setEntityRotation(final EntityPlayer var1) {
        float var2 = this.yaw;
        boolean var3 = false;
        if (!Float.isNaN(var2)) {
            var2 = this.pitch;
            var3 = false;
            if (!Float.isNaN(var2)) {
                this.fixedSensitivity(Util.mc.gameSettings.mouseSensitivity);
                var1.rotationYaw = this.yaw;
                var1.rotationPitch = this.pitch;
            }
        }
    }
    
    @Override
    public int hashCode() {
        return Float.hashCode(this.yaw) * 31 + Float.hashCode(this.pitch);
    }
    
    public float meth3() {
        return this.yaw;
    }
    
    @Override
    public String toString() {
        return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ")";
    }
    
    public void fixedSensitivity(final float var1) {
        final float var2 = var1 * 0.6f + 0.2f;
        final float var3 = var2 * var2 * var2 * 1.2f;
        this.yaw -= this.yaw % var3;
        this.pitch -= this.pitch % var3;
    }
    
    @Override
    public boolean equals(final Object var1) {
        if (this != var1) {
            if (var1 instanceof Rotation) {
                final Rotation var2 = (Rotation)var1;
                if (Float.compare(this.yaw, var2.yaw) == 0 && Float.compare(this.pitch, var2.pitch) == 0) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
