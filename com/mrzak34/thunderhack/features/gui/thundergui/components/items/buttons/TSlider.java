//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import java.awt.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.input.*;

public class TSlider extends TItem
{
    private final Number min;
    private final Number max;
    private final int difference;
    private final Setting setting;
    float nigger;
    
    public TSlider(final Setting setting, final int x, final int y) {
        super(setting.getName());
        this.nigger = 1.0f;
        this.setting = setting;
        this.min = setting.getMin();
        this.max = setting.getMax();
        this.difference = this.max.intValue() - this.min.intValue();
        this.setLocation((float)x, (float)y);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        this.upd();
        this.dragSetting(mouseX, mouseY);
        RenderUtil.drawRect2(this.x, this.y, this.x + 94.0f, this.y + 41.0f, ThunderHackGui.getInstance().buttsColor.getValue().getColorObject().getRGB());
        FontRender.drawString3(this.setting.getName(), (int)(this.x + 2.0f), (int)(this.y + 3.0f), -1);
        RenderUtil.drawSmoothRect(this.x + 2.0f, this.y + 23.0f, this.x + 92.0f, this.y + 26.0f, new Color(-1286911414, true).getRGB());
        RenderUtil.drawSmoothRect(this.x + 2.0f, this.y + 23.0f, (this.setting.getValue().floatValue() <= this.min.floatValue()) ? this.x : (this.x + (this.width + 92.0f) * this.partialMultiplier()), this.y + 26.0f, PaletteHelper.fadeColor(new Color(-243126, true).getRGB(), ThunderGui.getCatColor().getRGB(), this.nigger));
        RenderUtil.drawSmoothRect(this.x + 2.0f, this.y + 22.0f, (this.setting.getValue().floatValue() <= this.min.floatValue()) ? (this.x + 1.0f) : (this.x + (this.width + 93.0f) * this.partialMultiplier()), this.y + 27.0f, PaletteHelper.fadeColor(new Color(687622730, true).getRGB(), new Color(ThunderGui.getCatColor().getRed(), ThunderGui.getCatColor().getGreen(), ThunderGui.getCatColor().getBlue(), 30).getRGB(), this.nigger));
        if (this.partialMultiplier() > 0.0f) {
            RenderUtil.drawRect2(this.x + 90.0f * this.partialMultiplier(), this.y + 21.0f, this.x + 90.0f * this.partialMultiplier() + 3.0f, this.y + 27.0f, -1);
        }
        else {
            RenderUtil.drawRect2(this.x, this.y + 21.0f, this.x + 3.0f, this.y + 27.0f, -1);
        }
        final String value = this.setting.getValue().toString();
        RenderUtil.drawSmoothRect(this.x + 2.0f, this.y + 29.0f, Util.fr.getStringWidth(value) + 10 + this.x, this.y + 38.0f, new Color(-1286911414, true).getRGB());
        Util.fr.drawString(value, (int)this.x + 4, (int)this.y + 29, new Color(9079434).getRGB());
        Util.fr.drawString(this.setting.getDescription(), (int)this.x + 4, (int)this.y + 13, new Color(9079434).getRGB());
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovering(mouseX, mouseY)) {
            this.setSettingFromX(mouseX);
        }
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + 94.0f && mouseY >= this.y + 20.0f && mouseY <= this.y + 29.0f;
    }
    
    private void dragSetting(final int mouseX, final int mouseY) {
        if (this.isHovering(mouseX, mouseY) && Mouse.isButtonDown(0)) {
            this.setSettingFromX(mouseX);
        }
    }
    
    public void upd() {
        float percent = 0.0f;
        if (this.setting.getValue() instanceof Integer) {
            final float a = Float.parseFloat(String.valueOf(this.setting.getMax()));
            final float b = Float.parseFloat(String.valueOf(this.setting.getValue()));
            percent = b / a;
        }
        if (this.setting.getValue() instanceof Float) {
            percent = this.setting.getValue() / this.setting.getMax();
        }
        if (this.setting.getValue() instanceof Double) {
            percent = (float)(this.setting.getValue() / this.setting.getMax());
        }
        this.nigger = percent;
    }
    
    private void setSettingFromX(final int mouseX) {
        final float percent = (mouseX - (this.x + 2.0f)) / 90.0f;
        if (this.setting.getValue() instanceof Double) {
            final double result = this.setting.getMin() + this.difference * percent;
            this.setting.setValue(Math.round(10.0 * result) / 10.0);
        }
        else if (this.setting.getValue() instanceof Float) {
            final float result2 = this.setting.getMin() + this.difference * percent;
            this.setting.setValue(Math.round(10.0f * result2) / 10.0f);
        }
        else if (this.setting.getValue() instanceof Integer) {
            this.setting.setValue(this.setting.getMin() + (int)(this.difference * percent));
        }
    }
    
    private float middle() {
        return this.max.floatValue() - this.min.floatValue();
    }
    
    private float part() {
        return this.setting.getValue().floatValue() - this.min.floatValue();
    }
    
    private float partialMultiplier() {
        return this.part() / this.middle();
    }
}
