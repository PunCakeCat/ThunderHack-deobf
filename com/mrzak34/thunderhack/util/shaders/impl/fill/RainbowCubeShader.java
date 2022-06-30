//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.shaders.impl.fill;

import com.mrzak34.thunderhack.util.shaders.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class RainbowCubeShader extends FramebufferShader
{
    public static final RainbowCubeShader INSTANCE;
    public float time;
    
    public RainbowCubeShader() {
        super("rainbowCube.frag");
    }
    
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
        this.setupUniform("alpha");
        this.setupUniform("WAVELENGTH");
        this.setupUniform("R");
        this.setupUniform("G");
        this.setupUniform("B");
        this.setupUniform("RSTART");
        this.setupUniform("GSTART");
        this.setupUniform("BSTART");
    }
    
    public void updateUniforms(final float duplicate, final Color start, final int wave, final int rStart, final int gStart, final int bStart) {
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
    
    public void stopDraw(final Color color, final float radius, final float quality, final float duplicate, final Color start, final int wave, final int rStart, final int gStart, final int bStart) {
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
        this.startShader(duplicate, start, wave, rStart, gStart, bStart);
        this.mc.entityRenderer.setupOverlayRendering();
        this.drawFramebuffer(this.framebuffer);
        this.stopShader();
        this.mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    public void startShader(final float duplicate, final Color start, final int wave, final int rStart, final int gStart, final int bStart) {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap();
            this.setupUniforms();
        }
        this.updateUniforms(duplicate, start, wave, rStart, gStart, bStart);
    }
    
    public void update(final double speed) {
        this.time += (float)speed;
    }
    
    static {
        INSTANCE = new RainbowCubeShader();
    }
}
