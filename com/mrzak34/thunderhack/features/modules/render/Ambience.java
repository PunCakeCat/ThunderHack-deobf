//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;

public class Ambience extends Module
{
    public final Setting<ColorSetting> colorLight;
    
    public Ambience() {
        super("Ambience", "\u0438\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u0446\u0432\u0435\u0442-\u043e\u043a\u0440\u0443\u0436\u0435\u043d\u0438\u044f", Module.Category.RENDER, true, false, false);
        this.colorLight = (Setting<ColorSetting>)this.register(new Setting("Color Light", (T)new ColorSetting(-2013200640)));
    }
}
