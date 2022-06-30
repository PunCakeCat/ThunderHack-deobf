//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;

public class EzingKids extends Module
{
    public static EzingKids INSTANCE;
    public Setting<Float> scale;
    public Setting<Float> translatey;
    public Setting<Boolean> onlyme;
    
    public EzingKids() {
        super("TEST", "\u0434\u0435\u043b\u0430\u0435\u0442 \u0442\u0435\u0431\u044f \u043c\u0435\u043b\u043a\u0438\u043c", Module.Category.RENDER, true, false, false);
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)0.1f, (T)0.1f, (T)1.0f));
        this.translatey = (Setting<Float>)this.register(new Setting("translate", (T)0.0f, (T)(-5.0f), (T)20.0f));
        this.onlyme = (Setting<Boolean>)this.register(new Setting("onlyme", (T)true));
        EzingKids.INSTANCE = this;
    }
}
