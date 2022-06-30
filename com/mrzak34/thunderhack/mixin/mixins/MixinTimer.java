//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Timer.class })
public class MixinTimer
{
    @Shadow
    public float elapsedPartialTicks;
    
    @Inject(method = { "updateTimer" }, at = { @At(value = "FIELD", target = "net/minecraft/util/Timer.elapsedPartialTicks:F", ordinal = 1) })
    public void updateTimer(final CallbackInfo info) {
        this.elapsedPartialTicks *= Thunderhack.TICK_TIMER;
    }
}
