//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.setting;

import java.awt.*;

public final class ColorSetting
{
    private int color;
    private boolean cycle;
    private int globalOffset;
    
    public ColorSetting(final int color) {
        this.cycle = false;
        this.globalOffset = 0;
        this.color = color;
    }
    
    public ColorSetting(final int color, final boolean cycle) {
        this.cycle = false;
        this.globalOffset = 0;
        this.color = color;
        this.cycle = cycle;
    }
    
    public ColorSetting(final int color, final boolean cycle, final int globalOffset) {
        this.cycle = false;
        this.globalOffset = 0;
        this.color = color;
        this.cycle = cycle;
        this.globalOffset = globalOffset;
    }
    
    public ColorSetting withAlpha(final int alpha) {
        final int red = this.getColor() >> 16 & 0xFF;
        final int green = this.getColor() >> 8 & 0xFF;
        final int blue = this.getColor() & 0xFF;
        return new ColorSetting((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
    }
    
    public static int parseColor(final String nm) throws NumberFormatException {
        final Integer intval = Integer.decode(nm);
        return intval;
    }
    
    public int getColor() {
        if (this.cycle) {
            final float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
            double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0);
            rainbowState %= 360.0;
            final int rgb = Color.getHSBColor((float)(rainbowState / 360.0), hsb[1], hsb[2]).getRGB();
            final int alpha = this.color >> 24 & 0xFF;
            final int red = rgb >> 16 & 0xFF;
            final int green = rgb >> 8 & 0xFF;
            final int blue = rgb & 0xFF;
            return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF);
        }
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    public int getGlobalOffset() {
        return this.globalOffset;
    }
    
    public void setGlobalOffset(final int globalOffset) {
        this.globalOffset = globalOffset;
    }
    
    public int getOffsetColor(final int offset) {
        if (this.cycle) {
            final float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
            double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + offset + this.globalOffset) / 20.0);
            rainbowState %= 360.0;
            final int rgb = Color.getHSBColor((float)(rainbowState / 360.0), hsb[1], hsb[2]).getRGB();
            final int alpha = this.color >> 24 & 0xFF;
            final int red = rgb >> 16 & 0xFF;
            final int green = rgb >> 8 & 0xFF;
            final int blue = rgb & 0xFF;
            return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF);
        }
        return this.color;
    }
    
    public int getRed() {
        if (this.cycle) {
            final float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
            double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0);
            rainbowState %= 360.0;
            final int rgb = Color.getHSBColor((float)(rainbowState / 360.0), hsb[1], hsb[2]).getRGB();
            return rgb >> 16 & 0xFF;
        }
        return this.color >> 16 & 0xFF;
    }
    
    public int getGreen() {
        if (this.cycle) {
            final float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
            double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0);
            rainbowState %= 360.0;
            final int rgb = Color.getHSBColor((float)(rainbowState / 360.0), hsb[1], hsb[2]).getRGB();
            return rgb >> 8 & 0xFF;
        }
        return this.color >> 8 & 0xFF;
    }
    
    public int getBlue() {
        if (this.cycle) {
            final float[] hsb = Color.RGBtoHSB(this.color >> 16 & 0xFF, this.color >> 8 & 0xFF, this.color & 0xFF, null);
            double rainbowState = Math.ceil((System.currentTimeMillis() + 300L + this.globalOffset) / 20.0);
            rainbowState %= 360.0;
            final int rgb = Color.getHSBColor((float)(rainbowState / 360.0), hsb[1], hsb[2]).getRGB();
            return rgb & 0xFF;
        }
        return this.color & 0xFF;
    }
    
    public int getAlpha() {
        return this.color >> 24 & 0xFF;
    }
    
    public Color getColorObject() {
        final int color = this.getColor();
        final int alpha = color >> 24 & 0xFF;
        final int red = color >> 16 & 0xFF;
        final int green = color >> 8 & 0xFF;
        final int blue = color & 0xFF;
        return new Color(red, green, blue, alpha);
    }
    
    public int getRawColor() {
        return this.color;
    }
    
    public boolean isCycle() {
        return this.cycle;
    }
    
    public void setCycle(final boolean cycle) {
        this.cycle = cycle;
    }
    
    public void toggleCycle() {
        this.cycle = !this.cycle;
    }
}
