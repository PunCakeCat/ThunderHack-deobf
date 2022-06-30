//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.*;

public class TItem extends Feature
{
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private boolean hidden;
    
    public TItem(final String name) {
        super(name);
    }
    
    public void setLocation(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
    }
    
    public void update() {
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.height;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public boolean setHidden(final boolean hidden) {
        return this.hidden = hidden;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
}
