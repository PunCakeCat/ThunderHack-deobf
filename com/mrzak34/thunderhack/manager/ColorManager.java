//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.features.gui.components.*;
import com.mrzak34.thunderhack.features.setting.*;

public class ColorManager
{
    private float red;
    private float green;
    private float blue;
    private float alpha;
    private Color color;
    
    public ColorManager() {
        this.red = 1.0f;
        this.green = 1.0f;
        this.blue = 1.0f;
        this.alpha = 1.0f;
        this.color = new Color(this.red, this.green, this.blue, this.alpha);
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setColor(final Color color) {
        this.color = color;
    }
    
    public int getColorAsInt() {
        return ColorUtil.toRGBA(this.color);
    }
    
    public int getColorAsIntFullAlpha() {
        return ColorUtil.toRGBA(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255));
    }
    
    public int getColorWithAlpha(final int alpha) {
        if (ClickGui.getInstance().rainbow.getValue()) {
            return ColorUtil.rainbow(Component.counter1[0] * (int)ClickGui.getInstance().rainbowHue.getValue()).getRGB();
        }
        return ((ColorSetting)ClickGui.getInstance().mainColor.getValue()).getColor();
    }
    
    public void setColor(final float red, final float green, final float blue, final float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.updateColor();
    }
    
    public void updateColor() {
        this.setColor(new Color(this.red, this.green, this.blue, this.alpha));
    }
    
    public void setColor(final int red, final int green, final int blue, final int alpha) {
        this.red = red / 255.0f;
        this.green = green / 255.0f;
        this.blue = blue / 255.0f;
        this.alpha = alpha / 255.0f;
        this.updateColor();
    }
    
    public void setColor2(final float red, final float green, final float blue, final float alpha) {
        this.red = red / 255.0f;
        this.green = green / 255.0f;
        this.blue = blue / 255.0f;
        this.alpha = alpha / 255.0f;
        this.updateColor();
    }
    
    public void setRed(final float red) {
        this.red = red;
        this.updateColor();
    }
    
    public void setGreen(final float green) {
        this.green = green;
        this.updateColor();
    }
    
    public void setBlue(final float blue) {
        this.blue = blue;
        this.updateColor();
    }
    
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
        this.updateColor();
    }
}
