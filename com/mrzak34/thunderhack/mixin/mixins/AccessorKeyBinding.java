//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ KeyBinding.class })
public interface AccessorKeyBinding
{
    @Accessor("pressed")
    void setPressed(final boolean p0);
}
