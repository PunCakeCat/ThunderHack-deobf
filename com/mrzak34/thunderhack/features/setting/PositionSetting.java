//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.setting;

public class PositionSetting
{
    public float x;
    public float y;
    
    public PositionSetting(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(final float posx) {
        this.x = posx;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setY(final float posy) {
        this.y = posy;
    }
    
    public float getY() {
        return this.y;
    }
}
