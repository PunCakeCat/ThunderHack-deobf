//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiMainMenu.class })
public class MixinGuiMainMenu extends GuiScreen
{
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void initGui(final CallbackInfo info) {
        Thunderhack.shaders.init();
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") })
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo info) {
        if (Thunderhack.shaders.currentshader != null) {
            GlStateManager.disableCull();
            Thunderhack.shaders.currentshader.useShader(this.width * 2, this.height * 2, (float)(mouseX * 2), (float)(mouseY * 2), (System.currentTimeMillis() - Thunderhack.shaders.time) / 1000.0f);
            GL11.glBegin(7);
            GL11.glVertex2f(-1.0f, -1.0f);
            GL11.glVertex2f(-1.0f, 1.0f);
            GL11.glVertex2f(1.0f, 1.0f);
            GL11.glVertex2f(1.0f, -1.0f);
            GL11.glEnd();
            GL20.glUseProgram(0);
        }
    }
    
    @Inject(method = { "renderSkybox" }, at = { @At("HEAD") }, cancellable = true)
    public void renderSkybox(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo info) {
        if (Thunderhack.shaders.currentshader != null) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 0))
    public void drawGradientRect1(final GuiMainMenu guiMainMenu, final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        if (Thunderhack.shaders.currentshader == null) {
            this.drawGradientRect(left, top, right, bottom, startColor, endColor);
        }
    }
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawGradientRect(IIIIII)V", ordinal = 1))
    public void drawGradientRect2(final GuiMainMenu guiMainMenu, final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        if (Thunderhack.shaders.currentshader == null) {
            this.drawGradientRect(left, top, right, bottom, startColor, endColor);
        }
    }
}
