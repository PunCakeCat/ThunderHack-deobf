//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui;

import java.awt.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.features.gui.font.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.*;

public class WindowFeature
{
    private final String name;
    public float x;
    public float y;
    public float width;
    public float height;
    public String tooltip;
    public int position;
    Color color1;
    public int wheely;
    
    public WindowFeature(final String name, final float x, final float y, final float width, final float height, final int position) {
        this.color1 = new Color(1554666);
        this.wheely = 0;
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.position = position;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.checkMouseWheel();
        final ScaledResolution sr = new ScaledResolution(ItemUtil.mc);
        RenderUtil.glScissor(this.x, this.y + FontRendererWrapper.getFontHeight() + 3.0f, this.width + this.x, this.y + this.height - 10.0f, sr);
        GL11.glEnable(3089);
        Util.fr.drawStringWithShadow(this.name, this.x + 10.0f, this.y + 12.0f + 10 * this.position + this.wheely, -1);
        RenderUtil.drawSmoothRect(this.width + this.x - 15.0f, this.y + 12.0f + 15 * this.position + this.wheely, this.width + this.x - 5.0f, 10.0f + (this.y + 12.0f + 15 * this.position) + this.wheely, new Color(-366006481, true).getRGB());
        RenderUtil.drawLine2d(this.width + this.x - 15.0f, this.y + 12.0f + 15 * this.position + this.wheely, this.width + this.x - 5.0f, 10.0f + (this.y + 12.0f + 15 * this.position) + this.wheely, 1.0f, this.color1);
        RenderUtil.drawLine2d(this.width + this.x - 15.0f, 10.0f + (this.y + 12.0f + 15 * this.position) + this.wheely, this.width + this.x - 5.0f, this.y + 12.0f + 15 * this.position + this.wheely, 1.0f, this.color1);
        GL11.glDisable(3089);
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.wheely -= 5;
        }
        else if (dWheel > 0) {
            this.wheely += 5;
        }
    }
    
    protected boolean isWithin(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        if (this.isWithin(mouseX, mouseY)) {
            Thunderhack.friendManager.removeFriend(this.name);
        }
    }
    
    public boolean mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        return this.isWithin(mouseX, mouseY);
    }
    
    public String getName() {
        return this.name;
    }
}
