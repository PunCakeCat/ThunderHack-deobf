//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiIngame.class })
public class MixinGuiIngame extends Gui
{
    public GuiNewChat persistantChatGUI;
    
    @Inject(method = { "renderAttackIndicator" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderAttackIndicator(final float partialTicks, final ScaledResolution p_184045_2_, final CallbackInfo ci) {
        final RenderAttackIndicatorEvent event = new RenderAttackIndicatorEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
