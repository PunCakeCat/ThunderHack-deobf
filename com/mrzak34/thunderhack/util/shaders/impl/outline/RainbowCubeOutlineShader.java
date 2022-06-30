//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders.impl.outline;

import com.mrzak34.thunderhack.util.shaders.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public final class RainbowCubeOutlineShader extends FramebufferShader
{
    public static final RainbowCubeOutlineShader INSTANCE;
    public float time;
    
    public RainbowCubeOutlineShader() {
        super("rainbowCubeOutline.frag");
        this.time = 0.0f;
    }
    
    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("color");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
        this.setupUniform("alpha0");
        this.setupUniform("resolution");
        this.setupUniform("time");
        this.setupUniform("WAVELENGTH");
        this.setupUniform("R");
        this.setupUniform("G");
        this.setupUniform("B");
        this.setupUniform("RSTART");
        this.setupUniform("GSTART");
        this.setupUniform("BSTART");
        this.setupUniform("alpha");
    }
    
    public void updateUniforms(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final Color start, final int wave, final int rStart, final int gStart, final int bStart) {
        GL20.glUniform1i(this.getUniform("texture"), 0);
        GL20.glUniform2f(this.getUniform("texelSize"), 1.0f / this.mc.displayWidth * (radius * quality), 1.0f / this.mc.displayHeight * (radius * quality));
        GL20.glUniform1f(this.getUniform("divider"), 140.0f);
        GL20.glUniform1f(this.getUniform("radius"), radius);
        GL20.glUniform1f(this.getUniform("maxSample"), 10.0f);
        GL20.glUniform1f(this.getUniform("alpha0"), gradientAlpha ? -1.0f : (alphaOutline / 255.0f));
        GL20.glUniform2f(this.getUniform("resolution"), new ScaledResolution(this.mc).getScaledWidth() / duplicate, new ScaledResolution(this.mc).getScaledHeight() / duplicate);
        GL20.glUniform1f(this.getUniform("time"), this.time);
        GL20.glUniform1f(this.getUniform("alpha"), start.getAlpha() / 255.0f);
        GL20.glUniform1f(this.getUniform("WAVELENGTH"), (float)wave);
        GL20.glUniform1i(this.getUniform("R"), start.getRed());
        GL20.glUniform1i(this.getUniform("G"), start.getGreen());
        GL20.glUniform1i(this.getUniform("B"), start.getBlue());
        GL20.glUniform1i(this.getUniform("RSTART"), rStart);
        GL20.glUniform1i(this.getUniform("GSTART"), gStart);
        GL20.glUniform1i(this.getUniform("BSTART"), bStart);
    }
    
    public void stopDraw(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final Color start, final int wave, final int rStart, final int gStart, final int bStart) {
        this.mc.gameSettings.entityShadows = this.entityShadows;
        this.framebuffer.unbindFramebuffer();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.mc.getFramebuffer().bindFramebuffer(true);
        this.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        this.startShader(color, radius, quality, gradientAlpha, alphaOutline, duplicate, start, wave, rStart, gStart, bStart);
        this.mc.entityRenderer.setupOverlayRendering();
        this.drawFramebuffer(this.framebuffer);
        this.stopShader();
        this.mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    public void startShader(final Color color, final float radius, final float quality, final boolean gradientAlpha, final int alphaOutline, final float duplicate, final Color start, final int wave, final int rStart, final int gStart, final int bStart) {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap();
            this.setupUniforms();
        }
        this.updateUniforms(color, radius, quality, gradientAlpha, alphaOutline, duplicate, start, wave, rStart, gStart, bStart);
    }
    
    public void update(final double speed) {
        this.time += (float)speed;
    }
    
    static {
        INSTANCE = new RainbowCubeOutlineShader();
    }
}
