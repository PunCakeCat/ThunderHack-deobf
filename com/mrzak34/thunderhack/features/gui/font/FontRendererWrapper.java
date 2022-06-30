//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.font;

import com.mrzak34.thunderhack.util.*;

public class FontRendererWrapper
{
    private static IFontRenderer fontRenderer;
    
    public static void setFontRenderer(final IFontRenderer fontRenderer) {
        FontRendererWrapper.fontRenderer = fontRenderer;
    }
    
    public static IFontRenderer getFontRenderer() {
        return FontRendererWrapper.fontRenderer;
    }
    
    public static void drawString(final String text, final float x, final float y, final int color) {
        FontRendererWrapper.fontRenderer.drawString(text, x, y, color);
    }
    
    public static void drawStringWithShadow(final String text, final float x, final float y, final int color) {
        FontRendererWrapper.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
    
    public static void drawCenteredString(final String text, final float x, final float y, final int color) {
        FontRendererWrapper.fontRenderer.drawCenteredString(text, x, y, color);
    }
    
    public static int getFontHeight() {
        return FontRendererWrapper.fontRenderer.getFontHeight();
    }
    
    public static float getStringHeight(final String text) {
        return FontRendererWrapper.fontRenderer.getStringHeight(text);
    }
    
    public static float getStringWidth(final String text) {
        return FontRendererWrapper.fontRenderer.getStringWidth(text);
    }
    
    static {
        FontRendererWrapper.fontRenderer = DefaultFontRenderer.INSTANCE;
    }
}
