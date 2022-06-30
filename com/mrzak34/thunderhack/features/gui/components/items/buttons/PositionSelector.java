//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.components.items.buttons;

import com.mrzak34.thunderhack.features.setting.*;

public class PositionSelector extends Button
{
    public Setting setting;
    
    public PositionSelector(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }
}
