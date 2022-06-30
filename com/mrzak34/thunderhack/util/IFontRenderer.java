//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

public interface IFontRenderer
{
    int drawString(final String p0, final float p1, final float p2, final int p3);
    
    int drawStringWithShadow(final String p0, final float p1, final float p2, final int p3);
    
    int drawCenteredString(final String p0, final float p1, final float p2, final int p3);
    
    float getStringWidth(final String p0);
    
    int getFontHeight();
    
    float getStringHeight(final String p0);
}
