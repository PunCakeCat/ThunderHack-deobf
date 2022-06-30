//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders.impl.fill;

import com.mrzak34.thunderhack.util.shaders.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class SmokeShader extends FramebufferShader
{
    public static final SmokeShader INSTANCE;
    public float time;
    
    public SmokeShader() {
        super("smoke.frag");
    }
    
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
        this.setupUniform("first");
        this.setupUniform("second");
        this.setupUniform("third");
        this.setupUniform("oct");
    }
    
    public void updateUniforms(final float duplicate, final Color first, final Color second, final Color third, final int oct) {
        GL20.glUniform2f(this.getUniform("resolution"), new ScaledResolution(this.mc).getScaledWidth() / duplicate, new ScaledResolution(this.mc).getScaledHeight() / duplicate);
        GL20.glUniform1f(this.getUniform("time"), this.time);
        GL20.glUniform4f(this.getUniform("first"), first.getRed() / 255.0f * 5.0f, first.getGreen() / 255.0f * 5.0f, first.getBlue() / 255.0f * 5.0f, first.getAlpha() / 255.0f);
        GL20.glUniform3f(this.getUniform("second"), second.getRed() / 255.0f * 5.0f, second.getGreen() / 255.0f * 5.0f, second.getBlue() / 255.0f * 5.0f);
        GL20.glUniform3f(this.getUniform("third"), third.getRed() / 255.0f * 5.0f, third.getGreen() / 255.0f * 5.0f, third.getBlue() / 255.0f * 5.0f);
        GL20.glUniform1i(this.getUniform("oct"), oct);
    }
    
    public void stopDraw(final Color color, final float radius, final float quality, final float duplicate, final Color first, final Color second, final Color third, final int oct) {
        this.mc.gameSettings.entityShadows = this.entityShadows;
        this.framebuffer.unbindFramebuffer();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.mc.getFramebuffer().bindFramebuffer(true);
        this.red = color.getRed() / 255.0f;
        this.green = color.getGreen() / 255.0f;
        this.blue = color.getBlue() / 255.0f;
        this.alpha = color.getAlpha() / 255.0f;
        this.radius = radius;
        this.quality = quality;
        this.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        this.startShader(duplicate, first, second, third, oct);
        this.mc.entityRenderer.setupOverlayRendering();
        this.drawFramebuffer(this.framebuffer);
        this.stopShader();
        this.mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    public void startShader(final float duplicate, final Color first, final Color second, final Color third, final int oct) {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap();
            this.setupUniforms();
        }
        this.updateUniforms(duplicate, first, second, third, oct);
    }
    
    public void update(final double speed) {
        this.time += (float)speed;
    }
    
    static {
        INSTANCE = new SmokeShader();
    }
}
