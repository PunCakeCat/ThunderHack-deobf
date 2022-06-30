//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;
import net.minecraft.client.gui.*;

@Mixin({ GuiNewChat.class })
public class MixinGuiNewChat extends Gui
{
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void drawRectHook(final int left, final int top, final int right, final int bottom, final int color) {
        Gui.drawRect(left, top, right, bottom, (ChatTweaks.getInstance().isOn() && (boolean)ChatTweaks.getInstance().clean.getValue()) ? 0 : color);
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0))
    public int drawnChatLinesSize(final List<ChatLine> list) {
        return (ChatTweaks.getInstance().isOn() && (boolean)ChatTweaks.getInstance().infinite.getValue()) ? -2147483647 : list.size();
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2))
    public int chatLinesSize(final List<ChatLine> list) {
        return (ChatTweaks.getInstance().isOn() && (boolean)ChatTweaks.getInstance().infinite.getValue()) ? -2147483647 : list.size();
    }
}
