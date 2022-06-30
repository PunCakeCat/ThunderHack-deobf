//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.fontstuff;

import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;

public class FontRender
{
    public static boolean isCustomFontEnabled() {
        return true;
    }
    
    public static float drawStringWithShadow(final String text, final float x, final float y, final int color) {
        return drawStringWithShadow(text, (int)x, (int)y, color);
    }
    
    public static void prepare() {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
    }
    
    public static float drawString(final String text, final float x, final float y, final int color) {
        return drawString(text, (int)x, (int)y, color);
    }
    
    public static float drawStringWithShadow(final String text, final int x, final int y, final int color) {
        if (isCustomFontEnabled()) {
            return Thunderhack.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        return (float)Util.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    public static float drawString(final String text, final int x, final int y, final int color) {
        if (isCustomFontEnabled()) {
            return Thunderhack.fontRenderer.drawString(text, (float)x, (float)y, color);
        }
        return (float)Util.mc.fontRenderer.drawString(text, x, y, color);
    }
    
    public static float drawString2(final String text, final int x, final int y, final int color) {
        if (isCustomFontEnabled()) {
            return Thunderhack.fontRenderer2.drawString(text, (float)x, (float)y, color);
        }
        return (float)Util.mc.fontRenderer.drawString(text, x, y, color);
    }
    
    public static float drawString3(final String text, final int x, final int y, final int color) {
        if (isCustomFontEnabled()) {
            return Thunderhack.fontRenderer3.drawString(text, (float)x, (float)y, color);
        }
        return (float)Util.mc.fontRenderer.drawString(text, x, y, color);
    }
    
    public static int getStringWidth(final String str) {
        if (isCustomFontEnabled()) {
            return Thunderhack.fontRenderer.getStringWidth(str);
        }
        return Util.mc.fontRenderer.getStringWidth(str);
    }
    
    public static int getFontHeight() {
        if (isCustomFontEnabled()) {
            return Thunderhack.fontRenderer.getHeight() + 2;
        }
        return Util.mc.fontRenderer.FONT_HEIGHT;
    }
}
