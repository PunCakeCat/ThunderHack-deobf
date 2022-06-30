//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.thundergui.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.gui.*;

public class TPositionSelector extends TItem
{
    private final Setting setting;
    float ratioX;
    float ratioY;
    
    public TPositionSelector(final Setting setting, final int x, final int y) {
        super(setting.getName());
        this.setting = setting;
        this.setLocation((float)x, (float)y);
        this.ratioX = this.getPosSetting().x;
        this.ratioY = this.getPosSetting().y;
    }
    
    public PositionSetting getPosSetting() {
        return this.setting.getValue();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        if (Mouse.isButtonDown(0)) {
            this.dragSetting(mouseX, mouseY);
        }
        FontRender.drawString3(this.setting.getName(), (int)this.x + 3, (int)this.y + 2, -1);
        RenderUtil.drawRect2(this.x, this.y, this.x + 94.0f, this.y + 67.0f, ThunderHackGui.getInstance().buttsColor.getValue().getColorObject().getRGB());
        RenderUtil.drawRect2(this.x + 3.0f, this.y + 12.0f, this.x + 91.0f, this.y + 62.0f, new Color(-1291056116, true).getRGB());
        RenderUtil.drawRect2(this.x + 3.0f, this.y + 20.0f, this.x + 91.0f, this.y + 20.5, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 3.0f, this.y + 30.0f, this.x + 91.0f, this.y + 30.5, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 3.0f, this.y + 40.0f, this.x + 91.0f, this.y + 40.5, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 3.0f, this.y + 49.5, this.x + 91.0f, this.y + 50.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 11.0f + 3.0f, this.y + 12.0f, this.x + 11.5 + 3.0, this.y + 62.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 22.0f + 3.0f, this.y + 12.0f, this.x + 22.5 + 3.0, this.y + 62.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 33.0f + 3.0f, this.y + 12.0f, this.x + 33.5 + 3.0, this.y + 62.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 44.0f + 3.0f, this.y + 12.0f, this.x + 44.5 + 3.0, this.y + 62.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 55.0f + 3.0f, this.y + 12.0f, this.x + 55.5 + 3.0, this.y + 62.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 66.0f + 3.0f, this.y + 12.0f, this.x + 66.5 + 3.0, this.y + 62.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 77.0f + 3.0f, this.y + 12.0f, this.x + 77.5 + 3.0, this.y + 62.0f, new Color(-1290135014, true).getRGB());
        RenderUtil.drawRect2(this.x + 3.0f, this.y + 11.0f + 50.0f * this.getPosSetting().y, this.x + 91.0f, this.y + 50.0f * this.getPosSetting().y + 12.0f, ThunderGui.getCatColor().getRGB());
        RenderUtil.drawRect2(this.x - 1.0f + 88.0f * this.getPosSetting().x, this.y + 12.0f, this.x + 88.0f * this.getPosSetting().x, this.y + 62.0f, ThunderGui.getCatColor().getRGB());
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.handleMouseClick(mouseX, mouseY, mouseButton);
    }
    
    private boolean handleMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton != 0) {
            return false;
        }
        if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, (double)(this.x + 3.0f), (double)(this.y + 12.0f), 91.0, 50.0)) {
            this.getPosFromClick((float)mouseX, (float)mouseY);
        }
        return true;
    }
    
    private void getPosFromClick(final float mouseX, final float mouseY) {
        final float x1 = (mouseX - this.x) / 92.0f;
        final float y1 = (mouseY - (this.y + 12.0f)) / 50.0f;
        this.getPosSetting().setX(x1);
        this.getPosSetting().setY(y1);
    }
    
    private void dragSetting(final int mouseX, final int mouseY) {
        this.handleMouseClick(mouseX, mouseY, 0);
    }
}
