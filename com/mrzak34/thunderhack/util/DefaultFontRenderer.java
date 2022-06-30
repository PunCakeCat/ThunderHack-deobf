//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;

public class DefaultFontRenderer implements IFontRenderer
{
    public static DefaultFontRenderer INSTANCE;
    
    private DefaultFontRenderer() {
    }
    
    @Override
    public int drawString(String text, final float x, final float y, final int color) {
        text = text.replaceAll("§", String.valueOf('§'));
        return Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color, false);
    }
    
    @Override
    public int drawStringWithShadow(String text, final float x, final float y, final int color) {
        text = text.replaceAll("§", String.valueOf('§'));
        return Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
    }
    
    @Override
    public int drawCenteredString(String text, final float x, final float y, final int color) {
        text = text.replaceAll("§", String.valueOf('§'));
        return Minecraft.getMinecraft().fontRenderer.drawString(text, x - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2.0f, y, color, false);
    }
    
    @Override
    public float getStringWidth(final String text) {
        return (float)Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }
    
    @Override
    public int getFontHeight() {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }
    
    @Override
    public float getStringHeight(final String text) {
        return (float)Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }
    
    static {
        DefaultFontRenderer.INSTANCE = new DefaultFontRenderer();
    }
}
