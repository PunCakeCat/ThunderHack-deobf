//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders.impl.outline;

import com.mrzak34.thunderhack.util.shaders.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public final class SmokeOutlineShader extends FramebufferShader
{
    public static final SmokeOutlineShader INSTANCE;
    public float time;
    
    public SmokeOutlineShader() {
        super("smokeOutline.frag");
        this.time = 0.0f;
    }
    
    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
        this.setupUniform("alpha0");
        this.setupUniform("resolution");
        this.setupUniform("time");
        this.setupUniform("first");
        this.setupUniform("second");
        this.setupUniform("third");
        this.setupUniform("oct");
    }
    
    public void updateUniforms(final Color first, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final Color second, final Color third, final int oct) {
        GL20.glUniform1i(this.getUniform("texture"), 0);
        GL20.glUniform2f(this.getUniform("texelSize"), 1.0f / this.mc.displayWidth * (radius * quality), 1.0f / this.mc.displayHeight * (radius * quality));
        GL20.glUniform1f(this.getUniform("divider"), 140.0f);
        GL20.glUniform1f(this.getUniform("radius"), radius);
        GL20.glUniform1f(this.getUniform("maxSample"), 10.0f);
        GL20.glUniform1f(this.getUniform("alpha0"), gradientAlpha ? -1.0f : (alphaOutline / 255.0f));
        GL20.glUniform2f(this.getUniform("resolution"), new ScaledResolution(this.mc).getScaledWidth() / duplicate, new ScaledResolution(this.mc).getScaledHeight() / duplicate);
        GL20.glUniform1f(this.getUniform("time"), this.time);
        GL20.glUniform4f(this.getUniform("first"), first.getRed() / 255.0f * 5.0f, first.getGreen() / 255.0f * 5.0f, first.getBlue() / 255.0f * 5.0f, first.getAlpha() / 255.0f);
        GL20.glUniform3f(this.getUniform("second"), second.getRed() / 255.0f * 5.0f, second.getGreen() / 255.0f * 5.0f, second.getBlue() / 255.0f * 5.0f);
        GL20.glUniform3f(this.getUniform("third"), third.getRed() / 255.0f * 5.0f, third.getGreen() / 255.0f * 5.0f, third.getBlue() / 255.0f * 5.0f);
        GL20.glUniform1i(this.getUniform("oct"), oct);
    }
    
    public void stopDraw(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final Color second, final Color third, final int oct) {
        this.mc.gameSettings.entityShadows = this.entityShadows;
        this.framebuffer.unbindFramebuffer();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.mc.getFramebuffer().bindFramebuffer(true);
        this.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        this.startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, second, third, oct);
        this.mc.entityRenderer.setupOverlayRendering();
        this.drawFramebuffer(this.framebuffer);
        this.stopShader();
        this.mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    public void startShader(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final Color second, final Color third, final int oct) {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap();
            this.setupUniforms();
        }
        this.updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, second, third, oct);
    }
    
    public void update(final double speed) {
        this.time += (float)speed;
    }
    
    static {
        INSTANCE = new SmokeOutlineShader();
    }
}
