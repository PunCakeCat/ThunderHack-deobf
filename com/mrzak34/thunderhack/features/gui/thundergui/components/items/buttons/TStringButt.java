//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.*;

public class TStringButt extends TItem
{
    private final Setting setting;
    public boolean isListening;
    private StringButton.CurrentString currentString;
    
    public TStringButt(final Setting setting, final int x, final int y) {
        super(setting.getName());
        this.currentString = new StringButton.CurrentString("");
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
        RenderUtil.drawRect2(this.x + 2.0f, this.y + 15.0f, this.x + 92.0f, this.y + 26.0f, new Color(-1290792944, true).getRGB());
        if (this.isListening) {
            Util.fr.drawString(this.currentString.getString() + Thunderhack.textManager.getIdleSign(), (int)this.x + 6, (int)this.y + 16, -1);
        }
        else {
            Util.fr.drawString(this.setting.getValue() + " ", (int)this.x + 6, (int)this.y + 16, -1);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHoveringItem(this.x + 2.0f, this.y + 15.0f, this.x + 92.0f, this.y + 26.0f, (float)mouseX, (float)mouseY)) {
            this.ok();
        }
    }
    
    public boolean isHoveringItem(final float x, final float y, final float x1, final float y1, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.isListening) {
            switch (keyCode) {
                case 1: {
                    return;
                }
                case 28: {
                    this.enterString();
                }
                case 14: {
                    this.setString(removeLastChar(this.currentString.getString()));
                    break;
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                this.setString(this.currentString.getString() + typedChar);
            }
        }
    }
    
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    private void enterString() {
        if (this.currentString.getString().isEmpty()) {
            this.setting.setValue(this.setting.getDefaultValue());
        }
        else {
            this.setting.setValue(this.currentString.getString());
        }
        this.setString("");
        this.ok();
    }
    
    public void ok() {
        this.isListening = !this.isListening;
    }
    
    public boolean getState() {
        return !this.isListening;
    }
    
    public void setString(final String newString) {
        this.currentString = new StringButton.CurrentString(newString);
    }
    
    public static String removeLastChar(final String str) {
        String output = "";
        if (str != null && str.length() > 0) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }
    
    public static class CurrentString
    {
        private final String string;
        
        public CurrentString(final String string) {
            this.string = string;
        }
        
        public String getString() {
            return this.string;
        }
    }
}
