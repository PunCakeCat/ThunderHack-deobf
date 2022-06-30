//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.components.items.buttons;

import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.gui.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import com.mrzak34.thunderhack.features.setting.*;

public class SubBindButton extends Button
{
    private final Setting setting;
    public boolean isListening;
    
    public SubBindButton(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final int color = ColorUtil.toARGB(ClickGui.getInstance().mainColor.getValue().getRed(), ClickGui.getInstance().mainColor.getValue().getGreen(), ClickGui.getInstance().mainColor.getValue().getBlue(), 255);
        RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077) : (this.isHovering(mouseX, mouseY) ? Thunderhack.colorManager.getColorWithAlpha(Thunderhack.moduleManager.getModuleByClass(ClickGui.class).mainColor.getValue().getAlpha()) : Thunderhack.colorManager.getColorWithAlpha(Thunderhack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())));
        if (this.isListening) {
            Thunderhack.textManager.drawStringWithShadow("SubBind", this.x + 2.3f, this.y - 1.7f - OyVeyGui.getClickGui().getTextOffset(), -1);
        }
        else {
            Thunderhack.textManager.drawStringWithShadow(this.setting.getName() + " " + ChatFormatting.GRAY + this.setting.getValue().toString().toUpperCase(), this.x + 2.3f, this.y - 1.7f - OyVeyGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        }
    }
    
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            SubBindButton.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
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
            this.onMouseClick();
        }
    }
    
    public int getHeight() {
        return 14;
    }
    
    public void toggle() {
        this.isListening = !this.isListening;
    }
    
    public boolean getState() {
        return !this.isListening;
    }
}
