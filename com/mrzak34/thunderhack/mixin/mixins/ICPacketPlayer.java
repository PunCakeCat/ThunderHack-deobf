//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ CPacketPlayer.class })
public interface ICPacketPlayer
{
    @Accessor("yaw")
    float getYaw();
    
    @Accessor("yaw")
    void setYaw(final float p0);
    
    @Accessor("pitch")
    float getPitch();
    
    @Accessor("pitch")
    void setPitch(final float p0);
    
    @Accessor("y")
    void setY(final double p0);
    
    @Accessor("y")
    double getY();
    
    @Accessor("onGround")
    void setOnGround(final boolean p0);
    
    @Accessor("rotating")
    boolean isRotating();
    
    @Accessor("moving")
    boolean isMoving();
}
