//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;

public class ModuleSettingCategory extends TItem
{
    private final Setting setting;
    
    public ModuleSettingCategory(final Setting setting, final int x, final int y) {
        super(setting.getName());
        this.setting = setting;
        this.setLocation((float)x, (float)y);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        RenderUtil.drawRect2(this.x, this.y, this.x + 270.0f, this.y + 12.0f, ThunderHackGui.getInstance().catcolorinmodule.getValue().getRawColor());
        FontRender.drawString3(this.setting.getName(), (int)this.x + 1, (int)this.y + 2, -1);
    }
}
