//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.components.items.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import com.mrzak34.thunderhack.features.gui.components.*;
import java.util.*;

public class Button extends Item
{
    private boolean state;
    public Timer discriptiondelay;
    
    public Button(final String name) {
        super(name);
        this.discriptiondelay = new Timer();
        this.height = 15;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? Thunderhack.colorManager.getColorWithAlpha(Thunderhack.moduleManager.getModuleByClass(ClickGui.class).mainColor.getValue().getAlpha()) : Thunderhack.colorManager.getColorWithAlpha(Thunderhack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        RenderUtil.drawRect(this.x + 2.0f, this.y - 0.5f, this.x + this.width - 2.0f, this.y - 0.2f, new Color(110, 110, 110, 30).getRGB());
        Thunderhack.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 2.0f - OyVeyGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        if (this.isHovering(mouseX, mouseY) && ClickGui.getInstance().tips.getValue()) {
            if (this.discriptiondelay.getPassedTimeMs() < 2000L) {
                return;
            }
            if (Thunderhack.moduleManager.getModuleByName(this.getName()).getDescription() == null) {
                return;
            }
        }
        else {
            this.discriptiondelay.reset();
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onMouseClick();
        }
    }
    
    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        Button.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }
    
    public void toggle() {
    }
    
    public boolean getState() {
        return this.state;
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        for (final Component component : OyVeyGui.getClickGui().getComponents()) {
            if (!component.drag) {
                continue;
            }
            return false;
        }
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.height;
    }
}
