//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui;

import net.minecraft.client.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.helper.*;
import net.minecraft.client.shader.*;

public class BlurUtil
{
    private static BlurUtil INSTANCE;
    protected Minecraft mc;
    private final ResourceLocation resourceLocation;
    private ShaderGroup shaderGroup;
    private Framebuffer framebuffer;
    private int lastFactor;
    private int lastWidth;
    private int lastHeight;
    
    public BlurUtil() {
        this.mc = Util.mc;
        this.resourceLocation = new ResourceLocation("shaders/post/blur.json");
    }
    
    private void setInstance() {
        BlurUtil.INSTANCE = this;
    }
    
    public void init() {
        try {
            this.setInstance();
            (this.shaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), this.resourceLocation)).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.framebuffer = this.shaderGroup.mainFramebuffer;
        }
        catch (Exception e) {
            System.out.println("Fuck Blur");
            e.printStackTrace();
        }
    }
    
    public static BlurUtil getInstance() {
        if (BlurUtil.INSTANCE == null) {
            BlurUtil.INSTANCE = new BlurUtil();
        }
        return BlurUtil.INSTANCE;
    }
    
    public void blur(final float xBlur, final float yBlur, final float widthBlur, final float heightBlur, final int strength) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final int width = scaledResolution.getScaledWidth();
        final int height = scaledResolution.getScaledHeight();
        if (this.sizeHasChanged(scaleFactor, width, height) || this.framebuffer == null || this.shaderGroup == null) {
            this.init();
        }
        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderHelper.scissorRect(xBlur, yBlur, widthBlur, heightBlur);
        this.framebuffer.bindFramebuffer(true);
        this.shaderGroup.render(this.mc.timer.renderPartialTicks);
        for (int i = 0; i < 1; ++i) {
            this.shaderGroup.listShaders.get(i).getShaderManager().getShaderUniform("Radius").set((float)strength);
        }
        this.mc.getFramebuffer().bindFramebuffer(false);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }
    
    private boolean sizeHasChanged(final int scaleFactor, final int width, final int height) {
        return this.lastFactor != scaleFactor || this.lastWidth != width || this.lastHeight != height;
    }
    
    static {
        BlurUtil.INSTANCE = new BlurUtil();
    }
}
