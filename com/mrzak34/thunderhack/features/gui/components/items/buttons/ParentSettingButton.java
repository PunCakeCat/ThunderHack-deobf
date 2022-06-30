//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.components.items.buttons;

import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;

public class ParentSettingButton extends Button
{
    private final Setting<Parent> parentSetting;
    private final ResourceLocation logo;
    
    public ParentSettingButton(final Setting setting) {
        super(setting.getName());
        this.logo = new ResourceLocation("textures/parent.png");
        this.parentSetting = (Setting<Parent>)setting;
        this.width = 15;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, Thunderhack.colorManager.getColorWithAlpha(Thunderhack.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue()));
        Thunderhack.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 1.7f - OyVeyGui.getClickGui().getTextOffset(), -1);
        ParentSettingButton.mc.getTextureManager().bindTexture(this.logo);
        ModuleButton.drawCompleteImage(this.x - 1.5f + this.width - 7.8f, this.y - 5.0f - OyVeyGui.getClickGui().getTextOffset(), 20, 20);
    }
    
    public void update() {
        this.setHidden(!this.parentSetting.isVisible());
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            ParentSettingButton.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            this.getParentSetting().getValue().setExtended(!this.getParentSetting().getValue().isExtended());
        }
    }
    
    public int getHeight() {
        return 14;
    }
    
    public Setting<Parent> getParentSetting() {
        return this.parentSetting;
    }
}
