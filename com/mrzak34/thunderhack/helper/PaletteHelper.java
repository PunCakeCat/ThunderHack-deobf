//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

import java.awt.*;

public class PaletteHelper
{
    public static int getHealthColor(final float health, final float maxHealth) {
        return Color.HSBtoRGB(Math.max(0.0f, Math.min(health, maxHealth) / maxHealth) / 3.0f, 1.0f, 0.8f) | 0xFF000000;
    }
    
    public static int getColor(final Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static int getColor(final int bright) {
        return getColor(bright, bright, bright, 255);
    }
    
    public static int getColor(final int red, final int green, final int blue, final int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }
    
    public static Color astolfo(final boolean clickgui, final int yOffset) {
        final float speed = clickgui ? 3500.0f : 3000.0f;
        float hue = (float)(System.currentTimeMillis() % (int)speed + yOffset);
        if (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5f) {
            hue = 0.5f - (hue - 0.5f);
        }
        hue += 0.5f;
        return Color.getHSBColor(hue, 0.4f, 1.0f);
    }
    
    public static Color astolfo2() {
        final float speed = 3000.0f;
        float hue = (float)(System.currentTimeMillis() % (int)speed + 1L);
        if (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5f) {
            hue = 0.5f - (hue - 0.5f);
        }
        hue += 0.5f;
        return new Color(Color.getHSBColor(hue, 0.4f, 1.0f).getRed(), Color.getHSBColor(hue, 0.4f, 1.0f).getGreen(), Color.getHSBColor(hue, 0.4f, 1.0f).getBlue(), 100);
    }
    
    public static int getColor(final int brightness, final int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }
    
    public static Color rainbow(final int delay, final float saturation, final float brightness) {
        double rainbow = Math.ceil((double)((System.currentTimeMillis() + delay) / 16L));
        rainbow %= 360.0;
        return Color.getHSBColor((float)(rainbow / 360.0), saturation, brightness);
    }
    
    public static int fadeColor(final int startColor, final int endColor, float progress) {
        if (progress > 1.0f) {
            progress = 1.0f - progress % 1.0f;
        }
        return fade(startColor, endColor, progress);
    }
    
    public static int fade(final int startColor, final int endColor, final float progress) {
        final float invert = 1.0f - progress;
        final int r = (int)((startColor >> 16 & 0xFF) * invert + (endColor >> 16 & 0xFF) * progress);
        final int g = (int)((startColor >> 8 & 0xFF) * invert + (endColor >> 8 & 0xFF) * progress);
        final int b = (int)((startColor & 0xFF) * invert + (endColor & 0xFF) * progress);
        final int a = (int)((startColor >> 24 & 0xFF) * invert + (endColor >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }
}
