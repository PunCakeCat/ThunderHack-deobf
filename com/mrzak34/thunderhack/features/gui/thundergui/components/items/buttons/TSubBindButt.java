//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.setting.*;

public class TSubBindButt extends TItem
{
    private final Setting setting;
    public boolean isListening;
    
    public TSubBindButt(final Setting setting, final int x, final int y) {
        super(setting.getName());
        this.isListening = false;
        this.setting = setting;
        this.setLocation((float)x, (float)y);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        FontRender.drawString3(this.setting.getName(), (int)this.x + 2, (int)this.y + 3, -1);
        RenderUtil.drawRect2(this.x, this.y, this.x + 94.0f, this.y + 23.0f, ThunderHackGui.getInstance().buttsColor.getValue().getColorObject().getRGB());
        RenderUtil.drawRect2(this.x + 77.0f - this.deobf(), this.y + 6.0f, this.x + 88.0f, this.y + 17.0f, new Color(-1143811374, true).getRGB());
        if (this.isListening) {
            Util.fr.drawString("...", (int)this.x + 79, (int)this.y + 8, -1);
        }
        else if (this.setting.getValue().toString().toUpperCase().length() == 1) {
            FontRender.drawString3(this.setting.getValue().toString().toUpperCase(), (int)this.x + 79, (int)this.y + 8, new Color(0).getRGB());
        }
        else if (this.setting.getValue().toString().toUpperCase().equals("APOSTROPHE")) {
            Util.fr.drawString("APOSTR", (int)this.x + 79 - this.deobf(), (int)this.y + 8, new Color(0).getRGB());
        }
        else {
            Util.fr.drawString(this.setting.getValue().toString().toUpperCase(), (int)this.x + 79 - this.deobf(), (int)this.y + 8, new Color(0).getRGB());
        }
    }
    
    public int deobf() {
        if (this.setting.getValue().toString().toUpperCase().length() == 1) {
            return 0;
        }
        return 24;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHoveringItem(this.x, this.y, this.x + 94.0f, this.y + 23.0f, (float)mouseX, (float)mouseY)) {
            this.ok();
        }
    }
    
    public boolean isHoveringItem(final float x, final float y, final float x1, final float y1, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.isListening) {
            SubBind subBindbind = new SubBind(keyCode);
            if (subBindbind.toString().equalsIgnoreCase("Escape")) {
                return;
            }
            if (subBindbind.toString().equalsIgnoreCase("Delete")) {
                subBindbind = new SubBind(-1);
            }
            this.setting.setValue(subBindbind);
            this.ok();
        }
    }
    
    public void ok() {
        this.isListening = !this.isListening;
    }
}
