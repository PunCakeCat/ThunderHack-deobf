//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketPlayerPosLook.class })
public interface ISPacketPlayerPosLook
{
    @Accessor("yaw")
    void setYaw(final float p0);
    
    @Accessor("pitch")
    void setPitch(final float p0);
}
