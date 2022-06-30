//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.toasts.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiToast.class })
public class MixinGuiToast
{
    @Inject(method = { "drawToast" }, at = { @At("HEAD") }, cancellable = true)
    public void drawToastHook(final ScaledResolution resolution, final CallbackInfo info) {
        if (NoRender.getInstance().isOn() && (boolean)NoRender.getInstance().advancements.getValue()) {
            info.cancel();
        }
    }
}
