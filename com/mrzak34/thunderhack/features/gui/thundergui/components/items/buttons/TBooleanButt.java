//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;

public class TBooleanButt extends TItem
{
    private boolean state;
    private final Setting setting;
    
    public TBooleanButt(final Setting setting, final int x, final int y) {
        super(setting.getName());
        this.setting = setting;
        this.setLocation((float)x, (float)y);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        RenderUtil.drawRect2(this.x, this.y, this.x + 94.0f, this.y + 23.0f, ThunderHackGui.getInstance().buttsColor.getValue().getRawColor());
        RenderUtil.drawRect2(this.x + 77.0f, this.y + 6.0f, this.x + 88.0f, this.y + 17.0f, new Color(-1153285566, true).getRGB());
        if (this.getState()) {
            RenderUtil.drawRect2(this.x + 79.0f, this.y + 8.0f, this.x + 86.0f, this.y + 15.0f, ThunderGui.getCatColor().getRGB());
        }
        FontRender.drawString3(this.setting.getName(), (int)(this.x + 2.0f), (int)(this.y + 3.0f), -1);
    }
    
    public boolean getState() {
        return this.setting.getValue();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton != 0 || mouseX < this.x + 77.0f || mouseX > this.x + 88.0f || mouseY < this.y + 6.0f || mouseY > this.y + 17.0f) {
            return;
        }
        this.onNigger();
    }
    
    public void onNigger() {
        this.state = !this.state;
        this.etoshdbratik();
    }
    
    public void etoshdbratik() {
        this.setting.setValue(!this.setting.getValue());
    }
    
    @Override
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.height;
    }
}
