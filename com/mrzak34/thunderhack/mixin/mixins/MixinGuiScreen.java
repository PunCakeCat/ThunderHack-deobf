//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.features.modules.misc.*;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen extends Gui
{
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTipHook(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        if (ToolTips.getInstance().isOn() && stack.getItem() instanceof ItemShulkerBox) {
            ToolTips.getInstance().renderShulkerToolTip(stack, x, y, (String)null);
            info.cancel();
        }
    }
    
    @Inject(method = { "handleComponentHover" }, at = { @At("HEAD") }, cancellable = true)
    private void handleComponentHoverHook(final ITextComponent component, final int x, final int y, final CallbackInfo info) {
        if (component != null) {
            ChatTweaks.getInstance().saveDickPick(component.getStyle().getHoverEvent().getValue().getUnformattedText(), "png");
            ChatTweaks.getInstance().nado = true;
            ChatTweaks.getInstance().timer.reset();
        }
    }
}
