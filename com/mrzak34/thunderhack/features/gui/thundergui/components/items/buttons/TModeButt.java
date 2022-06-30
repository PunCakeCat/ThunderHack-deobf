//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;

public class TModeButt extends TItem
{
    private final Setting setting;
    
    public TModeButt(final Setting setting, final int x, final int y) {
        super(setting.getName());
        this.setting = setting;
        this.setLocation((float)x, (float)y);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        RenderUtil.drawRect2(this.x, this.y, this.x + 94.0f, this.y + 30.0f, ThunderHackGui.getInstance().buttsColor.getValue().getColorObject().getRGB());
        FontRender.drawString3(this.setting.getName(), (int)(this.x + 2.0f), (int)(this.y + 3.0f), -1);
        RenderUtil.drawRect2(this.x + 2.0f, this.y + 15.0f, this.x + 61.0f, this.y + 26.0f, new Color(-1290792944, true).getRGB());
        Util.fr.drawString(this.setting.currentEnumName(), (int)this.x + 6, (int)this.y + 16, -1);
        if (!this.isHoveringArrow(this.x + 79.0f, this.y + 15.0f, this.x + 90.0f, this.y + 26.0f, (float)mouseX, (float)mouseY)) {
            RenderUtil.drawRect2(this.x + 79.0f, this.y + 15.0f, this.x + 90.0f, this.y + 26.0f, new Color(-1290792944, true).getRGB());
            FontRender.drawString3(">", (int)this.x + 82, (int)this.y + 17, -1);
        }
        else {
            RenderUtil.drawRect2(this.x + 79.0f, this.y + 15.0f, this.x + 90.0f, this.y + 26.0f, new Color(-1288753361, true).getRGB());
            FontRender.drawString3(">", (int)this.x + 82, (int)this.y + 17, ThunderGui.getCatColor().getRGB());
        }
        if (!this.isHoveringArrow(this.x + 64.0f, this.y + 15.0f, this.x + 75.0f, this.y + 26.0f, (float)mouseX, (float)mouseY)) {
            RenderUtil.drawRect2(this.x + 64.0f, this.y + 15.0f, this.x + 75.0f, this.y + 26.0f, new Color(-1290792944, true).getRGB());
            FontRender.drawString3("<", (int)this.x + 67, (int)this.y + 17, -1);
        }
        else {
            RenderUtil.drawRect2(this.x + 64.0f, this.y + 15.0f, this.x + 75.0f, this.y + 26.0f, new Color(-1288753361, true).getRGB());
            FontRender.drawString3("<", (int)this.x + 67, (int)this.y + 17, ThunderGui.getCatColor().getRGB());
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHoveringArrow(this.x + 64.0f, this.y + 15.0f, this.x + 75.0f, this.y + 26.0f, (float)mouseX, (float)mouseY)) {
            this.setting.naoborotEnum();
        }
        if (this.isHoveringArrow(this.x + 79.0f, this.y + 15.0f, this.x + 90.0f, this.y + 26.0f, (float)mouseX, (float)mouseY)) {
            this.setting.increaseEnum();
        }
    }
    
    public boolean isHoveringArrow(final float x, final float y, final float x1, final float y1, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }
}
